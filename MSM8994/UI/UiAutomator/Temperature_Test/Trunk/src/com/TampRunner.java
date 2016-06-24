package com;


import android.graphics.Rect;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class TampRunner extends UiAutomatorTestCase{
	public static UiDevice ud;
	private static String rpath="/storage/sdcard0/压力测试/压力测试记录.txt";
	private static String ppath="/storage/sdcard0/压力测试/压力测试记录";
	private static String cap_Path="/sys/class/power_supply/battery/capacity";
	private static String vol_Path="/sys/class/power_supply/battery/batt_vol";
	public void testMUsicAndCallDemo()
	{
		ud=getUiDevice();
		CM.createFolder(ppath);
		String model=android.os.Build.MODEL;
		Oppo.record(model);
			String number="112";
			long times=0;//记录运行次数
			
		for(int x=0;x<30000;x++)
		{
			for(int y=0;y<30000;y++)
			{
				try
				{	
					
					Oppo.record("通话开始");
					times=Oppo.onlyCall(times,number);
					Oppo.record("通话结束");
					CM.hold(2000);
					Oppo.startMusic();
					CM.hold(30000);
					Oppo.stopMusic();
					Oppo.record("音乐结束");
				}catch(Exception e)
				{
					Oppo.record("通话结束####wrong");
					CM.stopPackage(Oppo.dailer_Pack);
					CM.stopPackage(Oppo.music_Pack);
					ud.pressBack();
					ud.pressBack();
					ud.pressHome();
				}
			}
		}
	}
//	public void testMusicDemo()//仅仅播放音乐
//	{
//		ud=getUiDevice();
//	CM.createFolder(ppath);
//		for(int x=0;x<30000;x++)
//		{
//			for(int y=0;y<3000;y++)
//			{
//				try
//				{
//					Oppo.startMusic();
//					CM.hold(30000);
//					Oppo.stopMusic();
//					Oppo.record("音乐停止");
//				}catch(Exception e)
//				{
//					ud.pressHome();
//					CM.stopPackage(Oppo.music_Pack);
//					Oppo.record("音乐停止出错####");
//				}
//			}
//		}
//		
//		
//	}
	
//	public void testCallDemo()//紧紧是拨到电话
//	{
//	
//		CM.hold(2000);
//		ud=getUiDevice();
//		CM.scroll(ud, CM.LEFT);
//		CM.hold(1000);
//		CM.scroll(ud, CM.RIGHT);
//		CM.hold(1000);
//		ud.pressHome();
//		CM.createFolder(ppath);
//		String model=android.os.Build.MODEL;
//		Oppo.record(model);
//			String number="120";
//			long times=0;//记录运行次数
//			
//		for(int x=0;x<30000;x++)
//		{
//			for(int y=0;y<30000;y++)
//			{
//				try
//				{	Oppo.record("通话开始");
//					times=Oppo.onlyCall(times,number);
//					Oppo.record("通话结束");
//				}catch(Exception e)
//				{
//					Oppo.record("通话结束####wrong");
//					CM.stopPackage(Oppo.dailer_Pack);
//					ud.pressBack();
//					ud.pressBack();
//					ud.pressHome();
//				}
//			}
//		}
//	}
	public static class Oppo
	{
		private static String cap_Path="/sys/class/power_supply/battery/capacity";
		private static String vol_Path="/sys/class/power_supply/battery/batt_vol";
		
		private static String LowPower="LP";
		private static String PhoneCall="PC";
		private static String Message="ME";
		private static String WeiNote="WN";		
		 
		private static String dailer_Pack="com.android.dialer";
		private static String dailer_Act="com.android.dialer.DialtactsActivity";
		
		private static String music_Pack="com.oppo.music";
		private static String music_Act="com.oppo.music.MainListActivity";
		
		private static String video_Pack="com.oppo.video";
		private static String video_Act="com.oppo.video.VideoListActivity";
		
		private static String camera_Pack="com.oppo.camera";
		private static String camera_Act="com.oppo.camera.Camera";
		
		private static String reader_Pack="com.oppo.reader";
		private static String reader_Act="com.chaozh.iReader.ui.activity.WelcomeActivity";
		private static void startMusic() throws UiObjectNotFoundException
		{
			ud.pressHome();
			CM.startActivity(music_Pack, music_Act);
			CM.waitForNewApp(music_Pack, 1000);
			if(CM.waitForId("android:id/button1", 1000))
				CM.findObjectByRid("android:id/button1", 1000).click();		
			if(CM.waitForId("com.oppo.music:id/control_bar_pause", 1000))
			    CM.findObjectByRid("com.oppo.music:id/control_bar_pause", 1000).click();
			else if(CM.waitForId("com.oppo.music:id/pause", 1000))
			    CM.findObjectByRid("com.oppo.music:id/pause", 1000).click();
			record("音乐开始");
		}
		private static void stopMusic() throws UiObjectNotFoundException
		{
			ud.pressHome();
			CM.startActivity(music_Pack, music_Act);
			CM.waitForNewApp(music_Pack, 1000);
			if(CM.waitForId("com.oppo.music:id/control_bar_pause", 1000))
				CM.findObjectByRid("com.oppo.music:id/control_bar_pause", 1000).click();
		}
		private static long onlyCall(long times,String number)
		{
			try
			{
				CM.startActivity(dailer_Pack, dailer_Act);//启动拨号盘
				if(!CM.waitForNewApp(dailer_Pack, 2000))//判断是否进入拨号盘
					CM.startActivity(dailer_Pack, dailer_Act);
				CM.hold(2000);
				enterNum(number);//输入手机号码
				CM.hold(1000);
				CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();//点击拨号
				
				
				CM.hold(30000);//等待30秒
				if(CM.waitForId("com.android.incallui:id/endButton", 2000))//判断是否还在通话中
					CM.findObjectByRid("com.android.incallui:id/endButton", 1000).click();//点击挂断
				if(CM.waitForId("com.android.dialer:id/dialpad", 2000))
					record(++times+" OK ");
				else
					record(++times+" Error ");
					CM.stopPackage(dailer_Pack);
					ud.pressBack();
					ud.pressBack();
					ud.pressHome();
			}catch(Exception e)
			{
				record(++times+" Error Error ");
				CM.stopPackage(dailer_Pack);
				ud.pressBack();
				ud.pressBack();
				ud.pressHome();
			}
			return times;
			
		}
		private static void enterNum(String number) throws UiObjectNotFoundException
		{
			Rect re;
			if(CM.waitForId("com.android.contacts:id/dialpad", 2000))
				 re=CM.findObjectByRid("com.android.contacts:id/dialpad", 1000).getBounds();
			else
				 re=CM.findObjectByRid("com.android.dialer:id/dialpad", 1000).getBounds();
				
			int l=re.left;
			int t=re.top;
			int w=re.width();
			int h=re.height();
			for(int i=0;i<number.length();i++)
			{
				switch(number.charAt(i))
				{
				case '1':
					ud.click(l+w/6, t+h/8);
					break;
				case '2':
					ud.click(l+w/2, t+h/8);
					break;
				case '3':
					ud.click(l+w*5/6, t+h/8);
					break;
				case '4':
					ud.click(l+w/6, t+h*3/8);
					break;
				case '5':
					ud.click(l+w/2, t+h*3/8);
					break;
				case '6':
					ud.click(l+w*5/6, t+h*3/8);
					break;
				case '7':
					ud.click(l+w/6, t+h*5/8);
					break;
				case '8':
					ud.click(l+w/2, t+h*5/8);
					break;
				case '9':
					ud.click(l+w*5/6, t+h*5/8);
					break;
				case '*':
					ud.click(l+w/6, t+h*7/8);
					break;
				case '0':
					ud.click(l+w/2, t+h*7/8);
					break;
				case '#':
					ud.click(l+w*5/6, t+h*7/8);
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
					c=CM.getText(cap_Path);
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
				}catch(Exception x){}
				CM.writeReport(rpath, t+" Capacity: "+c+"  "+" Voltage: "+v+"  "+txt+"\r\n");
//				ud.takeScreenshot(new File(ppath+"/"+t+".png"));    //去掉截图
//				if(Integer.parseInt(c.trim())<10)
//					ud.takeScreenshot(new File(ppath+"/consumInfo"+t+".png"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
