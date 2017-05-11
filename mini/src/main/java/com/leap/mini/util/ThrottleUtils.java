package com.leap.mini.util;

import android.util.Log;

/**
 * Created by weiyaling on 2017/4/25.
 * <p>
 * </> 阀门工具，加入间隔时间，防止连续点击发出多次请求
 */

public class ThrottleUtils {

  private static long lastClickTime = 0;

  public static boolean doubleClick() {
    long time = System.currentTimeMillis();
    long timeD = time - lastClickTime;
    if (0 < timeD && timeD < 500) {
      Log.e("连续点击:  ", timeD + "");
      return true;
    }
    lastClickTime = time;
    return false;
  }
}
