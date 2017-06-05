package com.leap.mars.presenter.auth.app;

import android.app.Application;

import com.leap.mars.presenter.auth.entity.ChatRecord;

public class MyApplication extends Application {

	private static MyApplication app;
	private static ChatRecord chatRecord;

	public static MyApplication getApp() {
		return app;
	}

	public static ChatRecord getchatRecord() {
		return chatRecord;

	}

	@Override
	public void onCreate() {
		app = this;
		chatRecord = new ChatRecord();
		chatRecord = chatRecord.readChatRecord();
		super.onCreate();
	}

}
