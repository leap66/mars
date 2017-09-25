package com.leap.mars.network.auth.usecase;

import com.leap.mars.cmp.SessionMgr;
import com.leap.mars.network.auth.AuthServiceApi;
import com.leap.mini.net.BaseUseCase;

import rx.Observable;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description : 手机号修改
 */
public class MobileResetCase extends BaseUseCase<AuthServiceApi> {
  private String mobile;
  private String code;

  public MobileResetCase(String mobile, String code) {
    this.mobile = mobile;
    this.code = code;
  }

  @Override
  protected Observable buildUseCaseObservable() {
    return platformApiClient().mobileReset(mobile, SessionMgr.getUser().getMobile(), code);
  }
}
