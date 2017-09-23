package com.leap.mars.network.user;

import com.leap.mars.model.User;
import com.leap.mini.model.network.Response;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description :
 */
public interface UserServiceApi {

  /**
   * 获取用户信息
   *
   * @param id
   *          用户ID
   * @return String
   */
  @GET("app/user/get")
  Observable<Response<User>> get(@Query("id") String id);

  /**
   * 更新用户信息
   *
   * @param user
   *          用户信息
   * @return String
   */
  @POST("app/user/update")
  Observable<Response<User>> update(@Body User user);
}
