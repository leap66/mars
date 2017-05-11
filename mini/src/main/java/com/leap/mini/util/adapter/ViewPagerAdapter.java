package com.leap.mini.util.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * ViewPagerAdapter
 * <p/>
 * Created by weiyaling on 2017/5/11.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

  private List<Fragment> fragmentList;

  public ViewPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
    super(fm);
    this.fragmentList = fragmentList;
  }

  @Override
  public Fragment getItem(int position) {
    return fragmentList.get(position);
  }

  @Override
  public int getCount() {
    return fragmentList.size();
  }

  public void setFragmentList(List<Fragment> fragmentList) {
    this.fragmentList = fragmentList;
  }
}
