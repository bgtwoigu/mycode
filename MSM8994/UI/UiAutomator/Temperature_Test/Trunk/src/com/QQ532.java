package com;

import android.graphics.Rect;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class QQ532 extends UiAutomatorTestCase {
	public String curTime = CM.getTime();
	public String reportPath = CM.getRootPath() + "/AutoTest/QQ-" + curTime
			+ ".txt";
	public static UiDevice ud;

	public void testDemo() {
		CM.createFolder(CM.getRootPath() + "/AutoTest");
		ud = getUiDevice();
		ud.pressHome();
		ud.pressHome();
		try {
			QQDemo.run(1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static class QQDemo {
		public static void run(int times) throws Exception {
			for (int i = 0; i < times; i++) {
				enterQQ();
				openChat();
				sendMess("test", 2);
				sendVoice(10, 5, 2);
				sendPicture(1, 2);
				sendImoticon(1, 2);
				sendCapture(3, 2);
				requestVideoChat();
				requestVoiceChat();
				shutChat();
				browseMoment();
				quit();
			}
		}

		public static void enterQQ() throws Exception {
			CM.startActivity("com.tencent.mobileqq",
					"com.tencent.mobileqq.activity.SplashActivity");
			CM.waitForNewApp("com.tencent.mobileqq", 3000);
		}

		// from main page,chose friend list, friend, then chat,
		public static void openChat() throws Exception {
			CM.findObjectByRid("android:id/tabs", 1000)
					.getChild(
							new UiSelector().className(
									"android.widget.RelativeLayout").index(1))
					.click();
			CM.waitForId("com.tencent.mobileqq:id/group_name", 2000);		
			CM.hold(1000);
			
			CM.findObjectByRid("com.tencent.mobileqq:id/group_name", 2,1000).click();
			CM.hold(1000);

			if (!CM.waitForId("com.tencent.mobileqq:id/icon", 1000)) {

				CM.findObjectByRid("com.tencent.mobileqq:id/group_name", 2,1000).click();
				CM.hold(1000);
			}
			
			CM.findObjectByRid("com.tencent.mobileqq:id/icon", 1000).click();
			
			CM.waitForId("com.tencent.mobileqq:id/ivTitleBtnLeft", 2000);
			CM.findObjectByText("发消息", 1000).click();
			if (!CM.waitForId("com.tencent.mobileqq:id/input", 500))
				CM.findObjectByRid("com.tencent.mobileqq:id/fun_btn", 1000)
						.click();
			CM.waitForId("com.tencent.mobileqq:id/input", 500);
		}

		// repeat send the mess, then return main page
		public static void sendMess(String mess, int repeat) throws Exception {
			for (int i = 0; i < repeat; i++) {
				CM.findObjectByRid("com.tencent.mobileqq:id/input", 1000)
						.setText(mess);
				CM.findObjectByRid("com.tencent.mobileqq:id/fun_btn", 1000)
						.click();
			}
			CM.hold(1000);
		}

		// repeat send voice of length "length", with "gap"-long gap,
		public static void sendVoice(int lenght, int gap, int repeat)
				throws Exception {
			CM.findObjectByRid("com.tencent.mobileqq:id/fun_btn", 1000).click();
			CM.waitForId("com.tencent.mobileqq:id/voice_input_btn", 2000);
			for (int i = 0; i < repeat; i++) {
				CM.findObjectByRid("com.tencent.mobileqq:id/press_to_speak_iv",
						1000).dragTo(
						CM.findObjectByRid(
								"com.tencent.mobileqq:id/press_to_speak_iv",
								1000), lenght * 50);
				CM.hold(gap * 1000);
			}
			CM.findObjectByRid("com.tencent.mobileqq:id/fun_btn", 1000).click();
			CM.waitForId("com.tencent.mobileqq:id/input", 2000);
		}

		public static void sendImoticon(int gap, int repeat) throws Exception {
			CM.findObjectByRid("com.tencent.mobileqq:id/emo_btn", 1000).click();
			CM.waitForId("com.tencent.mobileqq:id/0", 2000);
			Rect r = CM.findObjectByClassname(
					"android.support.v4.view.ViewPager", 1000).getBounds();
			for (int i = 0; i < repeat; i++) {
				UiDevice.getInstance().click(r.width() / 14,
						r.top + r.height() / 6);
				CM.findObjectByRid("com.tencent.mobileqq:id/fun_btn", 1000)
						.click();
				CM.hold(gap * 1000);
			}
			CM.findObjectByRid("com.tencent.mobileqq:id/emo_btn", 1000).click();
			CM.hold(2000);
		}

		public static void sendPicture(int gap, int repeat) throws Exception {
			for (int i = 0; i < repeat; i++) {
				if (!CM.waitForId("com.tencent.mobileqq:id/icon_viewPager", 200)) {
					CM.findObjectByRid("com.tencent.mobileqq:id/plus_btn", 1000)
							.click();
					CM.waitForId("com.tencent.mobileqq:id/icon_viewPager", 2000);
				}
				CM.findChildByPosistion(
						CM.findObjectByRid(
								"com.tencent.mobileqq:id/icon_viewPager", 1000),
						new int[] { 0, 0 })
						.getChild(
								new UiSelector().className(
										"android.widget.RelativeLayout").index(
										0)).click();
				CM.waitForId("com.tencent.mobileqq:id/ivTitleBtnLeft", 2000);
				CM.findObjectByRid("com.tencent.mobileqq:id/photo_list_gv",
						1000).getChild(new UiSelector().index(0)).click();
				// CM.findObjectByRid("com.tencent.mobileqq:id/send_btn",
				// 1000).click();
				new UiObject(new UiSelector().textContains("发送")).click();
				CM.waitForId("com.tencent.mobileqq:id/icon_viewPager", 2000);
				CM.hold(gap * 1000);
			}
		}

		public static void sendCapture(int gap, int repeat) throws Exception {
			for (int i = 0; i < repeat; i++) {
				if (!CM.waitForId("com.tencent.mobileqq:id/icon_viewPager", 200)) {
					CM.findObjectByRid("com.tencent.mobileqq:id/plus_btn", 1000)
							.click();
					CM.waitForId("com.tencent.mobileqq:id/icon_viewPager", 2000);
				}
				CM.findChildByPosistion(
						CM.findObjectByRid(
								"com.tencent.mobileqq:id/icon_viewPager", 1000),
						new int[] { 0, 0 })
						.getChild(
								new UiSelector().className(
										"android.widget.RelativeLayout").index(
										1)).click();
				CM.waitForNewApp("com.oppo.camera", 10000);
				CM.findObjectByRid("com.oppo.camera:id/shutter_button", 1000)
						.click();
				CM.waitForId("com.oppo.camera:id/btn_done", 10000);
				CM.findObjectByRid("com.oppo.camera:id/btn_done", 1000).click();
				CM.waitForId("com.tencent.mobileqq:id/photo_preview_right",
						3000);
				CM.findObjectByRid(
						"com.tencent.mobileqq:id/photo_preview_right", 1000)
						.click();
				CM.waitForId("com.tencent.mobileqq:id/icon_viewPager", 2000);
				CM.hold(gap * 1000);
			}
		}

		public static void requestVideoChat() throws Exception {
			if (!CM.waitForId("com.tencent.mobileqq:id/icon_viewPager", 200)) {
				CM.findObjectByRid("com.tencent.mobileqq:id/plus_btn", 1000)
						.click();
				CM.waitForId("com.tencent.mobileqq:id/icon_viewPager", 2000);
			}
			CM.findChildByPosistion(
					CM.findObjectByRid(
							"com.tencent.mobileqq:id/icon_viewPager", 1000),
					new int[] { 0, 1 })
					.getChild(
							new UiSelector().className(
									"android.widget.RelativeLayout").index(2))
					.click();
			CM.waitForId("com.tencent.mobileqq:id/qav_start_cancel", 5000);
			CM.hold(5000);
			if (CM.waitForId("com.tencent.mobileqq:id/qav_start_cancel", 500))// 5.1
				CM.findObjectByRid("com.tencent.mobileqq:id/qav_start_cancel",
						1000).click();
			if (CM.waitForId("com.tencent.mobileqq:id/0", 1000))// 5.4
				CM.findObjectByRid("com.tencent.mobileqq:id/0", 1000).click();
			if (CM.waitForId("com.tencent.mobileqq:id/0", 1000))// 5.2
				CM.findObjectByRid("com.tencent.mobileqq:id/0", 1000).click();
			CM.waitForId("com.tencent.mobileqq:id/plus_btn", 3000);
			if (CM.waitForId("com.tencent.mobileqq:id/icon_viewPager", 1000))
				CM.findObjectByRid("com.tencent.mobileqq:id/plus_btn", 1000)
						.click();
		}

		public static void requestVoiceChat() throws Exception {
			CM.findObjectByRid("com.tencent.mobileqq:id/plus_btn", 1000)
					.click();
			CM.waitForId("com.tencent.mobileqq:id/icon_viewPager", 2000);
			CM.findChildByPosistion(
					CM.findObjectByRid(
							"com.tencent.mobileqq:id/icon_viewPager", 1000),
					new int[] { 0, 0 })
					.getChild(
							new UiSelector().className(
									"android.widget.RelativeLayout").index(2))
					.click();
			CM.waitForId("com.tencent.mobileqq:id/qav_start_cancel", 5000);
			CM.hold(5000);
			if (CM.waitForId("com.tencent.mobileqq:id/qav_start_cancel", 500))// 5.1
				CM.findObjectByRid("com.tencent.mobileqq:id/qav_start_cancel",
						1000).click();
			if (CM.waitForId("com.tencent.mobileqq:id/0", 1000))// 5.4
				CM.findObjectByRid("com.tencent.mobileqq:id/0", 1000).click();
			if (CM.waitForId("com.tencent.mobileqq:id/0", 1000))// 5.2
				CM.findObjectByRid("com.tencent.mobileqq:id/0", 1000).click();
			CM.waitForId("com.tencent.mobileqq:id/plus_btn", 3000);
			if (CM.waitForId("com.tencent.mobileqq:id/icon_viewPager", 1000))
				CM.findObjectByRid("com.tencent.mobileqq:id/plus_btn", 1000)
						.click();
		}

		public static void shutChat() throws Exception {
			CM.findObjectByRid("com.tencent.mobileqq:id/ivTitleBtnLeft", 1000)
					.click();
			CM.waitForId("com.tencent.mobileqq:id/btns", 2000);
			CM.findObjectByRid("com.tencent.mobileqq:id/ivTitleBtnLeft", 1000)
					.click();
			CM.waitForId("android:id/tabs", 2000);
		}

		public static void browseMoment() throws Exception {
			CM.findObjectByRid("android:id/tabs", 1000)
					.getChild(
							new UiSelector().className(
									"android.widget.RelativeLayout").index(2))
					.click();
			CM.hold(2000);
			CM.findObjectByRid("com.tencent.mobileqq:id/letsIcon", 1000)
					.click();
			CM.waitForId("com.tencent.mobileqq:id/rlCommenTitle", 2000);
			CM.scroll(UiDevice.getInstance(), CM.DOWN);
			CM.scroll(UiDevice.getInstance(), CM.DOWN);
			CM.scroll(UiDevice.getInstance(), CM.DOWN);
			UiDevice.getInstance().pressBack();
			CM.waitForId("com.tencent.mobileqq:id/letsIcon", 2000);
		}

		public static void quit() {
			UiDevice.getInstance().pressBack();
			UiDevice.getInstance().pressBack();
			UiDevice.getInstance().pressBack();
		}
	}
}