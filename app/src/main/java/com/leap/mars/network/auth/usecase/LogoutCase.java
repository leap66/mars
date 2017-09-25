package com.leap.mars.network.auth.usecase;

import com.leap.mars.cmp.SessionMgr;
import com.leap.mars.network.auth.AuthServiceApi;
import com.leap.mini.net.BaseUseCase;

import rx.Observable;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description : 注销
 */
public class LogoutCase extends BaseUseCase<AuthServiceApi> {

  @Override
  protected Observable buildUseCaseObservable() {
    return platformApiClient().logout(SessionMgr.getUser().getId());
  }
}
