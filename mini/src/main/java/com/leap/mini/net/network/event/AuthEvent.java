package com.leap.mini.net.network.event;

/**
 * 过期 Event
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class AuthEvent {
  public static int TOKEN_EXPIRED = 1;

  public int type;

  public AuthEvent(int type) {
    this.type = type;
  }
}
