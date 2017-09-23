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
import com.leap.mars.presenter.auth.activity.ControlActivity;
import com.leap.mars.presenter.auth.app.MyApplication;
import com.leap.mars.widget.CircleImageView;

public class ControlFragment extends Fragment {
	private CircleImageView civControl;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_control, null);

		civControl = (CircleImageView) view
				.findViewById(R.id.civ_fragment_control);
		// ���Ŷ���
		ScaleAnimation anim = (ScaleAnimation) AnimationUtils.loadAnimation(
				MyApplication.getApp(), R.anim.play_voice);
		civControl.startAnimation(anim);
		civControl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyApplication.getApp(),
						ControlActivity.class);
				startActivity(intent);
			}
		});
		return view;
	}
}
