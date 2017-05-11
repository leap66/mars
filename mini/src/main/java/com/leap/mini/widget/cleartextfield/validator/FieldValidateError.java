package com.leap.mini.widget.cleartextfield.validator;

import android.widget.EditText;

/**
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class FieldValidateError {
  private EditText inputField;
  private String errorMessage;
  private String errorType;

  public FieldValidateError(EditText inputField, String errorMessage, String errorType) {
    this.inputField = inputField;
    this.errorMessage = errorMessage;
    this.errorType = errorType;
  }

  public EditText getInputField() {
    return inputField;
  }

  public void setInputField(EditText inputField) {
    this.inputField = inputField;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorType() {
    return errorType;
  }

  public void setErrorType(String errorType) {
    this.errorType = errorType;
  }
}
