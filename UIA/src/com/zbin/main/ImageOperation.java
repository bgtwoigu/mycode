package com.zbin.main;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.uiautomator.core.UiDevice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/*
 * 比较两幅图片，按照图片的像素点RGB值进行比较
 * 2015/06/25 by zhangbin
 */
public class ImageOperation {
	public static boolean imageSameAs(String targetImagePath, String comPath,
			double percent) {
		try {
			Bitmap m1 = BitmapFactory.decodeFile(targetImagePath);
			Bitmap m2 = BitmapFactory.decodeFile(comPath);

			int width = m2.getWidth();
			int height = m2.getHeight();
			int numDiffPixels = 0;
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (m2.getPixel(x, y) != m1.getPixel(x, y)) {
						numDiffPixels++;
					}
				}
			}

			double totalPicels = height * width;
			double diffPercent = numDiffPixels / totalPicels;

			return percent <= 1.0 - diffPercent;

		} catch (Exception e) {
		}

		return false;
	}

	/*
	 * 根据图片返回图片在当前界面的矩形框位置，找不到时返回null
	 */
	public static Rect imageCompare(String targetImagePath, String comPath,
			double percent) {
		Bitmap m1 = BitmapFactory.decodeFile(targetImagePath);
		Bitmap m2 = BitmapFactory.decodeFile(comPath);
		int m1_width = m1.getWidth();
		int m1_height = m1.getHeight();
		int m2_width = m2.getWidth();
		int m2_height = m2.getHeight();

		int color_A = m2.getPixel(0, 0);
		int color_B = m2.getPixel(m2.getWidth() - 1, 0);
		int color_C = m2.getPixel(0, m2_height - 1);
		int color_D = m2.getPixel(m2_width - 1, m2_height - 1);
		for (int i = 0; i < m1_width - m2_width; i++) {
			for (int j = 0; j < m1_height - m2_height; j++) {
				if (m1.getPixel(i, j) == color_A
						&& m1.getPixel(i + m2_width - 1, j) == color_B
						&& m1.getPixel(i, j + m2_height - 1) == color_C
						&& m1.getPixel(i + m2_width - 1, j + m2_height - 1) == color_D) {
					return new Rect(i, j, i + m2_width, j + m2_height);
				}
			}
		}
		return null;
	}

	// 保存位图到SD卡中，默认为SD卡根目录
	public static void saveBitMapToSdcard(Bitmap bitmap, String newName) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("/mnt/sdcard/" + newName + ".png");
			if (out != null) {
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
				out.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Boolean Screenshot(String logString) {
		
		String path="/mnt/sdcard/UILog/UIImageLog/"+logString+".png";
		File file=new File(path);
		UiDevice.getInstance().takeScreenshot(file);
		return true;
	}
}
