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
	 * filename:�ļ����Q
	 * 
	 * @param filename
	 * @param content
	 */
	public void saveToSDCard(String filename, String info) {
		FileOutputStream fos = null;
		try {
			// Environment.getExternalStorageDirectory()����ȡsd����·��
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
			Log.e("fileutil", "д���ļ�---");
			/*
			 * fos = new FileOutputStream(file); info = "##ë##";
			 * fos.write(info.getBytes()); // fos.write("\r\n"); fos.flush();
			 */
			System.out.println("д��ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * //��SD���ļ�ɾ�� public static void deleteFile(File file) {
	 * if(sdState.equals(Environment.MEDIA_MOUNTED)) { if (file.exists()) { if
	 * (file.isFile()) { file.delete(); } // �������һ��Ŀ¼ else if
	 * (file.isDirectory()) { // ����Ŀ¼�����е��ļ� files[]; File files[] =
	 * file.listFiles(); for (int i = 0; i < files.length; i++) { // ����Ŀ¼�����е��ļ�
	 * deleteFile(files[i]); // ��ÿ���ļ� ������������е��� } } file.delete(); } } }
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
 * System.out.println("��ȡ�ɹ���"+result); } catch (Exception e) {
 * e.printStackTrace(); }
 */
