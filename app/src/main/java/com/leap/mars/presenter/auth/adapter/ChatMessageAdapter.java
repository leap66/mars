package com.leap.mars.presenter.auth.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leap.mars.R;
import com.leap.mars.presenter.auth.activity.PersonalActivity;
import com.leap.mars.presenter.auth.activity.RobotActivity;
import com.leap.mars.presenter.auth.app.MyApplication;
import com.leap.mars.presenter.auth.entity.ItemInfo;
import com.leap.mars.presenter.auth.ui.CircleImageView;


public class ChatMessageAdapter extends BaseAdapter {
	Context context;
	List<ItemInfo> itemInfos;

	public ChatMessageAdapter(Context context, List<ItemInfo> itemInfos) {
		super();
		this.context = context;
		this.itemInfos = itemInfos;
	}

	@Override
	public int getItemViewType(int position) {
		ItemInfo infItemInfo = itemInfos.get(position);
		return infItemInfo.getType() == ItemInfo.Type.INPUT ? 1 : 0;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemInfo itemInfo = itemInfos.get(position);
		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			if (itemInfo.getType() != ItemInfo.Type.INPUT) {
				convertView = View.inflate(context, R.layout.item_robot_info,
						null);
				holder.tvDate = (TextView) convertView
						.findViewById(R.id.tv_item_robot_date);
				holder.tvContent = (TextView) convertView
						.findViewById(R.id.tv_item_robot_reply);
				holder.clvPictuer = (CircleImageView) convertView
						.findViewById(R.id.civ_item_robot_picture);
				holder.clvPictuer.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MyApplication.getApp(),
								RobotActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						MyApplication.getApp().startActivity(intent);
					}
				});
				convertView.setTag(holder);
			} else {
				convertView = View.inflate(context, R.layout.item_oneself_info,
						null);
				holder.tvDate = (TextView) convertView
						.findViewById(R.id.tv_item_oneself_date);
				holder.tvContent = (TextView) convertView
						.findViewById(R.id.tv_item_oneself_content);
				holder.clvPictuer = (CircleImageView) convertView
						.findViewById(R.id.civ_item_oneself_picture);
				holder.clvPictuer.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MyApplication.getApp(),
								PersonalActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						MyApplication.getApp().startActivity(intent);
					}
				});
				convertView.setTag(holder);
			}
		}
		holder = (ViewHolder) convertView.getTag();
		holder.tvDate.setText("");
		holder.tvContent.setText("");
		holder.tvDate.append(itemInfos.get(position).getDate());
		holder.tvContent.append(Html.fromHtml(itemInfos.get(position)
				.getContent()));
		holder.tvContent.setMovementMethod(LinkMovementMethod.getInstance());
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemInfos.size();
	}

	@Override
	public ItemInfo getItem(int position) {
		// TODO Auto-generated method stub
		return itemInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	class ViewHolder {
		TextView tvDate;
		TextView tvContent;
		CircleImageView clvPictuer;
	}
}
