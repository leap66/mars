package com.leap.mini.widget.cleartextfield.validator;

/**
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public interface FieldValidator<T> {

  boolean isValid(T editText);

  String getError();

}
