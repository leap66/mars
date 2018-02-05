package com.leap.mars.presenter.user;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.leap.mars.R;
import com.leap.mars.cmp.SessionMgr;
import com.leap.mars.config.PrecisionConfig;
import com.leap.mars.databinding.ActivityResetMobileBinding;
import com.leap.mars.network.auth.usecase.MobileResetCase;
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
 * 修改手机号Dialog
 * <p>
 * Created by lichaofan on 2016/12/26.
 */

public class ResetMobileActivity extends BaseActivity {
  private ActivityResetMobileBinding binding;
  private Validator smsValidator;
  private Validator validator;

  @Override
  protected void initComponent() {
    binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_mobile);
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
    binding.setItem(SessionMgr.getUser().getMobile());
  }

  @Override
  protected void createEventHandlers() {
    Validator mEmptyValidator = new Validator();
    mEmptyValidator.register(binding.mobileEt, new NotEmptyRule(""));
    mEmptyValidator.register(binding.codeEt, new NotEmptyRule(""));
    mEmptyValidator.bindEnable(binding.submitBtn);
    Validator codeCtEmptyValidator = new Validator();
    codeCtEmptyValidator.register(binding.mobileEt, new NotEmptyRule(""));
    codeCtEmptyValidator.bindEnable(binding.sendCodeCt);
    smsValidator = new Validator();
    smsValidator.register(binding.mobileEt,
        new MinLengthRule(PrecisionConfig.User.mobile, getString(R.string.login_phone_error)));
    validator = new Validator();
    validator.register(binding.mobileEt,
        new MinLengthRule(PrecisionConfig.User.mobile, getString(R.string.login_phone_error)));
    validator.register(binding.codeEt,
        new MinLengthRule(PrecisionConfig.User.authCode, getString(R.string.login_code_error)));
    FocusValidator focusValidator = new FocusValidator();
    focusValidator.verify(binding.mobileEt, binding.mobileIv, binding.mobileLine);
    focusValidator.verify(binding.codeEt, binding.codeIv, binding.codeLine);
    binding.codeEt.setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
          new Presenter().onConfirm();
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
  }

  public class Presenter {

    /**
     * 发送新手机验证短信
     */
    public void onSend() {
      smsValidator.validateAll(new ValidateResultCall() {
        @Override
        public void onSuccess() {
          String mobile = binding.mobileEt.getText().toString().trim();
          new SmsSendCase(mobile, false).execute(new HttpSubscriber<Boolean>(context) {
            @Override
            public void onFailure(String errorMsg, Response data) {
              DialogUtil.getErrorDialog(context, errorMsg).show();
            }

            @Override
            public void onSuccess(Response<Boolean> data) {
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

    /**
     * 确认信息并上传，信息错误提示
     */
    public void onConfirm() {
      validator.validateAll(new ValidateResultCall() {
        @Override
        public void onSuccess() {
          String mobile = binding.mobileEt.getText().toString().trim();
          String code = binding.codeEt.getText().toString().trim();
          new MobileResetCase(mobile, code).execute(new HttpSubscriber<String>(context) {
            @Override
            public void onFailure(String errorMsg, Response response) {

            }

            @Override
            public void onSuccess(Response<String> response) {

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
