package com.leap.mini.widget.validator.rules;

import java.util.regex.Pattern;

/**
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class RegexRule implements Rule {
  private String regex;
  private String message;

  public RegexRule(String regex, String message) {
    this.regex = regex;
    this.message = message;
  }

  @Override
  public boolean validate(String value) {
    return Pattern.compile(regex).matcher(value).matches();
  }

  @Override
  public String getErrorMessage() {
    return message;
  }
}
