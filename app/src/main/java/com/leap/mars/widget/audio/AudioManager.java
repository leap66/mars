package com.leap.mars.widget.audio;

import android.media.MediaRecorder;

import java.io.File;
import java.util.UUID;

public class AudioManager {

  private MediaRecorder mediaRecorder;
  private String dir;
  private String filePath;
  private boolean isPrepared;
  private static AudioManager mInstance;
  private AudioStateListener mListener;

  private AudioManager(String dir) {
    this.dir = dir;
  }

  public void setOnAudioStateListener(AudioStateListener Listener) {
    mListener = Listener;
  }

  public static AudioManager getInstance(String dir) {
    if (mInstance == null) {
      // AudioManager 单例模式
      synchronized (AudioManager.class) {
        if (mInstance == null) {
          mInstance = new AudioManager(dir);
        }
      }
    }
    return mInstance;
  }

  /**
   * 准备录音
   */
  public void prepareAudio() {
    isPrepared = false;
    File fileDir = new File(dir);
    if (!fileDir.isDirectory()) {
      fileDir.mkdirs();
    }
    String fileName = UUID.randomUUID().toString() + ".amr";
    File file = new File(fileDir, fileName);
    filePath = file.getAbsolutePath();
    try {
      // 1.使用new创建一个实例android.media.MediaRecorder
      mediaRecorder = new MediaRecorder();
      // 2.创建一个android.content.ContentValues实例并设置一些标准的属性，
      // 像TITLE，TIMESTAMP，最重要的是MIME_TYPE;
      // 3.创建一个要放置的文件的路径，这可以通过android.content.ContentResolver在内容数据库中来
      // 创建一个入口，并且自动地标志一个取得这个文件的路径
      mediaRecorder.setOutputFile(file.getAbsolutePath());
      // 4.设置音频资源；这将会很可能使用到MediaRecorder.AudioSource.MIC;
      mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
      // 5.设置输出文件格式
      mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
      // 6.设置音频编码；
      mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
      // 7.最后，prepare()和start()所录制的音频，stop()和release()在要结束的时候调用。
      mediaRecorder.prepare();
      mediaRecorder.start();
      isPrepared = true;
      if (mListener != null) {
        mListener.wellPrepared();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 获取音量大小1--7
   * 
   * @return int
   */
  public int getVoiceLevel(int maxLevel) {
    if (isPrepared) {
      try {
        // mediaRecorder.getMaxAmplitude() 1-32767
        return maxLevel * mediaRecorder.getMaxAmplitude() / 32768 + 1;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return 1;
  }

  /**
   * 释放音频资源
   */
  public void release() {
    if (mediaRecorder == null)
      return;
    mediaRecorder.stop();
    mediaRecorder.release();
    mediaRecorder = null;
  }

  /**
   * 取消录音并删除录音文件
   */
  public void cancel() {
    release();
    if (filePath != null) {
      File file = new File(filePath);
      file.delete();
      filePath = null;
    }
  }

  /**
   * 获取当前文件名
   */
  public String getCurrentFilePath() {
    return filePath;
  }
}
