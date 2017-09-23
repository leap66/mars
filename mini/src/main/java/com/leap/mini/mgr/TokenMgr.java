package com.leap.mini.mgr;

/**
 * Token 管理器
 * <p>
 * </> Created by weiyaling on 17/3/7.
 */
public class TokenMgr {

  // key
  private static String KEY_USER_TOKEN = "userToken";

  private static String userToken;

  public static void init() {
    TokenMgr.userToken = StorageMgr.get(KEY_USER_TOKEN, StorageMgr.LEVEL_GLOBAL);
  }

  // 清除
  public static void clear(String level) {
    if (level.equals(StorageMgr.LEVEL_USER) || level.equals(StorageMgr.LEVEL_GLOBAL)) {
      clearUserToken();
    }
  }

  private static void clearUserToken() {
    StorageMgr.set(KEY_USER_TOKEN, null, StorageMgr.LEVEL_GLOBAL);
    TokenMgr.userToken = null;
  }

  // 更新用户Token
  public static void updateUserToken(String token) {
    TokenMgr.userToken = token;
    StorageMgr.set(KEY_USER_TOKEN, TokenMgr.userToken, StorageMgr.LEVEL_GLOBAL);
  }

  // 以下是对象的方法
  public static String getUserToken() {
    return TokenMgr.userToken;
  }

  public static boolean hasUser() {
    return getUserToken() != null;
  }
}
