package com.leap.mini.util.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * ViewPagerAdapter
 * <p/>
 * Created by weiyaling on 2017/5/11.
 */
public class ViewPagerViewAdapter extends PagerAdapter {

  private List<View> viewList;

  public void setViewList(List<View> viewList) {
    this.viewList = viewList;
  }

  @Override
  public int getCount() {
    return viewList.size();
  }

  @Override
  public boolean isViewFromObject(View arg0, Object arg1) {
    return arg0 == arg1;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView(viewList.get(position));
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    container.addView(viewList.get(position));
    return viewList.get(position);
  }
}
