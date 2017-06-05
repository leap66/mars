package com.leap.mars.presenter.auth.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import android.os.AsyncTask;

import com.leap.mars.presenter.auth.app.MyApplication;

public class UtilBaiduReplyVoice {

	/**
	 * �첽���ط�����Ϣ �ӿ�
	 */
	public interface LoadCallBack {
		/**
		 * ���ݼ�����ɺ�ִ��
		 */
		void onDateLoaded(Object obj);
	}

	/**
	 * �첽���ط�����Ϣ
	 * 
	 * @param content
	 * @param loadCallBack
	 */
	public void LoadTextReply(final String content,
			final BaiDuCallback.LoadCallBack loadCallBack) {

		AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {

			@Override
			protected String doInBackground(String... params) {
				String respText = null;
				try {
					respText = loadTextCallback(content);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return respText;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				loadCallBack.onDateLoaded(result);
			}

		};
		task.execute();
	}

	private String loadTextCallback(String content) {
		String respText = null;
		// content = URLEncoder.encode(content);
		try {
			String content1 = new String(content.getBytes(), "utf-8");
			String serviceUrl2 = "http://tsn.baidu.com/text2audio?tex="
					+ content1
					+ "&lan=zh&cuid&lan=zh&cuid=%2095aba1232cd3471d4babf240cc019af3%20&ctp=1&tok=%2024.abbb9d77c68b74767a70ac9e62d7e4e2.2592000.1472980961.282335-8437596";
			// 1. URL
			URL url = new URL(serviceUrl2);
			// 2. HttpURLConnection
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 3. getInputStream
			InputStream is = conn.getInputStream();
			// 4. ��ȡ�ַ���

			File file = new File(MyApplication.getApp().getCacheDir(),
					generateFileName());
			FileOutputStream fos = new FileOutputStream(file);

			byte[] buff = new byte[9192];
			int b;// ����ÿһ��������
			while ((b = is.read(buff)) != -1) {
				fos.write(buff, 0, b);
			}
			is.close();
			fos.close();

			// Log.i("info", "file.length()   " + file.length());
			respText = file.toString();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// �ַ�����ȡ���
		// Log.i("info", "respText      " + respText);
		// Log.i("info", "respText.toString()" + respText.toString());
		return respText.toString();
	}

	/**
	 * ��������ļ���
	 * 
	 * @return
	 */
	private String generateFileName() {

		return UUID.randomUUID().toString() + ".amr";
	}
}
