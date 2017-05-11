package com.leap.mini.mgr;

import com.leap.mini.util.IsEmpty;

/**
 * Token 管理器
 * <p>
 * </> Created by weiyaling on 17/3/7.
 */

public class TokenMgr {

  // key
  private static String KEY_USER_TOKEN = "userToken";
  private static String KEY_SHOP_TOKEN = "shopToken";

  private static String userToken;
  private static String shopToken;

  public static void init() {
    TokenMgr.userToken = StorageMgr.get(KEY_USER_TOKEN, StorageMgr.LEVEL_GLOBAL);
    TokenMgr.shopToken = StorageMgr.get(KEY_SHOP_TOKEN, StorageMgr.LEVEL_GLOBAL);
  }

  // 清除
  public static void clear(String level) {
    if (level.equals(StorageMgr.LEVEL_USER) || level.equals(StorageMgr.LEVEL_GLOBAL)) {
      clearUserToken();
    }
    if (level.equals(StorageMgr.LEVEL_SHOP) || level.equals(StorageMgr.LEVEL_GLOBAL)) {
      clearShopToken();
    }
  }

  private static void clearUserToken() {
    StorageMgr.set(KEY_USER_TOKEN, null, StorageMgr.LEVEL_GLOBAL);
    TokenMgr.userToken = null;
  }

  private static void clearShopToken() {
    StorageMgr.set(KEY_SHOP_TOKEN, null, StorageMgr.LEVEL_GLOBAL);
    TokenMgr.shopToken = null;
  }

  // 更新用户Token
  public static void updateUserToken(String token) {
    TokenMgr.userToken = token;
    StorageMgr.set(KEY_USER_TOKEN, TokenMgr.userToken, StorageMgr.LEVEL_GLOBAL);
  }

  // 更新门店Token
  public static void updateShopToken(String token) {
    TokenMgr.shopToken = token;
    StorageMgr.set(KEY_SHOP_TOKEN, TokenMgr.shopToken, StorageMgr.LEVEL_GLOBAL);
  }

  // 以下是对象的方法
  public static String getUserToken() {
    return TokenMgr.userToken;
  }

  public static String getShopToken() {
    return TokenMgr.shopToken;
  }

  public static String getShopUserToken() {
    String token = "";
    if (!IsEmpty.string(TokenMgr.shopToken)) {
      token = TokenMgr.shopToken;
    } else {
      token = TokenMgr.userToken;
    }
    return token;
  }

  public static boolean isExpired() {
    return getShopUserToken() == null;
  }

  public static boolean hasShop() {
    return getShopToken() != null;
  }
}
