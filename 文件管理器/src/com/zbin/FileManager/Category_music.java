package com.zbin.FileManager;

import java.util.List;

import android.app.Activity;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
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

import com.example.FileManager.R;
import com.zbin.Util.Category_music_Util;
import com.zbin.bean.Category_music_info;
import com.zbin.myinterface.MusicInterface;
import com.zbin.service.MusicService;

public class Category_music extends Activity implements OnClickListener {
	private ListView listview;
	private List<Category_music_info> musicInfos = null;
	private Category_music_info musicInfo;
	private Category_music_Util util;
	private ImageButton stop, next, pre, music_alumb;
	private Button loop;
	private Boolean loop1 = true;
	private MusicInterface musicservice;
	private SeekBar sb;
	private TextView musicname, currenttime, totaltime;
	private int currentpos = 0;
	boolean mIsPause = true;// 暂停状态
	private String TAG = "Category_music";
	private MediaPlayer mediaPlayer;

	// 处理进度条更新
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				// 更新进度
				/*
				 * int position = mediaPlayer.getCurrentPosition();
				 * 
				 * int time = mediaPlayer.getDuration(); int max = sb.getMax();
				 * 
				 * sb.setProgress(position * max / time);
				 */
				long time = musicservice.getmediaplayer().getCurrentPosition();
				Log.d(TAG, time + "");
				int dcurrenttimeMinute = (int) (time / 1000 / 60);
				int currenttimeSecond = (int) (time / 1000 % 60);
				currenttime.setText(dcurrenttimeMinute + ":"
						+ currenttimeSecond);
				break;
			default:
				break;
			}

		}
	};

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
		Notification notify2;
		music_alumb = (ImageButton) findViewById(R.id.music_album);
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

		public musiclistadpter(Context context,
				List<Category_music_info> musicInfos) {
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
				convertView = View.inflate(Category_music.this,
						R.layout.music_item, null);
				viewHolder.musicTitle = (TextView) convertView
						.findViewById(R.id.name);
				viewHolder.musicDuration = (TextView) convertView
						.findViewById(R.id.time);
				viewHolder.musicArtist = (TextView) convertView
						.findViewById(R.id.actor);
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
			viewHolder.musicDuration.setText(dcurrenttimeMinute + ":"
					+ currenttimeSecond);// 显示时长

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
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			musicservice.dostart(position);
			musicInfo = musicInfos.get(position);
			long time = musicInfo.getDuration();

			// int time1 = mediaPlayer.getCurrentPosition();
			int dcurrenttimeMinute = (int) (time / 1000 / 60);
			int currenttimeSecond = (int) (time / 1000 % 60);

			/*
			 * musicInfo = musicInfos.get(position); Log.d("music",
			 * musicInfo.getTitle());
			 */
			mIsPause = false;
			stop.setBackgroundResource(R.drawable.img_lockscreen_pause_normal);

			music_alumb.setImageResource(R.drawable.ic_launcher);
			musicname.setText("当前歌曲：" + "\r\n" + musicInfo.getTitle());
			totaltime.setText(dcurrenttimeMinute + ":" + currenttimeSecond);

			currentpos = position;
			Log.d(TAG, musicservice.isplaying() + "onitemclick");
			// Thread.sleep(currenttimeSecond);

		}
	}

	ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			musicservice = (MusicInterface) service;
			musicservice.setList(musicInfos);
			mHandler.sendEmptyMessage(0);

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_play:
			if (!musicservice.isplaying() && !musicservice.ismedianull()) {
				// Log.e(TAG,
				// musicservice.getmp(mediaPlayer).isPlaying()+"	musicservice.getmp(mediaPlayer).isPlaying();");

				// 发送通知
				/*
				 * NotificationManager manager = (NotificationManager)
				 * getSystemService(Context.NOTIFICATION_SERVICE); notify2 = new
				 * Notification.Builder(this) .setTicker("TickerText:" +
				 * "您有新短消息，请注意查收！")// 设置在status // bar上显示的提示文字
				 * .setContentTitle("Notification Title")// 设置在下拉status //
				 * bar后Activity，本例子中的NotififyMessage的TextView中显示的标题
				 * .setContentText("This is the notification message")//
				 * TextView中显示的详细内容 .getNotification(); // 需要注意build()是在API
				 * level // 16及之后增加的，在API11中可以使用getNotificatin()来代替
				 * manager.notify(NOTIFICATION_FLAG, notify2);
				 */

				// Log.e(TAG, musicservice.isplaying() + "play");
				musicservice.doresume();
				stop.setBackgroundResource(R.drawable.img_lockscreen_pause_normal);
				mIsPause = false;
			} else {
				musicservice.dopause();
				stop.setBackgroundResource(R.drawable.img_lockscreen_play_normal);
				mIsPause = true;
				Log.d(TAG, musicservice.isplaying() + "pasuse");

			}
			musicInfo = musicInfos.get(currentpos);
			Log.d(TAG, musicInfo.getTitle());
			break;
		case R.id.button_next_music:
			musicservice.donext();
			Log.d(TAG, musicservice.isplaying() + "=next");
			musicInfo = musicInfos.get(currentpos + 1);
			long time = musicInfo.getDuration();
			int dcurrenttimeMinute = (int) (time / 1000 / 60);
			int currenttimeSecond = (int) (time / 1000 % 60);
			musicname.setText("当前歌曲：" + "\r\n" + musicInfo.getTitle());
			totaltime.setText(dcurrenttimeMinute + ":" + currenttimeSecond);
			stop.setBackgroundResource(R.drawable.img_lockscreen_pause_normal);
			currentpos++;
			mIsPause = false;
			break;
		case R.id.button_previous_music:
			musicservice.doppre();
			Log.d(TAG, musicservice.isplaying() + "=prev");
			musicInfo = musicInfos.get(currentpos - 1);
			long time1 = musicInfo.getDuration();
			int dcurrenttimeMinute1 = (int) (time1 / 1000 / 60);
			int currenttimeSecond1 = (int) (time1 / 1000 % 60);
			musicname.setText("当前歌曲：" + "\r\n" + musicInfo.getTitle());
			totaltime.setText(dcurrenttimeMinute1 + ":" + currenttimeSecond1);
			stop.setBackgroundResource(R.drawable.img_lockscreen_pause_normal);
			currentpos--;
			mIsPause = false;
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
