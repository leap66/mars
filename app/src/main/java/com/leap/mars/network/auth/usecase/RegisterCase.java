package com.leap.mars.network.auth.usecase;

import com.leap.mars.model.Auth;
import com.leap.mars.network.auth.AuthServiceApi;
import com.leap.mini.net.BaseUseCase;

import rx.Observable;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description : 注册
 */
public class RegisterCase extends BaseUseCase<AuthServiceApi> {
  private Auth auth;

  public RegisterCase(Auth auth) {
    this.auth = auth;
  }

  @Override
  protected Observable buildUseCaseObservable() {
    return platformApiClient().register(auth);
  }
}
