package com.leap.mars.widget.audio;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;

import com.leap.mars.R;
import com.leap.mars.databinding.DialogRecorderBinding;

public class AudioDialog {
  private DialogRecorderBinding binding;
  private Dialog dialog;
  private Context context;
  private boolean isCancel;

  public AudioDialog(Context context) {
    this.context = context;
  }

  /**
   * 设置录音中的Dialog
   */
  public void showDialog() {
    dialog = new Dialog(context, R.style.Theme_AudioDialog);
    binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_recorder, null,
        false);
    binding.setItem(false);
    dialog.setContentView(binding.getRoot());
    dialog.show();

  }

  /**
   * 录音中
   */
  public void recording() {
    if (dialog != null && dialog.isShowing()) {
      binding.setItem(false);
      binding.ivRecorder.setImageResource(R.mipmap.voice1);
      binding.tvHint.setText(context.getString(R.string.audio_recording));
      isCancel = false;
    }
  }

  /**
   * 想取消录音
   */
  public void wantToCancel() {
    if (dialog != null && dialog.isShowing()) {
      binding.setItem(true);
      binding.ivRecorder.setImageResource(R.mipmap.cancel_voice);
      binding.tvHint.setText(context.getString(R.string.audio_cancel_hint));
      isCancel = true;
    }
  }

  /**
   * 录音文件太短
   */
  public void tooShort() {
    if (dialog != null && dialog.isShowing()) {
      binding.setItem(true);
      binding.tvHint.setText(context.getString(R.string.audio_too_short));
      binding.ivRecorder.setImageResource(R.mipmap.warning);
      isCancel = true;
    }
  }

  /**
   * 取消弹框
   */
  public void dismissDialog() {
    if (dialog != null && dialog.isShowing()) {
      dialog.dismiss();
      dialog = null;
    }
  }

  /**
   * 更新弹框音量变化图标提示
   * 
   * @param level
   *          1--7
   */
  public void updateVoiceLevel(int level) {
    if (dialog != null && dialog.isShowing() && !isCancel) {
      int resId = context.getResources().getIdentifier("voice" + level, "drawable",
          context.getPackageName());
      binding.ivRecorder.setImageResource(resId);
    }
  }

}
