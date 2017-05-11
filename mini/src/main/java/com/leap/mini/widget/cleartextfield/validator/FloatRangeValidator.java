package com.leap.mini.widget.cleartextfield.validator;

import android.widget.EditText;

/**
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class FloatRangeValidator implements FieldValidator<EditText> {
  private float max;
  private float min;
  private String message;

  public FloatRangeValidator(float max, float min) {
    this.max = max;
    this.min = min;
  }

  @Override
  public boolean isValid(EditText editText) {
    if (editText.getText().length() <= 0) {
      return true;
    }
    try {
      double value = Double.parseDouble(editText.getText().toString());
      if (value > max) {
        message = PatternValidator.format("最大值为{0}", max);
        return false;
      }
      if (value < min) {
        message = PatternValidator.format("最小值为{0}", min);
        return false;
      }
      return true;
    } catch (NumberFormatException e) {
      message = e.getMessage();
      return false;
    }
  }

  @Override
  public String getError() {
    return message;
  }
}
