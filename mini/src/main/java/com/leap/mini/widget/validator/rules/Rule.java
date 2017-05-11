package com.leap.mini.widget.validator.rules;

/**
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public interface Rule {

  boolean validate(String value);

  String getErrorMessage();
}
