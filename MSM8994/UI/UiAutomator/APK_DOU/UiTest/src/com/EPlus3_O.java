package com;

import java.io.IOException;

import android.graphics.Rect;

import com.QQ532.QQDemo;
import com.Weixin61.WxDemo;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.core.UiWatcher;

public class EPlus3_O extends PowerRunner {
	public static String cap_Path = "/sys/class/power_supply/battery/capacity";
	public static String vol_Path = "/sys/class/power_supply/battery/batt_vol";

	public static String dailer_Pack = "com.android.dialer";
	public static String dailer_Act = "com.android.dialer.DialtactsActivity ";

	public static String music_Pack = "com.google.android.music";
	public static String music_Act = "com.google.android.music.ui.HomeActivity";

	public static String video_Pack = "com.qiyi.video";
	public static String video_Act = "org.qiyi.android.video.MainActivity";

	public static String camera_Pack = "com.oneplus.camera";
	public static String camera_Act = "";

	public static String reader_Pack = "com.oppo.reader";
	public static String reader_Act = "com.chaozh.iReader.ui.activity.WelcomeActivity";
	
	
	private static String PhoneCall = "PC";

	private static UiDevice ud;
	
	public static void setUd(UiDevice ud) {
		EPlus3_O.ud = ud;
	}

	public static int call(int times, String number)
			throws UiObjectNotFoundException, IOException {
		int count = 0;
		try {
			for (count = 0; count < times; count++) {
				ud.pressHome();
				CM.startActivity(dailer_Pack, dailer_Act);
				// while(!CM.waitForNewApp(dailer_Pack, 2000))
				// CM.startActivity(dailer_Pack, dailer_Act);
				CM.hold(2000);
				CM.findObjectByText("10086", 2000).click();
				CM.hold(30000);
				if (CM.waitForId(
						"com.android.dialer:id/floating_end_call_action_button",
						2000))
					CM.findObjectByRid(
							"com.android.dialer:id/floating_end_call_action_button",
							1000).click();

				// if(!CM.waitForId("com.android.dialer:id/floating_end_call_action_button",
				// 3000))
				// ud.click(ud.getDisplayWidth()/2,
				// ud.getDisplayHeight()*9/10);
				// else
				// CM.findObjectByRid("com.android.dialer:id/floating_end_call_action_button",
				// 1000).click();
				CM.hold(3000);
				if (CM.waitForNewApp(dailer_Pack, 1000)) {
					if (count % 5 == 0)
						record(count + " OK");
					// CM.findObjectByText("挂断", 500).click();
					// ud.click(ud.getDisplayWidth()/2,
					// ud.getDisplayHeight()*9/10);
				} else
					record(count + " Error");
				CM.waitForId("com.android.dialer:id/floating_action_button",
						3000);
				ud.pressBack();
				ud.pressBack();
				CM.hold(5000);
			}
		} catch (Exception Ex) {
			record(count + " Error");
			ud.pressHome();
			CM.stopPackage(dailer_Pack);
			return times - count;
		}
		return 0;
	}

	public static String getProviderNumber() {
		String providerNum = "10086";
		return providerNum;
	}

	public static int gameTest(int times) throws IOException,
			UiObjectNotFoundException {
		watchPhone();
		int count = 0;
		try {
			CM.startPackage("com.kiloo.subwaysurf");
			int x = ud.getDisplayWidth();
			int y = ud.getDisplayHeight();
			CM.waitForNewApp("com.kiloo.subwaysurf", 5000);
			for (count = 0; count < times; count++) {
				ud.click(x / 2, y * 2 / 3);
				CM.hold(55000);
				if (CM.waitForClass("android.view.View", 500)) {
					if (count % 5 == 0)
						record(count + " OK");
				} else {
					record(count + " Error");
					ud.pressHome();
					CM.stopPackage("com.kiloo.subwaysurf");
					break;
				}
			}
			// if(CM.waitForClass("android.view.View",
			// 1000)&&CM.waitForNewApp("com.kiloo.subwaysurf", 1000))
			// {
			// ud.pressBack();
			// CM.hold(2000);
			// ud.click(x/8, y*17/18);
			// CM.hold(2000);
			// ud.click(x*3/4, y*11/18);
			// CM.hold(2000);
			// ud.pressBack();
			// if(CM.waitForId("android:id/button1", 1000))
			// CM.findObjectByRid("android:id/button1", 1000).click();
			// CM.waitForNewApp("com.oppo.launcher", 5000);
			// }
			ud.pressHome();
			CM.stopPackage("com.kiloo.subwaysurf");
		} catch (Exception ex) {
			record(count + " Error");
			ud.pressHome();
			CM.stopPackage("com.kiloo.subwaysurf");
		}
		return times - count - 1;
	}
	
	public static void watchPhone() {
		ud.registerWatcher(PhoneCall, new UiWatcher() {
			@Override
			public boolean checkForCondition() {
				// TODO Auto-generated method stub
				if (CM.waitForId(
						"com.android.phone:id/incomingCallWidget_photo",
						600)) {
					Rect r;
					try {
						r = CM.findObjectByRid(
								"com.android.phone:id/incomingCallWidget_photo",
								600).getBounds();
						ud.drag(r.centerX(), r.centerY(), 10, r.centerY(),
								3);
						if (CM.waitForId(
								"com.android.phone:id/missed_call_buttons",
								600))
							CM.findObjectByRid(
									"com.android.phone:id/missed_call_close",
									600).click();
						return true;
					} catch (UiObjectNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if (CM
						.waitForId(
								"com.android.phone:id/oppo_half_incoming_call_answer",
								600)) {
					try {
						CM.findObjectByRid(
								"com.android.phone:id/oppo_half_incoming_call_decline",
								600).click();
						if (CM.waitForId(
								"com.android.phone:id/missed_call_buttons",
								600))
							CM.findObjectByRid(
									"com.android.phone:id/missed_call_close",
									600).click();
						return true;
					} catch (UiObjectNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return false;
			}

		});
		ud.runWatchers();
	}
	
	
	public static void watchLowPower() {
		ud.registerWatcher("电量", new UiWatcher() {

			@Override
			public boolean checkForCondition() {
				// TODO Auto-generated method stub
				if (CM.waitForSubText("电量", 1000)) {
					try {
						CM.findObjectByText("确定", 1000).click();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return true;
				}
				return false;
			}

		});
		ud.runWatchers();
	}

	
	public static int qqTest(int times) throws Exception {
		watchPhone();
		int count = 0;
		try {
			QQDemo.enterQQ();
			if (!CM.waitForId("android:id/tabhost", 1000)) {
				CM.hold(45000);
				ud.pressBack();
				ud.pressBack();
				ud.pressBack();
				return times - count - 1;
			}
			CM.waitForId("android:id/tabhost", 1000);
			QQDemo.openChat();
			if (!CM.waitForId("com.tencent.mobileqq:id/input", 1000)) {
				CM.findObjectByRid("com.tencent.mobileqq:id/fun_btn", 1000)
						.click();
				CM.waitForId("com.tencent.mobileqq:id/input", 1000);
			}
			UiObject editText = CM.findObjectByRid(
					"com.tencent.mobileqq:id/input", 1000);
			for (count = 0; count < times; count++) {
				for (int j = 0; j < 3; j++) {
					editText.click();
					input();
					CM.findObjectByText("发送", 1000).click();
				}
				CM.findObjectByRid("com.tencent.mobileqq:id/fun_btn", 1000)
						.click();
				CM.waitForId("com.tencent.mobileqq:id/press_to_speak_iv",
						2000);
				CM.findObjectByRid(
						"com.tencent.mobileqq:id/press_to_speak_iv", 1000)
						.dragTo(CM.findObjectByRid(
								"com.tencent.mobileqq:id/press_to_speak_iv",
								1000), 200);
				CM.findObjectByRid("com.tencent.mobileqq:id/fun_btn", 1000)
						.click();
				if (CM.waitForId("com.tencent.mobileqq:id/input", 2000)) {
					if (count % 5 == 0)
						record(count + " OK");
				} else {
					record(count + " Error");
					ud.pressHome();
					CM.stopPackage("com.tencent.mobileqq");
					break;
				}
			}
			if (CM.waitForId("com.tencent.mobileqq:id/fun_btn", 2000)) {
				ud.pressBack();
				ud.pressBack();
				ud.pressBack();
				ud.pressBack();
			}
		} catch (Exception ex) {
			record(count + " Error");
			ud.pressHome();
			CM.stopPackage("com.tencent.mobileqq");
		}
		return times - count - 1;
	}
	
	
	
	
	

	public static int wxTest(int times) throws Exception {
		int count = 0;
		CM.startActivity("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
		CM.waitForText("通讯录", 1000);
		CM.hold(3000);
		if (CM.waitForText("注册", 1000)) {
			CM.hold(45000);
			WxDemo.quit();
			return times - count - 1;
		}

		try {
			Rect rc = CM.findObjectByText("通讯录", 1000).getBounds();
			ud.click(rc.centerX(), rc.top);
			CM.waitForText("新的朋友", 1000);
			CM.findObjectByRid("com.tencent.mm:id/ex", 1000).click();
			CM.waitForText("发消息", 1000);
			CM.findObjectByText("发消息", 1000).click();
			if (!CM.waitForId("com.tencent.mm:id/r2", 500)) {
				if (CM.waitForText("按住 说话", 500))
					CM.findObjectByRid("com.tencent.mm:id/r0", 500).click();
				CM.waitForId("com.tencent.mm:id/r2", 1000);
			}
			UiObject editBox = CM.findObjectByRid("com.tencent.mm:id/r2", 1000);
			for (count = 0; count < times; count++) {
				for (int j = 0; j < 3; j++) {
					editBox.click();
					input();
					CM.findObjectByText("发送", 1000).click();
				}
				CM.findObjectByRid("com.tencent.mm:id/r0", 1000).click();
				CM.waitForText("按住 说话", 2000);
				CM.findObjectByRid("com.tencent.mm:id/r4", 1000).dragTo(
						CM.findObjectByRid("com.tencent.mm:id/r4", 1000), 200);// 50
																				// steps
																				// last
																				// 1
																				// second
				CM.findObjectByRid("com.tencent.mm:id/r0", 1000).click();
				CM.waitForId("com.tencent.mm:id/r2", 2000);
				if (CM.waitForId("com.tencent.mm:id/r2", 1000)) {
					if (count % 5 == 0)
						record(count + " OK");
				} else {
					record(count + " Error");
					ud.pressHome();
					CM.stopPackage("com.tencent.mm");
					break;
				}
			}
			if (CM.waitForId("com.tencent.mm:id/r2", 1000)) {
				WxDemo.quit();
				ud.pressBack();
			}
		} catch (Exception ex) {
			record(count + " Error" + ex);
			ud.pressHome();
			CM.stopPackage("com.tencent.mm");
		}
		return times - count - 1;
	}

	public static int videoTest(int times) throws IOException,
			UiObjectNotFoundException {
		ud.pressHome();
		int count = 0;
		try {
			CM.startActivity(video_Pack, video_Act);
			CM.waitForNewApp(video_Pack, 3000);
			CM.findObjectByRid("com.qiyi.video:id/naviOff", 1000).click();
			CM.findObjectByText("观看本地视频", 1000).click();
			CM.findObjectByRid("com.qiyi.video:id/phone_download_item_title",
					1000).click();
			for (count = 0; count < times; count++) {
				CM.hold(55000);// com.oppo.video:id/surface_view
				if (CM.waitForId("com.qiyi.video:id/player_spitslog_view", 1000)) {
					if (count % 5 == 0)
						record(count + " OK");
				} else {
					record(count + " Error");
					ud.pressHome();
					CM.stopPackage(video_Pack);
					break;
				}
			}
			if (CM.waitForId("com.qiyi.video:id/player_spitslog_view", 1000)) {
				ud.pressBack();
				CM.hold(2000);
				ud.pressBack();
				CM.hold(2000);
				ud.pressBack();
				CM.hold(2000);
				ud.pressBack();
				CM.hold(2000);
				if (CM.waitForId("com.qiyi.video:id/phone_exitpop_exit", 1000))
					CM.findObjectByRid("com.qiyi.video:id/phone_exitpop_exit",
							1000).click();
			}
		} catch (Exception ex) {
			record(count + " Error");
			ud.pressHome();
			CM.stopPackage(video_Pack);
		}
		return times - count - 1;
	}

	public static int readerTest(int times) throws UiObjectNotFoundException,
			IOException {
		startMusic();
		ud.pressHome();
		int count = 0;
		try {
			CM.startPackage(reader_Pack);
			if (CM.waitForId("com.oppo.reader:id/book_cover_id", 1000))
				CM.findObjectByRid("com.oppo.reader:id/book_cover_id", 1000)
						.click();
			else if (CM.waitForId("com.oppo.reader:id/iv_item_view", 1000))
				CM.findObjectByRid("com.oppo.reader:id/iv_item_view", 1000)
						.click();
			CM.waitForId("com.oppo.reader:id/bookview", 5000);
			for (count = 0; count < times; count++) {
				CM.scroll(ud, CM.LEFT);
				CM.hold(9000);
				CM.scroll(ud, CM.RIGHT);
				CM.hold(8000);
				CM.scroll(ud, CM.LEFT);
				CM.hold(9000);
				CM.scroll(ud, CM.RIGHT);
				CM.hold(8000);
				CM.scroll(ud, CM.LEFT);
				CM.hold(9000);
				CM.scroll(ud, CM.RIGHT);
				CM.hold(8000);
				if (CM.waitForId("com.oppo.reader:id/bookview", 1000)) {
					if (count % 5 == 0)
						record(count + " OK");
				} else {
					record(count + " Error");
					ud.pressHome();
					CM.stopPackage(reader_Pack);
					break;
				}
			}
		} catch (Exception ex) {
			record(count + " Error");
			ud.pressHome();
			CM.stopPackage(reader_Pack);
		}
		if (CM.waitForId("com.oppo.reader:id/bookview", 1000)) {
			ud.pressBack();
			CM.hold(1000);
			ud.pressBack();
			CM.waitForNewApp("com.oppo.launcher", 3000);
			ud.pressBack();
		}
		stopMusic();
		return times - count - 1;
	}

	public static int browserTest(int times) throws IOException,
			UiObjectNotFoundException {
		int count = 0;
		try {
			System.out.println("song start activity");
			startMusic();
			ud.pressHome();
			CM.startActivity("com.android.browser",
					"com.tencent.mtt.SplashActivity");
			if (CM.waitForNewApp("com.android.browser", 2000)) {
				CM.waitForId("android:id/content", 1000);
				for (count = 0; count < times; count++) {
					for (int i = 0; i < 4; i++) {
						ud.click(178, 392);
						CM.hold(9000);
						ud.click(755, 1850);
						if (CM.waitForNewApp("com.android.browser", 1000)) {
							if (count % 5 == 0)
								record(count + " OK");
						} else {
							record(count + " Error");
							ud.pressHome();
							CM.stopPackage("com.android.browser");
							break;
						}
						CM.hold(5000);
					}
				}
				if (CM.waitForNewApp("com.android.browser", 1000)) {
					ud.pressBack();
					ud.pressBack();
					ud.pressBack();
					ud.pressBack();
				}
			} else {
				CM.startPackage("com.android.chrome");
				CM.waitForNewApp("com.android.chrome", 2000);
				CM.hold(2000);
				CM.waitForId("com.android.chrome:id/most_visited_layout",
						2000);
				for (count = 0; count < times; count++) {
					for (int i = 0; i < 4; i++) {					
						ud.click(178, 392);
						CM.hold(9000);
						ud.pressBack();
						if (CM.waitForId(
								"com.android.chrome:id/most_visited_layout",
								1000)) {
							record(count+" Ok");
						} else {
							record(count + " Error");
							ud.pressHome();
							CM.stopPackage("com.android.chrome");
							break;
						}
						CM.hold(5000);
					}
				}
				if (CM.waitForNewApp("com.android.chrome", 1000)) {
					ud.pressBack();
					ud.pressBack();
				}
			}
		} catch (Exception ex) {
			record(count + " Error");
			ud.pressBack();
			ud.pressBack();
			ud.pressBack();
		}
		stopMusic();
		return times - count - 1;
	}

	public static int cameraTest(int times) throws UiObjectNotFoundException {
		int count = 0;
		try {

			for (count = 0; count < times; count++) {
				CM.startPackage(camera_Pack);
				CM.hold(5000);
				;
				for (int j = 0; j < 3; j++)// flashlight capture
				{
					CM.findObjectByRid(
							"com.oneplus.camera:id/primary_capture_button",
							1000).click();
					CM.hold(4000);
				}
				CM.hold(3000);
				ud.drag(100, ud.getDisplayHeight() / 2,
						ud.getDisplayWidth() - 100, ud.getDisplayHeight() / 2,
						5);
				if (CM.waitForText("视频", 1000))
					CM.findObjectByText("视频", 1000).click();
				CM.hold(3000);
				CM.findObjectByRid(
						"com.oneplus.camera:id/primary_capture_button", 1000)
						.click();
				CM.hold(30000);
				CM.findObjectByRid(
						"com.oneplus.camera:id/primary_capture_button", 1000)
						.click();
				CM.hold(7000);
				if (CM.waitForId(
						"com.oneplus.camera:id/primary_capture_button", 1000)) {
					ud.drag(100, ud.getDisplayHeight() / 2,
							ud.getDisplayWidth() - 100,
							ud.getDisplayHeight() / 2, 5);
					if (CM.waitForText("照片", 1000))
						CM.findObjectByText("照片", 1000).click();
					ud.pressBack();
					ud.pressBack();
					CM.hold(5000);
					if (count % 5 == 0)
						record(count + " OK");
				} else {
					record(count + " Error");
					ud.pressBack();
					ud.pressBack();
					ud.pressBack();
					break;
				}
			}
		} catch (Exception ex) {
			record(count + " Error");
			ud.pressBack();
			ud.pressBack();
			ud.pressBack();
		}
		return times - count - 1;
	}

	private static void startMusic() throws UiObjectNotFoundException {
		ud.pressHome();
		CM.startPackage(music_Pack);
		CM.waitForNewApp(music_Pack, 1000);
		CM.hold(3000);
		CM.findObjectByRid("com.google.android.music:id/play_pause_header",
				1000).click();
		ud.pressBack();
		ud.pressBack();
	}

	private static void stopMusic() throws UiObjectNotFoundException {
		ud.pressHome();
		CM.startPackage(music_Pack);
		CM.waitForNewApp(music_Pack, 1000);
		CM.hold(3000);
		CM.findObjectByRid("com.google.android.music:id/play_pause_header",
				1000).click();
		ud.pressBack();
		ud.pressBack();
	}

	public static String getEplusIMEI(String pack, String act) {
		String imei = "";
		try {
			CM.startActivity(pack, act);
			if (CM.waitForId("com.android.dialer:id/floating_action_button",
					1000))
				CM.findObjectByRid(
						"com.android.dialer:id/floating_action_button", 2000)
						.click();
			CM.hold(2000);
			enterEplusNum("*#66#");
			CM.waitForId("android:id/list", 2000);
			imei = CM.findObjectByRid("android:id/list", 1000)
					.getChild(new UiSelector().index(1)).getText();
			CM.hold(2000);
			ud.pressBack();
			CM.hold(1000);
			ud.pressBack();
			// enterEplusNum("*#06#");
			// CM.waitForId("android:id/message",1000);
			// imei=new UiObject(new
			// UiSelector().resourceId("android:id/message")).getText();
			// CM.hold(2000);
			// if(CM.waitForId("android:id/button1", 1000))
			// {
			// CM.findObjectByRid("android:id/button1", 1000).click();
			// ud.pressBack();
			// CM.hold(1000);
			// ud.pressBack();
			// }
			// else
			// {
			// ud.pressRecentApps();
			// CM.findObjectByRid("com.android.systemui:id/clear_recents",
			// 1000).click();
			// }
		} catch (Exception e) {
		}

		return imei;
	}

	private static void enterEplusNum(String number)
			throws UiObjectNotFoundException {
		Rect re = CM.findObjectByRid("com.android.dialer:id/dialpad", 1000)
				.getBounds();
		int l = re.left;
		int t = re.top;
		int w = re.width();
		int h = re.height();
		for (int i = 0; i < number.length(); i++) {
			switch (number.charAt(i)) {
			case '1':
				ud.click(l + w / 6, t + h / 10);
				break;
			case '2':
				ud.click(l + w / 2, t + h / 10);
				break;
			case '3':
				ud.click(l + w * 5 / 6, t + h / 10);
				break;
			case '4':
				ud.click(l + w / 6, t + h * 3 / 10);
				break;
			case '5':
				ud.click(l + w / 2, t + h * 3 / 10);
				break;
			case '6':
				ud.click(l + w * 5 / 6, t + h * 3 / 10);
				break;
			case '7':
				ud.click(l + w / 6, t + h * 5 / 10);
				break;
			case '8':
				ud.click(l + w / 2, t + h * 5 / 10);
				break;
			case '9':
				ud.click(l + w * 5 / 6, t + h * 5 / 10);
				break;
			case '*':
				ud.click(l + w / 6, t + h * 7 / 10);
				break;
			case '0':
				ud.click(l + w / 2, t + h * 7 / 10);
				break;
			case '#':
				ud.click(l + w * 5 / 6, t + h * 7 / 10);
				break;
			}
			CM.hold(500);
		}
	}
}