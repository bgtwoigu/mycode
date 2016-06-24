package com;

import java.io.File;
import java.util.Random;

import android.graphics.Rect;
import android.os.Bundle;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;  
  
public class Foreign2Runner extends UiAutomatorTestCase {
	public String curTime=CM.getTime();
	public String reportPath="";
	public String reportFolder=CM.getRootPath()+"/AutoTest";
	public static String snapPath="";
	public String oldsnap="";
	public String oldsnapfolder=CM.getRootPath()+"/AutoTest/Standard";
	public String snapforlder=CM.getRootPath()+"/AutoTest";
	public String para=CM.getRootPath()+"/AutoTest/cNo.txt";
	public String models=CM.getRootPath()+"/AutoTest/mSq.txt";
	public boolean CHECK=false;
	public String country="en";
	public UiDevice ud;
	public Random mRan=new Random();
	public int[] testSizes={
			34,//0
			40,
			39,
			45,
			35,
			17,//5
			33,
			11,
//			17,
			33,
			27,
			33,//10
			22,
			15,
			28,
			13,
			17,
			21};
	public String[] appNames={
			"Dialer","Contacts","Message","Calendar","Clock","Weather","Music",//6
			"Video",/*"Backup",*/"Filemanager","Settings","Safe","Recorder",//12
			"Desktop","Album","Camera","Notification","Email"
	};
	public String[][] appList={
		{"com.android.dialer", "com.android.dialer.DialtactsActivity"},//0
		{"com.android.contacts","com.android.contacts.activities.PeopleActivity"},
		{"com.android.mms","com.android.mms.ui.ConversationList"},
		{"com.android.calendar","com.android.calendar.AllInOneActivity"},
		{"com.oppo.alarmclock","com.oppo.alarmclock.AlarmClock"},
		{"com.oppo.weather","com.oppo.weather.OppoMainActivity"},//5
		{"com.oppo.music","com.oppo.music.MainListActivity"},
		{"com.oppo.video","com.oppo.video.VideoListActivity"},
//		{"com.oppo.backuprestore","com.oppo.backuprestore.MainActivity"},
		{"com.oppo.filemanager","com.oppo.filemanager.Main"},
		{"com.android.settings","com.oppo.settings.SettingsActivity"},
		{"com.oppo.safe","com.oppo.safe.SecureSafeMainActivity"},//10
		{"oppo.multimedia.soundrecorder","oppo.multimedia.soundrecorder.RecorderActivity"},
		{},//desktop
		{"com.oppo.gallery3d","com.oppo.gallery3d.app.Gallery"},
		{"com.oppo.camera","com.oppo.camera.Camera"},
		{},//notification
		{"com.android.email","com.android.email.activity.Welcome"}
	};
  
	public String[] languegeList={
			
	};
	// TODO Notice: set system language English before running the test.
    public void testDemo(){
    	int step=0;
//    	try
//    	{
////    	    country=CM.readTxt(para).trim();
//    		country=CM.getRegion();
//    	}catch(Exception e)
//    	{
//    		
//    	}
//    	int[] testApps=readTestApps();//read target apps to test
    	int choice=0;
    	int cycle=1;
    	String[] series=null;
    	try
    	{
    		Bundle mBundle=getParams();
    		String s=mBundle.getString("order");
    		series=s.split("#");
    		if(mBundle.getString("region")!=null)
    		    country=mBundle.getString("region");
    	}catch(Exception e)
    	{
    		outputErr("error-"+step,e.toString());
    		return;
    	}
    	
    	CM.createFolder(reportFolder+"/"+country);
    	reportPath=reportFolder+"/"+country+"/"+country+"_report.txt";
    	ud=getUiDevice();   	
    	try
    	{
    		ud.pressHome();
    		CM.hold(1000);
//    		setLanguege();
    		choice=Integer.parseInt(series[0]);
    		cycle=Integer.parseInt(series[1]);
    		if(choice==100)
    			return;
        	for(int k=0;k<cycle;k++)//series.length
        	{
//        		choice=Integer.parseInt(series[k]);
        		ud.pressBack();
        		ud.pressBack();
        		ud.pressHome();
        		snapPath=snapforlder+"/"+country+"/"+appNames[choice];
        		CM.createFolder(snapPath);
        		oldsnap=oldsnapfolder+"/"+country+"/"+appNames[choice];
            	if(new File(oldsnap).exists())
            		CHECK=true;
        		CM.hold(1500);
        		String model=android.os.Build.MODEL;
        		if(model.contains("plusf"))
        		{
        			switch(choice)//R7plusf共用的测试
        			{

                	case 12:
                		ud.pressHome();
                		ud.pressHome();
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo_R7plusf.DesktopDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
    	        		break;
                	case 0:
                		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo_R7plusf.DialerDemo.test(ud, step);//拨号测试    xuxin
    		        			check("step"+step);
    		        		}
                		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
    	        		break;
                	case 1:
                		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)//联系人测试
    		        		{
    		        			Oppo_R7plusf.ContactDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
    	        		break;
                	case 2:
                		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo_R7plusf.MessageDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
    	        		break;
                	case 3:
                		CM.startActivity(appList[choice][0], appList[choice][1]);//日历测试
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo_R7plusf.CalendarDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                		break;
                	case 4:
                		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    	        		for(step=1;step<=testSizes[choice];step++)
    	        		{
    	        			Oppo_R7plusf.AlarmDemo.test(ud, step);
    	        			check("step"+step);
    	        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                		break;
                	case 5:
                		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    	        		for(step=1;step<=testSizes[choice];step++)
    	        		{
    	        			Oppo_R7plusf.WeatherDemo.test(ud, step);
    	        			check("step"+step);
    	        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                		break;
                	case 6:
                		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo_R7plusf.MusicDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                		break;
                	case 7:
                		CM.startPackage("com.oppo.camera");            		
                		CM.waitForId("com.oppo.camera:id/oppo_video_lab", 5000);
                		CM.findObjectByRid("com.oppo.camera:id/oppo_video_lab", 1000).click();
                		CM.hold(2000);
                		CM.findObjectByRid("com.oppo.camera:id/shutter_button", 1000).click();
            			CM.hold(5000);
            			ud.pressBack();
            			CM.hold(2000);
            			ud.pressBack();
            			CM.waitForNewApp("com.oppo.launcher", 15000);
                		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo_R7plusf.VideoDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                		break;
//                	case 8:
//                		com.CM.findObjectByRid("com.oppo.launcher:id/dockbar", 1000).getChild(new UiSelector().index(0)).click();
//                		try
//                		{
//    		        		for(step=1;step<=testSizes[choice];step++)
//    		        		{
//    		        			BackupDemo.test(ud, step);
//    		        			check("step"+step);
//    		        		}
//                   		}catch(Exception ex)
//                		{
//                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
//                		}
//                		break;
                	case 8:
                		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo_R7plusf.FilemanagerDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                		break;
                	case 9:
                		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo_R7plusf.SettingDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                		break;
                	case 10:
                		CM.startActivity(appList[choice][0], appList[choice][1]);            		
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo_R7plusf.SafeDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                		break;
                   	case 11:
                   		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo_R7plusf.RecorderDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                		break;
                   	case 13:
                   		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo_R7plusf.AlbumDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                		break;
                   	case 14:
                   		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo_R7plusf.CameraDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                   		break;
                   	case 15:
                   		ud.drag(ud.getDisplayWidth()/2, 5, ud.getDisplayWidth()/2, ud.getDisplayHeight()/2, 4);
                		try
                		{
    		        		for(step=0;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo_R7plusf.NotificationDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                   		break;
                  	case 16://邮箱
                   		CM.startPackage("com.android.email");
                		try
                		{
    		        		for(step=0;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo_R7plusf.EmailDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                   		break;
                	default:
                		break;
                	
        			}
        		}else
        		{
        			switch(choice)//oppo共用测试
        			{

                	case 12:
                		ud.pressHome();
                		ud.pressHome();
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo.DesktopDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
    	        		break;
                	case 0:
                		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo.DialerDemo.test(ud, step);//拨号测试    xuxin
    		        			check("step"+step);
    		        		}
                		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
    	        		break;
                	case 1:
                		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)//联系人测试
    		        		{
    		        			Oppo.ContactDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
    	        		break;
                	case 2:
                		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo.MessageDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
    	        		break;
                	case 3:
                		CM.startActivity(appList[choice][0], appList[choice][1]);//日历测试
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo.CalendarDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                		break;
                	case 4:
                		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    	        		for(step=1;step<=testSizes[choice];step++)
    	        		{
    	        			Oppo.AlarmDemo.test(ud, step);
    	        			check("step"+step);
    	        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                		break;
                	case 5:
                		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    	        		for(step=1;step<=testSizes[choice];step++)
    	        		{
    	        			Oppo.WeatherDemo.test(ud, step);
    	        			check("step"+step);
    	        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                		break;
                	case 6:
                		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo.MusicDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                		break;
                	case 7:
                		CM.startPackage("com.oppo.camera");            		
                		CM.waitForId("com.oppo.camera:id/oppo_video_lab", 5000);
                		CM.findObjectByRid("com.oppo.camera:id/oppo_video_lab", 1000).click();
                		CM.hold(2000);
                		CM.findObjectByRid("com.oppo.camera:id/shutter_button", 1000).click();
            			CM.hold(5000);
            			ud.pressBack();
            			CM.hold(2000);
            			ud.pressBack();
            			CM.waitForNewApp("com.oppo.launcher", 15000);
                		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo.VideoDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                		break;
//                	case 8:
//                		com.CM.findObjectByRid("com.oppo.launcher:id/dockbar", 1000).getChild(new UiSelector().index(0)).click();
//                		try
//                		{
//    		        		for(step=1;step<=testSizes[choice];step++)
//    		        		{
//    		        			BackupDemo.test(ud, step);
//    		        			check("step"+step);
//    		        		}
//                   		}catch(Exception ex)
//                		{
//                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
//                		}
//                		break;
                	case 8:
                		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo.FilemanagerDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                		break;
                	case 9:
                		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo.SettingDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                		break;
                	case 10:
                		CM.startActivity(appList[choice][0], appList[choice][1]);            		
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo.SafeDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                		break;
                   	case 11:
                   		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo.RecorderDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                		break;
                   	case 13:
                   		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo.AlbumDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                		break;
                   	case 14:
                   		CM.startActivity(appList[choice][0], appList[choice][1]);
                		try
                		{
    		        		for(step=1;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo.CameraDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                   		break;
                   	case 15:
                   		ud.drag(ud.getDisplayWidth()/2, 5, ud.getDisplayWidth()/2, ud.getDisplayHeight()/2, 4);
                		try
                		{
    		        		for(step=0;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo.NotificationDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                   		break;
                  	case 16://邮箱
                   		CM.startPackage("com.android.email");
                		try
                		{
    		        		for(step=0;step<=testSizes[choice];step++)
    		        		{
    		        			Oppo.EmailDemo.test(ud, step);
    		        			check("step"+step);
    		        		}
                   		}catch(Exception ex)
                		{
                			outputErr("error-"+step,appNames[choice]+"-"+step+": "+ex.toString());
                		}
                   		break;
                	default:
                		break;
                	
        			}
        		}
        	}
    		//Enter main sheet

    	}
    	catch(Exception e)
    	{
    		outputErr("error-"+step,e.toString());
    	}
    }
    
    //***************Custom method**********************//
    public int[] readTestApps()
    {
    	try
    	{
	    	String l=CM.readTxt(models).trim();
	    	String[] tokens=l.split("#");
	    	int[] m=new int[tokens.length];
	    	for(int i=0;i<m.length;i++)
	    	{
	    		m[i]=Integer.parseInt(tokens[i]);
	    	}
	    	return m;
    	}catch(Exception e)
    	{
    		return new int[]{26,0,1,2,4,5,6,8,9,12,14,15,24,25};//
//    		return new int[]{1,12,24,25};//
    	}
    }
    public void setLanguege() throws UiObjectNotFoundException
    {
    	String targetLanguege="";
    	boolean flag=false;
    	if(country.contains("TH")||
    			country.contains("ID")||
    			country.contains("VN")||
    			country.contains("MY")||
    			country.contains("MM")||
    			country.contains("TW")||
    			country.contains("HK")||
    			country.contains("BD")||
    			country.contains("TR")||
    			country.contains("KH")||
    			country.contains("BD")||
    			country.contains("LA"))
    		flag=true;                    
    	if(country.contains("PK"))
    		targetLanguege="اردو";
        if(country.contains("IR"))
        	targetLanguege="فارسی";
        if(country.contains("DZ")||country.contains("MA"))
        	targetLanguege="Français";
    	if(country.contains("PH")||country.contains("US"))
    		targetLanguege="English (United States)";
    	if(country.contains("SG")||country.contains("AU")||country.contains("KE")||country.contains("NG"))
    		targetLanguege="English (United Kingdom)";
    	if(country.contains("AE")||
    			country.contains("EG")||
    			country.contains("KW")||
    			country.contains("OM")||
    			country.contains("BH")||
    			country.contains("QA"))
    		targetLanguege="العربية";
        if(country.contains("IN"))
        {
        	if(CM.waitForText("Phone", 1000))
        		targetLanguege="English (United States)";
        	else
        		flag=true;
        }
    	CM.startActivity("com.android.settings", "com.oppo.settings.SettingsActivity");
    	new UiObject(new UiSelector().className("android.app.ActionBar$Tab").index(0)).click();
    	UiScrollable sL=new UiScrollable(new UiSelector().resourceId("android:id/list"));
    	sL.scrollToEnd(5);
    	CM.hold(1000);
    	UiObject ul=CM.findObjectByRid("android:id/list", 1000);
    	ul.getChild(new UiSelector().className("android.widget.LinearLayout").index(ul.getChildCount()-3)).click();
    	CM.waitForId("android:id/up", 1000);
    	if(flag)
    	{
    		targetLanguege=CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(1)).getText();
    		flag=false;
    	}
    	CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(0)).click();
    	CM.hold(3000);
    	sL=new UiScrollable(new UiSelector().resourceId("android:id/list"));
    	sL.scrollTextIntoView("中文 (简体)");
    	CM.hold(1000);
    	CM.findObjectByText("中文 (简体)", 1000).click();
    	CM.hold(2000);
    	CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(0)).click();
    	CM.hold(3000);
    	sL=new UiScrollable(new UiSelector().resourceId("android:id/list"));
    	sL.scrollTextIntoView(targetLanguege);
    	CM.hold(2000);
    	CM.findObjectByText(targetLanguege, 1000).click();
    	CM.hold(1000);
    	ud.pressBack();
    	ud.pressBack();
    	ud.pressBack();
    }
    public void check(String picname)
    {
    	ud.takeScreenshot(new File(snapPath+"/"+picname+".png"));
    	CM.hold(1000);
    	if(CHECK)
    	{
    		if(!CM.comparePic(oldsnap, snapPath, picname,reportPath))
    		{
    			CM.writeReport(reportPath, "country "+country+": "+picname+" not match\r\n");
    		}
    	}
    }
    public void outputErr(String name,String e)
    {
		CM.writeReport(reportPath,e+"\r\n");
		ud.takeScreenshot(new File(snapPath+"/"+name+".png"));
    }
    

    /*****************Model Test Demo***********************/
    //Dialer id 0  length 
    public static class Oppo//oppo通用
    {
    	 public static class DialerDemo
    	    {
    	    	public static void test(UiDevice ud,int step) throws Exception
    	        {
    	        	switch(step)
    	        	{
    	        	case 1://enter dialer
    	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
    	        		break;
    	        	case 2://more options
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		break;
    	        	case 3://settings
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView3", 2000).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 4://speed dial
//    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{1,0}).click();
//    	        		CM.waitForId("com.android.dialer:id/number_key", 2000);
    	        		break;
    	        	case 5://update
    	 //       		ud.pressBack();
    	        		CM.waitForId("android:id/list", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{1,0}).click();
    	        		CM.hold(5000);
    	        		CM.waitForId("android:id/button1", 5000);
    	        		CM.findObjectByRid("android:id/button1",1000).click();
    	        		break;
    	        	case 6://notify on connect
    	        		if(CM.waitForId("android:id/button1", 1000))
    	        			CM.findObjectByRid("android:id/button1", 1000).click();
    	    	    	CM.waitForId("android:id/list", 2000);
    	    		    CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{3,0}).click();
    	    		    CM.waitForId("android:id/button2", 2000);
    	        		break;
    	        	case 7://quick reply
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("android:id/list", 2000);
    	                CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{4,0}).click();
    	            	CM.waitForId("oppo:id/MajorOperationButtonPositive", 2000);
    	        		break;
    	        	case 8://new quick replay
    	        		CM.waitForId("oppo:id/MajorOperationButtonPositive", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/button2", 2000);
    	        		break;
    	        	case 9://delete
    	        		CM.findObjectByRid("android:id/button2", 2000).click();
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 2000).click();
    	        		CM.waitForId("com.android.mms:id/action_select", 2000);
    	        		CM.findObjectByRid("com.android.mms:id/action_select", 2000).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 10://delete confirm
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/button2", 2000);
    	        		break;
    	        	case 11://statistics
    	        		CM.findObjectByRid("android:id/button2", 2000).click();
    	        		CM.hold(1000);
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		ud.pressBack();
    	        		CM.hold(2000);
//    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{7,0}).click();
//    	        		CM.hold(1500);
    	        		break;
    	        	case 12://rest of settings
//    	        		ud.pressBack();
//    	        		CM.hold(1000);
    	        		CM.scroll(ud, CM.UP);
    	        		CM.hold(1000);
    	        		break;
    	        	case 13://anti harassment
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView2", 2000).click();
    	        		CM.waitForId("oppo:id/emptybottle", 2000);
    	        		break;
    	        	case 14://anti harassment settings
    	        		CM.findObjectByRid("com.oppo.blacklist:id/pagertabstrip", 1000).getChild(new UiSelector().index(2)).click();
    	        		CM.hold(1500);
    	        		break;
    	        	case 15://dial
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
    	        		Rect r=CM.findObjectByRid("com.android.dialer:id/buttons", 1000).getBounds();
    	        		ud.click(r.left+r.width()/6, r.top+r.height()/8);
    	        		ud.click(r.left+r.width()/2, r.top+r.height()*7/8);
    	        		ud.click(r.left+r.width()/2, r.top+r.height()*7/8);
    	        		ud.click(r.left+r.width()/6, r.top+r.height()/8);
    	        		ud.click(r.left+r.width()/2, r.top+r.height()*7/8);
    	        		break;
    	        	case 16://add number to new contact
    	        		CM.findObjectByRid("com.android.dialer:id/add_new_contact", 1000).click();
    	        		CM.waitForNewApp("com.android.contacts", 2000);
//    	        		if(CM.waitForId("com.android.contacts:id/account_name", 1000))
//    	        			CM.findObjectByRid("com.android.contacts:id/account_name", 1000).click();
    	        		break;
    	        	case 17://add number to existed contact
    	        		Rect lr=CM.findObjectByRid("com.android.contacts:id/left_btn", 1000).getBounds();
    	        		ud.click(ud.getDisplayWidth()/2, lr.centerY());
    	        		CM.findObjectByRid("com.android.contacts:id/left_btn", 1000).click();
    	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
    	        		CM.findObjectByRid("com.android.dialer:id/add_to_contact", 1000).click();
    	        		CM.waitForNewApp("com.android.contacts", 2000);
    	        		break;
    	        	case 18://call out
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.waitForNewApp("com.android.incallui", 1000);
    	        		break;
    	        	case 19://hang up
    	        		CM.hold(3000);
//    	        		CM.findObjectByRid("com.android.incallui:id/endButton", 1000).click();
    	        		if(CM.waitForId("com.android.dialer:id/dialpad", 500))
    	        			break;
    	        		else
    	        		    ud.click(ud.getDisplayWidth()/2, ud.getDisplayHeight()*92/100);
    	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
    	        		break;
    	        	case 20://calling record options
    	        		CM.findObjectByRid("com.android.dialer:id/calls_type_changge_indicate", 1000).click();
    	        		CM.waitForId("com.android.dialer:id/call_type_checkbox", 2000);
    	        		break;
    	        	case 21://record details
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
    	        		CM.findObjectByRid("com.android.dialer:id/secondary_action_icon", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 22://edit calling record
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000).click();
    	        		CM.waitForId("com.android.dialer:id/select_all_or_none", 2000);
    	        		CM.findObjectByRid("com.android.dialer:id/select_all_or_none", 1000).click();
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		break;
    	        	case 23://add record to contact        		
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
    	        		CM.waitForNewApp("android:id/alertTitle", 2000);
    	        		break;
    	        	case 24://send message to record
    	        		CM.findObjectByRid("oppo:id/select_dialog_listview", 1000).getChild(new UiSelector().index(0)).click();
    	        		CM.waitForId("com.android.contacts:id/left_btn", 2000);
    	        		CM.findObjectByRid("com.android.contacts:id/left_btn", 1000).click();
    	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000).click();
    	        		CM.waitForId("com.android.dialer:id/select_all_or_none", 2000);
    	        		CM.findObjectByRid("com.android.dialer:id/select_all_or_none", 1000).click();
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
    	        		CM.waitForNewApp("android:id/alertTitle", 2000);
    	        		CM.findObjectByRid("oppo:id/select_dialog_listview", 1000).getChild(new UiSelector().index(1)).click();
    	        		CM.waitForNewApp("android:id/up", 2000);
    	        		CM.findObjectByRid("android:id/up", 1000).click();
    	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);        		
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000).click();
    	        		CM.waitForId("com.android.dialer:id/select_all_or_none", 2000);
    	        		CM.findObjectByRid("com.android.dialer:id/select_all_or_none", 1000).click();
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.waitForNewApp("com.android.mms", 2000);
    	        		break;
    	        	case 25://mute
    	        		ud.pressBack();
    	        		while(!CM.waitForId("com.android.dialer:id/dialpad", 1000))
    	        			ud.pressBack();
//    	        		CM.findObjectByRid("com.android.mms:id/oppo_contact_back_bt", 1000).click();
    	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
    	        		r=CM.findObjectByRid("com.android.dialer:id/buttons", 1000).getBounds();
    	        		ud.click(r.left+r.width()/6, r.top+r.height()/8);        		
    	        		ud.click(r.left+r.width()/2, r.top+r.height()*7/8);
    	        		ud.click(r.left+r.width()/2, r.top+r.height()*7/8);
    	        		ud.click(r.left+r.width()/6, r.top+r.height()/8);
    	        		ud.click(r.left+r.width()/2, r.top+r.height()*7/8);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.waitForId("com.android.incallui:id/endButton", 2000);
    	        		CM.findObjectByRid("com.android.incallui:id/muteButton", 1000).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 26://speaker
    	        		CM.findObjectByRid("com.android.incallui:id/muteButton", 1000).click();
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("com.android.incallui:id/audioButton", 1000).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 27://dialpad
    	        		CM.findObjectByRid("com.android.incallui:id/audioButton", 1000).click();
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("com.android.incallui:id/dialpadButton", 1000).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 28://more actions
    	        		CM.findObjectByRid("com.android.incallui:id/dialpadButton", 1000).click();
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("com.android.incallui:id/morebutton", 1000).click();
    	        		CM.waitForId("com.android.incallui:id/oppo_moreButtons_layout", 2000);
    	        		break;
//    	        	case 29://record
//    	        		CM.findObjectByRid("com.android.incallui:id/oppo_moreButtons_layout", 1000).getChild(new UiSelector().index(0)).click();
//    	        		CM.hold(1000);
//    	        		break;
    	        	case 29://contacts           //xuxin1
    	        		CM.findObjectByRid("com.android.incallui:id/oppo_moreButtons_layout", 1000).getChild(new UiSelector().index(0)).click();
    	        		CM.hold(1000);
    	        		
//    	        		CM.findObjectByRid("com.android.incallui:id/oppo_moreButtons_layout", 1000).getChild(new UiSelector().index(1)).click();
//    	        		CM.waitForClass("android.widget.HorizontalScrollView", 2000);
    	        		break;
    	        	case 30://add call
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.incallui:id/dialpadButton", 1000);
    	        		CM.findObjectByRid("com.android.incallui:id/oppo_moreButtons_layout", 1000).getChild(new UiSelector().index(1)).click();
    	        		CM.waitForId("oppo:id/MajorOperationButtonNegative", 2000);
    	        		
    	        		break;
    	        	case 32://notes
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.incallui:id/dialpadButton", 1000);
    	        		CM.findObjectByRid("com.android.incallui:id/oppo_moreButtons_layout", 1000).getChild(new UiSelector().index(2)).click();
    	        		CM.waitForId("com.google.android.gms:id/title", 2000);
    	        		break;
    	        	case 33://delete record
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.incallui:id/dialpadButton", 1000);
    	        		CM.findObjectByRid("com.android.incallui:id/endButton", 1000).click();
    	        		CM.startActivity("com.android.dialer", "com.android.dialer.DialtactsActivity");
    	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000).click();
    	        		CM.waitForId("com.android.dialer:id/select_all_or_none", 2000);
    	        		CM.findObjectByRid("com.android.dialer:id/select_all_or_none", 1000).click();
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView3", 2000).click();
    	        		break;
    	        	case 34://over
//    	        		if(CM.waitForId("android:id/button1", 1000))
//    	        		    CM.findObjectByRid("android:id/button1", 1000).click();
//    	        		else if(CM.waitForNewApp("com.android.contacts", 1000))
//    	        		{
//    	    	    		CM.findObjectByRid("com.android.contacts:id/left_btn", 1000).click();
//    	    	    		CM.waitForId("com.android.dialer:id/dialpad", 2000);
//    	    	    		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
//    	    	    		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
//    	    	    		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000).click();
//    	    	    		CM.waitForId("com.android.dialer:id/select_all_or_none", 2000);
//    	    	    		CM.findObjectByRid("com.android.dialer:id/select_all_or_none", 1000).click();
//    	    	    		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
//    	    	    		CM.waitForId("android:id/button1", 2000);
//    	    	    		CM.findObjectByRid("android:id/button1", 1000).click();
//    	        		}
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    		    		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    		    		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000).click();
    		    		CM.waitForId("com.android.dialer:id/select_all_or_none", 2000);
    		    		CM.findObjectByRid("com.android.dialer:id/select_all_or_none", 1000).click();
    		    		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    		    		CM.waitForId("android:id/button1", 2000);
    		    		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
    	        		ud.pressBack();
    	        		break;
    	        	default:
    	        		break;
    	        	}
    	        }
    	    }
    	    public static class ContactDemo
    	    {
    	    	public static void test(UiDevice ud,int step) throws Exception
    	        {
    	        	switch(step)
    	        	{
    	        	case 1://enter contacts    		
    	        		break;
    	        	case 2://create new contact
    	        		CM.findObjectByRid("com.android.contacts:id/create_contact_button", 2000).click();
    	        		CM.hold(1500);    		
    	        		break;
    	        	case 3://choose SIM or native
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("com.android.contacts:id/location_changge_indicate", 1000).click();
    	        		CM.hold(1500);    		
    	        		break;
    	        	case 4://choose SIM and add group
    	        		CM.findObjectByClassname("android.widget.ListView", 1000).getChild(new UiSelector().className("android.widget.LinearLayout").index(0)).click();
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("com.android.contacts:id/edit_group_view", 1000).click();
    	        		CM.hold(1500);    		
    	        		break;
    	        	case 5://edit group name
//    	       		ud.pressBack();
//    	        		CM.findObjectByRid("com.android.contacts:id/oppo_group_select_dlg_list", 1000).getChild(new UiSelector().index(1)).click();
    	        		CM.hold(3000);
    	        		CM.findObjectByRid("com.android.contacts:id/group_name", 1000).click();
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("com.android.contacts:id/group_name", 1000).click();
    	        		CM.hold(1000);
    	        		CM.setText(ud, CM.findObjectByRid("android:id/alertTitle", 1000), "mygroup");
    	        		CM.hold(1500);    		
    	        		break;
    	        	case 6://edit new contact interface after added group
    	        		if(CM.waitForId("android:id/button1", 1000))
    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.hold(1500);    		
    	        		break;
    	        	case 7://choose group after adding group
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("com.android.contacts:id/edit_group_view", 1000).click();
    	        		CM.hold(1500);    		
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		break;
    	        	case 8://choose native
    	        		CM.findObjectByRid("com.android.contacts:id/location_changge_indicate", 1000).click();
    	        		CM.waitForId("com.android.contacts:id/accountItem", 2000);
    	        		CM.findObjectByClassname("android.widget.ListView", 1000).getChild(new UiSelector().className("android.widget.LinearLayout").index(CM.findObjectByClassname("android.widget.ListView", 1000).getChildCount()-1)).click();
    	        		CM.hold(1500);    		
    	        		break;
    	        	case 9://add icon
//    	        		CM.findObjectByRid("com.android.contacts:id/stub_photo", 1000).click();
//    	        		UiObject choice=CM.findObjectByRid("oppo:id/select_dialog_listview", 1000);    		
//    	        		choice.getChild(new UiSelector().index(0)).click();
//    	        		CM.waitForId("android:id/button_always", 2000);
//    	        		ud.pressBack();
//    	        		CM.hold(1000);
//    	        		CM.findObjectByRid("com.android.contacts:id/stub_photo", 1000).click();
//    	        		choice.getChild(new UiSelector().index(1)).click();
//    	        		ud.pressBack();
//    	        		CM.hold(1000);
    	        		CM.findObjectByRid("com.android.contacts:id/frame", 1000).click();
    	        		CM.waitForId("android:id/alertTitle", 2000);
    	        		break;
    	        	case 10://choose phone type
    	        		ud.pressBack();
    	        		CM.hold(2000);
    	        		UiObject sect=CM.findObjectByRid("com.android.contacts:id/sect_fields", 1000);
    	        		CM.findChildByPosistion(sect, new int[]{0,0,0,0,0,0}).click();
    	        		CM.hold(1500);    		
    	        		break;
    	        	case 11://choose email type
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		sect=CM.findObjectByRid("com.android.contacts:id/sect_fields", 1000);
    	        		CM.findChildByPosistion(sect, new int[]{1,0,0,0,0,0}).click();
    	        		CM.hold(1500);    		
    	        		break;
    	        	case 12://add all more fields
    	        		
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		int i=0;
    	        		while(CM.waitForId("com.android.contacts:id/button_add_field", 2000))
    	        		{
    	        			CM.findObjectByRid("com.android.contacts:id/button_add_field", 2000).click();
    	        			if(CM.waitForId("oppo:id/select_dialog_listview", 2000))
    	        			{
    	        				i++;
    	        				CM.findObjectByRid("oppo:id/select_dialog_listview", 1000).getChild(new UiSelector().index(0)).click();
    	        				if(CM.waitForId("oppo:id/datePicker_btn", 1000))
    	        					CM.findObjectByRid("android:id/button1", 2000).click();
    	        				if(CM.waitForId("com.android.contacts:id/edit_title", 3000))
    	        					CM.scroll(ud, CM.UP);
    	        			}
    	        		}
    	        		CM.hold(1500);    		
    	        		break;
    	        	case 13://AIM list
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		CM.scroll(ud, CM.DOWN);
    	        		CM.scroll(ud, CM.DOWN);
    	        		CM.hold(1000);
    	        		sect=CM.findObjectByRid("com.android.contacts:id/sect_fields", 1000);
    	        		CM.findChildByPosistion(sect, new int[]{2,0,0,0,0,0}).click();
    	        		CM.hold(1500);    		
    	        		break;
    	        	case 14://AIM list lower part
    	        		CM.dragObj(ud, CM.findObjectByRid("oppo:id/select_dialog_listview", 1000), CM.UP, 5);
    	        		CM.hold(1000);
    	        		CM.hold(1500);    		
    	        		break;
    	        	case 15://choose custom aim
    	        		CM.findObjectByRid("oppo:id/select_dialog_listview", 1000).getChild(new UiSelector().index(CM.findObjectByRid("oppo:id/select_dialog_listview", 1000).getChildCount()-1)).click();
    	        		CM.hold(1500);    		
    	        		break;
    	        	case 16://added custom aim
    	        		if(CM.waitForId("android:id/button1", 2000))
    	        		{
    	    	    		ud.pressBack();
    	    	    		CM.hold(1000);
    	    	    		CM.setText(ud, CM.findObjectByRid("com.android.contacts:id/custom_dialog_content", 1000), "myaim");
    	    	    		CM.findObjectByRid("android:id/button1", 1000).click();
    	    	    		CM.hold(1500);
    	    	    		ud.pressBack();
    	    	    		CM.hold(1000);
    	        		}    		    		
    	        		break;
    	        	case 17://address type list    		
    	        		sect=CM.findObjectByRid("com.android.contacts:id/sect_fields", 1000);
    	        		CM.findChildByPosistion(sect, new int[]{4,0,0,0,0,0}).click();
    	        		CM.hold(1500);    		
    	        		break;
    	        	case 18://edit custom address
    	        		CM.findObjectByRid("oppo:id/select_dialog_listview", 1000).getChild(new UiSelector().index(2)).click();
    	        		CM.hold(1500);    		
    	        		break;
    	        	case 19://added custom adress
    	        		if(CM.waitForId("android:id/button1", 2000))
    	        		{
    	    	    		ud.pressBack();
    	    	    		CM.hold(1000);
    	    	    		CM.setText(ud, CM.findObjectByRid("com.android.contacts:id/custom_dialog_content", 1000), "myaddress");
    	    	    		CM.findObjectByRid("android:id/button1", 1000).click();
    	    	    		CM.hold(1500);
    	    	    		ud.pressBack();
    	    	    		CM.hold(1500);
    	        		}    		    		
    	        		break;
    	        	case 20://relation list    
    	        		CM.scroll(ud, CM.UP);
    	        		CM.scroll(ud, CM.UP);
    	        		CM.hold(1000);
    	        		sect=CM.findObjectByRid("com.android.contacts:id/sect_fields", 1000);
    	        		CM.findChildByPosistion(sect, new int[]{3,0,0,0,0,0}).click();
    	        		CM.hold(1500);    		
    	        		break;
    	        	case 21://relation list lower part
    	        		CM.dragObj(ud, CM.findObjectByRid("oppo:id/select_dialog_listview", 1000), CM.UP, 5);
    	        		CM.dragObj(ud, CM.findObjectByRid("oppo:id/select_dialog_listview", 1000), CM.UP, 5);
    	        		CM.hold(1000);    		    		
    	        		break;
    	        	case 22://custom relation
    	        		CM.findObjectByRid("oppo:id/select_dialog_listview", 1000).getChild(new UiSelector().index(CM.findObjectByRid("oppo:id/select_dialog_listview", 1000).getChildCount()-1)).click();
    	        		CM.hold(1500);    		
    	        		break;
    	        	case 23://add custom relation
    	        		if(CM.waitForId("android:id/button1", 2000))
    	        		{
    	    	    		ud.pressBack();
    	    	    		CM.hold(1500);
    	    	    		CM.setText(ud, CM.findObjectByRid("com.android.contacts:id/custom_dialog_content", 1000), "myrelation");
    	    	    		CM.findObjectByRid("android:id/button1", 1000).click();
    	    	    		CM.hold(1500);    		
    	    	    		ud.pressBack();
    	    	    		CM.hold(1500);
    	        		}    		
    	        		break;
    	        	case 24://edit all info of contact and save
    	        		CM.scroll(ud, CM.DOWN);
    	        		CM.scroll(ud, CM.DOWN);
    	        		CM.hold(1000);
    	        		sect=CM.findObjectByRid("com.android.contacts:id/sect_fields", 1000);
    	        		try
    	        		{
    	        		CM.setText(ud, CM.findChildByPosistion(sect, new int[]{0,0,0,0,0,2,0,0}), "12345678901");
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		CM.setText(ud, CM.findChildByPosistion(sect, new int[]{1,0,0,0,0,2,0,0}), "mytestemailcom");
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		CM.setText(ud, CM.findChildByPosistion(sect, new int[]{2,0,0,0,0,2,0,0}), "myaimcount");
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		CM.setText(ud, CM.findChildByPosistion(sect, new int[]{4,0,0,0,0,2,0,0}), "myaddress");
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		CM.setText(ud, CM.findChildByPosistion(sect, new int[]{5,0,0,0,0,0,0,0}), "mynotes");
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		CM.setText(ud, CM.findChildByPosistion(sect, new int[]{6,0,0,0,0,0,0,0}), "mynickname");
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		CM.setText(ud, CM.findChildByPosistion(sect, new int[]{7,0,0,0,0,0,0,0}), "mywebsite");
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		CM.setText(ud, CM.findChildByPosistion(sect, new int[]{3,0,0,0,0,2,0,0}), "myrelationship");
    	        		}catch(Exception ex)
    	        		{
    	        			
    	        		}
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		CM.scroll(ud, CM.DOWN);
    	        		CM.scroll(ud, CM.DOWN);
    	        		CM.scroll(ud, CM.DOWN);
    	        		CM.scroll(ud, CM.DOWN);
    	        		CM.hold(1000);
//    	        		CM.setText(ud, CM.findChildByPosistion(sect, new int[]{0,0,0,0,0,0}), "mycompany");
//    	        		CM.setText(ud, CM.findChildByPosistion(sect, new int[]{0,0,0,0,0,1}), "mytitle");
    	        		UiObject nameEdit=CM.findObjectByRid("com.android.contacts:id/edit_name_fields", 1000);
    	        		CM.setText(ud, CM.findChildByPosistion(nameEdit, new int[]{2,0,0,0,0,0}), "mycompany");
    	        		CM.setText(ud, CM.findChildByPosistion(nameEdit, new int[]{2,0,0,0,0,1}), "mytitle");
//    	        		CM.findChildByPosistion(nameEdit, new int[]{0,0,1,0}).click();
//    	        		CM.findChildByPosistion(nameEdit, new int[]{1,0,1,0}).click();
    	        		CM.hold(1000);
//    	        		try
//    	        		{
//    	        		CM.setText(ud, CM.findChildByPosistion(nameEdit, new int[]{0,0,0,0}), "myprefix");
//    	        		ud.pressBack();
//    	        		CM.hold(1000);
//    	        		CM.setText(ud, CM.findChildByPosistion(nameEdit, new int[]{0,0,0,1}), "mygiven");
//    	        		ud.pressBack();
//    	        		CM.hold(1000);
//    	        		CM.setText(ud, CM.findChildByPosistion(nameEdit, new int[]{0,0,0,2}), "mymiddle");
//    	        		ud.pressBack();
//    	        		CM.hold(1000);
//    	        		CM.setText(ud, CM.findChildByPosistion(nameEdit, new int[]{0,0,0,3}), "myfamilly");
//    	        		ud.pressBack();
//    	        		CM.hold(1000);
//    	        		CM.setText(ud, CM.findChildByPosistion(nameEdit, new int[]{0,0,0,4}), "mysuffix");
//    	        		ud.pressBack();
//    	        		CM.hold(1000);
//    	        		CM.setText(ud, CM.findChildByPosistion(nameEdit, new int[]{1,0,0,0}), "myphoneticfamily");
//    	        		ud.pressBack();
//    	        		CM.hold(1000);
//    	        		CM.setText(ud, CM.findChildByPosistion(nameEdit, new int[]{1,0,0,1}), "myphoneticmiddle");
//    	        		ud.pressBack();
//    	        		CM.hold(1000);
//    	        		CM.setText(ud, CM.findChildByPosistion(nameEdit, new int[]{1,0,0,2}), "myphoneticgiven");
//    	        		ud.pressBack();
//    	        		CM.hold(1000);
//    	        		}catch(Exception ex)
//    	        		{
//    	        			
//    	        		}
//    	        		CM.findObjectByRid("com.android.contacts:id/right_btn", 1000).click();
    	        		CM.hold(1500);    		
    	        		break;
    	        	case 25://send contact card
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		ud.pressBack();
    	        		if(CM.waitForId("android:id/button1", 1000))
    	        		    CM.findObjectByRid("android:id/button1", 1000).click();
    	        		if(CM.waitForId("oppo:id/MajorOperationButtonMore", 1000))
    	        		    CM.findObjectByRid("oppo:id/MajorOperationButtonNeutral", 1000).click();
    	        		else
    	        			CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.hold(1500);    		
    	        		break;
    	        	case 26://delete contact
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		if(CM.waitForId("oppo:id/MajorOperationButtonMore", 1000))
    	        		    CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		else
    	        			CM.findObjectByRid("oppo:id/MajorOperationButtonNeutral", 1000).click();
    	        		CM.hold(1500);    		
    	        		break;
    	        	case 27://contact list
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		ud.pressBack();
    	        		CM.hold(1000);    		
    	        		break;
    	        	case 28://choose contacts to display
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 2000).click();
    	        		CM.waitForId("com.android.contacts:id/radioBtn", 2000);    		
    	        		break;
    	        	case 29://import and export
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 2000).click();
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView2", 2000).click();
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		CM.hold(1000);
    	        		break;
    	        	case 30://contacts setting
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 2000).click();
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView3", 2000).click();
    	        		UiObject list=CM.findObjectByRid("android:id/list", 2000);
    	        		for(i=0;i<4;i++)
    	        		{
    	        			list.getChild(new UiSelector().index(i)).click();
    	        			CM.hold(1000);
    	        			ud.pressBack();
    	        			CM.hold(1000);
    	        		}
    	        		break;
    	        	case 31://group list with added contacts
    	        		ud.pressBack();
    	        		CM.hold(500);
    	        		UiObject tabs=CM.findObjectByRid("android:id/action_bar_container", 1000);
    	        		CM.findChildByPosistion(tabs, new int[]{1,0,0}).click();    		
    	        		break;
    	        	case 32://group by time
    	        		CM.findObjectByRid("com.android.contacts:id/icon", 1000).click();
    	        		CM.hold(500);    		
    	        		ud.pressBack();
    	        		break;
    	        	case 33://add group
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.hold(1000);    		
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("com.android.contacts:id/icon", 1000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 2000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
    	        		CM.waitForId("com.android.contacts:id/menu_mark", 1000);
    	        		CM.findObjectByRid("com.android.contacts:id/menu_mark", 1000).click();
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.waitForId("android:id/button1", 1000);
    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.waitForId("android:id/action_bar_container", 1000);
    	        		break;
    	        	case 34://favorite list
    	        		tabs=CM.findObjectByRid("android:id/action_bar_container", 1000);
    	        		CM.findChildByPosistion(tabs, new int[]{1,0,2}).click();
    	        		CM.waitForId("oppo:id/MajorOperationButtonNegative", 1000);    		
    	        		break;
    	        	case 35://add favorite
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.waitForId("com.android.contacts:id/photo", 1000);    		
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		break;
    	        	case 36://edit contact
    	        		tabs=CM.findObjectByRid("android:id/action_bar_container", 1000);
    	        		CM.findChildByPosistion(tabs, new int[]{1,0,1}).click();
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 2000).click();
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000).click();
    	        		CM.findObjectByRid("com.android.contacts:id/check", 2000).click();
    	        		UiObject bars=CM.findObjectByRid("oppo:id/MajorOperationsGroup", 2000);
    	        		for(i=2;i<3;i++)
    	        		{
    	        			bars.getChild(new UiSelector().index(i)).click();
    	        			CM.hold(500);
    	        			ud.pressBack();
    	        			CM.hold(500);
    	        		}	
    	        		bars.getChild(new UiSelector().index(0)).click();
    	        		CM.hold(500);
    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.waitForId("com.android.contacts:id/create_contact_button", 2000);
    	        		break;
    	        	case 37://import from storage
    	        		CM.findObjectByRid("com.android.contacts:id/import_contacts_button", 1000).click();
    	        		CM.waitForId("com.android.contacts:id/menu_mark", 1000);    		
    	        		break;
    	        	case 38://nearme account
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.contacts:id/create_contact_button", 2000);
    	        		CM.findObjectByRid("com.android.contacts:id/sync_nearMe_account", 1000).click();
    	        		CM.waitForId("com.android.contacts:id/netdisk_button", 2000);    		
    	        		break;
    	        	case 39://sync
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.contacts:id/create_contact_button", 2000);
    	        		CM.findObjectByRid("com.android.contacts:id/add_account_button", 2000).click();
    	        		CM.waitForId("android:id/list", 2000);    		
    	        		break;
    	        	case 40:
    	        		int listLen=CM.findObjectByRid("android:id/list", 1000).getChildCount();
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.contacts:id/import_contacts_button", 2000);
    	        		for(i=0;i<listLen;i++)
    	        		{
    	        			CM.findObjectByRid("com.android.contacts:id/add_account_button", 2000).click();
    	        			CM.waitForId("android:id/list", 5000);
    	        			CM.findObjectByRid("android:id/list", 2000).getChild(new UiSelector().index(i)).getChild(new UiSelector().index(1)).click();
    	        			CM.hold(2000);
    	        			ud.pressBack();
    	        			CM.hold(2000);
    	        			if(!CM.waitForId("com.android.contacts:id/import_contacts_button", 2000))
    	        			{
    	        				ud.pressBack();
    	            			CM.hold(2000);
    	        			}
    	        		}
    	        		CM.scroll(ud, CM.LEFT);
    	        		CM.hold(1000);
    	        		CM.scroll(ud, CM.RIGHT);
    	        		CM.hold(1000);
    	        		break;
    	        	default:
    	        		break;
    	        	}
    	        }
    	    }
    	    public static class MessageDemo
    	    {
    	    	public static void test(UiDevice ud,int step) throws Exception
    	        {
    	        	switch(step)
    	        	{
    	        	case 1://enter message
//    	        		if(CM.waitForId("com.android.mms:id/empty", 1000))
//    	        			break;
//    	        		else
//    	        		{
//    	        			CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
//    	        			CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
//    	        			CM.findObjectByRid("com.android.contacts:id/menu_mark", 1000).click();
//    	        			CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
//    	        			CM.findObjectByRid("android:id/button1", 1000).click();
//    	        			CM.waitForId("com.android.mms:id/empty", 1000);
//    	        		}
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarItemTitle", 1000);
    	        		break;
    	        	case 2://new message
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarItemTitle", 1000).click();
    	        		CM.waitForId("com.android.mms:id/send_button", 2000);
    	        		break;
    	        	case 3://add contact
    	        		CM.findObjectByRid("com.android.mms:id/oppo_contact_bt", 1000).click();
    	        		CM.waitForNewApp("com.android.contacts", 2000);
    	        		break;
    	        	case 4://attachments
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.mms:id/send_button", 2000);
    	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
    	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
    	        		break;
    	        	case 5://emotions
    	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(0).className("android.widget.LinearLayout")).click();
    	        		CM.waitForId("com.android.mms:id/face_view_manager", 2000);
    	        		break;
    	        	case 6://choose pictures
//    	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
//    	        		CM.hold(1000);
//    	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
//    	        		CM.waitForClass("android.widget.GridView", 1000);
    	        		ud.pressBack();
    	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(1).className("android.widget.LinearLayout")).click();        		
    	        		CM.waitForId("android:id/button1", 1000);
    	        		break;
    	        	case 7://
    	        		if(CM.waitForId("android:id/button1", 1000))
    	        		    CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.waitForId("com.android.mms:id/send_button", 2000);
    	        		break;
    	        	case 8:
//    	        		ud.pressBack();
//    	        		CM.waitForId("com.android.mms:id/add_attach", 1000);
//    	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
//    	        		CM.waitForClass("android.widget.GridView", 1000);
//    	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(1).className("android.widget.LinearLayout")).click();
//    	        		CM.waitForId("android:id/button1", 2000);
//    	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(1).className("android.widget.LinearLayout")).click();
//    	        		CM.waitForId("com.google.android.apps.plus:id/tiles", 2000);
    	        		break;
    	        	case 9://capture new picture
//    	        		ud.pressBack();
    	 //       		CM.waitForId("com.android.mms:id/add_attach", 2000);
//    	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
//    	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
//    	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(2).className("android.widget.LinearLayout")).click();
//    	        		CM.waitForNewApp("com.oppo.camera", 2000);
//    	        		CM.hold(2000);
//    	        		while(CM.waitForId("com.oppo.camera:id/guide_view", 1000))
//    	        		{
//    	        		    CM.scroll(ud, CM.UP);
//    	        		}
//    	        		CM.findObjectByRid("com.oppo.camera:id/shutter_button", 3000).click();
//    	        		CM.waitForId("com.oppo.camera:id/btn_done", 1000);
//    	        		CM.findObjectByRid("com.oppo.camera:id/btn_done", 1000).click();
//    	        		CM.waitForId("com.android.mms:id/add_attach", 2000);
    	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(2).className("android.widget.LinearLayout")).click();
    	        		CM.findObjectByRid("com.oppo.camera:id/shutter_button", 2000).click();
    	        		CM.hold(3000);
    	        		CM.findObjectByRid("com.oppo.camera:id/btn_done", 2000).click();
    	        		CM.hold(3000);
    	        		break;
    	        	case 10://insert name and number
    	        		CM.findObjectByRid("com.android.mms:id/remove_image_button", 1000).click();
    	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
    	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
    	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(3).className("android.widget.LinearLayout")).click();
    	        		CM.waitForNewApp("com.android.contacts", 2000);
    	        		break;
    	        	case 11://timing messages
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.mms:id/add_attach", 2000);
    	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
    	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
    	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(4).className("android.widget.LinearLayout")).click();
    	        		CM.waitForId("android:id/button1", 2000);
    	        		break;
    	        	case 12://insert sound
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.mms:id/add_attach", 2000);
    	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
    	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
    	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(5).className("android.widget.LinearLayout")).click();
    	        		CM.waitForNewApp("com.oppo.music", 2000);
    	        		break;
    	        	case 13://insert recording
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.mms:id/add_attach", 2000);
    	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
    	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
    	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(6).className("android.widget.LinearLayout")).click();
    	        		CM.waitForNewApp("oppo.multimedia.soundrecorder", 2000);
    	        		break;
    	        	case 14://insert 影片
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.mms:id/add_attach", 2000);
    	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
    	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
    	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(7).className("android.widget.LinearLayout")).click();
    	        		CM.waitForId("android:id/button1",1000);
    	        		break;
    	        	case 15://插入录像
    	        		if(CM.waitForId("android:id/button1", 1000))
    	        			CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
    	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(8).className("android.widget.LinearLayout")).click();
    	        		CM.waitForId("com.oppo.camera:id/shutter_button", 2000);
    	        		break;
    	        	case 16://
//    	        		ud.pressBack();
//    	        		CM.waitForId("com.android.mms:id/add_attach", 2000);
//    	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
//    	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
//    	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(7).className("android.widget.LinearLayout")).click();
//    	        		CM.waitForId("android:id/button1", 2000);
//    	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(0).className("android.widget.LinearLayout")).click();
//    	        		CM.waitForNewApp("com.google.android.apps.plus", 1000);
    	        		break;
    	        	case 17://insert recording videos
//    	        		ud.pressBack();
    	 //       		CM.waitForId("com.android.mms:id/add_attach", 2000);
//    	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
//    	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
//    	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(8).className("android.widget.LinearLayout")).click();
//    	        		CM.waitForNewApp("com.oppo.camera", 2000);
    	        		break;
    	        	case 18://insert subjects
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.mms:id/add_attach", 2000);
    	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
    	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
    	        		ud.drag(ud.getDisplayWidth()-50, ud.getDisplayHeight()*3/4, 50, ud.getDisplayHeight()*3/4, 5);
    	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(0).className("android.widget.LinearLayout")).click();        		
    	        		CM.waitForId("com.android.mms:id/subject", 2000);
    	        		break;
    	        	case 19://insert slides
    	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
    	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
    	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(1).className("android.widget.LinearLayout")).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 20:
    	        		ud.pressMenu();
    	        		CM.hold(1000);
    	        		break;
    	        	case 21:
    	        		ud.pressMenu();
    	        		CM.waitForId("android:id/up", 1000);
    	        		Rect r=CM.findObjectByClassname("android.widget.ListView", 1000).getChild(new UiSelector().index(0).className("android.widget.LinearLayout")).getBounds();
    	        		ud.click(r.centerX(), r.centerY()+r.height());
    	        		CM.waitForId("android:id/action_bar_overlay_layout", 1000);
    	        		break;
    	        	case 22:
    	        		ud.pressMenu();
    	        		CM.hold(1000);
    	        		break;
    	        	case 23:
    	        		CM.findObjectByMutiAttribut("", "android.widget.LinearLayout", "", 8, 1000).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 24://insert contacts
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.mms:id/add_attach", 2000);
    	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
    	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
    	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(2).className("android.widget.LinearLayout")).click();
    	        		CM.waitForNewApp("com.android.contacts", 2000);
    	        		break;
    	        	case 25://diy emotions
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.mms:id/add_attach", 2000);
    	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
    	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
    	        		ud.drag(50, ud.getDisplayHeight()*3/4, ud.getDisplayWidth()-50, ud.getDisplayHeight()*3/4, 5);
    	        		ud.drag(50, ud.getDisplayHeight()*3/4, ud.getDisplayWidth()-50, ud.getDisplayHeight()*3/4, 5);
    	        		CM.hold(1500);
    	        		break;
    	        	case 26://send message
//    	        		ud.pressBack();
    	        		CM.waitForId("com.android.mms:id/add_attach", 2000);
    	        		CM.setText(ud, CM.findObjectByRid("com.android.mms:id/recipients_editor", 1000), "10086");
    	        		CM.setText(ud, CM.findObjectByRid("com.android.mms:id/embedded_text_editor", 1000), "mss");
    	        		CM.findObjectByRid("com.android.mms:id/send_button", 1000).click();
    	        		CM.waitForId("com.android.mms:id/msg_list_item", 2000);
    	        		break;
    	        	case 27://save number
    	        		if(CM.waitForId("android:id/sms_short_code_confirm_message", 1000))
    	        		{
    	        			CM.findObjectByRid("android:id/sms_short_code_remember_choice_checkbox", 1000).click();
    	        			CM.findObjectByRid("android:id/button1", 1000).click();
    	        		}
    	        		break;
    	        	case 28://edit message
    	        		CM.findObjectByRid("com.android.mms:id/msg_list_item", 1000).longClick();
    	        		CM.waitForId("com.android.mms:id/checkbox", 2000);
    	        		break;
    	        	case 29://delete
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);
    	        		break;
    	        	case 30://forward
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("com.android.mms:id/checkbox", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.waitForId("com.android.mms:id/oppo_back_bt", 2000);
    	        		break;
    	        	case 31://cancel edit
    	        		CM.findObjectByRid("com.android.mms:id/oppo_back_bt", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);
    	        		break;
    	        	case 32://save draft
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.setText(ud, CM.findObjectByRid("com.android.mms:id/recipients_editor", 1000), "10010");
    	        		CM.findObjectByRid("com.android.mms:id/oppo_back_bt", 1000).click();
    	        		CM.waitForId("com.android.mms:id/contacts_actions", 2000);
    	        		ud.pressBack();
    	        		if(!CM.waitForId("com.android.mms:id/oppolistview", 2000))
    	        		{
    	        			ud.pressBack();
    	        			CM.waitForId("com.android.mms:id/oppolistview", 2000);
    	        		}
    	        		break;
    	        	case 33://more options
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		break;
    	        	case 34://block
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
    	        		CM.waitForNewApp("com.oppo.blacklist", 2000);
    	        		break;
    	        	case 35://message settings
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.mms:id/oppolistview", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView2", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 36://last part of settings
    	        		CM.scroll(ud, CM.UP);
    	        		CM.hold(1000);
    	        		break;
    	        	case 37://edit message list
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.mms:id/oppolistview", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
    	        		CM.waitForId("com.android.mms:id/action_select", 2000);
    	        		break;
    	        	case 38://delete
    	        		CM.findObjectByRid("com.android.mms:id/action_select", 1000).click();
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);
    	        		break;
    	        	case 39://over
    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.waitForId("com.android.mms:id/empty", 2000);
    	        		ud.pressBack();
    	        		break;
    	        	default:
    	        		break;
    	        	}
    	        }
    	    }
    	    public static class CalendarDemo
    	    {
    	    	public static void test(UiDevice ud,int step)throws Exception
    	        {
    	        	switch(step)
    	        	{
    	        	case 1://month view
    	        		CM.hold(1000); 
    	        		break;
    	        	case 2://year view
    	        		ud.click(ud.getDisplayWidth()/2, ud.getDisplayHeight()/2);
    	        		CM.hold(2000); 
    	        		CM.startActivity("com.android.calendar", "com.android.calendar.AllInOneActivity");
    	        		CM.hold(2000);
    	        		CM.findObjectByRid("com.android.calendar:id/year_btn", 1000).click();
    	        		CM.waitForId("com.android.calendar:id/year_view_pager", 2000);    		
    	        		break;
    	        	case 3://week view
    	        		CM.findObjectByRid("com.android.calendar:id/week_btn", 1000).click();
    	        		CM.hold(1000);    		
    	        		break;
    	        	case 4://date change
    	        		CM.findObjectByRid("com.android.calendar:id/calendar_date_month_num", 1000).click();
    	        		CM.waitForId("android:id/titleDivider", 2000);    		
    	        		break;
    	        	case 5://empty memos list
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("com.android.calendar:id/month_view_pager", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
    	        		CM.waitForId("oppo:id/MajorOperationsGroup", 2000);    		
    	        		break;
    	        	case 6://import browse
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
    	        		CM.waitForId("com.oppo.filemanager:id/select_folder_list_buttons", 2000);    		
    	        		break;
    	        	case 7://setting
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 1000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
    	        		CM.waitForId("android:id/list", 2000);    		
    	        		break;
    	        	case 8://accounts and sync
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{1,0}).click();
    	        		CM.waitForId("android:id/checkbox", 2000);    		
    	        		break;
    	        	case 9://add account
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{2,0}).click();
    	        		CM.findObjectByRid("android:id/title", 2000);
    	        		break;
    	        	case 10://select default account
    	        		ud.pressBack();
    	        		ud.pressBack();
//    	        		CM.waitForId("oppo:id/MajorOperationButtonPositive", 2000);
//    	        		CM.findObjectByRid("android:id/up", 1000).click();
    	        		CM.waitForId("android:id/action_bar_container", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{2,0}).click();
    	        		CM.waitForId("android:id/button2", 2000);    		
    	        		break;
    	        	case 11://show memos
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("android:id/action_bar_container", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{4,0}).click();
    	        		CM.waitForId("android:id/button2", 2000);    		
    	        		break;
    	        	case 12://home time zone
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("android:id/action_bar_container", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{6,1}).click();
    	        		CM.hold(1000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{7,0}).click();
    	        		CM.waitForId("android:id/button2", 2000);    		
    	        		break;
    	        	case 13://week starts on
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("android:id/action_bar_container", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{6,1}).click();
    	        		CM.hold(1000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{9,0}).click();
    	        		CM.waitForId("android:id/button2", 2000);    		
    	        		break;
    	        	case 14://ring tone
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("android:id/action_bar_container", 2000);
    	        		CM.scroll(ud, CM.UP);
    	        		CM.hold(1000);
    	        		CM.scroll(ud, CM.UP);
    	        		CM.hold(2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{4,0}).click();
    	        		CM.waitForId("com.oppo.filemanager:id/radiobutton", 2000);    		
    	        		break;
    	        	case 15://file browse
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{1,0}).click();
    	        		CM.hold(2000);
    	        		CM.waitForId("com.oppo.filemanager:id/music_file_full", 2000);    		
    	        		break;
    	        	case 16://alert type
//    	        		CM.findObjectByRid("android:id/up", 1000).click();
//    	        		CM.waitForId("com.oppo.filemanager:id/ringtone_editor_bar", 2000);
//    	        		CM.findObjectByRid("android:id/up", 1000).click();
//    	        		CM.waitForId("android:id/widget_frame", 2000);
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		ud.pressBack();
    	        		CM.waitForId("android:id/action_bar_container", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{5,0}).click();
    	        		CM.waitForId("android:id/button2", 2000);    		
    	        		break;
    	        	case 17://reminder time
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("android:id/action_bar_container", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{6,0}).click();
    	        		CM.waitForId("android:id/button2", 2000);    		
    	        		break;
    	        	case 18://quick response
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("android:id/action_bar_container", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{7,0}).click();
    	        		CM.waitForId("com.android.calendar:id/main_frame", 2000);    		
    	        		break;
    	        	case 19://edit response
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{0,0}).click();
    	        		CM.waitForId("android:id/button2", 2000);    		
    	        		break;
    	        	case 20://new event
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
//    	        		CM.waitForId("android:id/up", 2000);
//    	        		CM.findObjectByRid("android:id/up", 1000).click();
//    	        		CM.hold(1000);
//    	        		CM.findObjectByRid("android:id/up", 1000).click();
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		ud.pressBack();
    	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("com.android.calendar:id/edit_title_layout", 1000);    		
    	        		break;
    	        	case 21://birth of contact browse
    	        		CM.findObjectByRid("com.android.calendar:id/birthday_agenda", 1000).click();
    	        		CM.waitForId("com.android.calendar:id/birthday_add", 2000);
    	        		CM.findObjectByRid("com.android.calendar:id/birthday_add", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);    		
    	        		break;
    	        	case 22://set time
    	        		CM.findObjectByRid("android:id/up", 1000).click();
    	        		CM.waitForId("com.android.calendar:id/birthday_add", 1000);
    	        		CM.findObjectByRid("com.android.calendar:id/birthday_agenda", 1000).click();
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("com.android.calendar:id/start_time", 1000).click();
    	        		CM.waitForId("android:id/button2", 2000);    		
    	        		break;
    	        	case 23://set date
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("com.android.calendar:id/edit_title_layout", 2000);
    	        		CM.findObjectByRid("com.android.calendar:id/start_date", 1000).click();
    	        		CM.waitForId("android:id/button2", 2000);    		
    	        		break;
    	        	case 24://set time zone
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("com.android.calendar:id/edit_title_layout", 2000);
    	        		CM.findObjectByRid("com.android.calendar:id/timezone_button", 1000).click();
    	        		CM.waitForId("android:id/button2", 2000);    		
    	        		break;
    	        	case 25://show all tome zone
    	        		CM.findObjectByRid("com.android.calendar:id/timezone_footer", 1000).click();
    	        		CM.waitForId("oppo:id/select_dialog_listview", 2000);    		
    	        		break;
    	        	case 26://add reminder
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("com.android.calendar:id/edit_title_layout", 2000);
    	        		CM.findObjectByRid("com.android.calendar:id/reminder_button", 1000).click();
    	        		CM.waitForId("android:id/button2", 2000);    		
    	        		break;
    	        	case 27://more options
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("com.android.calendar:id/edit_title_layout", 2000);
    	        		CM.findObjectByRid("com.android.calendar:id/add_more_button", 1000).click();
    	        		CM.waitForId("com.android.calendar:id/to_row", 2000);    		
    	        		break;
    	        	case 28://set repeat
    	        		CM.scroll(ud, CM.UP);
    	        		CM.hold(1000);
    	        		CM.scroll(ud, CM.UP);
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("com.android.calendar:id/repeats_text", 1000).click();
    	        		CM.waitForId("android:id/button2", 2000);    		
    	        		break;
    	        	case 29://set calendar account
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("com.android.calendar:id/edit_title_layout", 2000);
    	        		CM.scroll(ud, CM.UP);
    	        		CM.hold(1000);
    	        		CM.scroll(ud, CM.UP);
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("com.android.calendar:id/availability_button", 1000).click();
    	        		CM.waitForId("android:id/button2", 2000);    		
    	        		break;
    	        	case 30://set privacy
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("com.android.calendar:id/edit_title_layout", 2000);
    	        		CM.findObjectByRid("com.android.calendar:id/visibility_button", 1000).click();
    	        		CM.waitForId("android:id/button2", 2000);    		
    	        		break;
    	        	case 31://edit name and save
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("com.android.calendar:id/edit_title_layout", 2000);
    	        		CM.findObjectByRid("com.android.calendar:id/cancel", 1000).click();
    	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("com.android.calendar:id/edit_title_layout", 1000);
    	        		CM.setText(ud, CM.findChildByPosistion(CM.findObjectByRid("com.android.calendar:id/two_pane", 1000), new int[]{0,0,1,0}), "test123");
    	        		CM.findObjectByRid("com.android.calendar:id/save", 1000).click();
    	        		CM.waitForId("android:id/alertTitle", 2000);    		
    	        		break;
    	        	case 32://event details
    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);    		
    	        		break;
    	        	case 33://edit event
    	        		CM.findObjectByRid("com.android.calendar:id/button_edit", 1000).click();
    	        		CM.waitForId("com.android.calendar:id/cancel", 2000);    		
    	        		break;
    	        	case 34://delete confirm
    	        		CM.findObjectByRid("com.android.calendar:id/cancel", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);
    	        		CM.findObjectByRid("com.android.calendar:id/button_delete", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);    		
    	        		break;
    	        	case 35://backup options
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("com.android.calendar:id/button_edit", 2000);
    	        		CM.findObjectByRid("com.android.calendar:id/button_save", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);    		
    	        		break;
    	        	case 36://send selections
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("com.android.calendar:id/button_edit", 2000);
    	        		CM.findObjectByRid("com.android.calendar:id/button_send", 1000).click();
    	        		CM.waitForId("android:id/icon", 2000);    		
    	        		break;
    	        	case 37://memos list
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.calendar:id/button_edit", 2000);
    	        		CM.findObjectByRid("android:id/up", 1000).click();
    	        		CM.waitForId("oppo:id/search_src_text", 2000);    		
    	        		break;
    	        	case 38://more selections
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);    		
    	        		break;
    	        	case 39://import browse
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
    	        		CM.waitForId("com.android.calendar:id/action_select_all", 2000);    		
    	        		break;
    	        	case 40://edit
    	        		ud.pressBack();
    	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
    	        		CM.waitForId("com.android.calendar:id/action_select_all", 2000);    		
    	        		break;
    	        	case 41://send options
    	        		CM.findObjectByRid("com.android.calendar:id/action_select_all", 1000).click();
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.waitForId("android:id/icon", 2000);    		
    	        		break;
    	        	case 42://backup options
    	        		ud.pressBack();
    	        		CM.waitForId("oppo:id/MajorOperationButtonNegative", 1000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNeutral", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);    		
    	        		break;
    	        	case 43://delete confirm
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("oppo:id/MajorOperationButtonNegative", 1000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);    		
    	        		break;
    	        	case 44://main fram with event
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 2000);
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.calendar:id/month_view_pager", 2000);    		
    	        		break;
    	        	case 45://recover
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
    	        		CM.findObjectByRid("com.android.calendar:id/action_select_all", 1000).click();
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);
    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.deleteFile(new File("/storage/sdcard0/Backup"));
    	        		CM.hold(2000);
    	        		CM.startPackage("com.oppo.filemanager");
    	        		CM.hold(2000);
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		break;
    	        	default:
    	        		break;
    	        	}
    	        }
    	    }
    	    public static class AlarmDemo
    	    {
    	    	public static void test(UiDevice ud,int step)throws Exception
    	        {
    	    		Rect mr=new Rect();
    	        	switch(step)
    	        	{
    	        	case 1://enter alarm
    	        		CM.findObjectByRid("android:id/action_bar_container", 1000).getChild(new UiSelector().index(1)).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(0)).click();
    	        		CM.waitForId("com.oppo.alarmclock:id/main_fram", 1000);    		
    	        		break;
    	        	case 2://create new alarm
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 2000).click();
    	        		CM.waitForId("com.oppo.alarmclock:id/timePicker", 2000);    		
    	        		break;
    	        	case 3://ringtone setting
//    	        		CM.findObjectByMutiAttribut("oppo:id/oppo_preference", "", "", 2, 1000).click();
    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().className("android.widget.LinearLayout").index(2)).click();
    	        		CM.waitForId("com.oppo.filemanager:id/ringtone_editor_bar", 2000);    		
    	        		break;
    	        	case 4://file browse
    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(1)).getChild(new UiSelector().index(0)).click();
    	        		CM.waitForId("com.oppo.filemanager:id/music_file_picker_buttons", 2000);    		
    	        		break;
    	        	case 5://ringtone list
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.filemanager:id/ringtone_editor_bar", 2000);
    	        		CM.scroll(ud, CM.UP);
    	        		CM.hold(1000);    		
    	        		break;
    	        	case 6://alarm name
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.alarmclock:id/timePicker", 2000);
//    	        		CM.findObjectByMutiAttribut("oppo:id/oppo_preference", "", "", 1, 1000).click();
    	        		Rect lR=CM.findObjectByRid("android:id/list", 1000).getBounds();
    	        		ud.click(lR.centerX(), lR.centerY());
    	        		if(!CM.waitForId("oppo:id/parentPanel", 2000))
    	        		{
    	        			ud.pressBack();
    	        			CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().className("android.widget.LinearLayout").index(1)).click();
    	        			CM.waitForId("oppo:id/parentPanel", 2000);
    	        		}
    	        		break;
    	        	case 7://set alarm name
    	        		CM.setText(ud, CM.findObjectByRid("com.oppo.alarmclock:id/rename_dialog_edit", 1000), "afds123");
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.waitForId("android:id/list", 1000);    		
    	        		break;
    	        	case 8://volume setting
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.alarmclock:id/timePicker", 2000);
    	        		ud.drag(ud.getDisplayWidth()/3, ud.getDisplayHeight()-80, ud.getDisplayWidth()/3, ud.getDisplayHeight()-250, 3);
    	        		CM.hold(1000);
//    	        		CM.findObjectByMutiAttribut("oppo:id/oppo_preference", "", "", 3, 1000).click();
    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().className("android.widget.LinearLayout").index(3)).click();
    	        		CM.waitForId("android:id/buttonPanel", 2000);    		
    	        		break;
    	        	case 9://repeat
    	        		ud.pressBack();
    	        		CM.findObjectByRid("oppo:id/oppo_preference", 1000).click();
    	        		break;
    	        	case 10://repeart setting
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("com.oppo.alarmclock:id/cancel", 1000);
    	        		break;
    	        	case 11://cancel new alarm
    	        		CM.findObjectByRid("com.oppo.alarmclock:id/cancel", 1000).click();
    	        		CM.waitForId("com.oppo.alarmclock:id/main_fram", 1000);    		
    	        		break;
    	        	case 12://delete select
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
    	        		CM.waitForId("com.oppo.alarmclock:id/menu_mark", 2000);    		
    	        		break;
    	        	case 13://delete confirm
    	        		CM.findObjectByRid("com.oppo.alarmclock:id/menu_mark", 1000).click();
    	        		CM.hold(2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);    		
    	        		break;
    	        	case 14://delete
    	        		CM.findObjectByRid("android:id/button2", 2000).click();
    	        		CM.waitForId("com.oppo.alarmclock:id/menu_mark", 1000);
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.alarmclock:id/main_fram", 2000);    		
    	        		break;
    	        	case 15://alarm setting
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
    	        		CM.waitForId("android:id/list", 1000);    		
    	        		break;
    	        	case 16://snooze interval
    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(0)).click();
    	        		CM.waitForId("oppo:id/parentPanel", 1000);    		
    	        		break;
    	        	case 17://global time
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("android:id/list", 1000);
    	        		CM.findObjectByRid("android:id/up", 1000).click();
    	        		CM.waitForId("android:id/content", 1000);
    	        		CM.findObjectByRid("android:id/action_bar_container", 1000).getChild(new UiSelector().index(1)).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(1)).click();
    	        		CM.waitForId("com.oppo.alarmclock:id/oppo_globaltime_option_menu_bar", 1000);    		
    	        		break;
    	        	case 18://add city
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/action_bar_container", 1000);   		
    	        		break;
    	        	case 19://search without result
    	        		CM.findObjectByRid("oppo:id/search_src_text", 1000).click();
    	        		CM.waitForId("android:id/search_src_text", 2000);
    	        		CM.setText(ud, CM.findObjectByRid("android:id/search_src_text", 1000), "1");
    	        		CM.hold(1000);    		
    	        		break;
    	        	case 20://back
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.alarmclock:id/oppo_globaltime_option_menu_bar", 1000);    		
    	        		break;
    	        	case 21://more selection
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000);    		
    	        		break;
    	        	case 22://change order
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
    	        		CM.waitForId("android:id/up", 1000);    		
    	        		break;
    	        	case 23://delete select
    	        		ud.pressBack();
    	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 1000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
    	        		CM.waitForId("com.oppo.alarmclock:id/menu_mark", 1000);    		
    	        		break;
    	        	case 24://delete confirm
    	        		CM.findObjectByRid("com.oppo.alarmclock:id/menu_mark", 1000).click();
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/alertTitle", 1000);    		
    	        		break;
    	        	case 25://delete all
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("com.oppo.alarmclock:id/menu_mark", 1000);
    	        		ud.pressBack();
    	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 2000);    		
    	        		break;
    	        	case 26://timer
    	        		CM.findObjectByRid("android:id/action_bar_container", 1000).getChild(new UiSelector().index(1)).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(3)).click();
    	        		CM.hold(1000);    		
    	        		break;
    	        	case 27://开始计时
    	        		CM.findObjectByRid("com.oppo.alarmclock:id/decrement", 1000).click();
    	        		mr=CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).getBounds();
    	        		ud.click(mr.centerX(), mr.centerY());
    	        		break;
    	        	case 28://暂停
    	        		ud.click(mr.centerX(), mr.centerY());   		
    	        		break;
    	        	case 29://复位
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		break;
    	        	case 30://alert type
//    	        		CM.findObjectByRid("com.oppo.filemanager:id/music_file_picker_buttons", 1000).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(1)).click();
//    	        		CM.waitForId("com.oppo.filemanager:id/ringtone_editor_bar", 2000);
//    	        		CM.findObjectByRid("android:id/up", 1000).click();
//    	        		CM.waitForId("com.oppo.alarmclock:id/timer_ring_set_layout", 1000);
//    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(1)).getChild(new UiSelector().index(0)).click();
//    	        		CM.waitForId("android:id/alertTitle", 1000);    		
    	        		break;
    	        	case 31://stop watch
    	        		CM.findObjectByRid("android:id/action_bar_container", 1000).getChild(new UiSelector().index(1)).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(2)).click();
    	        		CM.hold(1000);    		
    	        		break;
    	        	case 32://start
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.hold(1000);    		
    	        		break;
    	        	case 33://record
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.hold(1000);    		
    	        		break;
    	        	case 34://pause
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.hold(1000);    		
    	        		break;    		
    	        	case 35://reset
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.hold(1000);    		
    	        		break;
    	        	default:
    	        		break;
    	        	}
    	        }
    	    }
    	    public static class WeatherDemo
    	    {
    	    	public static void test(UiDevice ud,int step) throws Exception
    	        {
    	        	switch(step)
    	        	{
    	        	case 1://enter weather
    	        		if(CM.waitForId("android:id/message", 2000))
    	        		{
    	        			ud.pressBack();
    	        			CM.waitForId("com.oppo.weather:id/main_view_no_city_tip", 2000);
    	        		}
    	        		break;
    	        	case 2://menu options
    	        		ud.pressMenu();
    	        		CM.waitForId("com.oppo.weather:id/popup_window_manager", 2000);
    	        		break;
    	        	case 3://add city
    	        		CM.findObjectByRid("com.oppo.weather:id/popup_window_manager", 1000).click();
    	        		CM.waitForId("com.oppo.weather:id/iv_add", 2000);
    	        		break;
    	        	case 4://search and add city
    	        		UiObject group=CM.findObjectByRid("com.oppo.weather:id/gridview", 1000);
    	        		if(group.getChildCount()>1)
    	        		{
    	            		CM.clickLongOnObject(ud, CM.findObjectByRid("com.oppo.weather:id/iv_add", 1000));
    	            		CM.waitForId("com.oppo.weather:id/btn_delete", 2000);
    	            		CM.findObjectByRid("com.oppo.weather:id/btn_delete", 1000).click();
    	            		CM.findObjectByRid("com.oppo.weather:id/btn_complete", 1000).click();
    	            		CM.waitForId("com.oppo.weather:id/city_manager_location_button", 2000);
    	        		}
    	        		CM.findObjectByRid("com.oppo.weather:id/iv_add", 1000).click();
    	        		CM.waitForId("android:id/search_src_text", 2000);
    	        		CM.setText(ud, CM.findObjectByRid("android:id/search_src_text", 1000), "p");
    	        		CM.waitForId("com.oppo.weather:id/fragment_serrch_city_list", 2000);
    	        		CM.findObjectByRid("com.oppo.weather:id/city_name", 1000).click();
    	        		CM.hold(3000);
    	        		CM.waitForId("com.oppo.weather:id/iv_add", 2000);
    	        		break;
    	        	case 5://weather view
    	        		CM.findObjectByRid("com.oppo.weather:id/iv_weather_icon", 1000).click();
    	        		CM.waitForId("com.oppo.weather:id/weather_current_temp", 10000);
    	        		break;
    	        	case 6://three days weather
    	        		if(CM.findObjectByRid("com.oppo.weather:id/three_days_weather", 1000).getChildCount()<4)
    	        			break;
    	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.weather:id/three_days_weather", 1000), new int[]{3,1,0}).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 7://three days weather
    	        		if(CM.findObjectByRid("com.oppo.weather:id/three_days_weather", 1000).getChildCount()<4)
    	        			break;
    	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.weather:id/three_days_weather", 1000), new int[]{3,2,0}).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 8://three days weather
    	        		if(CM.findObjectByRid("com.oppo.weather:id/three_days_weather", 1000).getChildCount()<4)
    	        			break;
    	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.weather:id/three_days_weather", 1000), new int[]{3,3,0}).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 9://share
    	        		if(CM.findObjectByRid("com.oppo.weather:id/three_days_weather", 1000).getChildCount()<4)
    	        			break;
    	        		ud.pressMenu();
    	        		CM.waitForId("com.oppo.weather:id/popup_window_share", 2000);
    	        		CM.findObjectByRid("com.oppo.weather:id/popup_window_share", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 10://share options
    	        		if(!CM.waitForId("android:id/up", 1000))
    	        			break;
    	        		CM.findObjectByRid("com.oppo.weather:id/share_info_button", 1000).click();
    	        		CM.hold(2000);
    	        		break;
    	        	case 11://settings
    	        		if(CM.waitForId("android:id/alertTitle", 1000))
    	        		{
    	    	    		ud.pressBack();
    	    	    		CM.waitForId("android:id/up", 2000);
    	    	    		ud.pressBack();
    	    	    		CM.waitForId("com.oppo.weather:id/weather_current_temp", 10000);
    	        		}
    	        		ud.pressMenu();
    	        		CM.waitForId("com.oppo.weather:id/popup_window_setting", 2000);
    	        		CM.findObjectByRid("com.oppo.weather:id/popup_window_setting", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 12://display settings
    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().resourceId("oppo:id/oppo_preference").index(1)).click();
    	        		CM.waitForId("com.oppo.weather:id/radiobutton", 2000);
    	        		break;
    	        	case 13://automatic update
    	        		ud.pressBack();
    	        		CM.waitForId("android:id/checkbox", 2000);
    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().resourceId("oppo:id/oppo_preference").index(2)).click();
    	        		CM.waitForId("com.oppo.weather:id/radiobutton", 2000);
    	        		break;
    	        	case 14://change temperature unit
    	        		ud.pressBack();
    	        		CM.waitForId("android:id/checkbox", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{5,0}).click();
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.weather:id/weather_current_temp", 2000);
    	        		break;
    	        	case 15://delete
    	        		ud.pressMenu();
    	        		CM.waitForId("com.oppo.weather:id/popup_window_share", 2000);
    	        		CM.findObjectByRid("com.oppo.weather:id/popup_window_setting", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{4,0}).click();
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.weather:id/weather_current_temp", 2000);
    	        		ud.pressMenu();
    	        		CM.waitForId("com.oppo.weather:id/popup_window_share", 2000);
    	        		CM.findObjectByRid("com.oppo.weather:id/popup_window_manager", 1000).click();
    	        		CM.waitForId("com.oppo.weather:id/iv_add", 2000);
    	        		CM.clickLongOnObject(ud, CM.findObjectByRid("com.oppo.weather:id/iv_add", 1000));
    	        		CM.waitForId("com.oppo.weather:id/btn_delete", 2000);
    	        		break;
    	        	case 16://positioning
    	        		CM.findObjectByRid("com.oppo.weather:id/btn_delete", 1000).click();
    	        		CM.findObjectByRid("com.oppo.weather:id/btn_complete", 1000).click();
    	        		CM.waitForId("com.oppo.weather:id/city_manager_location_button", 2000);
    	        		CM.findObjectByRid("com.oppo.weather:id/city_manager_location_button", 1000).click();
    	        		CM.waitForId("android:id/message", 2000);
    	        		break;
    	        	case 17://over
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		break;
    	        	default:
    	        		break;
    	        	}
    	        }
    	    }
    	    public static class MusicDemo
    	    {
    	    	public static void test(UiDevice ud,int step)throws Exception
    	        {
    	        	switch(step)
    	        	{
    	        	case 1://main interface
    	        		if(CM.waitForId("com.oppo.music:id/md__drawer", 2000))
    	        		{
    	        			CM.findObjectByRid("com.oppo.music:id/icon_to_list", 1000).click();
    	        			CM.waitForId("com.oppo.music:id/gridview", 2000);    			
    	        		}    			
    	        		break;
    	        	case 2://my music
    	        		CM.findChildbyAtrribut(CM.findObjectByRid("com.oppo.music:id/list", 1000), "", "android.widget.LinearLayout", "", 1, 1000).click();
    	        		CM.waitForId("android:id/up", 2000);    		
    	        		break;
    	        	case 3://operation options
    	        		CM.findObjectByRid("com.oppo.music:id/operation", 1000).click();
    	        		CM.waitForId("com.oppo.music:id/list_operation_container", 2000);    		
    	        		break;
    	        	case 4://add to new list
    	        		CM.findObjectByRid("com.oppo.music:id/list_item_add_to_playlist", 1000).click();
    	        		CM.waitForId("android:id/alertTitle", 2000);    		
    	        		break;
    	        	case 5://create new list
    	        		CM.findObjectByRid("android:id/text1", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);    		
    	        		break;
    	        	case 6://add and delete favorite
    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		CM.findObjectByRid("com.oppo.music:id/operation", 1000).click();
    	        		CM.waitForId("com.oppo.music:id/list_operation_container", 2000);
    	        		CM.findObjectByRid("com.oppo.music:id/list_item_favour", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		CM.findObjectByRid("com.oppo.music:id/operation", 1000).click();
    	        		CM.waitForId("com.oppo.music:id/list_operation_container", 2000);
    	        		CM.findObjectByRid("com.oppo.music:id/list_item_favour", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);    		
    	        		break;
    	        	case 7://delete file
    	        		CM.findObjectByRid("com.oppo.music:id/operation", 1000).click();
    	        		CM.waitForId("com.oppo.music:id/list_operation_container", 2000);
    	        		CM.findObjectByRid("com.oppo.music:id/list_item_delete", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);    		
    	        		break;
    	        	case 8://ring tone
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		CM.findObjectByRid("com.oppo.music:id/operation", 1000).click();
    	        		CM.waitForId("com.oppo.music:id/list_operation_container", 2000);
    	        		CM.findObjectByRid("com.oppo.music:id/list_item_ringtone", 1000).click();
    	        		CM.waitForId("android:id/content", 2000);    		
    	        		break;
    	        	case 9://send file
    	        		ud.pressBack();
    	        		CM.waitForId("android:id/up", 2000);
    	        		CM.findObjectByRid("com.oppo.music:id/operation", 1000).click();
    	        		CM.waitForId("com.oppo.music:id/list_operation_container", 2000);
    	        		CM.findObjectByRid("com.oppo.music:id/list_item_send", 1000).click();
    	        		CM.waitForId("android:id/content", 2000);    		
    	        		break;
    	        	case 10://file info
    	        		ud.pressBack();
    	        		CM.waitForId("android:id/up", 2000);
    	        		CM.findObjectByRid("com.oppo.music:id/operation", 1000).click();
    	        		CM.waitForId("com.oppo.music:id/list_operation_container", 2000);
    	        		CM.findObjectByRid("com.oppo.music:id/list_item_details", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);    		
    	        		break;
    	        	case 11://artist
    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		CM.findObjectByRid("com.oppo.music:id/title", 1000).getChild(new UiSelector().index(1)).click();
    	        		CM.hold(1000);    		
    	        		break;
    	        	case 12://album
    	        		CM.findObjectByRid("com.oppo.music:id/title", 1000).getChild(new UiSelector().index(2)).click();
    	        		CM.hold(1000);    		
    	        		break;
    	        	case 13://folder
    	        		CM.findObjectByRid("com.oppo.music:id/title", 1000).getChild(new UiSelector().index(3)).click();  		
    	        		CM.hold(1000); 
    	        		break;
    	        	case 14://favor
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.music:id/gridview", 2000);
    	        		CM.findObjectByRid("com.oppo.music:id/list", 1000).getChild(new UiSelector().index(2).className("android.widget.LinearLayout")).click();
    	        		CM.waitForId("android:id/up", 2000);		
    	        		break;
    	        	case 15://create list
    	        		CM.findObjectByRid("android:id/up", 1000).click();
    	        		CM.waitForId("android:id/action_bar_overlay_layout", 2000);    
    	        		CM.findObjectByRid("com.oppo.music:id/list", 1000).getChild(new UiSelector().index(4).className("android.widget.LinearLayout")).click();
    	        		break;
    	        	case 16://new list
    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.waitForId("com.oppo.music:id/mark_list_bar_choose", 2000);		
    	        		break;
    	        	case 17://add song
    	        		CM.findObjectByRid("oppo:id/oppo_listview_scrollchoice_checkbox", 1000).click();
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/action_bar_overlay_layout", 2000); 
    	        		break;    		
    	        	case 18://view list
    	        		CM.findObjectByRid("com.oppo.music:id/list", 1000).getChild(new UiSelector().index(5).className("android.widget.LinearLayout")).click();   		
    	        		CM.waitForId("android:id/up", 1000);
    	        		break;
    	        	case 19://edit list
    	        		CM.findObjectByRid("com.oppo.music:id/edit_playlist", 1000).click();
    	        		CM.waitForId("android:id/alertTitle", 2000);    		
    	        		break;
    	        	case 20://clear list
    	        		CM.waitForId("android:id/alertTitle", 2000);
    	        		CM.findObjectByRid("oppo:id/select_dialog_listview", 1000).getChild(new UiSelector().index(1)).click();
    	        		CM.waitForId("com.oppo.music:id/add_songs", 2000);    		
    	        		break;
    	        	case 21://delete list
    	        		CM.findObjectByRid("com.oppo.music:id/edit_playlist", 1000).click();
    	        		CM.waitForId("android:id/alertTitle", 2000);
    	        		CM.findObjectByRid("oppo:id/select_dialog_listview", 1000).getChild(new UiSelector().index(2)).click();
    	        		CM.waitForId("android:id/button1", 2000);    		
    	        		break;
    	        	case 22://delete list
    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.waitForId("android:id/action_bar_container", 2000);
    	        		Rect lR=CM.findObjectByRid("com.oppo.music:id/list", 1000).getChild(new UiSelector().index(5).className("android.widget.LinearLayout")).getBounds();
    	        		CM.clickLongOnScreen(ud, lR.centerX(), lR.centerY());
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000); 
    	        		break;
    	        	case 23://more options
    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.waitForId("android:id/action_bar_container", 2000);
    	        		CM.findObjectByRid("com.oppo.music:id/control_bar_prev", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);    		
    	        		break;
    	        	case 24://scan music
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);    		
    	        		break;
    	        	case 25://scan filter
    	        		CM.findObjectByRid("com.oppo.music:id/sub_text_button", 1000).click();
    	        		CM.waitForId("com.oppo.music:id/menuoperation", 2000);    		
    	        		break;
    	        	case 26://folder filter
    	        		CM.findObjectByRid("com.oppo.music:id/file_directory_filter_layout", 1000).getChild(new UiSelector().index(1)).click();
    	        		CM.waitForId("com.oppo.music:id/textView_ok", 2000);    		
    	        		break;
    	        	case 27://do scan    		
    	        		CM.findObjectByRid("com.oppo.music:id/textView_ok", 1000).click();
    	        		CM.waitForId("com.oppo.music:id/menuoperation", 2000);
    	        		CM.findObjectByRid("android:id/up", 1000).click();
    	        		CM.waitForId("com.oppo.music:id/sub_text_button", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("com.oppo.music:id/textView_settings_scan_already", 5000);    		
    	        		break;
    	        	case 28://sleep mode
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/action_bar_container", 2000);
    	        		CM.findObjectByRid("com.oppo.music:id/control_bar_prev", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);    		
    	        		break;
    	        	case 29://settings
    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.waitForId("android:id/action_bar_container", 2000);
    	        		CM.findObjectByRid("com.oppo.music:id/control_bar_prev", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView3", 1000).click();
    	        		if(!CM.waitForId("android:id/action_bar", 2000))   	
    	        			CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView3", 1000).click();
    	        		break;
    	        	case 30://play interface
    	        		CM.findObjectByRid("android:id/up", 1000).click();
    	        		CM.waitForId("android:id/action_bar_container", 2000);
    	        		CM.findObjectByRid("com.oppo.music:id/control_bar_album_bg", 1000).click();
    	        		CM.waitForId("com.oppo.music:id/md__drawer", 2000);    		
    	        		break;
    	        	case 31://playing list
    	        		CM.scroll(ud, CM.LEFT);
    	        		CM.hold(1000);    		
    	        		break;
    	        	case 32://menu buttons
    	        		CM.scroll(ud, CM.RIGHT);
    	        		CM.hold(2000);
    	        		for(int i=0;i<4;i++)
    	        		{
//    	        			CM.findObjectByRid("com.oppo.music:id/icon_operation", 1000).click();
    	        			ud.pressMenu();
    	        			CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        			CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView"+i, 1000).click();
    	        			CM.hold(2000);
    	        			if(!CM.waitForId("com.oppo.music:id/icon_operation", 1000))
    	        			{
    	        				ud.pressBack();
    	        				CM.waitForId("com.oppo.music:id/icon_operation", 2000);
    	        			}
    	        		}
//    	    			CM.findObjectByRid("com.oppo.music:id/icon_operation", 1000).click();
    	        		ud.pressMenu();
    	    			CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	    			CM.hold(2000);    		
    	        		break;
    	        	case 33://
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		CM.hold(2000);
    	        		break;
    	        	default:
    	        		break;
    	        	}    	
    	        }
    	    }
    	    public static class VideoDemo
    	    {
    	    	public static void test(UiDevice ud,int step) throws Exception
    	        {
    	        	switch(step)
    	        	{
    	        	case 1://enter video
    	        		CM.waitForId("com.oppo.video:id/my_video_tab_list", 3000);
    	        		break;
    	        	case 2://enter shooting video
    	        		CM.findObjectByRid("com.oppo.video:id/my_video_tab_list", 1000).getChild(new UiSelector().index(1).className("android.widget.RelativeLayout")).click();
    	        		CM.waitForId("com.oppo.video:id/videoname", 2000);
    	        		break;
    	        	case 3://play video
    	        		CM.findObjectByRid("com.oppo.video:id/thumb_icon", 2000).click();
    	        		CM.waitForId("com.oppo.video:id/surface_view", 2000);
    	        		CM.hold(5000);
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.video:id/videoname", 2000);
    	        		break;
    	        	case 4://edit video file
    	        		CM.clickLongOnObject(ud, CM.findObjectByRid("com.oppo.video:id/thumb_icon", 1000));
    	        		CM.waitForId("com.oppo.video:id/option_menu_delete", 2000);
    	        		break;
    	        	case 5://delete confirm
    	        		CM.findObjectByRid("com.oppo.video:id/option_menu_delete", 1000).click();
    	        		CM.waitForId("com.oppo.video:id/button2", 2000);  		
    	        		break;
    	        	case 6://share video
    	        		CM.findObjectByRid("com.oppo.video:id/button2", 1000).click();
    	        		CM.waitForId("com.oppo.video:id/option_menu_delete", 2000); 
    	        		CM.findObjectByRid("com.oppo.video:id/option_menu_share", 1000).click();
    	        		CM.waitForId("android:id/title_template", 2000); 
    	        		break;
    	        	case 7://video file details
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.video:id/option_menu_share", 2000); 
    	        		CM.findObjectByRid("com.oppo.video:id/option_menu_detail", 1000).click();
    	        		CM.waitForId("com.oppo.video:id/button1", 2000);    		
    	        		break;
    	        	case 8://video file rename
    	        		CM.findObjectByRid("com.oppo.video:id/button1", 1000).click();
    	        		CM.waitForId("com.oppo.video:id/option_menu_detail", 2000); 
    	        		CM.findObjectByRid("com.oppo.video:id/option_menu_rename", 1000).click();
    	        		CM.waitForId("com.oppo.video:id/button2", 2000); 
    	        		break;
    	        	case 9://settings
    	        		CM.findObjectByRid("com.oppo.video:id/button2", 1000).click();
    	        		ud.pressBack();
//    	        		CM.waitForId("com.oppo.video:id/option_menu_rename", 2000);
//    	        		CM.findObjectByRid("com.oppo.video:id/iv_top_back", 1000).click();
    	        		CM.hold(1000);
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.video:id/my_video_tab_list", 2000);
    	        		CM.findObjectByRid("com.oppo.video:id/my_video_tab_list", 1000).getChild(new UiSelector().index(4).className("android.widget.RelativeLayout")).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 10://local video
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.video:id/my_video_tab_list", 2000);
    	        		CM.findObjectByRid("com.oppo.video:id/my_video_tab_list", 1000).getChild(new UiSelector().index(2).className("android.widget.RelativeLayout")).click();
    	        		CM.hold(1000);
    	        		break; 
    	        	case 11://local video
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		break;  
    	        	default:
    	        		break;
    	        	}
    	        }
    	    }
//    	    public static class BackupDemo
//    	    {
//    	    	public static void test(UiDevice ud,int step) throws Exception
//    	        {
//    	        	switch(step)
//    	        	{
//    	        	case 1://main interface
//    	        		CM.waitForId("com.oppo.backuprestore:id/oppo_option_menu_bar", 2000);
//    	        		break;
//    	        	case 2://create backup
//    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
//    	        		CM.waitForId("android:id/up", 2000);
//    	        		break;
//    	        	case 3://backup over
//    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/content", 1000), new int[]{0,1,0,0,0}).click();
//    	        		CM.waitForId("com.oppo.backuprestore:id/result_tip", 120000);
//    	        		break;
//    	        	case 4://main interface with backup
//    	        		CM.findObjectByRid("com.oppo.backuprestore:id/button", 1000).click();
//    	        		CM.waitForId("com.oppo.backuprestore:id/oppo_option_menu_bar", 2000);
//    	        		break;
//    	        	case 5://backup detail
//    	        		CM.findObjectByRid("com.oppo.backuprestore:id/listView", 1000).getChild(new UiSelector().index(0)).click();
//    	        		CM.waitForId("android:id/up", 2000);
//    	        		break;
//    	        	case 6://import backup
//    	        		CM.findObjectByRid("com.oppo.backuprestore:id/button", 1000).click();
//    	        		while(!CM.waitForId("com.oppo.backuprestore:id/result_tip", 1000))
//    	        		{
//    	        			if(CM.waitForId("com.android.vending:id/positive_button", 1000))
//    	        				CM.findObjectByRid("com.android.vending:id/positive_button", 1000).click();
//    	        		}    		   		
//    	        		break;
//    	        	case 7://more options
//    	        		CM.findObjectByRid("com.oppo.backuprestore:id/button", 1000).click();
//    	        		CM.waitForId("com.oppo.backuprestore:id/oppo_option_menu_bar", 2000);
//    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
//    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
//    	        		break;
//    	        	case 8://edit backup
//    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
//    	        		CM.waitForId("com.oppo.backuprestore:id/action_select_all", 2000);
//    	        		break;
//    	        	case 9://rename backup
//    	        		CM.findObjectByRid("com.oppo.backuprestore:id/action_select_all", 1000).click();
//    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
//    	        		CM.waitForId("android:id/button1", 2000);
//    	        		break;
//    	        	case 10://delete backup
//    	        		CM.findObjectByRid("android:id/button1", 1000).click();
//    	        		CM.waitForId("com.oppo.backuprestore:id/action_select_all", 2000);
//    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
//    	        		CM.waitForId("android:id/button1", 2000);
//    	        		break;
//    	        	case 11://timing backup
//    	        		CM.findObjectByRid("android:id/button1", 1000).click();
//    	        		CM.waitForId("oppo:id/MajorOperationButtonPositive", 2000);
//    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).getChild(new UiSelector().index(0)).click();
//    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
//    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
//    	        		CM.waitForId("android:id/up", 2000);
//    	        		break;
//    	        	case 12://backup cycle
//    	        		CM.findObjectByClassname("com.oppo.widget.OppoSwitch", 1000).click();
//    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{0,0}).click();
//    	        		CM.waitForId("android:id/button1", 2000);
//    	        		break;
//    	        	case 13://backup time
//    	        		CM.findObjectByRid("android:id/button1", 1000).click();
//    	        		CM.waitForId("android:id/list", 2000);
//    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{1,0}).click();
//    	        		CM.waitForId("android:id/button1", 2000);
//    	        		break;
//    	        	case 14://backup options
//    	        		CM.findObjectByRid("android:id/button1", 1000).click();
//    	        		CM.waitForId("android:id/list", 2000);
//    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{2,0}).click();
//    	        		CM.waitForId("com.oppo.backuprestore:id/expandableListView", 2000);
//    	        		break;
//    	        	case 15://settings
//    	        		CM.findObjectByRid("com.oppo.backuprestore:id/button", 1000).click();
//    	        		CM.waitForId("android:id/list", 2000);
//    	        		CM.findObjectByClassname("com.oppo.widget.OppoSwitch", 1000).click();
//    	        		ud.pressBack();    		
//    	        		CM.waitForId("oppo:id/MajorOperationButtonPositive", 2000);
//    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).getChild(new UiSelector().index(0)).click();
//    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
//    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView2", 1000).click();
//    	        		CM.waitForId("android:id/up", 2000);
//    	        		break;
//    	        	case 16://backup contacts from
//    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{0,0}).click();
//    	        		CM.waitForId("android:id/button1", 2000);
//    	        		break;
//    	        	case 17://over
//    	        		CM.findObjectByRid("android:id/button1", 1000).click();
//    	        		ud.pressBack();
//    	        		ud.pressBack();
//    	        		break;
//    	        	default:
//    	        		break;
//    	        	}
//    	        }
//    	    }
    	  //file manager test//index 14,length 33
    	    public static class FilemanagerDemo
    	    {    	  
    	        public static void test(UiDevice ud,int step)throws Exception
    	        {
    	        	switch(step)
    	        	{
    	        	case 1://main sheet
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/action_bar_container", 1000), new int[]{1,0,0}).click();
    	        		CM.hold(1000);    		
    	        		break;
    	        	case 2://music list
    	        		Rect r = null;
    	        		if(CM.waitForId("com.oppo.filemanager:id/category_gallery_item_view", 1000))
    	        			r=CM.findObjectByRid("com.oppo.filemanager:id/category_gallery_item_view", 1000).getBounds();
    	        		else if(CM.waitForId("com.oppo.filemanager:id/gallery_view", 1000))
    	        			r=CM.findObjectByRid("com.oppo.filemanager:id/gallery_view", 1000).getBounds();
    	        		for(int i=0;i<2;i++)
    	        		{
    	        			for(int j=0;j<3;j++)
    	        			{
    	        				ud.click(r.width()/4*(i*2+1), r.top+(r.height()/6*(j*2+1)));
    	        				CM.waitForId("android:id/up", 2000);
    	        				ud.pressBack();
    	        				CM.waitForId("com.oppo.filemanager:id/shortcut_button", 2000);
    	        			}
    	        		}
    	        		ud.click(r.width()/4, r.top+r.height()/6);
    	        		CM.waitForId("oppo:id/search_src_text", 2000);    		
    	        		break;
    	        	case 3://sort selections
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.waitForId("android:id/button2", 2000);    		
    	        		break;
    	        	case 4://edit list
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("oppo:id/search_src_text", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);    		
    	        		break;
    	        	case 5://delete confirm
    	        		CM.findObjectByRid("com.oppo.filemanager:id/action_select_all", 1000).click();
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);    		
    	        		break;
    	        	case 6://copy selection
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.waitForId("com.oppo.filemanager:id/folder_list_buttons", 2000);    		
    	        		break;
    	        	case 7://copy
    	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.filemanager:id/folder_list_buttons", 1000), new int[]{0,2}).click();
    	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);    		
    	        		break;
    	        	case 8://remote   
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("com.oppo.filemanager:id/ftp_open", 2000);    		
    	        		break;
    	        	case 9://clean up
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.filemanager:id/shortcut_button", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);    		
    	        		break;
    	        	case 10://timing clean up
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.waitForId("android:id/checkbox", 2000);    		
    	        		break;
    	        	case 11://safe
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.filemanager:id/shortcut_button", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);    		
    	        		break;
    	        	case 12://move
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.filemanager:id/shortcut_button", 2000);
    	        		if(CM.waitForId("com.oppo.filemanager:id/category_gallery_item_view", 1000))
    	        			r=CM.findObjectByRid("com.oppo.filemanager:id/category_gallery_item_view", 1000).getBounds();
    	        		else
    	        			r=CM.findObjectByRid("com.oppo.filemanager:id/gallery_view", 1000).getBounds();
    	        		ud.click(r.width()/4, r.top+r.height()/6);
//    	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.filemanager:id/pager", 1000), new int[]{0,0,0,0,0}).click();
    	        		CM.waitForId("oppo:id/search_src_text", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
    	        		CM.findObjectByRid("com.oppo.filemanager:id/action_select_all", 1000).click();
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNeutral", 1000).click();
    	        		CM.waitForId("com.oppo.filemanager:id/folder_list_buttons", 2000);    		
    	        		break;
    	        	case 13://more options
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);    		
    	        		break;
    	        	case 14://compress options
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView3", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);    		
    	        		break;
    	        	case 15://compressing
    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.hold(2000);
    	        		if(CM.waitForId("android:id/message", 1000))
    	        			CM.findObjectByRid("android:id/button1", 1000).click();    		
    	        		break;
    	        	case 16://rename
    	        		CM.waitForId("oppo:id/search_src_text", 600000);
    	        		if(CM.waitForId("com.oppo.filemanager:id/action_select_all", 1000))
    	        		{
    		        		ud.pressBack();
    		        		CM.hold(1000);
    	        		}
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
    	        		CM.findObjectByRid("oppo:id/oppo_listview_scrollchoice_checkbox", 1000).click();
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);    		
    	        		break;
    	        	case 17://share selections
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView2", 1000).click();
    	        		CM.hold(1000);    		
    	        		break;
    	        	case 18://details
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		Rect Rd=CM.findObjectByRid("oppo:id/MoreOperationsGroup", 1000).getBounds();
    	        		while(!CM.waitForId("oppo:id/MoreOperationArrowLeft", 1000))
    	        		    ud.drag(ud.getDisplayWidth()/2+150, Rd.centerY(), ud.getDisplayWidth()/2-150,  Rd.centerY(), 5);
//    	        		CM.dragObj(ud, CM.findObjectByRid("oppo:id/MoreOperationsGroup", 1000), CM.LEFT, 5);
    	        		CM.hold(2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);    		
    	        		break;
    	        	case 19://music set as
    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
//    	        		CM.dragObj(ud, CM.findObjectByRid("oppo:id/MoreOperationsGroup", 1000), CM.LEFT, 5);
//    	        		ud.drag(ud.getDisplayWidth()-50, ud.getDisplayHeight()-30, 50, ud.getDisplayHeight()-30, 50);
    	        		Rd=CM.findObjectByRid("oppo:id/MoreOperationsGroup", 1000).getBounds();
    	        		while(!CM.waitForId("oppo:id/MoreOperationArrowLeft", 1000))
    	        		    ud.drag(ud.getDisplayWidth()-100, Rd.centerY(), 50,  Rd.centerY(), 5);
    	        		CM.hold(2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
    	        		CM.waitForId("android:id/alertTitle", 2000);    		
    	        		break;
    	        	case 20://picture set as
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.filemanager:id/shortcut_button", 2000);
    	        		if(CM.waitForId("com.oppo.filemanager:id/category_gallery_item_view", 1000))
    	        			r=CM.findObjectByRid("com.oppo.filemanager:id/category_gallery_item_view", 1000).getBounds();
    	        		else
    	        			r=CM.findObjectByRid("com.oppo.filemanager:id/gallery_view", 1000).getBounds();
    	        		ud.click(r.width()/4, r.top+r.height()/2);
//    	        		CM.findObjectByRid("com.oppo.filemanager:id/picture_view", 1000).click();
    	        		CM.waitForId("oppo:id/search_src_text", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
    	        		CM.findObjectByRid("oppo:id/oppo_listview_scrollchoice_checkbox", 1000).click();
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
//    	        		CM.dragObj(ud, CM.findObjectByRid("oppo:id/MoreOperationsGroup", 1000), CM.LEFT, 5);
    	        		while(!CM.waitForId("oppo:id/MoreOperationArrowLeft", 1000))
    	        		    ud.drag(ud.getDisplayWidth()-50, ud.getDisplayHeight()-30, 50, ud.getDisplayHeight()-30, 50);
    	        		CM.hold(2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
    	        		CM.waitForId("android:id/alertTitle", 2000);    		
    	        		break;
    	        	case 21://video list
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.filemanager:id/shortcut_button", 2000);
    	        		if(CM.waitForId("com.oppo.filemanager:id/category_gallery_item_view", 1000))
    	        			r=CM.findObjectByRid("com.oppo.filemanager:id/category_gallery_item_view", 1000).getBounds();
    	        		else
    	        			r=CM.findObjectByRid("com.oppo.filemanager:id/gallery_view", 1000).getBounds();
    	        		ud.click(r.width()*3/4, r.top+r.height()/6);
//    	        		CM.findObjectByRid("com.oppo.filemanager:id/video_view", 1000).click();
    	        		CM.waitForId("oppo:id/search_src_text", 2000);    		
    	        		break;
    	        	case 22://document list
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.filemanager:id/shortcut_button", 2000);
    	        		if(CM.waitForId("com.oppo.filemanager:id/category_gallery_item_view", 1000))
    	        			r=CM.findObjectByRid("com.oppo.filemanager:id/category_gallery_item_view", 1000).getBounds();
    	        		else
    	        			r=CM.findObjectByRid("com.oppo.filemanager:id/gallery_view", 1000).getBounds();
    	        		ud.click(r.width()*3/4, r.top+r.height()/2);
//    	        		CM.findObjectByRid("com.oppo.filemanager:id/doc_view", 1000).click();
    	        		CM.waitForId("oppo:id/search_src_text", 2000);    		
    	        		break;
    	        	case 23://apk list
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.filemanager:id/shortcut_button", 2000);
//    	        		CM.findObjectByRid("com.oppo.filemanager:id/apk_view", 1000).click();
    	        		if(CM.waitForId("com.oppo.filemanager:id/category_gallery_item_view", 1000))
    	        			r=CM.findObjectByRid("com.oppo.filemanager:id/category_gallery_item_view", 1000).getBounds();
    	        		else
    	        			r=CM.findObjectByRid("com.oppo.filemanager:id/gallery_view", 1000).getBounds();
    	        		ud.click(r.width()/4, r.top+r.height()*5/6);
    	        		CM.waitForId("oppo:id/search_src_text", 2000);    		
    	        		break;
    	        	case 24://zip list
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.filemanager:id/shortcut_button", 2000);
//    	        		CM.findObjectByRid("com.oppo.filemanager:id/zip_view", 1000).click();
    	        		if(CM.waitForId("com.oppo.filemanager:id/category_gallery_item_view", 1000))
    	        			r=CM.findObjectByRid("com.oppo.filemanager:id/category_gallery_item_view", 1000).getBounds();
    	        		else
    	        			r=CM.findObjectByRid("com.oppo.filemanager:id/gallery_view", 1000).getBounds();
    	        		ud.click(r.width()*3/4, r.top+r.height()*5/6);
    	        		CM.waitForId("oppo:id/search_src_text", 2000);    		
    	        		break;
    	        	case 25://storage list
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.filemanager:id/shortcut_button", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/action_bar_container", 1000), new int[]{1,0,1}).click();
    	        		CM.waitForId("oppo:id/search_src_text", 2000);    		
    	        		break;
    	        	case 26://create new folder
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);    		
    	        		break;
    	        	case 27://more options
    	        		CM.findObjectByRid("com.oppo.filemanager:id/file_name_edit", 1000).clickBottomRight();
    	        		CM.setText(ud, CM.findObjectByRid("com.oppo.filemanager:id/file_name_edit", 1000), "0");
    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		while(CM.waitForId("android:id/custom", 2000))
    	        		{
    	        			ud.pressBack();
    	        			CM.hold(1500);
    	        		}
    	        		CM.waitForId("oppo:id/search_src_text", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);    		
    	        		break;
    	        	case 28://sort
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
    	        		CM.waitForId("android:id/button2", 2000);    		
    	        		break;
    	        	case 29://more select
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("oppo:id/search_src_text", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);  		
    	        		break;
    	        	case 30://
    	        		CM.hold(1000);    		
    	        		break;
    	        	case 31://copy to
    	        		ud.pressBack();
    	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
    	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.filemanager:id/file_list_view_container", 1000), new int[]{0,1,2,0}).click();
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.findObjectByRid("com.oppo.filemanager:id/mark_check", 1000).getChild(new UiSelector().index(0)).click();
    	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.filemanager:id/folder_list_buttons", 1000),new int[]{0,1}).click();
    	        		CM.hold(1000); 
    	        		if(CM.waitForId("android:id/button1", 1000))
    	        			CM.findObjectByRid("android:id/button1", 1000).click();  
    	        		break;
    	        	case 32://delete confirm
    	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 10000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
    	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
    	        		CM.findObjectByRid("com.oppo.filemanager:id/action_select_all", 1000).click();
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);    		
    	        		break;
    	        	case 33://delete
    	        		CM.findObjectByRid("android:id/button1", 1000).click();    		
    	        		break;
    	        	default:
    	        		break;
    	        	}    	
    	        }
    	    }
    	    public static class SettingDemo
    	    {
    	    	public static void test(UiDevice ud,int step) throws Exception
    	        {
    	        	switch(step)
    	        	{
    	        	case 1://sound settings
    	        		CM.waitForId("com.android.settings:id/pager", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/action_bar_container", 1000), new int[]{1,0,1}).click();
    	        		CM.hold(2000);
    	        		break;
    	        	case 2://volumes
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{1,1}).click();
    	        		CM.waitForId("android:id/button1", 2000);
    	        		break;
    	        	case 3://silent mode
    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.waitForId("com.android.settings:id/pager", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{2,1}).click();
    	        		CM.waitForId("android:id/button2", 2000);
    	        		break;
    	        	case 4://ring tone
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("com.android.settings:id/pager", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{4,1}).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 5://message tone
    	        		CM.findObjectByRid("android:id/up", 1000).click();
    	        		CM.waitForId("com.android.settings:id/pager", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{5,1}).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 6://other notifications
    	        		CM.findObjectByRid("android:id/up", 1000).click();
    	        		CM.waitForId("com.android.settings:id/pager", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{6,1}).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 7://other options
    	        		CM.findObjectByRid("android:id/up", 1000).click();
    	        		CM.waitForId("com.android.settings:id/pager", 2000);
    	        		CM.scroll(ud, CM.UP);
    	        		CM.hold(1000);
    	        		break;
    	        	case 8://display
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/action_bar_container", 1000), new int[]{1,0,2}).click();
    	        		CM.hold(2000);
    	        		break;
    	        	case 9://brightness
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{1,0}).click();
    	        		CM.waitForId("android:id/button1", 2000);
    	        		break;
    	        	case 10://wall paper and lock screen
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("com.android.settings:id/pager", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{2,0}).click();
    	        		CM.hold(2000);
    	        		break;
    	        	case 11://sleep
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.settings:id/pager", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{3,0}).click();
    	        		CM.waitForId("android:id/button2", 2000);
    	        		break;
    	        	case 12://font
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("com.android.settings:id/pager", 2000);
    	        		CM.scroll(ud, CM.UP);
    	        		CM.hold(1000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{CM.findObjectByRid("android:id/list", 1000).getChildCount()-1,0}).click();
    	        		if(!CM.waitForId("com.android.settings:id/radiobutton", 1000))
    	        			CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{CM.findObjectByRid("android:id/list", 1000).getChildCount()-2,0}).click();
    	        		CM.waitForId("com.android.settings:id/radiobutton", 1000);
    	        		break;
    	        	case 13://wifi        		
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.settings:id/pager", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/action_bar_container", 1000), new int[]{1,0,0}).click();
    	        		CM.hold(2000);
    	        		CM.findChildbyAtrribut(CM.findObjectByRid("android:id/list", 1000), "", "android.widget.LinearLayout", "", 1, 1000).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 14://add hot point
    	        		UiObject wB=CM.findObjectByClassname("com.oppo.widget.OppoSwitch", 1000);
    	        		if(!wB.isChecked())
    	        		    wB.click();
    	        		if(CM.waitForId("android:id/button1", 1000))
    	        			CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.hold(3000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.waitForId("android:id/button1", 1000);
    	        		break;
    	        	case 15://more options
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNeutral", 1000).click();
    	        		CM.waitForId("android:id/checkbox", 1000);
    	        		break;
    	        	case 16://SIM card setting
    	        		ud.pressBack();
//    	        		CM.waitForId("oppo:id/MajorOperationButtonNeutral", 1000);
//    	        		CM.findObjectByClassname("com.oppo.widget.OppoSwitch", 1000).click();
//    	        		if(CM.waitForId("android:id/button1", 1000))
//    	        			CM.findObjectByRid("android:id/button1", 1000).click();
    	        		ud.pressBack();
//    	        		CM.waitForId("com.android.settings:id/pager", 2000);
    	        		CM.findChildbyAtrribut(CM.findObjectByRid("android:id/list", 1000), "", "android.widget.LinearLayout", "", 2, 1000).click();
    	        		CM.waitForId("com.android.settings:id/Check_Enable", 2000);
    	        		break;
    	        	case 17://open networks
    	        		ud.pressBack();
//    	        		CM.waitForId("com.android.settings:id/pager", 2000);
    	        		CM.findChildbyAtrribut(CM.findObjectByRid("android:id/list", 1000), "", "android.widget.LinearLayout", "", 3, 1000).click();
    	        		CM.waitForId("android:id/checkbox", 1000);
//    	        		CM.waitForId("android:id/up", 2000);
//    	        		wB=CM.findObjectByClassname("com.oppo.widget.OppoSwitch", 1000);
//    	        		if(!wB.isChecked())
//    	        		    wB.click();
//    	        		CM.waitForId("android:id/list", 1000);
    	        		break;
    	        	case 18://open blue tooth
    	        		ud.pressBack();
    	        		CM.findChildbyAtrribut(CM.findObjectByRid("android:id/list", 1000), "", "android.widget.LinearLayout", "", 4, 1000).click();
    	        		if(CM.waitForId("android:id/empty", 1000))
    	        			CM.findObjectByRid("com.android.settings:id/switch_widget",1000).click();
    	        		CM.hold(3000);
//    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
//    	        		CM.hold(1000);
    	        		break;
    	        	case 19://more settings
    	        		if(!CM.waitForId("android:id/empty", 1000))
    	        			CM.findObjectByRid("com.android.settings:id/switch_widget",1000).click();//关闭蓝牙
    	        		ud.pressBack();
    	        		CM.findChildbyAtrribut(CM.findObjectByRid("android:id/list", 1000), "oppo:id/oppo_preference", "android.widget.LinearLayout", "", 5, 1000).click();
    	        		//进入more settings
    	        		CM.waitForId("android:id/checkbox", 1000);
//    	        		ud.pressBack(); 
//    	        		CM.waitForClass("com.oppo.widget.OppoSwitch", 1000);
//    	        		CM.findObjectByClassname("com.oppo.widget.OppoSwitch", 1000).click();
//    	        		ud.pressBack();
//    	        		CM.waitForId("com.android.settings:id/pager", 2000);
//    	        		CM.findChildbyAtrribut(CM.findObjectByRid("android:id/list", 1000), "", "android.widget.LinearLayout", "", 4, 1000).click();
//    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 20://open fly mode
//    	        		CM.findObjectByRid("android:id/checkbox", 1000).click();
//    	        		CM.hold(3000);
    	        		CM.waitForId("android:id/checkbox", 1000);
    	        		break;
    	        	case 21://last part
    	        		CM.waitForId("android:id/checkbox", 1000);
//    	        		CM.findObjectByRid("android:id/checkbox", 1000).click();
//    	        		CM.hold(3000);
//    	        		CM.scroll(ud, CM.UP);
//    	        		CM.hold(1000);
    	        		break;
    	        	case 22:// Safety setting
    	        		ud.pressBack();
//    	        		CM.waitForId("com.android.settings:id/pager", 2000);
    	        		CM.findChildbyAtrribut(CM.findObjectByRid("android:id/list", 1000), "", "android.widget.LinearLayout", "",7, 1000).click();
    	        		CM.waitForId("android:id/checkbox", 1000);
//    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 23://each setting       
    	        		CM.scroll(ud, CM.UP);//last part of safety setting
    	        		CM.hold(1000);
//    	        		UiObject sList=CM.findObjectByRid("android:id/list", 1000);
//    	        		for(int i=0;i<3;i++)
//    	        		{
//    	        			if(CM.findChildbyAtrribut(sList, "", "android.widget.LinearLayout", "", i, 1000).getChild(new UiSelector().index(0)).getClassName().contains("android.widget.RelativeLayout"))
//    	        			{
//    	        				CM.findChildbyAtrribut(sList, "", "android.widget.LinearLayout", "", i, 1000).click();
//    	        				CM.hold(2000);
//    	        				ud.takeScreenshot(new File(snapPath+"/Settings"+23+"_"+i+".png"));
//    	            			ud.pressBack();
//    	            			CM.hold(1500);
//    	        			}
//    	        		}
    	        		break;
    	        	case 24://   Notification center
    	        		ud.pressBack();
    	        		CM.findChildbyAtrribut(CM.findObjectByRid("android:id/list", 1000), "", "android.widget.LinearLayout", "", 8, 1000).click();
    	        		CM.waitForId("com.oppo.notification.center:id/notification_location", 1000);
    	        		break;
    	        	case 25://GPS location
    	        		ud.pressBack();
//    	        		CM.waitForId("com.android.settings:id/pager", 2000);
    	        		CM.findChildbyAtrribut(CM.findObjectByRid("android:id/list", 1000), "", "android.widget.LinearLayout", "", 9, 1000).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 26://account and synchronization
    	        		ud.pressBack();
    	        		CM.hold(2000);
    	        		CM.scroll(ud, CM.UP);
    	        		CM.hold(2000);
//    	        		CM.waitForId("com.android.settings:id/pager", 2000);
//    	        		CM.findChildbyAtrribut(CM.findObjectByRid("android:id/list", 1000), "", "android.widget.LinearLayout", "", 8, 1000).click();
//    	        		CM.hold(1000);
//    	        		break;
    	        	case 27://here is not right
//    	        		ud.pressBack();
//    	        		CM.waitForId("com.android.settings:id/pager", 2000);
//    	        		CM.scroll(ud, CM.UP);
//    	        		CM.hold(1000);
//    	        		CM.scroll(ud, CM.UP);
//    	        		CM.hold(1000);
//    	        		CM.scroll(ud, CM.UP);
//    	        		CM.hold(2000);
//    	        		UiObject HList=CM.findObjectByRid("android:id/list", 1000);
//    	        		int count=HList.getChildCount();
//    	        		for(int i=2;i<count;i++)
//    	        		{
//    	        			UiObject item=CM.findChildbyAtrribut(HList, "", "android.widget.LinearLayout", "", i, 1000);
//    	        			if(item.getChild(new UiSelector().index(0)).getClassName().contains("android.widget.ImageView"))
//    	        			{
//    	        				item.click();
//    	        				CM.hold(2000);
//    	        				if(CM.waitForId("com.android.settings:id/storage_overview", 100)
//    	        						||CM.waitForId("com.android.settings:id/app_on_sdcard", 100)
//    	        						||CM.waitForId("com.android.settings:id/inputmethod_settings", 100)
//    	        						||CM.waitForId("com.oppo.powermanager:id/progress_ring_view", 100)
//    	        						||CM.waitForId("com.android.settings:id/icon_large", 100))
//    	        				{
//    	        					ud.takeScreenshot(new File(snapPath+"/Settings"+27+"_"+i+".png"));
//    	        					ud.pressBack();
//    	        					CM.hold(2000);
//    	        				}else if(CM.waitForId("android:id/checkbox", 1000))
//    	        				{
//    	        					CM.findObjectByRid("android:id/checkbox", 1000).click();
//    	        					CM.hold(2000);
//    	        					CM.findObjectByRid("android:id/checkbox", 1000).click();
//    	        					CM.hold(3000);
//    	        					ud.takeScreenshot(new File(snapPath+"/Settings"+27+"_"+i+".png"));
//    	        					ud.pressBack();
//    	        					CM.hold(2000);
//    	        				}
//    	        				else
//    	        				{
//    	        					ud.takeScreenshot(new File(snapPath+"/Settings"+27+"_"+i+".png"));
//    	        	        		UiObject tList=CM.findObjectByRid("android:id/list", 1000);
//    	        	        		for(int j=0;j<tList.getChildCount();j++)
//    	        	        		{
//    	        	        			if(i==count-1)
//    	        	        				break;
//    	        	        			UiObject sitem=CM.findChildbyAtrribut(tList, "", "android.widget.LinearLayout", "", j, 1000);
//    	        	        			if(!sitem.getChild(new UiSelector().index(0)).getClassName().contains("android.widget.TextView"))
//    	        	        			{
//    	        	        				sitem.click();
//    	        	        				CM.hold(2000);
//    	        	        				ud.takeScreenshot(new File(snapPath+"/Settings"+27+"_"+i+"_"+j+".png"));
//    	        	        				ud.pressBack();
//    	        	        				CM.hold(2000);
//    	        	        			}
//    	        	        		}
//    	        	        		ud.pressBack();
//    	        	        		CM.hold(2000);
//    	        				}
//    	        			}
//    	        		}
//    	        		ud.pressBack();
    	        	default:
    	        		break;
    	        	}
    	        }

    	    }
    	    public static class SafeDemo
    	    {
    	    	public static void test(UiDevice ud,int step) throws Exception
    	        {
    	        	switch(step)
    	        	{
    	        	case 1://enter monitor
    	        		CM.waitForNewApp("com.oppo.safe", 2000);
    	        		break;
    	        	case 2://memory cleanup
    	        		CM.findObjectByRid("com.oppo.safe:id/button_accelerate", 1000).click();
    	        		CM.waitForId("com.oppo.oppomemoryclear:id/center", 2000);
    	        		break;
    	        	case 3://timing clean
    	        	    CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(1).resourceId("oppo:id/oppo_preference")).click();
    	        	    CM.waitForId("android:id/button2", 1000);
    	        	    break;
    	        	case 4://white list
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(3).resourceId("oppo:id/oppo_preference")).click();
    	        		CM.waitForId("com.android.settings:id/option_menu_bar", 1000);
    	        		break;
    	        	case 5://application permission
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.safe:id/securesafe_not_cmcc_layout", 1000);
    	        		CM.findObjectByRid("com.oppo.safe:id/button_permission", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 6://encryption
    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(1).resourceId("oppo:id/oppo_preference")).click();
    	        		CM.waitForId("com.oppo.safe:id/privacy_icon_center", 1000);
    	        		break;
    	        	case 7://encryption typ
    	        		CM.findObjectByClassname("com.oppo.widget.OppoSwitch", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 8://pop window
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.safe:id/shield", 1000);
    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(3).resourceId("oppo:id/oppo_preference")).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 9://other selections
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.safe:id/securesafe_not_cmcc_layout", 1000);
    	        		new UiScrollable(new UiSelector().resourceId("android:id/list")).scrollToEnd(2);
    	        		CM.hold(2000);
    	        		break;
    	        	case 10://data saving
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.safe:id/securesafe_not_cmcc_layout", 1000);
    	        		CM.findObjectByRid("com.oppo.safe:id/button_data", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 11://data details
    	        		CM.findObjectByRid("com.oppo.trafficmonitor:id/change_to_traffic_stats_view", 1000).click();
    	        		CM.waitForId("android:id/tabhost", 1000);
    	        		break;
    	        	case 12://menu selections
    	        		ud.pressMenu();
    	        		CM.hold(2000);
    	        		break;
    	        	case 13://connection control
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		CM.findObjectByRid("com.oppo.trafficmonitor:id/change_to_traffic_control_view", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 14://new installation default setting
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/checkbox", 1000);
    	        		break;
    	        	case 15://save setting
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.trafficmonitor:id/change_to_traffic_control_view", 1000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/checkbox", 1000);
    	        		break;
    	        	case 16://month package
    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(1).resourceId("oppo:id/oppo_preference")).click();
    	        		CM.waitForId("android:id/button2", 1000);
    	        		break;
    	        	case 17://settling day
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(2).resourceId("oppo:id/oppo_preference")).click();
    	        		CM.waitForId("android:id/button2", 1000);
    	        		break;
    	        	case 18://calibration
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(3).resourceId("oppo:id/oppo_preference")).click();
    	        		CM.waitForId("android:id/button2", 1000);
    	        		break;
    	        	case 19://reminder
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(5).resourceId("oppo:id/oppo_preference")).click();
    	        		CM.hold(2000);
    	        		break;
    	        	case 20://power manager
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.safe:id/securesafe_not_cmcc_layout", 1000);
    	        		CM.findObjectByRid("com.oppo.safe:id/button_power", 1000).click();
    	        		CM.waitForId("android:id/checkbox", 2000);
    	        		break;
    	        	case 21://other selections
    	        		new UiScrollable(new UiSelector().resourceId("android:id/list")).scrollToEnd(2);
    	        		new UiScrollable(new UiSelector().resourceId("android:id/list")).scrollToEnd(2);
    	        		
    	        		CM.hold(2000);
    	        		break;
    	        	case 22://power details
    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(3).resourceId("oppo:id/oppo_preference")).click();
    	        		CM.waitForId("android:id/action_bar_title", 1000);
    	        		break;
    	        	case 23://harass
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.safe:id/securesafe_not_cmcc_layout", 1000);
    	        		CM.findObjectByRid("com.oppo.safe:id/button_harass", 1000).click();
    	        		CM.waitForId("com.oppo.blacklist:id/pagertabstrip", 2000);
    	        		break;
    	        	case 24://harass setting         
    	        		CM.findObjectByRid("com.oppo.blacklist:id/pagertabstrip", 1000).getChild(new UiSelector().index(2)).click();
//    	        		CM.findObjectByRid("com.oppo.blacklist:id/pagertabstrip", 1000).getChild(new UiSelector().index(2)).click();
    	        		CM.waitForId("android:id/checkbox", 2000);
    	        		break;
    	        	case 25://quiet mode      Quiet Time
    	        		ud.pressBack();
    	        		CM.findObjectByRid("com.oppo.safe:id/button_holiday", 1000).click();
    	        		CM.waitForId("android:id/action_bar_title", 1000);
//    	        		ud.pressBack();
//    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(8).resourceId("oppo:id/oppo_preference")).click();
//    	        		CM.waitForId("com.oppo.holidaymode:id/main_icon", 2000);
    	        		break;
    	        	case 26://visitor          when calls and notifications arrive
    	        		CM.findObjectByRid("android:id/summary", 1000).click();
    	        		CM.waitForId("android:id/text1", 1000);
//    	        		ud.pressBack();
//    	        		ud.pressBack();
//    	        		CM.waitForId("com.oppo.safe:id/securesafe_not_cmcc_layout", 1000);
//    	        		CM.findObjectByRid("com.oppo.safe:id/button_vistor", 1000).click();
//    	        		CM.waitForClass("com.oppo.widget.OppoSwitch", 2000);
    	        		break;
    	        	case 27://visitor lock
//    	        		CM.findObjectByRid("android:id/action_bar", 1000).getChild(new UiSelector().className("com.oppo.widget.OppoSwitch")).click();
//    	        		CM.waitForId("android:id/button1", 2000);
    	        		break;
    	        	case 28://change lock
//    	        		CM.findObjectByRid("android:id/button1", 1000).click();
//    	        		CM.waitForId("android:id/up", 1000);
    	        		break;
    	        	case 29://secret contacts
//    	        		ud.pressBack();
//    	        		CM.waitForClass("com.oppo.widget.OppoSwitch", 1000);
//    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(0).resourceId("oppo:id/oppo_preference")).click();
//    	        		CM.waitForId("oppo:id/MajorOperationButtonPositive", 2000);
    	        		break;
    	        	case 30://secret photos
//    	        		ud.pressBack();
//    	        		CM.waitForClass("com.oppo.widget.OppoSwitch", 1000);
//    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(1).resourceId("oppo:id/oppo_preference")).click();
//    	        		CM.waitForId("oppo:id/MajorOperationButtonPositive", 2000);
    	        		break;
    	        	case 31://secret videos
//    	        		ud.pressBack();
//    	        		CM.waitForClass("com.oppo.widget.OppoSwitch", 1000);
//    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(2).resourceId("oppo:id/oppo_preference")).click();
//    	        		CM.waitForId("oppo:id/MajorOperationButtonPositive", 2000);
    	        		break;
    	        	case 32://hidden application
//    	        		ud.pressBack();
//    	        		CM.waitForClass("com.oppo.widget.OppoSwitch", 1000);
//    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(3).resourceId("oppo:id/oppo_preference")).click();
//    	        		CM.waitForId("android:id/checkbox", 2000);
    	        		break;
    	        	case 33://over
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		break;
    	        	default:
    	        		break;
    	        	}
    	        }
    	    }
    	    public static class RecorderDemo
    	    {
    	    	public static void test(UiDevice ud,int step) throws Exception
    	        {
    	        	switch(step)
    	        	{
    	        	case 1://enter recorder
    	        		CM.waitForNewApp("oppo.multimedia.soundrecorder", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/up", 1000);
    	        		if(CM.waitForId("oppo:id/emptybottle", 1000))
    	        		{
    	        			ud.pressBack();
    	        			break;
    	        		}
    	        		else
    	        		{
    	        			CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	            		CM.waitForId("oppo.multimedia.soundrecorder:id/item_cancel_all", 1000);
    	            		CM.findObjectByRid("oppo.multimedia.soundrecorder:id/item_cancel_all", 1000).click();
    	            		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	            		CM.waitForId("android:id/button2", 2000);
    	            		CM.findObjectByRid("android:id/button1", 1000).click();
    	            		CM.waitForId("android:id/up", 2000);
    	            		ud.pressBack();
    	            		CM.waitForId("oppo.multimedia.soundrecorder:id/recorder_panel", 2000);
    	        		}
    	        		break;
    	        	case 2://start record
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.hold(5000);
    	        		break;
    	        	case 3://pause
//    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		ud.click(ud.getDisplayWidth()/2, ud.getDisplayHeight()*9/10);
    	        		CM.hold(1000);
    	        		break;
    	        	case 4://stop
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 5://menu options
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		break;
    	        	case 6://timing record
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 7://settings
    	        		ud.pressBack();
    	        		CM.waitForId("oppo.multimedia.soundrecorder:id/middle_image", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 8://default name
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{0,0}).click();
    	        		CM.waitForId("android:id/button1", 2000); 
    	        		break;
    	        	case 9://file format
    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.waitForId("android:id/list", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{1,0}).click();
    	        		CM.waitForId("android:id/button2", 2000); 
    	        		break;
    	        	case 10://storage position
//    	        		CM.findObjectByRid("android:id/button2", 1000).click();
//    	        		CM.waitForId("android:id/list", 2000);
//    	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{2,0}).click();
//    	        		CM.waitForId("android:id/button2", 2000); 
    	        		break;
    	        	case 11://recordings list
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("android:id/list", 2000);
    	        		ud.pressBack();
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 12://edit
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("oppo.multimedia.soundrecorder:id/item_cancel_all", 2000);
    	        		break;
    	        	case 13://more edit options
    	        		CM.findObjectByRid("oppo.multimedia.soundrecorder:id/item_cancel_all", 1000).click();
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		break;
    	        	case 14://set as
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
    	        		CM.waitForId("com.oppo.music:id/ck_whole_track", 2000);
    	        		break;
    	        	case 15://rename
    	        		ud.pressBack();
    	        		CM.waitForId("oppo.multimedia.soundrecorder:id/item_cancel_all", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView2", 1000).click();
    	        		CM.waitForId("oppo:id/oppo_rename_dialog_edit", 2000);
    	        		break;
    	        	case 16://details
    	        		CM.setText(ud, CM.findObjectByRid("oppo:id/oppo_rename_dialog_edit", 1000), "rename");
    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("oppo.multimedia.soundrecorder:id/item_cancel_all", 2000);
    	        		CM.findObjectByRid("oppo.multimedia.soundrecorder:id/item_cancel_all", 1000).click();
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView3", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);
    	        		break;
    	        	case 17://share
    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.waitForId("android:id/alertTitle", 2000);
    	        		break;
    	        	case 18://delete
    	        		ud.pressBack();
    	        		CM.waitForId("oppo.multimedia.soundrecorder:id/item_cancel_all", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);
    	        		break;
    	        	case 19://additional recording
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.waitForId("oppo.multimedia.soundrecorder:id/item_cancel_all", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
    	        		CM.waitForId("oppo.multimedia.soundrecorder:id/middle_image", 2000);
    	        		CM.hold(10000);
    	        		break;
    	        	case 20://play recording
    	        		ud.click(ud.getDisplayWidth()/2, ud.getDisplayHeight()*9/10);
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.hold(2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/up", 2000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("oppo.multimedia.soundrecorder:id/browser_list", 1000), new int[]{0,0}).click();
    	        		CM.hold(5000);
    	        		CM.findChildByPosistion(CM.findObjectByRid("oppo.multimedia.soundrecorder:id/browser_list", 1000), new int[]{0,0}).click();
    	        		CM.hold(2000);
    	        		break;
    	        	case 21://delete
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		if(CM.waitForId("oppo.multimedia.soundrecorder:id/item_cancel_all", 1000))
    	        		{
    	            		CM.findObjectByRid("oppo.multimedia.soundrecorder:id/item_cancel_all", 1000).click();
    	            		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		}
    	        		CM.waitForId("android:id/button1", 2000);
    	        		break;
    	        	case 22://over
    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		CM.waitForId("oppo:id/MajorOperationButtonPositive", 2000);
    	        		ud.pressBack();
    	        		CM.waitForId("oppo.multimedia.soundrecorder:id/middle_image", 2000);
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.launcher:id/all_apps_2d_grid", 2000);
    	        		break;
    	        	default:
    	        		break;
    	        	}
    	        }
    	    }
    	    public static class DesktopDemo
    	    {
    	    	public static void test(UiDevice ud,int step) throws Exception
    	        {
    	        	switch(step)
    	        	{
    	        	case 1://enter desktop
    	        		CM.waitForId("android.appwidget.AppWidgetHostView", 1000);
    	        		break;
    	        	case 2:
    	        		CM.scroll(ud, CM.LEFT);
    	        		CM.hold(1000);
    	        		break;
    	        	case 3:
    	        		CM.scroll(ud, CM.RIGHT);
    	        		CM.scroll(ud, CM.RIGHT);
    	        		CM.hold(1000);
    	        		break;
    	        	case 4://move icon
//    	        		CM.scroll(ud, CM.LEFT);
    	        		ud.pressHome();
    	        		CM.hold(1000);
    	        		Rect r=CM.findObjectByRid("com.oppo.launcher:id/dockbar", 1000).getBounds();
    	        		ud.drag(r.width()*3/8, r.centerY(), r.width()*3/4, r.centerY(), 5);
    	        		CM.hold(2000);  
    	        		break;
    	        	case 5://delete widget
//    	        		r=CM.findObjectByRid("com.oppo.launcher:id/dockbar", 1000).getBounds();
//    	        		ud.drag(r.width()*3/8, r.centerY(), r.width()*3/4, r.centerY(), 5);
//    	        		CM.hold(2000);
//    	        		CM.findObjectByRid("com.oppo.launcher:id/dockbar", 1000).clickTopLeft();
    	        		CM.hold(1000);
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("com.oppo.launcher:id/dockbar", 1000);
    	        		break;
    	        	case 6://menu options
    	        		ud.pressMenu();
    	        		CM.waitForId("com.oppo.launcher:id/tog_title", 2000);
    	        		break;
    	        	case 7://add and enter widget  
    	        		CM.scroll(ud, CM.LEFT);
    	        		CM.scroll(ud, CM.LEFT);
    	        		CM.findObjectByRid("com.oppo.launcher:id/tog_title_0", 1000).click();
    	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.launcher:id/toggle_bar", 1000), new int[]{2,0}).click();
//    	        		if(!CM.waitForId("com.oppo.widget.digitalclock:id/digital_clock_main_frame", 1000))
//    	        			CM.findChildByPosistion(CM.findObjectByRid("com.oppo.launcher:id/toggle_bar", 1000), new int[]{2,0}).click();
    	        		CM.hold(1000);
    	        		ud.pressBack();
    	        		CM.hold(1000);
//    	        		if(CM.waitForId("com.oppo.widget.digitalclock:id/analog_clock", 1000))
//    	        		{
//    	    	    		CM.findObjectByRid("com.oppo.widget.digitalclock:id/analog_clock", 1000).click();
//    	    	    		CM.waitForId("com.oppo.alarmclock:id/main_fram", 2000);
//    	    	    		ud.pressBack();
//    	    	    		CM.waitForNewApp("com.oppo.launcher", 2000);
//    	        		}
    	        		break;
    	        	case 8://Select wallpaper
    	        		ud.pressMenu();
    	        		CM.waitForId("com.oppo.launcher:id/tog_title", 1000);
    	        		CM.findObjectByRid("com.oppo.launcher:id/tog_title_1", 1000).click();
    	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.launcher:id/toggle_bar", 1000), new int[]{0,0}).click();
    	        		CM.waitForId("android:id/action_bar_title", 2000);
    	        		break;
    	        	case 9://static wall paper
    	        		Rect wR=CM.findObjectByRid("android:id/content", 1000).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(0)).getBounds();
    	        		ud.click(wR.width()/6, wR.centerY());
    	        		CM.findObjectByRid("com.nearme.themespace:id/tab_title", 1000).getChild(new UiSelector().index(4)).click();
    	        		
    	        		CM.waitForId("com.nearme.themespace:id/local_picture_lib", 2000);
    	        		break;
    	        	case 10://album wall paper
    	        		ud.pressBack();
//    	        		CM.waitForId("android:id/alertTitle", 2000);
//    	        		wR=CM.findObjectByRid("android:id/content", 1000).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(0)).getBounds();
//    	        		ud.click(wR.width()*5/6, wR.centerY());
//    	        		CM.waitForId("android:id/up", 2000);
    	        		break;
    	        	case 11://live wall papers
    	 //       		ud.pressBack();
    	        		CM.waitForId("android:id/action_bar_title", 2000);
    	        		wR=CM.findObjectByRid("android:id/content", 1000).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(0)).getBounds();
    	        		ud.click(wR.width()/2, wR.centerY());
    	        		CM.waitForId("com.android.wallpaper.livepicker:id/themeGrid", 2000);
    	        		break;
    	        	case 12://photos wall papers
    	        		ud.pressBack();
    	        		CM.waitForId("android:id/action_bar_title", 1000);
    	        		wR=CM.findObjectByRid("android:id/content", 1000).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(0)).getBounds();
    	        		ud.click(wR.width()*5/6, wR.centerY());
    	        		
    	        		CM.hold(1000);
    	        		break;
    	        	case 13://add music space
//    	        		ud.pressMenu();
//    	        		CM.waitForId("com.oppo.launcher:id/tog_title", 2000);
//    	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.launcher:id/toggle_bar", 1000), new int[]{1,0}).click();
//    	        		CM.hold(1000);
//    	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.launcher:id/toggle_bar", 1000), new int[]{1,0}).click();
//    	        		CM.hold(1000);
//    	        		CM.findObjectByRid("com.oppo.launcher:id/tog_title_1", 1000).click();
//    	        		if(CM.waitForId("com.oppo.launcher:id/tog_title_olab_normal", 500))
//    	        			CM.findObjectByRid("com.oppo.launcher:id/tog_title_olab_normal", 500).click();
//    	        		else
//    	        		    CM.findObjectByRid("com.oppo.launcher:id/tog_title_olab", 1000).click();
//    	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.launcher:id/toggle_bar", 1000), new int[]{0,0}).click();
//    	        		CM.hold(1000);
//    	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.launcher:id/toggle_bar", 1000), new int[]{1,0}).click();
//    	        		CM.hold(1000);
//    	        		ud.pressBack();
//    	        		CM.hold(1000);
//    	        		CM.scroll(ud, CM.RIGHT);
//    	        		CM.hold(1000);
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		ud.pressHome();
    	        		ud.pressMenu();
    	        		CM.waitForId("com.oppo.launcher:id/tog_title_olab_normal", 1000);
    	        		CM.findObjectByRid("com.oppo.launcher:id/tog_title_olab_normal", 1000).click();
    	        		CM.hold(3000);
    	        		break;
    	        	case 14://recent task
    	        		ud.pressBack();
    	        		ud.pressHome();
    	        		CM.waitForId("com.oppo.launcher:id/dockbar", 2000);
    	        		ud.pressRecentApps();
    	        		CM.waitForId("com.oppo.recents:id/app_clear_anim", 2000);
    	        		break;
    	        	case 15://clear recent
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.launcher:id/dockbar", 2000);
    	        		break;
    	        	default:
    	        		break;    			
    	        	}
    	        }
    	    }
    	    public static class AlbumDemo
    	    {
    	    	public static void test(UiDevice ud,int step) throws Exception
    	        {
    	        	switch(step)
    	        	{
    	        	case 1://enter album
    	        		CM.waitForId("android:id/action_bar_overlay_layout", 5000);
    	        		break;
    	        	case 2://capture 5
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForNewApp("com.oppo.camera", 3000);
    	        		for(int i=0;i<5;i++)
    	        		{
    	        			CM.findObjectByRid("com.oppo.camera:id/shutter_button", 1000).click();
    	        			CM.hold(3000);
    	        		}
    	        		break;
    	        	case 3://thumbnail
    	        		CM.findObjectByRid("com.oppo.camera:id/thumbnail", 1000).click();
    	        		CM.hold(4000);
    	        		break;
    	        	case 4://back album
    	        		ud.pressBack();
    	        		CM.waitForNewApp("com.oppo.camera", 5000);
    	        		ud.pressBack();
    	        		CM.hold(3000);
    	        		break;
    	        	case 5://more options
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.hold(2000);
    	        		break;
    	        	case 6://edit
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
    	        		CM.hold(2000);
    	        		CM.findObjectByRid("com.oppo.gallery3d:id/action_select_all", 1000).click();
    	        		CM.hold(2000);
    	        		break;
    	        	case 7://delete
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
    	        		CM.waitForId("android:id/button2", 2000);
    	        		break;
    	        	case 8://set as
//    	        		CM.findObjectByRid("android:id/button2", 1000).click();
//    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
//    	        		CM.waitForId("android:id/button2", 2000);
    	        		break;
    	        	case 9://share
    	        		CM.findObjectByRid("android:id/button2", 1000).click();
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNeutral", 1000).click();
    	        		CM.waitForId("android:id/button2", 2000);
    	        		break;
    	        	case 10://album
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.gallery3d:id/action_select_all", 1000);
    	        		ud.pressBack();
    	        		CM.hold(2000);
    	        		break;
    	        	case 11://cloud
    	        		break;
    	        	case 12://combine
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
    	        		CM.waitForId("com.oppo.gallery3d:id/together_submit", 2000);
    	        		break;
    	        	case 13://choose picture
    	        		Rect gR=CM.findObjectByRid("com.oppo.gallery3d:id/gl_root_view", 1000).getBounds();
    	        		ud.click(gR.width()/8, gR.height()*3/10);
    	        		CM.hold(2000);
    	        		ud.click(gR.width()/8, gR.height()*3/10);
    	        		CM.hold(1000);
    	        		ud.click(gR.width()*3/8, gR.height()*3/10);
    	        		CM.hold(1000);
    	        		break;
    	        	case 14://submit combine
    	        		CM.findObjectByRid("com.oppo.gallery3d:id/together_submit", 1000).click();
    	        		CM.waitForId("com.oppo.gallery3d:id/template_button", 5000);
    	        		break;
    	        	case 15://save combine
    	        		CM.findObjectByRid("com.oppo.gallery3d:id/together_btn_save", 1000).click();
    	        		CM.waitForId("com.oppo.gallery3d:id/together_btn_share", 5000);
    	        		break;
    	        	case 16://share combine
    	        		CM.findObjectByRid("com.oppo.gallery3d:id/together_btn_share", 1000).click();
    	        		CM.waitForId("android:id/resolver_grid", 2000);
    	        		break;
    	        	case 17://play slide
//    	        		CM.findObjectByRid("android:id/button1", 1000).click();
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.gallery3d:id/together_btn_pre", 2000);
    	        		CM.findObjectByRid("com.oppo.gallery3d:id/together_btn_pre", 1000).click();
    	        		CM.waitForId("com.oppo.gallery3d:id/together_submit", 1000);
    	        		CM.findObjectByRid("android:id/up", 1000).click();
    	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 2000);
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView2", 1000).click();
    	        		CM.hold(2000);
    	        		break;
    	        	case 18://picture view
    	        		ud.pressBack();
    	        		CM.hold(2000);
    	        		ud.click(ud.getDisplayWidth()/8, ud.getDisplayHeight()*5/18);
    	        		CM.hold(3000);
    	        		break;
    	        	case 19://edit
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
    	        		CM.waitForId("com.oppo.gallery3d:id/undo_button", 2000);
    	        		break;
    	        	case 20://picture exposure
    	        		CM.findObjectByRid("com.oppo.gallery3d.photoeditor:id/filter_button", 1000).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 21://picture artistic
    	        		CM.findObjectByRid("com.oppo.gallery3d.photoeditor:id/frame_button", 1000).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 22://picture color
    	        		CM.findObjectByRid("com.oppo.gallery3d.photoeditor:id/artist_button", 1000).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 23://picture fix
    	        		CM.findObjectByRid("com.oppo.gallery3d.photoeditor:id/fix_button", 1000).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 24://details
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView2", 1000).click();
    	        		CM.waitForId("android:id/button1", 2000);
    	        		break;
    	        	case 25://all picture
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.gallery3d:id/gl_root_view", 2000);
    	        		ud.pressBack();
    	        		CM.waitForClass("android.widget.HorizontalScrollView", 2000);
    	        		CM.findObjectByMutiAttribut("", "android.app.ActionBar$Tab", "", 1, 1000).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 26://hide folder
    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView2", 1000).click();
    	        		CM.waitForId("com.oppo.gallery3d:id/action_select_all", 2000);
    	        		break;
    	        	case 27://cloud
    	        		ud.pressBack();
    	        		CM.hold(1000);
    	        		break;
    	        	case 28://quit
    	        		ud.pressBack();
    	        		break;
    	        	default:
    	        		break;    			
    	        	}
    	        }
    	    }
    	    public static class CameraDemo
    	    {
    	    	public static void test(UiDevice ud,int step) throws Exception
    	        {
    	        	switch(step)
    	        	{
    	        	case 1://enter camera
    	        		CM.waitForId("com.oppo.camera:id/camera", 5000);
    	        		while(CM.waitForId("com.oppo.camera:id/guide_view", 1000))
    	        		{
    	        		    CM.scroll(ud, CM.UP);
    	        		}
    	        		break;
    	        	case 2://capture
    	        		for(int i=0;i<5;i++)
    	        		{
    	        		    CM.findObjectByRid("com.oppo.camera:id/shutter_button", 1000).click();
    	        		    CM.hold(3000);
    	        		}
    	        		break;
    	        	case 3://thumbnail
    	        		CM.findObjectByRid("com.oppo.camera:id/thumbnail", 1000).click();
    	        		CM.hold(3000);
    	        		break;
    	        	case 4://record
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.camera:id/thumbnail", 2000);
    	        		CM.findObjectByRid("com.oppo.camera:id/oppo_video_lab", 1000).click();
    	        		Rect bR=CM.findObjectByRid("com.oppo.camera:id/shutter_button", 1000).getBounds();
    	        		for(int i=0;i<5;i++)
    	        		{
    	        			ud.click(bR.centerX(), bR.centerY());
    	        		    CM.hold(5000);
    	        		    ud.click(bR.centerX(), bR.centerY());
    	        		    CM.hold(3000);
    	        		}
    	        		CM.findObjectByRid("com.oppo.camera:id/thumbnail", 1000).click();
    	        		CM.hold(3000);
    	        		break;
    	        	case 5://switch camera
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.camera:id/thumbnail", 2000);
    	        		if(CM.waitForId("com.oppo.camera:id/oppo_setting_bar", 1000))
    	        		{
    	        			CM.findObjectByRid("com.oppo.camera:id/oppo_setting_bar", 1000).getChild(new UiSelector().index(1)).click();
    	        			CM.hold(3000);
    	        		}
    	        		bR=CM.findObjectByRid("com.oppo.camera:id/shutter_button", 1000).getBounds();
    	        		for(int i=0;i<2;i++)
    	        		{
    	        		    ud.click(bR.centerX(), bR.centerY());
    	        		    CM.hold(5000);
    	        		    ud.click(bR.centerX(), bR.centerY());
    	        		    CM.hold(3000);
    	        		}
    	        		if(CM.waitForId("com.oppo.camera:id/oppo_setting_bar", 1000))
    	        		{
    	        			CM.findObjectByRid("com.oppo.camera:id/oppo_setting_bar", 1000).getChild(new UiSelector().index(1)).click();
    	        			CM.hold(3000);
    	        		}
    	        		CM.findObjectByRid("com.oppo.camera:id/thumbnail", 1000).click();
    	        		CM.hold(3000);
    	        		break;
    	        	case 6://flash
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.camera:id/thumbnail", 2000);
    	        		CM.findObjectByRid("com.oppo.camera:id/oppo_setting_bar", 1000).getChild(new UiSelector().index(0)).click();
    	        		CM.hold(2000);
    	        		break;
    	        	case 7://widget
    	        		ud.click(ud.getDisplayWidth()/2, ud.getDisplayHeight()/2);
    	        		CM.findObjectByRid("com.oppo.camera:id/plug_popup_button", 1000).click();
    	        		CM.waitForId("com.oppo.camera:id/camera_mode_title", 2000);
    	        		break;
    	        	case 8://camera market
    	        		CM.findObjectByRid("com.oppo.camera:id/camera_mode_title", 1000).click();
    	        		CM.waitForId("com.oppo.camera:id/plugin_playstore_back_icon", 1000);
    	        		break;
    	        	case 9://setting
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.camera:id/camera_mode_title", 2000);
    	        		CM.findObjectByRid("com.oppo.camera:id/camera_mode_menu_layout", 1000).getChild(new UiSelector().index(4).className("android.widget.RelativeLayout")).click();
    	        		CM.waitForId("com.oppo.camera:id/camera_setting_menu_root", 2000);
    	        		break;
    	        	case 10://the remaining part
    	        		CM.scroll(ud, CM.UP);
    	        		CM.hold(1000);
    	        		break;
    	        	case 11://the other side options
    	        		ud.pressBack();
    	        		ud.pressBack();
    	        		CM.waitForId("com.oppo.camera:id/thumbnail", 2000);
    	        		break;
    	        	case 12://record and pause
    	        		bR=CM.findObjectByRid("com.oppo.camera:id/shutter_button", 1000).getBounds();
    	        		Rect sR=CM.findObjectByRid("com.oppo.camera:id/plug_popup_button", 1000).getBounds();
    	        		ud.click(bR.centerX(), bR.centerY());
    	    		    CM.hold(5000);
    	    		    ud.click(sR.centerX(), sR.centerY());
    	    		    CM.hold(3000);
    	    		    ud.click(bR.centerX(), bR.centerY());
    	    		    CM.hold(3000);
    	    		    break;
    	        	case 13://capture by volume key
    	        		ud.pressKeyCode(24);
    	        		CM.hold(3000);
    	        		ud.pressKeyCode(25);
    	        		CM.hold(3000);
    	        		break;
    	        	default:
    	        		break;    			
    	        	}
    	        }
    	    }
    	    public static class NotificationDemo
    	    {
    	    	public static void test(UiDevice ud,int step) throws Exception
    	        {
    	        	switch(step)
    	        	{
    	        	case 1://open notification
    	        		CM.waitForNewApp("com.android.systemui", 5000);
    	        		Rect r=CM.findObjectByRid("com.android.systemui:id/handle_arrow", 1000).getBounds();
    	        		CM.scroll(ud, CM.DOWN);
    	        		CM.hold(2000);
       	        		CM.scroll(ud, CM.DOWN);
//    	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
//    	        		{
//    	        			
//    	        			ud.drag(r.centerX(), r.centerY(), r.centerX(), ud.getDisplayHeight()/2, 4);
//    	        		    CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000);
//    	        		}
    	        		CM.waitForId("com.android.systemui:id/qstv_15", 2000);
    	        		break;
    	        	case 2://打开关闭蓝牙
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_1", 1000).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 3:
    	        		if(CM.waitForId("android:id/button1", 1000))
    	        		{
    	        			CM.findObjectByRid("android:id/button1", 1000).click();
    	        			CM.hold(1000);
    	        		}
    	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
    	            	{
    	        			CM.scroll(ud, CM.DOWN);
    	            	}
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_1", 1000).click();
    	        		CM.hold(2000);
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_2", 1000).click();
    	        		CM.hold(2000);
    	        		break;
    	        	case 4:
    	        		if(CM.waitForId("android:id/button1", 1000))
    	        		{
    	        			CM.findObjectByRid("android:id/button1", 1000).click();
    	        			CM.hold(1000);
    	        		}
    	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
    	            	{
    	        			CM.scroll(ud, CM.DOWN);
    	            	}
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_2", 1000).click();
    	        		CM.hold(2000);
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_3", 1000).click();
    	        		CM.hold(4000);
    	        		break;
    	        	case 5:
    	        		if(CM.waitForId("android:id/button1", 1000))
    	        		{
    	        			CM.findObjectByRid("android:id/button1", 1000).click();
    	        			CM.hold(1000);
    	        		}
    	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
    	            	{
    	        			CM.scroll(ud, CM.DOWN);
    	            	}
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_3", 1000).click();
    	        		CM.hold(2000);
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_4", 1000).click();
    	        		CM.hold(4000);
//    	         		if(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 1000))
//    	        		{
//    	        			ud.drag(ud.getDisplayWidth()/2, 5, ud.getDisplayWidth()/2, ud.getDisplayHeight()/2, 4);
//    	        			if(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
//    	        			{
//    	        			    r=CM.findObjectByRid("com.android.systemui:id/handle_arrow", 1000).getBounds();
//    	            		    ud.drag(r.centerX(), r.centerY(), r.centerX(), ud.getDisplayHeight()/2, 4);
//    	            		    CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000);
//    	        			}
//    	        		}
//    	       		CM.findObjectByRid("com.android.systemui:id/shortcuts_linear2", 1000).getChild(new UiSelector().index(0)).click();
//    	       		CM.hold(5000);
//    	        		if(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 1000))
//    	        		{
//    	        			ud.drag(ud.getDisplayWidth()/2, 5, ud.getDisplayWidth()/2, ud.getDisplayHeight()/2, 4);
//    	        			if(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
//    	        			{
//    	        			    r=CM.findObjectByRid("com.android.systemui:id/handle_arrow", 1000).getBounds();
//    	            		    ud.drag(r.centerX(), r.centerY(), r.centerX(), ud.getDisplayHeight()/2, 4);
//    	            		    CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000);
//    	        			}
//    	        		}
//    	        		CM.findObjectByRid("com.android.systemui:id/shortcuts_linear2", 1000).getChild(new UiSelector().index(1)).click();
//    	        		CM.hold(4000);
    	        		break;
    	        	case 6:
    	        		if(CM.waitForId("android:id/button1", 1000))
    	        		{
    	        			CM.findObjectByRid("android:id/button1", 1000).click();
    	        			CM.hold(1000);
    	        		}
    	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
    	            	{
    	        			CM.scroll(ud, CM.DOWN);
    	            	}
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_4", 1000).click();
    	        		CM.hold(4000);
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_6", 1000).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 7:
    	        		if(CM.waitForId("android:id/button1", 1000))
    	        		{
    	        			CM.findObjectByRid("android:id/button1", 1000).click();
    	        			CM.hold(1000);
    	        		}
    	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
    	            	{
    	        			CM.scroll(ud, CM.DOWN);
    	            	}
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_6", 1000).click();
    	        		CM.hold(4000);
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_7", 1000).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 8:
    	        		if(CM.waitForId("android:id/button1", 1000))
    	        		{
    	        			CM.findObjectByRid("android:id/button1", 1000).click();
    	        			CM.hold(1000);
    	        		}
    	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
    	            	{
    	        			CM.scroll(ud, CM.DOWN);
    	            	}
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_7", 1000).click();
    	        		CM.hold(4000);
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_8", 1000).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 9:
    	        		if(CM.waitForId("android:id/button1", 1000))
    	        		{
    	        			CM.findObjectByRid("android:id/button1", 1000).click();
    	        			CM.hold(1000);
    	        		}
    	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
    	            	{
    	        			CM.scroll(ud, CM.DOWN);
    	            	}
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_8", 1000).click();
    	        		CM.hold(4000);
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_9", 1000).click();
    	        		CM.hold(1000);
//    	        		if(CM.waitForId("android:id/title_template", 1000))
//    	        		{
//    	        			Rect cR=CM.findObjectByRid("android:id/title_template", 1000).getBounds();
//    	        			ud.click(cR.width()*3/4, cR.bottom-8);
//    	        			CM.hold(1000);
//    	        			if(CM.waitForId("android:id/button1", 1000))
//    	            		{
//    	            			CM.findObjectByRid("android:id/button1", 1000).click();
//    	            			CM.hold(1000);
//    	            		}
//    	        			ud.openNotification();
//    	        			CM.waitForId("com.android.systemui:id/handle_arrow", 1000);
//    	        			r=CM.findObjectByRid("com.android.systemui:id/handle_arrow", 1000).getBounds();
//    	            		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
//    	            		{
//    	            		    ud.drag(r.centerX(), r.centerY(), r.centerX(), ud.getDisplayHeight()/2, 4);
//    	            		    CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000);
//    	            		}
//    	        		}
//    	        		CM.findObjectByRid("com.android.systemui:id/shortcuts_linear2", 1000).getChild(new UiSelector().index(4)).click();
//    	        		CM.hold(1000);
//    	        		CM.findObjectByRid("com.android.systemui:id/shortcuts_linear1", 1000).getChild(new UiSelector().index(0)).click();
//    	        		CM.hold(1000);
    	        		break;
    	        	case 10:
    	        		if(CM.waitForId("android:id/button1", 1000))
    	        		{
    	        			CM.findObjectByRid("android:id/button1", 1000).click();
    	        			CM.hold(1000);
    	        		}
    	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
    	            	{
    	        			CM.scroll(ud, CM.DOWN);
    	            	}
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_9", 1000).click();
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_11", 1000).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 11:
    	        		if(CM.waitForId("android:id/button1", 1000))
    	        		{
    	        			CM.findObjectByRid("android:id/button1", 1000).click();
    	        			CM.hold(1000);
    	        		}
    	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
    	            	{
    	        			CM.scroll(ud, CM.DOWN);
    	            	}
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_11", 1000).click();
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_12", 1000).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 12:
    	        		if(CM.waitForId("android:id/button1", 1000))
    	        		{
    	        			CM.findObjectByRid("android:id/button1", 1000).click();
    	        			CM.hold(1000);
    	        		}
    	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
    	            	{
    	        			CM.scroll(ud, CM.DOWN);
    	            	}
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_12", 1000).click();
    	        		CM.hold(1000);
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_13", 1000).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 13:
    	        		if(CM.waitForId("android:id/button1", 1000))
    	        		{
    	        			CM.findObjectByRid("android:id/button1", 1000).click();
    	        			CM.hold(1000);
    	        		}
    	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
    	            	{
    	        			CM.scroll(ud, CM.DOWN);
    	            	}
    	        		CM.findObjectByRid("com.android.systemui:id/qstv_13", 1000).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 14://light adjust
//    	        		if(!CM.waitForId("com.android.systemui:id/seekbar", 1000))
//    	        			break;
    	        		CM.findObjectByRid("com.android.systemui:id/brightness_icon", 1000).click();
//    	        		r=CM.findObjectByRid("com.android.systemui:id/seekbar", 1000).getBounds();
//    	        		for(int i=0;i<10;i++)
//    	        		{
//    	        		    ud.drag(r.left+10, r.centerY(), r.right-10, r.centerY(), 5);
//    	        		    ud.drag(r.left-10, r.centerY(), r.right+10, r.centerY(), 5);
//    	        		}
    	        		
    	        		break;
    	        	case 15:
    	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
    	            	{
    	        			CM.scroll(ud, CM.DOWN);
    	            	}
    	        		CM.findObjectByRid("com.android.systemui:id/brightness_icon", 1000).click();
    	        		r=CM.findObjectByRid("com.android.systemui:id/slider", 1000).getBounds();
    	        		for(int i=0;i<10;i++)
    	        		{
//    	        		    ud.drag(r.left+10, r.centerY(), r.right-10, r.centerY(), 5);
//    	        		    ud.drag(r.left-10, r.centerY(), r.right+10, r.centerY(), 5);
    	        			ud.click(r.centerX()-20, r.centerY());
    	        			ud.click(r.centerX()+20, r.centerY());
    	        		}
    	        		break;
    	        	case 16://settings
    	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
    	            	{
    	        			CM.scroll(ud, CM.DOWN);
    	            	}
    	        		CM.findObjectByRid("com.android.systemui:id/oppo_settings_button", 1000).click();
    	        		CM.waitForNewApp("com.android.settings", 2000);
    	        		break;
    	        	case 17:
    	        		ud.pressBack();
    	        		ud.pressKeyCode(24);
    	        		CM.hold(300);
    	        		ud.pressKeyCode(24);
    	        		CM.hold(300);
    	        		ud.pressKeyCode(24);
    	        		CM.hold(3000);
    	        		break;
    	        	default:
    	        		break;    			
    	        	}
    	        }
    	    }
    	    public static class EmailDemo
    	    {
    	    	public static void test(UiDevice ud,int step) throws Exception
    	        {
    	        	switch(step)
    	        	{
    	        	case 1://enter email
    	        		CM.waitForId("com.android.email:id/bottom_btn_new", 8000);    		
    	        		break;
    	        	case 2://login
    	 //       		CM.waitForId("com.android.email:id/bottom_btn_new", 1000);    
//    	        		CM.enterText(ud, CM.findObjectByRid("com.android.email:id/account_email", 1000), "oppotext&@163.com");
//    	        		CM.enterText(ud, CM.findObjectByRid("com.android.email:id/account_password", 1000), "86076999");
//    	        		CM.findObjectByRid("com.android.email:id/btn_email_login", 1000).click();
//    	        		CM.hold(500);
    	        		break;
    	        	case 3://mail list
//    	        		if(!CM.waitForId("com.android.email:id/account_custom_view", 90000))
//    	        			return;
//    	        		CM.waitForId("com.android.email:id/bottom_btn_new", 1000); 
    	        		break;
    	        	case 4://new letter
    	        		CM.findObjectByRid("com.android.email:id/bottom_btn_new", 1000).click();//点击新建邮件
    	        		CM.waitForId("com.android.email:id/compose_send_btn", 2000);
    	        		break;
    	        	case 5://add contact
    	        		CM.findObjectByRid("com.android.email:id/add_to_address_list_btn", 1000).click();
    	        		CM.waitForId("android:id/button2", 2000);
    	        		break;
    	        	case 6://hand write
    	        		CM.findObjectByRid("com.android.email:id/contact_list_home", 1000).click();//点击返回
    	        		CM.waitForId("com.android.email:id/compose_send_btn", 2000);
    	        		CM.findObjectByRid("com.android.email:id/add_cc_address_list_btn", 1000).click();
    	        		CM.hold(1000);
    	        		break;
    	        	case 7:
    	        		
//    	        		CM.findObjectByRid("com.android.email:id/img_handwrite", 1000).click();
//    	        		CM.waitForId("com.android.email:id/lnr_bottom_bar", 2000);
    	        		break;
    	        	case 8://  choose attachment
    	        		CM.findObjectByRid("com.android.email:id/contact_list_home", 1000).click();//点击返回
    	        		CM.findObjectByRid("com.android.email:id/add_attachment_btn", 1000).click();
    	        		CM.waitForId("com.android.email:id/icon", 2000);
//    	        		CM.findObjectByRid("com.android.email:id/add_attachment", 1000).click();
//    	        		CM.waitForId("android:id/content", 2000);
    	        		break;
    	        	case 9://查看发送
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.email:id/compose_send_btn", 2000);
    	        		CM.findObjectByRid("com.android.email:id/compose_send_btn",1000).click();
    	        		CM.waitForId("com.android.email:id/dialog_button_ok", 2000);//等待有ok出现
//    	        		UiObject list=CM.findObjectByRid("oppo:id/select_dialog_listview", 1000);
//    	        		for(int i=0;i<list.getChildCount();i++)
//    	        		{
//    	        			list=CM.findObjectByRid("oppo:id/select_dialog_listview", 1000);
//    	        			list.getChild(new UiSelector().index(i).className("android.widget.LinearLayout")).click();
//    	        			CM.hold(2000);
//    	        			ud.takeScreenshot(new File(snapPath+"/8-"+i+".png"));
//    	        			ud.pressBack();
//    	        			CM.waitForId("com.android.email:id/add_attachment", 1000);
//    	        			CM.findObjectByRid("com.android.email:id/add_attachment", 1000).click();
//    	            		CM.waitForId("android:id/content", 2000);        			
//    	        		}
//    	        		ud.pressBack();
//    	        		CM.waitForId("android:id/up", 1000);
//    	        		CM.findObjectByRid("android:id/up", 1000).click();
    	        		break;
    	        	case 10://read letter
    	        		ud.pressBack();
    	        		CM.hold(2000);
    	        		ud.pressBack();
    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(2)).click();//点击进入邮件
    	        		CM.waitForId("com.android.email:id/conversation_delete", 1000);
//    	        		CM.waitForId("oppo:id/search_src_text", 2000);
//    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(2).className("android.view.View")).click();
//    	        		if(!CM.waitForId("android:id/up", 1000))
//    	        			CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(3).className("android.view.View")).click();
//    	        		CM.waitForId("android:id/up", 1000);
    	        		break;
    	        	case 11://发送添加   
    	        		CM.findObjectByRid("com.android.email:id/more", 1000).click();
    	        		CM.waitForId("com.android.email:id/bottom_more_image", 1000);
//    	        		CM.findObjectByRid("com.android.email:id/favorite", 1000).click();
//    	        		CM.waitForId("android:id/button2", 1000);
    	        		break;
    	        	case 12://replay
    	        		CM.findObjectByRid("com.android.email:id/more_options", 1000).getChild(new UiSelector().index(0)).click();
    	        		CM.waitForId("com.android.email:id/compose_toolsbar_media", 1000);
//    	        		ud.pressBack();
//    	        		CM.waitForId("android:id/up", 2000);
//    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
//    	        		CM.waitForId("oppo:id/select_dialog_listview", 2000);
    	        		break;
    	        	case 13://delete     Move to
    	        		ud.pressBack();
    	        		CM.hold(2000);
    	        		ud.pressBack();
    	        		CM.waitForId("com.android.email:id/more",1000);
    	        		CM.findObjectByRid("com.android.email:id/more", 1000).click();
    	        		CM.findObjectByRid("com.android.email:id/more_options", 2000).getChild(new UiSelector().index(3)).click();
    	        		CM.waitForId("com.android.email:id/name",1000);
//    	        		ud.pressBack();
//    	        		CM.waitForId("android:id/up", 2000);
//    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
//    	        		CM.waitForId("android:id/button2", 1000);
    	        		break;
    	        	case 14://folders       Quick replies
    	        		ud.pressBack();
    	        		CM.hold(2000);
    	        		CM.findObjectByRid("com.android.email:id/more", 1000).click();
    	        		CM.findObjectByRid("com.android.email:id/more_options", 2000).getChild(new UiSelector().index(6)).click();
    	        		CM.waitForId("com.android.email:id/name",1000);
//    	        		CM.findObjectByRid("android:id/button2", 1000).click();
//    	        		CM.waitForId("android:id/up", 1000);
//    	        		ud.pressBack();
//    	        		CM.waitForId("oppo:id/search_src_text", 2000);
//    	        		CM.findObjectByRid("com.android.email:id/img_account_entry", 1000).click();
//    	        		CM.hold(2000);
    	        		break;
    	        	case 15://more options   进入邮件设置  
    	        		ud.pressBack();
    	        		CM.hold(2000);
    	        		ud.pressBack();
    	        		CM.findObjectByRid("com.android.email:id/legacy_home", 1000).click();
    	        		CM.waitForId("com.android.email:id/icon_right", 1000);//判断设置按钮存在
    	        		
//    	        		ud.pressBack();
//    	        		CM.waitForId("oppo:id/search_src_text", 2000);
//    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
//    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
    	        		break;
    	        	case 16://sort options  查看邮件设置的另一半
    	        		CM.scroll(ud, CM.UP);
    	        		CM.hold(2000);
//    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
//    	        		CM.waitForId("oppo:id/select_dialog_listview", 2000);
    	        		break;
    	        	case 17://account list   
    	        		CM.findObjectByRid("com.android.email:id/btn_attachment", 1000).click();
    	        		CM.findObjectByRid("com.android.email:id/bottom_btn_att_mgr_order_sort", 1000).click();
    	        		CM.waitForId("com.android.email:id/dialog_button_cancel", 1000);
    	        		
//    	        		ud.pressBack();
//    	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 2000);
//    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
//    	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000);
//    	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
//    	        		CM.waitForId("android:id/list", 2000);
    	        		break;
    	        	case 18://account settings
    	        		ud.pressBack();
    	        		CM.hold(2000);
    	        		ud.pressBack();
    	        		CM.findObjectByRid("com.android.email:id/icon_right",2000).click();
    	        		CM.waitForId("com.android.email:id/account_name", 1000);
//    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(1).className("android.widget.LinearLayout")).click();
//    	        		CM.waitForId("android:id/checkbox", 2000);
    	        		break;
    	        	case 19://     进入账号设置
    	        		CM.findObjectByRid("com.android.email:id/account_name",1000).click();
    	        		CM.waitForId("com.android.email:id/account_description", 1000);
//    	        		CM.scroll(ud, CM.DOWN);
//    	        		CM.hold(1000);
    	        		break;
    	        	case 20://last part of setting 
    	        		CM.scroll(ud, CM.UP);
    	        		CM.hold(2000);
    	        		CM.scroll(ud, CM.UP);
    	        		CM.hold(2000);
//    	        		ud.pressBack();
//    	        		CM.waitForId("com.android.email:id/icon", 2000);
//    	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(0).className("android.widget.LinearLayout")).click();
//    	        		CM.waitForId("android:id/checkbox", 2000);
    	        		break;
    	        	case 21://delete account
    	        		CM.waitForId("com.android.email:id/account_delete_item", 1000);
    	        		CM.findObjectByRid("com.android.email:id/account_delete_item", 1000).click();
    	        		CM.waitForId("com.android.email:id/dialog_button_ok", 1000);
//    	        		ud.pressBack();
//    	        		CM.waitForId("com.android.email:id/icon", 2000);
//    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
//    	        		CM.waitForId("com.android.email:id/menu_multi_select", 2000);
//    	        		CM.findObjectByRid("com.android.email:id/check_box", 1000).click();
//    	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
//    	        		CM.waitForId("android:id/button1", 2000);
//    	        		CM.findObjectByRid("android:id/button1", 1000).click();
//    	        		CM.waitForId("com.android.email:id/btn_email_login", 2000);
    	 //       		ud.pressBack();
    	        		break;
    	        	default:
//    	        		CM.findObjectByRid("com.android.email:id/dialog_button_ok",1000).click();
//    	        		ud.pressBack();
    	        		break;    			
    	        	}
    	        }
    	    }
    }
    public static class Oppo_R7plusf
    /*
     * 9月24日更新第一次；
     */
    {

   	 public static class DialerDemo
   	    {
   	    	public static void test(UiDevice ud,int step) throws Exception
   	        {
   	        	switch(step)
   	        	{
   	        	case 1://enter dialer  拨号界面
   	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
   	        		break;
   	        	case 2://more options   更多选择界面
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		break;
   	        	case 3://call settings  通话设置界面  
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView3", 2000).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 4://speed dial   UPdate number
//   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{1,0}).click();
//   	        		CM.waitForId("com.android.dialer:id/number_key", 2000);
   	        		
   	        		break;
   	        	case 5://upgrading    升级界面
   	 //       		ud.pressBack();
   	        		CM.waitForId("android:id/list", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{1,0}).click();
   	        		CM.hold(5000);
   	        		CM.waitForId("android:id/button1", 5000);
//   	        		CM.findObjectByRid("android:id/button1",1000).click();
   	        		break;
   	        	case 6://notify on connect   
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	    	    	CM.waitForId("android:id/list", 2000);
   	    		    CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{3,0}).click();
   	    		    CM.waitForId("android:id/button2", 2000);
   	        		break;
   	        	case 7://quick reply
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("android:id/list", 2000);
   	                CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{4,0}).click();
   	            	CM.waitForId("oppo:id/MajorOperationButtonPositive", 2000);
   	        		break;
   	        	case 8://new quick replay
   	        		CM.waitForId("oppo:id/MajorOperationButtonPositive", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/button2", 2000);
   	        		break;
   	        	case 9://delete
   	        		CM.findObjectByRid("android:id/button2", 2000).click();
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 2000).click();
   	        		CM.waitForId("com.android.mms:id/action_select", 2000);
   	        		CM.findObjectByRid("com.android.mms:id/action_select", 2000).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 10://delete confirm
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/button2", 2000);
   	        		break;
   	        	case 11://statistics
   	        		CM.findObjectByRid("android:id/button2", 2000).click();
   	        		CM.hold(1000);
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		ud.pressBack();
   	        		CM.hold(2000);
//   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{7,0}).click();
//   	        		CM.hold(1500);
   	        		break;
   	        	case 12://rest of settings
//   	        		ud.pressBack();
//   	        		CM.hold(1000);
   	        		CM.scroll(ud, CM.UP);
   	        		CM.hold(1000);
   	        		break;
   	        	case 13://anti harassment
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView2", 2000).click();
   	        		CM.waitForId("oppo:id/emptybottle", 2000);
   	        		break;
   	        	case 14://anti harassment settings
   	        		CM.findObjectByRid("com.oppo.blacklist:id/pagertabstrip", 1000).getChild(new UiSelector().index(2)).click();
   	        		CM.hold(1500);
   	        		break;
   	        	case 15://dial
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
   	        		Rect r=CM.findObjectByRid("com.android.dialer:id/buttons", 1000).getBounds();
   	        		ud.click(r.left+r.width()/6, r.top+r.height()/8);
   	        		ud.click(r.left+r.width()/2, r.top+r.height()*7/8);
   	        		ud.click(r.left+r.width()/2, r.top+r.height()*7/8);
   	        		ud.click(r.left+r.width()/6, r.top+r.height()/8);
   	        		ud.click(r.left+r.width()/2, r.top+r.height()*7/8);
   	        		break;
   	        	case 16://add number to new contact
   	        		CM.findObjectByRid("com.android.dialer:id/add_new_contact", 1000).click();
   	        		CM.waitForNewApp("com.android.contacts", 2000);
//   	        		if(CM.waitForId("com.android.contacts:id/account_name", 1000))
//   	        			CM.findObjectByRid("com.android.contacts:id/account_name", 1000).click();
   	        		break;
   	        	case 17://add number to existed contact
   	        		Rect lr=CM.findObjectByRid("com.android.contacts:id/left_btn", 1000).getBounds();
   	        		ud.click(ud.getDisplayWidth()/2, lr.centerY());
   	        		CM.findObjectByRid("com.android.contacts:id/left_btn", 1000).click();
   	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
   	        		CM.findObjectByRid("com.android.dialer:id/add_to_contact", 1000).click();
   	        		CM.waitForNewApp("com.android.contacts", 2000);
   	        		break;
   	        	case 18://call out
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		CM.waitForNewApp("com.android.incallui", 1000);
   	        		break;
   	        	case 19://hang up
   	        		CM.hold(3000);
//   	        		CM.findObjectByRid("com.android.incallui:id/endButton", 1000).click();
   	        		if(CM.waitForId("com.android.dialer:id/dialpad", 500))
   	        			break;
   	        		else
   	        		    ud.click(ud.getDisplayWidth()/2, ud.getDisplayHeight()*92/100);
   	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
   	        		break;
   	        	case 20://calling record options
   	        		CM.findObjectByRid("com.android.dialer:id/calls_type_changge_indicate", 1000).click();
   	        		CM.waitForId("com.android.dialer:id/call_type_checkbox", 2000);
   	        		break;
   	        	case 21://record details
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
   	        		CM.findObjectByRid("com.android.dialer:id/secondary_action_icon", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 22://edit calling record
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000).click();
   	        		CM.waitForId("com.android.dialer:id/select_all_or_none", 2000);
   	        		CM.findObjectByRid("com.android.dialer:id/select_all_or_none", 1000).click();
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		break;
   	        	case 23://add record to contact        		
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
   	        		CM.waitForNewApp("android:id/alertTitle", 2000);
   	        		break;
   	        	case 24://send message to record
   	        		CM.findObjectByRid("oppo:id/select_dialog_listview", 1000).getChild(new UiSelector().index(0)).click();
   	        		CM.waitForId("com.android.contacts:id/left_btn", 2000);
   	        		CM.findObjectByRid("com.android.contacts:id/left_btn", 1000).click();
   	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000).click();
   	        		CM.waitForId("com.android.dialer:id/select_all_or_none", 2000);
   	        		CM.findObjectByRid("com.android.dialer:id/select_all_or_none", 1000).click();
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
   	        		CM.waitForNewApp("android:id/alertTitle", 2000);
   	        		CM.findObjectByRid("oppo:id/select_dialog_listview", 1000).getChild(new UiSelector().index(1)).click();
   	        		CM.waitForNewApp("android:id/up", 2000);
   	        		CM.findObjectByRid("android:id/up", 1000).click();
   	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);        		
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000).click();
   	        		CM.waitForId("com.android.dialer:id/select_all_or_none", 2000);
   	        		CM.findObjectByRid("com.android.dialer:id/select_all_or_none", 1000).click();
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		CM.waitForNewApp("com.android.mms", 2000);
   	        		break;
   	        	case 25://mute
   	        		ud.pressBack();
   	        		while(!CM.waitForId("com.android.dialer:id/dialpad", 1000))
   	        			ud.pressBack();
//   	        		CM.findObjectByRid("com.android.mms:id/oppo_contact_back_bt", 1000).click();
   	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
   	        		r=CM.findObjectByRid("com.android.dialer:id/buttons", 1000).getBounds();
   	        		ud.click(r.left+r.width()/6, r.top+r.height()/8);        		
   	        		ud.click(r.left+r.width()/2, r.top+r.height()*7/8);
   	        		ud.click(r.left+r.width()/2, r.top+r.height()*7/8);
   	        		ud.click(r.left+r.width()/6, r.top+r.height()/8);
   	        		ud.click(r.left+r.width()/2, r.top+r.height()*7/8);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		CM.waitForId("com.android.incallui:id/endButton", 2000);
   	        		CM.findObjectByRid("com.android.incallui:id/muteButton", 1000).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 26://speaker
   	        		CM.findObjectByRid("com.android.incallui:id/muteButton", 1000).click();
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("com.android.incallui:id/audioButton", 1000).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 27://dialpad
   	        		CM.findObjectByRid("com.android.incallui:id/audioButton", 1000).click();
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("com.android.incallui:id/dialpadButton", 1000).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 28://more actions
   	        		CM.findObjectByRid("com.android.incallui:id/dialpadButton", 1000).click();
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("com.android.incallui:id/morebutton", 1000).click();
   	        		CM.waitForId("com.android.incallui:id/oppo_moreButtons_layout", 2000);
   	        		break;
//   	        	case 29://record
//   	        		CM.findObjectByRid("com.android.incallui:id/oppo_moreButtons_layout", 1000).getChild(new UiSelector().index(0)).click();
//   	        		CM.hold(1000);
//   	        		break;
   	        	case 29://contacts           //xuxin1
   	        		CM.findObjectByRid("com.android.incallui:id/oppo_moreButtons_layout", 1000).getChild(new UiSelector().index(0)).click();
   	        		CM.hold(3000);
   	        		
//   	        		CM.findObjectByRid("com.android.incallui:id/oppo_moreButtons_layout", 1000).getChild(new UiSelector().index(1)).click();
//   	        		CM.waitForClass("android.widget.HorizontalScrollView", 2000);
   	        		break;
   	        	case 30://add call
   	        		ud.pressBack();
   	        		CM.findObjectByRid("com.android.incallui:id/oppo_moreButtons_layout", 1000).getChild(new UiSelector().index(1)).click();

 //  	        		CM.waitForId("oppo:id/MajorOperationButtonNegative", 2000);
   	        		CM.hold(3000);
   	        		
   	        		break;
   	        	case 32://notes
   	        		ud.pressBack();
   	        		CM.findObjectByRid("com.android.incallui:id/oppo_moreButtons_layout", 1000).getChild(new UiSelector().index(2)).click();
   	        		CM.waitForId("com.google.android.gms:id/title", 2000);
   	        		break;
   	        	case 33://delete record
   	        		ud.pressBack();
   	        		if(!CM.waitForId("com.android.incallui:id/dialpadButton", 1000))
   	        			ud.pressBack();
   	        		CM.waitForId("com.android.incallui:id/dialpadButton", 1000);
   	        		CM.findObjectByRid("com.android.incallui:id/endButton", 1000).click();
   	        		CM.startActivity("com.android.dialer", "com.android.dialer.DialtactsActivity");
   	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000).click();
   	        		CM.waitForId("com.android.dialer:id/select_all_or_none", 2000);
   	        		CM.findObjectByRid("com.android.dialer:id/select_all_or_none", 1000).click();
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView3", 2000).click();
   	        		break;
   	        	case 34://over
//   	        		if(CM.waitForId("android:id/button1", 1000))
//   	        		    CM.findObjectByRid("android:id/button1", 1000).click();
//   	        		else if(CM.waitForNewApp("com.android.contacts", 1000))
//   	        		{
//   	    	    		CM.findObjectByRid("com.android.contacts:id/left_btn", 1000).click();
//   	    	    		CM.waitForId("com.android.dialer:id/dialpad", 2000);
//   	    	    		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
//   	    	    		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
//   	    	    		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000).click();
//   	    	    		CM.waitForId("com.android.dialer:id/select_all_or_none", 2000);
//   	    	    		CM.findObjectByRid("com.android.dialer:id/select_all_or_none", 1000).click();
//   	    	    		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
//   	    	    		CM.waitForId("android:id/button1", 2000);
//   	    	    		CM.findObjectByRid("android:id/button1", 1000).click();
//   	        		}
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   		    		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   		    		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000).click();
   		    		CM.waitForId("com.android.dialer:id/select_all_or_none", 2000);
   		    		CM.findObjectByRid("com.android.dialer:id/select_all_or_none", 1000).click();
   		    		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   		    		CM.waitForId("android:id/button1", 2000);
   		    		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.waitForId("com.android.dialer:id/dialpad", 2000);
   	        		ud.pressBack();
   	        		break;
   	        	default:
   	        		break;
   	        	}
   	        }
   	    }
   	    public static class ContactDemo
   	    {
   	    	public static void test(UiDevice ud,int step) throws Exception
   	        {
   	        	switch(step)
   	        	{
   	        	case 1://enter contacts    		
   	        		break;
   	        	case 2://create new contact
   	        		CM.findObjectByRid("com.android.contacts:id/create_contact_button", 2000).click();
   	        		CM.hold(3000);    		
   	        		break;
   	        	case 3://choose SIM or native
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("com.android.contacts:id/location_changge_indicate", 1000).click();
   	        		CM.hold(1500);    		
   	        		break;
   	        	case 4://choose SIM and add group
   	        		CM.findObjectByClassname("android.widget.ListView", 1000).getChild(new UiSelector().className("android.widget.LinearLayout").index(0)).click();
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("com.android.contacts:id/edit_group_view", 1000).click();
   	        		CM.hold(1500);    		
   	        		break;
   	        	case 5://edit group name
   	        		ud.pressBack();
//   	        		CM.findObjectByRid("com.android.contacts:id/oppo_group_select_dlg_list", 1000).getChild(new UiSelector().index(1)).click();
  	        		CM.hold(1000);
   	        		CM.findObjectByRid("com.android.contacts:id/group_name", 1000).click();
   	        		CM.hold(1000);
   	        		if(CM.waitForId("android:id/button2", 1000))
   	        		CM.setText(ud, CM.findObjectByRid("android:id/alertTitle", 1000), "mygroup");
   	        		CM.hold(1500);    		
   	        		break;
   	        	case 6://edit new contact interface after added group
   	        		if(CM.waitForId("android:id/button1", 1000))
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.hold(1500);    		
   	        		break;
   	        	case 7://choose group after adding group
//   	        		ud.pressBack();
//   	        		CM.hold(1000);
//   	        		CM.findObjectByRid("com.android.contacts:id/edit_group_view", 1000).click();
//   	        		CM.hold(1500);    		
//   	        		ud.pressBack();
//   	        		CM.hold(1000);
   	        		break;
   	        	case 8://choose native
   	        		CM.findObjectByRid("com.android.contacts:id/location_changge_indicate", 1000).click();
   	        		CM.waitForId("com.android.contacts:id/accountItem", 2000);
   	        		CM.findObjectByClassname("android.widget.ListView", 1000).getChild(new UiSelector().className("android.widget.LinearLayout").index(CM.findObjectByClassname("android.widget.ListView", 1000).getChildCount()-1)).click();
   	        		CM.hold(1500);    		
   	        		break;
   	        	case 9://add icon
//   	        		CM.findObjectByRid("com.android.contacts:id/stub_photo", 1000).click();
//   	        		UiObject choice=CM.findObjectByRid("oppo:id/select_dialog_listview", 1000);    		
//   	        		choice.getChild(new UiSelector().index(0)).click();
//   	        		CM.waitForId("android:id/button_always", 2000);
//   	        		ud.pressBack();
//   	        		CM.hold(1000);
//   	        		CM.findObjectByRid("com.android.contacts:id/stub_photo", 1000).click();
//   	        		choice.getChild(new UiSelector().index(1)).click();
//   	        		ud.pressBack();
//   	        		CM.hold(1000);
   	        		CM.findObjectByRid("com.android.contacts:id/frame", 1000).click();
   	        		CM.waitForId("android:id/alertTitle", 2000);
   	        		break;
   	        	case 10://choose phone type
   	        		ud.pressBack();
   	        		CM.hold(2000);
   	        		UiObject sect=CM.findObjectByRid("com.android.contacts:id/sect_fields", 1000);
   	        		CM.findChildByPosistion(sect, new int[]{0,0,0,0,0,0}).click();
   	        		CM.hold(1500);    		
   	        		break;
   	        	case 11://choose email type
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		sect=CM.findObjectByRid("com.android.contacts:id/sect_fields", 1000);
   	        		CM.findChildByPosistion(sect, new int[]{1,0,0,0,0,0}).click();
   	        		CM.hold(1500);    		
   	        		break;
   	        	case 12://add all more fields
   	        		
   	        		ud.pressBack();
   	        		if(!CM.waitForId("com.android.contacts:id/button_add_field", 3000))
   	        			ud.pressBack();
   	        		CM.hold(1000);
   	        		int i=0;
   	        		while(CM.waitForId("com.android.contacts:id/button_add_field", 3000))
   	        		{
   	        			CM.findObjectByRid("com.android.contacts:id/button_add_field", 2000).click();
   	        			if(CM.waitForId("oppo:id/select_dialog_listview", 2000))
   	        			{
   	        				i++;
   	        				CM.findObjectByRid("oppo:id/select_dialog_listview", 1000).getChild(new UiSelector().index(0)).click();
   	        				if(CM.waitForId("oppo:id/datePicker_btn", 1000))
   	        					CM.findObjectByRid("android:id/button1", 2000).click();
   	        				if(CM.waitForId("com.android.contacts:id/edit_title", 3000))
   	        					CM.scroll(ud, CM.UP);
   	        			}
   	        		}
   	        		CM.hold(1500);    		
   	        		break;
   	        	case 13://AIM list
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		CM.scroll(ud, CM.DOWN);
   	        		CM.scroll(ud, CM.DOWN);
   	        		CM.hold(1000);
   	        		sect=CM.findObjectByRid("com.android.contacts:id/sect_fields", 1000);
   	        		CM.findChildByPosistion(sect, new int[]{2,0,0,0,0,0}).click();
   	        		CM.hold(1500);    		
   	        		break;
   	        	case 14://AIM list lower part
   	        		CM.dragObj(ud, CM.findObjectByRid("oppo:id/select_dialog_listview", 1000), CM.UP, 5);
   	        		CM.hold(1000);
   	        		CM.hold(1500);    		
   	        		break;
   	        	case 15://choose custom aim
   	        		CM.findObjectByRid("oppo:id/select_dialog_listview", 1000).getChild(new UiSelector().index(CM.findObjectByRid("oppo:id/select_dialog_listview", 1000).getChildCount()-1)).click();
   	        		CM.hold(1500);    		
   	        		break;
   	        	case 16://added custom aim
   	        		if(CM.waitForId("android:id/button1", 2000))
   	        		{
   	    	    		ud.pressBack();
   	    	    		CM.hold(1000);
   	    	    		CM.setText(ud, CM.findObjectByRid("com.android.contacts:id/custom_dialog_content", 1000), "myaim");
   	    	    		CM.findObjectByRid("android:id/button1", 1000).click();
   	    	    		CM.hold(1500);
   	    	    		ud.pressBack();
   	    	    		CM.hold(1000);
   	        		}    		    		
   	        		break;
   	        	case 17://address type list    		
   	        		sect=CM.findObjectByRid("com.android.contacts:id/sect_fields", 1000);
   	        		CM.findChildByPosistion(sect, new int[]{4,0,0,0,0,0}).click();
   	        		CM.hold(1500);    		
   	        		break;
   	        	case 18://edit custom address
   	        		CM.findObjectByRid("oppo:id/select_dialog_listview", 1000).getChild(new UiSelector().index(2)).click();
   	        		CM.hold(1500);    		
   	        		break;
   	        	case 19://added custom adress
   	        		if(CM.waitForId("android:id/button1", 2000))
   	        		{
   	    	    		ud.pressBack();
   	    	    		CM.hold(1000);
   	    	    		CM.setText(ud, CM.findObjectByRid("com.android.contacts:id/custom_dialog_content", 1000), "myaddress");
   	    	    		CM.findObjectByRid("android:id/button1", 1000).click();
   	    	    		CM.hold(1500);
   	    	    		ud.pressBack();
   	    	    		CM.hold(1500);
   	        		}    		    		
   	        		break;
   	        	case 20://relation list    
   	        		CM.scroll(ud, CM.UP);
   	        		CM.scroll(ud, CM.UP);
   	        		CM.hold(1000);
   	        		sect=CM.findObjectByRid("com.android.contacts:id/sect_fields", 1000);
   	        		CM.findChildByPosistion(sect, new int[]{3,0,0,0,0,0}).click();
   	        		CM.hold(1500);    		
   	        		break;
   	        	case 21://relation list lower part
   	        		CM.dragObj(ud, CM.findObjectByRid("oppo:id/select_dialog_listview", 1000), CM.UP, 5);
   	        		CM.dragObj(ud, CM.findObjectByRid("oppo:id/select_dialog_listview", 1000), CM.UP, 5);
   	        		CM.hold(1000);    		    		
   	        		break;
   	        	case 22://custom relation
   	        		CM.findObjectByRid("oppo:id/select_dialog_listview", 1000).getChild(new UiSelector().index(CM.findObjectByRid("oppo:id/select_dialog_listview", 1000).getChildCount()-1)).click();
   	        		CM.hold(1500);    		
   	        		break;
   	        	case 23://add custom relation
   	        		if(CM.waitForId("android:id/button1", 2000))
   	        		{
   	    	    		ud.pressBack();
   	    	    		CM.hold(1500);
   	    	    		CM.setText(ud, CM.findObjectByRid("com.android.contacts:id/custom_dialog_content", 1000), "myrelation");
   	    	    		CM.findObjectByRid("android:id/button1", 1000).click();
   	    	    		CM.hold(1500);    		
   	    	    		ud.pressBack();
   	    	    		CM.hold(1500);
   	        		}    		
   	        		break;
   	        	case 24://edit all info of contact and save
   	        		CM.scroll(ud, CM.DOWN);
   	        		CM.scroll(ud, CM.DOWN);
   	        		CM.hold(1000);
   	        		sect=CM.findObjectByRid("com.android.contacts:id/sect_fields", 1000);
   	        		try
   	        		{
   	        		CM.setText(ud, CM.findChildByPosistion(sect, new int[]{0,0,0,0,0,2,0,0}), "12345678901");
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		CM.setText(ud, CM.findChildByPosistion(sect, new int[]{1,0,0,0,0,2,0,0}), "mytestemailcom");
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		CM.setText(ud, CM.findChildByPosistion(sect, new int[]{2,0,0,0,0,2,0,0}), "myaimcount");
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		CM.setText(ud, CM.findChildByPosistion(sect, new int[]{4,0,0,0,0,2,0,0}), "myaddress");
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		CM.setText(ud, CM.findChildByPosistion(sect, new int[]{5,0,0,0,0,0,0,0}), "mynotes");
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		CM.setText(ud, CM.findChildByPosistion(sect, new int[]{6,0,0,0,0,0,0,0}), "mynickname");
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		CM.setText(ud, CM.findChildByPosistion(sect, new int[]{7,0,0,0,0,0,0,0}), "mywebsite");
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		CM.setText(ud, CM.findChildByPosistion(sect, new int[]{3,0,0,0,0,2,0,0}), "myrelationship");
   	        		}catch(Exception ex)
   	        		{
   	        			
   	        		}
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		CM.scroll(ud, CM.DOWN);
   	        		CM.scroll(ud, CM.DOWN);
   	        		CM.scroll(ud, CM.DOWN);
   	        		CM.scroll(ud, CM.DOWN);
   	        		CM.hold(1000);
//   	        		CM.setText(ud, CM.findChildByPosistion(sect, new int[]{0,0,0,0,0,0}), "mycompany");
//   	        		CM.setText(ud, CM.findChildByPosistion(sect, new int[]{0,0,0,0,0,1}), "mytitle");
   	        		UiObject nameEdit=CM.findObjectByRid("com.android.contacts:id/edit_name_fields", 1000);
   	        		CM.setText(ud, CM.findChildByPosistion(nameEdit, new int[]{2,0,0,0,0,0}), "mycompany");
   	        		CM.setText(ud, CM.findChildByPosistion(nameEdit, new int[]{2,0,0,0,0,1}), "mytitle");
//   	        		CM.findChildByPosistion(nameEdit, new int[]{0,0,1,0}).click();
//   	        		CM.findChildByPosistion(nameEdit, new int[]{1,0,1,0}).click();
   	        		CM.hold(1000);
//   	        		try
//   	        		{
//   	        		CM.setText(ud, CM.findChildByPosistion(nameEdit, new int[]{0,0,0,0}), "myprefix");
//   	        		ud.pressBack();
//   	        		CM.hold(1000);
//   	        		CM.setText(ud, CM.findChildByPosistion(nameEdit, new int[]{0,0,0,1}), "mygiven");
//   	        		ud.pressBack();
//   	        		CM.hold(1000);
//   	        		CM.setText(ud, CM.findChildByPosistion(nameEdit, new int[]{0,0,0,2}), "mymiddle");
//   	        		ud.pressBack();
//   	        		CM.hold(1000);
//   	        		CM.setText(ud, CM.findChildByPosistion(nameEdit, new int[]{0,0,0,3}), "myfamilly");
//   	        		ud.pressBack();
//   	        		CM.hold(1000);
//   	        		CM.setText(ud, CM.findChildByPosistion(nameEdit, new int[]{0,0,0,4}), "mysuffix");
//   	        		ud.pressBack();
//   	        		CM.hold(1000);
//   	        		CM.setText(ud, CM.findChildByPosistion(nameEdit, new int[]{1,0,0,0}), "myphoneticfamily");
//   	        		ud.pressBack();
//   	        		CM.hold(1000);
//   	        		CM.setText(ud, CM.findChildByPosistion(nameEdit, new int[]{1,0,0,1}), "myphoneticmiddle");
//   	        		ud.pressBack();
//   	        		CM.hold(1000);
//   	        		CM.setText(ud, CM.findChildByPosistion(nameEdit, new int[]{1,0,0,2}), "myphoneticgiven");
//   	        		ud.pressBack();
//   	        		CM.hold(1000);
//   	        		}catch(Exception ex)
//   	        		{
//   	        			
//   	        		}
//   	        		CM.findObjectByRid("com.android.contacts:id/right_btn", 1000).click();
   	        		CM.hold(1500);    		
   	        		break;
   	        	case 25://send contact card
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		ud.pressBack();
   	        		if(CM.waitForId("android:id/button1", 1000))
   	        		    CM.findObjectByRid("android:id/button1", 1000).click();
   	        		if(CM.waitForId("oppo:id/MajorOperationButtonMore", 1000))
   	        		    CM.findObjectByRid("oppo:id/MajorOperationButtonNeutral", 1000).click();
   	        		else
   	        			CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		CM.hold(1500);    		
   	        		break;
   	        	case 26://delete contact
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		if(CM.waitForId("oppo:id/MajorOperationButtonMore", 1000))
   	        		    CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		else
   	        			CM.findObjectByRid("oppo:id/MajorOperationButtonNeutral", 1000).click();
   	        		CM.hold(1500);    		
   	        		break;
   	        	case 27://contact list
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		ud.pressBack();
   	        		CM.hold(1000);    		
   	        		break;
   	        	case 28://choose contacts to display
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 2000).click();
   	        		CM.waitForId("com.android.contacts:id/radioBtn", 2000);    		
   	        		break;
   	        	case 29://import and export
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 2000).click();
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView2", 2000).click();
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		CM.hold(1000);
   	        		break;
   	        	case 30://contacts setting
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 2000).click();
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView3", 2000).click();
   	        		UiObject list=CM.findObjectByRid("android:id/list", 2000);
   	        		for(i=0;i<4;i++)
   	        		{
   	        			list.getChild(new UiSelector().index(i)).click();
   	        			CM.hold(1000);
   	        			ud.pressBack();
   	        			CM.hold(1000);
   	        		}
   	        		break;
   	        	case 31://group list with added contacts
   	        		ud.pressBack();
   	        		CM.hold(500);
   	        		UiObject tabs=CM.findObjectByRid("android:id/action_bar_container", 1000);
   	        		CM.findChildByPosistion(tabs, new int[]{1,0,0}).click();    		
   	        		break;
   	        	case 32://group by time
//   	        		CM.findObjectByRid("com.android.contacts:id/icon", 1000).click();
//   	        		CM.hold(500);    		
//   	        		ud.pressBack();
   	        		break;
   	        	case 33://add group
   	        		CM.hold(1000);
//   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
//   	        		CM.hold(1000);   
//   	        		CM.findObjectByRid("com.android.contacts:id/group_name", 1000).click();
//	        		CM.hold(1000);
//	        		if(CM.waitForId("android:id/button2", 1000))
//	        		CM.setText(ud, CM.findObjectByRid("android:id/alertTitle", 1000), "mygroup");
//   	        		CM.findObjectByRid("android:id/button1", 1000).click();
//   	        		CM.findObjectByRid("com.android.contacts:id/menu_mark", 1000).click();
//   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();//增加group
   	        		CM.waitForId("com.android.contacts:id/icon", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 2000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
   	        		CM.waitForId("com.android.contacts:id/menu_mark", 1000);
   	        		CM.findObjectByRid("com.android.contacts:id/menu_mark", 1000).click();
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		CM.waitForId("android:id/button1", 1000);
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.waitForId("android:id/action_bar_container", 1000);
   	        		break;
   	        	case 34://favorite list
   	        		tabs=CM.findObjectByRid("android:id/action_bar_container", 1000);
   	        		CM.findChildByPosistion(tabs, new int[]{1,0,2}).click();
   	        		CM.waitForId("oppo:id/MajorOperationButtonNegative", 1000);    		
   	        		break;
   	        	case 35://add favorite
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		CM.waitForId("com.android.contacts:id/photo", 1000);    		
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		break;
   	        	case 36://edit contact
   	        		tabs=CM.findObjectByRid("android:id/action_bar_container", 1000);
   	        		CM.findChildByPosistion(tabs, new int[]{1,0,1}).click();
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 2000).click();
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000).click();
   	        		CM.findObjectByRid("com.android.contacts:id/check", 2000).click();
   	        		UiObject bars=CM.findObjectByRid("oppo:id/MajorOperationsGroup", 2000);
   	        		for(i=2;i<3;i++)
   	        		{
   	        			bars.getChild(new UiSelector().index(i)).click();
   	        			CM.hold(500);
   	        			ud.pressBack();
   	        			CM.hold(500);
   	        		}	
   	        		bars.getChild(new UiSelector().index(0)).click();
   	        		CM.hold(500);
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.waitForId("com.android.contacts:id/create_contact_button", 2000);
   	        		break;
   	        	case 37://import from storage
   	        		CM.findObjectByRid("com.android.contacts:id/import_contacts_button", 1000).click();
   	        		CM.waitForId("com.android.contacts:id/menu_mark", 1000);    		
   	        		break;
   	        	case 38://nearme account
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.contacts:id/create_contact_button", 2000);
   	        		CM.findObjectByRid("com.android.contacts:id/sync_nearMe_account", 1000).click();
   	        		CM.waitForId("com.android.contacts:id/netdisk_button", 2000);    		
   	        		break;
   	        	case 39://sync
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.contacts:id/create_contact_button", 2000);
   	        		CM.findObjectByRid("com.android.contacts:id/add_account_button", 2000).click();
   	        		CM.waitForId("android:id/list", 2000);    		
   	        		break;
   	        	case 40:
   	        		int listLen=CM.findObjectByRid("android:id/list", 1000).getChildCount();
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.contacts:id/import_contacts_button", 2000);
   	        		for(i=0;i<listLen;i++)
   	        		{
   	        			CM.findObjectByRid("com.android.contacts:id/add_account_button", 2000).click();
   	        			CM.waitForId("android:id/list", 5000);
   	        			CM.findObjectByRid("android:id/list", 2000).getChild(new UiSelector().index(i)).getChild(new UiSelector().index(1)).click();
   	        			CM.hold(2000);
   	        			ud.pressBack();
   	        			CM.hold(2000);
   	        			if(!CM.waitForId("com.android.contacts:id/import_contacts_button", 2000))
   	        			{
   	        				ud.pressBack();
   	            			CM.hold(2000);
   	        			}
   	        		}
   	        		CM.scroll(ud, CM.LEFT);
   	        		CM.hold(1000);
   	        		CM.scroll(ud, CM.RIGHT);
   	        		CM.hold(1000);
   	        		break;
   	        	default:
   	        		break;
   	        	}
   	        }
   	    }
   	    public static class MessageDemo
   	    {
   	    	public static void test(UiDevice ud,int step) throws Exception
   	        {
   	        	switch(step)
   	        	{
   	        	case 1://enter message
//   	        		if(CM.waitForId("com.android.mms:id/empty", 1000))
//   	        			break;
//   	        		else
//   	        		{
//   	        			CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
//   	        			CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
//   	        			CM.findObjectByRid("com.android.contacts:id/menu_mark", 1000).click();
//   	        			CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
//   	        			CM.findObjectByRid("android:id/button1", 1000).click();
//   	        			CM.waitForId("com.android.mms:id/empty", 1000);
//   	        		}
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarItemTitle", 1000);
   	        		break;
   	        	case 2://new message
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarItemTitle", 1000).click();
   	        		CM.waitForId("com.android.mms:id/send_button", 2000);
   	        		break;
   	        	case 3://add contact
   	        		CM.findObjectByRid("com.android.mms:id/oppo_contact_bt", 1000).click();
   	        		CM.waitForNewApp("com.android.contacts", 2000);
   	        		break;
   	        	case 4://attachments
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.mms:id/send_button", 2000);
   	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
   	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
   	        		break;
   	        	case 5://emotions
//   	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(0).className("android.widget.LinearLayout")).click();
//   	        		CM.waitForId("com.android.mms:id/face_view_manager", 2000);
   	        		break;
   	        	case 6://choose pictures
//   	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
//   	        		CM.hold(1000);
//   	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
//   	        		CM.waitForClass("android.widget.GridView", 1000);
//   	        		ud.pressBack();
   	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(0).className("android.widget.LinearLayout")).click();        		
   	        		CM.waitForId("android:id/button1", 1000);
   	        		break;
   	        	case 7://
   	        		if(CM.waitForId("android:id/button1", 1000))
   	        		    CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.waitForId("com.android.mms:id/send_button", 2000);
   	        		break;
   	        	case 8:
//   	        		ud.pressBack();
//   	        		CM.waitForId("com.android.mms:id/add_attach", 1000);
//   	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
//   	        		CM.waitForClass("android.widget.GridView", 1000);
//   	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(1).className("android.widget.LinearLayout")).click();
//   	        		CM.waitForId("android:id/button1", 2000);
//   	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(1).className("android.widget.LinearLayout")).click();
//   	        		CM.waitForId("com.google.android.apps.plus:id/tiles", 2000);
   	        		break;
   	        	case 9://capture new picture
//   	        		ud.pressBack();
   	 //       		CM.waitForId("com.android.mms:id/add_attach", 2000);
//   	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
//   	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
//   	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(2).className("android.widget.LinearLayout")).click();
//   	        		CM.waitForNewApp("com.oppo.camera", 2000);
//   	        		CM.hold(2000);
//   	        		while(CM.waitForId("com.oppo.camera:id/guide_view", 1000))
//   	        		{
//   	        		    CM.scroll(ud, CM.UP);
//   	        		}
//   	        		CM.findObjectByRid("com.oppo.camera:id/shutter_button", 3000).click();
//   	        		CM.waitForId("com.oppo.camera:id/btn_done", 1000);
//   	        		CM.findObjectByRid("com.oppo.camera:id/btn_done", 1000).click();
//   	        		CM.waitForId("com.android.mms:id/add_attach", 2000);
   	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(1).className("android.widget.LinearLayout")).click();
   	        		CM.findObjectByRid("com.oppo.camera:id/shutter_button", 2000).click();
   	        		CM.hold(3000);
   	        		CM.findObjectByRid("com.oppo.camera:id/btn_done", 2000).click();
   	        		CM.hold(3000);
   	        		break;
   	        	case 10://insert name and number
   	        		CM.findObjectByRid("com.android.mms:id/remove_image_button", 1000).click();
   	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
   	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
   	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(2).className("android.widget.LinearLayout")).click();
   	        		CM.waitForNewApp("com.android.contacts", 2000);
   	        		break;
   	        	case 11://timing messages
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.mms:id/add_attach", 2000);
   	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
   	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
   	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(3).className("android.widget.LinearLayout")).click();
   	        		CM.waitForId("android:id/button1", 2000);
   	        		break;
   	        	case 12://insert sound
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.mms:id/add_attach", 2000);
   	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
   	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
   	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(4).className("android.widget.LinearLayout")).click();
   	        		CM.waitForNewApp("com.oppo.music", 2000);
   	        		break;
   	        	case 13://insert recording
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.mms:id/add_attach", 2000);
   	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
   	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
   	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(5).className("android.widget.LinearLayout")).click();
   	        		CM.waitForNewApp("oppo.multimedia.soundrecorder", 2000);
   	        		break;
   	        	case 14://insert 影片
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.mms:id/add_attach", 2000);
   	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
   	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
   	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(6).className("android.widget.LinearLayout")).click();
   	        		CM.waitForId("android:id/button1",1000);
   	        		break;
   	        	case 15://插入录像
   	        		if(CM.waitForId("android:id/button1", 1000))
   	        			CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
   	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(7).className("android.widget.LinearLayout")).click();
   	        		CM.waitForId("com.oppo.camera:id/shutter_button", 2000);
   	        		break;
   	        	case 16://
//   	        		ud.pressBack();
//   	        		CM.waitForId("com.android.mms:id/add_attach", 2000);
//   	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
//   	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
//   	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(7).className("android.widget.LinearLayout")).click();
//   	        		CM.waitForId("android:id/button1", 2000);
//   	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(0).className("android.widget.LinearLayout")).click();
//   	        		CM.waitForNewApp("com.google.android.apps.plus", 1000);
   	        		break;
   	        	case 17://insert recording videos
//   	        		ud.pressBack();
   	 //       		CM.waitForId("com.android.mms:id/add_attach", 2000);
//   	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
//   	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
//   	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(8).className("android.widget.LinearLayout")).click();
//   	        		CM.waitForNewApp("com.oppo.camera", 2000);
   	        		break;
   	        	case 18://insert subjects
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.mms:id/add_attach", 2000);
   	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
   	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
   	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(8).className("android.widget.LinearLayout")).click();
   	        		CM.waitForId("com.android.mms:id/subject", 2000);
//   	        		ud.drag(ud.getDisplayWidth()-50, ud.getDisplayHeight()*3/4, 50, ud.getDisplayHeight()*3/4, 5);
//   	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(0).className("android.widget.LinearLayout")).click();        		
//   	        		CM.waitForId("com.android.mms:id/subject", 2000);
   	        		break;
   	        	case 19://insert slides
   	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
   	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
	        		ud.drag(ud.getDisplayWidth()-50, ud.getDisplayHeight()*3/4, 50, ud.getDisplayHeight()*3/4, 5);
   	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(0).className("android.widget.LinearLayout")).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 20:
   	        		ud.pressMenu();
   	        		CM.hold(1000);
   	        		break;
   	        	case 21:
   	        		ud.pressMenu();
   	        		CM.waitForId("android:id/up", 1000);
   	        		Rect r=CM.findObjectByClassname("android.widget.ListView", 1000).getChild(new UiSelector().index(0).className("android.widget.LinearLayout")).getBounds();
   	        		ud.click(r.centerX(), r.centerY()+r.height());
   	        		CM.waitForId("android:id/action_bar_overlay_layout", 1000);
   	        		break;
   	        	case 22:
   	        		ud.pressMenu();
   	        		CM.hold(1000);
   	        		break;
   	        	case 23:
   	        		CM.findObjectByMutiAttribut("", "android.widget.LinearLayout", "", 8, 1000).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 24://insert contacts
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.mms:id/add_attach", 2000);
   	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
   	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
   	        		CM.findObjectByClassname("android.widget.GridView", 1000).getChild(new UiSelector().index(2).className("android.widget.LinearLayout")).click();
   	        		CM.waitForNewApp("com.android.contacts", 2000);
   	        		break;
   	        	case 25://diy emotions
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.mms:id/add_attach", 2000);
   	        		CM.findObjectByRid("com.android.mms:id/add_attach", 1000).click();
   	        		CM.waitForId("com.android.mms:id/iv_icon", 2000);
   	        		ud.drag(50, ud.getDisplayHeight()*3/4, ud.getDisplayWidth()-50, ud.getDisplayHeight()*3/4, 5);
   	        		ud.drag(50, ud.getDisplayHeight()*3/4, ud.getDisplayWidth()-50, ud.getDisplayHeight()*3/4, 5);
   	        		CM.hold(1500);
   	        		break;
   	        	case 26://send message
//   	        		ud.pressBack();
   	        		CM.waitForId("com.android.mms:id/add_attach", 2000);
   	        		CM.setText(ud, CM.findObjectByRid("com.android.mms:id/recipients_editor", 1000), "10086");
   	        		CM.setText(ud, CM.findObjectByRid("com.android.mms:id/embedded_text_editor", 1000), "mss");
   	        		CM.findObjectByRid("com.android.mms:id/send_button", 1000).click();
   	        		CM.waitForId("com.android.mms:id/msg_list_item", 2000);
   	        		break;
   	        	case 27://save number
   	        		if(CM.waitForId("android:id/sms_short_code_confirm_message", 1000))
   	        		{
   	        			CM.findObjectByRid("android:id/sms_short_code_remember_choice_checkbox", 1000).click();
   	        			CM.findObjectByRid("android:id/button1", 1000).click();
   	        		}
   	        		break;
   	        	case 28://edit message
   	        		CM.findObjectByRid("com.android.mms:id/msg_list_item", 1000).longClick();
   	        		CM.waitForId("com.android.mms:id/checkbox", 2000);
   	        		break;
   	        	case 29://delete
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);
   	        		break;
   	        	case 30://forward
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("com.android.mms:id/checkbox", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		CM.waitForId("com.android.mms:id/oppo_back_bt", 2000);
   	        		break;
   	        	case 31://cancel edit
   	        		CM.findObjectByRid("com.android.mms:id/oppo_back_bt", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);
   	        		break;
   	        	case 32://save draft
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.setText(ud, CM.findObjectByRid("com.android.mms:id/recipients_editor", 1000), "10010");
   	        		CM.findObjectByRid("com.android.mms:id/oppo_back_bt", 1000).click();
   	        		CM.waitForId("com.android.mms:id/contacts_actions", 2000);
   	        		ud.pressBack();
   	        		if(!CM.waitForId("com.android.mms:id/oppolistview", 2000))
   	        		{
   	        			ud.pressBack();
   	        			CM.waitForId("com.android.mms:id/oppolistview", 2000);
   	        		}
   	        		break;
   	        	case 33://more options
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		break;
   	        	case 34://block
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
   	        		CM.waitForNewApp("com.oppo.blacklist", 2000);
   	        		break;
   	        	case 35://message settings
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.mms:id/oppolistview", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView2", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 36://last part of settings
   	        		CM.scroll(ud, CM.UP);
   	        		CM.hold(1000);
   	        		break;
   	        	case 37://edit message list
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.mms:id/oppolistview", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
   	        		CM.waitForId("com.android.mms:id/action_select", 2000);
   	        		break;
   	        	case 38://delete
   	        		CM.findObjectByRid("com.android.mms:id/action_select", 1000).click();
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);
   	        		break;
   	        	case 39://over
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.waitForId("com.android.mms:id/empty", 2000);
   	        		ud.pressBack();
   	        		break;
   	        	default:
   	        		break;
   	        	}
   	        }
   	    }
   	    public static class CalendarDemo
   	    {
   	    	public static void test(UiDevice ud,int step)throws Exception
   	        {
   	        	switch(step)
   	        	{
   	        	case 1://month view
   	        		CM.hold(1000); 
   	        		break;
   	        	case 2://year view
   	        		ud.click(ud.getDisplayWidth()/2, ud.getDisplayHeight()/2);
   	        		CM.hold(2000); 
   	        		CM.startActivity("com.android.calendar", "com.android.calendar.AllInOneActivity");
   	        		CM.hold(2000);
   	        		CM.findObjectByRid("com.android.calendar:id/year_btn", 1000).click();
   	        		CM.waitForId("com.android.calendar:id/year_view_pager", 2000);    		
   	        		break;
   	        	case 3://week view
   	        		CM.findObjectByRid("com.android.calendar:id/week_btn", 1000).click();
   	        		CM.hold(1000);    		
   	        		break;
   	        	case 4://date change
   	        		CM.findObjectByRid("com.android.calendar:id/calendar_date_month_num", 1000).click();
   	        		CM.waitForId("android:id/titleDivider", 2000);    		
   	        		break;
   	        	case 5://empty memos list
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("com.android.calendar:id/month_view_pager", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
   	        		CM.waitForId("oppo:id/MajorOperationsGroup", 2000);    		
   	        		break;
   	        	case 6://import browse
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
   	        		CM.waitForId("com.oppo.filemanager:id/select_folder_list_buttons", 2000);    		
   	        		break;
   	        	case 7://setting
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 1000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
   	        		CM.waitForId("android:id/list", 2000);    		
   	        		break;
   	        	case 8://accounts and sync
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{1,0}).click();
   	        		CM.waitForId("android:id/checkbox", 2000);    		
   	        		break;
   	        	case 9://add account
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{2,0}).click();
   	        		CM.findObjectByRid("android:id/title", 2000);
   	        		break;
   	        	case 10://select default account
   	        		ud.pressBack();
   	        		ud.pressBack();
//   	        		CM.waitForId("oppo:id/MajorOperationButtonPositive", 2000);
//   	        		CM.findObjectByRid("android:id/up", 1000).click();
   	        		CM.waitForId("android:id/action_bar_container", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{2,0}).click();
   	        		CM.waitForId("android:id/button2", 2000);    		
   	        		break;
   	        	case 11://show memos
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("android:id/action_bar_container", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{4,0}).click();
   	        		CM.waitForId("android:id/button2", 2000);    		
   	        		break;
   	        	case 12://home time zone
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("android:id/action_bar_container", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{6,1}).click();
   	        		CM.hold(1000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{7,0}).click();
   	        		CM.waitForId("android:id/button2", 2000);    		
   	        		break;
   	        	case 13://week starts on
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("android:id/action_bar_container", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{6,1}).click();
   	        		CM.hold(1000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{9,0}).click();
   	        		CM.waitForId("android:id/button2", 2000);    		
   	        		break;
   	        	case 14://ring tone
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("android:id/action_bar_container", 2000);
   	        		CM.scroll(ud, CM.UP);
   	        		CM.hold(1000);
   	        		CM.scroll(ud, CM.UP);
   	        		CM.hold(2000);
   	        		CM.scroll(ud, CM.UP);
   	        		CM.hold(2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{3,0}).click();
   	        		CM.waitForId("com.oppo.filemanager:id/radiobutton", 2000);    		
   	        		break;
   	        	case 15://file browse
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{1,0}).click();
   	        		CM.hold(2000);
   	        		CM.waitForId("com.oppo.filemanager:id/music_file_full", 2000);    		
   	        		break;
   	        	case 16://alert type
//   	        		CM.findObjectByRid("android:id/up", 1000).click();
//   	        		CM.waitForId("com.oppo.filemanager:id/ringtone_editor_bar", 2000);
//   	        		CM.findObjectByRid("android:id/up", 1000).click();
//   	        		CM.waitForId("android:id/widget_frame", 2000);
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		ud.pressBack();
   	        		CM.waitForId("android:id/action_bar_container", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{4,0}).click();
   	        		CM.waitForId("android:id/button2", 2000);    		
   	        		break;
   	        	case 17://reminder time
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("android:id/action_bar_container", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{5,0}).click();
   	        		CM.waitForId("android:id/button2", 2000);    		
   	        		break;
   	        	case 18://quick response
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("android:id/action_bar_container", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{6,0}).click();
   	        		CM.waitForId("com.android.calendar:id/main_frame", 2000);    		
   	        		break;
   	        	case 19://edit response
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000),new int[]{0,0}).click();
   	        		CM.waitForId("android:id/button2", 2000);    		
   	        		break;
   	        	case 20://new event
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
//   	        		CM.waitForId("android:id/up", 2000);
//   	        		CM.findObjectByRid("android:id/up", 1000).click();
//   	        		CM.hold(1000);
//   	        		CM.findObjectByRid("android:id/up", 1000).click();
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		ud.pressBack();
   	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("com.android.calendar:id/edit_title_layout", 1000);    		
   	        		break;
   	        	case 21://birth of contact browse
   	        		CM.findObjectByRid("com.android.calendar:id/birthday_agenda", 1000).click();
   	        		CM.waitForId("com.android.calendar:id/birthday_add", 2000);
   	        		CM.findObjectByRid("com.android.calendar:id/birthday_add", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);    		
   	        		break;
   	        	case 22://set time
   	        		CM.findObjectByRid("android:id/up", 1000).click();
   	        		CM.waitForId("com.android.calendar:id/birthday_add", 1000);
   	        		CM.findObjectByRid("com.android.calendar:id/birthday_agenda", 1000).click();
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("com.android.calendar:id/start_time", 1000).click();
   	        		CM.waitForId("android:id/button2", 2000);    		
   	        		break;
   	        	case 23://set date
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("com.android.calendar:id/edit_title_layout", 2000);
   	        		CM.findObjectByRid("com.android.calendar:id/start_date", 1000).click();
   	        		CM.waitForId("android:id/button2", 2000);    		
   	        		break;
   	        	case 24://set time zone
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("com.android.calendar:id/edit_title_layout", 2000);
   	        		CM.findObjectByRid("com.android.calendar:id/timezone_button", 1000).click();
   	        		CM.waitForId("android:id/button2", 2000);    		
   	        		break;
   	        	case 25://show all tome zone
   	        		CM.findObjectByRid("com.android.calendar:id/timezone_footer", 1000).click();
   	        		CM.waitForId("oppo:id/select_dialog_listview", 2000);    		
   	        		break;
   	        	case 26://add reminder
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("com.android.calendar:id/edit_title_layout", 2000);
   	        		CM.findObjectByRid("com.android.calendar:id/reminder_button", 1000).click();
   	        		CM.waitForId("android:id/button2", 2000);    		
   	        		break;
   	        	case 27://more options
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("com.android.calendar:id/edit_title_layout", 2000);
   	        		CM.findObjectByRid("com.android.calendar:id/add_more_button", 1000).click();
   	        		CM.waitForId("com.android.calendar:id/to_row", 2000);    		
   	        		break;
   	        	case 28://set repeat
   	        		CM.scroll(ud, CM.UP);
   	        		CM.hold(1000);
   	        		CM.scroll(ud, CM.UP);
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("com.android.calendar:id/repeats_text", 1000).click();
   	        		CM.waitForId("android:id/button2", 2000);    		
   	        		break;
   	        	case 29://set calendar account
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("com.android.calendar:id/edit_title_layout", 2000);
   	        		CM.scroll(ud, CM.UP);
   	        		CM.hold(1000);
   	        		CM.scroll(ud, CM.UP);
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("com.android.calendar:id/availability_button", 1000).click();
   	        		CM.waitForId("android:id/button2", 2000);    		
   	        		break;
   	        	case 30://set privacy
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("com.android.calendar:id/edit_title_layout", 2000);
   	        		CM.findObjectByRid("com.android.calendar:id/visibility_button", 1000).click();
   	        		CM.waitForId("android:id/button2", 2000);    		
   	        		break;
   	        	case 31://edit name and save
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("com.android.calendar:id/edit_title_layout", 2000);
   	        		CM.findObjectByRid("com.android.calendar:id/cancel", 1000).click();
   	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("com.android.calendar:id/edit_title_layout", 1000);
   	        		CM.setText(ud, CM.findChildByPosistion(CM.findObjectByRid("com.android.calendar:id/two_pane", 1000), new int[]{0,0,1,0}), "test123");
   	        		CM.findObjectByRid("com.android.calendar:id/save", 1000).click();
   	        		CM.waitForId("android:id/alertTitle", 2000);    		
   	        		break;
   	        	case 32://event details
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);    		
   	        		break;
   	        	case 33://edit event
   	        		CM.findObjectByRid("com.android.calendar:id/button_edit", 1000).click();
   	        		CM.waitForId("com.android.calendar:id/cancel", 2000);    		
   	        		break;
   	        	case 34://delete confirm
   	        		CM.findObjectByRid("com.android.calendar:id/cancel", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);
   	        		CM.findObjectByRid("com.android.calendar:id/button_delete", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);    		
   	        		break;
   	        	case 35://backup options
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("com.android.calendar:id/button_edit", 2000);
   	        		CM.findObjectByRid("com.android.calendar:id/button_save", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);    		
   	        		break;
   	        	case 36://send selections
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("com.android.calendar:id/button_edit", 2000);
   	        		CM.findObjectByRid("com.android.calendar:id/button_send", 1000).click();
   	        		CM.waitForId("android:id/icon", 2000);    		
   	        		break;
   	        	case 37://memos list
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.calendar:id/button_edit", 2000);
   	        		CM.findObjectByRid("android:id/up", 1000).click();
   	        		CM.waitForId("oppo:id/search_src_text", 2000);    		
   	        		break;
   	        	case 38://more selections
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);    		
   	        		break;
   	        	case 39://import browse
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
   	        		CM.waitForId("com.android.calendar:id/action_select_all", 2000);    		
   	        		break;
   	        	case 40://edit
   	        		ud.pressBack();
   	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
   	        		CM.waitForId("com.android.calendar:id/action_select_all", 2000);    		
   	        		break;
   	        	case 41://send options
   	        		CM.findObjectByRid("com.android.calendar:id/action_select_all", 1000).click();
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		CM.waitForId("android:id/icon", 2000);    		
   	        		break;
   	        	case 42://backup options
   	        		ud.pressBack();
   	        		CM.waitForId("oppo:id/MajorOperationButtonNegative", 1000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNeutral", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);    		
   	        		break;
   	        	case 43://delete confirm
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("oppo:id/MajorOperationButtonNegative", 1000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);    		
   	        		break;
   	        	case 44://main fram with event
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 2000);
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.calendar:id/month_view_pager", 2000);    		
   	        		break;
   	        	case 45://recover
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
   	        		CM.findObjectByRid("com.android.calendar:id/action_select_all", 1000).click();
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.deleteFile(new File("/storage/sdcard0/Backup"));
   	        		CM.hold(2000);
   	        		CM.startPackage("com.oppo.filemanager");
   	        		CM.hold(2000);
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		break;
   	        	default:
   	        		break;
   	        	}
   	        }
   	    }
   	    public static class AlarmDemo
   	    {
   	    	public static void test(UiDevice ud,int step)throws Exception
   	        {
   	    		Rect mr=new Rect();
   	        	switch(step)
   	        	{
   	        	case 1://enter alarm
   	        		CM.findObjectByRid("android:id/action_bar_container", 1000).getChild(new UiSelector().index(1)).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(0)).click();
   	        		CM.waitForId("com.oppo.alarmclock:id/main_fram", 1000);    		
   	        		break;
   	        	case 2://create new alarm
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 2000).click();
   	        		CM.waitForId("com.oppo.alarmclock:id/timePicker", 2000);    		
   	        		break;
   	        	case 3://ringtone setting
//   	        		CM.findObjectByMutiAttribut("oppo:id/oppo_preference", "", "", 2, 1000).click();
   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().className("android.widget.LinearLayout").index(2)).click();
   	        		CM.waitForId("com.oppo.filemanager:id/ringtone_editor_bar", 2000);    		
   	        		break;
   	        	case 4://file browse
   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(1)).getChild(new UiSelector().index(0)).click();
   	        		CM.waitForId("com.oppo.filemanager:id/music_file_picker_buttons", 2000);    		
   	        		break;
   	        	case 5://ringtone list
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.filemanager:id/ringtone_editor_bar", 2000);
   	        		CM.scroll(ud, CM.UP);
   	        		CM.hold(1000);    		
   	        		break;
   	        	case 6://alarm name
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.alarmclock:id/timePicker", 2000);
//   	        		CM.findObjectByMutiAttribut("oppo:id/oppo_preference", "", "", 1, 1000).click();
   	        		Rect lR=CM.findObjectByRid("android:id/list", 1000).getBounds();
   	        		ud.click(lR.centerX(), lR.centerY());
   	        		if(!CM.waitForId("oppo:id/parentPanel", 2000))
   	        		{
   	        			ud.pressBack();
   	        			CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().className("android.widget.LinearLayout").index(1)).click();
   	        			CM.waitForId("oppo:id/parentPanel", 2000);
   	        		}
   	        		break;
   	        	case 7://set alarm name
   	        		CM.setText(ud, CM.findObjectByRid("com.oppo.alarmclock:id/rename_dialog_edit", 1000), "afds123");
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.waitForId("android:id/list", 1000);    		
   	        		break;
   	        	case 8://volume setting
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.alarmclock:id/timePicker", 2000);
   	        		ud.drag(ud.getDisplayWidth()/3, ud.getDisplayHeight()-80, ud.getDisplayWidth()/3, ud.getDisplayHeight()-250, 3);
   	        		CM.hold(1000);
//   	        		CM.findObjectByMutiAttribut("oppo:id/oppo_preference", "", "", 3, 1000).click();
   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().className("android.widget.LinearLayout").index(3)).click();
   	        		CM.waitForId("android:id/buttonPanel", 2000);    		
   	        		break;
   	        	case 9://repeat
   	        		ud.pressBack();
   	        		CM.findObjectByRid("oppo:id/oppo_preference", 1000).click();
   	        		break;
   	        	case 10://repeart setting
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("com.oppo.alarmclock:id/cancel", 1000);
   	        		break;
   	        	case 11://cancel new alarm
   	        		CM.findObjectByRid("com.oppo.alarmclock:id/cancel", 1000).click();
   	        		CM.waitForId("com.oppo.alarmclock:id/main_fram", 1000);    		
   	        		break;
   	        	case 12://delete select
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
   	        		CM.waitForId("com.oppo.alarmclock:id/menu_mark", 2000);    		
   	        		break;
   	        	case 13://delete confirm
   	        		CM.findObjectByRid("com.oppo.alarmclock:id/menu_mark", 1000).click();
   	        		CM.hold(2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);    		
   	        		break;
   	        	case 14://delete
   	        		CM.findObjectByRid("android:id/button2", 2000).click();
   	        		CM.waitForId("com.oppo.alarmclock:id/menu_mark", 1000);
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.alarmclock:id/main_fram", 2000);    		
   	        		break;
   	        	case 15://alarm setting
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
   	        		CM.waitForId("android:id/list", 1000);    		
   	        		break;
   	        	case 16://snooze interval
   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(0)).click();
   	        		CM.waitForId("oppo:id/parentPanel", 1000);    		
   	        		break;
   	        	case 17://global time
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("android:id/list", 1000);
   	        		CM.findObjectByRid("android:id/up", 1000).click();
   	        		CM.waitForId("android:id/content", 1000);
   	        		CM.findObjectByRid("android:id/action_bar_container", 1000).getChild(new UiSelector().index(1)).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(1)).click();
   	        		CM.waitForId("com.oppo.alarmclock:id/oppo_globaltime_option_menu_bar", 1000);    		
   	        		break;
   	        	case 18://add city
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/action_bar_container", 1000);   		
   	        		break;
   	        	case 19://search without result
   	        		CM.findObjectByRid("oppo:id/search_src_text", 1000).click();
   	        		CM.waitForId("android:id/search_src_text", 2000);
   	        		CM.setText(ud, CM.findObjectByRid("android:id/search_src_text", 1000), "1");
   	        		CM.hold(1000);    		
   	        		break;
   	        	case 20://back
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.alarmclock:id/oppo_globaltime_option_menu_bar", 1000);    		
   	        		break;
   	        	case 21://more selection
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000);    		
   	        		break;
   	        	case 22://change order
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
   	        		CM.waitForId("android:id/up", 1000);    		
   	        		break;
   	        	case 23://delete select
   	        		ud.pressBack();
   	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 1000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
   	        		CM.waitForId("com.oppo.alarmclock:id/menu_mark", 1000);    		
   	        		break;
   	        	case 24://delete confirm
   	        		CM.findObjectByRid("com.oppo.alarmclock:id/menu_mark", 1000).click();
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/alertTitle", 1000);    		
   	        		break;
   	        	case 25://delete all
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("com.oppo.alarmclock:id/menu_mark", 1000);
   	        		ud.pressBack();
   	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 2000);    		
   	        		break;
   	        	case 26://timer
   	        		CM.findObjectByRid("android:id/action_bar_container", 1000).getChild(new UiSelector().index(1)).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(3)).click();
   	        		CM.hold(1000);    		
   	        		break;
   	        	case 27://开始计时
   	        		CM.findObjectByRid("com.oppo.alarmclock:id/decrement", 1000).click();
   	        		mr=CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).getBounds();
   	        		ud.click(mr.centerX(), mr.centerY());
   	        		break;
   	        	case 28://暂停
   	        		ud.click(mr.centerX(), mr.centerY());   		
   	        		break;
   	        	case 29://复位
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		break;
   	        	case 30://alert type
//   	        		CM.findObjectByRid("com.oppo.filemanager:id/music_file_picker_buttons", 1000).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(1)).click();
//   	        		CM.waitForId("com.oppo.filemanager:id/ringtone_editor_bar", 2000);
//   	        		CM.findObjectByRid("android:id/up", 1000).click();
//   	        		CM.waitForId("com.oppo.alarmclock:id/timer_ring_set_layout", 1000);
//   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(1)).getChild(new UiSelector().index(0)).click();
//   	        		CM.waitForId("android:id/alertTitle", 1000);    		
   	        		break;
   	        	case 31://stop watch
   	        		CM.findObjectByRid("android:id/action_bar_container", 1000).getChild(new UiSelector().index(1)).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(2)).click();
   	        		CM.hold(1000);    		
   	        		break;
   	        	case 32://start
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.hold(1000);    		
   	        		break;
   	        	case 33://record
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		CM.hold(1000);    		
   	        		break;
   	        	case 34://pause
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.hold(1000);    		
   	        		break;    		
   	        	case 35://reset
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		CM.hold(1000);    		
   	        		break;
   	        	default:
   	        		break;
   	        	}
   	        }
   	    }
   	    public static class WeatherDemo
   	    {
   	    	public static void test(UiDevice ud,int step) throws Exception
   	        {
   	        	switch(step)
   	        	{
   	        	case 1://enter weather
   	        		if(CM.waitForId("android:id/message", 2000))
   	        		{
   	        			ud.pressBack();
   	        			CM.waitForId("com.oppo.weather:id/main_view_no_city_tip", 2000);
   	        		}
   	        		break;
   	        	case 2://menu options
   	        		ud.pressMenu();
   	        		CM.waitForId("com.oppo.weather:id/popup_window_manager", 2000);
   	        		break;
   	        	case 3://add city
   	        		CM.findObjectByRid("com.oppo.weather:id/popup_window_manager", 1000).click();
   	        		CM.waitForId("com.oppo.weather:id/iv_add", 2000);
   	        		break;
   	        	case 4://search and add city
   	        		UiObject group=CM.findObjectByRid("com.oppo.weather:id/gridview", 1000);
   	        		if(group.getChildCount()>1)
   	        		{
   	            		CM.clickLongOnObject(ud, CM.findObjectByRid("com.oppo.weather:id/iv_add", 1000));
   	            		CM.waitForId("com.oppo.weather:id/btn_delete", 2000);
   	            		CM.findObjectByRid("com.oppo.weather:id/btn_delete", 1000).click();
   	            		CM.findObjectByRid("com.oppo.weather:id/btn_complete", 1000).click();
   	            		CM.waitForId("com.oppo.weather:id/city_manager_location_button", 2000);
   	        		}
   	        		CM.findObjectByRid("com.oppo.weather:id/iv_add", 1000).click();
   	        		CM.waitForId("android:id/search_src_text", 2000);
   	        		CM.setText(ud, CM.findObjectByRid("android:id/search_src_text", 1000), "p");
   	        		CM.waitForId("com.oppo.weather:id/fragment_serrch_city_list", 2000);
   	        		CM.findObjectByRid("com.oppo.weather:id/city_name", 1000).click();
   	        		CM.hold(3000);
   	        		CM.waitForId("com.oppo.weather:id/iv_add", 2000);
   	        		break;
   	        	case 5://weather view
   	        		CM.findObjectByRid("com.oppo.weather:id/iv_weather_icon", 1000).click();
   	        		CM.waitForId("com.oppo.weather:id/weather_current_temp", 10000);
   	        		break;
   	        	case 6://three days weather
   	        		if(CM.findObjectByRid("com.oppo.weather:id/three_days_weather", 1000).getChildCount()<4)
   	        			break;
   	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.weather:id/three_days_weather", 1000), new int[]{3,1,0}).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 7://three days weather
   	        		if(CM.findObjectByRid("com.oppo.weather:id/three_days_weather", 1000).getChildCount()<4)
   	        			break;
   	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.weather:id/three_days_weather", 1000), new int[]{3,2,0}).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 8://three days weather
   	        		if(CM.findObjectByRid("com.oppo.weather:id/three_days_weather", 1000).getChildCount()<4)
   	        			break;
   	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.weather:id/three_days_weather", 1000), new int[]{3,3,0}).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 9://share
   	        		if(CM.findObjectByRid("com.oppo.weather:id/three_days_weather", 1000).getChildCount()<4)
   	        			break;
   	        		ud.pressMenu();
   	        		CM.waitForId("com.oppo.weather:id/popup_window_share", 2000);
   	        		CM.findObjectByRid("com.oppo.weather:id/popup_window_share", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 10://share options
   	        		if(!CM.waitForId("android:id/up", 1000))
   	        			break;
   	        		CM.findObjectByRid("com.oppo.weather:id/share_info_button", 1000).click();
   	        		CM.hold(2000);
   	        		break;
   	        	case 11://settings
   	        		if(CM.waitForId("android:id/alertTitle", 1000))
   	        		{
   	    	    		ud.pressBack();
   	    	    		CM.waitForId("android:id/up", 2000);
   	    	    		ud.pressBack();
   	    	    		CM.waitForId("com.oppo.weather:id/weather_current_temp", 10000);
   	        		}
   	        		ud.pressMenu();
   	        		CM.waitForId("com.oppo.weather:id/popup_window_setting", 2000);
   	        		CM.findObjectByRid("com.oppo.weather:id/popup_window_setting", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 12://display settings
   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().resourceId("oppo:id/oppo_preference").index(1)).click();
   	        		CM.waitForId("com.oppo.weather:id/radiobutton", 2000);
   	        		break;
   	        	case 13://automatic update
   	        		ud.pressBack();
   	        		CM.waitForId("android:id/checkbox", 2000);
   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().resourceId("oppo:id/oppo_preference").index(2)).click();
   	        		CM.waitForId("com.oppo.weather:id/radiobutton", 2000);
   	        		break;
   	        	case 14://change temperature unit
   	        		ud.pressBack();
   	        		CM.waitForId("android:id/checkbox", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{5,0}).click();
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.weather:id/weather_current_temp", 2000);
   	        		break;
   	        	case 15://delete
   	        		ud.pressMenu();
   	        		CM.waitForId("com.oppo.weather:id/popup_window_share", 2000);
   	        		CM.findObjectByRid("com.oppo.weather:id/popup_window_setting", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{4,0}).click();
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.weather:id/weather_current_temp", 2000);
   	        		ud.pressMenu();
   	        		CM.waitForId("com.oppo.weather:id/popup_window_share", 2000);
   	        		CM.findObjectByRid("com.oppo.weather:id/popup_window_manager", 1000).click();
   	        		CM.waitForId("com.oppo.weather:id/iv_add", 2000);
   	        		CM.clickLongOnObject(ud, CM.findObjectByRid("com.oppo.weather:id/iv_add", 1000));
   	        		CM.waitForId("com.oppo.weather:id/btn_delete", 2000);
   	        		break;
   	        	case 16://positioning
   	        		CM.findObjectByRid("com.oppo.weather:id/btn_delete", 1000).click();
   	        		CM.findObjectByRid("com.oppo.weather:id/btn_complete", 1000).click();
   	        		CM.waitForId("com.oppo.weather:id/city_manager_location_button", 2000);
   	        		CM.findObjectByRid("com.oppo.weather:id/city_manager_location_button", 1000).click();
   	        		CM.waitForId("android:id/message", 2000);
   	        		break;
   	        	case 17://over
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		break;
   	        	default:
   	        		break;
   	        	}
   	        }
   	    }
   	    public static class MusicDemo
   	    {
   	    	public static void test(UiDevice ud,int step)throws Exception
   	        {
   	        	switch(step)
   	        	{
   	        	case 1://main interface
   	        		if(CM.waitForId("com.oppo.music:id/md__drawer", 2000))
   	        		{
   	        			CM.findObjectByRid("com.oppo.music:id/icon_to_list", 1000).click();
   	        			CM.waitForId("com.oppo.music:id/gridview", 2000);    			
   	        		}    			
   	        		break;
   	        	case 2://my music
   	        		CM.findChildbyAtrribut(CM.findObjectByRid("com.oppo.music:id/list", 1000), "", "android.widget.LinearLayout", "", 1, 1000).click();
   	        		CM.waitForId("android:id/up", 2000);    		
   	        		break;
   	        	case 3://operation options
   	        		CM.findObjectByRid("com.oppo.music:id/operation", 1000).click();
   	        		CM.waitForId("com.oppo.music:id/list_operation_container", 2000);    		
   	        		break;
   	        	case 4://add to new list
   	        		CM.findObjectByRid("com.oppo.music:id/list_item_add_to_playlist", 1000).click();
   	        		CM.waitForId("android:id/alertTitle", 2000);    		
   	        		break;
   	        	case 5://create new list
   	        		CM.findObjectByRid("android:id/text1", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);    		
   	        		break;
   	        	case 6://add and delete favorite
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		CM.findObjectByRid("com.oppo.music:id/operation", 1000).click();
   	        		CM.waitForId("com.oppo.music:id/list_operation_container", 2000);
   	        		CM.findObjectByRid("com.oppo.music:id/list_item_favour", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		CM.findObjectByRid("com.oppo.music:id/operation", 1000).click();
   	        		CM.waitForId("com.oppo.music:id/list_operation_container", 2000);
   	        		CM.findObjectByRid("com.oppo.music:id/list_item_favour", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);    		
   	        		break;
   	        	case 7://delete file
   	        		CM.findObjectByRid("com.oppo.music:id/operation", 1000).click();
   	        		CM.waitForId("com.oppo.music:id/list_operation_container", 2000);
   	        		CM.findObjectByRid("com.oppo.music:id/list_item_delete", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);    		
   	        		break;
   	        	case 8://ring tone
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		CM.findObjectByRid("com.oppo.music:id/operation", 1000).click();
   	        		CM.waitForId("com.oppo.music:id/list_operation_container", 2000);
   	        		CM.findObjectByRid("com.oppo.music:id/list_item_ringtone", 1000).click();
   	        		CM.waitForId("android:id/content", 2000);    		
   	        		break;
   	        	case 9://send file
   	        		ud.pressBack();
   	        		CM.waitForId("android:id/up", 2000);
   	        		CM.findObjectByRid("com.oppo.music:id/operation", 1000).click();
   	        		CM.waitForId("com.oppo.music:id/list_operation_container", 2000);
   	        		CM.findObjectByRid("com.oppo.music:id/list_item_send", 1000).click();
   	        		CM.waitForId("android:id/content", 2000);    		
   	        		break;
   	        	case 10://file info
   	        		ud.pressBack();
   	        		CM.waitForId("android:id/up", 2000);
   	        		CM.findObjectByRid("com.oppo.music:id/operation", 1000).click();
   	        		CM.waitForId("com.oppo.music:id/list_operation_container", 2000);
   	        		CM.findObjectByRid("com.oppo.music:id/list_item_details", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);    		
   	        		break;
   	        	case 11://artist
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		CM.findObjectByRid("com.oppo.music:id/title", 1000).getChild(new UiSelector().index(1)).click();
   	        		CM.hold(1000);    		
   	        		break;
   	        	case 12://album
   	        		CM.findObjectByRid("com.oppo.music:id/title", 1000).getChild(new UiSelector().index(2)).click();
   	        		CM.hold(1000);    		
   	        		break;
   	        	case 13://folder
   	        		CM.findObjectByRid("com.oppo.music:id/title", 1000).getChild(new UiSelector().index(3)).click();  		
   	        		CM.hold(1000); 
   	        		break;
   	        	case 14://favor
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.music:id/gridview", 2000);
   	        		CM.findObjectByRid("com.oppo.music:id/list", 1000).getChild(new UiSelector().index(2).className("android.widget.LinearLayout")).click();
   	        		CM.waitForId("android:id/up", 2000);		
   	        		break;
   	        	case 15://create list
   	        		CM.findObjectByRid("android:id/up", 1000).click();
   	        		CM.waitForId("android:id/action_bar_overlay_layout", 2000);    
   	        		CM.findObjectByRid("com.oppo.music:id/list", 1000).getChild(new UiSelector().index(4).className("android.widget.LinearLayout")).click();
   	        		break;
   	        	case 16://new list
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.waitForId("com.oppo.music:id/mark_list_bar_choose", 2000);		
   	        		break;
   	        	case 17://add song
   	        		CM.findObjectByRid("oppo:id/oppo_listview_scrollchoice_checkbox", 1000).click();
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/action_bar_overlay_layout", 2000); 
   	        		break;    		
   	        	case 18://view list
   	        		CM.findObjectByRid("com.oppo.music:id/list", 1000).getChild(new UiSelector().index(5).className("android.widget.LinearLayout")).click();   		
   	        		CM.waitForId("android:id/up", 1000);
   	        		break;
   	        	case 19://edit list
   	        		CM.findObjectByRid("com.oppo.music:id/edit_playlist", 1000).click();
   	        		CM.waitForId("android:id/alertTitle", 2000);    		
   	        		break;
   	        	case 20://clear list
   	        		CM.waitForId("android:id/alertTitle", 2000);
   	        		CM.findObjectByRid("oppo:id/select_dialog_listview", 1000).getChild(new UiSelector().index(1)).click();
   	        		CM.waitForId("com.oppo.music:id/add_songs", 2000);    		
   	        		break;
   	        	case 21://delete list
   	        		CM.findObjectByRid("com.oppo.music:id/edit_playlist", 1000).click();
   	        		CM.waitForId("android:id/alertTitle", 2000);
   	        		CM.findObjectByRid("oppo:id/select_dialog_listview", 1000).getChild(new UiSelector().index(2)).click();
   	        		CM.waitForId("android:id/button1", 2000);    		
   	        		break;
   	        	case 22://delete list
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.waitForId("android:id/action_bar_container", 2000);
   	        		Rect lR=CM.findObjectByRid("com.oppo.music:id/list", 1000).getChild(new UiSelector().index(5).className("android.widget.LinearLayout")).getBounds();
   	        		CM.clickLongOnScreen(ud, lR.centerX(), lR.centerY());
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000); 
   	        		break;
   	        	case 23://more options
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.waitForId("android:id/action_bar_container", 2000);
   	        		CM.findObjectByRid("com.oppo.music:id/control_bar_prev", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);    		
   	        		break;
   	        	case 24://scan music
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);    		
   	        		break;
   	        	case 25://scan filter
   	        		CM.findObjectByRid("com.oppo.music:id/sub_text_button", 1000).click();
   	        		CM.waitForId("com.oppo.music:id/menuoperation", 2000);    		
   	        		break;
   	        	case 26://folder filter
   	        		CM.findObjectByRid("com.oppo.music:id/file_directory_filter_layout", 1000).getChild(new UiSelector().index(1)).click();
   	        		CM.waitForId("com.oppo.music:id/textView_ok", 2000);    		
   	        		break;
   	        	case 27://do scan    		
   	        		CM.findObjectByRid("com.oppo.music:id/textView_ok", 1000).click();
   	        		CM.waitForId("com.oppo.music:id/menuoperation", 2000);
   	        		CM.findObjectByRid("android:id/up", 1000).click();
   	        		CM.waitForId("com.oppo.music:id/sub_text_button", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("com.oppo.music:id/textView_settings_scan_already", 5000);    		
   	        		break;
   	        	case 28://sleep mode
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/action_bar_container", 2000);
   	        		CM.findObjectByRid("com.oppo.music:id/control_bar_prev", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);    		
   	        		break;
   	        	case 29://settings
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.waitForId("android:id/action_bar_container", 2000);
   	        		CM.findObjectByRid("com.oppo.music:id/control_bar_prev", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView3", 1000).click();
   	        		if(!CM.waitForId("android:id/action_bar", 2000))   	
   	        			CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView3", 1000).click();
   	        		break;
   	        	case 30://play interface
   	        		CM.findObjectByRid("android:id/up", 1000).click();
   	        		CM.waitForId("android:id/action_bar_container", 2000);
   	        		CM.findObjectByRid("com.oppo.music:id/control_bar_album_bg", 1000).click();
   	        		CM.waitForId("com.oppo.music:id/md__drawer", 2000);    		
   	        		break;
   	        	case 31://playing list
   	        		CM.scroll(ud, CM.LEFT);
   	        		CM.hold(1000);    		
   	        		break;
   	        	case 32://menu buttons
   	        		CM.scroll(ud, CM.RIGHT);
   	        		CM.hold(2000);
   	        		for(int i=0;i<4;i++)
   	        		{
//   	        			CM.findObjectByRid("com.oppo.music:id/icon_operation", 1000).click();
   	        			ud.pressMenu();
   	        			CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        			CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView"+i, 1000).click();
   	        			CM.hold(2000);
   	        			if(!CM.waitForId("com.oppo.music:id/icon_operation", 1000))
   	        			{
   	        				ud.pressBack();
   	        				CM.waitForId("com.oppo.music:id/icon_operation", 2000);
   	        			}
   	        		}
//   	    			CM.findObjectByRid("com.oppo.music:id/icon_operation", 1000).click();
   	        		ud.pressMenu();
   	    			CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	    			CM.hold(2000);    		
   	        		break;
   	        	case 33://
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		CM.hold(2000);
   	        		break;
   	        	default:
   	        		break;
   	        	}    	
   	        }
   	    }
   	    public static class VideoDemo
   	    {
   	    	public static void test(UiDevice ud,int step) throws Exception
   	        {
   	        	switch(step)
   	        	{
   	        	case 1://enter video
   	        		CM.waitForId("com.oppo.video:id/my_video_tab_list", 3000);
   	        		break;
   	        	case 2://enter shooting video
   	        		CM.findObjectByRid("com.oppo.video:id/my_video_tab_list", 1000).getChild(new UiSelector().index(1).className("android.widget.RelativeLayout")).click();
   	        		CM.waitForId("com.oppo.video:id/videoname", 2000);
   	        		break;
   	        	case 3://play video
   	        		CM.findObjectByRid("com.oppo.video:id/thumb_icon", 2000).click();
   	        		CM.waitForId("com.oppo.video:id/surface_view", 2000);
   	        		CM.hold(5000);
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.video:id/videoname", 2000);
   	        		break;
   	        	case 4://edit video file
   	        		CM.clickLongOnObject(ud, CM.findObjectByRid("com.oppo.video:id/thumb_icon", 1000));
   	        		CM.waitForId("com.oppo.video:id/option_menu_delete", 2000);
   	        		break;
   	        	case 5://delete confirm
   	        		CM.findObjectByRid("com.oppo.video:id/option_menu_delete", 1000).click();
   	        		CM.waitForId("com.oppo.video:id/button2", 2000);  		
   	        		break;
   	        	case 6://share video
   	        		CM.findObjectByRid("com.oppo.video:id/button2", 1000).click();
   	        		CM.waitForId("com.oppo.video:id/option_menu_delete", 2000); 
   	        		CM.findObjectByRid("com.oppo.video:id/option_menu_share", 1000).click();
   	        		CM.waitForId("android:id/title_template", 2000); 
   	        		break;
   	        	case 7://video file details
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.video:id/option_menu_share", 2000); 
   	        		CM.findObjectByRid("com.oppo.video:id/option_menu_detail", 1000).click();
   	        		CM.waitForId("com.oppo.video:id/button1", 2000);    		
   	        		break;
   	        	case 8://video file rename
   	        		CM.findObjectByRid("com.oppo.video:id/button1", 1000).click();
   	        		CM.waitForId("com.oppo.video:id/option_menu_detail", 2000); 
   	        		CM.findObjectByRid("com.oppo.video:id/option_menu_rename", 1000).click();
   	        		CM.waitForId("com.oppo.video:id/button2", 2000); 
   	        		break;
   	        	case 9://settings
   	        		CM.findObjectByRid("com.oppo.video:id/button2", 1000).click();
   	        		ud.pressBack();
//   	        		CM.waitForId("com.oppo.video:id/option_menu_rename", 2000);
//   	        		CM.findObjectByRid("com.oppo.video:id/iv_top_back", 1000).click();
   	        		CM.hold(1000);
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.video:id/my_video_tab_list", 2000);
   	        		CM.findObjectByRid("com.oppo.video:id/my_video_tab_list", 1000).getChild(new UiSelector().index(4).className("android.widget.RelativeLayout")).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 10://local video
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.video:id/my_video_tab_list", 2000);
   	        		CM.findObjectByRid("com.oppo.video:id/my_video_tab_list", 1000).getChild(new UiSelector().index(2).className("android.widget.RelativeLayout")).click();
   	        		CM.hold(1000);
   	        		break; 
   	        	case 11://local video
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		break;  
   	        	default:
   	        		break;
   	        	}
   	        }
   	    }
//   	    public static class BackupDemo
//   	    {
//   	    	public static void test(UiDevice ud,int step) throws Exception
//   	        {
//   	        	switch(step)
//   	        	{
//   	        	case 1://main interface
//   	        		CM.waitForId("com.oppo.backuprestore:id/oppo_option_menu_bar", 2000);
//   	        		break;
//   	        	case 2://create backup
//   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
//   	        		CM.waitForId("android:id/up", 2000);
//   	        		break;
//   	        	case 3://backup over
//   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/content", 1000), new int[]{0,1,0,0,0}).click();
//   	        		CM.waitForId("com.oppo.backuprestore:id/result_tip", 120000);
//   	        		break;
//   	        	case 4://main interface with backup
//   	        		CM.findObjectByRid("com.oppo.backuprestore:id/button", 1000).click();
//   	        		CM.waitForId("com.oppo.backuprestore:id/oppo_option_menu_bar", 2000);
//   	        		break;
//   	        	case 5://backup detail
//   	        		CM.findObjectByRid("com.oppo.backuprestore:id/listView", 1000).getChild(new UiSelector().index(0)).click();
//   	        		CM.waitForId("android:id/up", 2000);
//   	        		break;
//   	        	case 6://import backup
//   	        		CM.findObjectByRid("com.oppo.backuprestore:id/button", 1000).click();
//   	        		while(!CM.waitForId("com.oppo.backuprestore:id/result_tip", 1000))
//   	        		{
//   	        			if(CM.waitForId("com.android.vending:id/positive_button", 1000))
//   	        				CM.findObjectByRid("com.android.vending:id/positive_button", 1000).click();
//   	        		}    		   		
//   	        		break;
//   	        	case 7://more options
//   	        		CM.findObjectByRid("com.oppo.backuprestore:id/button", 1000).click();
//   	        		CM.waitForId("com.oppo.backuprestore:id/oppo_option_menu_bar", 2000);
//   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
//   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
//   	        		break;
//   	        	case 8://edit backup
//   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
//   	        		CM.waitForId("com.oppo.backuprestore:id/action_select_all", 2000);
//   	        		break;
//   	        	case 9://rename backup
//   	        		CM.findObjectByRid("com.oppo.backuprestore:id/action_select_all", 1000).click();
//   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
//   	        		CM.waitForId("android:id/button1", 2000);
//   	        		break;
//   	        	case 10://delete backup
//   	        		CM.findObjectByRid("android:id/button1", 1000).click();
//   	        		CM.waitForId("com.oppo.backuprestore:id/action_select_all", 2000);
//   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
//   	        		CM.waitForId("android:id/button1", 2000);
//   	        		break;
//   	        	case 11://timing backup
//   	        		CM.findObjectByRid("android:id/button1", 1000).click();
//   	        		CM.waitForId("oppo:id/MajorOperationButtonPositive", 2000);
//   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).getChild(new UiSelector().index(0)).click();
//   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
//   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
//   	        		CM.waitForId("android:id/up", 2000);
//   	        		break;
//   	        	case 12://backup cycle
//   	        		CM.findObjectByClassname("com.oppo.widget.OppoSwitch", 1000).click();
//   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{0,0}).click();
//   	        		CM.waitForId("android:id/button1", 2000);
//   	        		break;
//   	        	case 13://backup time
//   	        		CM.findObjectByRid("android:id/button1", 1000).click();
//   	        		CM.waitForId("android:id/list", 2000);
//   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{1,0}).click();
//   	        		CM.waitForId("android:id/button1", 2000);
//   	        		break;
//   	        	case 14://backup options
//   	        		CM.findObjectByRid("android:id/button1", 1000).click();
//   	        		CM.waitForId("android:id/list", 2000);
//   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{2,0}).click();
//   	        		CM.waitForId("com.oppo.backuprestore:id/expandableListView", 2000);
//   	        		break;
//   	        	case 15://settings
//   	        		CM.findObjectByRid("com.oppo.backuprestore:id/button", 1000).click();
//   	        		CM.waitForId("android:id/list", 2000);
//   	        		CM.findObjectByClassname("com.oppo.widget.OppoSwitch", 1000).click();
//   	        		ud.pressBack();    		
//   	        		CM.waitForId("oppo:id/MajorOperationButtonPositive", 2000);
//   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).getChild(new UiSelector().index(0)).click();
//   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
//   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView2", 1000).click();
//   	        		CM.waitForId("android:id/up", 2000);
//   	        		break;
//   	        	case 16://backup contacts from
//   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{0,0}).click();
//   	        		CM.waitForId("android:id/button1", 2000);
//   	        		break;
//   	        	case 17://over
//   	        		CM.findObjectByRid("android:id/button1", 1000).click();
//   	        		ud.pressBack();
//   	        		ud.pressBack();
//   	        		break;
//   	        	default:
//   	        		break;
//   	        	}
//   	        }
//   	    }
   	  //file manager test//index 14,length 33
   	    public static class FilemanagerDemo
   	    {    	  
   	        public static void test(UiDevice ud,int step)throws Exception
   	        {
   	        	switch(step)
   	        	{
   	        	case 1://main sheet
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/action_bar_container", 1000), new int[]{1,0,0}).click();
   	        		CM.hold(1000);    		
   	        		break;
   	        	case 2://music list
   	        		Rect r = null;
   	        		if(CM.waitForId("com.oppo.filemanager:id/category_gallery_item_view", 1000))
   	        			r=CM.findObjectByRid("com.oppo.filemanager:id/category_gallery_item_view", 1000).getBounds();
   	        		else if(CM.waitForId("com.oppo.filemanager:id/gallery_view", 1000))
   	        			r=CM.findObjectByRid("com.oppo.filemanager:id/gallery_view", 1000).getBounds();
   	        		for(int i=0;i<2;i++)
   	        		{
   	        			for(int j=0;j<3;j++)
   	        			{
   	        				ud.click(r.width()/4*(i*2+1), r.top+(r.height()/6*(j*2+1)));
   	        				CM.waitForId("android:id/up", 2000);
   	        				ud.pressBack();
   	        				CM.waitForId("com.oppo.filemanager:id/shortcut_button", 2000);
   	        			}
   	        		}
   	        		ud.click(r.width()/4, r.top+r.height()/6);
   	        		CM.waitForId("oppo:id/search_src_text", 2000);    		
   	        		break;
   	        	case 3://sort selections
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		CM.waitForId("android:id/button2", 2000);    		
   	        		break;
   	        	case 4://edit list
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("oppo:id/search_src_text", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);    		
   	        		break;
   	        	case 5://delete confirm
   	        		CM.findObjectByRid("com.oppo.filemanager:id/action_select_all", 1000).click();
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);    		
   	        		break;
   	        	case 6://copy selection
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		CM.waitForId("com.oppo.filemanager:id/folder_list_buttons", 2000);    		
   	        		break;
   	        	case 7://copy
   	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.filemanager:id/folder_list_buttons", 1000), new int[]{0,2}).click();
   	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);    		
   	        		break;
   	        	case 8://remote   
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("com.oppo.filemanager:id/ftp_open", 2000);    		
   	        		break;
   	        	case 9://clean up
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.filemanager:id/shortcut_button", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);    		
   	        		break;
   	        	case 10://timing clean up
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		CM.waitForId("android:id/checkbox", 2000);    		
   	        		break;
   	        	case 11://safe
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.filemanager:id/shortcut_button", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);    		
   	        		break;
   	        	case 12://move
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.filemanager:id/shortcut_button", 2000);
   	        		if(CM.waitForId("com.oppo.filemanager:id/category_gallery_item_view", 1000))
   	        			r=CM.findObjectByRid("com.oppo.filemanager:id/category_gallery_item_view", 1000).getBounds();
   	        		else
   	        			r=CM.findObjectByRid("com.oppo.filemanager:id/gallery_view", 1000).getBounds();
   	        		ud.click(r.width()/4, r.top+r.height()/6);
//   	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.filemanager:id/pager", 1000), new int[]{0,0,0,0,0}).click();
   	        		CM.waitForId("oppo:id/search_src_text", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
   	        		CM.findObjectByRid("com.oppo.filemanager:id/action_select_all", 1000).click();
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNeutral", 1000).click();
   	        		CM.waitForId("com.oppo.filemanager:id/folder_list_buttons", 2000);    		
   	        		break;
   	        	case 13://more options
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);    		
   	        		break;
   	        	case 14://compress options
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView3", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);    		
   	        		break;
   	        	case 15://compressing
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.hold(2000);
   	        		if(CM.waitForId("android:id/message", 1000))
   	        			CM.findObjectByRid("android:id/button1", 1000).click();    		
   	        		break;
   	        	case 16://rename
   	        		CM.waitForId("oppo:id/search_src_text", 600000);
   	        		if(CM.waitForId("com.oppo.filemanager:id/action_select_all", 1000))
   	        		{
   		        		ud.pressBack();
   		        		CM.hold(1000);
   	        		}
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
   	        		CM.findObjectByRid("oppo:id/oppo_listview_scrollchoice_checkbox", 1000).click();
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);    		
   	        		break;
   	        	case 17://share selections
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView2", 1000).click();
   	        		CM.hold(1000);    		
   	        		break;
   	        	case 18://details
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		Rect Rd=CM.findObjectByRid("oppo:id/MoreOperationsGroup", 1000).getBounds();
   	        		while(!CM.waitForId("oppo:id/MoreOperationArrowLeft", 1000))
   	        		    ud.drag(ud.getDisplayWidth()/2+150, Rd.centerY(), ud.getDisplayWidth()/2-150,  Rd.centerY(), 5);
//   	        		CM.dragObj(ud, CM.findObjectByRid("oppo:id/MoreOperationsGroup", 1000), CM.LEFT, 5);
   	        		CM.hold(2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);    		
   	        		break;
   	        	case 19://music set as
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
//   	        		CM.dragObj(ud, CM.findObjectByRid("oppo:id/MoreOperationsGroup", 1000), CM.LEFT, 5);
//   	        		ud.drag(ud.getDisplayWidth()-50, ud.getDisplayHeight()-30, 50, ud.getDisplayHeight()-30, 50);
   	        		Rd=CM.findObjectByRid("oppo:id/MoreOperationsGroup", 1000).getBounds();
   	        		while(!CM.waitForId("oppo:id/MoreOperationArrowLeft", 1000))
   	        		    ud.drag(ud.getDisplayWidth()-100, Rd.centerY(), 50,  Rd.centerY(), 5);
   	        		CM.hold(2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
   	        		CM.waitForId("android:id/alertTitle", 2000);    		
   	        		break;
   	        	case 20://picture set as
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.filemanager:id/shortcut_button", 2000);
   	        		if(CM.waitForId("com.oppo.filemanager:id/category_gallery_item_view", 1000))
   	        			r=CM.findObjectByRid("com.oppo.filemanager:id/category_gallery_item_view", 1000).getBounds();
   	        		else
   	        			r=CM.findObjectByRid("com.oppo.filemanager:id/gallery_view", 1000).getBounds();
   	        		ud.click(r.width()/4, r.top+r.height()/2);
//   	        		CM.findObjectByRid("com.oppo.filemanager:id/picture_view", 1000).click();
   	        		CM.waitForId("oppo:id/search_src_text", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
   	        		CM.findObjectByRid("oppo:id/oppo_listview_scrollchoice_checkbox", 1000).click();
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
//   	        		CM.dragObj(ud, CM.findObjectByRid("oppo:id/MoreOperationsGroup", 1000), CM.LEFT, 5);
   	        		while(!CM.waitForId("oppo:id/MoreOperationArrowLeft", 1000))
   	        		    ud.drag(ud.getDisplayWidth()-50, ud.getDisplayHeight()-30, 50, ud.getDisplayHeight()-30, 50);
   	        		CM.hold(2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
   	        		CM.waitForId("android:id/alertTitle", 2000);    		
   	        		break;
   	        	case 21://video list
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.filemanager:id/shortcut_button", 2000);
   	        		if(CM.waitForId("com.oppo.filemanager:id/category_gallery_item_view", 1000))
   	        			r=CM.findObjectByRid("com.oppo.filemanager:id/category_gallery_item_view", 1000).getBounds();
   	        		else
   	        			r=CM.findObjectByRid("com.oppo.filemanager:id/gallery_view", 1000).getBounds();
   	        		ud.click(r.width()*3/4, r.top+r.height()/6);
//   	        		CM.findObjectByRid("com.oppo.filemanager:id/video_view", 1000).click();
   	        		CM.waitForId("oppo:id/search_src_text", 2000);    		
   	        		break;
   	        	case 22://document list
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.filemanager:id/shortcut_button", 2000);
   	        		if(CM.waitForId("com.oppo.filemanager:id/category_gallery_item_view", 1000))
   	        			r=CM.findObjectByRid("com.oppo.filemanager:id/category_gallery_item_view", 1000).getBounds();
   	        		else
   	        			r=CM.findObjectByRid("com.oppo.filemanager:id/gallery_view", 1000).getBounds();
   	        		ud.click(r.width()*3/4, r.top+r.height()/2);
//   	        		CM.findObjectByRid("com.oppo.filemanager:id/doc_view", 1000).click();
   	        		CM.waitForId("oppo:id/search_src_text", 2000);    		
   	        		break;
   	        	case 23://apk list
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.filemanager:id/shortcut_button", 2000);
//   	        		CM.findObjectByRid("com.oppo.filemanager:id/apk_view", 1000).click();
   	        		if(CM.waitForId("com.oppo.filemanager:id/category_gallery_item_view", 1000))
   	        			r=CM.findObjectByRid("com.oppo.filemanager:id/category_gallery_item_view", 1000).getBounds();
   	        		else
   	        			r=CM.findObjectByRid("com.oppo.filemanager:id/gallery_view", 1000).getBounds();
   	        		ud.click(r.width()/4, r.top+r.height()*5/6);
   	        		CM.waitForId("oppo:id/search_src_text", 2000);    		
   	        		break;
   	        	case 24://zip list
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.filemanager:id/shortcut_button", 2000);
//   	        		CM.findObjectByRid("com.oppo.filemanager:id/zip_view", 1000).click();
   	        		if(CM.waitForId("com.oppo.filemanager:id/category_gallery_item_view", 1000))
   	        			r=CM.findObjectByRid("com.oppo.filemanager:id/category_gallery_item_view", 1000).getBounds();
   	        		else
   	        			r=CM.findObjectByRid("com.oppo.filemanager:id/gallery_view", 1000).getBounds();
   	        		ud.click(r.width()*3/4, r.top+r.height()*5/6);
   	        		CM.waitForId("oppo:id/search_src_text", 2000);    		
   	        		break;
   	        	case 25://storage list
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.filemanager:id/shortcut_button", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/action_bar_container", 1000), new int[]{1,0,1}).click();
   	        		CM.waitForId("oppo:id/search_src_text", 2000);    		
   	        		break;
   	        	case 26://create new folder
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);    		
   	        		break;
   	        	case 27://more options
   	        		CM.findObjectByRid("com.oppo.filemanager:id/file_name_edit", 1000).clickBottomRight();
   	        		CM.setText(ud, CM.findObjectByRid("com.oppo.filemanager:id/file_name_edit", 1000), "0");
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		while(CM.waitForId("android:id/custom", 2000))
   	        		{
   	        			ud.pressBack();
   	        			CM.hold(1500);
   	        		}
   	        		CM.waitForId("oppo:id/search_src_text", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);    		
   	        		break;
   	        	case 28://sort
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
   	        		CM.waitForId("android:id/button2", 2000);    		
   	        		break;
   	        	case 29://more select
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("oppo:id/search_src_text", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);  		
   	        		break;
   	        	case 30://
   	        		CM.hold(1000);    		
   	        		break;
   	        	case 31://copy to
   	        		ud.pressBack();
   	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
   	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.filemanager:id/file_list_view_container", 1000), new int[]{0,1,2,0}).click();
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		CM.findObjectByRid("com.oppo.filemanager:id/mark_check", 1000).getChild(new UiSelector().index(0)).click();
   	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.filemanager:id/folder_list_buttons", 1000),new int[]{0,1}).click();
   	        		CM.hold(1000); 
   	        		if(CM.waitForId("android:id/button1", 1000))
   	        			CM.findObjectByRid("android:id/button1", 1000).click();  
   	        		break;
   	        	case 32://delete confirm
   	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 10000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
   	        		CM.waitForId("com.oppo.filemanager:id/action_select_all", 2000);
   	        		CM.findObjectByRid("com.oppo.filemanager:id/action_select_all", 1000).click();
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);    		
   	        		break;
   	        	case 33://delete
   	        		CM.findObjectByRid("android:id/button1", 1000).click();    		
   	        		break;
   	        	default:
   	        		break;
   	        	}    	
   	        }
   	    }
   	    public static class SettingDemo
   	    {
   	    	public static void test(UiDevice ud,int step) throws Exception
   	        {
   	        	switch(step)
   	        	{
   	        	case 1://sound settings
   	        		CM.waitForId("com.android.settings:id/pager", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/action_bar_container", 1000), new int[]{1,0,1}).click();
   	        		CM.hold(2000);
   	        		break;
   	        	case 2://volumes
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{1,1}).click();
   	        		CM.waitForId("android:id/button1", 2000);
   	        		break;
   	        	case 3://silent mode
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.waitForId("com.android.settings:id/pager", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{2,1}).click();
   	        		CM.waitForId("android:id/button2", 2000);
   	        		break;
   	        	case 4://ring tone
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("com.android.settings:id/pager", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{4,1}).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 5://message tone
   	        		CM.findObjectByRid("android:id/up", 1000).click();
   	        		CM.waitForId("com.android.settings:id/pager", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{5,1}).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 6://other notifications
   	        		CM.findObjectByRid("android:id/up", 1000).click();
   	        		CM.waitForId("com.android.settings:id/pager", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{6,1}).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 7://other options
   	        		CM.findObjectByRid("android:id/up", 1000).click();
   	        		CM.waitForId("com.android.settings:id/pager", 2000);
   	        		CM.scroll(ud, CM.UP);
   	        		CM.hold(1000);
   	        		break;
   	        	case 8://display
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/action_bar_container", 1000), new int[]{1,0,2}).click();
   	        		CM.hold(2000);
   	        		break;
   	        	case 9://brightness
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{1,0}).click();
   	        		CM.waitForId("android:id/button1", 2000);
   	        		break;
   	        	case 10://wall paper and lock screen
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("com.android.settings:id/pager", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{2,0}).click();
   	        		CM.hold(2000);
   	        		break;
   	        	case 11://sleep
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.settings:id/pager", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{3,0}).click();
   	        		CM.waitForId("android:id/button2", 2000);
   	        		break;
   	        	case 12://font
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("com.android.settings:id/pager", 2000);
   	        		CM.scroll(ud, CM.UP);
   	        		CM.hold(1000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{CM.findObjectByRid("android:id/list", 1000).getChildCount()-1,0}).click();
   	        		if(!CM.waitForId("com.android.settings:id/radiobutton", 1000))
   	        			CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{CM.findObjectByRid("android:id/list", 1000).getChildCount()-2,0}).click();
   	        		CM.waitForId("com.android.settings:id/radiobutton", 1000);
   	        		break;
   	        	case 13://wifi        		
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.settings:id/pager", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/action_bar_container", 1000), new int[]{1,0,0}).click();
   	        		CM.hold(2000);
   	        		CM.findChildbyAtrribut(CM.findObjectByRid("android:id/list", 1000), "", "android.widget.LinearLayout", "", 1, 1000).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 14://add hot point
   	        		UiObject wB=CM.findObjectByClassname("com.oppo.widget.OppoSwitch", 1000);
   	        		if(!wB.isChecked())
   	        		    wB.click();
   	        		if(CM.waitForId("android:id/button1", 1000))
   	        			CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.hold(3000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		CM.waitForId("android:id/button1", 1000);
   	        		break;
   	        	case 15://more options
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNeutral", 1000).click();
   	        		CM.waitForId("android:id/checkbox", 1000);
   	        		break;
   	        	case 16://SIM card setting
   	        		ud.pressBack();
//   	        		CM.waitForId("oppo:id/MajorOperationButtonNeutral", 1000);
//   	        		CM.findObjectByClassname("com.oppo.widget.OppoSwitch", 1000).click();
//   	        		if(CM.waitForId("android:id/button1", 1000))
//   	        			CM.findObjectByRid("android:id/button1", 1000).click();
   	        		ud.pressBack();
//   	        		CM.waitForId("com.android.settings:id/pager", 2000);
   	        		CM.findChildbyAtrribut(CM.findObjectByRid("android:id/list", 1000), "", "android.widget.LinearLayout", "", 2, 1000).click();
   	        		CM.waitForId("com.android.settings:id/Check_Enable", 2000);
   	        		break;
   	        	case 17://open networks
   	        		ud.pressBack();
//   	        		CM.waitForId("com.android.settings:id/pager", 2000);
   	        		CM.findChildbyAtrribut(CM.findObjectByRid("android:id/list", 1000), "", "android.widget.LinearLayout", "", 3, 1000).click();
   	        		CM.waitForId("android:id/checkbox", 1000);
//   	        		CM.waitForId("android:id/up", 2000);
//   	        		wB=CM.findObjectByClassname("com.oppo.widget.OppoSwitch", 1000);
//   	        		if(!wB.isChecked())
//   	        		    wB.click();
//   	        		CM.waitForId("android:id/list", 1000);
   	        		break;
   	        	case 18://open blue tooth
   	        		ud.pressBack();
   	        		if(!CM.waitForId("com.android.settings:id/pager", 1000))
   	        			ud.pressBack();
   	        		CM.findChildbyAtrribut(CM.findObjectByRid("android:id/list", 1000), "", "android.widget.LinearLayout", "", 4, 1000).click();
   	        		if(CM.waitForId("android:id/empty", 1000))
   	        			CM.findObjectByRid("com.android.settings:id/switch_widget",1000).click();
   	        		CM.hold(3000);
//   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
//   	        		CM.hold(1000);
   	        		break;
   	        	case 19://more settings
   	        		if(!CM.waitForId("android:id/empty", 1000))
   	        			CM.findObjectByRid("com.android.settings:id/switch_widget",1000).click();//关闭蓝牙
   	        		ud.pressBack();
   	        		CM.findChildbyAtrribut(CM.findObjectByRid("android:id/list", 1000), "oppo:id/oppo_preference", "android.widget.LinearLayout", "", 5, 1000).click();
   	        		//进入more settings
   	        		CM.waitForId("android:id/checkbox", 1000);
//   	        		ud.pressBack(); 
//   	        		CM.waitForClass("com.oppo.widget.OppoSwitch", 1000);
//   	        		CM.findObjectByClassname("com.oppo.widget.OppoSwitch", 1000).click();
//   	        		ud.pressBack();
//   	        		CM.waitForId("com.android.settings:id/pager", 2000);
//   	        		CM.findChildbyAtrribut(CM.findObjectByRid("android:id/list", 1000), "", "android.widget.LinearLayout", "", 4, 1000).click();
//   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 20://open fly mode
//   	        		CM.findObjectByRid("android:id/checkbox", 1000).click();
//   	        		CM.hold(3000);
   	        		CM.waitForId("android:id/checkbox", 1000);
   	        		break;
   	        	case 21://last part
   	        		CM.waitForId("android:id/checkbox", 1000);
//   	        		CM.findObjectByRid("android:id/checkbox", 1000).click();
//   	        		CM.hold(3000);
//   	        		CM.scroll(ud, CM.UP);
//   	        		CM.hold(1000);
   	        		break;
   	        	case 22:// Safety setting
   	        		ud.pressBack();
//   	        		CM.waitForId("com.android.settings:id/pager", 2000);
   	        		CM.findChildbyAtrribut(CM.findObjectByRid("android:id/list", 1000), "", "android.widget.LinearLayout", "",7, 1000).click();
   	        		CM.waitForId("android:id/checkbox", 1000);
//   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 23://each setting       
   	        		CM.scroll(ud, CM.UP);//last part of safety setting
   	        		CM.hold(1000);
//   	        		UiObject sList=CM.findObjectByRid("android:id/list", 1000);
//   	        		for(int i=0;i<3;i++)
//   	        		{
//   	        			if(CM.findChildbyAtrribut(sList, "", "android.widget.LinearLayout", "", i, 1000).getChild(new UiSelector().index(0)).getClassName().contains("android.widget.RelativeLayout"))
//   	        			{
//   	        				CM.findChildbyAtrribut(sList, "", "android.widget.LinearLayout", "", i, 1000).click();
//   	        				CM.hold(2000);
//   	        				ud.takeScreenshot(new File(snapPath+"/Settings"+23+"_"+i+".png"));
//   	            			ud.pressBack();
//   	            			CM.hold(1500);
//   	        			}
//   	        		}
   	        		break;
   	        	case 24://   Notification center
   	        		ud.pressBack();
   	        		CM.findChildbyAtrribut(CM.findObjectByRid("android:id/list", 1000), "", "android.widget.LinearLayout", "", 8, 1000).click();
   	        		CM.waitForId("com.oppo.notification.center:id/notification_location", 1000);
   	        		break;
   	        	case 25://GPS location
   	        		ud.pressBack();
//   	        		CM.waitForId("com.android.settings:id/pager", 2000);
   	        		CM.findChildbyAtrribut(CM.findObjectByRid("android:id/list", 1000), "", "android.widget.LinearLayout", "", 9, 1000).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 26://account and synchronization
   	        		ud.pressBack();
   	        		CM.hold(2000);
   	        		CM.scroll(ud, CM.UP);
   	        		CM.hold(2000);
//   	        		CM.waitForId("com.android.settings:id/pager", 2000);
//   	        		CM.findChildbyAtrribut(CM.findObjectByRid("android:id/list", 1000), "", "android.widget.LinearLayout", "", 8, 1000).click();
//   	        		CM.hold(1000);
//   	        		break;
   	        	case 27://here is not right
//   	        		ud.pressBack();
//   	        		CM.waitForId("com.android.settings:id/pager", 2000);
//   	        		CM.scroll(ud, CM.UP);
//   	        		CM.hold(1000);
//   	        		CM.scroll(ud, CM.UP);
//   	        		CM.hold(1000);
//   	        		CM.scroll(ud, CM.UP);
//   	        		CM.hold(2000);
//   	        		UiObject HList=CM.findObjectByRid("android:id/list", 1000);
//   	        		int count=HList.getChildCount();
//   	        		for(int i=2;i<count;i++)
//   	        		{
//   	        			UiObject item=CM.findChildbyAtrribut(HList, "", "android.widget.LinearLayout", "", i, 1000);
//   	        			if(item.getChild(new UiSelector().index(0)).getClassName().contains("android.widget.ImageView"))
//   	        			{
//   	        				item.click();
//   	        				CM.hold(2000);
//   	        				if(CM.waitForId("com.android.settings:id/storage_overview", 100)
//   	        						||CM.waitForId("com.android.settings:id/app_on_sdcard", 100)
//   	        						||CM.waitForId("com.android.settings:id/inputmethod_settings", 100)
//   	        						||CM.waitForId("com.oppo.powermanager:id/progress_ring_view", 100)
//   	        						||CM.waitForId("com.android.settings:id/icon_large", 100))
//   	        				{
//   	        					ud.takeScreenshot(new File(snapPath+"/Settings"+27+"_"+i+".png"));
//   	        					ud.pressBack();
//   	        					CM.hold(2000);
//   	        				}else if(CM.waitForId("android:id/checkbox", 1000))
//   	        				{
//   	        					CM.findObjectByRid("android:id/checkbox", 1000).click();
//   	        					CM.hold(2000);
//   	        					CM.findObjectByRid("android:id/checkbox", 1000).click();
//   	        					CM.hold(3000);
//   	        					ud.takeScreenshot(new File(snapPath+"/Settings"+27+"_"+i+".png"));
//   	        					ud.pressBack();
//   	        					CM.hold(2000);
//   	        				}
//   	        				else
//   	        				{
//   	        					ud.takeScreenshot(new File(snapPath+"/Settings"+27+"_"+i+".png"));
//   	        	        		UiObject tList=CM.findObjectByRid("android:id/list", 1000);
//   	        	        		for(int j=0;j<tList.getChildCount();j++)
//   	        	        		{
//   	        	        			if(i==count-1)
//   	        	        				break;
//   	        	        			UiObject sitem=CM.findChildbyAtrribut(tList, "", "android.widget.LinearLayout", "", j, 1000);
//   	        	        			if(!sitem.getChild(new UiSelector().index(0)).getClassName().contains("android.widget.TextView"))
//   	        	        			{
//   	        	        				sitem.click();
//   	        	        				CM.hold(2000);
//   	        	        				ud.takeScreenshot(new File(snapPath+"/Settings"+27+"_"+i+"_"+j+".png"));
//   	        	        				ud.pressBack();
//   	        	        				CM.hold(2000);
//   	        	        			}
//   	        	        		}
//   	        	        		ud.pressBack();
//   	        	        		CM.hold(2000);
//   	        				}
//   	        			}
//   	        		}
//   	        		ud.pressBack();
   	        	default:
   	        		break;
   	        	}
   	        }

   	    }
   	    public static class SafeDemo
   	    {
   	    	public static void test(UiDevice ud,int step) throws Exception
   	        {
   	        	switch(step)
   	        	{
   	        	case 1://enter monitor
   	        		CM.waitForNewApp("com.oppo.safe", 2000);
   	        		break;
   	        	case 2://memory cleanup
   	        		CM.findObjectByRid("com.oppo.safe:id/button_accelerate", 1000).click();
   	        		CM.waitForId("com.oppo.oppomemoryclear:id/center", 2000);
   	        		break;
   	        	case 3://timing clean
   	        	    CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(1).resourceId("oppo:id/oppo_preference")).click();
   	        	    CM.waitForId("android:id/button2", 1000);
   	        	    break;
   	        	case 4://white list
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(3).resourceId("oppo:id/oppo_preference")).click();
   	        		CM.waitForId("com.android.settings:id/option_menu_bar", 1000);
   	        		break;
   	        	case 5://application permission
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.safe:id/securesafe_not_cmcc_layout", 1000);
   	        		CM.findObjectByRid("com.oppo.safe:id/button_permission", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 6://encryption
   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(1).resourceId("oppo:id/oppo_preference")).click();
   	        		CM.waitForId("com.oppo.safe:id/privacy_icon_center", 1000);
   	        		break;
   	        	case 7://encryption typ
   	        		CM.findObjectByClassname("com.oppo.widget.OppoSwitch", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 8://pop window
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.safe:id/shield", 1000);
   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(3).resourceId("oppo:id/oppo_preference")).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 9://other selections
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.safe:id/securesafe_not_cmcc_layout", 1000);
   	        		new UiScrollable(new UiSelector().resourceId("android:id/list")).scrollToEnd(2);
   	        		CM.hold(2000);
   	        		break;
   	        	case 10://data saving
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.safe:id/securesafe_not_cmcc_layout", 1000);
   	        		CM.findObjectByRid("com.oppo.safe:id/button_data", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 11://data details
   	        		CM.findObjectByRid("com.oppo.trafficmonitor:id/change_to_traffic_stats_view", 1000).click();
   	        		CM.waitForId("android:id/tabhost", 1000);
   	        		break;
   	        	case 12://menu selections
   	        		ud.pressMenu();
   	        		CM.hold(2000);
   	        		break;
   	        	case 13://connection control
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		CM.findObjectByRid("com.oppo.trafficmonitor:id/change_to_traffic_control_view", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 14://new installation default setting
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/checkbox", 1000);
   	        		break;
   	        	case 15://save setting
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.trafficmonitor:id/change_to_traffic_control_view", 1000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/checkbox", 1000);
   	        		break;
   	        	case 16://month package
   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(1).resourceId("oppo:id/oppo_preference")).click();
   	        		CM.waitForId("android:id/button2", 1000);
   	        		break;
   	        	case 17://settling day
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(2).resourceId("oppo:id/oppo_preference")).click();
   	        		CM.waitForId("android:id/button2", 1000);
   	        		break;
   	        	case 18://calibration
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(3).resourceId("oppo:id/oppo_preference")).click();
   	        		CM.waitForId("android:id/button2", 1000);
   	        		break;
   	        	case 19://reminder
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(5).resourceId("oppo:id/oppo_preference")).click();
   	        		CM.hold(2000);
   	        		break;
   	        	case 20://power manager
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.safe:id/securesafe_not_cmcc_layout", 1000);
   	        		CM.findObjectByRid("com.oppo.safe:id/button_power", 1000).click();
   	        		CM.waitForId("android:id/checkbox", 2000);
   	        		break;
   	        	case 21://other selections
   	        		new UiScrollable(new UiSelector().resourceId("android:id/list")).scrollToEnd(2);
   	        		new UiScrollable(new UiSelector().resourceId("android:id/list")).scrollToEnd(2);
   	        		
   	        		CM.hold(2000);
   	        		break;
   	        	case 22://power details
   	        		new UiScrollable(new UiSelector().resourceId("android:id/list")).scrollToEnd(2);
   	        		new UiScrollable(new UiSelector().resourceId("android:id/list")).scrollToEnd(2);
   	        		CM.hold(2000);
   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(3).resourceId("oppo:id/oppo_preference")).click();
   	        		CM.waitForId("android:id/action_bar_title", 1000);
   	        		break;
   	        	case 23://harass
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.safe:id/securesafe_not_cmcc_layout", 1000);
   	        		CM.findObjectByRid("com.oppo.safe:id/button_harass", 1000).click();
   	        		CM.waitForId("com.oppo.blacklist:id/pagertabstrip", 2000);
   	        		break;
   	        	case 24://harass setting         
   	        		CM.findObjectByRid("com.oppo.blacklist:id/pagertabstrip", 1000).getChild(new UiSelector().index(2)).click();
//   	        		CM.findObjectByRid("com.oppo.blacklist:id/pagertabstrip", 1000).getChild(new UiSelector().index(2)).click();
   	        		CM.waitForId("android:id/checkbox", 2000);
   	        		break;
   	        	case 25://quiet mode      Quiet Time
   	        		ud.pressBack();
   	        		CM.findObjectByRid("com.oppo.safe:id/button_holiday", 1000).click();
   	        		CM.waitForId("android:id/action_bar_title", 1000);
//   	        		ud.pressBack();
//   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(8).resourceId("oppo:id/oppo_preference")).click();
//   	        		CM.waitForId("com.oppo.holidaymode:id/main_icon", 2000);
   	        		break;
   	        	case 26://visitor          when calls and notifications arrive
   	        		CM.findObjectByRid("android:id/summary", 1000).click();
   	        		CM.waitForId("android:id/text1", 1000);
//   	        		ud.pressBack();
//   	        		ud.pressBack();
//   	        		CM.waitForId("com.oppo.safe:id/securesafe_not_cmcc_layout", 1000);
//   	        		CM.findObjectByRid("com.oppo.safe:id/button_vistor", 1000).click();
//   	        		CM.waitForClass("com.oppo.widget.OppoSwitch", 2000);
   	        		break;
   	        	case 27://visitor lock
//   	        		CM.findObjectByRid("android:id/action_bar", 1000).getChild(new UiSelector().className("com.oppo.widget.OppoSwitch")).click();
//   	        		CM.waitForId("android:id/button1", 2000);
   	        		break;
   	        	case 28://change lock
//   	        		CM.findObjectByRid("android:id/button1", 1000).click();
//   	        		CM.waitForId("android:id/up", 1000);
   	        		break;
   	        	case 29://secret contacts
//   	        		ud.pressBack();
//   	        		CM.waitForClass("com.oppo.widget.OppoSwitch", 1000);
//   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(0).resourceId("oppo:id/oppo_preference")).click();
//   	        		CM.waitForId("oppo:id/MajorOperationButtonPositive", 2000);
   	        		break;
   	        	case 30://secret photos
//   	        		ud.pressBack();
//   	        		CM.waitForClass("com.oppo.widget.OppoSwitch", 1000);
//   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(1).resourceId("oppo:id/oppo_preference")).click();
//   	        		CM.waitForId("oppo:id/MajorOperationButtonPositive", 2000);
   	        		break;
   	        	case 31://secret videos
//   	        		ud.pressBack();
//   	        		CM.waitForClass("com.oppo.widget.OppoSwitch", 1000);
//   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(2).resourceId("oppo:id/oppo_preference")).click();
//   	        		CM.waitForId("oppo:id/MajorOperationButtonPositive", 2000);
   	        		break;
   	        	case 32://hidden application
//   	        		ud.pressBack();
//   	        		CM.waitForClass("com.oppo.widget.OppoSwitch", 1000);
//   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(3).resourceId("oppo:id/oppo_preference")).click();
//   	        		CM.waitForId("android:id/checkbox", 2000);
   	        		break;
   	        	case 33://over
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		break;
   	        	default:
   	        		break;
   	        	}
   	        }
   	    }
   	    public static class RecorderDemo
   	    {
   	    	public static void test(UiDevice ud,int step) throws Exception
   	        {
   	        	switch(step)
   	        	{
   	        	case 1://enter recorder
   	        		CM.waitForNewApp("oppo.multimedia.soundrecorder", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/up", 1000);
   	        		if(CM.waitForId("oppo:id/emptybottle", 1000))
   	        		{
   	        			ud.pressBack();
   	        			break;
   	        		}
   	        		else
   	        		{
   	        			CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	            		CM.waitForId("oppo.multimedia.soundrecorder:id/item_cancel_all", 1000);
   	            		CM.findObjectByRid("oppo.multimedia.soundrecorder:id/item_cancel_all", 1000).click();
   	            		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	            		CM.waitForId("android:id/button2", 2000);
   	            		CM.findObjectByRid("android:id/button1", 1000).click();
   	            		CM.waitForId("android:id/up", 2000);
   	            		ud.pressBack();
   	            		CM.waitForId("oppo.multimedia.soundrecorder:id/recorder_panel", 2000);
   	        		}
   	        		break;
   	        	case 2://start record
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		CM.hold(5000);
   	        		break;
   	        	case 3://pause
//   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		ud.click(ud.getDisplayWidth()/2, ud.getDisplayHeight()*9/10);
   	        		CM.hold(1000);
   	        		break;
   	        	case 4://stop
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 5://menu options
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		break;
   	        	case 6://timing record
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 7://settings
   	        		ud.pressBack();
   	        		CM.waitForId("oppo.multimedia.soundrecorder:id/middle_image", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 8://default name
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{0,0}).click();
   	        		CM.waitForId("android:id/button1", 2000); 
   	        		break;
   	        	case 9://file format
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.waitForId("android:id/list", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{1,0}).click();
   	        		CM.waitForId("android:id/button2", 2000); 
   	        		break;
   	        	case 10://storage position
//   	        		CM.findObjectByRid("android:id/button2", 1000).click();
//   	        		CM.waitForId("android:id/list", 2000);
//   	        		CM.findChildByPosistion(CM.findObjectByRid("android:id/list", 1000), new int[]{2,0}).click();
//   	        		CM.waitForId("android:id/button2", 2000); 
   	        		break;
   	        	case 11://recordings list
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("android:id/list", 2000);
   	        		ud.pressBack();
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 12://edit
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("oppo.multimedia.soundrecorder:id/item_cancel_all", 2000);
   	        		break;
   	        	case 13://more edit options
   	        		CM.findObjectByRid("oppo.multimedia.soundrecorder:id/item_cancel_all", 1000).click();
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		break;
   	        	case 14://set as
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
   	        		CM.waitForId("com.oppo.music:id/ck_whole_track", 2000);
   	        		break;
   	        	case 15://rename
   	        		ud.pressBack();
   	        		CM.waitForId("oppo.multimedia.soundrecorder:id/item_cancel_all", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView2", 1000).click();
   	        		CM.waitForId("oppo:id/oppo_rename_dialog_edit", 2000);
   	        		break;
   	        	case 16://details
   	        		CM.setText(ud, CM.findObjectByRid("oppo:id/oppo_rename_dialog_edit", 1000), "rename");
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("oppo.multimedia.soundrecorder:id/item_cancel_all", 2000);
   	        		CM.findObjectByRid("oppo.multimedia.soundrecorder:id/item_cancel_all", 1000).click();
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView3", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);
   	        		break;
   	        	case 17://share
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		CM.waitForId("android:id/alertTitle", 2000);
   	        		break;
   	        	case 18://delete
   	        		ud.pressBack();
   	        		CM.waitForId("oppo.multimedia.soundrecorder:id/item_cancel_all", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);
   	        		break;
   	        	case 19://additional recording
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.waitForId("oppo.multimedia.soundrecorder:id/item_cancel_all", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
   	        		CM.waitForId("oppo.multimedia.soundrecorder:id/middle_image", 2000);
   	        		CM.hold(10000);
   	        		break;
   	        	case 20://play recording
   	        		ud.click(ud.getDisplayWidth()/2, ud.getDisplayHeight()*9/10);
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.hold(2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/up", 2000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("oppo.multimedia.soundrecorder:id/browser_list", 1000), new int[]{0,0}).click();
   	        		CM.hold(5000);
   	        		CM.findChildByPosistion(CM.findObjectByRid("oppo.multimedia.soundrecorder:id/browser_list", 1000), new int[]{0,0}).click();
   	        		CM.hold(2000);
   	        		break;
   	        	case 21://delete
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		if(CM.waitForId("oppo.multimedia.soundrecorder:id/item_cancel_all", 1000))
   	        		{
   	            		CM.findObjectByRid("oppo.multimedia.soundrecorder:id/item_cancel_all", 1000).click();
   	            		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		}
   	        		CM.waitForId("android:id/button1", 2000);
   	        		break;
   	        	case 22://over
   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		CM.waitForId("oppo:id/MajorOperationButtonPositive", 2000);
   	        		ud.pressBack();
   	        		CM.waitForId("oppo.multimedia.soundrecorder:id/middle_image", 2000);
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.launcher:id/all_apps_2d_grid", 2000);
   	        		break;
   	        	default:
   	        		break;
   	        	}
   	        }
   	    }
   	    public static class DesktopDemo
   	    {
   	    	public static void test(UiDevice ud,int step) throws Exception
   	        {
   	        	switch(step)
   	        	{
   	        	case 1://enter desktop
   	        		CM.waitForId("android.appwidget.AppWidgetHostView", 1000);
   	        		break;
   	        	case 2:
   	        		CM.scroll(ud, CM.LEFT);
   	        		CM.hold(1000);
   	        		break;
   	        	case 3:
   	        		CM.scroll(ud, CM.RIGHT);
   	        		CM.scroll(ud, CM.RIGHT);
   	        		CM.hold(1000);
   	        		break;
   	        	case 4://move icon
//   	        		CM.scroll(ud, CM.LEFT);
   	        		ud.pressHome();
   	        		CM.hold(1000);
   	        		Rect r=CM.findObjectByRid("com.oppo.launcher:id/dockbar", 1000).getBounds();
   	        		ud.drag(r.width()*3/8, r.centerY(), r.width()*3/4, r.centerY(), 5);
   	        		CM.hold(2000);  
   	        		break;
   	        	case 5://delete widget
//   	        		r=CM.findObjectByRid("com.oppo.launcher:id/dockbar", 1000).getBounds();
//   	        		ud.drag(r.width()*3/8, r.centerY(), r.width()*3/4, r.centerY(), 5);
//   	        		CM.hold(2000);
//   	        		CM.findObjectByRid("com.oppo.launcher:id/dockbar", 1000).clickTopLeft();
   	        		CM.hold(1000);
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("com.oppo.launcher:id/dockbar", 1000);
   	        		break;
   	        	case 6://menu options
   	        		ud.pressMenu();
   	        		CM.waitForId("com.oppo.launcher:id/tog_title", 2000);
   	        		break;
   	        	case 7://add and enter widget  
   	        		CM.scroll(ud, CM.LEFT);
   	        		CM.scroll(ud, CM.LEFT);
   	        		CM.findObjectByRid("com.oppo.launcher:id/tog_title_0", 1000).click();
   	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.launcher:id/toggle_bar", 1000), new int[]{2,0}).click();
//   	        		if(!CM.waitForId("com.oppo.widget.digitalclock:id/digital_clock_main_frame", 1000))
//   	        			CM.findChildByPosistion(CM.findObjectByRid("com.oppo.launcher:id/toggle_bar", 1000), new int[]{2,0}).click();
   	        		CM.hold(1000);
   	        		ud.pressBack();
   	        		CM.hold(1000);
//   	        		if(CM.waitForId("com.oppo.widget.digitalclock:id/analog_clock", 1000))
//   	        		{
//   	    	    		CM.findObjectByRid("com.oppo.widget.digitalclock:id/analog_clock", 1000).click();
//   	    	    		CM.waitForId("com.oppo.alarmclock:id/main_fram", 2000);
//   	    	    		ud.pressBack();
//   	    	    		CM.waitForNewApp("com.oppo.launcher", 2000);
//   	        		}
   	        		break;
   	        	case 8://Select wallpaper
   	        		ud.pressMenu();
   	        		CM.waitForId("com.oppo.launcher:id/tog_title", 1000);
   	        		CM.findObjectByRid("com.oppo.launcher:id/tog_title_1", 1000).click();
   	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.launcher:id/toggle_bar", 1000), new int[]{0,0}).click();
   	        		CM.waitForId("android:id/action_bar_title", 2000);
   	        		break;
   	        	case 9://static wall paper
   	        		Rect wR=CM.findObjectByRid("android:id/content", 1000).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(0)).getBounds();
   	        		ud.click(wR.width()/6, wR.centerY());
   	        		CM.findObjectByRid("com.nearme.themespace:id/tab_title", 1000).getChild(new UiSelector().index(4)).click();
   	        		
   	        		CM.waitForId("com.nearme.themespace:id/local_picture_lib", 2000);
   	        		break;
   	        	case 10://album wall paper
   	        		ud.pressBack();
//   	        		CM.waitForId("android:id/alertTitle", 2000);
//   	        		wR=CM.findObjectByRid("android:id/content", 1000).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(0)).getBounds();
//   	        		ud.click(wR.width()*5/6, wR.centerY());
//   	        		CM.waitForId("android:id/up", 2000);
   	        		break;
   	        	case 11://live wall papers
   	 //       		ud.pressBack();
   	        		CM.waitForId("android:id/action_bar_title", 2000);
   	        		wR=CM.findObjectByRid("android:id/content", 1000).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(0)).getBounds();
   	        		ud.click(wR.width()/2, wR.centerY());
   	        		CM.waitForId("com.android.wallpaper.livepicker:id/themeGrid", 2000);
   	        		break;
   	        	case 12://photos wall papers
   	        		ud.pressBack();
   	        		CM.waitForId("android:id/action_bar_title", 1000);
   	        		wR=CM.findObjectByRid("android:id/content", 1000).getChild(new UiSelector().index(0)).getChild(new UiSelector().index(0)).getBounds();
   	        		ud.click(wR.width()*5/6, wR.centerY());
   	        		
   	        		CM.hold(1000);
   	        		break;
   	        	case 13://add music space
//   	        		ud.pressMenu();
//   	        		CM.waitForId("com.oppo.launcher:id/tog_title", 2000);
//   	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.launcher:id/toggle_bar", 1000), new int[]{1,0}).click();
//   	        		CM.hold(1000);
//   	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.launcher:id/toggle_bar", 1000), new int[]{1,0}).click();
//   	        		CM.hold(1000);
//   	        		CM.findObjectByRid("com.oppo.launcher:id/tog_title_1", 1000).click();
//   	        		if(CM.waitForId("com.oppo.launcher:id/tog_title_olab_normal", 500))
//   	        			CM.findObjectByRid("com.oppo.launcher:id/tog_title_olab_normal", 500).click();
//   	        		else
//   	        		    CM.findObjectByRid("com.oppo.launcher:id/tog_title_olab", 1000).click();
//   	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.launcher:id/toggle_bar", 1000), new int[]{0,0}).click();
//   	        		CM.hold(1000);
//   	        		CM.findChildByPosistion(CM.findObjectByRid("com.oppo.launcher:id/toggle_bar", 1000), new int[]{1,0}).click();
//   	        		CM.hold(1000);
//   	        		ud.pressBack();
//   	        		CM.hold(1000);
//   	        		CM.scroll(ud, CM.RIGHT);
//   	        		CM.hold(1000);
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		ud.pressHome();
   	        		ud.pressMenu();
   	        		CM.waitForId("com.oppo.launcher:id/tog_title_olab_normal", 1000);
   	        		CM.findObjectByRid("com.oppo.launcher:id/tog_title_olab_normal", 1000).click();
   	        		CM.hold(3000);
   	        		break;
   	        	case 14://recent task
   	        		ud.pressBack();
   	        		ud.pressHome();
   	        		CM.waitForId("com.oppo.launcher:id/dockbar", 2000);
   	        		ud.pressRecentApps();
   	        		CM.waitForId("com.oppo.recents:id/app_clear_anim", 2000);
   	        		break;
   	        	case 15://clear recent
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.launcher:id/dockbar", 2000);
   	        		break;
   	        	default:
   	        		break;    			
   	        	}
   	        }
   	    }
   	    public static class AlbumDemo
   	    {
   	    	public static void test(UiDevice ud,int step) throws Exception
   	        {
   	        	switch(step)
   	        	{
   	        	case 1://enter album
   	        		CM.waitForId("android:id/action_bar_overlay_layout", 5000);
   	        		break;
   	        	case 2://capture 5
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForNewApp("com.oppo.camera", 3000);
   	        		for(int i=0;i<5;i++)
   	        		{
   	        			CM.findObjectByRid("com.oppo.camera:id/shutter_button", 1000).click();
   	        			CM.hold(3000);
   	        		}
   	        		break;
   	        	case 3://thumbnail
   	        		CM.findObjectByRid("com.oppo.camera:id/thumbnail", 1000).click();
   	        		CM.hold(4000);
   	        		break;
   	        	case 4://back album
   	        		ud.pressBack();
   	        		CM.waitForNewApp("com.oppo.camera", 5000);
   	        		ud.pressBack();
   	        		CM.hold(3000);
   	        		break;
   	        	case 5://more options
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.hold(2000);
   	        		break;
   	        	case 6://edit
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
   	        		CM.hold(2000);
   	        		CM.findObjectByRid("com.oppo.gallery3d:id/action_select_all", 1000).click();
   	        		CM.hold(2000);
   	        		break;
   	        	case 7://delete
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
   	        		CM.waitForId("android:id/button2", 2000);
   	        		break;
   	        	case 8://set as
//   	        		CM.findObjectByRid("android:id/button2", 1000).click();
//   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
//   	        		CM.waitForId("android:id/button2", 2000);
   	        		break;
   	        	case 9://share
   	        		CM.findObjectByRid("android:id/button2", 1000).click();
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNeutral", 1000).click();
   	        		CM.waitForId("android:id/button2", 2000);
   	        		break;
   	        	case 10://album
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.gallery3d:id/action_select_all", 1000);
   	        		ud.pressBack();
   	        		CM.hold(2000);
   	        		break;
   	        	case 11://cloud
   	        		break;
   	        	case 12://combine
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
   	        		CM.waitForId("com.oppo.gallery3d:id/together_submit", 2000);
   	        		break;
   	        	case 13://choose picture
   	        		Rect gR=CM.findObjectByRid("com.oppo.gallery3d:id/gl_root_view", 1000).getBounds();
   	        		ud.click(gR.width()/8, gR.height()*3/10);
   	        		CM.hold(2000);
   	        		ud.click(gR.width()/8, gR.height()*3/10);
   	        		CM.hold(1000);
   	        		ud.click(gR.width()*3/8, gR.height()*3/10);
   	        		CM.hold(1000);
   	        		break;
   	        	case 14://submit combine
   	        		CM.findObjectByRid("com.oppo.gallery3d:id/together_submit", 1000).click();
   	        		CM.waitForId("com.oppo.gallery3d:id/template_button", 5000);
   	        		break;
   	        	case 15://save combine
   	        		CM.findObjectByRid("com.oppo.gallery3d:id/together_btn_save", 1000).click();
   	        		CM.waitForId("com.oppo.gallery3d:id/together_btn_share", 5000);
   	        		break;
   	        	case 16://share combine
   	        		CM.findObjectByRid("com.oppo.gallery3d:id/together_btn_share", 1000).click();
   	        		CM.waitForId("android:id/resolver_grid", 2000);
   	        		break;
   	        	case 17://play slide
//   	        		CM.findObjectByRid("android:id/button1", 1000).click();
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.gallery3d:id/together_btn_pre", 2000);
   	        		CM.findObjectByRid("com.oppo.gallery3d:id/together_btn_pre", 1000).click();
   	        		CM.waitForId("com.oppo.gallery3d:id/together_submit", 1000);
   	        		CM.findObjectByRid("android:id/up", 1000).click();
   	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 2000);
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView2", 1000).click();
   	        		CM.hold(2000);
   	        		break;
   	        	case 18://picture view
   	        		ud.pressBack();
   	        		CM.hold(2000);
   	        		ud.click(ud.getDisplayWidth()/8, ud.getDisplayHeight()*5/18);
   	        		CM.hold(3000);
   	        		break;
   	        	case 19://edit
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
   	        		CM.waitForId("com.oppo.gallery3d:id/undo_button", 2000);
   	        		break;
   	        	case 20://picture exposure
   	        		CM.findObjectByRid("com.oppo.gallery3d.photoeditor:id/filter_button", 1000).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 21://picture artistic
   	        		CM.findObjectByRid("com.oppo.gallery3d.photoeditor:id/frame_button", 1000).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 22://picture color
   	        		CM.findObjectByRid("com.oppo.gallery3d.photoeditor:id/artist_button", 1000).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 23://picture fix
   	        		CM.findObjectByRid("com.oppo.gallery3d.photoeditor:id/fix_button", 1000).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 24://details
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView2", 1000).click();
   	        		CM.waitForId("android:id/button1", 2000);
   	        		break;
   	        	case 25://all picture
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.gallery3d:id/gl_root_view", 2000);
   	        		ud.pressBack();
   	        		CM.waitForClass("android.widget.HorizontalScrollView", 2000);
   	        		CM.findObjectByMutiAttribut("", "android.app.ActionBar$Tab", "", 1, 1000).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 26://hide folder
   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView2", 1000).click();
   	        		CM.waitForId("com.oppo.gallery3d:id/action_select_all", 2000);
   	        		break;
   	        	case 27://cloud
   	        		ud.pressBack();
   	        		CM.hold(1000);
   	        		break;
   	        	case 28://quit
   	        		ud.pressBack();
   	        		break;
   	        	default:
   	        		break;    			
   	        	}
   	        }
   	    }
   	    public static class CameraDemo
   	    {
   	    	public static void test(UiDevice ud,int step) throws Exception
   	        {
   	        	switch(step)
   	        	{
   	        	case 1://enter camera
   	        		CM.waitForId("com.oppo.camera:id/camera", 5000);
   	        		while(CM.waitForId("com.oppo.camera:id/guide_view", 1000))
   	        		{
   	        		    CM.scroll(ud, CM.UP);
   	        		}
   	        		break;
   	        	case 2://capture
   	        		for(int i=0;i<5;i++)
   	        		{
   	        		    CM.findObjectByRid("com.oppo.camera:id/shutter_button", 1000).click();
   	        		    CM.hold(3000);
   	        		}
   	        		break;
   	        	case 3://thumbnail
   	        		CM.findObjectByRid("com.oppo.camera:id/thumbnail", 1000).click();
   	        		CM.hold(3000);
   	        		break;
   	        	case 4://record
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.camera:id/thumbnail", 2000);
   	        		CM.findObjectByRid("com.oppo.camera:id/oppo_video_lab", 1000).click();
   	        		Rect bR=CM.findObjectByRid("com.oppo.camera:id/shutter_button", 1000).getBounds();
   	        		for(int i=0;i<5;i++)
   	        		{
   	        			ud.click(bR.centerX(), bR.centerY());
   	        		    CM.hold(5000);
   	        		    ud.click(bR.centerX(), bR.centerY());
   	        		    CM.hold(3000);
   	        		}
   	        		CM.findObjectByRid("com.oppo.camera:id/thumbnail", 1000).click();
   	        		CM.hold(3000);
   	        		break;
   	        	case 5://switch camera
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.camera:id/thumbnail", 2000);
   	        		if(CM.waitForId("com.oppo.camera:id/oppo_setting_bar", 1000))
   	        		{
   	        			CM.findObjectByRid("com.oppo.camera:id/oppo_setting_bar", 1000).getChild(new UiSelector().index(1)).click();
   	        			CM.hold(3000);
   	        		}
   	        		bR=CM.findObjectByRid("com.oppo.camera:id/shutter_button", 1000).getBounds();
   	        		for(int i=0;i<2;i++)
   	        		{
   	        		    ud.click(bR.centerX(), bR.centerY());
   	        		    CM.hold(5000);
   	        		    ud.click(bR.centerX(), bR.centerY());
   	        		    CM.hold(3000);
   	        		}
   	        		if(CM.waitForId("com.oppo.camera:id/oppo_setting_bar", 1000))
   	        		{
   	        			CM.findObjectByRid("com.oppo.camera:id/oppo_setting_bar", 1000).getChild(new UiSelector().index(1)).click();
   	        			CM.hold(3000);
   	        		}
   	        		CM.findObjectByRid("com.oppo.camera:id/thumbnail", 1000).click();
   	        		CM.hold(3000);
   	        		break;
   	        	case 6://flash
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.camera:id/thumbnail", 2000);
   	        		CM.findObjectByRid("com.oppo.camera:id/oppo_setting_bar", 1000).getChild(new UiSelector().index(0)).click();
   	        		CM.hold(2000);
   	        		break;
   	        	case 7://widget
   	        		ud.click(ud.getDisplayWidth()/2, ud.getDisplayHeight()/2);
   	        		CM.findObjectByRid("com.oppo.camera:id/plug_popup_button", 1000).click();
   	        		CM.waitForId("com.oppo.camera:id/camera_mode_title", 2000);
   	        		break;
   	        	case 8://camera market
   	        		CM.findObjectByRid("com.oppo.camera:id/camera_mode_title", 1000).click();
   	        		CM.waitForId("com.oppo.camera:id/plugin_playstore_back_icon", 1000);
   	        		break;
   	        	case 9://setting
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.camera:id/camera_mode_title", 2000);
   	        		CM.findObjectByRid("com.oppo.camera:id/camera_mode_menu_layout", 1000).getChild(new UiSelector().index(4).className("android.widget.RelativeLayout")).click();
   	        		CM.waitForId("com.oppo.camera:id/camera_setting_menu_root", 2000);
   	        		break;
   	        	case 10://the remaining part
   	        		CM.scroll(ud, CM.UP);
   	        		CM.hold(1000);
   	        		break;
   	        	case 11://the other side options
   	        		ud.pressBack();
   	        		ud.pressBack();
   	        		CM.waitForId("com.oppo.camera:id/thumbnail", 2000);
   	        		break;
   	        	case 12://record and pause
   	        		bR=CM.findObjectByRid("com.oppo.camera:id/shutter_button", 1000).getBounds();
   	        		Rect sR=CM.findObjectByRid("com.oppo.camera:id/plug_popup_button", 1000).getBounds();
   	        		ud.click(bR.centerX(), bR.centerY());
   	    		    CM.hold(5000);
   	    		    ud.click(sR.centerX(), sR.centerY());
   	    		    CM.hold(3000);
   	    		    ud.click(bR.centerX(), bR.centerY());
   	    		    CM.hold(3000);
   	    		    break;
   	        	case 13://capture by volume key
   	        		ud.pressKeyCode(24);
   	        		CM.hold(3000);
   	        		ud.pressKeyCode(25);
   	        		CM.hold(3000);
   	        		break;
   	        	default:
   	        		break;    			
   	        	}
   	        }
   	    }
   	    public static class NotificationDemo
   	    {
   	    	public static void test(UiDevice ud,int step) throws Exception
   	        {
   	        	switch(step)
   	        	{
   	        	case 1://open notification
   	        		CM.waitForNewApp("com.android.systemui", 5000);
   	        		Rect r=CM.findObjectByRid("com.android.systemui:id/handle_arrow", 1000).getBounds();
   	        		CM.scroll(ud, CM.DOWN);
   	        		CM.hold(2000);
   	        		CM.scroll(ud, CM.DOWN);
//   	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
//   	        		{
//   	        			
//   	        			ud.drag(r.centerX(), r.centerY(), r.centerX(), ud.getDisplayHeight()/2, 4);
//   	        		    CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000);
//   	        		}
   	        		CM.waitForId("com.android.systemui:id/qstv_15", 2000);
   	        		break;
   	        	case 2://打开关闭蓝牙
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_1", 1000).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 3:
   	        		if(CM.waitForId("android:id/button1", 1000))
   	        		{
   	        			CM.findObjectByRid("android:id/button1", 1000).click();
   	        			CM.hold(1000);
   	        		}
   	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
   	            	{
   	        			CM.scroll(ud, CM.DOWN);
   	            	}
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_1", 1000).click();
   	        		CM.hold(2000);
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_2", 1000).click();
   	        		CM.hold(2000);
   	        		break;
   	        	case 4:
   	        		if(CM.waitForId("android:id/button1", 1000))
   	        		{
   	        			CM.findObjectByRid("android:id/button1", 1000).click();
   	        			CM.hold(1000);
   	        		}
   	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
   	            	{
   	        			CM.scroll(ud, CM.DOWN);
   	            	}
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_2", 1000).click();
   	        		CM.hold(2000);
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_3", 1000).click();
   	        		CM.hold(4000);
   	        		break;
   	        	case 5:
   	        		if(CM.waitForId("android:id/button1", 1000))
   	        		{
   	        			CM.findObjectByRid("android:id/button1", 1000).click();
   	        			CM.hold(1000);
   	        		}
   	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
   	            	{
   	        			CM.scroll(ud, CM.DOWN);
   	            	}
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_3", 1000).click();
   	        		CM.hold(2000);
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_4", 1000).click();
   	        		CM.hold(4000);
//   	         		if(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 1000))
//   	        		{
//   	        			ud.drag(ud.getDisplayWidth()/2, 5, ud.getDisplayWidth()/2, ud.getDisplayHeight()/2, 4);
//   	        			if(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
//   	        			{
//   	        			    r=CM.findObjectByRid("com.android.systemui:id/handle_arrow", 1000).getBounds();
//   	            		    ud.drag(r.centerX(), r.centerY(), r.centerX(), ud.getDisplayHeight()/2, 4);
//   	            		    CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000);
//   	        			}
//   	        		}
//   	       		CM.findObjectByRid("com.android.systemui:id/shortcuts_linear2", 1000).getChild(new UiSelector().index(0)).click();
//   	       		CM.hold(5000);
//   	        		if(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 1000))
//   	        		{
//   	        			ud.drag(ud.getDisplayWidth()/2, 5, ud.getDisplayWidth()/2, ud.getDisplayHeight()/2, 4);
//   	        			if(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
//   	        			{
//   	        			    r=CM.findObjectByRid("com.android.systemui:id/handle_arrow", 1000).getBounds();
//   	            		    ud.drag(r.centerX(), r.centerY(), r.centerX(), ud.getDisplayHeight()/2, 4);
//   	            		    CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000);
//   	        			}
//   	        		}
//   	        		CM.findObjectByRid("com.android.systemui:id/shortcuts_linear2", 1000).getChild(new UiSelector().index(1)).click();
//   	        		CM.hold(4000);
   	        		break;
   	        	case 6:
   	        		if(CM.waitForId("android:id/button1", 1000))
   	        		{
   	        			CM.findObjectByRid("android:id/button1", 1000).click();
   	        			CM.hold(1000);
   	        		}
   	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
   	            	{
   	        			CM.scroll(ud, CM.DOWN);
   	            	}
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_4", 1000).click();
   	        		CM.hold(4000);
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_6", 1000).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 7:
   	        		if(CM.waitForId("android:id/button1", 1000))
   	        		{
   	        			CM.findObjectByRid("android:id/button1", 1000).click();
   	        			CM.hold(1000);
   	        		}
   	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
   	            	{
   	        			CM.scroll(ud, CM.DOWN);
   	            	}
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_6", 1000).click();
   	        		CM.hold(4000);
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_7", 1000).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 8:
   	        		if(CM.waitForId("android:id/button1", 1000))
   	        		{
   	        			CM.findObjectByRid("android:id/button1", 1000).click();
   	        			CM.hold(1000);
   	        		}
   	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
   	            	{
   	        			CM.scroll(ud, CM.DOWN);
   	            	}
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_7", 1000).click();
   	        		CM.hold(4000);
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_8", 1000).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 9:
   	        		if(CM.waitForId("android:id/button1", 1000))
   	        		{
   	        			CM.findObjectByRid("android:id/button1", 1000).click();
   	        			CM.hold(1000);
   	        		}
   	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
   	            	{
   	        			CM.scroll(ud, CM.DOWN);
   	            	}
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_8", 1000).click();
   	        		CM.hold(4000);
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_9", 1000).click();
   	        		CM.hold(1000);
//   	        		if(CM.waitForId("android:id/title_template", 1000))
//   	        		{
//   	        			Rect cR=CM.findObjectByRid("android:id/title_template", 1000).getBounds();
//   	        			ud.click(cR.width()*3/4, cR.bottom-8);
//   	        			CM.hold(1000);
//   	        			if(CM.waitForId("android:id/button1", 1000))
//   	            		{
//   	            			CM.findObjectByRid("android:id/button1", 1000).click();
//   	            			CM.hold(1000);
//   	            		}
//   	        			ud.openNotification();
//   	        			CM.waitForId("com.android.systemui:id/handle_arrow", 1000);
//   	        			r=CM.findObjectByRid("com.android.systemui:id/handle_arrow", 1000).getBounds();
//   	            		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
//   	            		{
//   	            		    ud.drag(r.centerX(), r.centerY(), r.centerX(), ud.getDisplayHeight()/2, 4);
//   	            		    CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000);
//   	            		}
//   	        		}
//   	        		CM.findObjectByRid("com.android.systemui:id/shortcuts_linear2", 1000).getChild(new UiSelector().index(4)).click();
//   	        		CM.hold(1000);
//   	        		CM.findObjectByRid("com.android.systemui:id/shortcuts_linear1", 1000).getChild(new UiSelector().index(0)).click();
//   	        		CM.hold(1000);
   	        		break;
   	        	case 10:
   	        		if(CM.waitForId("android:id/button1", 1000))
   	        		{
   	        			CM.findObjectByRid("android:id/button1", 1000).click();
   	        			CM.hold(1000);
   	        		}
   	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
   	            	{
   	        			CM.scroll(ud, CM.DOWN);
   	            	}
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_9", 1000).click();
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_11", 1000).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 11:
   	        		if(CM.waitForId("android:id/button1", 1000))
   	        		{
   	        			CM.findObjectByRid("android:id/button1", 1000).click();
   	        			CM.hold(1000);
   	        		}
   	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
   	            	{
   	        			CM.scroll(ud, CM.DOWN);
   	            	}
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_11", 1000).click();
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_12", 1000).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 12:
   	        		if(CM.waitForId("android:id/button1", 1000))
   	        		{
   	        			CM.findObjectByRid("android:id/button1", 1000).click();
   	        			CM.hold(1000);
   	        		}
   	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
   	            	{
   	        			CM.scroll(ud, CM.DOWN);
   	            	}
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_12", 1000).click();
   	        		CM.hold(1000);
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_13", 1000).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 13:
   	        		if(CM.waitForId("android:id/button1", 1000))
   	        		{
   	        			CM.findObjectByRid("android:id/button1", 1000).click();
   	        			CM.hold(1000);
   	        		}
   	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
   	            	{
   	        			CM.scroll(ud, CM.DOWN);
   	            	}
   	        		CM.findObjectByRid("com.android.systemui:id/qstv_13", 1000).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 14://light adjust
//   	        		if(!CM.waitForId("com.android.systemui:id/seekbar", 1000))
//   	        			break;
   	        		CM.findObjectByRid("com.android.systemui:id/brightness_icon", 1000).click();
//   	        		r=CM.findObjectByRid("com.android.systemui:id/seekbar", 1000).getBounds();
//   	        		for(int i=0;i<10;i++)
//   	        		{
//   	        		    ud.drag(r.left+10, r.centerY(), r.right-10, r.centerY(), 5);
//   	        		    ud.drag(r.left-10, r.centerY(), r.right+10, r.centerY(), 5);
//   	        		}
   	        		
   	        		break;
   	        	case 15:
   	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
   	            	{
   	        			CM.scroll(ud, CM.DOWN);
   	            	}
   	        		CM.findObjectByRid("com.android.systemui:id/brightness_icon", 1000).click();
   	        		r=CM.findObjectByRid("com.android.systemui:id/slider", 1000).getBounds();
   	        		for(int i=0;i<10;i++)
   	        		{
//   	        		    ud.drag(r.left+10, r.centerY(), r.right-10, r.centerY(), 5);
//   	        		    ud.drag(r.left-10, r.centerY(), r.right+10, r.centerY(), 5);
   	        			ud.click(r.centerX()-20, r.centerY());
   	        			ud.click(r.centerX()+20, r.centerY());
   	        		}
   	        		break;
   	        	case 16://settings
   	        		while(!CM.waitForId("com.android.systemui:id/shortcuts_linear3", 2000))
   	            	{
   	        			CM.scroll(ud, CM.DOWN);
   	            	}
   	        		CM.findObjectByRid("com.android.systemui:id/oppo_settings_button", 1000).click();
   	        		CM.waitForNewApp("com.android.settings", 2000);
   	        		break;
   	        	case 17:
   	        		ud.pressBack();
   	        		ud.pressKeyCode(24);
   	        		CM.hold(300);
   	        		ud.pressKeyCode(24);
   	        		CM.hold(300);
   	        		ud.pressKeyCode(24);
   	        		CM.hold(3000);
   	        		break;
   	        	default:
   	        		break;    			
   	        	}
   	        }
   	    }
   	    public static class EmailDemo
   	    {
   	    	public static void test(UiDevice ud,int step) throws Exception
   	        {
   	        	switch(step)
   	        	{
   	        	case 1://enter email
   	        		CM.waitForId("com.android.email:id/bottom_btn_new", 8000);    		
   	        		break;
   	        	case 2://login
   	 //       		CM.waitForId("com.android.email:id/bottom_btn_new", 1000);    
//   	        		CM.enterText(ud, CM.findObjectByRid("com.android.email:id/account_email", 1000), "oppotext&@163.com");
//   	        		CM.enterText(ud, CM.findObjectByRid("com.android.email:id/account_password", 1000), "86076999");
//   	        		CM.findObjectByRid("com.android.email:id/btn_email_login", 1000).click();
//   	        		CM.hold(500);
   	        		break;
   	        	case 3://mail list
//   	        		if(!CM.waitForId("com.android.email:id/account_custom_view", 90000))
//   	        			return;
//   	        		CM.waitForId("com.android.email:id/bottom_btn_new", 1000); 
   	        		break;
   	        	case 4://new letter
   	        		CM.findObjectByRid("com.android.email:id/bottom_btn_new", 1000).click();//点击新建邮件
   	        		CM.waitForId("com.android.email:id/compose_send_btn", 2000);
   	        		break;
   	        	case 5://add contact
   	        		CM.findObjectByRid("com.android.email:id/add_to_address_list_btn", 1000).click();
   	        		CM.waitForId("android:id/button2", 2000);
   	        		break;
   	        	case 6://hand write
   	        		CM.findObjectByRid("com.android.email:id/contact_list_home", 1000).click();//点击返回
   	        		CM.waitForId("com.android.email:id/compose_send_btn", 2000);
   	        		CM.findObjectByRid("com.android.email:id/add_cc_address_list_btn", 1000).click();
   	        		CM.hold(1000);
   	        		break;
   	        	case 7:
   	        		
//   	        		CM.findObjectByRid("com.android.email:id/img_handwrite", 1000).click();
//   	        		CM.waitForId("com.android.email:id/lnr_bottom_bar", 2000);
   	        		break;
   	        	case 8://  choose attachment
   	        		CM.findObjectByRid("com.android.email:id/contact_list_home", 1000).click();//点击返回
   	        		CM.findObjectByRid("com.android.email:id/add_attachment_btn", 1000).click();
   	        		CM.waitForId("com.android.email:id/icon", 2000);
//   	        		CM.findObjectByRid("com.android.email:id/add_attachment", 1000).click();
//   	        		CM.waitForId("android:id/content", 2000);
   	        		break;
   	        	case 9://查看发送
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.email:id/compose_send_btn", 2000);
   	        		CM.findObjectByRid("com.android.email:id/compose_send_btn",1000).click();
   	        		CM.waitForId("com.android.email:id/dialog_button_ok", 2000);//等待有ok出现
//   	        		UiObject list=CM.findObjectByRid("oppo:id/select_dialog_listview", 1000);
//   	        		for(int i=0;i<list.getChildCount();i++)
//   	        		{
//   	        			list=CM.findObjectByRid("oppo:id/select_dialog_listview", 1000);
//   	        			list.getChild(new UiSelector().index(i).className("android.widget.LinearLayout")).click();
//   	        			CM.hold(2000);
//   	        			ud.takeScreenshot(new File(snapPath+"/8-"+i+".png"));
//   	        			ud.pressBack();
//   	        			CM.waitForId("com.android.email:id/add_attachment", 1000);
//   	        			CM.findObjectByRid("com.android.email:id/add_attachment", 1000).click();
//   	            		CM.waitForId("android:id/content", 2000);        			
//   	        		}
//   	        		ud.pressBack();
//   	        		CM.waitForId("android:id/up", 1000);
//   	        		CM.findObjectByRid("android:id/up", 1000).click();
   	        		break;
   	        	case 10://read letter
   	        		ud.pressBack();
   	        		CM.hold(2000);
   	        		ud.pressBack();
   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(2)).click();//点击进入邮件
   	        		CM.waitForId("com.android.email:id/conversation_delete", 1000);
//   	        		CM.waitForId("oppo:id/search_src_text", 2000);
//   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(2).className("android.view.View")).click();
//   	        		if(!CM.waitForId("android:id/up", 1000))
//   	        			CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(3).className("android.view.View")).click();
//   	        		CM.waitForId("android:id/up", 1000);
   	        		break;
   	        	case 11://发送添加   
   	        		CM.findObjectByRid("com.android.email:id/more", 1000).click();
   	        		CM.waitForId("com.android.email:id/bottom_more_image", 1000);
//   	        		CM.findObjectByRid("com.android.email:id/favorite", 1000).click();
//   	        		CM.waitForId("android:id/button2", 1000);
   	        		break;
   	        	case 12://replay
   	        		CM.findObjectByRid("com.android.email:id/more_options", 1000).getChild(new UiSelector().index(0)).click();
   	        		CM.waitForId("com.android.email:id/compose_toolsbar_media", 1000);
//   	        		ud.pressBack();
//   	        		CM.waitForId("android:id/up", 2000);
//   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
//   	        		CM.waitForId("oppo:id/select_dialog_listview", 2000);
   	        		break;
   	        	case 13://delete     Move to
   	        		ud.pressBack();
   	        		CM.hold(2000);
   	        		ud.pressBack();
   	        		CM.waitForId("com.android.email:id/more",1000);
   	        		CM.findObjectByRid("com.android.email:id/more", 1000).click();
   	        		CM.findObjectByRid("com.android.email:id/more_options", 2000).getChild(new UiSelector().index(3)).click();
   	        		CM.waitForId("com.android.email:id/name",1000);
//   	        		ud.pressBack();
//   	        		CM.waitForId("android:id/up", 2000);
//   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
//   	        		CM.waitForId("android:id/button2", 1000);
   	        		break;
   	        	case 14://folders       Quick replies
   	        		ud.pressBack();
   	        		CM.hold(2000);
   	        		CM.findObjectByRid("com.android.email:id/more", 1000).click();
   	        		CM.findObjectByRid("com.android.email:id/more_options", 3000).getChild(new UiSelector().index(6)).click();
   	        		CM.waitForId("com.android.email:id/name",1000);
//   	        		CM.findObjectByRid("android:id/button2", 1000).click();
//   	        		CM.waitForId("android:id/up", 1000);
//   	        		ud.pressBack();
//   	        		CM.waitForId("oppo:id/search_src_text", 2000);
//   	        		CM.findObjectByRid("com.android.email:id/img_account_entry", 1000).click();
//   	        		CM.hold(2000);
   	        		break;
   	        	case 15://more options   进入邮件设置  
   	        		ud.pressBack();
   	        		CM.hold(2000);
   	        		ud.pressBack();
   	        		CM.findObjectByRid("com.android.email:id/legacy_home", 1000).click();
   	        		CM.waitForId("com.android.email:id/icon_right", 1000);//判断设置按钮存在
   	        		
//   	        		ud.pressBack();
//   	        		CM.waitForId("oppo:id/search_src_text", 2000);
//   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
//   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
   	        		break;
   	        	case 16://sort options  查看邮件设置的另一半
   	        		CM.scroll(ud, CM.UP);
   	        		CM.hold(2000);
//   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000).click();
//   	        		CM.waitForId("oppo:id/select_dialog_listview", 2000);
   	        		break;
   	        	case 17://account list   
   	        		CM.findObjectByRid("com.android.email:id/btn_attachment", 1000).click();
   	        		CM.findObjectByRid("com.android.email:id/bottom_btn_att_mgr_order_sort", 1000).click();
   	        		CM.waitForId("com.android.email:id/dialog_button_cancel", 1000);
   	        		
//   	        		ud.pressBack();
//   	        		CM.waitForId("oppo:id/MajorOperationButtonMore", 2000);
//   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
//   	        		CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 1000);
//   	        		CM.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
//   	        		CM.waitForId("android:id/list", 2000);
   	        		break;
   	        	case 18://account settings
   	        		ud.pressBack();
   	        		CM.hold(2000);
   	        		ud.pressBack();
   	        		CM.findObjectByRid("com.android.email:id/icon_right",2000).click();
   	        		CM.waitForId("com.android.email:id/account_name", 1000);
//   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(1).className("android.widget.LinearLayout")).click();
//   	        		CM.waitForId("android:id/checkbox", 2000);
   	        		break;
   	        	case 19://     进入账号设置
   	        		CM.findObjectByRid("com.android.email:id/account_name",1000).click();
   	        		CM.waitForId("com.android.email:id/account_description", 1000);
//   	        		CM.scroll(ud, CM.DOWN);
//   	        		CM.hold(1000);
   	        		break;
   	        	case 20://last part of setting 
   	        		CM.scroll(ud, CM.UP);
   	        		CM.hold(2000);
   	        		CM.scroll(ud, CM.UP);
   	        		CM.hold(2000);
//   	        		ud.pressBack();
//   	        		CM.waitForId("com.android.email:id/icon", 2000);
//   	        		CM.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(0).className("android.widget.LinearLayout")).click();
//   	        		CM.waitForId("android:id/checkbox", 2000);
   	        		break;
   	        	case 21://delete account
   	        		CM.scroll(ud, CM.UP);
   	        		CM.hold(2000);
   	        		CM.waitForId("com.android.email:id/account_delete_item", 1000);
   	        		CM.findObjectByRid("com.android.email:id/account_delete_item", 1000).click();
   	        		CM.waitForId("com.android.email:id/dialog_button_ok", 1000);
//   	        		ud.pressBack();
//   	        		CM.waitForId("com.android.email:id/icon", 2000);
//   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
//   	        		CM.waitForId("com.android.email:id/menu_multi_select", 2000);
//   	        		CM.findObjectByRid("com.android.email:id/check_box", 1000).click();
//   	        		CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
//   	        		CM.waitForId("android:id/button1", 2000);
//   	        		CM.findObjectByRid("android:id/button1", 1000).click();
//   	        		CM.waitForId("com.android.email:id/btn_email_login", 2000);
   	 //       		ud.pressBack();
   	        		break;
   	        	default:
//   	        		CM.findObjectByRid("com.android.email:id/dialog_button_ok",1000).click();
//   	        		ud.pressBack();
   	        		break;    			
   	        	}
   	        }
   	    }
   
    }
}
