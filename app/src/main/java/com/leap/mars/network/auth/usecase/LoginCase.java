package com.leap.mars.network.auth.usecase;

import com.leap.mars.network.auth.AuthServiceApi;
import com.leap.mini.net.BaseUseCase;

import rx.Observable;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description : 登陆
 */
public class LoginCase extends BaseUseCase<AuthServiceApi> {
  private String mobile;
  private String password;

  public LoginCase(String mobile, String password) {
    this.mobile = mobile;
    this.password = password;
  }

  @Override
  protected Observable buildUseCaseObservable() {
    return platformApiClient().login(mobile, password);
  }
}
