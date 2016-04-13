package com.zbin.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.R.string;

public class Log {
	private static BufferedWriter bW = null;
	private static String pathDir = "/mnt/sdcard/UILog";
	private static String logPath = pathDir + "/UITextLog/" + "Log.txt";
	private static String imageLogpath = pathDir + "/UIImageLog";
	private static String _host = "172.21.6.29";
	private static int _port = 9998;
	private static Socket client = null;
	private static Writer writer = null;
	private static String _logString;

	public static void logcat() {
		try {
			Runtime.getRuntime().exec("logcat >>mnt/sdcard/log.txt");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void creatLogFile() {
		try {
			Runtime.getRuntime().exec("mkdir -p " + pathDir + "/UITextLog/");
			Runtime.getRuntime().exec("mkdir -p " + imageLogpath);
			Runtime.getRuntime().exec("touch " + logPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void pass(String tag, String info) {
		System.out.println(tag);
		saveFile(tag, logPath);
		sendMMSSocket(tag);
	}

	public static void message(String tag, String info) {
		_logString = makeline(tag, info);
		System.out.println(_logString);
		saveFile(_logString, logPath);
		sendMMSSocket(_logString);
	}

	public static void error(String tag, String info) {
		_logString = makeline(tag, info);
		System.out.println(_logString);
		saveFile(_logString, logPath);
		sendMMSSocket(_logString);
	}

	public static void warning(String tag, String info) {
		_logString = makeline(tag, info);
		System.out.println(_logString);
		saveFile(_logString, logPath);
		sendMMSSocket(_logString);
	}

	public static void image(String tag, String info) {
		_logString = makeline(tag, "截图信息:" + info);
		System.out.println(_logString);
		saveFile(_logString, logPath);
		sendMMSSocket(_logString);
		ImageOperation.Screenshot(_logString.replace(":", "-").replaceAll("截图信息-", ""));
	}

	public static String makeline(String tag, String info) {
		return "[" + tag + "] " + "[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString()
				+ "] [" + info + "]";
	}

	public static BufferedWriter saveFile(String line, String path) {
		File file = new File(path);
		try {
			FileOutputStream stream = new FileOutputStream(file, true);
			OutputStreamWriter out = new OutputStreamWriter(stream);
			bW = new BufferedWriter(out);
			bW.append(line);
			bW.newLine();
			bW.flush();
		} catch (Exception e) {
		}
		return bW;
	}

	public static void sendMMSSocket(String mms) {
		Log.sendMMSSocket(mms, _host, _port);
	}

	public static void sendMMSSocket(String mms, String host, int port) {
		try {
			client = new Socket(host, port);
			writer = new OutputStreamWriter(client.getOutputStream());
			writer.write(mms);
			writer.flush();
			writer.close();
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
