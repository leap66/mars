package com.leap.mini;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * 获取系统类工具
 * <p>
 * </> Created by weiyaling on 2017/3/13.
 */

public class SystemUtils {

  /**
   * 获取屏幕宽度
   */
  public static int getScreenWidth() {
    DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
    return metrics.widthPixels;
  }

  /**
   * 获取屏幕高度
   */
  public static int getScreenHeight() {
    DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
    return metrics.heightPixels;
  }
}
