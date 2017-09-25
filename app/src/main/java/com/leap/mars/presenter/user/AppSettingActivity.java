package com.leap.mars.presenter.user;

import com.leap.mars.R;
import com.leap.mars.databinding.ActivityAppSettingBinding;
import com.leap.mars.presenter.base.BaseActivity;
import com.leap.mini.widget.NavigationBar;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

/**
 * 个人设置界面
 * <p>
 * </> Created by lichaofan on 2017/8/3.
 */

public class AppSettingActivity extends BaseActivity {
  private ActivityAppSettingBinding binding;
  private Context context;

  @Override
  protected void initComponent() {
    binding = DataBindingUtil.setContentView(this, R.layout.activity_app_setting);
    binding.setPresenter(new Presenter());
    context = this;
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {

  }

  @Override
  protected void createEventHandlers() {
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

  /**
   * 主界面Presenter
   */
  public class Presenter {

    /**
     * 修改头像
     */
    public void onSkipBlueTooth() {
    }
  }
}
