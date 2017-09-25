package com.leap.mini.widget.validator;

import android.view.View;
import android.widget.EditText;

/**
 * 框焦点校验器
 * <p>
 * </> Created by ylwei on 2017/8/3.
 */
public class FocusValidator {

  /**
   * 输入框焦点校验
   * 
   * @param editText
   *          被监听输入框
   * @param views
   *          根据校验框改变状态View
   */
  public void verify(EditText editText, final View... views) {
    for (View view : views) {
      view.setEnabled(false);
    }
    editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        for (View view : views) {
          view.setEnabled(hasFocus);
        }
      }
    });
  }
}
