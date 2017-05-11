package com.leap.mini.widget.validator.rules;

/**
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class PhoneNumberRule extends RegexRule {

  public PhoneNumberRule(String message) {
    super("^\\d{11}$", message);
  }
}
