package com.leap.mars.network.auth.usecase;

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
  private String oldMobile;
  private String code;

  public MobileResetCase(String mobile, String oldMobile, String code) {
    this.mobile = mobile;
    this.oldMobile = oldMobile;
    this.code = code;
  }

  @Override
  protected Observable buildUseCaseObservable() {
    return platformApiClient().mobileReset(mobile, oldMobile, code);
  }
}
