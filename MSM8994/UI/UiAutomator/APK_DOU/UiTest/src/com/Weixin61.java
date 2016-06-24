package com;

import android.graphics.Rect;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class Weixin61 extends UiAutomatorTestCase{
	public static String curTime=CM.getTime();
	public static String reportPath=CM.getRootPath()+"/AutoTest/WEIXIN-"+curTime+".txt";
	public static UiDevice ud;
	public void testDemo(){
		CM.createFolder(CM.getRootPath()+"/AutoTest");
    	ud=getUiDevice();
    	ud.pressHome();
    	ud.pressHome();
    	try {
			WxDemo.run(1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static class WxDemo{
		public static void run(int times) throws Exception
		{
			for(int i=0;i<times;i++)
			{
				try
				{
					enterWeixin();
					openChat();
					sendMess("test",2);
					sendVoice(10,5,2);
					sendMV(3,2);
					sendPicture(1,2);
					sendImoticon(1,2);
					sendCapture(3,2);
					sendLocation(2,2);
					requestVideoChat();
					shutChat();			
					browseMoment(3);
					quit();
				}catch(Exception ex)
				{
					CM.writeReport(reportPath, ex.toString());
					quit();
					quit();
				}
			}
		}
		public static void enterWeixin() throws Exception
		{
			CM.startActivity("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
			CM.waitForText("通讯录", 1000);
		}
		public static void openChat() throws Exception
		{
			CM.findObjectByText("通讯录", 1000).click();
			CM.waitForText("新的朋友", 1000);
			CM.findObjectByRid("com.tencent.mm:id/ex", 1000).click();
			CM.waitForText("发消息", 1000);
			CM.findObjectByText("发消息", 1000).click();
			if(!CM.waitForId("com.tencent.mm:id/r2", 500))
			{
				if(CM.waitForText("按住 说话", 500))
					CM.findObjectByRid("com.tencent.mm:id/r0", 500).click();
				CM.waitForId("com.tencent.mm:id/r2", 1000);
			}
		}
		public static void sendMess(String mess, int repeat) throws Exception
		{
			for(int i=0;i<repeat;i++)
			{
			    CM.findObjectByRid("com.tencent.mm:id/r1", 1000).setText(mess);
			    CM.findObjectByText("发送", 1000).click();
			}
			CM.hold(1000);
		}
		//repeat send voice of length "length", with "gap"-long gap,
		public static void sendVoice(int lenght,int gap,int repeat) throws Exception
		{
			CM.findObjectByRid("com.tencent.mm:id/qz", 1000).click();
			CM.waitForText("按住 说话", 2000);
			for(int i=0;i<repeat;i++)
			{
				CM.findObjectByText("按住 说话", 1000).dragTo(CM.findObjectByText("按住 说话", 1000), lenght*50);			
				CM.hold(gap*1000);
			}
			CM.findObjectByRid("com.tencent.mm:id/qz", 1000).click();
			CM.waitForId("com.tencent.mm:id/r1", 2000);
		}
		public static void sendImoticon(int gap,int repeat) throws Exception
		{
			CM.findObjectByRid("com.tencent.mm:id/r2", 1000).click();
			CM.waitForId("com.tencent.mm:id/b_j", 2000);
			for(int i=0;i<repeat;i++)
			{
				CM.findObjectByRid("com.tencent.mm:id/b_l", 1000).click();
				CM.findObjectByText("发送", 1000).click();
				CM.hold(gap*1000);
			}
			CM.findObjectByRid("com.tencent.mm:id/r2", 1000).click();
			CM.hold(2000);
		}
		public static void sendPicture(int gap,int repeat) throws Exception
		{			
			for(int i=0;i<repeat;i++)
			{		
				CM.findObjectByRid("com.tencent.mm:id/r6", 1000).click();
				CM.waitForId("com.tencent.mm:id/fm", 2000);
				CM.findObjectByText("图片", 1000).click();
				CM.waitForId("com.tencent.mm:id/dk", 2000);
				CM.findObjectByRid("com.tencent.mm:id/aex", 1000).click();
				CM.findObjectBySubText("发送", 1000).click();
				CM.waitForId("com.tencent.mm:id/r1", 2000);
				CM.hold(gap*1000);
			}
			CM.hold(2000);
		}
		public static void sendCapture(int gap,int repeat) throws Exception
		{			
			for(int i=0;i<repeat;i++)
			{
				CM.findObjectByRid("com.tencent.mm:id/r6", 1000).click();
				CM.waitForId("com.tencent.mm:id/fm", 2000);
				CM.findObjectByText("图片", 1000).click();
				CM.waitForId("com.tencent.mm:id/dk", 2000);
				CM.findObjectByRid("com.tencent.mm:id/px", 1000).click();
				CM.waitForNewApp("com.oppo.camera", 10000);
				CM.findObjectByRid("com.oppo.camera:id/shutter_button", 1000).click();
				CM.waitForId("com.oppo.camera:id/btn_done", 10000);
				CM.findObjectByRid("com.oppo.camera:id/btn_done", 1000).click();
				CM.waitForText("完成", 5000);
				CM.findObjectByText("完成", 1000).click();
				CM.hold(gap*1000);
			}
			CM.hold(2000);
		}
		public static void sendLocation(int gap,int repeat) throws Exception
		{
			CM.findObjectByRid("com.tencent.mm:id/r6", 1000).click();
			CM.waitForId("com.tencent.mm:id/fm", 2000);			
			for(int i=0;i<repeat;i++)
			{
				CM.findObjectByText("位置", 1000).click();
				CM.waitForText("发送位置", 1000);
				CM.findObjectByText("发送位置", 1000).click();
				CM.waitForText("发送", 3000);
				CM.hold(5000);
				CM.waitForId("com.tencent.mm:id/auq", 10000);
				CM.findObjectByText("发送", 1000).click();
				CM.hold(gap*1000);
			}
			UiDevice.getInstance().pressBack();
			CM.hold(2000);
		}
		public static void sendMV(int gap,int repeat) throws Exception
		{						
			for(int i=0;i<repeat;i++)
			{
				CM.findObjectByRid("com.tencent.mm:id/r6", 1000).click();
				CM.waitForId("com.tencent.mm:id/fm", 2000);
				CM.findObjectByText("小视频", 1000).click();
				CM.waitForText("按住拍", 1000);
				Rect r =CM.findObjectByText("按住拍", 1000).getBounds();
				UiDevice.getInstance().drag(r.centerX(), r.centerY(), r.centerX(), r.centerY(), 500);
				CM.hold(gap*1000);
			}
			CM.hold(2000);
		}
		public static void requestVideoChat() throws Exception
		{
			CM.findObjectByRid("com.tencent.mm:id/r6", 1000).click();
			CM.waitForId("com.tencent.mm:id/fm", 2000);	
			CM.findObjectByText("视频聊天", 1000).click();
			if(CM.waitForText("确定", 1000))
				CM.findObjectByText("确定", 1000).click();
			CM.hold(5000);
			UiDevice.getInstance().click(UiDevice.getInstance().getDisplayWidth()/2, UiDevice.getInstance().getDisplayHeight()*9/10);
			CM.waitForId("com.tencent.mm:id/r1", 5000);
		}
		public static void shutChat() throws Exception
		{
			CM.findObjectByRid("com.tencent.mm:id/d6", 1000).click();
			CM.waitForText("通讯录", 2000);
		}
		public static void browseMoment(int repeat) throws Exception
		{
//			Rect r=CM.findObjectByRid("com.tencent.mm:id/laucher_tab_top", 1000).getBounds();
//			ud.click(r.width()/2, r.centerY());	
//			UiDevice.getInstance().click(UiDevice.getInstance().getDisplayWidth()*5/8, UiDevice.getInstance().getDisplayHeight()*13/14);
			CM.findObjectByText("发现", 1000).click();
			CM.waitForText("朋友圈", 5000);
			CM.findObjectByText("朋友圈", 1000).click();
			CM.waitForId("com.tencent.mm:id/dk", 5000);
			CM.scroll(UiDevice.getInstance(), CM.DOWN);
			CM.scroll(UiDevice.getInstance(), CM.DOWN);
			CM.scroll(UiDevice.getInstance(), CM.DOWN);
			for(int i=0;i<repeat;i++)
			{
				CM.findObjectByRid("android:id/action_bar", 1000).getChild(new UiSelector().index(2)).click();
				CM.waitForText("照片", 5000);
				CM.findObjectByText("照片", 1000).click();
				CM.waitForText("拍摄照片", 5000);
				CM.findObjectByText("拍摄照片", 1000).click();
				CM.waitForNewApp("com.oppo.camera", 10000);
				CM.findObjectByRid("com.oppo.camera:id/shutter_button", 1000).click();
				CM.waitForId("com.oppo.camera:id/btn_done", 10000);
				CM.findObjectByRid("com.oppo.camera:id/btn_done", 1000).click();
				CM.waitForId("com.tencent.mm:id/ch", 5000);
				CM.findObjectByText("完成", 1000).click();
				CM.waitForText("发送", 5000);
				CM.findObjectByText("发送", 1000).click();
			}
			CM.waitForId("android:id/action_bar", 2000);
			UiDevice.getInstance().pressBack();
			CM.waitForId("com.tencent.mm:id/dk", 2000);
		}
		public static void quit()
		{
			UiDevice.getInstance().pressBack();
			UiDevice.getInstance().pressBack();
			UiDevice.getInstance().pressBack();
		}
	}
}