package com.leap.mars.presenter.auth.util;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.leap.mars.presenter.auth.app.MyApplication;

/**
 * ��������
 * 
 * @param content
 */
public class ControlConsts {

	/**
	 * ���ֿ���
	 * 
	 * @param content
	 */
	public void ToControl(String content) {

		if (content.contains("�绰")) {
			OpenPhone();
		} else if (content.contains("����")) {
			OpenSMS();
		} else if (content.contains("Ӧ��")) {
			OpenApp();
		} else if (content.contains("��Ѷ")) {
			OpenQQ();
		} else if (content.contains("�ٶ�")) {
			OpenBaiDu();
		} else if (content.contains("��ͼ")) {
			OpenMap();
		} else if (content.contains("��ϵ��")) {
			OpenContacts();
		} else if (content.contains("���")) {
			OpenPhoto();
		} else if (content.contains("ͼ��")) {
			OpenPhotos();
		} else {
			// TODO
			Toast.makeText(MyApplication.getApp(), "�˹�����δʵ�֣����ǻ��ں����汾�г�������...",
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * ��QQ
	 */
	public void OpenQQ() {
		try {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			ComponentName componentName = new ComponentName(
					"com.tencent.mobileqq",
					"com.tencent.mobileqq.activity.SplashActivity");
			intent.setComponent(componentName);
			MyApplication.getApp().startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * �򿪰ٶ�
	 */
	public void OpenBaiDu() {
		try {
			Uri uri = Uri.parse("http://www.baidu.com");
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			MyApplication.getApp().startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * ��绰
	 */
	public void OpenPhone() {
		try {
			Uri uri = Uri.parse("tel:");
			Intent intent = new Intent(Intent.ACTION_DIAL, uri);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			MyApplication.getApp().startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * ������
	 */
	public void OpenSMS() {
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.putExtra("sms_body", "");
			intent.setType("vnd.android-dir/mms-sms");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			MyApplication.getApp().startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * �򿪵�ͼ
	 */
	public void OpenMap() {
		try {
			Uri uri = Uri.parse("geo:38.899533,-77.036476"); 
			Intent intent = new Intent(Intent.ACTION_VIEW,uri);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			MyApplication.getApp().startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * ��ʾ�û�Ӧ����ϸ��Ϣ
	 */
	public void OpenApp() {
		try {
			Uri uri =Uri.parse("market://details?id=app_id"); 
			Intent intent = new Intent(Intent.ACTION_VIEW,uri);    			
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			MyApplication.getApp().startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * ����ϵ���б�
	 */
	public void OpenContacts() {
		try {
			Uri uri = Uri.parse("content://contacts/people"); 
			Intent intent = new Intent(Intent.ACTION_PICK, uri); 			
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			MyApplication.getApp().startActivity(intent);
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * �����
	 */
	public void OpenPhoto() {
		try {
			Intent intent = new Intent("android.media.action.STILL_IMAGE_CAMERA"); //��������� 
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			MyApplication.getApp().startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * ��ͼ��
	 */
	public void OpenPhotos() {
		try {
			Intent intent = new Intent(); 
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			MyApplication.getApp().startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}


}
