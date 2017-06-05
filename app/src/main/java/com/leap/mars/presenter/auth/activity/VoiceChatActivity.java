package com.leap.mars.presenter.auth.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.leap.mars.R;
import com.google.gson.Gson;
import com.leap.mars.presenter.auth.adapter.ChatVoiceAdapter;
import com.leap.mars.presenter.auth.app.MyApplication;
import com.leap.mars.presenter.auth.entity.BaiduReplyText;
import com.leap.mars.presenter.auth.entity.RecorderVoice;
import com.leap.mars.presenter.auth.entity.TuLingBackMessage;
import com.leap.mars.presenter.auth.presenter.RobotPresenter;
import com.leap.mars.presenter.auth.ui.AudioRecorderButton;
import com.leap.mars.presenter.auth.util.BaiDuCallback;
import com.leap.mars.presenter.auth.util.MediaManager;
import com.leap.mars.presenter.auth.util.UtilBaiduReplyText;
import com.leap.mars.presenter.auth.util.UtilBaiduReplyVoice;
import com.leap.mars.presenter.auth.util.UtilDate;
import com.leap.mars.presenter.auth.view.IView;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class VoiceChatActivity extends Activity implements IView {
  private ListView listView;
  private List<RecorderVoice> recorders;
  private ChatVoiceAdapter mAdapter;
  private AudioRecorderButton button;
  private View animView;
  private RobotPresenter robotPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_voice_chat);
    // ��ʼ���ؼ�
    setViews();
    // ����Adapter
    setAdapter();
    // ���ü�����
    setListener();
  }

  /**
   * ����Adapter
   */
  private void setAdapter() {
    mAdapter = new ChatVoiceAdapter(recorders, this);
    listView.setAdapter(mAdapter);
  }

  /**
   * ���ü�����
   */
  private void setListener() {
    button.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {

      @Override
      public void onFinish(float seconds, String filePath) {
        // File musicDir = Environment
        // .getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        // File file = new File(musicDir, "/baidu.amr");
        // Log.i("info", file.toString() + " file.toString()");

        RecorderVoice recorder = new RecorderVoice();
        recorder.setTime(seconds);
        recorder.setFilePath(filePath);
        recorder.setType(RecorderVoice.Type.INPUT);
        recorder.setName("��Ľ");
        recorder.setDate(UtilDate.getFormatedTime(System.currentTimeMillis()));
        recorders.add(recorder);
        // mAdapter.notifyDataSetChanged();
        setAdapter();
        listView.setSelection(recorders.size() - 1);
        /**
         * ����˵������ת��Ϊ����
         */
        getVoiceToText(filePath);
      }
    });

    listView.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // �������������
        if (recorders.get(position).getType() == RecorderVoice.Type.INPUT) {
          if (animView != null) {
            animView.setBackgroundResource(R.mipmap.audio_right);
            animView = null;
          }
          // ���Ŷ���
          animView = view.findViewById(R.id.v_item_voice_case);
          animView.setBackgroundResource(R.drawable.play_right_voice);
          AnimationDrawable anim = (AnimationDrawable) animView.getBackground();
          anim.start();
          // ������Ƶ
          MediaManager.playSound(recorders.get(position).getFilePath(),
              new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                  animView.setBackgroundResource(R.mipmap.audio_right);
                }
              });
        } else { // �������������
          if (animView != null) {
            animView.setBackgroundResource(R.mipmap.audio_lift);
            animView = null;
          }
          // ���Ŷ���
          animView = view.findViewById(R.id.v_item_voice_case_reply);
          animView.setBackgroundResource(R.drawable.play_lift_voice);
          AnimationDrawable anim = (AnimationDrawable) animView.getBackground();
          anim.start();
          // ������Ƶ
          MediaManager.playSound(recorders.get(position).getFilePath(),
              new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                  animView.setBackgroundResource(R.mipmap.audio_lift);
                }
              });
        }

      }

    });

  }

  /**
   * �ڶ��� ������ת��Ϊ����
   *
   * @param filePath
   */
  protected void getVoiceToText(String filePath) {
    UtilBaiduReplyText replyText = new UtilBaiduReplyText();
    replyText.LoadTextReply(filePath, new BaiDuCallback.LoadCallBack() {

      @Override
      public void onDateLoaded(Object obj) {
        if (obj == null) {
          // Log.i("info", "�ڶ��� �����������ٶȺ�û�еõ�����...");
          return;
        }
        Gson gson = new Gson();
        BaiduReplyText replyText = gson.fromJson((String) obj, BaiduReplyText.class);
        String textReply = replyText.getResult()[0];
        if (textReply == null) {
          Toast.makeText(MyApplication.getApp(), "�ף�û���������˵����ʲô...", Toast.LENGTH_LONG)
              .show();
          return;
        }
        textReply = textReply.substring(0, textReply.length() - 1);
        // ������ �����ַ���ͼ�飬�õ����ֻظ�
        robotPresenter.getRobotReply(textReply);
        // Log.i("info", replyText.toString());
        Toast.makeText(MyApplication.getApp(), textReply, Toast.LENGTH_LONG).show();
      }
    });

  }

  @Override
  protected void onPause() {
    // TODO Auto-generated method stub
    super.onPause();
    MediaManager.pause();
  }

  @Override
  protected void onResume() {
    // TODO Auto-generated method stub
    super.onResume();
    MediaManager.resume();
  }

  @Override
  protected void onDestroy() {
    // TODO Auto-generated method stub
    super.onDestroy();
    MediaManager.release();
  }

  /**
   * ��ʼ���ؼ�
   */
  private void setViews() {
    listView = (ListView) findViewById(R.id.lv_main_chat);
    button = (AudioRecorderButton) findViewById(R.id.btn_recorder);

    recorders = new ArrayList<RecorderVoice>();
    robotPresenter = new RobotPresenter(this);
  }

  @Override
  public void showList(String replyText) {
    if (replyText == null) {
      // Log.i("info", "�����ַ���ͼ���û�еõ��ش�...");
      return;
    }
    if (replyText != null) {
      Gson gson = new Gson();
      TuLingBackMessage message = gson.fromJson(replyText, TuLingBackMessage.class);
      // RecorderVoice recorder = new RecorderVoice();
      // if (message.getUrl() != null) {
      // recorder.setURLPath("" + "\t" + "<a href='" + message.getUrl()
      // + "'>������</a>");
      // } else {
      // recorder.setContent(message.getText());
      // }
      // �����ַ����ٶ� �õ� �����ش�
      UtilBaiduReplyVoice replyVoice = new UtilBaiduReplyVoice();
      replyVoice.LoadTextReply(message.getText(), new BaiDuCallback.LoadCallBack() {
        @Override
        public void onDateLoaded(Object obj) {
          if (obj == null) {
            // Log.i("info", "�����ַ����ٶ� �õ� �����ش� Ϊ��");
            return;
          }
          String filePath = (String) obj;
          File file = new File(filePath);
          RecorderVoice recorder = new RecorderVoice();
          recorder.setTime(file.length() / 1000);
          // Log.i("info",
          // file.length()/1000+"�����ַ����ٶ� �õ� �����ش� 22222");
          recorder.setName("СĽ");
          recorder.setType(RecorderVoice.Type.OUTPUT);
          recorder.setFilePath(filePath);
          recorder.setDate(UtilDate.getFormatedTime(System.currentTimeMillis()));
          // Log.i("info", recorder.toString()+
          // "�����ַ����ٶ� �õ� �����ش� 22222");
          recorders.add(recorder);
          // mAdapter.notifyDataSetChanged();
          setAdapter();
          listView.setSelection(recorders.size() - 1);

          // ������Ƶ
          MediaManager.playSound(filePath, new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
              return;
            }
          });

        }
      });
    }

  }
}
