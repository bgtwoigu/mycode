
package com.zbin.main;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Instrumentation;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Environment;
import android.os.RemoteException;
import android.test.InstrumentationTestRunner;
import android.util.Log;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.core.UiWatcher;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class BaseTestCase extends UiAutomatorTestCase {

	public BaseTestCase() {

	}

	public static long _delaytime;
	public String ScrollName = "android.widget.ScrollView";
	public String ListName = "android.widget.ListView";

	private String screenpng = "/mnt/sdcard/autotestpng";
	private String manualscreenpng = "/mnt/sdcard/manualtestpng";
	File Storage = Environment.getExternalStorageDirectory();
	private String autotestpng = Storage + "/" + "autotestpng ";
	private String manualtestpng = Storage + "/" + "manualtestpng ";

	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

	public void takeScreenshot() throws Exception {

		File targetDir = new File(screenpng);
		if (!targetDir.exists()) {
			Runtime.getRuntime().exec("mkdir -p " + screenpng);
		}

		UiDevice.getInstance().takeScreenshot(new File(screenpng + "/***" + df.format(new Date()).toString() + ".png"));
	}

	//
	public void takeScreenshot(String screenname) throws Exception {
		File targetDir = new File(manualscreenpng);
		if (!targetDir.exists()) {
			Runtime.getRuntime().exec("mkdir -p " + manualscreenpng);
		}
		UiDevice.getInstance().takeScreenshot(new File(manualscreenpng + "/***" + screenname + ".png"));
	}

	// 根据ID和类名查找图标
	public UiObject getObjectByIDClass(String id, String className) {
		return new UiObject(new UiSelector().resourceId(id).className(className));
	}

	// 根据文字和类名查找图标
	public UiObject getObjectByTextClass(String text, String className) {
		return new UiObject(new UiSelector().text(text).className(className));
	}

	// 根据描述查找图标
	public UiObject getObjectByDesc(String desc) {
		return new UiObject(new UiSelector().description(desc));
	}

	// 根据文本查找图标
	public UiObject getObjectByText(String text) {
		return new UiObject(new UiSelector().text(text));
	}

	// 根据ID查找图标
	public UiObject getObjectByID(String text) {
		return new UiObject(new UiSelector().resourceId(text));
	}

	// 根据类名查找图标
	public UiObject getObjectByClassName(String text) {
		return new UiObject(new UiSelector().className(text));
	}

	/**
	 * 滑动寻找UI 点击UI 延时
	 * 
	 * @param className
	 * @param text
	 * @param delayTime
	 * @throws Exception
	 */
	public void clickItem(String text, long delayTime) throws Exception {
		UiScrollable ScrollView = new UiScrollable(new UiSelector().className(ScrollName).index(0));
		UiScrollable ListView = new UiScrollable(new UiSelector().className(ScrollName).index(0));
		if (ScrollView.exists()) {
			ScrollView.scrollIntoView(new UiSelector().textContains(text));
		} else {
			ListView.scrollIntoView(new UiSelector().textContains(text));
		}
		clickxy(text, delayTime);
	}

	/**
	 * 滑动寻找UI 点击UI 延时
	 * 
	 * @param className
	 * @param text
	 * @param delayTime
	 * @throws Exception
	 */
	public void clickItem(String className, String text, long delayTime) throws Exception {
		UiScrollable listView = new UiScrollable(new UiSelector().className(className).index(0));
		listView.scrollIntoView(new UiSelector().textContains(text));
		clickxy(text, delayTime);
	}

	/**
	 * 点击坐标
	 * 
	 * @param x
	 * @param y
	 */
	public void clickxy(int x, int y) {
		UiDevice.getInstance().click(x, y);

	}

	/**
	 * 点击坐标，附带延时参数
	 * 
	 * @param x
	 * @param y
	 * @param delayTime
	 */
	public void clickxy(int x, int y, long delayTime) {
		UiDevice.getInstance().click(x, y);
		sleep(delayTime);
	}

	/**
	 * 长按坐标
	 * 
	 * @param x
	 * @param y
	 * @param longclick
	 */
	public void clickxy(int x, int y, boolean longclick) {
		UiDevice.getInstance().swipe(x, y, x, y, 200);

	}

	/**
	 * 根据文字点击文字的坐标，针对于不可以点击的控件
	 * 
	 * @param text
	 * @param delayTime
	 * @throws Exception
	 */
	public void clickxy(String text, long delayTime) throws Exception {
		UiObject display = getObjectByText(text);
		Rect display1 = (Rect) display.getBounds();
		int x = display1.centerX();
		int y = display1.centerY();
		clickxy(x, y);
		sleep(delayTime);

	}

	// 点击某个UI元素
	public boolean clickUiobject(UiObject uiObject) throws UiObjectNotFoundException {
		if (!uiObject.exists()) {
			return false;
		}
		int left = uiObject.getBounds().left;
		int top = uiObject.getBounds().top;
		int right = uiObject.getBounds().right;
		int bottom = uiObject.getBounds().bottom;
		int width = right - left;
		int height = bottom - top;
		int centerX = left + width / 2;
		int centerY = top + height / 2;
		UiDevice.getInstance().click(centerX, centerY);
		return true;
	}

	// 根据文字点击图标
	public Boolean clickItemscrollclassName(String className, String itemName) throws UiObjectNotFoundException {
		// UiObject uiObject = new UiObject(new UiSelector().text(itemName));
		UiScrollable uiObject = new UiScrollable(new UiSelector().scrollable(true));
		uiObject.setAsVerticalList();

		UiObject settingsApp = uiObject.getChildByText(new UiSelector().className(className), itemName);

		settingsApp.clickAndWaitForNewWindow(5000);
		// uiObject.scrollIntoView(new UiSelector().text(itemName));
		if (!uiObject.exists()) {
			return false;
		}
		// if (uiObject.isClickable()) {
		// uiObject.click();
		//
		// return true;
		// }
		Log.d("222", "iiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
		if (clickUiobject(uiObject))
			return true;
		return false;
	}

	// 根据文字点击图标
	public Boolean clickItemscrollclassName1(String className, String itemName) throws UiObjectNotFoundException {
		// UiObject uiObject = new UiObject(new UiSelector().text(itemName));
		UiScrollable uiObject = new UiScrollable(new UiSelector().scrollable(true));
		uiObject.setAsVerticalList();

		UiObject settingsApp = uiObject.getChildByText(new UiSelector().className(className), itemName);

		// settingsApp.clickAndWaitForNewWindow(5000);
		// uiObject.scrollIntoView(new UiSelector().text(itemName));
		if (!uiObject.exists()) {
			return false;
		}
		// if (uiObject.isClickable()) {
		// uiObject.click();
		//
		// return true;
		// }
		/*
		 * Log.d("222", "iiiiiiiiiiiiiiiiiiiiiiiiiiiiii"); if
		 * (clickUiobject(uiObject)) return true;
		 */
		return true;
	}

	// 根据文字点击图标
	public Boolean clickResourceIDAndText(String classname, String itemName) throws UiObjectNotFoundException {
		UiObject uiObject = new UiObject(new UiSelector().resourceId(classname).text(itemName));

		if (!uiObject.exists()) {
			return false;
		}
		if (uiObject.isClickable()) {
			uiObject.click();
			return true;
		}
		if (clickUiobject(uiObject))
			return true;
		return false;
	}

	// 根据文字点击图标
	public Boolean clickClassNameAndText(String classname, String itemName) throws UiObjectNotFoundException {
		UiObject uiObject = new UiObject(new UiSelector().className(classname).text(itemName));

		if (!uiObject.exists()) {
			return false;
		}
		if (uiObject.isClickable()) {
			uiObject.click();
			return true;
		}
		if (clickUiobject(uiObject))
			return true;
		return false;
	}

	// 根据文字点击图标
	public void clickItem(String itemName) throws UiObjectNotFoundException {
		UiObject uiObject = getObjectByText(itemName);
		Rect uiObject1 = (Rect) uiObject.getBounds();
		int x = uiObject1.centerX();
		int y = uiObject1.centerY();

		if (!uiObject.exists()) {
			//return false;
		} else {
			clickxy(x, y);
			//return true;
		}

		

	}

	// 根据描述点击图标
	public Boolean clickItemByDescription(String itemName) throws UiObjectNotFoundException {
		UiObject uiObject = getObjectByDesc(itemName);
		if (!uiObject.exists()) {
			return false;
		}
		if (uiObject.isClickable()) {
			uiObject.click();
			sleep(500);
			return true;
		}
		if (clickUiobject(uiObject))
			return true;
		return false;
	}

	/**
	 * // 根据文字正则表达式点击图标
	 * 
	 * @param regex
	 * @return
	 * @throws UiObjectNotFoundException
	 */

	public boolean clickItemByRegex(String regex) throws UiObjectNotFoundException {
		UiObject uiObject = new UiObject(new UiSelector().textMatches(regex));
		if (!uiObject.exists()) {
			return false;
		}
		if (uiObject.isClickable()) {
			uiObject.click();
			return true;
		}
		if (clickUiobject(uiObject))
			return true;
		return false;
	}

	// 根据类名
	public boolean clickItemByClassName(String className) throws UiObjectNotFoundException {
		UiObject uiObject = getObjectByClassName(className);

		Rect uiObject1 = (Rect) uiObject.getBounds();
		int x = uiObject1.centerX();
		int y = uiObject1.centerY();

		if (!uiObject.exists()) {

			return false;
		}

		clickxy(x, y);

		return false;
	}

	// 根据类名
	public boolean clickItemByClassName(String className, boolean longclick) throws UiObjectNotFoundException {
		UiObject uiObject = new UiObject(new UiSelector().className(className));
		if (!uiObject.exists()) {
			return false;
		}
		if (uiObject.isClickable()) {
			if (longclick) {
				uiObject.longClick();
			} else {
				uiObject.click();
			}
			return true;
		}
		if (clickUiobject(uiObject))
			return true;
		return false;
	}

	// 根据ID点击图标
	public boolean clickItemByID(String id, boolean longclick) throws UiObjectNotFoundException {
		UiObject uiObject = new UiObject(new UiSelector().resourceId(id));

		if (!uiObject.exists()) {
			return false;
		}
		if (uiObject.isClickable()) {
			if (longclick) {
				uiObject.longClick();
			} else {
				uiObject.click();
			}
			return true;
		}
		if (clickUiobject(uiObject))
			return true;
		return false;
	}

	// 根据ID点击图标
	public boolean clickItemByID(String id) throws UiObjectNotFoundException {
		UiObject uiObject = getObjectByID(id);

		Rect uiObject1 = (Rect) uiObject.getBounds();
		int x = uiObject1.centerX();
		int y = uiObject1.centerY();

		if (!uiObject.exists()) {

			return false;
		}

		clickxy(x, y);

		return false;
	}

	// 根据ID正则表达式点击图标
	public boolean clickItemByRegexID(String regex) throws UiObjectNotFoundException {
		UiObject uiObject = new UiObject(new UiSelector().resourceIdMatches(regex));
		if (!uiObject.exists()) {
			return false;
		}
		if (uiObject.isClickable()) {
			uiObject.click();
			return true;
		}
		if (clickUiobject(uiObject))
			return true;
		return false;
	}

	// 根据ID正则表达式点击图标,并且提供延迟
	public boolean clickItemByRegexIDWithDelay(String regex) throws UiObjectNotFoundException {
		if (_delaytime == 0f) {
			sleep(3000);
		} else {
			sleep(_delaytime);
		}
		return clickItemByRegexID(regex);
	}

	// 自定义断言方法
	/*
	 * public boolean assertuser(String expected,String actual) {
	 * assertEquals(expected, actual); UiObject object = new UiObject(new
	 * UiSelector().text(message)); if (object.exists()) { return true; } return
	 * false; // Context context=Instrumentation }
	 */
	// **************************************滑动方法***************************************//
	/**
	 * 亮屏解锁，根据实际情况进行修改
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param tep
	 * @throws RemoteException
	 */
	public void unlockScreen(int x1, int y1, int x2, int y2, int tep) throws RemoteException {

		UiDevice.getInstance().swipe(x1, y1, x2, y2, tep);
	}

	/**
	 * 从底部往上滑动
	 */
	public void swipeup() {
		Point startPoint = new Point(UiDevice.getInstance().getDisplayWidth() / 2,
				UiDevice.getInstance().getDisplayHeight() - 50);
		Point endPoint = new Point(UiDevice.getInstance().getDisplayWidth() / 2,
				UiDevice.getInstance().getDisplayHeight() / 2 + 10);
		UiDevice.getInstance().swipe(new Point[] { startPoint, endPoint }, 50);
		sleep(1000);
	}

	// 从底部往上滑动
	public void swipetodown() {
		Point startPoint = new Point(UiDevice.getInstance().getDisplayWidth() / 2,
				UiDevice.getInstance().getDisplayHeight() / 2 + 10);
		Point endPoint = new Point(UiDevice.getInstance().getDisplayWidth() / 2,
				UiDevice.getInstance().getDisplayHeight() - 10);
		UiDevice.getInstance().swipe(new Point[] { startPoint, endPoint }, 50);
		sleep(1000);
	}

	// 从右往左滑
	public void swipToRight() {
		Point startPoint = new Point(UiDevice.getInstance().getDisplayWidth() / 5,
				UiDevice.getInstance().getDisplayHeight() / 2 - 10);
		Point endPoint = new Point(UiDevice.getInstance().getDisplayWidth() - 50,
				UiDevice.getInstance().getDisplayHeight() / 2 - 10);
		UiDevice.getInstance().swipe(new Point[] { endPoint, startPoint }, 10);
		sleep(1000);
	}

	// 从左往右滑
	public void swipToLeft() {
		Point startPoint = new Point(UiDevice.getInstance().getDisplayWidth() / 5,
				UiDevice.getInstance().getDisplayHeight() / 2 - 10);
		Point endPoint = new Point(UiDevice.getInstance().getDisplayWidth() - 50,
				UiDevice.getInstance().getDisplayHeight() / 2 - 10);
		UiDevice.getInstance().swipe(new Point[] { startPoint, endPoint }, 10);
		sleep(1000);
	}

	// 按下某个按键一定次数
	public void pressKeyCode(int keyCode) {
		UiDevice.getInstance().pressKeyCode(keyCode);
	}

	// 按下某个按键一定次数
	public void pressKeyCode(int keyCode, int time) {
		for (int i = 0; i < time; i++) {
			UiDevice.getInstance().pressKeyCode(keyCode);
		}
	}

	// 最近应用
	public void pressRecentApps() throws Exception {

		UiDevice.getInstance().pressRecentApps();
	}

	// 循环上下左右滚动查找点击
	public boolean ScrollleftandrightText(String text) throws UiObjectNotFoundException {
		UiObject ui = new UiObject(new UiSelector().textContains(text));

		if (ui.exists()) {
			ui.click();
		} else {

			for (int j = 0; j < 3; j++) {
				swipToRight();
				if (ui.exists()) {
					Rect ui1 = (Rect) ui.getBounds();
					int x = ui1.centerX();
					int y = ui1.centerY();
					clickxy(x, y, 500);
					break;
				}
			}
			for (int j = 0; j < 3; j++) {

				swipToLeft();
				if (ui.exists()) {
					Rect ui1 = (Rect) ui.getBounds();
					int x = ui1.centerX();
					int y = ui1.centerY();
					clickxy(x, y, 500);
					break;
				}
			}
			

		}
		return false;

	}

	// 循环滚动查找点击
	public boolean ScrollrightandleftText(String text) throws UiObjectNotFoundException {
		UiObject ui = new UiObject(new UiSelector().textContains(text));
		for (int i = 0; i < 2; i++) {
			if (ui.exists()) {
				ui.click();

				break;
			}
			for (int j = 0; j < 2; j++) {
				swipToLeft();
				if (ui.exists()) {
					ui.click();

					break;
				}
			}
			for (int j = 0; j < 2; j++) {
				swipToRight();
				if (ui.exists()) {
					ui.click();

					break;
				}

			}
		}

		return false;

	}

	// 循环滚动查找点击
	public boolean ScrollGetObjectText(String text) throws UiObjectNotFoundException {
		UiObject ui = new UiObject(new UiSelector().textContains(text));
		for (int i = 0; i < 2; i++) {
			if (ui.exists()) {
				ui.click();

				break;
			}
			for (int j = 0; j < 2; j++) {
				swipeup();
				if (ui.exists()) {
					ui.click();

					break;
				}
			}
			for (int j = 0; j < 2; j++) {
				swipetodown();
				if (ui.exists()) {
					ui.click();

					break;
				}

			}
		}

		return false;

	}

	public UiObject ScrollGetObjectText1(String text) throws UiObjectNotFoundException {
		UiObject ui = new UiObject(new UiSelector().text(text));
		for (int i = 0; i < 5; i++) {
			if (ui.exists()) {
				ui.click();
				break;
			}
			swipetodown();

		}
		return null;

	}

	/**
	 * 监听程序出错 添加点击动作 添加截图动作
	 *//*
		 * 
		 * public void watcherStop(String watcherName, String name) {
		 * UiDevice.getInstance().registerWatcher(watcherName, // 注册监听器 new
		 * UiWatcher() {
		 * 
		 * public boolean checkForCondition() { // 中断监听检查条件
		 * System.out.println("正在监听是否停止运行……");
		 * 
		 * UiObject dialog = new UiObject(new UiSelector().text(name));
		 * 
		 * if (dialog.exists()) { try { takeScreenshot(); dialog.click(); }
		 * catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } return true; } else { return false; }
		 * 
		 * } });
		 * 
		 * // UiDevice.getInstance().removeWatcher(watcherName); }
		 */
	// 按下某个按键一定次数
		public void back() {
			UiDevice.getInstance().pressBack();
		}
	
	/**
	 * 向某个IP端口发送一条消息
	 * 
	 * @param mms
	 * @param host
	 * @param port
	 */
	public static void sendMMSSocket(String mms, String host, int port) {
		Socket client;
		try {
			client = new Socket(host, port);
			Writer writer = new OutputStreamWriter(client.getOutputStream());
			writer.write(mms);
			writer.flush();
			writer.close();
			client.close();
		} catch (Exception e) {
		}
	}
}
