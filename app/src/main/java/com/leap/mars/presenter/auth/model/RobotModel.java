package com.leap.mars.presenter.auth.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.os.AsyncTask;

import com.leap.mars.presenter.auth.app.MyApplication;
import com.leap.mars.presenter.auth.util.PreferenceNavigation;

public class RobotModel implements IModel {

	private String URL_Head = "http://www.tuling123.com/openapi/api?key=158f6dbe76894285a4b705af6e06bb20&info=";
	String userid = PreferenceNavigation.getUserId(MyApplication.getApp());

	@Override
	public void LoadRobotReply(final String content, final LoadCallBack callback) {

		AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {

			@Override
			protected String doInBackground(String... params) {
				String respText = loadMessages(content);
				return respText;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				callback.onDateLoaded(result);
			}

		};
		task.execute();
	}

	/**
	 * ���ݴ����content ��ȡ�ظ���Ϣ
	 * 
	 * @return
	 */
	protected String loadMessages(String content) {
		String respText = "";
		try {
			// URL
			URL url = new URL(URL_Head + URLEncoder.encode(content, "utf-8")
					+ "&userid=" + userid);
			// HttpURLConnection
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// ��ȡ��ʽ
			conn.setRequestMethod("GET");
			// ��ȡ������
			InputStream is = conn.getInputStream();
			// ����
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			respText = sb.toString();
			if (is != null) {
				is.close();
			}
			if (reader != null) {
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return respText = null;
		}
		return respText;
	}

}
