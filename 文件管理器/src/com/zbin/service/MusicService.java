package com.zbin.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.SeekBar;

import com.zbin.FileManager.Category_music.OnProgresslistener;
import com.zbin.bean.Category_music_info;
import com.zbin.myinterface.MusicInterface;

public class MusicService extends Service {

	private static final String TAG = "musicsevice";
	private MediaPlayer mp;
	// private boolean f = false;// ÔÝÍ£

	private OnProgresslistener listener;

	@Override
	public IBinder onBind(Intent intent) {

		return new MyBinder();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	List<Category_music_info> mPlayList;
	int mPos = 0;

	private void setList(List<Category_music_info> playList) {
		mPlayList = playList;
	}

	private void start(int pos) {
		mPos = pos;
		Uri url = Uri.parse(mPlayList.get(pos).getUrl());
		Log.e(TAG, "url" + url.toString());
		Log.d(TAG, "pos " + pos);
		if (!new File(url.toString()).exists()) {
			Log.e(TAG, url.toString() + " is not exist");
			return;
		}
		try {
			mp.reset();
			mp.setDataSource(url.toString());
			// mp.prepareAsync();
			mp.prepare();
			listener.processStart(mp.getDuration());
			mp.start();
			Timer mTimer = new Timer();
			TimerTask mTimerTask = new TimerTask() {
				@Override
				public void run() {
					listener.process(MusicService.this.mp.getCurrentPosition());
					// Log.d(TAG, MusicService.this.mp.getCurrentPosition() +
					// "");

				}
			};
			mTimer.schedule(mTimerTask, 0, 10);

			/*
			 * new Thread() {
			 * 
			 * @Override public void run() { // TODO Auto-generated method stub
			 * super.run();
			 * 
			 * } }.start();
			 */
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void next() {
		start((mPos + 1) % mPlayList.size());
	}

	private void next1() {
		start((mPos) % mPlayList.size());
	}

	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("onCreate");
		mp = new MediaPlayer();

		mp.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) { //
				// mp.stop();
				// mp.release();
				// next();

				Log.d(TAG, "[onCompletion] mediaplayer play copletion");
			}
		});

	}

	@Override
	public void onDestroy() {
		mp.release();
		mp = null;
		super.onDestroy();
		System.out.println("onDestroy");

	}

	@Override
	public boolean onUnbind(Intent intent) {
		System.out.println("onUnbind");
		return super.onUnbind(intent);
	}

	class MyBinder extends Binder implements MusicInterface {
		private SeekBar seekbar;

		@Override
		public MediaPlayer getmediaplayer() {
			// TODO Auto-generated method stub
			return MusicService.this.mp;
		}

		@Override
		public boolean ismedianull() {
			// TODO Auto-generated method stub
			return mp.equals(null);
		}

		@Override
		public boolean isplaying() {

			return mp.isPlaying();
		}

		@Override
		public List<Category_music_info> getList(
				List<Category_music_info> playList) {

			return playList;
		}

		public void dopause() {
			// f = false;
			mp.pause();
		}

		public void doresume() {
			mp.start();
		}

		public void dorevmo() {
		}

		public void dostart(int pos) {
			start(pos);
		}

		public void dostop() {
			// TODO Auto-generated method stub
			if (mp != null && mp.isPlaying()) {
				mp.stop();
				mp.release();
				mp = null;
			}
		}

		public void doRset() {
			mp.release();
		}

		@Override
		public void doppre() {

			if (mPos == 0) {
				// start((mPos + 1) % mPlayList.size());
				start(mPlayList.size() - 1);
			} else {

				start((mPos - 1) % mPlayList.size());
			}
		}

		@Override
		public void donext() {
			next();

		}

		@Override
		public void setList(List<Category_music_info> playList) {
			MusicService.this.setList(playList);
		}

		@Override
		public void setOnProgressListener(OnProgresslistener listener) {
			MusicService.this.listener = listener;

		}

		@Override
		public void getOnProgressListener(OnProgresslistener listener) {
			MusicService.this.listener = listener;

		}

	}

}
