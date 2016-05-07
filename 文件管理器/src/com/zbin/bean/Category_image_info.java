package com.zbin.bean;

import android.media.Image;

public class Category_image_info {
	public Category_image_info() {
		super();
	}
	public Category_image_info(long id, String title, Image image) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Category_image_info [id=" + id + ", title=" + title
				+ ", image=" + image + ", path=" + path + "]";
	}
	private long id; // ¸èÇúID
	private String title; // ¸èÇúÃû³Æ
	private Image image; // ¸èÇúÊ±³¤
	private String path; // ¸èÇúÊ±³¤
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	public Category_image_info(String path) {
		super();
		this.path = path;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	
}
