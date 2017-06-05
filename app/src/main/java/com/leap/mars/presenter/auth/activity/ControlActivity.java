package com.leap.mars.presenter.auth.activity;

import com.google.gson.Gson;
import com.leap.mars.R;
import com.leap.mars.presenter.auth.app.MyApplication;
import com.leap.mars.presenter.auth.entity.BaiduReplyText;
import com.leap.mars.presenter.auth.ui.AudioRecorderButton;
import com.leap.mars.presenter.auth.util.BaiDuCallback;
import com.leap.mars.presenter.auth.util.ControlConsts;
import com.leap.mars.presenter.auth.util.UtilBaiduReplyText;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class ControlActivity extends Activity {
  private ImageButton ibQQ;
  private ImageButton ibWeiXin;
  private ImageButton ibAlipay;
  private ImageButton ibPhone;
  private ImageButton ibYuYIn;
  private ImageButton ibInterent;
  private ImageButton ibJiSuanJi;
  private ImageButton ibWeather;
  private ImageButton ibRiLi;
  private AudioRecorderButton btnContorl;
  private ControlConsts controlConsts;

  private InnerOnClickListener listener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_control);
    // ��ʼ���ؼ�
    setViews();
    // ���ü�����
    setListener();
  }

  /**
   * ���ü�����
   */
  private void setListener() {
    ibQQ.setOnClickListener(listener);
    ibWeiXin.setOnClickListener(listener);
    ibAlipay.setOnClickListener(listener);
    ibPhone.setOnClickListener(listener);
    ibYuYIn.setOnClickListener(listener);
    ibInterent.setOnClickListener(listener);
    ibJiSuanJi.setOnClickListener(listener);
    ibWeather.setOnClickListener(listener);
    ibRiLi.setOnClickListener(listener);

    btnContorl
        .setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {

          @Override
          public void onFinish(float seconds, String filePath) {
            UtilBaiduReplyText replyText = new UtilBaiduReplyText();
            replyText.LoadTextReply(filePath, new BaiDuCallback.LoadCallBack() {

              @Override
              public void onDateLoaded(Object obj) {
                if (obj == null) {
                  Log.i("info", "�ٶ�û�а�����ת��");
                  return;
                }
                Gson gson = new Gson();
                BaiduReplyText baiduReplyText = gson.fromJson((String) obj, BaiduReplyText.class);
                String textReply = baiduReplyText.getResult()[0];
                if (textReply == null) {
                  Toast.makeText(MyApplication.getApp(), "�ף�û���������˵����ʲô...",
                      Toast.LENGTH_LONG).show();
                  return;
                }
                textReply = textReply.substring(0, textReply.length() - 1);
                Toast.makeText(MyApplication.getApp(), textReply, Toast.LENGTH_LONG).show();
                controlConsts.ToControl(textReply);
              }
            });
          }
        });
  }

  /**
   * ��ʼ���ؼ�
   */
  private void setViews() {
    ibQQ = (ImageButton) findViewById(R.id.ibtn_more_QQ);
    ibWeiXin = (ImageButton) findViewById(R.id.ibtn_more_weixin);
    ibAlipay = (ImageButton) findViewById(R.id.ibtn_more_alipay);
    ibPhone = (ImageButton) findViewById(R.id.ibtn_more_phone);
    ibYuYIn = (ImageButton) findViewById(R.id.ibtn_more_yuyin);
    ibInterent = (ImageButton) findViewById(R.id.ibtn_more_interent);
    ibJiSuanJi = (ImageButton) findViewById(R.id.ibtn_more_jisuanqi);
    ibWeather = (ImageButton) findViewById(R.id.ibtn_more_tianqi);
    ibRiLi = (ImageButton) findViewById(R.id.ibtn_more_rili);

    btnContorl = (AudioRecorderButton) findViewById(R.id.btn_recorder_control);

    listener = new InnerOnClickListener();
    controlConsts = new ControlConsts();
  }

  private class InnerOnClickListener implements OnClickListener {

    @Override
    public void onClick(View v) {
      switch (v.getId()) {
      case R.id.ibtn_more_QQ:
        Toast.makeText(MyApplication.getApp(), "��QQ", Toast.LENGTH_LONG).show();
        controlConsts.OpenQQ();
        break;

      case R.id.ibtn_more_interent:
        Toast.makeText(MyApplication.getApp(), "�򿪰ٶ�", Toast.LENGTH_LONG).show();
        controlConsts.OpenBaiDu();
        break;

      case R.id.ibtn_more_weixin:
        Toast.makeText(MyApplication.getApp(), "�˹�����δʵ�֣����ǻ��ں����汾�г�������...",
            Toast.LENGTH_LONG).show();
        // controlConsts.OpenBaiDu();
        break;

      case R.id.ibtn_more_alipay:
        Toast.makeText(MyApplication.getApp(), "�˹�����δʵ�֣����ǻ��ں����汾�г�������...",
            Toast.LENGTH_LONG).show();
        // controlConsts.OpenBaiDu();
        break;

      case R.id.ibtn_more_phone:
        Toast.makeText(MyApplication.getApp(), "�����", Toast.LENGTH_LONG).show();
        controlConsts.OpenPhoto();
        break;

      case R.id.ibtn_more_yuyin:
        Toast.makeText(MyApplication.getApp(), "�˹�����δʵ�֣����ǻ��ں����汾�г�������...",
            Toast.LENGTH_LONG).show();
        // controlConsts.OpenBaiDu();
        break;

      case R.id.ibtn_more_jisuanqi:
        Toast.makeText(MyApplication.getApp(), "�˹�����δʵ�֣����ǻ��ں����汾�г�������...",
            Toast.LENGTH_LONG).show();
        // controlConsts.OpenBaiDu();
        break;

      case R.id.ibtn_more_rili:
        Toast.makeText(MyApplication.getApp(), "�˹�����δʵ�֣����ǻ��ں����汾�г�������...",
            Toast.LENGTH_LONG).show();
        // controlConsts.OpenBaiDu();
        break;

      case R.id.ibtn_more_tianqi:
        Toast.makeText(MyApplication.getApp(), "�˹�����δʵ�֣����ǻ��ں����汾�г�������...",
            Toast.LENGTH_LONG).show();
        // controlConsts.OpenBaiDu();
        break;
      }

    }

  }
}
