package com.example.catchpackagename;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class util {
	public static List<packagelistitem> getinfo(Context context){
		PackageManager pm = context.getPackageManager();
		List<packagelistitem> appinfos = new ArrayList<packagelistitem>();
		List<PackageInfo> packinfos = pm.getInstalledPackages(0);
		for(PackageInfo packinfo : packinfos){
			packagelistitem item=new packagelistitem();
			String packagename = packinfo.packageName;
			item.setPackagename(packagename);
			appinfos.add(item);
		}
		return appinfos;
		
		
	}
	
}
