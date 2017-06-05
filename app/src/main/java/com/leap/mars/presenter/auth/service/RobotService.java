package com.leap.mars.presenter.auth.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.leap.mars.presenter.auth.activity.RobotActivity;
import com.leap.mars.presenter.auth.app.MyApplication;
import com.leap.mars.presenter.auth.util.Consts;

public class RobotService extends Service implements Consts {

	private InnerReceover receiver;
	AlertDialog alertDialog = null;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// ע��㲥������
		receiver = new InnerReceover();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_OPEN_MAIN_EDIT);
		registerReceiver(receiver, filter);
	}

	private class InnerReceover extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// ��ȡ�㲥�е�Action
			String action = intent.getAction();
			// �ж�Action
			if (ACTION_OPEN_MAIN_EDIT.equals(action)) {
				OpenEdit();
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * �򿪼�����༭
	 */
	public void OpenEdit() {
		Intent intent = new Intent(MyApplication.getApp(), RobotActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}
}
