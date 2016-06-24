package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Environment;
import android.os.StatFs;

import com.android.uiautomator.core.Configurator;
import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

public class PF {
	public static final int LEFT=0;
	public static final int RIGHT=1;
	public static final int UP=2;
	public static final int DOWN=3;
	public static Random random = new Random();
	protected static final int SWIPE_MARGIN_LIMIT = 35;
	
	
	//在桌面通过名称搜索
	public static UiObject searchAppByName(String name) throws UiObjectNotFoundException
    {
    	UiObject app=new UiObject(new UiSelector().textMatches(".*"+name+"$"));
    	if(!app.exists())
    	{
    		UiDevice.getInstance().pressHome();
    		PF.hold(500);
    		UiDevice.getInstance().pressHome();
    		PF.hold(1000);
    		while(!app.exists())
        	{
    			String mText1="";
    			String mText2="";
    			UiObject text1=new UiObject(new UiSelector().className("android.widget.TextView").index(0));
    			UiObject text2=new UiObject(new UiSelector().className("android.widget.TextView").index(1));
    			if(text1.exists())
    			{
    				mText1=text1.getText();
    			}
    			if(text2.exists())
    			{
    				mText2=text2.getText();
    			}
    			UiObject homeRoot=PF.findObjectByRid("com.oppo.launcher:id/workspace", 5000);
    			PF.swipeLeft(homeRoot, 11);
    			PF.hold(2000);
    			if(text1.getText().equals(mText1)&&text2.getText().equals(mText2))
    			{
        			PF.swipeLeft(homeRoot, 11);
        			PF.hold(2000);
        			if(text1.getText().equals(mText1)&&text2.getText().equals(mText2))
        			{
        				return app;
        			}
    			}
        	}
    	}
		return app;
    }
	//统计当前页面mText数目
	public static int totalOfText(String mText)
	{
		int t=0;
		UiCollection root=new UiCollection(new UiSelector().className("android.widget.FrameLayout"));
		t=root.getChildCount(new UiSelector().text(mText));
		return t;
	}
	
	//统计某个类型的View数目
	public static int totalOfClassView(String type)
	{
		int t=0;
		UiCollection root=new UiCollection(new UiSelector().className("android.widget.FrameLayout"));
		t=root.getChildCount(new UiSelector().className(type));
		return t;
	}
	
	//统计View数目按照ID
	public static int totalById(String id)
	{
		int t=0;
		UiCollection root=new UiCollection(new UiSelector().className("android.widget.FrameLayout"));
		t=root.getChildCount(new UiSelector().resourceId(id));
		return t;
	}	
	
	//输入——解决uiautomator部分界面输入位置错误的问题
	public static boolean setText(UiObject editor,String mText)
	{
		try{
			UiDevice.getInstance().pressBack();
			hold(1500);
//			editor.click();
//			hold(1000);
			editor.setText(mText);
			hold(500);
			if(editor.getText().contains(mText))
			{
				return true;
			}else{
				return false;
			}
		}catch(Throwable ee){
			return false;
		}
	}
	
	
	//截图及图像对比函数
	public static boolean sameAs (String path1, String path2) throws FileNotFoundException {
        boolean res = false;
        FileInputStream fis1 = new FileInputStream(path1);
        Bitmap bitmap1  = BitmapFactory.decodeStream(fis1);
        FileInputStream fis2 = new FileInputStream(path2);
        Bitmap bitmap2  = BitmapFactory.decodeStream(fis2);
        res = sameAs(bitmap1,bitmap2);
        return res;
      }
      
      public static boolean sameAs (String path1, String path2,double percent) throws FileNotFoundException {
        FileInputStream fis1 = new FileInputStream(path1);
        Bitmap bitmap1  = BitmapFactory.decodeStream(fis1);
        FileInputStream fis2 = new FileInputStream(path2);
        Bitmap bitmap2  = BitmapFactory.decodeStream(fis2);
        return sameAs(bitmap1,bitmap2,percent);
      }
      
      public static boolean sameAs (Bitmap bitmap1, Bitmap bitmap2, double percent) {
        if(bitmap1.getHeight() != bitmap2.getHeight())
          return false;
        
        if(bitmap1.getWidth() != bitmap2.getWidth())
          return false;
        
        if(bitmap1.getConfig() != bitmap2.getConfig())
          return false;

        int width = bitmap1.getWidth();
        int height = bitmap2.getHeight();

        int numDiffPixels = 0;
           
           for (int y = 0; y < height; y++) {
             for (int x = 0; x < width; x++) {
               if (bitmap1.getPixel(x, y) != bitmap2.getPixel(x, y)) {
                 numDiffPixels++;
               }
             }
           }
           double numberPixels = height * width;
           double diffPercent = numDiffPixels / numberPixels;
           return percent <= 1.0D - diffPercent;
      }
      
      public static boolean sameAs (Bitmap bmp1, Bitmap bmp2) throws FileNotFoundException {
        boolean res = false;
        res = bmp1.sameAs(bmp2);
        return res;		
      }
      
      public static Bitmap getSubImage(String path,int x,int y,int width,int height) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        Bitmap bitmap  = BitmapFactory.decodeStream(fis);
        Bitmap res = Bitmap.createBitmap(bitmap, x, y, width, height);
        return res;
      }
	
	//由于uiautomator里面的swip方法在使用时，底部和顶部只加减5像素，而我们部分机型底部和顶部20像素范围是不可用的
	//因此需要改下UiObject原有的swip方法
	public static boolean swipeUp(UiObject mObj,int steps) throws UiObjectNotFoundException
	{
		Rect rect = mObj.getVisibleBounds();
		if(rect.height() <= SWIPE_MARGIN_LIMIT * 2)
            return false; // too small to swipe
        return UiDevice.getInstance().swipe(rect.centerX(),
                rect.bottom - SWIPE_MARGIN_LIMIT, rect.centerX(), rect.top + SWIPE_MARGIN_LIMIT,steps);
	}
	//向下滑动
	public static boolean swipeDown(UiObject mObj,int steps) throws UiObjectNotFoundException
	{
		Rect rect = mObj.getVisibleBounds();
		if(rect.height() <= SWIPE_MARGIN_LIMIT * 2)
            return false; // too small to swipe
        return UiDevice.getInstance().swipe(rect.centerX(),
                rect.top + SWIPE_MARGIN_LIMIT, rect.centerX(),rect.bottom - SWIPE_MARGIN_LIMIT, steps);
	}
	public static boolean swipeLeft(UiObject mObj,int steps) throws UiObjectNotFoundException
	{
		Rect rect = mObj.getVisibleBounds();
		if(rect.height() <= SWIPE_MARGIN_LIMIT * 2)
            return false; // too small to swipe
        return UiDevice.getInstance().swipe(rect.right - SWIPE_MARGIN_LIMIT,
                rect.centerY(), rect.left + SWIPE_MARGIN_LIMIT, rect.centerY(), steps);
	}
	public static boolean swipeRight(UiObject mObj,int steps) throws UiObjectNotFoundException
	{
		Rect rect = mObj.getVisibleBounds();
		if(rect.height() <= SWIPE_MARGIN_LIMIT * 2)
            return false; // too small to swipe
        return UiDevice.getInstance().swipe(rect.left + SWIPE_MARGIN_LIMIT,
                rect.centerY(), rect.right - SWIPE_MARGIN_LIMIT, rect.centerY(), steps);
	}
	
	//获取textview对应的其他控件
	public static UiObject getObjBySameRowText(UiObject root,String fatherClass,String mText,String objClass)
	{
		int childNum;
		try {
			childNum = root.getChildCount();
			for(int i=0;i<childNum;i++)
			{
				UiObject child=root.getChild(new UiSelector().className(fatherClass).index(i));
				UiObject text=child.getChild(new UiSelector().text(mText));
				if(text.exists())
				{
					UiObject result=child.getChild(new UiSelector().className(objClass));
					if(result.exists())
					{
						return result;
					}
				}
			}
		} catch (UiObjectNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return null;
	}
	
	public static UiObject getObjBySameRowPartText(UiObject root,String fatherClass,String mText,String objClass)
	{
		int childNum;
		try {
			childNum = root.getChildCount();
			for(int i=0;i<childNum;i++)
			{
				UiObject child=root.getChild(new UiSelector().className(fatherClass).index(i));
				UiObject text=child.getChild(new UiSelector().textContains(mText));
				if(text.exists())
				{
					UiObject result=child.getChild(new UiSelector().className(objClass));
					if(result.exists())
					{
						return result;
					}
				}
			}
		} catch (UiObjectNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return null;
	}
	
	//根据双textView获取父节点
	public static UiObject getFatherObjByTwoText(UiObject root,String fatherClass,String mText,String rText) throws UiObjectNotFoundException
	{
		for(int i=0;i<root.getChildCount();i++)
		{
			UiObject father=root.getChild(new UiSelector().className(fatherClass).index(i));
			UiObject child1=father.getChild(new UiSelector().textContains(mText));
			UiObject child2=father.getChild(new UiSelector().textContains(rText));
			if(child1.exists()&&child2.exists())
			{
				return father;
			}
		}
		return null;
	}
	
	public static void startActivity(String pack,String activity)
	{
		Process starter = null;
		try
		{
			starter=Runtime.getRuntime().exec("am start "+pack+"/"+activity);
			starter.waitFor();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(starter!=null)
				starter.destroy();
		}	
	}
	//返回主界面
    public static boolean gobackHomePage(UiDevice ud) {
		int n=0;
		while(!ud.getCurrentPackageName().contains("com.oppo.launcher"))
		{
			ud.pressBack();
			PF.hold(1000);
			n++;
			if(n>6)
			{
				break;
			}
		}
		ud.pressHome();
		PF.hold(1000);
		ud.pressHome();
		PF.hold(1000);
		if(ud.getCurrentPackageName().contains("com.oppo.launcher"))
		{
			return true;
		}else{
			return false;
		}
	}
	public static void clickFastOnScreen(int num,UiDevice ud,int x,int y)
	{
		long timeout =Configurator.getInstance().getActionAcknowledgmentTimeout();
		Configurator.getInstance().setActionAcknowledgmentTimeout(0);
		for(int i =0;i <num; i++){
			ud.click(x,y);
		}
		Configurator.getInstance().setActionAcknowledgmentTimeout(timeout);
	}
	public static void clickLongOnScreen(UiDevice ud,int x,int y)
	{
		ud.drag(x, y, x, y, 15);
	}
	public static void clickLongOnObject(UiDevice ud,UiObject obj) throws UiObjectNotFoundException
	{
		Rect r=obj.getBounds();
		ud.drag(r.centerX(), r.centerY(), r.centerX(), r.centerY(), 20);
	}
	
	public static void clickObjOnScreen(UiDevice ud,UiObject obj) throws UiObjectNotFoundException
	{
		Rect r=obj.getBounds();
		ud.click(r.centerX(), r.centerY());
	}
	
	//点击menu菜单
	public static boolean clickOnMenuItem(UiDevice ud,String menuText) throws UiObjectNotFoundException
	{
		ud.pressMenu();
		if(waitForText(menuText, 8000))
		{
			findObjectByText(menuText, 5000).click();
			hold(2000);
		}else{
			return false;
		}
		return true;
	}
	
	//输入文字
	public static void enterText(UiDevice ud,UiObject obj,String text) throws UiObjectNotFoundException
	{
		if(obj.exists())
		{
			obj.setText(text);
		}
	}
	
    //通过完整text搜索
    public static UiObject searchObjByWholeText(UiScrollable list,String text)
    {
    	UiObject result=null;
    	if(list.exists())
    	{
    		try {
				result=list.getChildByText(new UiSelector().className("android.widget.TextView"), text);
			} catch (UiObjectNotFoundException e) {
				//此处若没有搜索到，则不抛出异常，不做处理，避免主函数的catch做异常处理
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
    	}
    	hold(500);
		return result;
    }
    
    //通过完整desc搜索
    public static UiObject searchObjByWholeDecs(UiScrollable list,String desc)
    {
    	UiObject result=null;
    	if(list.exists())
    	{
    		try {
				if(list.scrollIntoView(new UiSelector().description(desc)))
				{
					result=new UiObject(new UiSelector().description(desc));
				}
			} catch (UiObjectNotFoundException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
    	}
    	hold(500);
		return result;
    }
    
    public static UiObject searchObjByWholeTextButton(UiScrollable list,String buttonText)
    {
    	UiObject result=null;
    	if(list.exists())
    	{
    		try {
				result=list.getChildByText(new UiSelector().className("android.widget.Button"), buttonText);
			} catch (UiObjectNotFoundException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
    	}
    	hold(500);
		return result;
    }
    
    //在listview中搜索text
    public static UiObject searchObjByText(UiScrollable list,String text)
    {
    	UiObject result=null;
    	if(list.exists())
    	{
    		try {
				if(list.scrollIntoView(new UiSelector().textContains(text)))
				{
					result=new UiObject(new UiSelector().textContains(text));
				}
			} catch (UiObjectNotFoundException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
    	}
    	hold(500);
		return result;
    }
    
    //在listview中搜索desc
    public static UiObject searchObjByDesc(UiScrollable list,String desc)
    {
    	UiObject result=null;
    	if(list.exists())
    	{
    		try {
				if(list.scrollIntoView(new UiSelector().descriptionContains(desc)))
				{
					result=new UiObject(new UiSelector().descriptionContains(desc));
				}
			} catch (UiObjectNotFoundException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
    	}
    	hold(500);
		return result;
    }
    
    public static UiObject searchObjByID(UiScrollable list,String mID)
    {
    	UiObject result=null;
    	if(list.exists())
    	{
    		try {
				if(list.scrollIntoView(new UiSelector().resourceId(mID)))
				{
					result=new UiObject(new UiSelector().resourceId(mID));
				}
			} catch (UiObjectNotFoundException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
    	}
    	hold(500);
		return result;
    }
	
    public static boolean waitForScrollable(UiScrollable scroll,long timeout)
    {
    	for(int i=-1;i<timeout/1000;i++)
    	{
    		if(scroll.exists())
    			return true;
    		else
    		{
    			if(timeout>=1000)
    			    hold(1000);
    			else
    				hold(100);
    		}  
    	}
    	return false;
    }
    
    public static boolean waitForObject(UiObject obj,long timeout)
    {
    	for(int i=-1;i<timeout/1000;i++)
    	{
    		if(obj.exists())
    			return true;
    		else
    		{
    			if(timeout>=1000)
    			    hold(1000);
    			else
    				hold(100);
    		}  
    	}
    	return false;
    }
    public static boolean waitForPackage(String packageName,long timeout)
    {
    	UiObject obj=new UiObject(new UiSelector().packageName(packageName));
    	for(int i=-1;i<timeout/1000;i++)
    	{
    		if(obj.exists())
    			return true;
    		else
    		{
    			if(timeout>=1000)
    			    hold(1000);
    			else
    				hold(100);
    		}  
    	}
    	return false;
    }
    public static boolean waitForClass(String className,long timeout)
    {
    	UiObject obj=new UiObject(new UiSelector().className(className));
    	for(int i=-1;i<timeout/1000;i++)
    	{
    		if(obj.exists())
    			return true;
    		else
    		{
    			if(timeout>=1000)
    			    hold(1000);
    			else
    				hold(100);
    		}  
    	}
    	return false;
    }
    public static boolean waitforObjectScroll(UiObject obj,long timeout)
    {
    	for(int i=-1;i<timeout/1000;i++)
    	{
    		if(obj.exists())
    			return true;
    		else
    		{
    			if(timeout>=1000)
    			    hold(1000);
    			else
    				hold(100);
    		}  
    	}
    	return false;
    }
    public static boolean waitForId(String rId,long timeout)
    {
    	UiObject obj=new UiObject(new UiSelector().resourceId(rId));
    	for(int i=-1;i<timeout/1000;i++)
    	{
    		if(obj.exists())
    			return true;
    		else
    		{
    			if(timeout>=1000)
    			    hold(1000);
    			else
    				hold(100);
    		}  
    	}
    	return false;
    }
    public static boolean waitForPartId(String partID,long timeout)
    {
    	UiObject obj=new UiObject(new UiSelector().resourceIdMatches(".*"+partID+".*"));
    	for(int i=-1;i<timeout/1000;i++)
    	{
    		if(obj.exists())
    			return true;
    		else
    		{
    			if(timeout>=1000)
    			    hold(1000);
    			else
    				hold(100);
    		}  
    	}
    	return false;
    }
    public static boolean waitForWholeText(String rText,long timeout)
    {
    	UiObject obj=new UiObject(new UiSelector().text(rText));
    	for(int i=-1;i<timeout/1000;i++)
    	{
    		if(obj.exists())
    			return true;
    		else
    		{
    			if(timeout>=1000)
    			    hold(1000);
    			else
    				hold(100);
    		}  
    	}
    	return false;
    }
    public static boolean waitForText(String rText,long timeout)
    {
    	UiObject obj=new UiObject(new UiSelector().textContains(rText));
    	for(int i=-1;i<timeout/1000;i++)
    	{
    		if(obj.exists())
    			return true;
    		else
    		{
    			if(timeout>=1000)
    			    hold(1000);
    			else
    				hold(100);
    		}  
    	}
    	return false;
    }
    public static boolean waitForTextAndID(String rText,String rid,long timeout)
    {
    	UiObject obj=new UiObject(new UiSelector().resourceId(rid).textContains(rText));
    	for(int i=-1;i<timeout/1000;i++)
    	{
    		if(obj.exists())
    			return true;
    		else
    		{
    			if(timeout>=1000)
    			    hold(1000);
    			else
    				hold(100);
    		}  
    	}
    	return false;
    }
    public static boolean waitForWholeDesc(String rDesc,long timeout)
    {
    	UiObject obj=new UiObject(new UiSelector().description(rDesc));
    	for(int i=-1;i<timeout/1000;i++)
    	{
    		if(obj.exists())
    			return true;
    		else
    		{
    			if(timeout>=1000)
    			    hold(1000);
    			else
    				hold(100);
    		}  
    	}
    	return false;
    }
    public static boolean waitForDesc(String rDesc,long timeout)
    {
    	UiObject obj=new UiObject(new UiSelector().descriptionContains(rDesc));
    	for(int i=-1;i<timeout/1000;i++)
    	{
    		if(obj.exists())
    			return true;
    		else
    		{
    			if(timeout>=1000)
    			    hold(1000);
    			else
    				hold(100);
    		}  
    	}
    	return false;
    }
    
    public static boolean waitForNewApp(String packageName,long timeout) throws UiObjectNotFoundException
    {
    	for(int i=-1;i<timeout/1000;i++)
    	{
    		try
    		{
	    		if((new UiObject(new UiSelector().index(0))).getPackageName().equals(packageName))
	    			return true;
	    		else
	    		{
	    			if(timeout>=1000)
	    			    hold(1000);
	    			else
	    				hold(100);
	    		}   
    		}catch(Exception e)
    		{}
    	}
    	return false;
    }
    
    
    
    public static UiObject findObjectByDesc(String mText,long timeout)
    {
    	UiObject mObj=new UiObject(new UiSelector().descriptionContains(mText));
    	if(waitForObject(mObj,timeout))
    		return mObj;
    	else
    		return null;
    }
    
//    public static UiScrollable findScrollableByDesc(String mText,long timeout)
//    {
//    	UiScrollable mScr=new UiScrollable(new UiSelector().descriptionContains(mText));
//    	if(waitForScrollable(mScr,timeout))
//    		return mScr;
//    	else
//    		return null;
//    }
    
    public static UiObject findObjectByWholeDesc(String mText,long timeout)
    {
    	UiObject mObj=new UiObject(new UiSelector().description(mText));
    	if(waitForObject(mObj,timeout))
    		return mObj;
    	else
    		return null;
    }
    
//    public static UiScrollable findScrollableByWholeDesc(String mText,long timeout)
//    {
//    	UiScrollable mScr=new UiScrollable(new UiSelector().description(mText));
//    	if(waitForScrollable(mScr,timeout))
//    		return mScr;
//    	else
//    		return null;
//    }
    
    public static UiObject findObjectByRid(String rid,long timeout)
    {
    	UiObject mObj=new UiObject(new UiSelector().resourceId(rid));
    	if(waitForObject(mObj,timeout))
    		return mObj;
    	else
    		return null;
    }
    
    public static UiObject findObjectByRidMatches(String rid,long timeout)
    {
    	UiObject mObj=new UiObject(new UiSelector().resourceIdMatches(rid));
    	if(waitForObject(mObj,timeout))
    		return mObj;
    	else
    		return null;
    }
    
    //已某字符串ID结尾
    public static UiObject findObjectEndWithId(String endID,long timeout)
    {
    	UiObject mObj=new UiObject(new UiSelector().resourceIdMatches(".*"+endID+"$"));
    	if(waitForObject(mObj,timeout))
    		return mObj;
    	else
    		return null;
    }
    //已某字符串ID开头
    public static UiObject findObjectStartWithId(String startID,long timeout)
    {
    	UiObject mObj=new UiObject(new UiSelector().resourceIdMatches("^"+startID+".*"));
    	if(waitForObject(mObj,timeout))
    		return mObj;
    	else
    		return null;
    }
    //包含某字符串ID
    public static UiObject findObjectContainId(String partID,long timeout)
    {
    	UiObject mObj=new UiObject(new UiSelector().resourceIdMatches(".*"+partID+".*"));
    	if(waitForObject(mObj,timeout))
    		return mObj;
    	else
    		return null;
    }
    public static UiScrollable findScrollableByRid(String rid,long timeout)
    {
    	UiScrollable mScr=new UiScrollable(new UiSelector().resourceId(rid));
    	if(waitForScrollable(mScr,timeout))
    		return mScr;
    	else
    		return null;
    }
    
    public static UiObject findObjectRidMatches(String regex,long timeout)
    {
    	UiObject mObj=new UiObject(new UiSelector().resourceIdMatches("["+regex+"]"));
    	if(waitForObject(mObj,timeout))
    		return mObj;
    	else
    		return null;
    }
    
    public static UiScrollable findScrollableByRidMatches(String regex,long timeout)
    {
    	UiScrollable mScr=new UiScrollable(new UiSelector().resourceIdMatches("["+regex+"]"));
    	if(waitForScrollable(mScr,timeout))
    		return mScr;
    	else
    		return null;
    }
    
    public static UiObject findObjectByClassname(String className,long timeout)
    {
    	UiObject mObj=new UiObject(new UiSelector().className(className));
    	if(waitForObject(mObj,timeout))
    		return mObj;
    	else
    		return null;
    }
    
    public static UiScrollable findScrollableByClassname(String className,long timeout)
    {
    	UiScrollable mScr=new UiScrollable(new UiSelector().className(className));
    	if(waitForScrollable(mScr,timeout))
    		return mScr;
    	else
    		return null;
    }
    
    public static UiObject findObjectByText(String text,long timeout)
    {
    	UiObject mObj=new UiObject(new UiSelector().textContains(text));
    	if(waitForObject(mObj,timeout))
    		return mObj;
    	else
    		return null;
    }
    
    
    public static UiScrollable findScrollableByText(String text,long timeout)
    {
    	UiScrollable mScr=new UiScrollable(new UiSelector().textContains(text));
    	if(waitForScrollable(mScr,timeout))
    		return mScr;
    	else
    		return null;
    }
    
    public static UiObject findObjectByWholeText(String text,long timeout)
    {
    	UiObject mObj=new UiObject(new UiSelector().text(text));
    	if(waitForObject(mObj,timeout))
    		return mObj;
    	else
    		return null;
    }
    
    //不包含某个字符
    public static UiObject findObjectByNoText(String noText,long timeout)
    {
    	UiObject mObj=new UiObject(new UiSelector().textMatches("^((?!"+noText+").)*$"));
    	if(waitForObject(mObj,timeout))
    		return mObj;
    	else
    		return null;
    }
    
//    public static UiScrollable findScrollableByWholeText(String text,long timeout)
//    {
//    	UiScrollable mScr=new UiScrollable(new UiSelector().text(text));
//    	if(waitForScrollable(mScr,timeout))
//    		return mScr;
//    	else
//    		return null;
//    }
    
    public static UiObject findChildByPosistion(UiObject obj,int[] pos)
    {
    	try
    	{
	    	for(int i=0;i<pos.length;i++)
	    	{
	    		obj=obj.getChild(new UiSelector().index(pos[i]));
	    	}
    	}catch(Exception e)
    	{
    		return null;
    	}
    	return obj;
    }
    //长按某个object多少秒
    public static void touchLongOnObj(UiDevice ud,UiObject obj,int time) throws UiObjectNotFoundException
    {
    	Rect mRect=obj.getBounds(); 
    	//拖动60步约耗时1秒
    	ud.drag(mRect.centerX(), mRect.centerY(), mRect.centerX(), mRect.centerY(), time/1000*60);
    }
    
    public static void touchInObj(UiDevice ud,UiObject obj,double x,double y) throws UiObjectNotFoundException
    {
    	Rect mr=obj.getBounds();
    	ud.click(mr.left+(int)(mr.width()*x), mr.top+(int)(mr.height()*y));
    }
    public static void dragObj(UiDevice ud,UiObject obj,int direction,int steps) throws UiObjectNotFoundException
    {
    	Rect mr=obj.getBounds();  	
    	switch(direction)
    	{
    	case LEFT:
    	    ud.drag(mr.right-5, mr.centerY(), mr.left+5, mr.centerY(), steps);    	    
    	    break;
    	case RIGHT:
    		ud.drag(mr.left+5, mr.centerY(), mr.right-5, mr.centerY(), steps);    	    
    	    break;
    	case UP:
    		ud.drag(mr.centerX(), mr.bottom-5, mr.centerX(), mr.top+5, steps);    	    
    	    break;
    	case DOWN:
    		ud.drag(mr.centerX(), mr.top+5, mr.centerX(), mr.bottom-5, steps);    	    
    	    break;
    	}
    }
    public static void dragObjTo(UiDevice ud,UiObject obj,int direction,int distance,int steps) throws UiObjectNotFoundException
    {
    	Rect mr=obj.getBounds();  	
    	switch(direction)
    	{
    	case LEFT:
    	    ud.drag(mr.right-5, mr.centerY(), mr.left-distance, mr.centerY(), steps);    	    
    	    break;
    	case RIGHT:
    		ud.drag(mr.left+5, mr.centerY(), mr.right+distance, mr.centerY(), steps);    	    
    	    break;
    	case UP:
    		ud.drag(mr.centerX(), mr.bottom+distance, mr.centerX(), mr.top+5, steps);    	    
    	    break;
    	case DOWN:
    		ud.drag(mr.centerX(), mr.top-distance, mr.centerX(), mr.bottom-5, steps);    	    
    	    break;
    	}
    }
    public static void scroll(UiDevice ud,int direction)
    {
    	int w=ud.getDisplayWidth();
    	int h=ud.getDisplayHeight();
    	switch(direction)
    	{
    	case LEFT:
    		ud.swipe(w/2+100, h/2, w/2-100, h/2, 5);
    		break;
    	case RIGHT:
    		ud.swipe(w/2-100, h/2, w/2+100, h/2, 5);
    		break;
    	case UP:
    		ud.swipe(w/2, h/2+200, w/2, h/2-200, 5);
    		break;
    	case DOWN:
    		ud.swipe(w/2, h/2-200, w/2, h/2+200, 5);
    		break;
    	}
    }
//    public static void setText(UiDevice ud,UiObject obj,String text) throws UiObjectNotFoundException
//    {
//    	String chars="0123456789************abcdefghijklmnopqrstuvwxyz";
//    	obj.click();
//    	hold(500);
//    	obj.click();
//    	hold(1000);
//    	for(char w:text.toCharArray())
//    	{
//    		ud.pressKeyCode(chars.indexOf(w)+7);
//    		hold(50);
//    	}
//    		
//    }
//    public static void setText(UiObject obj,String mText) throws UiObjectNotFoundException
//    {
//    	hold(2000);
//    	obj.click();
//    	hold(500);
//    	obj.click();
//    	hold(500);
//    	obj.setText(mText);
//    }
    
    public static void hold(long time)
    {
    	try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static void writeReport(String fileName,String report) throws IOException
    {
    	 FileOutputStream fos = new FileOutputStream(fileName,true); 
         OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8"); 
         osw.write(report); 
         osw.flush();
         osw.close();
    }
    
    public static String getMilliSecond()
    {
    	SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmssSSS");
    	Date nowTime = new Date(System.currentTimeMillis());  
    	return formatter.format(nowTime);
    }
    
    public static String getTime()
    {
 	   SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");     
 	   Date nowTime = new Date(System.currentTimeMillis());
 	   return formatter.format(nowTime).replace(" ", "-");
    }
    
    public static String getShortTime()
	{
	   SimpleDateFormat formatter=new SimpleDateFormat("HH-mm-ss");     
	   Date nowTime = new Date(System.currentTimeMillis());   
	   return formatter.format(nowTime);
	}
    
    public static String getDay()
	{
	   SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");     
	   Date nowTime = new Date(System.currentTimeMillis());
	   return formatter.format(nowTime);
	}
    
    public static String getRootPath()
    {
    	String path=Environment.getExternalStorageDirectory()+"";
    	if(path.contains("emulated"))
    		return "/storage/sdcard0";
    	else
    	    return path;
    }
    public static void createFolder(String path)
    {
    	File mF=new File(path);
    	if(!mF.exists())
    		mF.mkdirs();
    }
    public static void recordLog(UiDevice curDev,String log,String logPath,String snapPath)
    {
		try {
		    writeReport(logPath,log);
		    curDev.takeScreenshot(new File(snapPath+"step1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static String readTxt(String fileName) throws Exception
    {
		BufferedReader reader=new BufferedReader(new FileReader(new File(fileName)));
		String line=reader.readLine().trim();
		reader.close();
		return line;  	    			
    }
    public static boolean comparePic(String oldPath,String newPath,String picname,String reportpath)
    {
    	try
    	{
	    	Bitmap oldBit=BitmapFactory.decodeFile(oldPath+"/"+picname+".png");
	    	Bitmap newBit=BitmapFactory.decodeFile(newPath+"/"+picname+".png");
	    	int count=0;
	    	for(int h=oldBit.getHeight()*2/50;h<oldBit.getHeight();h++)
	    	{
	    		for(int w=0;w<oldBit.getWidth();w++)
	    		{
	    			if(Math.abs(oldBit.getPixel(w, h)-newBit.getPixel(w, h))>200000)
	    			{
	    				count++;
	    			}
	    		}
	    	}
	    	if(count<150)
	    		return true;
	    	else
	    		return false;
    	}catch(Exception e)
    	{
    		try {
				writeReport(reportpath, e.toString()+"\r\n");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		return false;
    	}
    }
    
    public static int getRandomNum(int k)
	{
		return random.nextInt(k);
	}
	
	public static int getRandomNumBetween(int a,int b)
	{
		if(a<b)
		{
		    return a+random.nextInt(b-a);		
		}
		else
		{
			return b+random.nextInt(a-b);
		}
	}
	
	//随机获取
	public static String getString(int a,int b)//返回大于a小于b个字符
	{
		String num="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"+
				"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"+
				"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"+
				"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"+
				"!@#$%^&*()~-<>?[]{}|";
//                "的一是了我不人在他有这个上们来到时大地为子中你说生国年着就那和要她出也得里后自以会家可" +
//				"下而过天去能对小多然于心学么之都好看起发当没成只如事把还用第样道想作种开美总从无情己面最女但" +
//				"现前些所同日手又行意动方期它头经长儿回位分爱老因很给名法间斯知世什两次使身者被高已亲其进此话常" +
//				"与活正感的一是了我不人在他有这个上们来到时大地为子中你说生国年着就那和要她出也得里后自以会"+
//				"的一是了我不人在他有这个上们来到时大地为子中你说生国年着就那和要她出也得里后自以会家可下而过" +
//				"天去能对小多然于心学么之都好看起发当没成只如事把还用第样道想作种开见明问力理尔点文几定本公特做外" +
//				"孩相西果走将月十实向声车全信重三机工物气每并别真打太新比才便夫再书部水像眼等体却加电主界门利海受" +
//				"听表德少克代员许稜先口由死安写性马光白或住难望教命花结乐色更拉东神记处让母父应直字场平报友关放至" +
//				"张认接告入笑内英军候民岁往何度山觉路带万男边风解叫任金快原吃妈变通师立象数四失满战远格士音轻目条呢" +
//				"病始达深完今提求清王化空业思切怎非找片罗钱紶吗语元喜曾离飞科言干流欢约各即指合反题必该论交终林请医晚" +
//				"制球决窢传画保读运及则房早院量苦火布品近坐产答星精视五连司巴奇管类未朋且婚台夜青北队久乎越观落尽形影" +
//				"红爸百令周吧识步希亚术留市半热送兴造谈容极随演收首根讲整式取照办强石古华諣拿计您装似足双妻尼转诉米称丽" +
//				"客南领节衣站黑刻统断福城故历惊脸选包紧争另建维绝树系伤示愿持千史谁准联妇纪基买志静阿诗独复痛消社算算义" +
//				"竟确酒需单治卡幸兰念举仅钟怕共毛句息功官待究跟穿室易游程号居考突皮哪费倒价图具刚脑永歌响商礼细专黄块脚" +
//				"味灵改据般破引食仍存众注笔甚某沉血备习校默务土微娘须试怀料调广蜖苏显赛查密议底列富梦错座参八除跑亮假印" +
//				"设线温虽掉京初养香停际致阳纸李纳验助激够严证帝饭忘趣支春集丈木研班普导顿睡展跳获艺六波察群皇段急庭创区" +
//				"奥器谢弟店否害草排背止组州朝封睛板角况曲馆育忙质河续哥呼若推境遇雨标姐充围案伦护冷警贝著雪索剧啊船险烟依" +
//				"斗值帮汉慢佛肯闻唱沙局伯族低玩资屋击速顾泪洲团圣旁堂兵七露园牛哭旅街劳型烈姑陈莫鱼异抱宝权鲁简态级票怪" +
//				"寻杀律胜份汽右洋范床舞秘午登楼贵吸责例追较职属渐左录丝牙党继托赶章智冲叶胡吉卖坚喝肉遗救修松临藏担戏善卫" +
//				"药悲敢靠伊村戴词森耳差短祖云规窗散迷油旧适乡架恩投弹铁博雷府压超负勒杂醒洗采毫嘴毕九冰既状乱景席珍童顶派" +
//				"脱农疑练野按犯拍征坏骨余承置臓彩灯巨琴免环姆暗换技翻束增忍餐洛塞缺忆判欧层付阵玛批岛项狗休懂武革良恶恋委" +
//				"拥娜妙探呀营退摇弄桌熟诺宣银势奖宫忽套康供优课鸟喊降夏困刘罪亡鞋健模败伴守挥鲜财孤枪禁恐伙杰迹妹藸遍盖副坦" +
//				"牌江顺秋萨菜划授归浪听凡预奶雄升碃编典袋莱含盛济蒙棋端腿招释介烧误" 
//				"●★◆◣◢◥▲▼◤◥◆◣◥▲【】★◆■▅ "+
//				"▆ ▇〓▌▍▎▓〖〗◎①◎Θ⊙〇¤○□☆╰☆╮△▽⊿△◇△∩□" +
//				"＋－×÷﹢﹣±／＝∥∠≌∽≦≧≒﹤﹥≈≡≠＝≤≥＜＞≮≯∷∫∮∝∞∧∨∪∩∈⊥∥∠⌒⊙√∟⊿㏒㏑％‰↑↓→←↘↙♀♂"+
//				"⑴⑵⑶⑷⑸⑹⑺⑻⑼⑽⑾⑿⒀⒁⒂⒃⒄⒅⒆⒇ ①②③④⑤⑥⑦⑧⑨⑩ⅰⅱⅲⅳⅴⅵⅶⅷⅸⅹⅠⅡⅢⅣⅤⅥⅦⅧⅨⅩ"+
//				"βγδεζηθικλμνξοπρστυφχψω";
		int numCount=num.length();
		StringBuffer strBuffer = new StringBuffer();
		int sum=getRandomNumBetween(a, b);
		for(int i=0;i<sum;i++)
		{
			int m=getRandomNum(numCount);
			strBuffer.append(num.charAt(m));		
		}
		return strBuffer.toString();
	}
	
	//随机生成号码
	public static String getNumber(int bits)
	{
		String num="";
		for(int i=0;i<bits;i++)
		{
			num+=getRandomNum(10);
		}
		return num;
	}	
	
	//获取网络时间
	public static Date getBeijingTime() throws Exception {

	       URL url=new URL("http://bjtime.cn");//取得资源对象

	       URLConnection uc=url.openConnection();//生成连接对象

	       uc.connect(); //发出连接

	       long ld=uc.getDate(); //取得网站日期时间

	       Date date=new Date(ld); //转换为标准时间对象
	       return date;

	       //分别取得时间中的小时，分钟和秒，并输出
//	       System.out.print(date.getHours()+"时"+date.getMinutes()+"分"+date.getSeconds()+"秒");

	}
	
	//获取手机SD卡剩余空间
	public int getSDCardSpace() { 
		int s=0;
        String state = Environment.getExternalStorageState(); 
        if(Environment.MEDIA_MOUNTED.equals(state)) { 
        	File sdcardDir = new File("/storage/sdcard1");
            StatFs sf = new StatFs(sdcardDir.getPath()); 
            long blockSize = sf.getBlockSizeLong(); 
            long availCount = sf.getAvailableBlocksLong(); 
            s=(int) (availCount*blockSize/1024/1024);
        }    
        return s;
    }	
}
