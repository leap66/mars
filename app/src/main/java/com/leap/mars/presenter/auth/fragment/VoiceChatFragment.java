package com.leap.mars.presenter.auth.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

import com.leap.mars.R;
import com.leap.mars.presenter.auth.activity.VoiceChatActivity;
import com.leap.mars.presenter.auth.app.MyApplication;
import com.leap.mars.presenter.auth.ui.CircleImageView;

public class VoiceChatFragment extends Fragment{
	private CircleImageView civFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_voice_chat, null);
		civFragment = (CircleImageView) view.findViewById(R.id.civ_fragment);
		// ���Ŷ���
		ScaleAnimation anim = (ScaleAnimation) AnimationUtils.loadAnimation(MyApplication.getApp(), R.anim.play_voice);
		civFragment.startAnimation(anim);
		
		civFragment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyApplication.getApp(),VoiceChatActivity.class);
				startActivity(intent);
			}
		});
		
		return view;
	}
}
