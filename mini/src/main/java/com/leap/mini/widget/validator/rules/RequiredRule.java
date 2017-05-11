package com.leap.mini.widget.validator.rules;

/**
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class RequiredRule implements Rule {
  private String message;

  public RequiredRule(String message) {
    this.message = message;
  }

  @Override
  public boolean validate(String value) {
    return value != null && !value.isEmpty();
  }

  @Override
  public String getErrorMessage() {
    return message;
  }
}
