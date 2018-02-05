package com.leap.mars.presenter.user;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.leap.mars.R;
import com.leap.mars.cmp.SessionMgr;
import com.leap.mars.databinding.ActivityUserPhotoBinding;
import com.leap.mars.presenter.base.BaseActivity;
import com.leap.mini.widget.NavigationBar;

/**
 * 个人设置界面
 * <p>
 * </> Created by weiyaling on 2017/8/3.
 */
public class UserPhotoActivity extends BaseActivity {
  private ActivityUserPhotoBinding binding;

  @Override
  protected void initComponent() {
    binding = DataBindingUtil.setContentView(this, R.layout.activity_user_photo);
    binding.setPresenter(new Presenter());
    binding.setItem(SessionMgr.getUser().getName());
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

  }
}
