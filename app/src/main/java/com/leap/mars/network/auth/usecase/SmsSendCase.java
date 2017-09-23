package com.leap.mars.network.auth.usecase;

import com.leap.mars.network.auth.AuthServiceApi;
import com.leap.mini.net.BaseUseCase;

import rx.Observable;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description :获取验证码
 */
public class SmsSendCase extends BaseUseCase<AuthServiceApi> {
  private String mobile;
  private boolean exist;

  public SmsSendCase(String mobile, boolean exist) {
    this.mobile = mobile;
    this.exist = exist;
  }

  @Override
  protected Observable buildUseCaseObservable() {
    return platformApiClient().smsSend(mobile, exist);
  }
}
