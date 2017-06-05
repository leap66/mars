//package com.leap.mars.presenter.main;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Stack;
//
//import com.leap.mars.R;
//import com.leap.mars.databinding.ActivityMainBinding;
//import com.leap.mars.presenter.base.BaseActivity;
//import com.leap.mini.util.adapter.ViewPagerAdapter;
//import com.leap.mini.util.listener.OnPageChangeListener;
//
//import android.content.Context;
//import android.databinding.DataBindingUtil;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.View;
//
//public class MainActivity extends BaseActivity {
//  private ActivityMainBinding binding;
//  private Context context;
//  private List<Fragment> fragmentList;
//  private Stack<View> viewList;
//  public static int currentIndex;
//
//  @Override
//  protected void initComponent() {
//    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//    context = this;
//    binding.setPresenter(new Presenter());
//    fragmentList = new ArrayList<>();
//    viewList = new Stack<>();
//  }
//
//  @Override
//  protected void loadData(Bundle savedInstanceState) {
//
//  }
//
//  @Override
//  protected void createEventHandlers() {
//    fragmentList.add(0, new TypeFragment());
//    fragmentList.add(1, new TypeFragment());
//    fragmentList.add(2, new TypeFragment());
//    viewList.add(binding.typeLl);
//    viewList.add(binding.voiceLl);
//    viewList.add(binding.controlLl);
//    ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//    pagerAdapter.setFragmentList(fragmentList);
//    binding.viewPage.setAdapter(pagerAdapter);
//    setCurrentIndex(0);
//    binding.viewPage.addOnPageChangeListener(new OnPageChangeListener() {
//      @Override
//      public void onPageSelected(int position) {
//        setCurrentIndex(position);
//      }
//    });
//  }
//
//  public class Presenter {
//
//    public void onType(int position) {
//      setCurrentIndex(position);
//    }
//  }
//
//  private void setCurrentIndex(int position) {
//    currentIndex = position;
//    binding.viewPage.setCurrentItem(currentIndex);
//    for (View view : viewList)
//      view.setSelected(false);
//    viewList.get(currentIndex).setSelected(true);
//  }
//}
