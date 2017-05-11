package com.leap.mini.util.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * EditText 字段改变接口
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public abstract class TextChangedListener implements TextWatcher {

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
  }

  @Override
  public abstract void onTextChanged(CharSequence s, int start, int before, int count);

  @Override
  public void afterTextChanged(Editable s) {
  }
}
