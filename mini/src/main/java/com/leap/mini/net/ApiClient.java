package com.leap.mini.net;

import com.leap.mini.R;
import com.leap.mini.mgr.ContextMgr;
import com.leap.mini.mgr.TokenMgr;
import com.leap.mini.net.network.subscriber.ApiException;
import com.leap.mini.net.network.subscriber.NullOnEmptyConverterFactory;
import com.leap.mini.net.network.subscriber.TokenExpiredException;
import com.leap.mini.util.GsonUtil;
import com.leap.mini.util.IsEmpty;
import com.leap.mini.util.NetworkUtil;
import com.leap.mini.widget.gsonconverter.GsonConverterFactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * 基础API 网络访问客户端
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class ApiClient {
  private static String baseUrl;
  private static Retrofit platformClient;

  // 拦截器 处理请求的status code
  private static Interceptor requestErrorInterceptor = new Interceptor() {
    @Override
    public Response intercept(Chain chain) throws IOException {
      if (!NetworkUtil.isConnected(ContextMgr.getInstance()))
        throw new ApiException(0, ContextMgr.getInstance().getString(R.string.network_err_0));
      Request request = chain.request();
      Response response = chain.proceed(request);
      parser(response);
      return response;
    }
  };
  // 获取用户Cookie并设置Cookie到header
  private static Interceptor setUserCookie = new Interceptor() {
    @Override
    public Response intercept(Chain chain) throws IOException {
      Request request;
      String token = TokenMgr.getUserToken();
      if (!IsEmpty.string(token)) {
        request = chain.request().newBuilder().addHeader("Cookie", token).build();
      } else {
        request = chain.request();
      }
      return chain.proceed(request);
    }
  };
  // 保存用户Cookie
  private static Interceptor getUserCookie = new Interceptor() {
    @Override
    public Response intercept(Chain chain) throws IOException {
      Request request = chain.request();
      Response response = chain.proceed(request);
      String cookie = response.headers().get("Set-Cookie");
      if (!IsEmpty.string(cookie) && cookie.contains("jwt=")) {
        TokenMgr.updateUserToken(cookie);
      }
      return response;
    }
  };

  // 未登陆之前使用该对象 没有Token
  static Retrofit platformClient() {
    if (null != platformClient) {
      return platformClient;
    }
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient okClient = new OkHttpClient.Builder().retryOnConnectionFailure(true)
        .addInterceptor(logging).addInterceptor(getUserCookie).addInterceptor(setUserCookie)
        .addInterceptor(requestErrorInterceptor).build();
    platformClient = new Retrofit.Builder().baseUrl(baseUrl).client(okClient)
        .addConverterFactory(new NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create(GsonUtil.getInstance()))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

    return platformClient;
  }

  public static void init(String baseUrl) {
    ApiClient.baseUrl = baseUrl;
  }

  private static void parser(Response response) throws IOException {
    ApiException e = null;
    if (401 == response.code()) {
      throw new TokenExpiredException(401,
          ContextMgr.getInstance().getString(R.string.network_request_err_401));
    } else if (403 == response.code()) {
      e = new ApiException(403,
          ContextMgr.getInstance().getString(R.string.network_request_err_403));
    } else if (503 == response.code()) {
      e = new ApiException(503,
          ContextMgr.getInstance().getString(R.string.network_request_err_503));
    } else if (500 == response.code()) {
      e = new ApiException(500,
          ContextMgr.getInstance().getString(R.string.network_request_err_500));
    } else if (404 == response.code()) {
      e = new ApiException(404,
          ContextMgr.getInstance().getString(R.string.network_request_err_404));
    }
    if (!IsEmpty.object(e)) {
      throw e;
    }
  }

}
