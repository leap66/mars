package com.leap.mars.util;

import android.content.Intent;

import com.leap.mars.MarsApp;
import com.leap.mars.cmp.SessionMgr;
import com.leap.mars.config.AppConfig;
import com.leap.mars.model.User;
import com.leap.mars.presenter.auth.LoginActivity;
import com.leap.mars.presenter.main.MainActivity;
import com.leap.mini.mgr.StorageMgr;
import com.leap.mini.mgr.TokenMgr;

/**
 * 
 * Created by neil on 2017/8/4.
 */

public class ShortcutMgr {

  /**
   * 安全退出门店
   */
  public static void logout() {
    TokenMgr.clear(StorageMgr.LEVEL_GLOBAL);
    // 当退出应用的时候，就应该将socket取消
    Intent intent = new Intent(MarsApp.getInstance(), LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    MarsApp.getInstance().startActivity(intent);
  }

  public static void login(User user) {
    SessionMgr.updateUser(user);
    StorageMgr.set(AppConfig.MOBILE, user.getMobile(), StorageMgr.LEVEL_GLOBAL);
    Intent intent = new Intent(MarsApp.getInstance(), MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    MarsApp.getInstance().startActivity(intent);
  }
}
