package com.leap.mini.util;

import com.leap.mini.R;
import com.leap.mini.widget.sweetAlert.SweetAlertDialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;

/**
 * 页面描述：对话框工具
 * <p>
 * Created by weiyaling on 17/3/7.
 */
public class DialogUtil {

  /**
   * 加载进度对话框
   */
  public static SweetAlertDialog getProgressDialog(Context context) {
    SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
    dialog.setTitleText(context.getResources().getString(R.string.load_data));
    dialog.getProgressHelper().setBarColor(ContextCompat.getColor(context, R.color.theme_opaque));// colorPrimary
    return dialog;
  }

  /**
   * 显示错误对话框
   */
  public static SweetAlertDialog showError(Context context, String string) {
    SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
    dialog.setConfirmText(context.getString(R.string.ok));
    // dialog.setTitleText(string);
    dialog.setContentText(string);
    return dialog;
  }

  /**
   * 确认对话框
   */
  public static SweetAlertDialog getConfirmDialog(Context context, String title) {
    final SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
    dialog.setTitleText(title);
    dialog.setConfirmText(context.getString(R.string.confirm));
    dialog.setCancelText(context.getString(R.string.cancel));
    dialog.showCancelButton(true);
    dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
      @Override
      public void onClick(SweetAlertDialog sweetAlertDialog) {
        dialog.dismiss();
      }
    });
    return dialog;
  }
}
