package com.zbin.Util;

import java.util.ArrayList;
import java.util.List;

import com.zbin.bean.Category_music_info;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class Category_music_Util {

	public int getcount(Context context) {
		Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
				null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		return cursor.getCount();

	}

	public Category_music_Util() {
		super();
	}

	public static List<Category_music_info> getMp3Infos(Context context) {
		Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
				null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

		List<Category_music_info> musicInfos = new ArrayList<Category_music_info>();
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToNext();
			Category_music_info musicInfo = new Category_music_info();
			long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID)); // ����id
			String title = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))); // ���ֱ���
			String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)); // ������
			String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)); // ר��
			String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
			long albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
			long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)); // ʱ��
			long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)); // �ļ���С
			String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)); // �ļ�·��
			int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC)); // �Ƿ�Ϊ����
			if (isMusic != 0) { // ֻ��������ӵ����ϵ���
				musicInfo.setId(id);
				musicInfo.setTitle(title);
				musicInfo.setArtist(artist);
				musicInfo.setAlbum(album);
				musicInfo.setDisplayName(displayName);
				musicInfo.setAlbumId(albumId);
				musicInfo.setDuration(duration);
				musicInfo.setSize(size);
				musicInfo.setUrl(url);
				musicInfos.add(musicInfo);
			}
		}
		cursor.close();
		return musicInfos;

	}
}
