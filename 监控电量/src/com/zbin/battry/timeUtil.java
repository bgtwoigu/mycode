package com.zbin.battry;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

public class timeUtil {
	

	@SuppressLint("SimpleDateFormat")
	public String currentTime() {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy��MM��dd��    HH:mm:ss     ");
		Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
		String str = formatter.format(curDate);
		return str;
	}
}
