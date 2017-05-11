package com.leap.mini.widget.cleartextfield.validator;

import java.util.ArrayList;
import java.util.List;

import android.widget.EditText;

/**
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class DefaultTextValidator {
  private EditText editText;
  private List<FieldValidator<EditText>> validators = new ArrayList<>();
  private FieldValidateError error;

  public DefaultTextValidator(EditText editText) {
    this.editText = editText;
  }

  public void addValidator(FieldValidator<EditText> validator) {
    validators.add(validator);
  }

  public boolean isValid() {
    for (FieldValidator<EditText> validator : validators) {
      if (!validator.isValid(editText)) {
        error = new FieldValidateError(editText, validator.getError(),
            validator.getClass().toString());
        return false;
      }
    }
    return true;
  }

  public FieldValidateError getError() {
    return error;
  }
}
