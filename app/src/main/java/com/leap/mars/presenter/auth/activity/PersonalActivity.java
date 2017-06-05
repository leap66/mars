package com.leap.mars.presenter.auth.activity;

import com.leap.mars.R;
import com.leap.mars.presenter.auth.app.MyApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class PersonalActivity extends Activity {
  private Button btEdit;
  private Button btSave;
  private ImageView ivBack;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_personal);
    // ��ʼ���ؼ�
    setViews();
    // ���ü�����
    setListeners();

  }

  /**
   * ���ü�����
   */
  private void setListeners() {
    btEdit.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        Toast.makeText(MyApplication.getApp(), "�˹�����δʵ�֣����ǻ��ں����汾�г�������...",
            Toast.LENGTH_SHORT).show();
      }
    });
    btSave.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        Intent intent = new Intent(PersonalActivity.this, TextChatActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_anin, R.anim.out_to_anin);
      }
    });
    ivBack.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        Intent intent = new Intent(PersonalActivity.this, TextChatActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_anin, R.anim.out_to_anin);
      }
    });

  }

  /**
   * ��ʼ���ؼ�
   */
  private void setViews() {
    btEdit = (Button) findViewById(R.id.bt_personal_edit);
    btSave = (Button) findViewById(R.id.bt_personal_save);
    ivBack = (ImageView) findViewById(R.id.iv_personal_back);
  }

}
