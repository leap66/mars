package com.leap.mini.widget.validator;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.leap.mini.widget.validator.rules.PhoneNumberRule;
import com.leap.mini.widget.validator.rules.RequiredRule;
import com.leap.mini.widget.validator.rules.Rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class Validator {
  private Map<TextView, List<Rule>> validations = new LinkedHashMap<>();
  private List<View> viewList = new ArrayList<>();

  public void register(final TextView textView, Rule... rules) {
    textView.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override
      public void afterTextChanged(Editable s) {
        for (View view : viewList) {
          view.setEnabled(validateAll());
        }
      }
    });

    validations.put(textView, Arrays.asList(rules));
  }

  public void bindEnable(View... views) {
    this.viewList.addAll(Arrays.asList(views));
  }

  public void unregister(TextView textView) {
    validations.remove(textView);
  }

  public void validateAll(ValidateResultCall resultCall) {
    for (TextView key : validations.keySet()) {
      for (Rule rule : validations.get(key)) {
        if (!rule.validate(String.valueOf(key.getText()))) {
          resultCall.onFailure(rule.getErrorMessage());
          return;
        }
      }
    }
    resultCall.onSuccess();
  }

  private boolean validateAll() {
    for (TextView key : validations.keySet()) {
      for (Rule rule : validations.get(key)) {
        if (!rule.validate(String.valueOf(key.getText()))) {
          return false;
        }
      }
    }
    return true;
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
      // TODO
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
