package com.leap.mini.widget.validator;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.leap.mini.widget.validator.rules.PhoneNumberRule;
import com.leap.mini.widget.validator.rules.RequiredRule;
import com.leap.mini.widget.validator.rules.Rule;

import android.widget.TextView;

/**
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class Validator {
  public Map<TextView, List<Rule>> validations = new LinkedHashMap<TextView, List<Rule>>();

  public void register(TextView textView, Rule... rules) {
    validations.put(textView, Arrays.asList(rules));
  }

  public void unregister(TextView textView) {
    validations.remove(textView);
  }

  public void validateAll(ValidateResultCall resultCall) {
    for (TextView key : validations.keySet()) {
      for (Rule rule : validations.get(key)) {
        if (!rule.validate(String.valueOf(key.getText()))) {
          resultCall.onFailure(rule.getErrorMessage());
        }
      }
    }
    resultCall.onSuccess();
  }

  public boolean validate(TextView textView) {
    List<Rule> rules = validations.get(textView);
    if (rules == null) {
      return true;
    } else {
      for (Rule rule : rules) {
        boolean validate = rule.validate(String.valueOf(textView.getText()));
        if (!validate) {
          return false;
        }
      }
    }
    return true;
  }

  public static void main(String[] args) {
    TextView text = new TextView(null);

    Validator validator = new Validator();
    validator.register(text, new RequiredRule("手机号不能为空"), new PhoneNumberRule("手机号必须为11位有效数字"));

    if (validator.validate(text)) {
      // 单个控件检查成功
    }

    validator.validateAll(new ValidateResultCall() {
      @Override
      public void onSuccess() {
        // 提交内容
      }

      @Override
      public void onFailure(String message) {
        // toast提示
      }
    });
  }
}
