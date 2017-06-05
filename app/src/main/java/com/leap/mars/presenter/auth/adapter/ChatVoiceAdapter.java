package com.leap.mars.presenter.auth.adapter;

import java.util.List;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leap.mars.R;
import com.leap.mars.presenter.auth.entity.RecorderVoice;

public class ChatVoiceAdapter extends BaseAdapter {

	private List<RecorderVoice> recorders;
	private Context context;

	private int mMinItemWith;
	private int mMaxItemWith;

	public ChatVoiceAdapter(List<RecorderVoice> recorders, Context context) {
		super();
		this.recorders = recorders;
		this.context = context;

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);

		mMaxItemWith = (int) (outMetrics.widthPixels * 0.6f);
		mMinItemWith = (int) (outMetrics.widthPixels * 0.15f);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		RecorderVoice recorderVoice = recorders.get(position);
		// Log.i("info", recorders.toString() + "");
		// Log.i("info", recorderVoice.toString() + "");
		if (convertView == null) {
			holder = new ViewHolder();
			if (recorderVoice.getType() == RecorderVoice.Type.INPUT) {
				convertView = View.inflate(context, R.layout.item_chat_voice,
						null);
				holder.tvTime = (TextView) convertView
						.findViewById(R.id.tv_item_voice_time);
				holder.tvDate = (TextView) convertView
						.findViewById(R.id.tv_item_voice_date);
				holder.length = convertView
						.findViewById(R.id.fl_item_voice_length);
				holder.tvName = (TextView) convertView
						.findViewById(R.id.tv_item_voice_name);
				convertView.setTag(holder);
			} else {
				convertView = View.inflate(context,
						R.layout.item_chat_voice_reply, null);

				holder.tvURL = (TextView) convertView
						.findViewById(R.id.tv_item_voice_url_reply);
				holder.tvDate = (TextView) convertView
						.findViewById(R.id.tv_item_voice_date_reply);
				holder.tvTime = (TextView) convertView
						.findViewById(R.id.tv_item_voice_time_reply);
				holder.length = convertView
						.findViewById(R.id.fl_item_voice_length_reply);
				holder.tvName = (TextView) convertView
						.findViewById(R.id.tv_item_voice_name_reply);
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();
			holder.tvName.setText(recorderVoice.getName());
			holder.tvDate.setText(recorderVoice.getDate());
			holder.tvTime.setText(Math.round(recorderVoice.getTime()) + "\"");
			ViewGroup.LayoutParams lp = holder.length.getLayoutParams();
			lp.width = (int) (mMinItemWith + (mMaxItemWith / 60f * recorderVoice
					.getTime()));

			// if(recorderVoice.getURLPath() == null){
			// holder.tvURL.setVisibility(View.GONE);
			// }else {
			// holder.tvURL.setVisibility(View.VISIBLE);
			// holder.tvURL.append(Html.fromHtml(recorderVoice.getURLPath()));
			// holder.tvURL.setMovementMethod(LinkMovementMethod.getInstance());
			// }
		}
		return convertView;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return recorders.size();
	}

	@Override
	public RecorderVoice getItem(int position) {
		// TODO Auto-generated method stub
		return recorders.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	class ViewHolder {
		TextView tvDate;
		View length;
		TextView tvName;
		TextView tvTime;
		TextView tvURL;
	}
}
