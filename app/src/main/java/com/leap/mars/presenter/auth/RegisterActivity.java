package com.leap.mars.presenter.auth;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;

import com.leap.mars.R;
import com.leap.mars.config.AppConfig;
import com.leap.mars.config.PrecisionConfig;
import com.leap.mars.databinding.ActivityRegisterBinding;
import com.leap.mars.model.Auth;
import com.leap.mars.network.auth.usecase.RegisterCase;
import com.leap.mars.presenter.base.BaseActivity;
import com.leap.mars.util.ShortcutMgr;
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
 * 注册界面
 * <p>
 * Created by weiyaling on 2016/12/1.
 */

public class RegisterActivity extends BaseActivity {
  private ActivityRegisterBinding binding;
  private Validator mValidator;
  private boolean isVisible;
  private Auth auth;

  @Override
  protected void initComponent() {
    binding = DataBindingUtil.setContentView(RegisterActivity.this, R.layout.activity_register);
    binding.setPresenter(new Presenter());
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {
    auth = (Auth) getIntent().getSerializableExtra(AppConfig.DATA);
  }

  @Override
  protected void createEventHandlers() {
    Validator mEmptyValidator = new Validator();
    mEmptyValidator.register(binding.nameEt, new NotEmptyRule(""));
    mEmptyValidator.register(binding.passwordEt, new NotEmptyRule(""));
    mEmptyValidator.bindEnable(binding.registerBtn);
    mValidator = new Validator();
    mValidator.register(binding.passwordEt,
        new MinLengthRule(PrecisionConfig.User.minPwd, getString(R.string.login_pwd_error)));
    FocusValidator focusValidator = new FocusValidator();
    focusValidator.verify(binding.nameEt, binding.nameIv, binding.nameLine);
    focusValidator.verify(binding.passwordEt, binding.passwordIv, binding.passwordLine);
    binding.passwordEt.setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
          registerSubmit();
          return true;
        }
        return false;
      }
    });
    binding.nameEt.requestFocus();
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

  @Override
  protected View statusBarView() {
    return binding.immersionBar;
  }

  @Override
  protected boolean isDarkFont() {
    return true;
  }

  public class Presenter {

    public void onBack() {
      onBackPressed();
    }

    public void onReadAgreement() {
      Intent intent = new Intent(RegisterActivity.this, AgreementActivity.class);
      startActivity(intent);
    }

    // 明文、密文切换
    public void onPasswordVisible() {
      isVisible = !isVisible;
      binding.passwordVisibleIv.setSelected(isVisible);
      binding.passwordEt.setInputType(isVisible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
          : (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));
      binding.passwordEt.setSelection(binding.passwordEt.getText().length());
    }

    public void onRegister() {
      registerSubmit();
    }
  }

  public void registerSubmit() {
    mValidator.validateAll(new ValidateResultCall() {
      @Override
      public void onSuccess() {
        new RegisterCase(auth).execute(new HttpSubscriber<String>(context) {
          @Override
          public void onFailure(String errorMsg, Response response) {
            DialogUtil.getErrorDialog(RegisterActivity.this, errorMsg).show();
          }

          @Override
          public void onSuccess(Response<String> response) {
            ToastUtil.showSuccess(context, R.string.register_success);
            ShortcutMgr.logout();
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
