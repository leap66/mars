package com.leap.mars.presenter.main;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leap.mars.R;
import com.leap.mars.databinding.FragmentMineBinding;
import com.leap.mars.network.auth.usecase.LogoutCase;
import com.leap.mars.presenter.auth.NavigationActivity;
import com.leap.mars.presenter.base.BaseFragment;
import com.leap.mars.presenter.user.AppAboutActivity;
import com.leap.mars.presenter.user.AppSettingActivity;
import com.leap.mars.presenter.user.UserSettingActivity;
import com.leap.mars.util.ShortcutMgr;
import com.leap.mini.model.network.Response;
import com.leap.mini.net.HttpSubscriber;

/**
 * @author : ylwei
 * @time : 2017/9/21
 * @description :
 */
public class UserFragment extends BaseFragment {

  @Override
  protected View initComponent(LayoutInflater inflater, ViewGroup container) {
    FragmentMineBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mine,
        container, false);
    binding.setPresenter(new Presenter());
    return binding.getRoot();
  }

  @Override
  protected boolean isDarkFont() {
    return true;
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {

  }

  public class Presenter {

    public void onNavigation() {
      startActivity(new Intent(context, NavigationActivity.class));
    }

    public void onUser() {
      startActivity(new Intent(context, UserSettingActivity.class));
    }

    public void onRobot() {
      ShortcutMgr.logout();
    }

    public void onAppSetting() {
      startActivity(new Intent(context, AppSettingActivity.class));
    }

    public void onAbout() {
      startActivity(new Intent(context, AppAboutActivity.class));
    }

    public void onQuit() {
      new LogoutCase().execute(new HttpSubscriber<Boolean>(context) {
        @Override
        public void onFailure(String errorMsg, Response response) {
          ShortcutMgr.logout();
        }

        @Override
        public void onSuccess(Response<Boolean> response) {
          ShortcutMgr.logout();
        }
      });
      ShortcutMgr.logout();
    }
  }
}