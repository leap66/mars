package com.leap.mars.network.user.usecase;

import com.leap.mars.network.user.UserServiceApi;
import com.leap.mini.net.BaseUseCase;

import rx.Observable;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description : 获取用户信息
 */
public class UserGetCase extends BaseUseCase<UserServiceApi> {
  private String id;

  public UserGetCase(String id) {
    this.id = id;
  }

  @Override
  protected Observable buildUseCaseObservable() {
    return platformApiClient().get(id);
  }
}
