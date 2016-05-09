package com.zbin.Util;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class NotifictionCustomer extends Activity {
	// ���ļ�
	private void openFile(File file) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);

		String type = getMIMEType(file);
		intent.setDataAndType(Uri.fromFile(file), type);
		startActivity(intent);
	}

	// ��ȡ�ļ�mimetype
	private String getMIMEType(File file) {
		String type = "";
		String name = file.getName();
		// �ļ���չ��
		String end = name.substring(name.lastIndexOf(".") + 1, name.length()).toLowerCase();
		if (end.equals("m4a") || end.equals("mp3") || end.equals("wav")) {
			type = "audio";
		} else if (end.equals("mp4") || end.equals("3gp")) {
			type = "video";
		} else if (end.equals("jpg") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")
				|| end.equals("gif")) {
			type = "image";
		} else {
			// ����޷�ֱ�Ӵ򿪣������б����û�ѡ��
			type = "*";
		}
		type += "/*";
		return type;
	}

	private void displayToast(String message, int time) {
		Toast.makeText(NotifictionCustomer.this, message, time).show();
	}
}
