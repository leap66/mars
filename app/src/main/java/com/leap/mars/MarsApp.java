package com.leap.mars;

import android.app.Application;

import com.leap.mars.cmp.SessionMgr;
import com.leap.mini.mgr.ContextMgr;
import com.leap.mini.mgr.StorageMgr;
import com.leap.mini.mgr.TokenMgr;
import com.leap.mini.mgr.logger.CrashHandler;
import com.leap.mini.net.ApiClient;

/**
 * MarsApp
 * <p>
 * </> Created by weiyaling on 2017/5/11.
 */
public class MarsApp extends Application {
  private static MarsApp instance;

  public static MarsApp getInstance() {
    return instance;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    ContextMgr.init(this);
    // 初始化网络组件
    ApiClient.init(BuildConfig.SERVER_URL);
    // 初始化异常捕获组件
    CrashHandler.getInstance().init();
    // 初始化缓存组件
    StorageMgr.init();
    // 初始化Token组件
    TokenMgr.init();
    // 初始化Session组件
    SessionMgr.init();
  }

  @Override
  public void onTerminate() {
    SessionMgr.updateCancel();
    super.onTerminate();
  }
}
