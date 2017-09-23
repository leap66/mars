package com.leap.mars.widget.audio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.leap.mars.R;

import java.io.IOException;

@SuppressLint("HandlerLeak")
public class AudioRecorderButton extends AppCompatTextView implements AudioStateListener {
  private static final int DISTANCE_Y_CANCEL = 50; // 取消距离
  private static final int STATE_NORMAL = 1; // 正常状态
  private static final int STATE_RECORDING = 2; // 点击录音中状态
  private static final int STATE_WANT_TO_CANCEL = 3; // 取消状态
  private float mTime;
  private int mCurState = STATE_NORMAL; // 当前状态
  // 是否正在录音中
  private boolean isRecording = false;
  // 录音已完成
  private boolean isReady;
  private AudioDialog audioDialog;
  private AudioManager audioManager;

  private AudioListener listener;

  public void setAudioListener(AudioListener listener) {
    this.listener = listener;
  }

  public AudioRecorderButton(Context context) {
    this(context, null);
  }

  public AudioRecorderButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    audioDialog = new AudioDialog(context);
    String dir = Environment.getExternalStorageDirectory() + "/mars_audios";
    audioManager = AudioManager.getInstance(dir);
    audioManager.setOnAudioStateListener(this);
    setOnLongClickListener(new OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        // 长按时即启动录音
        isReady = true;
        audioManager.prepareAudio();
        return false;
      }
    });
  }

  private static final int MSG_AUDIO_PREPARED = 0X110; // 录音已准备完毕
  private static final int MSG_VOICE_CHANGED = 0X111; // 音量改变
  private static final int MSG_DIALOG_DISMISS = 0X112; // Dialog取消

  /**
   * 计时
   */
  private Runnable mGetVoiceLevelRunnable = new Runnable() {

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
   * 接收消息
   */
  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {
      switch (msg.what) {
      case MSG_AUDIO_PREPARED:
        audioDialog.showDialog();
        isRecording = true;
        new Thread(mGetVoiceLevelRunnable).start();
        break;
      case MSG_VOICE_CHANGED:
        audioDialog.updateVoiceLevel(audioManager.getVoiceLevel(7));
        break;

      case MSG_DIALOG_DISMISS:
        audioDialog.dismissDialog();
        break;
      }
    };
  };

  /**
   * 录音已准备就绪
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
        // 根据手势滑动距离判断是否取消录音
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
        handler.sendEmptyMessageDelayed(MSG_DIALOG_DISMISS, 1000);
        reset();
        return super.onTouchEvent(event);
      } else if (mCurState == STATE_RECORDING) { // 正常录音完成
        audioDialog.dismissDialog();
        audioManager.release();
        if (listener != null) {
          try {
            listener.onFinish((int) (mTime * 1000), audioManager.getCurrentFilePath());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      } else if (mCurState == STATE_WANT_TO_CANCEL) {
        audioDialog.dismissDialog();
        audioManager.cancel();
      }
      reset();
      break;
    }
    return super.onTouchEvent(event);
  }

  /**
   * 重置
   */
  private void reset() {
    isReady = false;
    isRecording = false;
    changeState(STATE_NORMAL);
    mTime = 0;
  }

  /**
   * 按钮状态改变
   * 
   * @param state
   *          state
   */
  private void changeState(int state) {
    if (mCurState != state) {
      mCurState = state;
      switch (state) {
      case STATE_NORMAL:
        setBackgroundResource(R.drawable.btn_recorder_narmal);
        setText(R.string.str_recorder_normal);
        reset();
        break;

      case STATE_RECORDING:
        setBackgroundResource(R.drawable.btn_recording);
        setText(R.string.str_recorder_recording);
        if (isRecording) {
          audioDialog.recording();
        }
        break;

      case STATE_WANT_TO_CANCEL:
        setBackgroundResource(R.drawable.btn_recording);
        setText(R.string.str_recorder_want_cancel);
        audioDialog.wantToCancel();
        break;
      }
    }

  }

  /**
   * 根据手势滑动距离判断是否取消录音
   */
  private boolean wantToCancel(int x, int y) {
    if (x < 0 || x > getWidth()) {
      return true;
    }
    // 最大滑动距离为50
    return (y < -DISTANCE_Y_CANCEL || y > getMeasuredHeight() + DISTANCE_Y_CANCEL);
  }

}
