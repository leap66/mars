package com.leap.mars.presenter.auth.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.leap.mars.R;

public class NavigationActivity extends Activity{
	
	private Button button;
	private ViewPager pager;
	private List<View> lists;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);
		// ��ʼ���ؼ�
		setViews();
		// ����ViewPager
		setViewPagers();		
		// ���ü�����
		setListener();
		
	}

	/**
	 * ���ü�����
	 */
	private void setListener() {
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), MainActivity.class));
				overridePendingTransition(R.anim.in_from_anin, R.anim.out_to_anin);			
			}
		});
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				if(arg0 == lists.size()-1){
					button.setVisibility(View.VISIBLE);
				}else {
					button.setVisibility(View.GONE);
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

	/**
	 * ��ʼ���ؼ�
	 */
	private void setViews() {
		button = (Button) findViewById(R.id.btn_navigation);
		pager = (ViewPager) findViewById(R.id.vp_navigation);	
		lists = new ArrayList<View>();
	}
	
	/**
	 * 
	 * ViewPagerAdapter
	 */
	private class myAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return lists.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		// ɾ��ViewPager��
		@Override            
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(lists.get(position));
		}
		// ��ʼ��itemʵ��
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(lists.get(position));
			return lists.get(position);			
		}		
	}
	
	/**
	 * ��ʼ��ViewPager
	 */
	private void setViewPagers(){
		ImageView iv = new ImageView(getBaseContext());
		iv.setImageResource(R.mipmap.navigation_01);
		lists.add(iv);
		ImageView iv1 = new ImageView(getBaseContext());
		iv1.setImageResource(R.mipmap.navigation_02);
		lists.add(iv1);
		ImageView iv2 = new ImageView(getBaseContext());
		iv2.setImageResource(R.mipmap.navigation_03);
		lists.add(iv2);
		pager.setAdapter(new myAdapter());
	}
}
