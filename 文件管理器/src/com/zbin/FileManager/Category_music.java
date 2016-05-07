package com.zbin.FileManager;

import java.text.ParsePosition;
import java.util.List;

import com.example.FileManager.R;
import com.zbin.Util.Category_music_Util;
import com.zbin.bean.Category_music_info;
import com.zbin.service.MusicService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import myinterface.musicinterface;

public class Category_music extends Activity implements OnClickListener {
	private ListView listview;
	private List<Category_music_info> musicInfos = null;
	private Category_music_info musicInfo;
	private Category_music_Util util;
	private ImageButton stop, next, pre;
	private Button loop;
	private Boolean loop1 = true;
	private musicinterface musicservice;
	private SeekBar sb;
	private TextView musicname, currenttime, totaltime;
	private int currentpos = 0;

	private String TAG = "Category_music";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music_main);
		init();

		musicInfos = Category_music_Util.getMp3Infos(Category_music.this);
		listview.setAdapter(new musiclistadpter(Category_music.this, musicInfos));
		loop.setText("全部歌曲" + "(" + musicInfos.size() + ")");
		//
		Intent intent = new Intent(Category_music.this, MusicService.class);
		bindService(intent, conn, BIND_AUTO_CREATE);
		startService(intent);
	}

	public void init() {
		currenttime = (TextView) findViewById(R.id.textview_duration);
		totaltime = (TextView) findViewById(R.id.textview_current_time);
		musicname = (TextView) findViewById(R.id.isplaying_music_name);
		stop = (ImageButton) findViewById(R.id.button_play);
		loop = (Button) findViewById(R.id.loop);
		sb = (SeekBar) findViewById(R.id.seekbar);
		next = (ImageButton) findViewById(R.id.button_next_music);
		pre = (ImageButton) findViewById(R.id.button_previous_music);

		listview = (ListView) findViewById(R.id.MUSIC_LIST);

		listview.setOnItemClickListener(new ListClickitemIml());
		stop.setOnClickListener((OnClickListener) this);
		next.setOnClickListener(this);
		pre.setOnClickListener(this);

	}

	class musiclistadpter extends BaseAdapter {
		private List<Category_music_info> musicInfos = null;
		private Context context;

		public musiclistadpter(Context context, List<Category_music_info> musicInfos) {
			this.context = context;
			this.musicInfos = musicInfos;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return musicInfos.size();
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
				convertView = View.inflate(Category_music.this, R.layout.music_item, null);
				viewHolder.musicTitle = (TextView) convertView.findViewById(R.id.name);
				viewHolder.musicDuration = (TextView) convertView.findViewById(R.id.time);
				viewHolder.musicArtist = (TextView) convertView.findViewById(R.id.actor);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();// 通过getTag的方法将数据取出来
			}
			musicInfo = musicInfos.get(position);
			long time = musicInfo.getDuration();
			int dcurrenttimeMinute = (int) (time / 1000 / 60);
			int currenttimeSecond = (int) (time / 1000 % 60);

			viewHolder.musicTitle.setText(musicInfo.getTitle()); // 显示标题
			viewHolder.musicArtist.setText(musicInfo.getArtist()); // 显示艺术家
			viewHolder.musicDuration.setText(dcurrenttimeMinute + ":" + currenttimeSecond);// 显示时长

			return convertView;
		}

	}

	public class ViewHolder {
		// 所有控件对象引用
		public ImageView albumImage; // 专辑图片
		public TextView musicTitle; // 歌曲名称
		public TextView musicDuration; // 歌曲时长
		public TextView musicArtist; // 歌曲作者
	}

	public class ListClickitemIml implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			musicservice.dostart(position);
			musicInfo = musicInfos.get(position);
			long time = musicInfo.getDuration();
			int dcurrenttimeMinute = (int) (time / 1000 / 60);
			int currenttimeSecond = (int) (time / 1000 % 60);
			/*
			 * musicInfo = musicInfos.get(position); Log.d("music",
			 * musicInfo.getTitle());
			 */
			stop.setBackgroundResource(R.drawable.img_lockscreen_pause_normal);
			musicname.setText("当前歌曲：" + "\r\n" + musicInfo.getTitle());
			totaltime.setText(dcurrenttimeMinute + ":" + currenttimeSecond);
			currentpos = position;
		}

	}

	ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			musicservice = (musicinterface) service;
			musicservice.setList(musicInfos);
			musicservice.setOnProgressListener(new OnProgresslistener() {

				@Override
				public void process(int progress) {

					sb.setProgress(progress);
					// currenttime.setText(progress);

				}

				@Override
				public void processStart(int total) {
					sb.setMax(total);

				}
			});
		}
	};
	boolean mIsPause = false;// 暂停状态

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_play:
			if (mIsPause) {
				musicservice.doresume();
				stop.setBackgroundResource(R.drawable.img_lockscreen_pause_normal);
				mIsPause = false;

			} else {
				musicservice.dopause();
				stop.setBackgroundResource(R.drawable.img_lockscreen_play_normal);
				mIsPause = true;

			}
			musicInfo = musicInfos.get(currentpos);
			Log.d(TAG, musicInfo.getTitle());
			break;
		case R.id.button_next_music:
			musicservice.donext();
			musicInfo = musicInfos.get(currentpos + 1);
			long time = musicInfo.getDuration();
			int dcurrenttimeMinute = (int) (time / 1000 / 60);
			int currenttimeSecond = (int) (time / 1000 % 60);
			musicname.setText("当前歌曲：" + "\r\n" + musicInfo.getTitle());
			totaltime.setText(dcurrenttimeMinute + ":" + currenttimeSecond);
			stop.setBackgroundResource(R.drawable.img_lockscreen_pause_normal);
			currentpos++;
			break;
		case R.id.button_previous_music:
			musicservice.doppre();
			musicInfo = musicInfos.get(currentpos - 1);
			long time1 = musicInfo.getDuration();
			int dcurrenttimeMinute1 = (int) (time1 / 1000 / 60);
			int currenttimeSecond1 = (int) (time1 / 1000 % 60);
			musicname.setText("当前歌曲：" + "\r\n" + musicInfo.getTitle());
			totaltime.setText(dcurrenttimeMinute1 + ":" + currenttimeSecond1);
			stop.setBackgroundResource(R.drawable.img_lockscreen_pause_normal);
			currentpos--;
			break;
		default:
			break;
		}

	}

	/*
	 * 设置开始（暂停按钮的图片）
	 */
	private void setPlayOrPauseButtonImage() {
		if (!mIsPause) {
			stop.setBackgroundResource(R.drawable.img_lockscreen_pause_normal);
		} else if (mIsPause) {
			stop.setBackgroundResource(R.drawable.img_lockscreen_play_normal);
		}
	}

	public interface OnProgresslistener {
		void process(int progress);

		void processStart(int total);
	}
}
