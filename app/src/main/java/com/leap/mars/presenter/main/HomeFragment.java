package com.leap.mars.presenter.main;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leap.mars.R;
import com.leap.mars.cmp.SessionMgr;
import com.leap.mars.databinding.FragmentHomeBinding;
import com.leap.mars.model.User;
import com.leap.mars.network.auth.usecase.LoginCase;
import com.leap.mars.presenter.auth.activity.TextChatActivity;
import com.leap.mars.presenter.base.BaseFragment;
import com.leap.mars.presenter.chat.ChatTypeActivity;
import com.leap.mini.model.network.Response;
import com.leap.mini.net.HttpSubscriber;
import com.leap.mini.util.DialogUtil;
import com.leap.mini.util.ToastUtil;

import java.util.Date;

/**
 * @author : ylwei
 * @time : 2017/9/21
 * @description :
 */
public class HomeFragment extends BaseFragment {
  private Context context;
  private FragmentHomeBinding binding;

  @Override
  protected View initComponent(LayoutInflater inflater, ViewGroup container) {
    context = getContext();
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
        new LoginCase("13900001093", "123456").execute(new HttpSubscriber<String>(context) {
          @Override
          public void onFailure(String errorMsg, Response response) {
            DialogUtil.showError(context, errorMsg).show();
          }

          @Override
          public void onSuccess(Response<String> response) {
            User user = new User();
            user.setId(response.getData());
            user.setBirth(new Date());
            user.setCreated(new Date());
            user.setLastModified(new Date());
            SessionMgr.updateUser(user);
            ToastUtil.showSuccess(context, response.getData());
          }
        });
        return;
      case 3:
        intent = new Intent(context, TextChatActivity.class);
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
        intent = new Intent(context, TextChatActivity.class);
        break;
      }
      startActivity(intent);
    }
  }
}