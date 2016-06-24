package com;


import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class NBA2015 extends UiAutomatorTestCase{
	
	public static void main(String[] args) {
		String android_id = "8";  //android list target
		String test_class = "com.NBA2015";
		String jar_name = "UiTest";
		String test_name = "testDemo";
		new Debug(jar_name, test_class, test_name, android_id);
	}
	
	
	public void testDemo() throws RemoteException {
		gameTest(1);
	}
	
	public static void gameTest(int times) {
		/**进入比赛模式*/
		UiDevice ud = UiDevice.getInstance();
		for (int i = 0; i < times; i++) {
			CM.startActivity("com.t2ksports.nba2k15android", ".gameactivity");
			CM.hold(20000);
			for (int j = 0; j < 8; j++) {
				ud.click(940, 600);
				CM.hold(5000);
			}
			CM.waitForClass("android.view.View", 1000);
			CM.hold(240000);
//			CM.hold(60000);
		}
		CM.stopPackage("com.t2ksports.nba2k15android");
	}
	
}
