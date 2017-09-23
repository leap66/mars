package com.leap.mars.network.auth.usecase;

import com.leap.mars.network.auth.AuthServiceApi;
import com.leap.mini.net.BaseUseCase;

import rx.Observable;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description : 密码修改
 */
public class PwdResetCase extends BaseUseCase<AuthServiceApi> {
  private String mobile;
  private String password;
  private String code;

  public PwdResetCase(String mobile, String password, String code) {
    this.mobile = mobile;
    this.password = password;
    this.code = code;
  }

  @Override
  protected Observable buildUseCaseObservable() {
    return platformApiClient().pwdReset(mobile, password, code);
  }
}
