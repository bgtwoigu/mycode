package com.example.catchpackagename;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.catchpackagename.util;
import com.example.getpackagename.R;;

public class MainActivity extends Activity {
	private ListView listview;
	private List<packagelistitem> packagelistitems = null;
	private packagelistitem item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		listview = (ListView) findViewById(R.id.list);
		packagelistitems=util.getinfo(MainActivity.this);
		Log.e("zbin",  packagelistitems.toString());
		Log.e("zbin",  "heheh");
		listview.setAdapter(new myadapter(MainActivity.this,packagelistitems));

	}
	

	class myadapter extends BaseAdapter {
		private List<packagelistitem> packagelistitems = null;
		
		private Context context;

		public myadapter(Context context, List<packagelistitem> packagelistitems) {
			this.context = context;
			this.packagelistitems = packagelistitems;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = View.inflate(MainActivity.this, R.layout.list_item, null);
			//	viewHolder.imageView = (ImageView) convertView.findViewById(R.id.icon);
				viewHolder.textView = (TextView) convertView.findViewById(R.id.packagename);
				//viewHolder.musicArtist = (TextView) convertView.findViewById(R.id.actor);*/
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();// 通过getTag的方法将数据取出来
			}
			item = packagelistitems.get(position);
			viewHolder.textView.setText(item.getPackagename()); // 显示标题
			//viewHolder.imageView.setImageDrawable(item.getImageview()); 
			
			return convertView;
		}

	}
	
	class ViewHolder{
		ImageView imageView;
		TextView textView;
		
	}

}
