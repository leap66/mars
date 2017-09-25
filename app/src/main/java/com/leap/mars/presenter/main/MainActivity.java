package com.leap.mars.presenter.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;

import com.leap.mars.R;
import com.leap.mars.databinding.ActivityMainBinding;
import com.leap.mars.presenter.base.BaseActivity;
import com.leap.mini.util.ExitHelper;
import com.leap.mini.util.ToastUtil;
import com.leap.mini.util.adapter.ViewPagerAdapter;
import com.leap.mini.util.listener.OnPageChangeListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends BaseActivity {
  private ActivityMainBinding binding;
  private List<Fragment> fragmentList;
  private Stack<View> viewList;
  public int currentIndex;
  private ExitHelper.TwicePressHolder mExitHelper;
  private Context context;

  @Override
  protected void initComponent() {
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    binding.setPresenter(new Presenter());
    context = this;
    fragmentList = new ArrayList<>();
    viewList = new Stack<>();
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {
    fragmentList.add(new HomeFragment());
    fragmentList.add(new HomeFragment());
    fragmentList.add(new HomeFragment());
    fragmentList.add(new UserFragment());
    viewList.add(binding.rbType);
    viewList.add(binding.rbVoice);
    viewList.add(binding.rbControl);
    viewList.add(binding.rbMine);
  }

  @Override
  protected View statusBarView() {
    return binding.immersionBar;
  }

  @Override
  protected void createEventHandlers() {
    ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    pagerAdapter.setFragmentList(fragmentList);
    binding.viewPager.setAdapter(pagerAdapter);
    setCurrentIndex(0);
    binding.viewPager.addOnPageChangeListener(new OnPageChangeListener() {
      @Override
      public void onPageSelected(int position) {
        setCurrentIndex(position);
      }
    });
    mExitHelper = new ExitHelper.TwicePressHolder(new ExitHelper.IExitInterface() {
      @Override
      public void showExitTip() {
        ToastUtil.showHint(context, R.string.main_again_exit);
      }

      @Override
      public void exit() {
        onBackPressed();
      }
    }, 3000);
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    return mExitHelper.onKeyDown(keyCode, event);
  }

  public class Presenter {

    public void onType(int position) {
      setCurrentIndex(position);
    }
  }

  private void setCurrentIndex(int position) {
    currentIndex = position;
    binding.viewPager.setCurrentItem(currentIndex);
    for (View view : viewList)
      view.setSelected(false);
    viewList.get(currentIndex).setSelected(true);
  }
}
