package com.leap.mini.widget.validator.filter;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class LengthInputFilter implements InputFilter {
  private int length;

  public LengthInputFilter(int length) {
    this.length = length;
  }

  public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
      int dend) {

    // 删除等特殊字符，直接返回
    if ("".equals(source.toString())) {
      return null;
    }

    return source.subSequence(0,
        source.length() > length - dest.length() ? length - dest.length() : source.length());
  }
}
