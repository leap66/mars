package com.leap.mars.config;

/**
 * 精确参数配置
 * <p>
 * </>Created by weiyaling on 2017/3/9.
 */
public class PrecisionConfig {

  public static class User {
    public static int name = 16;
    public static int mobile = 11;
    public static int password = 16;
    public static int idCard = 18;
    public static int minPwd = 6;
    public static int authCode = 6;
    public static int countTime = 60;
    public static int joinCode = 6;
  }
}
