package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Environment;

import com.android.uiautomator.core.Configurator;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

public class CM {
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;

	public static void startActivity(String pack, String activity) {
		Process starter = null;
		try {
			starter = Runtime.getRuntime().exec(
					"am start " + pack + "/" + activity);
			starter.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (starter != null)
				starter.destroy();
		}
	}

	public static void startPackage(String pack) {
		Process starter = null;
		try {
			starter = Runtime.getRuntime().exec("monkey -p " + pack + " 1");
			starter.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (starter != null)
				starter.destroy();
		}
	}

	public static boolean packIsRuning(String pack) {
		try {
			Process starter = Runtime.getRuntime().exec("dumpsys activity");
			final BufferedReader br = new BufferedReader(new InputStreamReader(
					starter.getInputStream()));
			String line = "";
			line = br.readLine();
			while (line != null) {
				if (line.contains("mFocusedActivity")) {
					br.close();
					starter.destroy();
					// CM.writeReport("/storage/sdcard0/1.txt", line+"\r\n");
					if (line.contains(pack))
						return true;
					else
						return false;
				} else
					line = br.readLine();
			}
			// CM.writeReport("/storage/sdcard0/1.txt", line+"\r\n");
			br.close();
			starter.destroy();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static void stopPackage(String pack) {
		Process starter = null;
		try {
			starter = Runtime.getRuntime().exec("am force-stop " + pack);
			starter.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (starter != null)
				starter.destroy();
		}
	}

	public static String getText(String file) {
		Process starter = null;
		String result = "";
		try {
			starter = Runtime.getRuntime().exec("cat " + file);
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(starter.getInputStream()));
			result = bufferedReader.readLine();
			starter.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (starter != null)
				starter.destroy();
		}
		return result;
	}

	public static void deleteFile(File file) {
		if (file.isFile())
			file.delete();
		else if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++)
				deleteFile(files[i]);
		}
	}

	public static void clickFastOnScreen(int num, UiDevice ud, int x, int y) {
		long timeout = Configurator.getInstance()
				.getActionAcknowledgmentTimeout();
		Configurator.getInstance().setActionAcknowledgmentTimeout(0);
		for (int i = 0; i < num; i++) {
			ud.click(x, y);
		}
		Configurator.getInstance().setActionAcknowledgmentTimeout(timeout);
	}

	public static void clickLongOnScreen(UiDevice ud, int x, int y) {
		ud.drag(x, y, x, y, 15);
	}

	public static void clickLongOnObject(UiDevice ud, UiObject obj)
			throws UiObjectNotFoundException {
		Rect r = obj.getBounds();
		ud.drag(r.centerX(), r.centerY(), r.centerX(), r.centerY(), 20);
	}

	public static boolean waitForObject(UiObject obj, long timeout) {
		for (int i = -1; i < timeout / 1000; i++) {
			if (obj.exists())
				return true;
			else {
				if (timeout >= 1000)
					hold(1000);
				else
					hold(100);
			}
		}
		return false;
	}

	public static boolean waitForClass(String className, long timeout) {
		UiObject obj = new UiObject(new UiSelector().className(className));
		for (int i = -1; i < timeout / 1000; i++) {
			if (obj.exists())
				return true;
			else {
				if (timeout >= 1000)
					hold(1000);
				else
					hold(100);
			}
		}
		return false;
	}

	public static boolean waitForText(String text, long timeout) {
		UiObject obj = new UiObject(new UiSelector().text(text));
		for (int i = -1; i < timeout / 1000; i++) {
			if (obj.exists())
				return true;
			else {
				if (timeout >= 1000)
					hold(1000);
				else
					hold(100);
			}
		}
		return false;
	}

	public static boolean waitForText(String text) {
		UiObject obj = new UiObject(new UiSelector().text(text));
		if (obj.exists())
			return true;
		else
			return false;
	}

	public static boolean waitForSubText(String text, long timeout) {
		UiObject obj = new UiObject(new UiSelector().textContains(text));
		for (int i = -1; i < timeout / 1000; i++) {
			if (obj.exists())
				return true;
			else {
				if (timeout >= 1000)
					hold(1000);
				else
					hold(100);
			}
		}
		return false;
	}

	public static boolean waitForDesc(String text, long timeout) {
		UiObject obj = new UiObject(new UiSelector().description(text));
		for (int i = -1; i < timeout / 1000; i++) {
			if (obj.exists())
				return true;
			else {
				if (timeout >= 1000)
					hold(1000);
				else
					hold(100);
			}
		}
		return false;
	}

	public static boolean waitForSubDesc(String text, long timeout) {
		UiObject obj = new UiObject(new UiSelector().descriptionContains(text));
		for (int i = -1; i < timeout / 1000; i++) {
			if (obj.exists())
				return true;
			else {
				if (timeout >= 1000)
					hold(1000);
				else
					hold(100);
			}
		}
		return false;
	}

	public static boolean waitforObjectScroll(UiObject obj, long timeout) {
		for (int i = -1; i < timeout / 1000; i++) {
			if (obj.exists())
				return true;
			else {
				if (timeout >= 1000)
					hold(1000);
				else
					hold(100);
			}
		}
		return false;
	}

	public static boolean waitForId(String rId, long timeout) {
		UiObject obj = new UiObject(new UiSelector().resourceId(rId));
		for (int i = -1; i < timeout / 1000; i++) {
			if (obj.exists())
				return true;
			else {
				if (timeout >= 1000)
					hold(1000);
				else
					hold(100);
			}
		}
		return false;
	}

	public static boolean waitForNewApp(String packageName, long timeout)
			throws UiObjectNotFoundException {
		for (int i = -1; i < timeout / 1000; i++) {
			try {
				if ((new UiObject(new UiSelector().index(0))).getPackageName()
						.equals(packageName))
					return true;
				else {
					if (timeout >= 1000)
						hold(1000);
					else
						hold(100);
				}
			} catch (Exception e) {
			}
		}
		return false;
	}

	public static UiObject findObjectByRid(String rid, long timeout) {
		UiObject mObj = new UiObject(new UiSelector().resourceId(rid));
		if (waitForObject(mObj, timeout))
			return mObj;
		else
			return null;
	}
	
	public static UiObject findObjectByRid(String rid,int instance, long timeout) {
		UiObject mObj = new UiObject(new UiSelector().resourceId(rid).instance(instance));
		if (waitForObject(mObj, timeout))
			return mObj;
		else
			return null;
	}
	
	
	

	public static UiObject findObjectRidMatches(String regex, long timeout) {
		UiObject mObj = new UiObject(new UiSelector().resourceIdMatches("["
				+ regex + "]"));
		if (waitForObject(mObj, timeout))
			return mObj;
		else
			return null;
	}

	public static UiObject findObjectByClassname(String className, long timeout) {
		UiObject mObj = new UiObject(new UiSelector().className(className));
		if (waitForObject(mObj, timeout))
			return mObj;
		else
			return null;
	}
	
	public static UiObject findObjectByClassName(String className,int instance, long timeout) {
		UiObject mObj = new UiObject(new UiSelector().className(className).instance(instance));
		if (waitForObject(mObj, timeout))
			return mObj;
		else
			return null;
	}
	

	public static UiObject findObjectByText(String text, long timeout) {
		UiObject mObj = new UiObject(new UiSelector().text(text));
		if (waitForObject(mObj, timeout))
			return mObj;
		else
			return null;
	}

	public static UiObject findObjectBySubText(String text, long timeout) {
		UiObject mObj = new UiObject(new UiSelector().textContains(text));
		if (waitForObject(mObj, timeout))
			return mObj;
		else
			return null;
	}

	public static UiObject findObjectByDesc(String text, long timeout) {
		UiObject mObj = new UiObject(new UiSelector().description(text));
		if (waitForObject(mObj, timeout))
			return mObj;
		else
			return null;
	}

	public static UiObject findObjectBySubDesc(String text, long timeout) {
		UiObject mObj = new UiObject(new UiSelector().descriptionContains(text));
		if (waitForObject(mObj, timeout))
			return mObj;
		else
			return null;
	}

	public static UiObject findChildByPosistion(UiObject obj, int[] pos) {
		try {
			for (int i = 0; i < pos.length; i++) {
				obj = obj.getChild(new UiSelector().index(pos[i]));
			}
		} catch (Exception e) {
			return null;
		}
		return obj;
	}

	public static UiObject findObjectByMutiAttribut(String resourceID,
			String className, String text, int index, int timeout) {
		UiSelector oSelector = new UiSelector();
		if (!resourceID.equals(""))
			oSelector = oSelector.resourceId(resourceID);
		if (!className.equals(""))
			oSelector = oSelector.className(className);
		if (!text.equals(""))
			oSelector = oSelector.text(text);
		if (index != -1)
			oSelector = oSelector.index(index);
		UiObject mObj = new UiObject(oSelector);
		if (waitForObject(mObj, timeout))
			return mObj;
		else
			return null;
	}

	public static UiObject findChildbyAtrribut(UiObject obj, String resourceID,
			String className, String text, int index, int timeout)
			throws UiObjectNotFoundException {
		UiSelector oSelector = new UiSelector();
		if (!resourceID.equals(""))
			oSelector = oSelector.resourceId(resourceID);
		if (!className.equals(""))
			oSelector = oSelector.className(className);
		if (!text.equals(""))
			oSelector = oSelector.text(text);
		if (index != -1)
			oSelector = oSelector.index(index);
		UiObject mObj = obj.getChild(oSelector);
		if (mObj.exists())
			return mObj;
		else
			return null;
	}

	public static UiObject findObjectByMutiAttribut(String resourceId,
			int index, int timeout) {
		UiObject mObj = new UiObject(new UiSelector().resourceId(resourceId)
				.index(index));
		if (waitForObject(mObj, timeout))
			return mObj;
		else
			return null;
	}

	public static UiObject findObjectByMutiAttribut(int index,
			String className, int timeout) {
		UiObject mObj = new UiObject(new UiSelector().index(index).className(
				className));
		if (waitForObject(mObj, timeout))
			return mObj;
		else
			return mObj;

	}

	public static UiObject findObjectByMutiAttribut(String resourceId,
			String className, int timeout) {
		UiObject mObj = new UiObject(new UiSelector().resourceId(resourceId)
				.className(className));
		if (waitForObject(mObj, timeout))
			return mObj;
		else
			return null;
	}

	public static void touchInObj(UiDevice ud, UiObject obj, double x, double y)
			throws UiObjectNotFoundException {
		Rect mr = obj.getBounds();
		ud.click(mr.left + (int) (mr.width() * x), mr.top
				+ (int) (mr.height() * y));
	}

	public static void dragObj(UiDevice ud, UiObject obj, int direction,
			int steps) throws UiObjectNotFoundException {
		Rect mr = obj.getBounds();
		switch (direction) {
		case LEFT:
			ud.drag(mr.right - 5, mr.centerY(), mr.left + 5, mr.centerY(),
					steps);
			break;
		case RIGHT:
			ud.drag(mr.left + 5, mr.centerY(), mr.right - 5, mr.centerY(),
					steps);
			break;
		case UP:
			ud.drag(mr.centerX(), mr.bottom - 5, mr.centerX(), mr.top + 5,
					steps);
			break;
		case DOWN:
			ud.drag(mr.centerX(), mr.top + 5, mr.centerX(), mr.bottom - 5,
					steps);
			break;
		}
	}

	public static void dragObjTo(UiDevice ud, UiObject obj, int direction,
			int distance, int steps) throws UiObjectNotFoundException {
		Rect mr = obj.getBounds();
		switch (direction) {
		case LEFT:
			ud.drag(mr.right - 5, mr.centerY(), mr.left - distance,
					mr.centerY(), steps);
			break;
		case RIGHT:
			ud.drag(mr.left + 5, mr.centerY(), mr.right + distance,
					mr.centerY(), steps);
			break;
		case UP:
			ud.drag(mr.centerX(), mr.bottom + distance, mr.centerX(),
					mr.top + 5, steps);
			break;
		case DOWN:
			ud.drag(mr.centerX(), mr.top - distance, mr.centerX(),
					mr.bottom - 5, steps);
			break;
		}
	}

	public static void scroll(UiDevice ud, int direction) {
		int w = ud.getDisplayWidth();
		int h = ud.getDisplayHeight();
		switch (direction) {
		case LEFT:
			ud.swipe(w / 2 + 150, h / 2, w / 2 - 150, h / 2, 5);
			break;
		case RIGHT:
			ud.swipe(w / 2 - 150, h / 2, w / 2 + 150, h / 2, 5);
			break;
		case UP:
			ud.swipe(w / 2, h / 2 + 200, w / 2, h / 2 - 200, 5);
			break;
		case DOWN:
			ud.swipe(w / 2, h / 2 - 200, w / 2, h / 2 + 200, 5);
			break;
		}
	}

	public static void scroll(UiDevice ud, int direction, int span) {
		int w = ud.getDisplayWidth();
		int h = ud.getDisplayHeight();
		switch (direction) {
		case LEFT:
			ud.swipe(w / 2 + span, h / 2, w / 2 - span, h / 2, 5);
			break;
		case RIGHT:
			ud.swipe(w / 2 - span, h / 2, w / 2 + span, h / 2, 5);
			break;
		case UP:
			ud.swipe(w / 2, h / 2 + span, w / 2, h / 2 - span, 5);
			break;
		case DOWN:
			ud.swipe(w / 2, h / 2 - span, w / 2, h / 2 + span, 5);
			break;
		}
	}

	public static void setText(UiDevice ud, UiObject obj, String text)
			throws UiObjectNotFoundException {
		String chars = "0123456789************abcdefghijklmnopqrstuvwxyz";
		obj.click();
		obj.click();
		CM.hold(1000);
		for (char w : text.toCharArray())
			ud.pressKeyCode(chars.indexOf(w) + 7);
	}

	public static void hold(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeReport(String fileName, String report)
			throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName, true);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
		osw.write(report);
		osw.flush();
		osw.close();
	}

	public static String getTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Date nowTime = new Date(System.currentTimeMillis());//
		return formatter.format(nowTime);// .replace(" ", "-")
	}

	public static String getRootPath() {
		String path = Environment.getExternalStorageDirectory().getAbsolutePath();
		/*if (path.contains("emulated"))
			return "/storage/sdcard0";
		else*/
		return path;
	}

	public static void createFolder(String path) {
		synchronized (CM.class) {
			File mF = new File(path);
			if (!mF.exists())
				mF.mkdirs();
		}
	}

	public static void recordLog(UiDevice curDev, String log, String logPath,
			String snapPath) {
		try {
			writeReport(logPath, log);
			curDev.takeScreenshot(new File(snapPath + "step1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String readTxt(String fileName) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(new File(
				fileName)));
		String line = reader.readLine().trim();
		reader.close();
		return line;
	}

	public static String getIMEI(UiDevice ud, String pack, String act) {
		String imei = "";
		try {
			CM.startActivity(pack, act);
			SPA.Dailer.enter("*#66#");
			CM.waitForId("android:id/list", 2000);
			imei = CM.findObjectByRid("android:id/list", 1000)
					.getChild(new UiSelector().index(1)).getText();
			CM.hold(2000);
			ud.pressBack();
			CM.hold(1000);
			ud.pressBack();
			// waitForId("android:id/message",1000);
			// imei=new UiObject(new
			// UiSelector().resourceId("android:id/message")).getText();

		} catch (Exception e) {
		}

		return imei;
	}

	public static boolean comparePic(String oldPath, String newPath,
			String picname, String reportpath) {
		try {
			Bitmap oldBit = BitmapFactory.decodeFile(oldPath + "/" + picname
					+ ".png");
			Bitmap newBit = BitmapFactory.decodeFile(newPath + "/" + picname
					+ ".png");
			int count = 0;
			for (int h = oldBit.getHeight() * 2 / 50; h < oldBit.getHeight(); h++) {
				for (int w = 0; w < oldBit.getWidth(); w++) {
					if (Math.abs(oldBit.getPixel(w, h) - newBit.getPixel(w, h)) > 200000) {
						count++;
					}
				}
			}
			if (count < 150)
				return true;
			else
				return false;
		} catch (Exception e) {
			try {
				writeReport(reportpath, e.toString() + "\r\n");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		}
	}
	 public static void clickOnText(String text, boolean longClick, boolean contains,
				boolean scroll) throws UiObjectNotFoundException {
			UiObject textView = null;
		
			if (scroll) {
				//声明一个名叫listView的UiScrollable
				UiScrollable scrollView = new UiScrollable(new UiSelector()
						.className("android.widget.ScrollView").index(0));			
				UiScrollable listView = new UiScrollable(new UiSelector()
				.className("android.widget.ListView").index(0));			
				//滑动listView直到出现text就停止
				if (scrollView.exists()) {				
					scrollView.scrollIntoView(new UiSelector().text(text));
				}else if(listView.exists()){
					listView.scrollIntoView(new UiSelector().text(text));				
				}
				textView = new UiObject(new UiSelector().text(text));
				if (!textView.exists()) {
					if (contains)
						textView = listView.getChild(new UiSelector()
								.textContains(text));
					else {
						textView = listView.getChild(new UiSelector()
								.textMatches(text));
					}

				}
			} else if (contains) {
				textView = new UiObject(new UiSelector().textContains(text));
				if (!textView.exists()) {
					textView = new UiObject(
							new UiSelector().descriptionContains(text));
				}
				if (!textView.exists()) {
					textView = new UiObject(new UiSelector().textMatches(text));
				}
			} else {
				textView = new UiObject(new UiSelector().text(text));
				if (!textView.exists()) {
					textView = new UiObject(new UiSelector().textMatches(text));
				}
				if (!textView.exists()) {
					textView = new UiObject(new UiSelector().description(text));
				}
			}

			 
			if (longClick) {
				UiDevice.getInstance()
						.swipe((textView.getBounds().left + textView.getBounds().right) / 2,
								(textView.getBounds().top + textView.getBounds().bottom) / 2,
								(textView.getBounds().left + textView.getBounds().right) / 2,
								(textView.getBounds().top + textView.getBounds().bottom) / 2,
								100);
			}else{
				if (!textView.isClickable()) {
				      UiDevice.getInstance().click((textView.getBounds().left + textView.getBounds().right) / 2,
								(textView.getBounds().top + textView.getBounds().bottom) / 2);
					}else{
				textView.click();
					}
			}
		}
	
	

	public static void recordPowerInfo(String snapPath)
			throws UiObjectNotFoundException {
		startActivity("com.oppo.powermanager",
				"com.oppo.powermanager.PowerMgrFragment");
		waitForNewApp("com.oppo.powermanager", 2000);
		scroll(UiDevice.getInstance(), UP);
		findObjectByText("耗电详情", 1000).click();
		waitForText("耗电统计", 1000);
		UiDevice.getInstance().takeScreenshot(new File(snapPath));
	}

	public static void enterText(UiDevice ud, UiObject obj, String text)
			throws UiObjectNotFoundException {
		String chars = "abcdefghijklmnopqrstuvwxyz";
		String nums = "0123456789";
		Rect R = obj.getBounds();
		ud.click(R.left + 2, R.centerY());
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '&') {
				ud.pressKeyCode(66);
				continue;
			}
			if (text.charAt(i) == '@') {
				ud.pressKeyCode(77);
				continue;
			}
			if (text.charAt(i) == '.') {
				ud.pressKeyCode(56);
				continue;
			}
			for (int j = 0; j < nums.length(); j++) {
				if (nums.charAt(j) == text.charAt(i)) {
					ud.pressKeyCode(j + 7);
					continue;
				}
			}
			for (int j = 0; j < chars.length(); j++) {
				if (chars.charAt(j) == text.charAt(i)) {
					ud.pressKeyCode(j + 29);
					continue;
				}
			}
		}
	}

	public static int getRandomNumBetween(int a, int b) {
		Random R = new Random();
		if (a < b) {
			return a + R.nextInt(b - a);
		} else {
			return b + R.nextInt(a - b);
		}
	}

	public static String GetRandomString(int a, int b)// 返回大于a小于b个字符
	{
		String num = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
				+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
				+ "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
				+ "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		String result = "";
		Random R = new Random();
		int len = R.nextInt(b - a) + a;
		for (int i = 0; i < len; i++) {
			result += num.charAt(R.nextInt(num.length()));
		}
		return result;
	}
}
