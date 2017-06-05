package com.leap.mars.presenter.auth.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.leap.mars.R;
import com.leap.mars.presenter.auth.fragment.ControlFragment;
import com.leap.mars.presenter.auth.fragment.MineFragment;
import com.leap.mars.presenter.auth.fragment.TextChatFragment;
import com.leap.mars.presenter.auth.fragment.VoiceChatFragment;

public class MainActivity extends FragmentActivity {
	private RadioGroup radioGroup;
	private ViewPager viewPager;
	private RadioButton rb0, rb1, rb2, rb3;
	private List<Fragment> fragments;
	private MainPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// ��ʼ���ؼ�
		setViews();
		// ����Adapter
		setAdapter();
		// ���ü�����
		setListener();

	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void setViews() {
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup_main);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		rb0 = (RadioButton) findViewById(R.id.rb_text_chat);
		rb1 = (RadioButton) findViewById(R.id.rb_voice_chat);
		rb2 = (RadioButton) findViewById(R.id.rb_contral);
		rb3 = (RadioButton) findViewById(R.id.rb_mine);

		fragments = new ArrayList<Fragment>();
		adapter = new MainPagerAdapter(getSupportFragmentManager());
	}

	/**
	 * ����Adapter
	 */
	private void setAdapter() {
		fragments.add(new TextChatFragment());
		fragments.add(new VoiceChatFragment());
		fragments.add(new ControlFragment());
		fragments.add(new MineFragment());
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(1);

	}

	/**
	 * ���ü�����
	 */
	private void setListener() {
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_text_chat:
					viewPager.setCurrentItem(0);
					break;
				case R.id.rb_voice_chat:
					viewPager.setCurrentItem(1);
					break;
				case R.id.rb_contral:
					viewPager.setCurrentItem(2);
					break;
				case R.id.rb_mine:
					viewPager.setCurrentItem(3);
					break;
				}

			}
		});

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					rb0.setChecked(true);
					break;
				case 1:
					rb1.setChecked(true);
					break;
				case 2:
					rb2.setChecked(true);
					break;
				case 3:
					rb3.setChecked(true);
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}

	private class MainPagerAdapter extends FragmentPagerAdapter {

		public MainPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

	}

	@Override
	public void onBackPressed() {
		Intent Intent1 = new Intent(Intent.ACTION_MAIN);
		Intent1.addCategory(Intent.CATEGORY_HOME);
		startActivity(Intent1);
	}

}
