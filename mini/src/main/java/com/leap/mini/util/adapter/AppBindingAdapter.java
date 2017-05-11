package com.leap.mini.util.adapter;

import java.util.concurrent.TimeUnit;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.leap.mini.R;
import com.leap.mini.util.IsEmpty;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rx.functions.Action1;

/**
 * 数据绑定的adapter 用于处理自定义的xml中的属性操作
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class AppBindingAdapter {

  @BindingAdapter("imageResourceId")
  public static void loadImage(ImageView view, int resourceId) {
    Glide.with(view.getContext()).load(resourceId).into(view);
  }

  @BindingAdapter("imageResourceUrl")
  public static void loadImage(ImageView view, Object resourceUrl) {
    if (IsEmpty.object(resourceUrl) | IsEmpty.string(String.valueOf(resourceUrl))) {
      Glide.with(view.getContext()).load(R.mipmap.img_no_photo).placeholder(R.mipmap.img_no_photo)
          .error(R.mipmap.img_no_photo).into(view);
    } else if (resourceUrl instanceof String) {
      Glide.with(view.getContext()).load((String) resourceUrl).placeholder(R.mipmap.img_no_photo)
          .error(R.mipmap.img_failed_load).into(view);
    } else if (resourceUrl instanceof Integer) {
      Glide.with(view.getContext()).load((Integer) resourceUrl).placeholder(R.mipmap.img_no_photo)
          .error(R.mipmap.img_failed_load).into(view);
    } else {
      Glide.with(view.getContext()).load(R.mipmap.img_no_photo).placeholder(R.mipmap.img_no_photo)
          .error(R.mipmap.img_no_photo).into(view);
    }
  }

  @BindingAdapter("layoutManager")
  public static void setLayoutManager(RecyclerView view, RecyclerView.LayoutManager manager) {
    view.setLayoutManager(manager);
  }

  @BindingAdapter("adapter")
  public static void setAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
    view.setAdapter(adapter);
  }

  /**
   * 注意，单独设置 throttleTime 是没有意义的
   */
  @BindingAdapter(value = {
      "throttleClick", "throttleTime" }, requireAll = false)
  public static void throttleClick(final View view, final View.OnClickListener clickListener,
      Integer throttleTime) {
    // 如果没有设置 throttleTime， 默认间隔为 defaultTime
    int defaultTime = 5;
    if (throttleTime != null) {
      defaultTime = throttleTime;
    }
    // 如果只设置了 throttleTime, clickListener 为 null
    if (clickListener != null) {
      RxView.clicks(view).throttleFirst(defaultTime, TimeUnit.SECONDS)
          .subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
              clickListener.onClick(view);
            }
          });
    }
  }
}
