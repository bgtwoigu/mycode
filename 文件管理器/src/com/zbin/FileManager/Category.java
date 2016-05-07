package com.zbin.FileManager;

import java.text.DecimalFormat;

import com.example.FileManager.R;
import com.zbin.Util.Category_music_Util;
import com.zbin.Util.MemoryInfo;
import com.zbin.Util.PhoneMemory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Category extends Activity {
	private long mExitTime = 0;
	private GridView gridview;
	private TextView textViewToal, textViewLeft, more;
	private Category_music_Util category_music_Util;

	private DecimalFormat fomart;
	private int count = 0;
	MemoryInfo mm = new MemoryInfo();
	long mm1 = mm.getTotalMemory() / 1024 / 1024;
	long mm2 = mm.getTotalMemory() % 1024 % 1024;
	PhoneMemory phonememory = new PhoneMemory();
	long toalrom = phonememory.getRomSpace();
	long aviliblerom = phonememory.getRomavailableSpace() / 1024 / 1024;
	long aviliblerom1 = phonememory.getRomavailableSpace() / 1024 / 1024;

	// String totalMemory=formatFileSize(mm1)+"";

	// String
	// leftMemory=Long.toString(mm.getFreeMemorySize(getBaseContext())/1024)+"G";
	// long mm1=mm.getTotalMemory();
	// String totalMemory = fomart.format((double) mm1 / 1024);

	private String[] name = { "图片", "音乐", "视频", "文档", "压缩包", "安装包" };
	private int[] icons = { R.drawable.image_icon_bg, R.drawable.file_icon_mp3, R.drawable.category_icon_video,
			R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		more = (TextView) findViewById(R.id.more);
		more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Category.this, Category_more.class);
				startActivity(intent);

			}
		});
		gridview = (GridView) findViewById(R.id.caegory);
		textViewToal = (TextView) findViewById(R.id.Totals);
	
		textViewLeft = (TextView) findViewById(R.id.Lefts);
		gridview.setAdapter(new gridviewadpter());
		textViewToal.setText(formatFileSize(toalrom) + " ");// String.valueOf(mm1)formatFileSize(mm1)+"
		// "
// textViewToal.setText(totalMemory);
		// textViewLeft.setText(String.valueOf(aviliblerom)+"."+String.valueOf(aviliblerom1)+"");
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent;
				switch (position) {

				case 0:
					intent = new Intent(Category.this, Category_images.class);
					startActivity(intent);
					break;

				case 1:
					intent = new Intent(Category.this, Category_music.class);
					startActivity(intent);
					break;

				case 2:
					intent = new Intent(Category.this, Category_video.class);
					startActivity(intent);
					break;

				case 3:
					intent = new Intent(Category.this, Category_phoneStorage.class);
					startActivity(intent);
					break;

				case 4:
					break;
				case 5:
					break;

				}

			}
		});

	}

	class gridviewadpter extends BaseAdapter {

		@Override
		public int getCount() {

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
			View view = View.inflate(Category.this, R.layout.category_item, null);
			TextView tv_name = (TextView) view.findViewById(R.id.tv_music_text);
			TextView tv_count = (TextView) view.findViewById(R.id.tv_music_count);
			ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_music_icon);
			tv_name.setText(name[position]);
			iv_icon.setImageResource(icons[position]);
			// tv_count.setText(position);
			return view;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - mExitTime > 2000) {
				Toast.makeText(this, "请再按一次退出程序", 0).show();
				mExitTime = System.currentTimeMillis();
			} else {
				finish();
				// System.exit(0);// 否则退出程序
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/*
	 * *字符串转换 long-string KB/MB
	 */
	private String formatFileSize(long number) {
		return Formatter.formatFileSize(Category.this, number);
	}
}
