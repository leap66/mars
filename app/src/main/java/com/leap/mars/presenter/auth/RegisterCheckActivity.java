package com.leap.mars.presenter.auth;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.leap.mars.R;
import com.leap.mars.config.PrecisionConfig;
import com.leap.mars.databinding.ActivityRegisterCheckBinding;
import com.leap.mars.network.auth.usecase.SmsCheckCase;
import com.leap.mars.network.auth.usecase.SmsSendCase;
import com.leap.mars.presenter.base.BaseActivity;
import com.leap.mini.model.network.Response;
import com.leap.mini.net.HttpSubscriber;
import com.leap.mini.util.DialogUtil;
import com.leap.mini.util.ToastUtil;
import com.leap.mini.widget.NavigationBar;
import com.leap.mini.widget.validator.FocusValidator;
import com.leap.mini.widget.validator.ValidateResultCall;
import com.leap.mini.widget.validator.Validator;
import com.leap.mini.widget.validator.rules.MinLengthRule;
import com.leap.mini.widget.validator.rules.NotEmptyRule;

/**
 * 注册校验界面
 * <p>
 * Created by weiyaling on 2016/12/1.
 */
public class RegisterCheckActivity extends BaseActivity {
  private ActivityRegisterCheckBinding binding;
  private Context context;
  private Validator mValidator;
  private Validator smsValidator;

  protected void initComponent() {
    binding = DataBindingUtil.setContentView(this, R.layout.activity_register_check);
    binding.setPresenter(new Presenter());
    context = this;
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {

  }

  @Override
  protected void createEventHandlers() {
    Validator mEmptyValidator = new Validator();
    mEmptyValidator.register(binding.mobileEt, new NotEmptyRule(""));
    mEmptyValidator.register(binding.codeEt, new NotEmptyRule(""));
    mEmptyValidator.bindEnable(binding.nextBtn);
    Validator codeCtEmptyValidator = new Validator();
    codeCtEmptyValidator.register(binding.mobileEt, new NotEmptyRule(""));
    codeCtEmptyValidator.bindEnable(binding.sendCodeCt);
    smsValidator = new Validator();
    smsValidator.register(binding.mobileEt,
        new MinLengthRule(PrecisionConfig.User.mobile, getString(R.string.login_phone_error)));
    mValidator = new Validator();
    mValidator.register(binding.mobileEt,
        new MinLengthRule(PrecisionConfig.User.mobile, getString(R.string.login_phone_error)));
    mValidator.register(binding.codeEt,
        new MinLengthRule(PrecisionConfig.User.authCode, getString(R.string.login_code_error)));
    FocusValidator focusValidator = new FocusValidator();
    focusValidator.verify(binding.mobileEt, binding.mobileIv, binding.mobileLine);
    focusValidator.verify(binding.codeEt, binding.codeIv, binding.codeLine);
    binding.codeEt.setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
          new Presenter().onNext();
          return true;
        }
        return false;
      }
    });
    binding.navigation.setListener(new NavigationBar.INavigationBarOnClickListener() {
      @Override
      public void onBack() {
        onBackPressed();
      }

      @Override
      public void performAction(View view) {

      }
    });
    binding.mobileEt.requestFocus();
  }

  @Override
  protected View statusBarView() {
    return binding.immersionBar;
  }

  @Override
  protected boolean isDarkFont() {
    return true;
  }

  public class Presenter {

    public void onSend() {
      smsValidator.validateAll(new ValidateResultCall() {
        @Override
        public void onSuccess() {
          String mobile = binding.mobileEt.getText().toString().trim();
          new SmsSendCase(mobile, false).execute(new HttpSubscriber<Boolean>(context) {
            @Override
            public void onFailure(String errorMsg, Response response) {
              DialogUtil.getErrorDialog(context, errorMsg).show();
            }

            @Override
            public void onSuccess(Response<Boolean> response) {
              ToastUtil.showSuccess(context, getString(R.string.send_code_success));
              binding.codeEt.requestFocus();
              binding.sendCodeCt.startCount();
            }
          });
        }

        @Override
        public void onFailure(String message) {
          ToastUtil.showFailure(context, message);
        }
      });
    }

    public void onNext() {
      mValidator.validateAll(new ValidateResultCall() {
        @Override
        public void onSuccess() {
          String mobile = binding.mobileEt.getText().toString().trim();
          String code = binding.codeEt.getText().toString().trim();
          new SmsCheckCase(mobile, code).execute(new HttpSubscriber<String>(context) {
            @Override
            public void onFailure(String errorMsg, Response response) {
              DialogUtil.getErrorDialog(context, errorMsg).show();
            }

            @Override
            public void onSuccess(Response<String> response) {
              Intent intent = new Intent(context, RegisterActivity.class);
              startActivity(intent);
            }
          });
        }

        @Override
        public void onFailure(String message) {
          ToastUtil.showFailure(context, message);
        }
      });
    }
  }

}
