package com.leap.mini.mgr;

import android.content.Context;

/**
 * 全局Context管理器 Application
 * <p>
 * </> Created by ylwei on 2017/12/14.
 */
public class ContextMgr {
  private static Object instance;

  public static Context getInstance() {
    if (null == instance)
      throw new NullPointerException("you should init ContextMgr first");
    return (Context) instance;
  }

  public static void init(Context context) {
    if (null == context)
      throw new NullPointerException("context is NULL");
    instance = context.getApplicationContext();
  }
}
