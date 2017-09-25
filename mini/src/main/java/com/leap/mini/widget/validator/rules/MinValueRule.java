package com.leap.mini.widget.validator.rules;

import java.math.BigDecimal;

/**
 * 最小值验证
 * <p>
 * </> Created by ylwei on 2017/8/5.
 */
public class MinValueRule implements Rule {
  private float minValue;
  private String message;

  public MinValueRule(float minValue, String message) {
    this.minValue = minValue;
    this.message = message;
  }

  @Override
  public boolean validate(String value) {
    try {
      return new BigDecimal(value).floatValue() >= minValue;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public String getErrorMessage() {
    return message;
  }
}
