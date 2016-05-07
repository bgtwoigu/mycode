package com.zbin.FileManager;

import java.util.List;

import com.example.FileManager.R;
import com.zbin.Util.Category_video_Util;
import com.zbin.bean.Category_video_info;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Category_video extends Activity {
	private ListView listview;
	private List<Category_video_info> videoInfos = null;

	private Category_video_info videoInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_main);

		listview = (ListView) findViewById(R.id.video_LIST);
		videoInfos = Category_video_Util.getvideoInfos(Category_video.this);
		listview.setAdapter(new videolistadpter(Category_video.this, videoInfos));
	}

	class videolistadpter extends BaseAdapter {
		private List<Category_video_info> videoInfos = null;
		private Context context;

		public videolistadpter(Context context, List<Category_video_info> videoInfos) {
			this.context = context;
			this.videoInfos = videoInfos;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return videoInfos.size();
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
				convertView = View.inflate(Category_video.this, R.layout.video_item, null);
				viewHolder.videoTitle = (TextView) convertView.findViewById(R.id.name);
				/*viewHolder.musicDuration = (TextView) convertView.findViewById(R.id.time);
				viewHolder.musicArtist = (TextView) convertView.findViewById(R.id.actor);*/
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();// 通过getTag的方法将数据取出来
			}
			videoInfo = videoInfos.get(position);
			viewHolder.videoTitle.setText(videoInfo.getTitle()); // 显示标题
			
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					videoInfo = videoInfos.get(position);
					Intent intent=new Intent();
					//intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			        intent.setAction(android.content.Intent.ACTION_VIEW);
			        intent.setDataAndType(Uri.parse(videoInfo.getFilepath()), "video/*");
			       // intent.setType("video/*");
			     //   intent.setData(Uri.parse(videoInfo.getFilepath()));
			        startActivity(intent);
			        
					
				}
			});
			// viewHolder.musicDuration.setText(MusicUtil.
			// (musicInfo.getDuration()));//显示时长
			return convertView;
		}

	}

	public class ViewHolder {
		// 所有控件对象引用
		public ImageView albumImage; // 
		public TextView videoTitle; // 
		public TextView videoDuration; // 
		public TextView videoArtist; // 
	}

}
