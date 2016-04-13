
package com.zbin.main;

import java.io.IOException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.core.UiWatcher;

public class Common extends BaseTestCase {
	// private static final AccessibilityNodeInfo AccessibilityNodeInfo = null;
	// private static final android.support.test.uiautomator.UiDevice UiDevice =
	// null;
	// private static final BySelector BySelector = null;

	/**
	 * 关闭app，回到主界面
	 */
	public void stopApp() {
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressHome();
		UiDevice.getInstance().pressHome();

	}

	/**
	 * 如果出现文字点击
	 * 
	 * @throws Exception
	 */
	public void watchExeption(String text) throws Exception {
		UiObject okDialog = new UiObject(new UiSelector().text(text));
		if (okDialog.exists()) {
			// takeScreenshot();
			sleep(500);
			okDialog.click();
		}

	}

	/**
	 * runtime
	 */
	public void Runtime(String name) {
		try {

			Runtime.getRuntime().exec(name);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void watcherStop() {
		// TODO Auto-generated method stub
		UiDevice.getInstance().registerWatcher("camerawatcher", // 注册监听器
				new UiWatcher() {

					public boolean checkForCondition() { // 中断监听检查条件
						// TODO Auto-generated method stub
						System.out.println("正在监听是否停止运行……");

						return false;

					}
				});
	}

	/**
	 * 监听程序出错 添加点击动作 添加截图动作
	 */

	public void watcherStop1() {
		// UiDevice.getInstance().resetWatcherTriggers();
		UiDevice.getInstance().registerWatcher("hehe",
				// 注册监听器
				new UiWatcher() {

					public boolean checkForCondition() {
						// 中断监听检查条件
						System.out.println("正在监听是否停止运行……");

						UiObject dialog = new UiObject(new UiSelector().text("存储"));

						if (dialog.exists()) {
							try {
								takeScreenshot();
								// dialog.click();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return true;
						} else {
							return false;
						}

					}
				});

		// UiDevice.getInstance().removeWatcher(watcherName);
	}
	public UiObject getChildView(String rootClass,String fatherClass,String mText,String objClass) {
		//get parent uiobject
		UiObject rootObject = new UiObject(new UiSelector().className(rootClass));
		if(!rootObject.exists()){
			rootObject=new UiObject(new UiSelector().resourceId(rootClass));
		}
		if(!rootObject.exists())
			return null;
		try {
			int childCount=rootObject.getChildCount();
			for (int i=0;i<childCount;i++){
				UiObject child=rootObject.getChild(new UiSelector().className(fatherClass).instance(i));
				UiObject childText=rootObject.getChild(new UiSelector().textContains(mText));
				if(childText.exists()){
					UiObject result=child.getChild(new UiSelector().className(objClass));
					if(!result.exists()){
						result=child.getChild(new UiSelector().resourceId(objClass));
					}					
					if(result.exists())
					{
						return result;
					}
				}
			}
			
		} catch (UiObjectNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
