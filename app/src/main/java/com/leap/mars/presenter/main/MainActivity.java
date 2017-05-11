package com.leap.mars.presenter.main;

import java.util.ArrayList;
import java.util.List;

import com.leap.mars.R;
import com.leap.mars.databinding.ActivityMainBinding;
import com.leap.mars.presenter.base.BaseActivity;
import com.leap.mini.util.adapter.ViewPagerAdapter;
import com.leap.mini.util.listener.OnPageChangeListener;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class MainActivity extends BaseActivity {
  private ActivityMainBinding binding;
  private Context context;
  private List<Fragment> fragmentList;
  public static int currentIndex;

  @Override
  protected void initComponent() {
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    context = this;
    binding.setPresenter(new Presenter());
    fragmentList = new ArrayList<>();
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {

  }

  @Override
  protected void createEventHandlers() {
    fragmentList.add(new TypeFragment());
    fragmentList.add(new TypeFragment());
    fragmentList.add(new TypeFragment());
    ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    pagerAdapter.setFragmentList(fragmentList);
    binding.viewPage.setAdapter(pagerAdapter);
    binding.viewPage.addOnPageChangeListener(new OnPageChangeListener() {
      @Override
      public void onPageSelected(int position) {
        currentIndex = position;
        binding.viewPage.setCurrentItem(currentIndex);
      }
    });
  }

  public class Presenter {

    public void onType() {
      currentIndex = 0;
      binding.viewPage.setCurrentItem(currentIndex);
    }

    public void onVoice() {
      currentIndex = 1;
      binding.viewPage.setCurrentItem(currentIndex);
    }

    public void onControl() {
      currentIndex = 2;
      binding.viewPage.setCurrentItem(currentIndex);
    }
  }
}
