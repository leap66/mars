package com.leap.mini.widget.cleartextfield.validator;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class EmptyValidator implements FieldValidator<EditText> {
  private String message = "不允许为空";

  public EmptyValidator(String message) {
    if (!TextUtils.isEmpty(message)) {
      this.message = message;
    }
  }

  @Override
  public boolean isValid(EditText editText) {
    return editText.getText().length() > 0;
  }

  @Override
  public String getError() {
    return message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
