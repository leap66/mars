package com.leap.mars.network.auth;

import com.leap.mars.model.Auth;
import com.leap.mini.model.network.Response;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description :
 */
public interface AuthServiceApi {

  /**
   * 注册
   *
   * @param auth
   *          注册信息
   * @return String
   */
  @POST("app/auth/register")
  Observable<Response<String>> register(@Body Auth auth);

  /**
   * 登陆
   *
   * @param mobile
   *          手机号
   * @param password
   *          密码
   * @return String
   */
  @POST("app/auth/login")
  Observable<Response<String>> login(@Query("mobile") String mobile,
      @Query("password") String password);

  /**
   * 注销登陆
   *
   * @param id
   *          用户ID
   * @return String
   */
  @POST("app/auth/login")
  Observable<Response<String>> logout(@Query("id") String id);

  /**
   * 发送验证码
   *
   * @param mobile
   *          手机号
   * @param exist
   *          核验是否存在
   * @return String
   */
  @POST("app/auth/login")
  Observable<Response<String>> smsSend(@Query("mobile") String mobile,
      @Query("exist") boolean exist);

  /**
   * 校验验证码
   *
   * @param mobile
   *          手机号
   * @param code
   *          验证码
   * @return String
   */
  @POST("app/auth/login")
  Observable<Response<String>> smsCheck(@Query("mobile") String mobile, @Query("code") String code);

  /**
   * 密码修改
   *
   * @param mobile
   *          手机号
   * @param password
   *          密码
   * @param code
   *          验证码
   * @return String
   */
  @POST("app/auth/reset/password")
  Observable<Response<String>> pwdReset(@Query("mobile") String mobile,
      @Query("password") String password, @Query("code") String code);

  /**
   * 手机号修改
   *
   * @param mobile
   *          手机号
   * @param oldMobile
   *          新手机号
   * @param code
   *          验证码
   * @return String
   */
  @POST("app/auth/reset/mobile")
  Observable<Response<Boolean>> mobileReset(@Query("mobile") String mobile,
      @Query("oldMobile") String oldMobile, @Query("code") String code);
}
