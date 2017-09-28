package com.leap.mini.widget;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import com.leap.mini.R;
import com.leap.mini.databinding.DialogLoadingBinding;

/**
 * 数量修改对话框
 * <p>
 * Created by weiyaling on 2017/1/12.
 */

public class LoadingDialog extends Dialog {
  private AnimationDrawable mAnimationDrawable;

  public LoadingDialog(@NonNull Context context) {
    super(context, R.style.alert_dialog);
    initComponent(context);
  }

  /**
   * 初始化组件
   */
  private void initComponent(Context context) {
    DialogLoadingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
        R.layout.dialog_loading, null, false);
    setContentView(binding.getRoot());
    binding.loading.setImageResource(R.drawable.loading);
    mAnimationDrawable = (AnimationDrawable) binding.loading.getDrawable();
    mAnimationDrawable.start();
  }

  @Override
  public void dismiss() {
    mAnimationDrawable.stop();
    super.dismiss();
  }
}
