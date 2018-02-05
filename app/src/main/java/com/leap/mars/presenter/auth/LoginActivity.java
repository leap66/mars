package com.leap.mars.presenter.auth;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;

import com.leap.mars.R;
import com.leap.mars.cmp.SessionMgr;
import com.leap.mars.config.AppConfig;
import com.leap.mars.config.PrecisionConfig;
import com.leap.mars.databinding.ActivityAuthLoginBinding;
import com.leap.mars.model.User;
import com.leap.mars.network.auth.usecase.LoginCase;
import com.leap.mars.presenter.base.BaseActivity;
import com.leap.mars.presenter.main.MainActivity;
import com.leap.mars.presenter.user.ResetPwdActivity;
import com.leap.mini.mgr.StorageMgr;
import com.leap.mini.model.network.Response;
import com.leap.mini.net.HttpSubscriber;
import com.leap.mini.util.DialogUtil;
import com.leap.mini.util.IsEmpty;
import com.leap.mini.util.ToastUtil;
import com.leap.mini.widget.validator.FocusValidator;
import com.leap.mini.widget.validator.ValidateResultCall;
import com.leap.mini.widget.validator.Validator;
import com.leap.mini.widget.validator.rules.MinLengthRule;
import com.leap.mini.widget.validator.rules.NotEmptyRule;

/**
 * 登录界面
 * <p>
 * Created by weiyaling on 2016/12/1.
 */
public class LoginActivity extends BaseActivity {
  private ActivityAuthLoginBinding binding;
  private String mobile;
  private boolean isVisible;
  private Validator validator;

  /**
   * 初始化控件
   */
  protected void initComponent() {
    binding = DataBindingUtil.setContentView(this, R.layout.activity_auth_login);
    binding.setPresenter(new Presenter());
  }

  @Override
  protected boolean isDarkFont() {
    return true;
  }

  /**
   * 加载数据
   */
  protected void loadData(Bundle savedInstanceState) {
    mobile = StorageMgr.get(AppConfig.MOBILE, StorageMgr.LEVEL_GLOBAL);
    if (!IsEmpty.string(mobile)) {
      binding.mobileEt.setText(mobile);
      binding.mobileEt.setSelection(mobile.length());
    }
  }

  @Override
  protected void createEventHandlers() {
    Validator emptyValidator = new Validator();
    emptyValidator.register(binding.mobileEt, new NotEmptyRule(""));
    emptyValidator.register(binding.passwordEt, new NotEmptyRule(""));
    emptyValidator.bindEnable(binding.loginBtn);
    validator = new Validator();
    validator.register(binding.mobileEt,
        new MinLengthRule(PrecisionConfig.User.mobile, getString(R.string.login_phone_error)));
    validator.register(binding.passwordEt,
        new MinLengthRule(PrecisionConfig.User.minPwd, getString(R.string.login_pwd_error)));
    FocusValidator focusValidator = new FocusValidator();
    focusValidator.verify(binding.mobileEt, binding.mobileIv, binding.mobileLine);
    focusValidator.verify(binding.passwordEt, binding.passwordIv, binding.passwordLine);
    binding.passwordEt.setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
          new Presenter().onLogin();
          return true;
        }
        return false;
      }
    });
  }

  private void login() {
    mobile = binding.mobileEt.getText().toString().trim();
    StorageMgr.set(AppConfig.MOBILE, mobile, StorageMgr.LEVEL_GLOBAL);
    String password = binding.passwordEt.getText().toString();
    new LoginCase(mobile, password).execute(new HttpSubscriber<User>(context) {
      @Override
      public void onFailure(String errorMsg, Response data) {
        DialogUtil.getErrorDialog(context, errorMsg).show();
      }

      @Override
      public void onSuccess(Response<User> response) {
        SessionMgr.updateUser(response.getData());
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        finish();
      }
    });
  }

  public class Presenter {

    // 明文、密文切换
    public void onVisible() {
      isVisible = !isVisible;
      binding.visibleIv.setSelected(isVisible);
      binding.passwordEt.setInputType(isVisible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
          : (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));
      binding.passwordEt.setSelection(binding.passwordEt.getText().length());
    }

    // 登录
    public void onLogin() {
      validator.validateAll(new ValidateResultCall() {
        @Override
        public void onSuccess() {
          login();
        }

        @Override
        public void onFailure(String message) {
          ToastUtil.showFailure(context, message);
        }
      });
    }

    // 忘记密码
    public void onPassword() {
      startActivity(new Intent(context, ResetPwdActivity.class));
    }

    // 注册
    public void onRegister() {
      Intent intent = new Intent(context, RegisterCheckActivity.class);
      startActivity(intent);
    }
  }
}
