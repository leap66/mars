package com.leap.mars.network.user.usecase;

import com.leap.mars.model.User;
import com.leap.mars.network.user.UserServiceApi;
import com.leap.mini.net.BaseUseCase;

import rx.Observable;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description : 更新用户信息
 */
public class UserUpdateCase extends BaseUseCase<UserServiceApi> {
  private User user;

  public UserUpdateCase(User user) {
    this.user = user;
  }

  @Override
  protected Observable buildUseCaseObservable() {
    return platformApiClient().update(user);
  }
}
