package com.leap.mini.net.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leap.mini.net.network.subscriber.ApiException;
import com.leap.mini.net.network.subscriber.NullOnEmptyConverterFactory;
import com.leap.mini.net.network.subscriber.TokenExpiredException;
import com.leap.mini.util.DateUtil;
import com.leap.mini.util.IsEmpty;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 基础API APP更新客户端
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class UpdateClient {
  private static String baseUrl;
  private static Retrofit updateClient;
  private static Gson gson = new GsonBuilder().setDateFormat(DateUtil.DEFAULT_DATE_FORMAT).create();

  private static Interceptor requestErrorInterceptor = new Interceptor() {
    @Override
    public Response intercept(Chain chain) throws IOException {
      Request request = chain.request();
      Response response = chain.proceed(request);
      ApiException e = null;
      if (401 == response.code()) {
        throw new TokenExpiredException(401, "登录已过期,请重新登录!");
      } else if (403 == response.code()) {
        e = new ApiException(403, "禁止访问!");
      } else if (503 == response.code()) {
        e = new ApiException(503, "服务器升级中!");
      } else if (500 == response.code()) {
        e = new ApiException(500, "服务器内部错误!");
      } else if (404 == response.code()) {
        e = new ApiException(404, "链接错误");
      }
      if (!IsEmpty.object(e)) {
        throw e;
      }
      return response;
    }
  };

  // 未登陆之前使用该对象 没有Token
  public static Retrofit updateClient() {
    if (null != updateClient) {
      return updateClient;
    }
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient okClient = new OkHttpClient.Builder().retryOnConnectionFailure(true)
        .addInterceptor(logging).connectTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(requestErrorInterceptor).build();
    updateClient = new Retrofit.Builder().baseUrl(baseUrl).client(okClient)
        .addConverterFactory(new NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

    return updateClient;
  }

  public static void setBaseUrl(String baseUrl) {
    UpdateClient.baseUrl = baseUrl;
  }
}
