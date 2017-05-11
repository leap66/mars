package com.leap.mini.widget.validator.rules;

/**
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class MaxLengthRule implements Rule {
  private String message;
  private int length;

  public MaxLengthRule(int length, String message) {
    this.length = length;
    this.message = message;
  }

  @Override
  public boolean validate(String value) {
    if (value == null || value.length() == 0) {
      return false;
    }
    return value.length() <= length;
  }

  @Override
  public String getErrorMessage() {
    return message;
  }
}
