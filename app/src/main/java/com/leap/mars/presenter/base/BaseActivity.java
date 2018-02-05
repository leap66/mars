package com.leap.mars.presenter.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.leap.mars.R;
import com.leap.mars.util.ShortcutMgr;
import com.leap.mini.net.network.event.AuthEvent;
import com.leap.mini.util.KeyBoardUtil;
import com.leap.mini.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 基础 BaseActivity
 * <p>
 * Created by weiyaling on 20175/9.
 */
public abstract class BaseActivity extends AppCompatActivity {
  private boolean keyboardAutoHide = true;
  protected ImmersionBar mStatusBar;
  protected Context context;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    context = this;
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    initComponent();
    loadData(savedInstanceState);
    createEventHandlers();
    EventBus.getDefault().register(this);
    if (useStatusBar())
      initStatusBar();
  }

  protected void initStatusBar() {
    mStatusBar = ImmersionBar.with(this);
    if (statusBarView() != null)
      mStatusBar.statusBarView(statusBarView());
    if (isDarkFont())
      mStatusBar.statusBarDarkFont(true, 0.2f);
    if (keyboardEnable())
      mStatusBar.keyboardEnable(true);
    mStatusBar.init();
  }

  protected boolean useStatusBar() {
    return true;
  }

  protected View statusBarView() {
    return null;
  }

  protected boolean isDarkFont() {
    return false;
  }

  protected boolean keyboardEnable() {
    return false;
  }

  /**
   * 初始化界面控件
   */
  protected abstract void initComponent();

  /**
   * 初次加载数据
   */
  protected abstract void loadData(Bundle savedInstanceState);

  /**
   * 界面事件响应
   */
  protected void createEventHandlers() {
  }

  /**
   * 设置软键盘是否自动隐藏
   */
  protected void setKeyboardAutoHide(boolean b) {
    this.keyboardAutoHide = b;
  }

  /**
   * 点击空白处隐藏软键盘
   */
  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
      View v = getCurrentFocus();
      if (isEdt(v, ev) && keyboardAutoHide)
        KeyBoardUtil.keyShow(v, false);
      return super.dispatchTouchEvent(ev);
    }
    if (getWindow().superDispatchTouchEvent(ev))
      return getWindow().superDispatchTouchEvent(ev);
    return onTouchEvent(ev);
  }

  /**
   * 判断当前焦点是否是输入框
   */
  public boolean isEdt(View v, MotionEvent event) {
    if (v != null && (v instanceof EditText)) {
      int[] leftTop = {
          0, 0 };
      v.getLocationInWindow(leftTop);
      int left = leftTop[0];
      int top = leftTop[1];
      int bottom = top + v.getHeight();
      int right = left + v.getWidth();
      return !(event.getX() > left && event.getX() < right && event.getY() > top
          && event.getY() < bottom);
    }
    return false;
  }

  @Override
  protected void onDestroy() {
    if (mStatusBar != null)
      mStatusBar.destroy();
    EventBus.getDefault().unregister(this);
    super.onDestroy();
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void handleTokenExpired(AuthEvent event) {
    if (event.type == AuthEvent.TOKEN_EXPIRED) {
      ToastUtil.showHint(this, R.string.token_expired);
      ShortcutMgr.logout();
    }
  }
}