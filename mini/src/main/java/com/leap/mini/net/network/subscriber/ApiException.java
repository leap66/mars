package com.leap.mini.net.network.subscriber;

import java.io.IOException;

/**
 * 网络返回异常解析
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class ApiException extends IOException {

  private int code;

  public ApiException(int code, String message) {
    super(message);
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
