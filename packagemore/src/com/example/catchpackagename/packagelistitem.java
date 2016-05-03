package com.example.catchpackagename;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * private long id; // 视频ID private String title; // 视频名称 private String
 * mimeType; // 视频类型 private String filepath; // 视频路径 private String
 * thumbPath;// 视频缩略图
 * 
 * @author Administrator
 *
 */
public class packagelistitem {
	private long id; 
	private Drawable imageview; 
	private String packagename;
	@Override
	public String toString() {
		return "packagelistitem [id=" + id + ", imageview=" + imageview + ", packagename=" + packagename + "]";
	}
	public packagelistitem(long id, Drawable imageview, String packagename) {
		super();
		this.id = id;
		this.imageview = imageview;
		this.packagename = packagename;
	}
	
	public long getId() {
		return id;
	}
	public packagelistitem() {
		super();
	}
	public void setId(long id) {
		this.id = id;
	}
	public Drawable getImageview() {
		return imageview;
	}
	public void setImageview(Drawable imageview) {
		this.imageview = imageview;
	}
	public String getPackagename() {
		return packagename;
	}
	public void setPackagename(String packagename) {
		this.packagename = packagename;
	} 



}
