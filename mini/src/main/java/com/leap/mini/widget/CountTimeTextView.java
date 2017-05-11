package com.leap.mini.widget;

import com.leap.mini.R;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * 倒计时按钮
 * <p>
 * </> Created by weiyaling on 2016/11/21.
 */

public class CountTimeTextView extends TextView {

  private long totalTime = 60000;
  private CountDownTimer timer;

  public CountTimeTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setGravity(Gravity.CENTER);
  }

  public void startCount() {
    this.startCount(totalTime);
  }

  public void startCount(long totalTime) {
    this.totalTime = totalTime;
    long tickTime = 1000;
    setEnabled(false);
    timer = new CountDownTimer(totalTime, tickTime) {
      @Override
      public void onTick(long millisUntilFinished) {
        setText(millisUntilFinished / 1000 + " "
            + getResources().getString(R.string.register_send_code_time));
      }

      @Override
      public void onFinish() {
        setEnabled(true);
        setText(getResources().getString(R.string.register_send_code));
      }
    }.start();
  }

  public void finishCount() {
    timer.cancel();
    timer.onFinish();
    setEnabled(true);
  }

  public void setTotaltime(long totalTime) {
    this.totalTime = totalTime;
  }
}
