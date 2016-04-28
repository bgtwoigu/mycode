package com.zbin.bean;

import android.graphics.drawable.Drawable;

public class packagebean {
	@Override
	public String toString() {
		return "blacknumberbean [packicon=" + packicon + ", packname=" + packname + ", appname=" + appname + "]";
	}
	private Drawable packicon;
	private String packname;
	private String appname;
public Drawable getPackicon() {
		return packicon;
	}
	public void setPackicon(Drawable packicon) {
		this.packicon = packicon;
	}
	public String getPackname() {
		return packname;
	}
	public void setPackname(String packname) {
		this.packname = packname;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
}
