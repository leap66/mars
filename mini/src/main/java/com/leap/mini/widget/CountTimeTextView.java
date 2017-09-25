package com.leap.mini.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

import com.leap.mini.R;
import com.leap.mini.util.StringUtil;

/**
 * 倒计时按钮
 * <p>
 * </> Created by weiyaling on 2016/11/21.
 */
public class CountTimeTextView extends AppCompatTextView {

  private long totalTime = 60000;
  private CountDownTimer timer;
  private boolean isCount;

  public CountTimeTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setGravity(Gravity.CENTER);
    setBackgroundResource(R.drawable.bg_default_ignore);
  }

  public void startCount() {
    startCount(totalTime);
  }

  public void startCount(long totalTime) {
    this.totalTime = totalTime;
    long tickTime = 1000;
    setEnabled(false);
    isCount = true;
    timer = new CountDownTimer(totalTime, tickTime) {
      @Override
      public void onTick(long millisUntilFinished) {
        setText(StringUtil.format(getResources().getString(R.string.send_code),
            millisUntilFinished / 1000));
      }

      @Override
      public void onFinish() {
        isCount = false;
        setEnabled(true);
        setText(getResources().getString(R.string.send_code_hint));
      }
    }.start();
  }

  public void finishCount() {
    if (timer != null) {
      timer.cancel();
      timer.onFinish();
    }
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    finishCount();
  }

  @Override
  public void setEnabled(boolean enabled) {
    if (!isCount) {
      super.setEnabled(enabled);
    }
  }
}
