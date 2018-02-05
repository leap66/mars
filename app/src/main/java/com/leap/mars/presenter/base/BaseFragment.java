package com.leap.mars.presenter.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;

/**
 * 基础Fragment
 * <p>
 * Created by weiyaling on 20175/9.
 */
public abstract class BaseFragment extends Fragment {
  protected View mRootView;
  protected boolean mIsVisible;
  protected boolean mIsPrepare;
  protected boolean mIsImmersion;
  protected ImmersionBar mStatusBar;
  protected Context context;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    this.context = context;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    if (mRootView != null) {
      ViewGroup parent = (ViewGroup) mRootView.getParent();
      if (parent != null)
        parent.removeView(mRootView);
    } else {
      mRootView = initComponent(inflater, container);
      createEventHandlers();
      loadData(savedInstanceState);
    }
    return mRootView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (isLazyLoad()) {
      mIsPrepare = true;
      mIsImmersion = true;
      onLazyLoad();
    } else {
      if (isStatusBarEnabled())
        initStatusBar();
    }
    if (statusBarView() != null)
      ImmersionBar.setTitleBar(getActivity(), statusBarView());
  }

  protected void initStatusBar() {
    mStatusBar = ImmersionBar.with(getActivity(), this);
    if (isDarkFont())
      mStatusBar.statusBarDarkFont(true, 0.2f);
    mStatusBar.init();
  }

  protected View statusBarView() {
    return null;
  }

  protected boolean isDarkFont() {
    return false;
  }

  protected boolean isLazyLoad() {
    return true;
  }

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (getUserVisibleHint()) {
      mIsVisible = true;
      onVisibled();
    } else {
      mIsVisible = false;
      onInvisibled();
    }
  }

  protected void onVisibled() {
    onLazyLoad();
  }

  protected void onInvisibled() {
  }

  private void onLazyLoad() {
    if (mIsVisible && mIsPrepare)
      mIsPrepare = false;
    if (mIsVisible && mIsImmersion && isStatusBarEnabled())
      initStatusBar();
  }

  protected abstract View initComponent(LayoutInflater inflater, ViewGroup container);

  protected abstract void loadData(Bundle savedInstanceState);

  protected void createEventHandlers() {
  }

  protected boolean isStatusBarEnabled() {
    return true;
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (!hidden && mStatusBar != null)
      mStatusBar.init();
  }

  @Override
  public void onDestroy() {
    if (mStatusBar != null)
      mStatusBar.destroy();
    super.onDestroy();
  }
}
