package com.example.getpackagename;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * zhangbin
 *
 */
public class ListViewAdapter extends BaseAdapter {
	Context context;
	private List<AppInfo> list;

	public ListViewAdapter(Context context) {
		this.context = context;
	}

	public List<AppInfo> getList() {
		return list;
	}

	public void setList(List<AppInfo> list) {
		this.list = list;
	}

	class viewHolder {
		TextView nameTextView;
		TextView sizeTextView;
		TextView timeTextView;
		TextView compackagename;
		ImageView iconImageView;
	}

	@Override
	public int getCount() {
		if (list != null) {
			return list.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		viewHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.listview_item, null);
			holder = new viewHolder();
			holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
			holder.nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
			holder.sizeTextView = (TextView) convertView.findViewById(R.id.sizeTextView);
			holder.timeTextView = (TextView) convertView.findViewById(R.id.timeTextView);
			holder.compackagename = (TextView) convertView.findViewById(R.id.compackagename);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}

		AppInfo appInfo = list.get(position);
		holder.nameTextView.setText(appInfo.getAppName());
		holder.sizeTextView.setText(appInfo.getAppSize());
		holder.timeTextView.setText(appInfo.getAppTime());
		holder.compackagename.setText(appInfo.getPackagename());
		holder.iconImageView.setImageDrawable(appInfo.getAppIcon());
		return convertView;
	}

}
