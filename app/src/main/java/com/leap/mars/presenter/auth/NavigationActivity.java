package com.leap.mars.presenter.auth;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.leap.mars.R;
import com.leap.mars.databinding.ActivityNavigationBinding;
import com.leap.mars.presenter.base.BaseActivity;
import com.leap.mars.presenter.main.MainActivity;
import com.leap.mini.mgr.TokenMgr;
import com.leap.mini.util.adapter.ViewPagerViewAdapter;
import com.leap.mini.util.listener.OnPageChangeListener;

import java.util.ArrayList;
import java.util.List;

public class NavigationActivity extends BaseActivity {
  private ActivityNavigationBinding binding;

  @Override
  protected void initComponent() {
    binding = DataBindingUtil.setContentView(this, R.layout.activity_navigation);
    binding.setItem(false);
    binding.setPresenter(new Presenter());
    initData();
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {

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
  protected void createEventHandlers() {
    binding.viewPager.addOnPageChangeListener(new OnPageChangeListener() {
      @Override
      public void onPageSelected(int position) {
        binding.setItem(!TokenMgr.hasUser() && position == 2);
      }
    });
  }

  /**
   * 初始化 ViewPager
   */
  private void initData() {
    List<View> viewList = new ArrayList<>();
    ImageView iv = new ImageView(getBaseContext());
    iv.setImageResource(R.mipmap.navigation_01);
    viewList.add(iv);
    ImageView iv1 = new ImageView(getBaseContext());
    iv1.setImageResource(R.mipmap.navigation_02);
    viewList.add(iv1);
    ImageView iv2 = new ImageView(getBaseContext());
    iv2.setImageResource(R.mipmap.navigation_03);
    viewList.add(iv2);
    ViewPagerViewAdapter adapter = new ViewPagerViewAdapter();
    adapter.setViewList(viewList);
    binding.viewPager.setAdapter(adapter);
  }

  public class Presenter {

    public void onEntry() {
      startActivity(new Intent(getBaseContext(), MainActivity.class));
      overridePendingTransition(R.anim.in_from_anin, R.anim.out_to_anin);
    }
  }
}
