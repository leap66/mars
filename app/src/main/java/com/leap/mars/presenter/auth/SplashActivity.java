package com.leap.mars.presenter.auth;

import com.leap.mars.R;
import com.leap.mars.databinding.ActivitySplashBinding;
import com.leap.mars.presenter.base.BaseActivity;
import com.leap.mars.presenter.main.MainActivity;
import com.leap.mini.mgr.TokenMgr;
import com.leap.mini.util.ToastUtil;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends BaseActivity {
  private ActivitySplashBinding binding;
  private Context context;

  @Override
  protected void initComponent() {
    binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
    context = this;
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {

  }

  @Override
  protected void createEventHandlers() {
    setAnimation();
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        if (TokenMgr.hasUser()) {
          startActivity(new Intent(context, MainActivity.class));
        } else {
          startActivity(new Intent(context, LoginActivity.class));
        }
        overridePendingTransition(R.anim.in_from_anin, R.anim.out_to_anin);
        finish();
      }
    }, 1000);
  }

  /**
   * 开启动画
   */
  private void setAnimation() {
    ObjectAnimator anim = ObjectAnimator.ofFloat(binding.ivSplash, "x", 120, 270);
    anim.setDuration(2000);
    anim.setRepeatCount(ObjectAnimator.INFINITE);
    anim.setRepeatMode(ObjectAnimator.INFINITE);
    anim.start();
    ToastUtil.showHint(context, getString(R.string.splash_loading));
  }
}
