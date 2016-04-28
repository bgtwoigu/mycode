package com.zbin.testphone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class LOGIN extends Activity {

	private GridView gridview;
	private String[] name = { "自动拨打电话测试", "黑名单", "自动接听电话测试", "添加短信", "Monkey Test", "高级设置" };
	private int[] icons = { R.drawable.phone, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher };

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		new AlertDialog.Builder(LOGIN.this).setTitle("提示").setMessage("是否确认退出程序？").setCancelable(false)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						LOGIN.this.finish();
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

					}
				});

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		gridview = (GridView) findViewById(R.id.home);
		gridview.setAdapter(new gridviewadpter());
		gridview.setOnItemClickListener(new OnItemClickListener() {
			Intent intent;

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {

				case 0:
					intent = new Intent(LOGIN.this, callTest.class);
					startActivity(intent);
					break;

				case 1:
					intent = new Intent(LOGIN.this, blackhome.class);
					startActivity(intent);
					break;

				case 2:
					intent = new Intent(LOGIN.this, incoming.class);
					startActivity(intent);
					break;
				case 3:
					intent = new Intent(LOGIN.this, autoSMS.class);
					startActivity(intent);
					break;
				case 4:
					intent = new Intent(LOGIN.this, monkeyTest.class);
					startActivity(intent);
					break;
				case 5:
					intent = new Intent(LOGIN.this, advanceSetting.class);
					startActivity(intent);
					break;

				}
			}
		});

	}

	class gridviewadpter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return name.length;
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
			View view = View.inflate(LOGIN.this, R.layout.listview_item, null);
			TextView tv_name = (TextView) view.findViewById(R.id.tv_home_item);
			ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_home_item);
			tv_name.setText(name[position]);
			iv_icon.setImageResource(icons[position]);
			return view;
		}

	}

}
