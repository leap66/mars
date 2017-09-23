package com.leap.mars.presenter.auth.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.leap.mars.R;
import com.leap.mars.widget.audio.AudioDialog;
import com.leap.mars.widget.audio.AudioManager;
import com.leap.mars.widget.audio.AudioStateListener;

@SuppressLint("HandlerLeak")
public class AudioRecorderButton extends Button implements AudioStateListener {

  private static final int DISTANCE_Y_CANCEL = 50; // �ı�y�������ƶ��ľ���

  private static final int STATE_NARMAL = 1; // Ĭ��״̬
  private static final int STATE_RECORDING = 2; // ¼��״̬
  private static final int STATE_WANT_TO_CANCEL = 3; // ȡ��״̬

  private float mTime;

  private int mCurState = STATE_NARMAL; // ��ǰ״̬��¼

  // �Ƿ� �Ѿ���ʼ¼��
  private boolean isRecording = false;
  // �Ƿ񴥷�LongClick
  private boolean isReady;
  private AudioDialog audioDialog;
  private AudioManager audioManager;

  /**
   * ¼����ɺ�Ļص�
   */
  public interface AudioFinishRecorderListener {
    void onFinish(float seconds, String filePath);
  }

  private AudioFinishRecorderListener audioFinishRecorderListener;

  public void setAudioFinishRecorderListener(AudioFinishRecorderListener listener) {
    audioFinishRecorderListener = listener;
  }

  public AudioRecorderButton(Context context) {
    this(context, null);
  }

  public AudioRecorderButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    audioDialog = new AudioDialog(context);
    String dir = Environment.getExternalStorageDirectory() + "/chat_recorder_audios";
    audioManager = AudioManager.getInstance(dir);
    audioManager.setOnAudioStateListener(this);

    setOnLongClickListener(new OnLongClickListener() {

      @Override
      public boolean onLongClick(View v) {
        // TODO ������ʾӦ����audio end prepared �Ժ�
        isReady = true;
        audioManager.prepareAudio();
        return false;
      }
    });
  }

  private static final int MSG_AUDIO_PREPARED = 0X110; // ¼��׼����
  private static final int MSG_VOICE_CHANGED = 0X111; // ¼���ı�
  private static final int MSG_DIALOG_DIMISS = 0X112; // �Ի���ȡ��

  /**
   * ��ȡ������С ���߳��н���
   */
  private Runnable mGetVoiceLevelRaunnable = new Runnable() {

    @Override
    public void run() {
      while (isRecording) {
        try {
          Thread.sleep(100);
          handler.sendEmptyMessage(MSG_VOICE_CHANGED);
          mTime += 0.1f;
        } catch (InterruptedException e) {
          e.printStackTrace();
          return;
        }

      }
    }
  };

  /**
   * ����һ����Ϣ�߳�
   */
  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch (msg.what) {
      case MSG_AUDIO_PREPARED:
        audioDialog.showDialog();
        isRecording = true;
        new Thread(mGetVoiceLevelRaunnable).start();
        break;
      case MSG_VOICE_CHANGED:
        audioDialog.updateVoiceLevel(audioManager.getVoiceLevel(7));
        break;

      case MSG_DIALOG_DIMISS:
        audioDialog.dismissDialog();
        break;
      }
    };
  };

  /**
   * ¼��׼�����֮��Ļص�
   */
  @Override
  public void wellPrepared() {
    handler.sendEmptyMessage(MSG_AUDIO_PREPARED);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    int Action = event.getAction();
    int x = (int) event.getX();
    int y = (int) event.getY();

    switch (Action) {
    case MotionEvent.ACTION_DOWN:
      changeState(STATE_RECORDING);
      break;
    case MotionEvent.ACTION_MOVE:
      if (isRecording) {
        // ����x,y�����꣬�ж��Ƿ���Ҫȡ��
        if (wantToCancel(x, y)) {
          changeState(STATE_WANT_TO_CANCEL);
        } else {
          changeState(STATE_RECORDING);
        }
      }
      break;
    case MotionEvent.ACTION_UP:
      if (!isReady) {
        reset();
        return super.onTouchEvent(event);
      }
      if (!isRecording || mTime < 0.6f) {
        audioDialog.tooShort();
        audioManager.cancel();
        handler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS, 1000);
        reset();
        return super.onTouchEvent(event);
      } else if (mCurState == STATE_RECORDING) { // ����¼�ƽ���
        audioDialog.dismissDialog();
        audioManager.release();
        if (audioFinishRecorderListener != null) {
          audioFinishRecorderListener.onFinish(mTime, audioManager.getCurrentFilePath());
        }
        // release
        // callbackToAct
      } else if (mCurState == STATE_WANT_TO_CANCEL) {
        audioDialog.dismissDialog();
        audioManager.cancel();
        // cancel
      }
      // audioDialog.dismissDialog();
      // ����
      reset();
      break;
    }

    // TODO Auto-generated method stub
    return super.onTouchEvent(event);
  }

  /**
   * ����
   */
  private void reset() {
    isReady = false;
    isRecording = false;
    changeState(STATE_NARMAL);
    mTime = 0;
  }

  /**
   * �ı䵱ǰ״̬
   * 
   * @param state
   */
  private void changeState(int state) {
    if (mCurState != state) {
      mCurState = state;
      switch (state) {
      case STATE_NARMAL:
        setBackgroundResource(R.drawable.btn_recorder_narmal);
        setText(R.string.str_recorder_normal);
        reset();
        break;

      case STATE_RECORDING:
        setBackgroundResource(R.drawable.btn_recording);
        setText(R.string.str_recorder_recording);
        if (isRecording) {
          // TODO Dialog.recording();
          audioDialog.recording();
        }

        break;

      case STATE_WANT_TO_CANCEL:
        setBackgroundResource(R.drawable.btn_recording);
        setText(R.string.str_recorder_want_cancel);
        // TODO Dialog.wantCanael();
        audioDialog.wantToCancel();
        break;
      }
    }

  }

  /**
   * ����x,y�����꣬�ж��Ƿ���Ҫȡ��
   */
  private boolean wantToCancel(int x, int y) {
    if (x < 0 || x > getWidth()) {
      return true;
    }
    // �ж���ָ��������ƶ� �Ƿ񳬳���ť��Χ
    if (y < -DISTANCE_Y_CANCEL || y > getWidth() + DISTANCE_Y_CANCEL) {
      return true;
    }

    return false;
  }

}
