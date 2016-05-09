/*package com.zbin.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import myinterface.musicinterface;

public class muiscServive2 extends Service {
	private MediaPlayer mp;
	private String path;
	private boolean f = false;

	@Override
	public IBinder onBind(Intent intent) {
		path = intent.getStringExtra("path");
		System.out.println(path);
		// return new MyBinder();
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("onCreate");
		// 播放音乐
		try {
			// 得到媒体播放器
			mp = new MediaPlayer();

			// mp.pause();
			// mp.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		System.out.println("onStart");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("onDestroy");

	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("onUnbind");
		return super.onUnbind(intent);
	}

	// 暂停
	public void pause() {
		mp.pause();
	}

	// 继续
	public void resume() {
		mp.start();
	}

	class MyBinder extends Binder implements musicinterface {
		// MyService getService(){
		// return MyService.this;
		// }
		private SeekBar seekbar;
		Handler handle = new Handler() {

		};
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				// 根新进度条
				if (mp.isPlaying()) {
					seekbar.setProgress(mp.getCurrentPosition());

				}
				handle.postDelayed(runnable, 500);
			}
		};

		public void dopause() {
			// 调用的本地MyService的暂停
			f = false;
			pause();
		}

		public void doresume() {
			resume();
		}

		public void dorevmo() {
			handle.removeCallbacks(runnable);
		}

		public void init(SeekBar seekbar, final onMusicOver over) {
			System.out.println("*********init******");
			this.seekbar = seekbar;
			// 重置
			mp.reset();

			// 设置播放资源
			try {
				mp.setDataSource(path);
				mp.prepare();
				seekbar.setMax(mp.getDuration());
				// 设置拖动进度条改变的时候的监听方法
				seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// fromUser 是否是用户操作

						if (fromUser) {
							mp.seekTo(progress);

						}
					}
				});
				// 设置进度
				mp.start();
				f = true;
				handle.post(runnable);
				mp.setOnCompletionListener(new OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						over.onMusicOver();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void dostart() {
			try {
				mp.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
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

	}

}
*/