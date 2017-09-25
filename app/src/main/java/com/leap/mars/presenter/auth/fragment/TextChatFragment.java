package com.leap.mars.presenter.auth.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.leap.mars.R;
import com.leap.mars.presenter.auth.NavigationActivity;
import com.leap.mars.presenter.auth.activity.TextChatActivity;
import com.leap.mars.presenter.auth.app.MyApplication;
import com.leap.mars.widget.CircleImageView;

public class TextChatFragment extends Fragment {

	private CircleImageView civHead;

	private LinearLayout linearLayoutHeadItem;
	private LinearLayout linearLayoutSaoYiSao;
	private LinearLayout linearLayoutYaoYiYao;
	private LinearLayout linearLayoutNearby;
	private LinearLayout linearLayoutHelp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_text_chat, null);
		// �ؼ���ʼ��
		setViews(view);
		// ���ü�����
		setListeners();

		return view;
	}

	/**
	 * ���ü�����
	 */
	private void setListeners() {
		civHead.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyApplication.getApp(),
						TextChatActivity.class);
				startActivity(intent);
			}
		});
		linearLayoutHeadItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyApplication.getApp(),
						TextChatActivity.class);
				startActivity(intent);
			}
		});
		linearLayoutSaoYiSao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(MyApplication.getApp(), "�˹�����δʵ�֣����ǻ��ں����汾�г�������...",
						Toast.LENGTH_SHORT).show();
			}
		});
		linearLayoutYaoYiYao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(MyApplication.getApp(), "�˹�����δʵ�֣����ǻ��ں����汾�г�������...",
						Toast.LENGTH_SHORT).show();			}
		});
		linearLayoutNearby.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(MyApplication.getApp(), "�˹�����δʵ�֣����ǻ��ں����汾�г�������...",
						Toast.LENGTH_SHORT).show();
			}
		});
		linearLayoutHelp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyApplication.getApp(),
						NavigationActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});

	}

	/**
	 * �ؼ���ʼ��
	 * 
	 * @param view
	 */
	private void setViews(View view) {
		civHead = (CircleImageView) view.findViewById(R.id.civ_text_chat_head);
		linearLayoutHeadItem = (LinearLayout) view
				.findViewById(R.id.ll_text_chat_head_item);
		linearLayoutSaoYiSao = (LinearLayout) view
				.findViewById(R.id.ll_saoyisao_item);
		linearLayoutYaoYiYao = (LinearLayout) view
				.findViewById(R.id.ll_yaoyiyao_item);
		linearLayoutNearby = (LinearLayout) view
				.findViewById(R.id.ll_nearby_item);
		linearLayoutHelp = (LinearLayout) view.findViewById(R.id.ll_help_item);
	}
}
