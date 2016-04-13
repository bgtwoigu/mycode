package com.zbin.main;

import java.io.IOException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Browser;

public class myTest extends Common {
	String key = "20";

	public void testQqSHUSHUO() throws Exception {
		// 启动日历

		// click addnew
		int times = 5000;

		Bundle mBundle = getParams();
		if (mBundle.getString("key") != null) {
			times = Integer.parseInt(mBundle.getString("key"));
		}

		for (int i = 0; i < times; i++) {

			// Runtime.getRuntime().exec("am start -n
			// com.oneplus.deskclock/.DeskClock");
			clickxy("写说说", 500);
			clickxy(256, 326);
			UiObject shusuho = getObjectByClassName("android.widget.EditText");
			shusuho.setText("test");
			clickItem("发表");

		}
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
	}

	public void testcalender() throws Exception {
		// 启动日历
		Runtime.getRuntime().exec("am start -n com.oneplus.calendar/com.android.calendar.AllInOneActivity");
		// click addnew
		int times = 5000;

		Bundle mBundle = getParams();
		if (mBundle.getString("key") != null) {
			times = Integer.parseInt(mBundle.getString("key"));
		}

		for (int i = 0; i < times; i++) {
			sleep(300);
			clickItemByID("com.oneplus.calendar:id/floating_action_button");
			sleep(500);
			UiObject title = getObjectByID("com.oneplus.calendar:id/title");
			sleep(500);
			title.setText("test");
			sleep(500);
			clickItemByID("com.oneplus.calendar:id/action_done");
			clickItem("test");
			sleep(500);
			clickItemByID("com.oneplus.calendar:id/info_action_delete");
			sleep(500);
			clickItemByID("android:id/button1");
		}
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
	}

	public void testalarm() throws Exception {
		// 启动日历

		// click addnew
		int times = 5000;

		Bundle mBundle = getParams();
		if (mBundle.getString("key") != null) {
			times = Integer.parseInt(mBundle.getString("key"));
		}

		for (int i = 0; i < times; i++) {
			sleep(500);
			Runtime.getRuntime().exec("am start -n com.oneplus.deskclock/.DeskClock");
			sleep(500);
			clickItemByID("com.oneplus.deskclock:id/fab");
			sleep(500);
			clickItem("确定");
			sleep(1000);
			clickItemByID("com.oneplus.deskclock:id/delete");
			sleep(1000);
			UiDevice.getInstance().pressBack();
			UiDevice.getInstance().pressBack();
			UiDevice.getInstance().pressBack();
		}
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
	}

	public void testclocktab() throws Exception {
		// 启动日历
		Runtime.getRuntime().exec("am start -n com.oneplus.deskclock/.DeskClock");
		// click addnew
		int times = 5000;

		Bundle mBundle = getParams();
		if (mBundle.getString("key") != null) {
			times = Integer.parseInt(mBundle.getString("key"));
		}

		for (int i = 0; i < times; i++) {
			sleep(300);
			clickxy(130, 139, 500);
			clickxy(401, 141, 500);
			clickxy(668, 141, 500);
			clickxy(957, 139, 500);
		}
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
	}

	public void testxiaominote() throws Exception {

		// stopApp();
		// 启动便签
		// Runtime.getRuntime().exec("am start -n
		// com.oneplus.note/.ui.MainActivity");
		// click addnew
		int times = 1;

		Bundle mBundle = getParams();
		if (mBundle.getString("key") != null) {
			times = Integer.parseInt(mBundle.getString("key"));
		}

		for (int i = 0; i < times; i++) {
			clickItem("新建便签");
			// sleep(300);
			UiObject title = getObjectByID("com.miui.notes:id/rich_editor");
			// sleep(200);
			title.setText("note");
			clickItemByID("com.miui.notes:id/home");
			/*
			 * clickItem("test"); sleep(500);
			 * clickItemByID("com.oneplus.calendar:id/info_action_delete");
			 * sleep(500); clickItemByID("android:id/button1");
			 */
		}
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
	}

	public void testnote() throws Exception {

		stopApp();
		// 启动便签
		Runtime.getRuntime().exec("am start -n com.oneplus.note/.ui.MainActivity");
		// click addnew
		int times = 7000;

		Bundle mBundle = getParams();
		if (mBundle.getString("key") != null) {
			times = Integer.parseInt(mBundle.getString("key"));
		}

		for (int i = 0; i < times; i++) {
			sleep(1000);
			clickItemByID("com.oneplus.note:id/add_item_button");
			sleep(500);
			UiObject title = getObjectByID("com.oneplus.note:id/editText");
			sleep(500);
			title.setText("note");
			sleep(1000);
			clickItemByID("com.oneplus.note:id/menu_save");
			sleep(1000);
			UiDevice.getInstance().pressBack();
			/*
			 * clickItem("test"); sleep(500);
			 * clickItemByID("com.oneplus.calendar:id/info_action_delete");
			 * sleep(500); clickItemByID("android:id/button1");
			 */
		}
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
	}

	public void testrecorder() throws Exception {
		// 启动日历
		Runtime.getRuntime().exec("am start -n com.oneplus.soundrecorder/.SoundRecorder");
		// click addnew
		int times = 5000;

		Bundle mBundle = getParams();
		if (mBundle.getString("key") != null) {
			times = Integer.parseInt(mBundle.getString("key"));
		}

		for (int i = 0; i < times; i++) {
			sleep(300);
			clickItemByID("com.oneplus.soundrecorder:id/id_record");
			sleep(30000);
			clickxy(536, 1622, 5000);
		}
		sleep(300);
		clickItemByID("com.oneplus.soundrecorder:id/id_stop");
		sleep(300);
		clickItem("保存");
		sleep(300);
		clickItemByID("android:id/button1");
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
	}

	public void testnone() throws Exception {
		//
		/*
		 * clickItem("设置"); ScrollGetObjectText("WLAN"); sleep(500);
		 */
		/*
		 * UiObject wifistats = new UiObject(new
		 * UiSelector().description("android:id/summary").text("Connected")); if
		 * (wifistats.exists()) {
		 */
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		// ScrollrightandleftText("设置");
		Runtime.getRuntime().exec("am start -n com.android.settings/.Settings");
		boolean wumima = ScrollGetObjectText("安全");
		if (wumima) {
			sleep(500);
			ScrollGetObjectText("屏幕锁定方式");

			sleep(500);
			clickItem("无");
			sleep(500);
			UiDevice.getInstance().pressBack();
			UiDevice.getInstance().pressBack();
			UiDevice.getInstance().pressBack();
		} else {
			ScrollGetObjectText("密码");
			sleep(500);
			clickItem("解锁方式");
			sleep(500);
			clickItem("无锁屏");
			sleep(500);
			UiDevice.getInstance().pressBack();
			UiDevice.getInstance().pressBack();
			UiDevice.getInstance().pressBack();

		}
		/*
		 * ScrollGetObjectText("WLAN"); sleep(500);
		 * ScrollGetObjectText("Oneplus-free"); sleep(1000); UiObject pw =
		 * getObjectByID("com.android.settings:id/password"); sleep(500);
		 * pw.setText("oneplus818"); sleep(1000); clickItem("连接"); sleep(1000);
		 * UiDevice.getInstance().pressBack();
		 */
		/*
		 * clickItem("浏览器"); sleep(500); sleep(500); clickxy(548, 1847, 500);
		 * sleep(500); clickxy(679, 1634, 500); sleep(500);
		 * clickxy(891,501,500); sleep(500); clickxy(165,512,500);
		 * UiDevice.getInstance().pressBack(); sleep(500);
		 * UiDevice.getInstance().pressBack(); sleep(500); clickxy(376
		 * ,948,500); UiObject pw = getObjectByClassName("android.view.View");
		 * sleep(500); pw.setText("www.baidu.com"); sleep(500); clickxy(1002
		 * ,143,500);
		 */
	}

	public void testdaiding() throws RemoteException, UiObjectNotFoundException, Exception {
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		Runtime.getRuntime().exec("am start -n com.android.settings/.Settings");
		UiObject display = getObjectByText("显示");
		Rect display1 = (Rect) display.getBounds();
		int x = display1.centerX();
		int y = display1.centerY();
		clickxy(x, y, 500);

		/*
		 * UiObject wifistats = new UiObject(new
		 * UiSelector().description("android:id/summary").text("Connected")); if
		 * (wifistats.exists()) {
		 */
		ScrollGetObjectText("自动锁定时间");
		sleep(800);
		clickItem("30分钟");
		sleep(800);

		UiDevice.getInstance().pressBack();

	}

	public void testdisplay30() throws RemoteException, UiObjectNotFoundException, Exception {
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		Runtime.getRuntime().exec("am start -n com.android.settings/.Settings");
		UiObject display = getObjectByText("显示");
		Rect display1 = (Rect) display.getBounds();
		int x = display1.centerX();
		int y = display1.centerY();
		clickxy(x, y, 500);

		/*
		 * UiObject wifistats = new UiObject(new
		 * UiSelector().description("android:id/summary").text("Connected")); if
		 * (wifistats.exists()) {
		 */
		ScrollGetObjectText("自动锁定时间");
		sleep(800);
		clickItem("30分钟");
		sleep(800);

		UiDevice.getInstance().pressBack();

	}

	public void testconnectwifiMOBILE() throws RemoteException, UiObjectNotFoundException, Exception {
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		Runtime.getRuntime().exec("am start -n com.android.settings/.Settings");
		ScrollGetObjectText("WLAN");
		sleep(500);

		/*
		 * UiObject wifistats = new UiObject(new
		 * UiSelector().resourceId("android:id/switchWidget")); if
		 * (!wifistats.isCheckable()) { wifistats.click(); }
		 */
		sleep(500);
		ScrollGetObjectText("Moblie-VIP");
		sleep(500);
		UiObject pw = getObjectByID("com.android.settings:id/password");
		sleep(500);
		pw.setText("Oneplus12@3.");
		sleep(500);
		clickItem("连接");
		sleep(500);
		UiDevice.getInstance().pressBack();

	}

	public void testconnectwififree() throws RemoteException, UiObjectNotFoundException, Exception {
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		Runtime.getRuntime().exec("am start -n com.android.settings/.Settings");
		ScrollGetObjectText("WLAN");
		sleep(500);
		/*
		 * UiObject wifistats = new UiObject(new
		 * UiSelector().description("android:id/summary").text("Connected")); if
		 * (wifistats.exists()) {
		 */
		ScrollGetObjectText("Oneplus-free");
		sleep(500);
		UiObject pw = getObjectByID("com.android.settings:id/password");
		sleep(500);
		pw.setText("oneplus818");
		sleep(500);
		clickItem("连接");
		sleep(500);
		UiDevice.getInstance().pressBack();

	}

	public void test1() throws Exception {
		/*
		 * int times = 5000;
		 * 
		 * Bundle mBundle = getParams(); if (mBundle != null &&
		 * mBundle.containsKey("key")) { times =
		 * Integer.parseInt(mBundle.getString("key")); }
		 * 
		 * for (int i = 0; i < times; i++) { sleep(times); pressKeyCode(26);
		 * Runtime("adb shell "); }
		 */
		swipToRight();
		sleep(2000);
		swipToLeft();
	}

	public void testothers() throws RemoteException, UiObjectNotFoundException {
		sleep(500);
		// UiObject display=new UiObject(new UiSelector().text(text));
		/*
		 * UiObject display = getObjectByText("显示"); Rect display1=(Rect)
		 * display.getBounds(); int x=display1.centerX(); int
		 * y=display1.centerY(); clickxy(x, y, 500);
		 */
		for (int i = 0; i < 5; i++) {
			clickItem("确定");
			clickItem("ok");
			clickItem("不再提示");
			clickItem("确定");
			clickItem("允许");
			clickItem("允许");
			clickItem("立即体验");
			sleep(500);
		}
		UiDevice.getInstance().pressBack();

	}

	public void testctsPREO2() throws Exception {
		stopApp();

		// 1.设置系统语言

		clickItem("Settings");
		ScrollGetObjectText("Language & input");
		sleep(500);
		clickItem("System language");
		sleep(500);
		// clickItem("English (US)");
		// unlockScreen(300, 500, 300, 30, 50);
		sleep(500);
		ScrollGetObjectText("English");
		sleep(500);
		UiDevice.getInstance().pressBack();
		// 2.选择键盘
		clickItem("Current Keyboard");
		clickItem("CHOOSE KEYBOARDS");

		clickItem("Current Keyboard");

		// 2.设置display最长时间
		ScrollGetObjectText("Display");
		clickItem("Sleep");
		clickItem("30 minutes");
		UiDevice.getInstance().pressBack();

		// 3.设置安全无锁屏
		ScrollGetObjectText("Security");
		sleep(500);
		clickItem("Passworld lock");
		sleep(500);
		clickItem("unlock way");
		sleep(500);
		clickItem("None lock screen");
		sleep(500);
		UiDevice.getInstance().pressBack();

		/*
		 * // 4.设置开发者选项 ScrollGetObjectText("Developer options"); sleep(500);
		 * ScrollGetObjectText("Stay awake"); sleep(500); //
		 * ScrollGetObjectclassText("android:id/title","Allow mock loactions");
		 * sleep(500); UiDevice.getInstance().pressBack();
		 */

		// 5.连接wifi
		// unlockScreen(300, 100, 300, 500, 50);
		sleep(500);
		ScrollGetObjectText1("Wi‑Fi");
		sleep(500);
		/*
		 * if
		 * (!getObjectByID("com.android.settings:id/switch_widget").isChecked())
		 * {
		 * 
		 * clickItemByID("com.android.settings:id/switch_widget"); }
		 */
		sleep(500);
		ScrollGetObjectText("mobile-vip");
		UiObject pw = getObjectByID("com.android.settings:id/password");
		pw.setText("Oneplus12@3.");
		sleep(500);
		clickItem("Connect");
		UiDevice.getInstance().pressBack();
		// 安装adminapk
		// UiAutomatorHelper help = new UiAutomatorHelper();
		// Process p = Runtime.getRuntime().exec("am start
		// com.android.documentsui");

	}

	public void testctsPREO22() throws Exception {
		stopApp();

		// 1.设置系统语言
		UiObject main = new UiObject(new UiSelector().description("Apps"));
		if (main.exists()) {
			main.click();
		}
		clickItem("Settings");
		ScrollGetObjectText("Language & input");
		sleep(500);
		clickItem("Language");
		sleep(500);
		// clickItem("English (US)");
		// unlockScreen(300, 500, 300, 30, 50);
		sleep(500);
		clickItem("English");
		sleep(500);
		// UiDevice.getInstance().pressBack();
		// 2.选择键盘
		clickItem("Current Keyboard");
		sleep(500);
		clickItem("Choose keyboards");
		sleep(500);
		clickItem("Current Keyboard");
		sleep(500);
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		// 2.设置display最长时间
		swipetodown();
		swipetodown();
		swipetodown();
		sleep(500);
		ScrollGetObjectText("Display");
		sleep(500);

		clickItem("Sleep");
		sleep(500);

		clickItem("30 minutes");
		sleep(500);

		UiDevice.getInstance().pressBack();

		// 3.设置安全无锁屏
		ScrollGetObjectText("Security");
		sleep(500);
		clickxy(237, 463, 500);
		sleep(500);
		clickItem("None");
		sleep(500);
		sleep(500);

		UiDevice.getInstance().pressBack();

		/*
		 * // 4.设置开发者选项 ScrollGetObjectText("Developer options"); sleep(500);
		 * ScrollGetObjectText("Stay awake"); sleep(500); //
		 * ScrollGetObjectclassText("android:id/title","Allow mock loactions");
		 * sleep(500); UiDevice.getInstance().pressBack();
		 */

		// 5.连接wifi
		// unlockScreen(300, 100, 300, 500, 50);
		sleep(500);
		ScrollGetObjectText1("Wi‑Fi");
		sleep(500);
		/*
		 * if
		 * (!getObjectByID("com.android.settings:id/switch_widget").isChecked())
		 * {
		 * 
		 * clickItemByID("com.android.settings:id/switch_widget"); }
		 */
		sleep(500);
		ScrollGetObjectText("mobile-vip");
		UiObject pw = getObjectByID("com.android.settings:id/password");
		pw.setText("Oneplus12@3.");
		sleep(500);
		clickItem("Connect");
		UiDevice.getInstance().pressBack();
		// 安装adminapk
		// UiAutomatorHelper help = new UiAutomatorHelper();
		// Process p = Runtime.getRuntime().exec("am start
		// com.android.documentsui");

	}

	/**
	 * @throws Exception
	 */
	public void testctsPREH2() throws Exception {
		stopApp();

		// 1.设置系统语言

		clickItem("Settings");

		ScrollGetObjectText("Language & input");
		sleep(500);
		clickItem("System language");
		sleep(500);
		// clickItem("English (US)");
		// unlockScreen(300, 500, 300, 30, 50);
		sleep(500);
		ScrollGetObjectText("English");
		sleep(500);
		// UiDevice.getInstance().pressBack();
		// 2.选择键盘
		clickItem("Current Keyboard");
		sleep(500);
		clickItemByID("android:id/button1");
		UiObject key = new UiObject(new UiSelector().resourceId("android:id/switchWidget").instance(1));
		if (key.isChecked()) {
			key.click();
		}
		sleep(500);

		UiDevice.getInstance().pressBack();
		sleep(500);
		UiDevice.getInstance().pressBack();
		sleep(500);

		// 3.设置display最长时间
		swipetodown();
		sleep(500);
		ScrollGetObjectText("Display");
		clickItem("Screen turns off");
		sleep(500);
		clickItem("30 minutes");
		sleep(500);
		UiDevice.getInstance().pressBack();

		// 4.设置安全无锁屏
		sleep(500);
		ScrollGetObjectText("Password lock");
		sleep(1000);
		clickResourceIDAndText("android:id/title", "Unlock way");
		sleep(500);
		clickItem("None lock screen");
		sleep(1000);
		// clickItemByClassName("android.widget.ImageButton");
		UiDevice.getInstance().pressBack();
		sleep(500);
		// 5.设置时间为12时制
		ScrollGetObjectText("Date & time");
		sleep(500);
		UiObject date = new UiObject(new UiSelector().resourceId("android:id/switchWidget").instance(1));
		if (date.isChecked()) {
			date.click();
		}
		UiDevice.getInstance().pressBack();
		sleep(500);
		// 6.关闭权限管理
		ScrollGetObjectText("Apps and permission");
		sleep(500);
		clickItem("App permission manager");
		UiObject permission = new UiObject(
				new UiSelector().resourceId("com.oneplus.security:id/switch_button").instance(0));
		if (permission.isChecked()) {
			permission.click();
		}
		UiDevice.getInstance().pressBack();
		sleep(500);
		// 7.选择device admin
		clickItem("Device administrators");
		sleep(500);
		UiObject admin1 = new UiObject(new UiSelector().resourceId("com.android.settings:id/checkbox").instance(1));
		if (!admin1.isChecked()) {
			admin1.click();
		}
		sleep(500);
		clickItem("Activate");
		sleep(500);
		UiObject admin2 = new UiObject(new UiSelector().resourceId("com.android.settings:id/checkbox").instance(2));
		if (!admin2.isChecked()) {
			admin2.click();
		}
		sleep(500);
		clickItem("Activate");
		sleep(500);
		UiDevice.getInstance().pressBack();
		sleep(500);
		UiDevice.getInstance().pressBack();
		// 8.高级设置
		ScrollGetObjectText("Advance Settings");
		sleep(500);
		ScrollGetObjectText("Developer options");
		sleep(500);
		UiObject stay = new UiObject(new UiSelector().resourceId("android:id/switchWidget").instance(2));
		if (!stay.isChecked()) {
			stay.click();
		}
		swipeup();
		swipeup();
		/*
		 * UiObject alow = new UiObject(new
		 * UiSelector().resourceId("android:id/switchWidget").instance(2)); if
		 * (!alow.isChecked()) { stay.click(); }
		 */
		UiObject allow = new UiObject(new UiSelector().resourceId("android:id/title").text("Allow mock locations"));
		/*
		 * UiObject allow1=new UiObject(new
		 * UiSelector().className("android.widget.ListView")); UiObject
		 * allow2=allow1.getFromParent(new
		 * UiSelector().className("android.widget.LinearLayout"));
		 */
		sleep(500);
		allow.click();
		sleep(500);
		UiDevice.getInstance().pressBack();
		sleep(500);
		UiDevice.getInstance().pressBack();

		// 9.连接wifi
		// unlockScreen(300, 100, 300, 500, 50);
		sleep(500);
		ScrollGetObjectText1("Wi‑Fi");
		sleep(500);
		// UiObject wifistats = new UiObject(new
		// UiSelector().description("android:id/summary").text("Connected"));
		// if (!wifistats.exists()) {
		ScrollGetObjectText("mobile-vip");
		sleep(500);
		UiObject pw = getObjectByID("com.android.settings:id/password");
		sleep(500);
		pw.setText("Oneplus12@3.");
		sleep(500);
		clickItem("Connect");
		sleep(500);
		UiDevice.getInstance().pressBack();

		// 10.点掉浏览器ok框
		sleep(500);
		clickItem("Browser");
		sleep(1500);
		clickItem("OK");
		sleep(500);

		// 返回主界面，清除后台
		UiDevice.getInstance().pressHome();
		UiDevice.getInstance().pressRecentApps();
		sleep(1000);
		UiObject clearall = getObjectByClassName("android.widget.ImageButton");
		if (clearall.exists()) {
			clearall.click();
		}
	}

	public void testO2ROOT() throws Exception {
		Runtime.getRuntime().exec("am start -n com.android.engineeringmode/.qualcomm.DiagEnabled");
		sleep(500);
		/*
		 * clickItemByID("com.android.dialer:id/floating_action_button");
		 * sleep(500); UiObject
		 * pwd=getObjectByID("com.android.dialer:id/digits");
		 * pwd.setText("*#801#"); sleep(500);
		 */
		clickItem("Privilege Escalate");
		sleep(500);
		UiObject pwd1 = getObjectByID("com.android.engineeringmode:id/password");
		pwd1.setText("angela");
		clickItem("Sure");
		// Runtime.getRuntime().exec("pm disable com.google.android.gms");

		// com.android.engineeringmode/.qualcomm.DiagEnabled
	}

	public void test15801_O2_DOU() throws Exception {
		stopApp();
		Runtime.getRuntime().exec("am start -n com.android.settings/.Settings");
		clickItem("android.widget.ScrollView", "显示", 500);
		clickxy("休眠", 500);
		clickxy("30分钟", 500);

		stopApp();
		Runtime.getRuntime().exec("am start -n com.android.settings/.Settings");
		/*
		 * boolean wumima = ScrollGetObjectText("安全"); boolean wumima1 =
		 * ScrollGetObjectText("密码"); if (wumima) { sleep(500);
		 * ScrollGetObjectText("屏幕锁定方式");
		 * 
		 * sleep(500); clickItem("无"); sleep(500);
		 * UiDevice.getInstance().pressBack();
		 * UiDevice.getInstance().pressBack();
		 * UiDevice.getInstance().pressBack(); } else if(wumima1){
		 * ScrollGetObjectText("密码"); sleep(500); clickItem("解锁方式"); sleep(500);
		 * clickItem("无锁屏"); sleep(500); UiDevice.getInstance().pressBack(); }
		 */

		clickItem("android.widget.ScrollView", "Security & fingerprint", 500);
		clickxy("屏幕锁定方式", 500);
		clickxy("无", 500);
		stopApp();

		Runtime.getRuntime().exec("am start -n com.android.settings/.Settings");
		sleep(500);
		ScrollGetObjectText("WLAN");
		sleep(500);
		ScrollGetObjectText("Oneplus-free");
		sleep(500);
		UiObject pw = getObjectByID("com.android.settings:id/password");
		sleep(500);
		pw.setText("oneplus818");
		sleep(500);
		clickItem("连接");
		sleep(500);
		stopApp();

	}

	public void test15801_O2_DOU_pre() throws Exception {
		Runtime.getRuntime().exec("am start -n com.oneplus.camera/.OPCameraActivity");
		sleep(1000);
		for (int i = 0; i < 5; i++) {
			clickItem("确定");
			clickItem("ok");
			clickItem("不再提示");
			clickItem("确定");
			clickItem("允许");
			clickItem("允许");
			clickItem("立即体验");
			sleep(500);
		}
		UiDevice.getInstance().pressBack();
		stopApp();
		Runtime.getRuntime().exec("am start -n com.android.chrome/com.google.android.apps.chrome.Main");
		sleep(1000);
		for (int i = 0; i < 5; i++) {
			clickItem("确定");
			clickItem("ok");
			clickItem("不再提示");
			clickItem("确定");
			clickItem("允许");
			clickItem("允许");
			clickItem("立即体验");
			sleep(500);
		}
		UiDevice.getInstance().pressBack();
		stopApp();
		Runtime.getRuntime()
				.exec("am start -n com.google.android.music/com.android.music.activitymanagement.TopLevelActivity");
		sleep(1000);
		for (int i = 0; i < 5; i++) {
			clickItem("确定");
			clickItem("ok");
			clickItem("不再提示");
			clickItem("确定");
			clickItem("允许");
			clickItem("允许");
			clickItem("立即体验");
			sleep(500);
		}
		UiDevice.getInstance().pressBack();
		stopApp();
		Runtime.getRuntime().exec("am start -n com.android.documentsui/.DocumentsActivity");
		sleep(1000);
		for (int i = 0; i < 5; i++) {
			clickItem("确定");
			clickItem("ok");
			clickItem("不再提示");
			clickItem("确定");
			clickItem("允许");
			clickItem("允许");
			clickItem("立即体验");
			sleep(500);
		}
		UiDevice.getInstance().pressBack();
		stopApp();
		Runtime.getRuntime().exec("am start -n com.kiloo.subwaysurf/com.kiloo.unityutilities.UnityPluginActivity");
		sleep(1000);
		stopApp();
		Runtime.getRuntime().exec("am start -n com.tencent.mobileqq/.activity.SplashActivity");
		sleep(1000);
		stopApp();
		Runtime.getRuntime().exec("am start -n com.oppo.reader/com.chaozh.iReader.ui.activity.WelcomeActivity");
		sleep(1000);
		stopApp();
		Runtime.getRuntime().exec("am start -n com.netease.newsreader.activity/com.netease.nr.biz.ad.AdActivity");
		sleep(1000);
		stopApp();

	}

	public void test15801_H2_DOU_launcher() throws Exception {
		stopApp();
		// clickItemByID("com.oneplus.hydrogen.launcher:id/active",true);
		clickxy(350, 1529, true);
		watchExeption("时钟");
		watchExeption("天气");
		watchExeption("日历");
		watchExeption("信息");
		sleep(1000);
		unlockScreen(848, 673, 199, 673, 10);
		sleep(1000);
		unlockScreen(848, 673, 199, 673, 10);
		sleep(1000);
		watchExeption("时钟");
		sleep(1000);
		watchExeption("天气");
		sleep(1000);
		watchExeption("日历");
		sleep(1000);
		watchExeption("信息");
		sleep(1000);
		ScrollleftandrightText("爱奇艺视频");
		ScrollleftandrightText("微博");
		ScrollleftandrightText("网易新闻");
		sleep(1000);
		ScrollleftandrightText("阅读");
		ScrollleftandrightText("QQ");
		ScrollleftandrightText("微信");
		ScrollleftandrightText("网易云音乐");
		ScrollleftandrightText("Subway Surf");
		swipToLeft();
		swipToLeft();
		swipToLeft();
		sleep(1000);
		clickItem("Subway Surf");
		sleep(1000);
		clickItem("网易云音乐");
		sleep(1000);
		clickItem("爱奇艺视频");
		sleep(1000);
		clickItem("阅读");
		sleep(1000);
		clickItem("图库");
		sleep(1000);
		clickItem("创建新文件夹");
		back();

	}

	public void test15801_H2_DOU() throws Exception {
		stopApp();
		Runtime.getRuntime().exec("am start -n com.android.settings/.Settings");
		clickItem("android.widget.ListView", "显示", 500);
		clickxy("自动锁定时间", 500);
		clickxy("30分钟", 500);

		stopApp();
		Runtime.getRuntime().exec("am start -n com.android.settings/.Settings");
		clickItem("android.widget.ListView", "指纹和密码", 500);
		clickxy("解锁方式", 500);
		clickxy("无锁屏", 500);
		stopApp();

		Runtime.getRuntime().exec("am start -n com.android.settings/.Settings");
		sleep(500);
		clickItem("android.widget.ListView", "WLAN", 500);
		// ScrollGetObjectText("WLAN");
		sleep(500);
		ScrollGetObjectText("Oneplus-free");
		watchExeption("继续");
		watchExeption("拼音");
		watchExeption("确定");
		sleep(500);
		UiObject pw = getObjectByID("com.android.settings:id/password");
		sleep(500);
		pw.setText("oneplus818");
		sleep(500);
		clickItem("连接");
		sleep(500);
		stopApp();
		Runtime.getRuntime().exec("am start -n com.android.settings/.Settings");
		sleep(500);
		clickItem("android.widget.ListView", "电池", 500);
		UiObject battry = getObjectByID("android:id/switchWidget");
		if (!battry.isChecked()) {
			clickItemByID("android:id/switchWidget");
		}
		stopApp();
		Runtime.getRuntime().exec("am start -n com.android.settings/.Settings");
		sleep(500);
		clickItem("android.widget.ListView", "电池", 500);
		UiObject gps = getObjectByID("android:id/switchWidget");
		if (gps.isChecked()) {
			clickItemByID("android:id/switchWidget");
		}
	}

	public void test15801_H2_DOU_pre() throws Exception {
		// 网易新闻
		stopApp();
		Runtime.getRuntime().exec("am start -n com.netease.newsreader.activity/com.netease.nr.biz.ad.AdActivity");
		sleep(1000);
		watchExeption("不再提示");
		sleep(1000);
		watchExeption("确定");
		sleep(1000);
		watchExeption("始终允许");
		sleep(1000);
		watchExeption("始终允许");
		sleep(1000);
		watchExeption("始终允许");
		sleep(1000);

		// 网易音乐
		stopApp();
		Runtime.getRuntime().exec("am start -n com.netease.cloudmusic/.activity.LoadingActivity");
		sleep(1000);
		watchExeption("确定");
		sleep(1000);
		watchExeption("立即体验");
		sleep(1000);
		watchExeption("确定");
		sleep(2000);
		clickxy(550, 141, 1000);
		watchExeption("本地音乐");
		sleep(2000);
		watchExeption("回到我的音乐");
		sleep(1000);
		watchExeption("You Belong With Me");
		clickxy(83, 1765, 1000);
		sleep(1000);
		clickxy(83, 1765, 1000);
		UiDevice.getInstance().pressBack();
		// 阅读
		stopApp();
		Runtime.getRuntime().exec("am start -n com.oppo.reader/com.chaozh.iReader.ui.activity.WelcomeActivity");
		sleep(1000);
		watchExeption("允许");
		sleep(1000);
		watchExeption("允许");
		swipToRight();
		sleep(1000);
		clickItemByID("com.oppo.reader:id/iv_male");
		sleep(1000);
		watchExeption("跳过");
		clickxy(530, 1522, 1000);
		clickxy(98, 1823);
		sleep(1000);
		watchExeption("导入图书");
		sleep(1000);
		watchExeption("阅读本机图书");
		sleep(1000);
		watchExeption("NormalLife");
		sleep(1000);
		swipToRight();
		swipToRight();
		swipToRight();
		swipToRight();
		swipToLeft();
		swipToLeft();
		swipToLeft();
		swipToLeft();
		swipToLeft();
		watchExeption("确定");
		back();
		back();
		back();
		back();
		back();
		watchExeption("暂不登录");
		// 微博
		stopApp();
		Runtime.getRuntime().exec("am start -n com.sina.weibo/.SplashActivity");
		sleep(1000);

		UiDevice.getInstance().pressBack();
		// 相机
		stopApp();
		Runtime.getRuntime().exec("am start -n com.oneplus.camera/.OPCameraActivity");
		sleep(1000);

		UiDevice.getInstance().pressBack();
		// 浏览器
		stopApp();
		Runtime.getRuntime().exec("am start -n com.android.browser/com.tencent.mtt.SplashActivity");
		sleep(1000);
		watchExeption("不再提醒");
		sleep(1000);
		watchExeption("确定");
		sleep(1000);
		clickxy(468, 948, 500);
		UiObject browser = getObjectByClassName("android.view.View");
		browser.setText("www.baidu.com");
		sleep(500);
		clickxy(987, 134, 500);

		// 游戏
		stopApp();
		Runtime.getRuntime().exec("am start -n com.kiloo.subwaysurf/com.kiloo.unityutilities.UnityPluginActivity");
		UiDevice.getInstance().pressBack();
		// QQ
		stopApp();
		Runtime.getRuntime().exec("am start -n com.tencent.mobileqq/.activity.SplashActivity");
		sleep(1000);
		for (int i = 0; i < 5; i++) {
			watchExeption("确定");
			watchExeption("登录");
			watchExeption("ok");
			watchExeption("不再提示");
			watchExeption("确定");
			watchExeption("允许");
			watchExeption("允许");
			watchExeption("立即体验");
			sleep(500);
		}
		UiDevice.getInstance().pressBack();

		stopApp();
	}

	public static void main(String[] args) {
		String jarName, testClass, testName, androidId;
		jarName = "testdou";
		testClass = "com.zbin.main.myTest";
		testName = "test15801_H2_DOU_launcher";
		androidId = "7";
		new UiAutomatorHelper(jarName, testClass, testName, androidId);
	}
}
