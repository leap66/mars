package com.leap.mars.presenter.auth.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.leap.mars.R;
import com.leap.mars.presenter.auth.app.MyApplication;
import com.leap.mars.presenter.auth.util.PreferenceNavigation;

public class SplashActivity extends Activity {
	private ImageView ivSplashLoadingItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		// �ؼ���ʼ��
		setViews();
		// ���ö���
		setAnimation();
		// ��ת��MainActivity
		toActivitv();
	}

	/**
	 * ��ת��MainActivity
	 */
	private void toActivitv() {
		Timer timer = new Timer();
		timer.schedule(new Task(), 1000);
	}
	
	/**
	 * ��֧��ת
	 */
	private class Task extends TimerTask{
		@Override
		public void run() {
			if(PreferenceNavigation.getNavigation(getBaseContext())){
				Intent intent = new Intent(SplashActivity.this,
						MainActivity.class);
				startActivity(intent); 
				overridePendingTransition(R.anim.in_from_anin, R.anim.out_to_anin);
				finish();
			}else {
				PreferenceNavigation.putUserId(MyApplication.getApp(), System.currentTimeMillis()+"");
				Intent intent = new Intent(SplashActivity.this,
						NavigationActivity.class);
				startActivity(intent); 
				overridePendingTransition(R.anim.in_from_anin, R.anim.out_to_anin);
				PreferenceNavigation.putNavigation(getBaseContext(), true);
				finish();
			}
			
		}
		
	}

	/**
	 * ����ͼƬ����
	 */
	private void setAnimation() {
		ObjectAnimator anim = ObjectAnimator.ofFloat(ivSplashLoadingItem, "x",
				120, 270);
		anim.setDuration(2000);
		anim.setRepeatCount(ObjectAnimator.INFINITE);
		anim.setRepeatMode(ObjectAnimator.INFINITE);
		anim.start();
		Toast.makeText(SplashActivity.this, "����ƴ��������.....", Toast.LENGTH_LONG)
				.show();
	}

	/**
	 * �ؼ���ʼ��
	 */
	private void setViews() {
		ivSplashLoadingItem = (ImageView) findViewById(R.id.iv_splash_loading_item);

	}
}
