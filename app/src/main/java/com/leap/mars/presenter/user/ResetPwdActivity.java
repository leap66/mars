package com.leap.mars.presenter.user;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.leap.mars.R;
import com.leap.mars.config.AppConfig;
import com.leap.mars.config.PrecisionConfig;
import com.leap.mars.databinding.ActivityResetPwdBinding;
import com.leap.mars.network.auth.usecase.PwdResetCase;
import com.leap.mars.network.auth.usecase.SmsSendCase;
import com.leap.mars.presenter.base.BaseActivity;
import com.leap.mars.util.ShortcutMgr;
import com.leap.mini.mgr.StorageMgr;
import com.leap.mini.mgr.TokenMgr;
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
 * 重置密码界面
 * <p>
 * Created by weiyaling on 2016/12/1.
 */
public class ResetPwdActivity extends BaseActivity {
  private ActivityResetPwdBinding binding;
  private Validator mValidator;
  private Validator smsValidator;
  private boolean isVisible;

  @Override
  protected void initComponent() {
    binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_pwd);
    binding.setPresenter(new Presenter());
  }

  @Override
  protected View statusBarView() {
    return binding.immersionBar;
  }

  @Override
  protected boolean isDarkFont() {
    return true;
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {
  }

  @Override
  protected void createEventHandlers() {
    Validator mEmptyValidator = new Validator();
    mEmptyValidator.register(binding.mobileEt, new NotEmptyRule(""));
    mEmptyValidator.register(binding.codeEt, new NotEmptyRule(""));
    mEmptyValidator.register(binding.passwordEt, new NotEmptyRule(""));
    mEmptyValidator.bindEnable(binding.submitBtn);
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
    mValidator.register(binding.passwordEt,
        new MinLengthRule(PrecisionConfig.User.minPwd, getString(R.string.login_pwd_error)));
    FocusValidator focusValidator = new FocusValidator();
    focusValidator.verify(binding.mobileEt, binding.mobileIv, binding.mobileLine);
    focusValidator.verify(binding.codeEt, binding.codeIv, binding.codeLine);
    focusValidator.verify(binding.passwordEt, binding.passwordIv, binding.passwordLine);
    binding.passwordEt.setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
          resetPwdSubmit();
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
    String mobile = StorageMgr.get(AppConfig.MOBILE, StorageMgr.LEVEL_GLOBAL);
    if (!TextUtils.isEmpty(mobile)) {
      binding.mobileEt.setText(mobile);
      binding.mobileEt.setSelection(mobile.length());
    }
    binding.mobileEt.requestFocus();
    binding.mobileEt.setFocusable(!TokenMgr.hasUser());
    if (TokenMgr.hasUser())
      binding.codeEt.requestFocus();
  }

  public class Presenter {
    public void onBack() {
      onBackPressed();
    }

    public void onSend() {
      smsValidator.validateAll(new ValidateResultCall() {
        @Override
        public void onSuccess() {
          String mobile = binding.mobileEt.getText().toString().trim();
          new SmsSendCase(mobile, true).execute(new HttpSubscriber<Boolean>(context) {
            @Override
            public void onFailure(String errorMsg, Response response) {
              DialogUtil.getErrorDialog(ResetPwdActivity.this, errorMsg).show();
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

    // 明文、密文切换
    public void onPasswordVisible() {
      isVisible = !isVisible;
      binding.passwordVisibleIv.setSelected(isVisible);
      binding.passwordEt.setInputType(isVisible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
          : (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));
      binding.passwordEt.setSelection(binding.passwordEt.getText().length());
    }

    public void onSubmit() {
      resetPwdSubmit();
    }
  }

  public void resetPwdSubmit() {
    mValidator.validateAll(new ValidateResultCall() {
      @Override
      public void onSuccess() {
        String mobile = binding.mobileEt.getText().toString().trim();
        String code = binding.codeEt.getText().toString().trim();
        String password = binding.passwordEt.getText().toString().trim();
        new PwdResetCase(mobile, password, code).execute(new HttpSubscriber<Boolean>(context) {
          @Override
          public void onFailure(String errorMsg, Response data) {
            DialogUtil.getErrorDialog(context, errorMsg).show();
          }

          @Override
          public void onSuccess(Response<Boolean> response) {
            StorageMgr.set(AppConfig.MOBILE, response.getData(), StorageMgr.LEVEL_GLOBAL);
            ToastUtil.showSuccess(context, getString(R.string.pwd_reset_success));
            ShortcutMgr.logout();
          }
        });
      }

      @Override
      public void onFailure(String message) {
        ToastUtil.showFailure(ResetPwdActivity.this, message);
      }
    });
  }

}
