package com.leap.mars.network.auth.usecase;

import com.leap.mars.network.auth.AuthServiceApi;
import com.leap.mini.net.BaseUseCase;

import rx.Observable;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description : 注销
 */
public class LogoutCase extends BaseUseCase<AuthServiceApi> {
  private String id;

  public LogoutCase(String id) {
    this.id = id;
  }

  @Override
  protected Observable buildUseCaseObservable() {
    return platformApiClient().logout(id);
  }
}
