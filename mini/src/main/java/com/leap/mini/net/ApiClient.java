package com.leap.mini.net;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leap.mini.R;
import com.leap.mini.mgr.TokenMgr;
import com.leap.mini.net.network.subscriber.ApiException;
import com.leap.mini.net.network.subscriber.NullOnEmptyConverterFactory;
import com.leap.mini.net.network.subscriber.TokenExpiredException;
import com.leap.mini.util.DateUtil;
import com.leap.mini.util.IsEmpty;
import com.leap.mini.util.NetworkUtil;

import android.content.Context;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 基础API 网络访问客户端
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class ApiClient {
  private static String baseUrl;
  private static Retrofit platformClient;
  private static Retrofit shopClient;
  private static Object mContext;
  private static Gson gson = new GsonBuilder().setDateFormat(DateUtil.DEFAULT_DATE_FORMAT).create();
  // 拦截器 处理请求的status code
  private static Interceptor requestErrorInterceptor = new Interceptor() {
    @Override
    public Response intercept(Chain chain) throws IOException {
      if (!NetworkUtil.isConnected((Context) mContext)) {
        throw new ApiException(0, ((Context) mContext).getString(R.string.network_err_0));
      }
      Request request = chain.request();
      Response response = chain.proceed(request);
      ApiException e = null;
      if (401 == response.code()) {
        // 使用EventBus通知跳转到登陆页面
        throw new TokenExpiredException(401,
            ((Context) mContext).getString(R.string.network_request_err_401));
      } else if (403 == response.code()) {
        e = new ApiException(403, ((Context) mContext).getString(R.string.network_request_err_403));
      } else if (503 == response.code()) {
        e = new ApiException(503, ((Context) mContext).getString(R.string.network_request_err_503));
      } else if (500 == response.code()) {
        e = new ApiException(500, ((Context) mContext).getString(R.string.network_request_err_500));
      } else if (404 == response.code()) {
        e = new ApiException(404, ((Context) mContext).getString(R.string.network_request_err_404));
      }
      if (!IsEmpty.object(e)) {
        throw e;
      }
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
  // 获取门店Cookie并设置Cookie到header
  private static Interceptor setShopCookie = new Interceptor() {
    @Override
    public Response intercept(Chain chain) throws IOException {
      Request request;
      String token = TokenMgr.getShopUserToken();
      if (!IsEmpty.string(token)) {
        request = chain.request().newBuilder().addHeader("Cookie", token).build();
      } else {
        request = chain.request();
      }
      return chain.proceed(request);
    }
  };
  // 保存门店Cookie
  private static Interceptor getShopCookie = new Interceptor() {
    @Override
    public Response intercept(Chain chain) throws IOException {
      Request request = chain.request();
      Response response = chain.proceed(request);
      String cookie = response.headers().get("Set-Cookie");
      if (!IsEmpty.string(cookie) && cookie.contains("jwt=")
          && IsEmpty.string(TokenMgr.getShopToken())) {
        TokenMgr.updateShopToken(cookie);
      }
      return response;
    }
  };

  // 未登陆之前使用该对象 没有Token
  public static Retrofit platformClient() {
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
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

    return platformClient;
  }

  public static Retrofit shopClient() {
    // TODO
    return shopClient(baseUrl);
  }

  // 在header里添加了token的retrofit对象
  public static Retrofit shopClient(String baseUrl) {
    if (baseUrl == null) {
      return null;
    }
    if (!baseUrl.endsWith("/")) {
      baseUrl += "/";
    }

    if (!IsEmpty.object(shopClient) && shopClient.baseUrl().toString().equals(baseUrl)) {
      return shopClient;
    }
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(getShopCookie)
        .addInterceptor(setShopCookie).addInterceptor(requestErrorInterceptor)
        .addInterceptor(logging).connectTimeout(60, TimeUnit.SECONDS).retryOnConnectionFailure(true)
        .writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();

    shopClient = new Retrofit.Builder().baseUrl(baseUrl)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(new NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create(gson)).client(httpClient).build();

    return shopClient;
  }

  public static void init(Context context, String baseUrl) {
    ApiClient.baseUrl = baseUrl;
    ApiClient.mContext = context;
  }
  // public static RequestBody createBody(Map<String, Object> params) {
  // RequestBody body = RequestBody.create(
  // okhttp3.MediaType.parse("application/json; " + "charset=utf-8"),
  // (new JSONObject(params)).toString());
  // return body;
  // }
  //
  // public static <T> RequestBody createBodyFromBean(T t) {
  // RequestBody body = RequestBody.create(
  // okhttp3.MediaType.parse("application/json; " + "charset=utf-8"), new
  // Gson().toJson(t));
  // Log.d("REQUEST_BODY_STRING", body.toString());
  // return body;
  // }

}
