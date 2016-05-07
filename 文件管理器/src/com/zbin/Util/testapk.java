/*package com.zbin.Util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;

public class testapk {
	 public ContentResolver cr;  
	   public Cursor cur;  
	   public Context context;  
	   public testapk(){
		   this.context=context;
		   this.cur=cur;
		   this.cr=cr;
		   cr = context.getContentResolver(); 
		   cur = cr.query(Environment, null,  
	                null, null, null);  
	  
	        if (cur != null) {  
	            // 移动游标到第一个  
	            cur.moveToFirst();  
	            int j = 1;  
	            //遍历所有查询到的记录  
	            for (int i = 0; i < cur.getCount(); i++) {  
	                if (cur.getString(0).endsWith(".mp3")) {// 过滤获取MP3文件  
	                    MusicInfo mInfo = new MusicInfo();  
	                    String musicName = cur.getString(0).substring(0,  
	                            cur.getString(0).lastIndexOf(".mp3"));  
	                    mInfo.setMusicIndex(j++);  
	                    mInfo.setMusicName(musicName);  
	                    mInfo.setMusicAlubm(cur.getString(1));  
	                    mInfo.setMusicSinger(cur.getString(2));  
	                    mInfo.setMusicTime(cur.getInt(3));  
	                    mInfo.setMusicSize(cur.getInt(4));  
	                    mInfo.setMusicId(cur.getInt(5));  
	                    mInfo.setMusicPath(cur.getString(6));  
	                    musicList.add(mInfo);  
	                }  
	                cur.moveToNext();  
	            }  
	  
	        }  
		   
	   }
	  
}
*/