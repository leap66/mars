package com.leap.mars.presenter.auth.ui;

import com.leap.mars.R;
import com.leap.mars.presenter.auth.app.MyApplication;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogManager {

  private Dialog dialog;
  private Context context;
  private ImageView ivVoice;
  private TextView tvLable;
  private boolean isCancel;

  public DialogManager(Context context) {
    this.context = context;
  }

  /**
   * ��ʾ¼���Ի���
   */
  public void showRecordingDialog() {

    dialog = new Dialog(context, R.style.Theme_AudioDialog);
    View view = View.inflate(context, R.layout.dialog_recorder, null);
    dialog.setContentView(view);

    ivVoice = (ImageView) dialog.findViewById(R.id.iv_dialog_recorder);
    tvLable = (TextView) dialog.findViewById(R.id.tv_dialog_recorder);
    dialog.show();

  }

  /**
   * ����¼��
   */
  public void recording() {
    if (dialog != null && dialog.isShowing()) {
      ivVoice.setVisibility(View.VISIBLE);
      tvLable.setVisibility(View.VISIBLE);
      tvLable.setTextColor(MyApplication.getApp().getResources().getColor(R.color.BLACK));

      ivVoice.setImageResource(R.mipmap.voice1);
      tvLable.setText("��ָ�ϻ���ȡ������");
      isCancel = false;
    }
  }

  /**
   * ��Ҫȡ��¼��
   */
  public void wantToCancel() {
    if (dialog != null && dialog.isShowing()) {
      ivVoice.setVisibility(View.VISIBLE);
      tvLable.setVisibility(View.VISIBLE);
      tvLable.setTextColor(MyApplication.getApp().getResources().getColor(R.color.RED));

      ivVoice.setImageResource(R.mipmap.cancel_voice);
      tvLable.setText("�ɿ���ָ��ȡ������");
      isCancel = true;
    }
  }

  /**
   * ʱ��̫��
   */
  public void tooShort() {
    if (dialog != null && dialog.isShowing()) {
      ivVoice.setVisibility(View.VISIBLE);
      tvLable.setVisibility(View.VISIBLE);
      tvLable.setText("¼��ʱ�����");
      tvLable.setTextColor(MyApplication.getApp().getResources().getColor(R.color.RED));

      ivVoice.setImageResource(R.mipmap.warning);
      // android.util.Log.i("info", "tooShort");
      isCancel = true;
    }
  }

  /**
   * ȡ���Ի���
   */
  public void dimissDialog() {
    if (dialog != null && dialog.isShowing()) {
      dialog.dismiss();
      dialog = null;
    }
  }

  /**
   * ͨ��levelȥ����voice�ϵ�ͼƬ ���¶�������
   * 
   * @param level
   *          1-7
   */
  public void updateVoiceLevel(int level) {
    if (dialog != null && dialog.isShowing() && !isCancel) {
      // ivVoice.setVisibility(View.VISIBLE);
      // tvLable.setVisibility(View.VISIBLE);

      int resId = context.getResources().getIdentifier("voice" + level, "drawable",
          context.getPackageName());
      ivVoice.setImageResource(resId);
    }
  }

}
