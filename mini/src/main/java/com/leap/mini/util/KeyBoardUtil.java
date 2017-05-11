package com.leap.mini.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 键盘显示工具
 * <p>
 * </> Created by weiyaling on 2017/3/16.
 */

public class KeyBoardUtil {

  /**
   * 显示或取消软键盘
   *
   * @param view
   *          上下文
   * @param isShow
   *          是否显示软键盘
   */
  public static void keyShow(View view, boolean isShow) {
    InputMethodManager imm = (InputMethodManager) view.getContext()
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    if (isShow) {
      // 强制显示软键盘
      imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    } else {
      // 强制取消软键盘
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
  }
}
