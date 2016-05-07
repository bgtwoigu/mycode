package com.zbin.bean;

public class Category_music_info {
	private long id; // 歌曲ID
	private String title; // 歌曲名称
	private String artist; // 歌手名称
	private long duration; // 歌曲时长

	private String album; // 专辑
	private long albumId;// 专辑ID
	private String displayName; // 显示名称
	private long size; // 歌曲大小
	private String url; // 歌曲路径
	private String lrcTitle; // 歌词名称
	private String lrcSize; // 歌词大小

	@Override
	public String toString() {
		return "Category_music_info [id=" + id + ", title=" + title + ", artist=" + artist + ", duration=" + duration
				+ ", album=" + album + ", albumId=" + albumId + ", displayName=" + displayName + ", size=" + size
				+ ", url=" + url + ", lrcTitle=" + lrcTitle + ", lrcSize=" + lrcSize + "]";
	}

	public Category_music_info() {
		super();
	}

	public Category_music_info(long id, String title, String artist, long duration, String album, long albumId,
			String displayName, long size, String url, String lrcTitle, String lrcSize) {
		super();
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.duration = duration;
		this.album = album;
		this.albumId = albumId;
		this.displayName = displayName;
		this.size = size;
		this.url = url;
		this.lrcTitle = lrcTitle;
		this.lrcSize = lrcSize;
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

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(long albumId) {
		this.albumId = albumId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLrcTitle() {
		return lrcTitle;
	}

	public void setLrcTitle(String lrcTitle) {
		this.lrcTitle = lrcTitle;
	}

	public String getLrcSize() {
		return lrcSize;
	}

	public void setLrcSize(String lrcSize) {
		this.lrcSize = lrcSize;
	}
}
