package com.leap.mini.widget.cleartextfield.validator;

import java.text.MessageFormat;
import java.util.regex.Pattern;

import android.widget.EditText;

/**
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class PatternValidator implements FieldValidator<EditText> {
  private String message = "不匹配正则表达式{0}";
  private Pattern pattern;

  public PatternValidator(String regex, String message) {
    this.pattern = Pattern.compile(regex);
    if (message != null) {
      this.message = message;
    } else {
      this.message = format(message, regex);
    }
  }

  @Override
  public boolean isValid(EditText editText) {
    return pattern.matcher(editText.getText()).matches();
  }

  @Override
  public String getError() {
    return message;
  }

  public static String format(String pattern, Object... arguments) {
    MessageFormat format = new MessageFormat(pattern);
    for (int i = 0; i < arguments.length; i++) {
      if (arguments[i] == null) {
        format.setFormat(i, null);
      }
    }
    return format.format(arguments);
  }

}
