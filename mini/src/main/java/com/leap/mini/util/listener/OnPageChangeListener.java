package com.leap.mini.util.listener;

import android.support.v4.view.ViewPager;

/**
 * OnPageChangeListener
 * <p>
 * </> Created by weiyaling on 2017/5/11.
 */
public abstract class OnPageChangeListener implements ViewPager.OnPageChangeListener {

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
  }

  @Override
  public abstract void onPageSelected(int position);

  @Override
  public void onPageScrollStateChanged(int state) {
  }

}
