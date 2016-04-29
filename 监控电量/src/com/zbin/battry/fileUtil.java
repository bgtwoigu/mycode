package com.zbin.battry;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

public class fileUtil {
	/**
	 * filename:文件名稱
	 * 
	 * @param filename
	 * @param content
	 */
	public void saveToSDCard(String filename, String info) {
		FileOutputStream fos = null;
		try {
			// Environment.getExternalStorageDirectory()。获取sd卡的路径
			File file = new File(Environment.getExternalStorageDirectory(), filename);
			if (!file.exists())
				file.createNewFile();
			Log.e("fileutil", file.toString());
			

			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			// info = " hey, yoo,bitch";
			bw.write(info);
			bw.write("\r\n");
			bw.flush();
			bw.close();
			Log.e("fileutil", "写入文件---");
			/*
			 * fos = new FileOutputStream(file); info = "##毛##";
			 * fos.write(info.getBytes()); // fos.write("\r\n"); fos.flush();
			 */
			System.out.println("写入成功：");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * //将SD卡文件删除 public static void deleteFile(File file) {
	 * if(sdState.equals(Environment.MEDIA_MOUNTED)) { if (file.exists()) { if
	 * (file.isFile()) { file.delete(); } // 如果它是一个目录 else if
	 * (file.isDirectory()) { // 声明目录下所有的文件 files[]; File files[] =
	 * file.listFiles(); for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
	 * deleteFile(files[i]); // 把每个文件 用这个方法进行迭代 } } file.delete(); } } }
	 */
}

/*
 * public void saveToRom(String name, String content) { // TODO Auto-generated
 * method stub
 * 
 * } public void fileToRom(String name, String content) { try { File file = new
 * File(Environment.getExternalStorageDirectory(), "test.txt"); FileInputStream
 * is = new FileInputStream(file); byte[] b = new byte[inputStream.available()];
 * is.read(b); String result = new String(b);
 * System.out.println("读取成功："+result); } catch (Exception e) {
 * e.printStackTrace(); }
 */
