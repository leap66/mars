package com.leap.mars.presenter.main;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leap.mars.R;
import com.leap.mars.databinding.FragmentHomeBinding;
import com.leap.mars.presenter.auth.SplashActivity;
import com.leap.mars.presenter.auth.activity.TextChatActivity;
import com.leap.mars.presenter.base.BaseFragment;
import com.leap.mars.presenter.chat.ChatTypeActivity;
import com.leap.mars.presenter.scan.QRScannerActivity;

/**
 * @author : ylwei
 * @time : 2017/9/21
 * @description :
 */
public class HomeFragment extends BaseFragment {
  private FragmentHomeBinding binding;

  @Override
  protected View initComponent(LayoutInflater inflater, ViewGroup container) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
    binding.setPresenter(new Presenter());
    return binding.getRoot();
  }

  @Override
  protected boolean isDarkFont() {
    return true;
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {
    binding.getRoot();
  }

  public class Presenter {

    public void onType(int position) {
      Intent intent = null;
      switch (position) {
      case 1:
        intent = new Intent(context, ChatTypeActivity.class);
        break;
      case 2:
        intent = new Intent(context, ChatTypeActivity.class);
        break;
      case 3:
        intent = new Intent(context, QRScannerActivity.class);
        break;
      case 4:
        intent = new Intent(context, TextChatActivity.class);
        break;
      case 5:
        intent = new Intent(context, TextChatActivity.class);
        break;
      case 6:
        intent = new Intent(context, TextChatActivity.class);
        break;
      case 7:
        intent = new Intent(context, SplashActivity.class);
        break;
      }
      startActivity(intent);
    }
  }
}