package com.leap.mars.presenter.auth.util;

import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * ʵ�����ݵĴ������ȡ
 */
public class PreferenceNavigation {
	private static final String FILE_NAME = "navigation";
	private static final String MODE_NAME = "navigationKey";
	private static final String USERID_NAME = "USERID_NAME";
	private static final String USERIDKEY_NAME = "USERIDKEY_NAME";
	private static String userID = null;
	

	/**
	 * ��һ�ε�¼ʱ�洢��½ID��Ϣ
	 */
	public static void putUserId(Context context, String userId) {
		userID = userId;
		Editor editor = context.getSharedPreferences(USERID_NAME,
				Context.MODE_PRIVATE).edit();
		editor.putString(USERIDKEY_NAME, userId);
		editor.commit();
	}
	
	/**
	 * ��һ�ε�¼ʱ�洢��½��Ϣ
	 */
	public static void putNavigation(Context context, boolean isFrist) {
		Editor editor = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE).edit();
		editor.putBoolean(MODE_NAME, isFrist);
		editor.commit();
	}

	/**
	 * ��ȡ��һ�ε�½�û���ϢId
	 */
	public static String getUserId(Context context) {
		return context.getSharedPreferences(USERID_NAME, Context.MODE_PRIVATE)
				.getString(USERIDKEY_NAME, userID);
	}
	
	/**
	 * ÿ�ε�¼ʱ����Ƿ�Ϊ��һ�ε�½
	 */
	public static boolean getNavigation(Context context) {
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(MODE_NAME, false);
	}

}
