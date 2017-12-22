package com.leap.mini.mgr;

import android.content.Context;

/**
 * 全局Context管理器 Application
 * <p>
 * </> Created by ylwei on 2017/12/14.
 */
public class ContextMgr {
  private static Context instance;

  public static Context getInstance() {
    if (null == instance)
      throw new NullPointerException("you should init first");
    return instance;
  }

  public static void init(Context mInstance) {
    if (null == mInstance)
      throw new NullPointerException("context is NULL");
    instance = mInstance;
  }
}
