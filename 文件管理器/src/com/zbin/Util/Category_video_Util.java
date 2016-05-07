package com.zbin.Util;

import java.util.ArrayList;
import java.util.List;

import com.zbin.bean.Category_video_info;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class Category_video_Util {

	static List<Category_video_info> videoInfos;
	static Category_video_info videoInfo;

	public int getcount(Context context) {
		Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null,
				null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
		return cursor.getCount();

	}

	public static List<Category_video_info> getvideoInfos(Context context) {
		String[] thumbColumns = new String[] { MediaStore.Video.Thumbnails.DATA, MediaStore.Video.Thumbnails.VIDEO_ID };

		String[] mediaColumns = new String[] { MediaStore.Video.Media.DATA, MediaStore.Video.Media._ID,
				MediaStore.Video.Media.TITLE, MediaStore.Video.Media.MIME_TYPE };

		Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns,
				null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
		videoInfos = new ArrayList<Category_video_info>();

		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToNext();
			videoInfo = new Category_video_info();
			long ID = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
			String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
			String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
			String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
			videoInfo.setFilepath(filePath);
			videoInfo.setMimeType(mimeType);
			videoInfo.setTitle(title);
			videoInfo.setId(ID);
			videoInfos.add(videoInfo);
		}
		cursor.close();
		return videoInfos;
	}

}
