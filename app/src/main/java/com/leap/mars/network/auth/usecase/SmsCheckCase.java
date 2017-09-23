package com.leap.mars.network.auth.usecase;

import com.leap.mars.network.auth.AuthServiceApi;
import com.leap.mini.net.BaseUseCase;

import rx.Observable;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description : 校验验证码
 */
public class SmsCheckCase extends BaseUseCase<AuthServiceApi> {
  private String mobile;
  private String code;

  public SmsCheckCase(String mobile, String code) {
    this.mobile = mobile;
    this.code = code;
  }

  @Override
  protected Observable buildUseCaseObservable() {
    return platformApiClient().smsCheck(mobile, code);
  }
}
