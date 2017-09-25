//package com.leap.mars.presenter.auth.fragment;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.RelativeLayout;
//import android.widget.Toast;
//
//import com.leap.mars.R;
//import com.leap.mars.presenter.auth.NavigationActivity;
//import com.leap.mars.presenter.auth.activity.PersonalActivity;
//import com.leap.mars.presenter.auth.activity.RobotActivity;
//import com.leap.mars.presenter.auth.app.MyApplication;
//import com.leap.mars.presenter.main.MainActivity;
//
//public class MineFragment extends Fragment {
//
//	private RelativeLayout layout;
//	private Button btnInstructions;
//	private Button btnPersonal;
//	private Button btnRobot;
//	private Button btnEdit;
//	private Button btnBack;
//	private InnerOnClickListener listener;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		View view = inflater.inflate(R.layout.fragment_mine, null);
//		// ��ʼ���ؼ�
//		setViews(view);
//		// ���ü�����
//		setListener();
//		return view;
//	}
//
//	/**
//	 * ���ü�����
//	 */
//	private void setListener() {
//		listener = new InnerOnClickListener();
//		layout.setOnClickListener(listener);
//		btnInstructions.setOnClickListener(listener);
//		btnPersonal.setOnClickListener(listener);
//		btnRobot.setOnClickListener(listener);
//		btnEdit.setOnClickListener(listener);
//		btnBack.setOnClickListener(listener);
//	}
//
//	/**
//	 * ��ʼ���ؼ�
//	 */
//	private void setViews(View view) {
//		layout = (RelativeLayout) view.findViewById(R.id.rl_main_head);
//		btnInstructions = (Button) view.findViewById(R.id.bt_edit_instructions);
//		btnPersonal = (Button) view.findViewById(R.id.bt_edit_personal);
//		btnRobot = (Button) view.findViewById(R.id.bt_edit_robot);
//		btnEdit = (Button) view.findViewById(R.id.bt_edit_edit);
//		btnBack = (Button) view.findViewById(R.id.bt_edit_back);
//	}
//
//	public class InnerOnClickListener implements OnClickListener {
//
//		@Override
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.id.rl_main_head:
//				break;
//			case R.id.bt_edit_instructions:
//				Intent intent4 = new Intent(MyApplication.getApp(),
//						NavigationActivity.class);
//				intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				startActivity(intent4);
//				break;
//			case R.id.bt_edit_personal:
//				Intent intent5 = new Intent(MyApplication.getApp(),
//						PersonalActivity.class);
//				intent5.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				startActivity(intent5);
//				break;
//			case R.id.bt_edit_robot:
//				Intent intent6 = new Intent(MyApplication.getApp(),
//						RobotActivity.class);
//				intent6.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				startActivity(intent6);
//				break;
//			case R.id.bt_edit_edit:
//				Toast.makeText(MyApplication.getApp(),
//						"�˹�����δʵ�֣����ǻ��ں����汾�г�������...", Toast.LENGTH_SHORT).show();
//				break;
//			case R.id.bt_edit_back:
//				Intent intent7 = new Intent(MyApplication.getApp(),
//						MainActivity.class);
//				intent7.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				startActivity(intent7);
//				break;
//			}
//
//		}
//	}
//
//}
