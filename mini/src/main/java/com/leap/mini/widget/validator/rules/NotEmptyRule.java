package com.leap.mini.widget.validator.rules;

/**
 * 空验证
 * <p>
 * </> Created by ylwei on 2017/8/5.
 */
public class NotEmptyRule implements Rule {

  private String message;

  public NotEmptyRule(String message) {
    this.message = message;
  }

  @Override
  public boolean validate(String value) {
    return !(value == null || value.length() == 0);
  }

  @Override
  public String getErrorMessage() {
    return message;
  }
}
