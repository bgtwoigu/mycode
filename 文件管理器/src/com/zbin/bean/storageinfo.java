package com.zbin.bean;

public class storageinfo {
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public storageinfo(String name, String path) {
		super();
		this.name = name;
		this.path = path;
	}

	public storageinfo() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "storageinfo [name=" + name + ", path=" + path + "]";
	}

	private String path;
}
