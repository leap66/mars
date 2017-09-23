package com.leap.mars.presenter.auth.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.leap.mars.R;
import com.leap.mars.presenter.auth.adapter.ChatMessageAdapter;
import com.leap.mars.presenter.auth.app.MyApplication;
import com.leap.mars.presenter.auth.entity.BaiduReplyText;
import com.leap.mars.presenter.auth.entity.ChatRecord;
import com.leap.mars.presenter.auth.entity.ItemInfo;
import com.leap.mars.presenter.auth.entity.TuLingBackMessage;
import com.leap.mars.presenter.auth.presenter.RobotPresenter;
import com.leap.mars.presenter.auth.service.RobotService;
import com.leap.mars.presenter.auth.ui.AudioRecorderButton;
import com.leap.mars.presenter.auth.util.BaiDuCallback;
import com.leap.mars.presenter.auth.util.Consts;
import com.leap.mars.presenter.auth.util.JudgeEtInfo;
import com.leap.mars.presenter.auth.util.UtilBaiduReplyText;
import com.leap.mars.presenter.auth.util.UtilDate;
import com.leap.mars.presenter.auth.view.IView;
import com.leap.mars.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

public class TextChatActivity extends Activity implements IView, Consts {

  private ListView listView;
  private Button btnSend;
  private ImageButton ibtnVoice;
  private CircleImageView civEdit;
  private EditText etSendInfo;
  private List<ItemInfo> itemInfos = new ArrayList<ItemInfo>();
  private List<ItemInfo> cacheItemInfos = new ArrayList<ItemInfo>();
  private boolean isCache = true;
  private ChatMessageAdapter chatMessageAdapter;
  private RobotPresenter presenter;
  private JudgeEtInfo judgeEtInfo;
  private ChatRecord chatRecord = new ChatRecord();
  private BroadcastReceiver receiver;
  private ServiceConnection conn;
  private RelativeLayout layoutVoice;
  private AudioRecorderButton audioRecorderButton;
  private GestureDetector gestureDetector = new GestureDetector(new myGestureListener());
  private boolean isRefresh = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat_text);
    // ��ʼ���ؼ�
    setView();
    // ��ʼ��������Ϣ
    setListView();
    // ���ü�����
    setListeners();

    // �� Service
    Intent intent = new Intent(this, RobotService.class);
    conn = new ServiceConnection() {

      @Override
      public void onServiceDisconnected(ComponentName name) {
      }

      @Override
      public void onServiceConnected(ComponentName name, IBinder service) {
      }
    };
    bindService(intent, conn, BIND_AUTO_CREATE);

    // ע��㲥������
    receiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
      }
    };
    IntentFilter filter = new IntentFilter();
    filter.addAction(ACTION_OPEN_MAIN_MORE);
    filter.addAction(ACTION_OPEN_MAIN_EDIT);
    filter.addAction(ACTION_OPEN_MAIN_VOICE);
    registerReceiver(receiver, filter);
  }

  /**
   * ��ʼ��������Ϣ
   */
  private void setListView() {
    // ��ȡ����
    itemInfos = MyApplication.getchatRecord().getItemInfos();
    ItemInfo itemInfo = new ItemInfo();
    itemInfo.setDate(UtilDate.getFormatedTime(System.currentTimeMillis()));
    itemInfo.setContent("��ã����ǰ�����СĽ�������ּ�����...");
    itemInfos.add(itemInfo);
    if (isCache) {
      cacheItemInfos.add(itemInfo);
      setAdapter();
    }
    if (itemInfos.size() > 1) {
      if ((itemInfos.get(itemInfos.size() - 2).getContent())
          .equals("��ã����ǰ�����СĽ�������ּ�����...")) {
        chatRecord.saveChatRecord(itemInfos);
        chatRecord.removeItem();
      }
    }
    setAdapter();
  }

  private class myGestureListener extends SimpleOnGestureListener {
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
      if (e2.getY() - e1.getY() > 400) {
        isCache = false;
        setAdapter();
        isRefresh = false;
      }
      return super.onFling(e1, e2, velocityX, velocityY);
    }
  }

  /**
   * ���ü�����
   */
  private void setListeners() {
    // ΪbtnSend ���� ����
    InnerOnClickListener listener = new InnerOnClickListener();
    btnSend.setOnClickListener(listener);
    civEdit.setOnClickListener(listener);
    ibtnVoice.setOnClickListener(listener);
    listView.setOnTouchListener(new OnTouchListener() {
      @Override
      // ���Բ���event�¼�
      public boolean onTouch(View v, MotionEvent event) {
        if (isRefresh) {
          gestureDetector.onTouchEvent(event);
        }
        return false;
      }
    });
    etSendInfo.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        ibtnVoice.setBackgroundResource(R.mipmap.yuyin);
        layoutVoice.setVisibility(View.GONE);
        isVoicePressed = true;
      }
    });
    audioRecorderButton
        .setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {

          @Override
          public void onFinish(float seconds, String filePath) {
            // if (Environment.MEDIA_MOUNTED.equals(Environment
            // .getExternalStorageState())) {
            // }
            // File musicDir = Environment
            // .getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
            // File file = new File(musicDir, "/baidu.amr");
            // Log.i("info", file.toString() +
            // " file.toString()");
            UtilBaiduReplyText replyText = new UtilBaiduReplyText();
            replyText.LoadTextReply(filePath, new BaiDuCallback.LoadCallBack() {

              @Override
              public void onDateLoaded(Object obj) {
                if (obj == null) {
                  // Log.i("info", obj + "obj");
                  return;
                }
                Gson gson = new Gson();
                BaiduReplyText replyText = gson.fromJson((String) obj, BaiduReplyText.class);
                // Toast.makeText(
                // MyApplication.getApp(),
                // replyText.getErr_msg(),
                // Toast.LENGTH_SHORT).show();
                // Log.i("info", replyText + "replyText");
                if (replyText.getResult()[0] == null) {
                  Toast.makeText(MyApplication.getApp(), "�ף�û��������˵����ʲô...",
                      Toast.LENGTH_SHORT).show();
                  return;
                }
                // Log.i("info", replyText.toString()
                // + " replyText.toString()");
                String content1 = replyText.getResult()[0].toString();
                ItemInfo itemInfo = new ItemInfo();
                itemInfo.setType(ItemInfo.Type.INPUT);
                itemInfo.setDate(UtilDate.getFormatedTime(System.currentTimeMillis()));
                itemInfo.setContent(content1.substring(0, content1.length() - 1));
                if (isCache) {
                  cacheItemInfos.add(itemInfo);
                  setAdapter();
                }
                itemInfos.add(itemInfo);
                setAdapter();
                judgeEtInfo = new JudgeEtInfo();
                String back = judgeEtInfo.getBack(content1.substring(0, content1.length() - 1));
                if (back != null) {
                  ItemInfo itemInfo1 = new ItemInfo();
                  itemInfo1.setDate(UtilDate.getFormatedTime(System.currentTimeMillis()));
                  itemInfo1.setContent(back);
                  if (isCache) {
                    cacheItemInfos.add(itemInfo1);
                    setAdapter();
                  }
                  itemInfos.add(itemInfo1);
                  setAdapter();
                } else {
                  presenter.getRobotReply(content1.substring(0, content1.length() - 1));
                }
              }
            });

          }
        });
  }

  private class InnerOnClickListener implements OnClickListener {
    @Override
    public void onClick(View v) {
      switch (v.getId()) {
      case R.id.btn_main_bottom_send:
        // ��ȡ �������Ϣ������
        getInputInfo();
        break;
      case R.id.civ_main_edit:
        // �༭ ��������Ϣ �� ������Ϣ
        Toast.makeText(TextChatActivity.this, "�༭ ��������Ϣ", Toast.LENGTH_SHORT).show();
        sendBroadcast(new Intent(ACTION_OPEN_MAIN_EDIT));
        break;
      case R.id.ibtn_main_bottom_voice:
        if (isVoicePressed) {
          ibtnVoice.setBackgroundResource(R.mipmap.jianpan);
          layoutVoice.setVisibility(View.VISIBLE);
          isVoicePressed = false;
        } else {
          ibtnVoice.setBackgroundResource(R.mipmap.yuyin);
          layoutVoice.setVisibility(View.GONE);
          isVoicePressed = true;
        }
        break;
      }

    }

  }

  private boolean isVoicePressed = true;

  /**
   * ��ȡ������Ϣ
   */
  public void getInputInfo() {
    String etInputInfo = etSendInfo.getText().toString();
    etSendInfo.setText(null);
    ItemInfo ItemInfo = new ItemInfo();
    ItemInfo.setContent(etInputInfo);
    ItemInfo.setType(com.leap.mars.presenter.auth.entity.ItemInfo.Type.INPUT);
    ItemInfo.setDate(UtilDate.getFormatedTime(System.currentTimeMillis()));
    if (isCache) {
      cacheItemInfos.add(ItemInfo);
      setAdapter();
    }
    itemInfos.add(ItemInfo);
    judgeEtInfo = new JudgeEtInfo();
    String back = judgeEtInfo.getBack(etInputInfo);
    if (back != null) {
      ItemInfo itemInfo = new ItemInfo();
      itemInfo.setDate(UtilDate.getFormatedTime(System.currentTimeMillis()));
      itemInfo.setContent(back);
      if (isCache) {
        cacheItemInfos.add(itemInfo);
        setAdapter();
      }
      itemInfos.add(itemInfo);
      setAdapter();
    } else {
      presenter.getRobotReply(etInputInfo);
    }

  }

  /**
   * ΪlistView ���� Adapter
   */
  private void setAdapter() {
    if (isCache) {
      // ���漯��
      chatRecord.saveChatRecord(itemInfos);
      chatMessageAdapter = new ChatMessageAdapter(this, cacheItemInfos);
      listView.setAdapter(chatMessageAdapter);
      listView.setSelection(cacheItemInfos.size() - 1);
    } else {
      chatMessageAdapter = new ChatMessageAdapter(this, itemInfos);
      listView.setAdapter(chatMessageAdapter);
      listView.setSelection(itemInfos.size() - 1);
    }
  }

  /**
   * ��ʼ���ؼ�
   */
  private void setView() {
    listView = (ListView) findViewById(R.id.lv_main_list);
    btnSend = (Button) findViewById(R.id.btn_main_bottom_send);
    civEdit = (CircleImageView) findViewById(R.id.civ_main_edit);
    ibtnVoice = (ImageButton) findViewById(R.id.ibtn_main_bottom_voice);
    etSendInfo = (EditText) findViewById(R.id.et_main_bottom_info);
    layoutVoice = (RelativeLayout) findViewById(R.id.rl_main_bottom2);
    audioRecorderButton = (AudioRecorderButton) findViewById(R.id.btn_recorder_control);

    presenter = new RobotPresenter(this);
  }

  @Override
  public void showList(String replyText) {
    if (replyText != null) {
      Gson gson = new Gson();
      TuLingBackMessage message = gson.fromJson(replyText, TuLingBackMessage.class);
      ItemInfo itemInfo = new ItemInfo();
      if (message.getUrl() != null) {
        itemInfo
            .setContent(message.getText() + "\t" + "<a href='" + message.getUrl() + "'>������</a>");
      } else {
        itemInfo.setContent(message.getText());
      }
      itemInfo.setDate(UtilDate.getFormatedTime(System.currentTimeMillis()));
      if (isCache) {
        cacheItemInfos.add(itemInfo);
        setAdapter();
      }
      itemInfos.add(itemInfo);
      setAdapter();
    } else {
      ItemInfo itemInfo = new ItemInfo();
      itemInfo.setContent("��ѽ..�������̫������...");
      itemInfo.setDate(UtilDate.getFormatedTime(System.currentTimeMillis()));
      itemInfos.add(itemInfo);
      if (isCache) {
        cacheItemInfos.add(itemInfo);
        setAdapter();
      }
      setAdapter();
    }
  }

  @Override
  protected void onDestroy() {
    // ע���㲥������
    unregisterReceiver(receiver);
    unbindService(conn);
    super.onDestroy();
  }

}
