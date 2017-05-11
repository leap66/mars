package com.leap.mini.widget.validator;

/**
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public interface ValidateResultCall {

  void onSuccess();

  void onFailure(String message);
}
