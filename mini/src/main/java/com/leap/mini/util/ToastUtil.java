package com.leap.mini.util;

import com.leap.mini.R;
import com.leap.mini.databinding.ItemToastBinding;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

/**
 * Toast 格式化工具
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class ToastUtil {
  private static Toast toastSingle;

  public static void showHint(Context context, int stringResId) {
    String s = context.getResources().getString(stringResId);
    showToast(context, s, 0);
  }

  public static void showHint(Context context, String text) {
    showToast(context, text, 0);
  }

  public static void showFailure(Context context, int stringResId) {
    String text = context.getResources().getString(stringResId);
    showToast(context, text, R.mipmap.ic_toast_warning);
  }

  public static void showFailure(Context context, String text) {
    showToast(context, text, R.mipmap.ic_toast_warning);
  }

  public static void showSuccess(Context context, String text) {
    showToast(context, text, R.mipmap.ic_toast_success);
  }

  public static void showSuccess(Context context, int stringResId) {
    String text = context.getResources().getString(stringResId);
    showToast(context, text, R.mipmap.ic_toast_success);
  }

  private static void showToast(Context context, String text, int imageResource) {
    ItemToastBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
        R.layout.item_toast, null, false);
    Toast toast = new Toast(context);
    toast.setDuration(Toast.LENGTH_SHORT);
    toast.setGravity(Gravity.CENTER, 0, 0);
    if (0 == imageResource) {
      binding.imageIv.setVisibility(View.GONE);
    }
    binding.imageIv.setImageResource(imageResource);
    binding.titleTv.setText(text);
    binding.titleTv.setMaxWidth(Resources.getSystem().getDisplayMetrics().widthPixels * 3 / 5);
    toast.setView(binding.getRoot());
    if (!IsEmpty.object(toastSingle)) {
      toastSingle.cancel();
    }
    toast.show();
    toastSingle = toast;
  }
}
