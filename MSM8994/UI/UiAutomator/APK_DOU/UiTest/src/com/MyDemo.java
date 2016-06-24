package com;

import java.io.File;
import java.util.Random;

import android.graphics.Rect;
import android.os.Bundle;
import android.telephony.SignalStrength;


import com.Weixin61.WxDemo;
import com.android.uiautomator.core.Configurator;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class MyDemo extends UiAutomatorTestCase{
	public  static UiDevice ud;
	private static String rpath="/storage/sdcard0/耗电测试/模拟待机测试.txt";
	private static String ppath="/storage/sdcard0/耗电测试/模拟待机测试";
	private static String cap_Path="/sys/class/power_supply/battery/capacity";
	private static String vol_Path="/sys/class/power_supply/battery/batt_vol";
	private static String dailer_Pack="com.android.dialer";
	private static String dailer_Act="com.android.dialer.DialtactsActivity ";
	private static String setting_Pack="com.android.settings";
	private static String setting_Act="com.oppo.settings.SettingsActivity";
	private static String music_Pack="com.oppo.music";
	private int choice=0;
	
	public void testDemo()//用于压力测试
	{
		ud=getUiDevice();
		CM.createFolder(ppath);
		try
    	{
    	    Bundle mBundle=getParams();
    	    String model=android.os.Build.MODEL;
    	    MD.record("机型："+model);
    	    if(mBundle.getString("choice")!=null)
    	    	choice=Integer.parseInt(mBundle.getString("choice"));
    	}catch(Exception ex){}
		switch(choice)
		{
		case 100://使用于一加测试，10秒发一条信息
			for(int x=0;x<1000;x++)
			{
				for(int y=0;y<1000;y++)
				{
					try
					{  
						MD.record("微信开始");
					    int remainingTimes=MD.wxTest(1000);
					    while(remainingTimes>0)
					    	remainingTimes=MD.wxTest(remainingTimes);
						MD.record("微信结束, 进入待机");
					}catch(Exception we)
					{
						MD.record("微信结束####WRONG");
						ud.pressBack();
						ud.pressBack();
					}
				}
			}
			break;
		case 200://使用于一加测试，60秒发一条信息
			for(int x=0;x<1000;x++)
			{
				for(int y=0;y<1000;y++)
				{
					try
					{  
						MD.record("微信开始");
					    int remainingTimes=MD.wxTest1(1000);
					    while(remainingTimes>0)
					    	remainingTimes=MD.wxTest1(remainingTimes);
						MD.record("微信结束, 进入待机");
					}catch(Exception we)
					{
						MD.record("微信结束####WRONG");
						ud.pressBack();
						ud.pressBack();
					}
				}
			}
			break;
		case 300://紧紧是做设置而已，拨打电话112，挂断后播放音乐，循环，用于R7Plus的测试
			
			try{
				//设置为常亮
				CM.startActivity(dailer_Pack, dailer_Act);
				if(!CM.waitForNewApp(dailer_Pack, 2000))
					CM.startActivity(dailer_Pack, dailer_Act);
				SPA.Dailer.enter("*#99#");
				CM.hold(2000);
				CM.stopPackage(dailer_Pack);
				CM.hold(2000);
				//进入设置，去掉屏幕锁屏，亮度为手动模式，关闭自动旋转屏幕；
				CM.startActivity(setting_Pack, setting_Act);
				if(!CM.waitForNewApp(setting_Pack, 2000))
					CM.startActivity(setting_Pack, setting_Act);
				CM.findObjectByMutiAttribut(0,"android.app.ActionBar$Tab",1000).click();//点击常规
				CM.findChildByPosistion(CM.findObjectByRid("android:id/list",1000), new int[]{7,0}).click();
				CM.waitForId("android:id/action_bar_container", 1000);
				CM.scroll(ud, CM.UP);
				CM.hold(2000);
				CM.findChildByPosistion(CM.findObjectByRid("android:id/list",1000),new int[]{6,0}).click();//进入屏幕锁屏
				CM.hold(2000);
				CM.findChildByPosistion(CM.findObjectByRid("android:id/list",1000),new int[]{4,0}).click();//选择无锁屏
				CM.hold(2000);
				ud.pressBack();
				CM.hold(2000);
				CM.findObjectByMutiAttribut(2,"android.app.ActionBar$Tab",1000).click();//点击显示
				CM.findChildByPosistion(CM.findObjectByRid("android:id/list",1000),new int[]{1,0,0}).click();//选择无锁屏
				if(CM.waitForId("com.android.systemui:id/checkbox", 1000))
					CM.findObjectByRid("com.android.systemui:id/checkbox", 1000).click();
				CM.hold(2000);
				CM.findObjectByRid("android:id/button1", 1000).click();
				CM.hold(2000);
				CM.findChildByPosistion(CM.findObjectByRid("android:id/list",1000),new int[]{4,0,2}).click();//选择无锁屏
				ud.pressBack();
				ud.pressBack();
				//进入音乐，点去弹框，及刷新出音乐；
				CM.startPackage(music_Pack);
				if(!CM.waitForNewApp(music_Pack, 1000))
					CM.startPackage(music_Pack);
				if(CM.waitForId("android:id/button1", 1000))
					CM.findObjectByRid("android:id/button1", 1000).click();
				ud.pressMenu();
				CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();//点击扫描本地音乐
				CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 2000).click();
				CM.hold(5000);
				CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 2000).click();
				CM.stopPackage(music_Pack);
				ud.pressBack();
				ud.pressBack();
				ud.pressHome();
			}catch(Exception e){}
			break;
		case 400://拨打电话112，挂断后播放音乐，循环，用于R7Plus的测试
			 int count=0;
			 int counterror=0;
			for(int x=0;x<1000;x++)
			{
				for(int y=0;y<1000;y++)
				{
					try
					{
						CM.startActivity(dailer_Pack, dailer_Act);
						if(!CM.waitForNewApp(dailer_Pack, 2000))
							CM.startActivity(dailer_Pack, dailer_Act);
						SPA.Dailer.enter("112");
						CM.hold(1000);
						CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
						CM.hold(10000);
						if(CM.waitForText("免提", 1000))
						{
							ud.click(ud.getDisplayWidth()/2, ud.getDisplayHeight()*93/100);
						}
						CM.stopPackage(dailer_Pack);
						CM.hold(2000);
						ud.pressHome();
						CM.startPackage(music_Pack);
						if(!CM.waitForNewApp(music_Pack, 1000))
							CM.startPackage(music_Pack);
						CM.findObjectByRid("com.oppo.music:id/control_bar_pause", 2000).click();
						CM.hold(5000);
						CM.stopPackage(music_Pack);
						CM.hold(2000);
						CM.stopPackage(music_Pack);
						MD.record("count: "+count+++" OK");
					}catch(Exception e)
					{
						CM.stopPackage(dailer_Pack);
						CM.stopPackage(music_Pack);
						ud.pressHome();
						MD.record("counterror: "+counterror+++" errror");
					}
				}
			}
			break;
		}
	}
public static class MD
{
	private static String[] args={//
		"howareyou",
		"finethankyou",
		"whereareyoufrom",
		"iamfromhenan",
		"whatdoyoudo",
		"iamateacher",
		"whatisyourfavor",
		"ilikefootballmost",
		"canihelpyou",
		"isayiloveyou",
		"pleasebemyfriend",
		"howoldareyou",
		"iamthirty",
		"whereareyou",
		"iamathome",
		"timeforlunch",
		"goouttogether",
		"remembermeplease",
		"givemeakiss",
		"fucktheqq",
		"trustmeplease",
		"areyoufool",
		"joinusok",
		"leavemealone",
		"whatabeauty",
		"youarebeautiful",
		"iamnothungry",
		"givemeyourhand",
		"tellmeyournumber",
		"callmenow",
		"iwillkillyou",
		"yoursisterde",
		"gameoverboy",
		"timetosleep",
		"goodnightman"
};
	public static void enter(char ch)
	{
		String chs="abcdefghijklmnopqrstuvwxyz";
		if(ch==' ')
			ud.pressKeyCode(62);
		else if(chs.contains(ch+""))
		{
			for(int i=0;i<chs.length();i++)
			{
				if(chs.charAt(i)==ch)
				{
					ud.pressKeyCode(i+29,1);
					
					
				}
			}
		}
			
	}
	private static int wxTest(int times) throws Exception
	{	
		int count =0;
		CM.startActivity("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
		CM.waitForText("通讯录", 1000);
		CM.hold(3000);


		try
		{
			Rect rc=CM.findObjectByText("通讯录", 1000).getBounds();
			ud.click(rc.centerX(), rc.top);
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
			UiObject editBox=CM.findObjectByRid("com.tencent.mm:id/r2", 1000);
			for(count=0;count<times;count++)
			{				
				for(int j=0;j<3;j++)
				{
					editBox.click();
					input();
					CM.findObjectByText("发送", 1000).click();
					CM.hold(10000);
				}
				CM.waitForId("com.tencent.mm:id/r2", 2000);
				if(CM.waitForId("com.tencent.mm:id/r2", 1000))
				{
					if(count%5==0)
					    record(count+" OK");
				}
				else
				{
					record(count+" Error");
					ud.pressHome();
					CM.stopPackage("com.tencent.mm");
					break;
				}
			}
			if(CM.waitForId("com.tencent.mm:id/r2", 1000))
			{		
			    WxDemo.quit();
			    ud.pressBack();
			}
		}catch(Exception ex)
		{
			record(count+" Error"+ex);	
			ud.pressHome();
			CM.stopPackage("com.tencent.mm");				
		}
		return times-count-1;
	}
	private static int wxTest1(int times) throws Exception
	{	
		int count =0;
		CM.startActivity("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
		CM.waitForText("通讯录", 1000);
		CM.hold(3000);


		try
		{
			Rect rc=CM.findObjectByText("通讯录", 1000).getBounds();
			ud.click(rc.centerX(), rc.top);
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
			UiObject editBox=CM.findObjectByRid("com.tencent.mm:id/r2", 1000);
			for(count=0;count<times;count++)
			{				
				for(int j=0;j<3;j++)
				{
					editBox.click();
					input();
					CM.findObjectByText("发送", 1000).click();
					CM.hold(60000);
				}
				CM.waitForId("com.tencent.mm:id/r2", 2000);
				if(CM.waitForId("com.tencent.mm:id/r2", 1000))
				{
					if(count%5==0)
					    record(count+" OK");
				}
				else
				{
					record(count+" Error");
					ud.pressHome();
					CM.stopPackage("com.tencent.mm");
					break;
				}
			}
			if(CM.waitForId("com.tencent.mm:id/r2", 1000))
			{		
			    WxDemo.quit();
			    ud.pressBack();
			}
		}catch(Exception ex)
		{
			record(count+" Error"+ex);	
			ud.pressHome();
			CM.stopPackage("com.tencent.mm");				
		}
		return times-count-1;
	}
	public static void  input()
	{
		long timeout =Configurator.getInstance().getActionAcknowledgmentTimeout();
		Configurator.getInstance().setActionAcknowledgmentTimeout(0);
		Random r=new Random();
		String message=args[r.nextInt(35)];
		for(int i=0;i<message.length();i++)
			enter(message.charAt(i));
		for(int i=0;i<21-message.length();i++)
			ud.pressKeyCode(62);
		Configurator.getInstance().setActionAcknowledgmentTimeout(timeout);
	}
	private static String getEplusIMEI(String pack,String act)
	{
		String imei="";
		try
		{
			CM.startActivity(pack,act);
			if(CM.waitForId("com.android.dialer:id/floating_action_button", 1000))
			CM.findObjectByRid("com.android.dialer:id/floating_action_button", 2000).click();
			CM.hold(2000);
			enterEplusNum("*#06#");
			CM.waitForId("android:id/message",1000);
			imei=new UiObject(new UiSelector().resourceId("android:id/message")).getText();	
			CM.hold(2000);
			if(CM.waitForId("android:id/button1", 1000))
			{
				CM.findObjectByRid("android:id/button1", 1000).click();
				ud.pressBack();
				CM.hold(1000);
				ud.pressBack();
			}
			else
			{
				ud.pressRecentApps();
				CM.findObjectByRid("com.android.systemui:id/clear_recents", 1000).click();
			}
		}
		catch(Exception e){}
		
		return imei;
	}
	private  static void enterEplusNum(String number) throws UiObjectNotFoundException
	{
		Rect re=CM.findObjectByRid("com.android.dialer:id/dialpad", 1000).getBounds();
		int l=re.left;
		int t=re.top;
		int w=re.width();
		int h=re.height();
		for(int i=0;i<number.length();i++)
		{
			switch(number.charAt(i))
			{
			case '1':
				ud.click(l+w/6, t+h/10);
				break;
			case '2':
				ud.click(l+w/2, t+h/10);
				break;
			case '3':
				ud.click(l+w*5/6, t+h/10);
				break;
			case '4':
				ud.click(l+w/6, t+h*3/10);
				break;
			case '5':
				ud.click(l+w/2, t+h*3/10);
				break;
			case '6':
				ud.click(l+w*5/6, t+h*3/10);
				break;
			case '7':
				ud.click(l+w/6, t+h*5/10);
				break;
			case '8':
				ud.click(l+w/2, t+h*5/10);
				break;
			case '9':
				ud.click(l+w*5/6, t+h*5/10);
				break;
			case '*':
				ud.click(l+w/6, t+h*7/10);
				break;
			case '0':
				ud.click(l+w/2, t+h*7/10);
				break;
			case '#':
				ud.click(l+w*5/6, t+h*7/10);
				break;
			}
			CM.hold(500);
		}
	}

	private static void record(String txt)
	{
		try {
			String t=CM.getTime();
			String c="UN";
			String v="UN";
			try
			{
				c=CM.getText("/sys/class/power_supply/battery/capacity");
				try
				{
					v=CM.getText(vol_Path);
					if(v.equals("UN")||v.contains("null"))
					{
						v=CM.getText("/sys/class/power_supply/battery/voltage_now");
					}
				}catch(Exception ex)
				{
					v=CM.getText("/sys/class/power_supply/battery/voltage_now");
				}
			}catch(Exception x){v=CM.getText("/sys/class/power_supply/battery/voltage_now");}
			CM.writeReport(rpath, t+" Capacity:"+c+" Voltage:"+v+" "+txt+"\r\n");
			ud.takeScreenshot(new File(ppath+"/"+t+".png"));
//			if(Integer.parseInt(c.trim())<10)
//				ud.takeScreenshot(new File(ppath+"/consumInfo"+t+".png"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

}
	
	