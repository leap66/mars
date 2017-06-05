package com.leap.mars.presenter.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.leap.mars.R;
import com.leap.mini.net.network.event.AuthEvent;
import com.leap.mini.util.ToastUtil;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 基础 BaseActivity
 * <p>
 * Created by weiyaling on 20175/9.
 */
public abstract class BaseActivity extends AppCompatActivity {
  private boolean keyboardAutoHide = true;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    initComponent();
    // 在4.4及其以下的地方，状态栏都是黑色的（没有设置 translucent的情况下），那么文字直接设置成白色就好了
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
      setStatusBarDarkMode(false);
    } else {
      setStatusBarDarkMode(isStatusBarTextDark());
    }
    createEventHandlers();
    loadData(savedInstanceState);
    EventBus.getDefault().register(this);
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
   * 在onDestroy中销毁
   */
  protected void destroy() {
  }

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
      if (isEdt(v, ev) && keyboardAutoHide) {
        hideSoftInput(v);
      }
      return super.dispatchTouchEvent(ev);
    }
    if (getWindow().superDispatchTouchEvent(ev)) {
      return getWindow().superDispatchTouchEvent(ev);
    }
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

  /**
   * 隐藏软键盘
   */
  protected void hideSoftInput(View view) {
    InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    im.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
    destroy();
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void handleTokenExpired(AuthEvent event) {
    // 如果删除成功，就将详情页设置为前一页
    if (event.type == AuthEvent.TOKEN_EXPIRED) {
      // if (UpdateMgr.getInstance(this).isDialogShow())
      // return;
      ToastUtil.showHint(this, R.string.token_expired);
      logout();
    }
  }

  private void logout() {
    // CloudPushMgr.unBindAccount(SessionMgr.getUser().getId());
    // TokenMgr.clear();
    // SessionMgr.clearUser();
    // Intent intent = new Intent(this, MainActivity.class);
    // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
    // Intent.FLAG_ACTIVITY_NEW_TASK);
    // startActivity(intent);
  }

  /**
   * MIUI StatusBar 文字颜色
   * 
   * @param isStatusTextDark
   *          StatusBar 文字颜色是否为黑色
   */
  public void setStatusBarDarkMode(boolean isStatusTextDark) {
    Class<? extends Window> clazz = getWindow().getClass();
    try {
      int darkModeFlag = 0;
      Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
      Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
      darkModeFlag = field.getInt(layoutParams);
      Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
      extraFlagField.invoke(getWindow(), isStatusTextDark ? darkModeFlag : 0, darkModeFlag);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public boolean isStatusBarTextDark() {
    // 默认为白色
    return false;
  }
}