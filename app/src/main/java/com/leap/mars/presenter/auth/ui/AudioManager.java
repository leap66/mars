package com.leap.mars.presenter.auth.ui;

import java.io.File;
import java.util.UUID;

import android.media.MediaRecorder;

public class AudioManager {

	private MediaRecorder mediaRecorder;
	private String mDir;
	private String mCurrentFilePath;

	private boolean isPrepared;

	private static AudioManager mInstance;

	public AudioManager(String dir) {
		mDir = dir;
	}

	/**
	 * ¼��״̬�ӿ� ��¼��׼����ɺ�ص�
	 * 
	 * @author Administrator
	 */
	public interface AudioStateListener {
		void wellPrepared();
	}

	public AudioStateListener mListener;

	public void setOnAudioStateListener(AudioStateListener Listener) {
		mListener = Listener;
	}

	public static AudioManager getmInstance(String dir) {
		if (mInstance == null) {
			// ͬ�� mInstance
			synchronized (AudioManager.class) {
				if (mInstance == null) {
					mInstance = new AudioManager(dir);
				}
			}
		}

		return mInstance;
	}

	/**
	 * ׼�� ¼��
	 */
	public void prepareAudio() {

		isPrepared = false;
		File dir = new File(mDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String fileName = generateFileName();
		File file = new File(dir, fileName);
		mCurrentFilePath = file.getAbsolutePath();
		try {
			mediaRecorder = new MediaRecorder();
			// ��������ļ�
			mediaRecorder.setOutputFile(file.getAbsolutePath());
			// ���� MediaRecorder ����ƵԴΪ��˷�
			mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			// ���� MediaRecorder ����Ƶ�ĸ�ʽ
			mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
			// ���� MediaRecorder ����Ƶ�ı���Ϊamr
			mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mediaRecorder.prepare();
			mediaRecorder.start();
			// ׼������
			isPrepared = true;
			if (mListener != null) {
				mListener.wellPrepared();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��������ļ���
	 * 
	 * @return
	 */
	private String generateFileName() {

		return UUID.randomUUID().toString() + ".amr";
	}

	/**
	 * ��ȡ�����ȼ�
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
	 * �ͷ�
	 */
	public void release() {
		mediaRecorder.stop();
		mediaRecorder.release();
		mediaRecorder = null;
	}

	/**
	 * ȡ��
	 */
	public void cancel() {
		if (mCurrentFilePath != null) {
			File file = new File(mCurrentFilePath);
			file.delete();
			mCurrentFilePath = null;
		}
	}

	/**
	 * ��ȡ��ǰ�ļ��еľ���·��
	 * 
	 * @return
	 */
	public String getCurrentFilePath() {
		return mCurrentFilePath;
	}
}
