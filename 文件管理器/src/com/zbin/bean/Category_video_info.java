package com.zbin.bean;

/**
 * private long id; // 视频ID private String title; // 视频名称 private String
 * mimeType; // 视频类型 private String filepath; // 视频路径 private String
 * thumbPath;// 视频缩略图
 * 
 * @author Administrator
 *
 */
public class Category_video_info {
	private long id; // 视频ID
	private String title; // 视频名称
	private String mimeType; // 视频类型
	private String filepath; // 视频路径
	private String thumbPath;// 视频缩略图

	public Category_video_info() {
		super();
	}

	public Category_video_info(long id, String title, String mimeType, String filepath, String thumbPath) {
		super();
		this.id = id;
		this.title = title;
		this.mimeType = mimeType;
		this.filepath = filepath;
		this.thumbPath = thumbPath;
	}

	@Override
	public String toString() {
		return "Category_video_info [id=" + id + ", title=" + title + ", mimeType=" + mimeType + ", filepath="
				+ filepath + ", thumbPath=" + thumbPath + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}

}
