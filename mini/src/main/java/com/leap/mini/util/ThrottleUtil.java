package com.leap.mini.util;

import java.util.concurrent.TimeUnit;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import android.view.View;
import android.widget.TextView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by weiyaling on 2017/4/25.
 * <p>
 * </> 阀门工具，加入间隔时间，防止连续点击发出多次请求
 */

public class ThrottleUtil {

  /**
   * 防止重复点击
   */
  public static Observable<Void> clicks(View view) {
    return RxView.clicks(view).throttleFirst(5, TimeUnit.SECONDS);
  }

  /**
   * 延迟搜索
   */
  public static Observable<CharSequence> textChanges(TextView view) {
    return RxTextView.textChanges(view).debounce(5, TimeUnit.SECONDS)
        .subscribeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
        .filter(new Func1<CharSequence, Boolean>() {
          @Override
          public Boolean call(CharSequence charSequence) {
            return charSequence.toString().trim().length() > 0;
          }
        }).observeOn(AndroidSchedulers.mainThread());
  }
}
