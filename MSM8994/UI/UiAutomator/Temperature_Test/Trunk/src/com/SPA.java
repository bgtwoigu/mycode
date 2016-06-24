package com;

import java.io.File;
import java.io.IOException;

import android.graphics.Rect;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

public class SPA {
	public static final String SET="Set";
	public static final String CHECK="Check";
	public static String currentTask=SET;

	
	public static String getVersion() throws UiObjectNotFoundException
	{
		Dailer.start();
		Dailer.enter("*#1234#");
		PF.waitForId("oppo:id/parentPanel", 5000);
		String v=PF.findObjectByRid("android:id/message", 1000).getText();
		PF.findObjectByRid("android:id/button1", 1000).click();
		PF.hold(1000);
		UiDevice.getInstance().pressBack();
		PF.hold(500);
		UiDevice.getInstance().pressHome();
		PF.hold(500);
		return v;
	}
	public static class Dailer{
		private static String CN="Dailer";
		private static String pkM="com.android.dialer";
		private static String acM="com.android.dialer.DialtactsActivity";
		
		//test methods to execute actions
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
		public static void itemAutoMess() throws UiObjectNotFoundException
		{
			CM.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
			CM.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView0", 2000);
			CM.findObjectByText("通话设置", 1000).click();
			CM.waitForId("android:id/up", 2000);
			CM.findObjectBySubText("拒接", 1000).click();
			CM.waitForId("oppo:id/MajorOperationButtonPositive", 2000);
			CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
			CM.waitForId("com.android.mms:id/embedded_text_editor", 2000);
			CM.findObjectByRid("com.android.mms:id/embedded_text_editor", 1000).setText("test message");
			CM.findObjectByRid("android:id/button1", 1000).click();
			CM.waitForId("android:id/up", 2000);
			//recover initial interface;
			CM.findObjectByRid("android:id/up", 1000).click();
			CM.waitForId("android:id/checkbox", 2000);
			CM.findObjectByRid("android:id/up", 1000).click();
			CM.waitForId("com.android.dialer:id/dialpad", 1000);

		}
		public static void dail(String txt,long time) throws UiObjectNotFoundException
		{
			enter(txt);
			CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
			CM.waitForText("免提", 2000);
			CM.hold(time);
			CM.findObjectByRid("com.android.incallui:id/endButton", 1000).click();
			CM.waitForId("com.android.dialer:id/dialpad", 1000);
		}
		public static void viewRecord() throws UiObjectNotFoundException
		{
			CM.findObjectByRid("com.android.dialer:id/secondary_action_icon", 1000).click();
			CM.waitForId("android:id/up", 2000);
			UiDevice.getInstance().pressBack();
		}
		public static void quit()
		{
			UiDevice.getInstance().pressBack();
		}
		//interface methods to execute basic function
		public static void enter(String txt) throws UiObjectNotFoundException
		{
			Rect re=PF.findObjectByRid("com.android.dialer:id/dialpad", 1000).getBounds();
			int l=re.left;
			int t=re.top;
			int w=re.width();
			int h=re.height();  
			for(int i=0;i<txt.length();i++)
			{
				switch(txt.charAt(i))
				{
				case '1':
					UiDevice.getInstance().click(l+w/6, t+h/8);
					break;
				case '2':
					UiDevice.getInstance().click(l+w/2, t+h/8);
					break;
				case '3':
					UiDevice.getInstance().click(l+w*5/6, t+h/8);
					break;
				case '4':
					UiDevice.getInstance().click(l+w/6, t+h*3/8);
					break;
				case '5':
					UiDevice.getInstance().click(l+w/2, t+h*3/8);
					break;
				case '6':
					UiDevice.getInstance().click(l+w*5/6, t+h*3/8);
					break;
				case '7':
					UiDevice.getInstance().click(l+w/6, t+h*5/8);
					break;
				case '8':
					UiDevice.getInstance().click(l+w/2, t+h*5/8);
					break;
				case '9':
					UiDevice.getInstance().click(l+w*5/6, t+h*5/8);
					break;
				case '*':
					UiDevice.getInstance().click(l+w/6, t+h*7/8);
					break;
				case '0':
					UiDevice.getInstance().click(l+w/2, t+h*7/8);
					break;
				case '#':
					UiDevice.getInstance().click(l+w*5/6, t+h*7/8);
					break;
				}
				PF.hold(500);
			}
		}
		
//		public static void enterNumber(String num)
//		{
//			
//		}
	}
	public static class Contacts{
		private static String CN="Contacts";
		private static String pkM="com.android.contacts";
		private static String acM="com.android.contacts.activities.PeopleActivity";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
		public static boolean createContact(String name,String number,String position) throws UiObjectNotFoundException
		{
			try
			{
				CM.findObjectByRid("oppo:id/MajorOperationButtonNegative", 1000).click();
				CM.waitForText("新建联系人", 1000);
				CM.findObjectByRid("com.android.contacts:id/location_changge_indicate", 1000).click();
				CM.waitForId("com.android.contacts:id/checkbox", 1000);
				CM.findObjectBySubText(position, 1000).click();
				CM.findObjectByRid("com.android.contacts:id/Add_photo_normal", 1000).click();
				CM.waitForText("拍照", 1000);
				CM.findObjectByText("拍照", 1000).click();
				CM.waitForNewApp("com.oppo.camera", 2000);
				CM.findObjectByRid("com.oppo.camera:id/shutter_button", 1000).click();
				CM.waitForId("com.oppo.camera:id/btn_done", 2000);
				CM.findObjectByRid("com.oppo.camera:id/btn_done", 1000).click();
				CM.waitForId("oppo:id/MajorOperationButtonPositive", 2000);
				CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
				CM.enterText(UiDevice.getInstance(), CM.findObjectByText("姓名", 1000), name);
				CM.enterText(UiDevice.getInstance(), CM.findObjectByText("电话", 1000), number);
				CM.findObjectByRid("com.android.contacts:id/right_btn", 1000).click();
				CM.waitForId("com.android.contacts:id/weather", 2000);
				CM.findObjectByRid("com.android.contacts:id/left_btn", 1000).click();
				CM.waitForId("android:id/search_src_text", 2000);
			}catch(Exception e)
			{
				return false;
			}
			return true;
		}
		public static void deleteContact(String name) throws UiObjectNotFoundException
		{
			while(!CM.waitForText(name, 1000))
				CM.scroll(UiDevice.getInstance(), CM.DOWN);
			CM.findObjectByText(name, 1000).dragTo(CM.findObjectByText(name, 1000), 5);
			CM.waitForId("com.android.contacts:id/menu_mark", 1000);
			CM.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
			CM.waitForText("确定", 1000);
			CM.findObjectByText("确定", 1000).click();
			CM.waitForId("android:id/search_src_text", 1000);
		}
		//增加常用联系人
		public static boolean addFavoriteContac() throws UiObjectNotFoundException
		{
			PF.findObjectByWholeText("常用", 4000).clickAndWaitForNewWindow();
			if(PF.waitForText("没有常用联系人", 4000))
			{
				PF.findObjectByRid("oppo:id/MajorOperationButtonNegative", 4000).clickAndWaitForNewWindow();
				if(!PF.findObjectByClassname("android.widget.CheckBox", 4000).isChecked())
				{
					PF.findObjectByRid("com.android.contacts:id/menu_mark", 4000).click();
				}
				UiObject sure=new UiObject(new UiSelector().text("确定"));
				if(!sure.isEnabled())
				{
					PF.hold(2000);
				}
				if(sure.isEnabled())
				{
					sure.clickAndWaitForNewWindow();
					PF.hold(1000);
					PF.findObjectByWholeText("联系人", 6000).clickAndWaitForNewWindow();
				}else{
					return false;
				}
			}
			return true;
		}
		
		//创建群组
		public static boolean createContactsGroup(String groupName) throws UiObjectNotFoundException
		{
			PF.findObjectByWholeText("群组", 4000).clickAndWaitForNewWindow();
			PF.findObjectByRid("oppo:id/MajorOperationButtonNegative", 4000).clickAndWaitForNewWindow();
			UiObject edit=new UiObject(new UiSelector().className("android.widget.EditText").resourceId("com.android.contacts:id/group_name"));
			if(PF.waitForObject(edit, 4000))
			{
				edit.setText(groupName);
				PF.hold(500);
				if(!PF.findObjectByWholeText("确定", 4000).isEnabled())
				{
					PF.hold(2000);
					if(!PF.findObjectByWholeText("确定", 4000).isEnabled())
					{
						return false;
					}
				}
				PF.findObjectByWholeText("确定", 4000).clickAndWaitForNewWindow();
				PF.findObjectByRid("com.android.contacts:id/menu_mark", 4000).click();
				PF.hold(1000);
				PF.findObjectByWholeText("确定", 4000).clickAndWaitForNewWindow();
				PF.hold(1000);
				PF.waitForWholeText("联系人", 15000);
				PF.findObjectByWholeText("联系人", 6000).clickAndWaitForNewWindow();
			}else{
				return false;
			}
			return true;
		}
		
		//更多
		public static boolean moreSetting(String select) throws UiObjectNotFoundException
		{
			PF.findObjectByRid("oppo:id/MajorOperationButtonMore", 4000).clickAndWaitForNewWindow();
			PF.findObjectByText("要显示的联系人", 4000).clickAndWaitForNewWindow();
			PF.findObjectByWholeText(select, 4000).clickAndWaitForNewWindow();
			PF.hold(1000);
			return true;
		}
	}
	
	//信息
	public static class Message{
		private static String CN="Setting";
		private static String pkM="com.android.mms";
		private static String acM="com.android.mms.ui.ConversationList";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
		
		//导入包含已读短信、未读彩信
		public static boolean importMes() throws UiObjectNotFoundException
		{
			PF.findObjectByRid("oppo:id/MajorOperationButtonMore", 4000).clickAndWaitForNewWindow();
			PF.findObjectByWholeText("设置", 4000).clickAndWaitForNewWindow();
			UiScrollable list=new UiScrollable(new UiSelector().resourceId("android:id/list"));
			PF.searchObjByWholeText(list, "短信导入导出").clickAndWaitForNewWindow();
			PF.findObjectByText("OTA", 4000).clickAndWaitForNewWindow();
			PF.findObjectByWholeText("恢复", 4000).clickAndWaitForNewWindow();
			PF.hold(2000);
			PF.waitForWholeText("导入短信", 50000);
			UiDevice.getInstance().pressBack();
			PF.waitForWholeText("设置", 5000);
			while(PF.waitForId("android:id/up", 3000))
			{
				PF.findObjectByRid("android:id/up", 3000).clickAndWaitForNewWindow();
				PF.hold(500);
			}
			return true;
		}
		
		//新建草稿
		public static boolean newDrafts(String number,String mText) throws UiObjectNotFoundException
		{
			PF.findObjectByWholeText("新建信息", 4000).clickAndWaitForNewWindow();
			PF.findObjectByRid("com.android.mms:id/recipients_editor", 4000).setText(number);
			PF.findObjectByRid("com.android.mms:id/embedded_text_editor", 4000).click();
			PF.hold(500);
			PF.findObjectByRid("com.android.mms:id/embedded_text_editor", 4000).click();
			PF.hold(1000);
			PF.findObjectByRid("com.android.mms:id/embedded_text_editor", 4000).setText(mText);
			PF.hold(1000);
			UiDevice.getInstance().pressBack();
			if(!PF.waitForWholeText("新建信息", 4000))
			{
				if(UiDevice.getInstance().getCurrentPackageName().contains("com.android.mms"))
				{
					UiDevice.getInstance().pressBack();
					if(!PF.waitForWholeText("新建信息", 4000))
					{
						return false;
					}
				}else{
					return false;
				}
			}
			return true;
		}
		
		
		//定时短信
		public static boolean timingMes(String number,String mText) throws UiObjectNotFoundException
		{
			PF.findObjectByWholeText("新建信息", 4000).clickAndWaitForNewWindow();
			PF.findObjectByRid("com.android.mms:id/recipients_editor", 4000).setText(number);
			PF.findObjectByRid("com.android.mms:id/embedded_text_editor", 4000).click();
			PF.hold(500);
			PF.findObjectByRid("com.android.mms:id/embedded_text_editor", 4000).click();
			PF.hold(1000);
			PF.findObjectByRid("com.android.mms:id/embedded_text_editor", 4000).setText(mText);
			PF.findObjectByRid("com.android.mms:id/add_attach", 4000).clickAndWaitForNewWindow();
			PF.findObjectByWholeText("定时信息", 4000).clickAndWaitForNewWindow();
			PF.findObjectByRid("oppo:id/day", 4000).swipeUp(11);
			PF.findObjectByWholeText("确定", 4000).clickAndWaitForNewWindow();
			if(PF.findObjectByRid("com.android.mms:id/send_button", 4000).isEnabled())
			{
				PF.findObjectByRid("com.android.mms:id/send_button", 4000).clickAndWaitForNewWindow();
				UiDevice.getInstance().pressBack();
				if(!PF.waitForWholeText("新建信息", 4000))
				{
					if(UiDevice.getInstance().getCurrentPackageName().contains("com.android.mms"))
					{
						UiDevice.getInstance().pressBack();
						if(!PF.waitForWholeText("新建信息", 4000))
						{
							return false;
						}
					}else{
						return false;
					}
				}
			}else{
				return false;
			}
			return true;
		}
		
		//短信设置——个性签名
		public static boolean signature(String mText) throws UiObjectNotFoundException
		{
			PF.findObjectByRid("oppo:id/MajorOperationButtonMore", 4000).clickAndWaitForNewWindow();
			PF.findObjectByWholeText("设置", 4000).clickAndWaitForNewWindow();
			UiScrollable root=new UiScrollable(new UiSelector().resourceId("android:id/list"));
			UiObject sign=PF.getObjBySameRowText(root, "android.widget.LinearLayout", "个性签名", "com.oppo.widget.OppoSwitch");
			if(!sign.isChecked())
			{
				sign.click();
				PF.hold(1000);
			}
			if(!PF.findObjectByWholeText("个性签名设置", 4000).isEnabled())
			{
				PF.hold(2000);
				if(!sign.isChecked())
				{
					sign.click();
					PF.hold(1000);
				}
				if(!PF.findObjectByWholeText("个性签名设置", 4000).isEnabled())
				{
					return false;
				}
			}
			PF.findObjectByWholeText("个性签名设置", 4000).clickAndWaitForNewWindow();
			if(!PF.waitForClass("android.widget.EditText", 4000))
			{
				return false;
			}
			//EditText可能存在位置不准确的问题
			PF.findObjectByClassname("android.widget.EditText", 4000).click();
			PF.hold(2000);
			while(!PF.waitForClass("android.widget.EditText", 2000))
			{
				PF.findObjectByWholeText("个性签名设置", 4000).clickAndWaitForNewWindow();
				PF.hold(2000);
				PF.findObjectByClassname("android.widget.EditText", 4000).click();
				PF.hold(1000);
			}
			PF.findObjectByClassname("android.widget.EditText", 4000).clearTextField();
			PF.findObjectByClassname("android.widget.EditText", 4000).setText(mText);
			PF.findObjectByWholeText("确定", 4000).clickAndWaitForNewWindow();
			while(PF.waitForId("android:id/up", 3000))
			{
				PF.findObjectByRid("android:id/up", 3000).clickAndWaitForNewWindow();
				PF.hold(500);
			}
//			UiDevice.getInstance().pressBack();
//			if(!PF.waitForWholeText("新建信息", 4000))
//			{
//				if(UiDevice.getInstance().getCurrentPackageName().contains("com.android.mms"))
//				{
//					UiDevice.getInstance().pressBack();
//					if(!PF.waitForWholeText("新建信息", 4000))
//					{
//						return false;
//					}
//				}else{
//					return false;
//				}
//			}
			return true;
		}
	}
	
	public static class Setting{
		private static String CN="Setting";
		private static String pkM="com.android.settings";
		private static String acM="com.oppo.settings.SettingsActivity";
		public static int wfID=0;
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
		public static void getIndex() throws UiObjectNotFoundException{
			UiObject list=PF.findObjectByRid("android:id/list", 1000);
			for(int i=0;i<list.getChildCount();i++)
			{
				list.getChild(new UiSelector().index(i).className("android.widget.LinearLayout")).click();
				if(PF.waitForId("com.android.settings:id/pager", 1000))
					continue;
				if(PF.waitForId("android:id/up", 500)&&PF.waitForText("WLAN", 500)&&PF.waitForClass("com.oppo.widget.OppoSwitch", 500))
				{
					wfID=i;
					PF.findObjectByRid("android:id/up", 500).click();
					PF.waitForId("com.android.settings:id/pager", 1000);
					break;
				}
				else
				{
					PF.findObjectByRid("android:id/up", 500).click();
					PF.waitForId("com.android.settings:id/pager", 1000);
				}
			}
		}
//		public static void openWifi() throws UiObjectNotFoundException
//		{
//			PF.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(wfID).className("android.widget.LinearLayout")).click();
//			PF.waitForClass("com.oppo.widget.OppoSwitch", 1000);
//			PF.findObjectByClassname("com.oppo.widget.OppoSwitch", 1000).click();
//			PF.findObjectByRid("android:id/up", 500).click();
//			PF.waitForId("com.android.settings:id/pager", 1000);
//		}
		
		//打开或关闭wifi
		public static boolean openWifi(boolean isOpen) throws UiObjectNotFoundException
		{
			UiObject wifi=new UiObject(new UiSelector().className("com.oppo.widget.OppoSwitch"));
			if(isOpen)
			{
				if(!wifi.isChecked())
				{
					wifi.click();
					if(PF.waitForWholeText("安全警告", 3000))
					{
						if(PF.waitForClass("android.widget.CheckBox", 3000))
						{
							if(!PF.findObjectByClassname("android.widget.CheckBox", 3000).isChecked())
							{
								PF.findObjectByClassname("android.widget.CheckBox", 3000).click();
								PF.hold(500);
							}
						}
						PF.findObjectByWholeText("确定", 3000).clickAndWaitForNewWindow();
					}
					if(!wifi.isChecked())
					{
						return false;
					}
				}
			}else{
				if(wifi.isChecked())
				{
					wifi.click();
					if(PF.waitForWholeText("安全警告", 3000))
					{
						if(PF.waitForClass("android.widget.CheckBox", 3000))
						{
							if(!PF.findObjectByClassname("android.widget.CheckBox", 3000).isChecked())
							{
								PF.findObjectByClassname("android.widget.CheckBox", 3000).click();
								PF.hold(500);
							}
						}
						PF.findObjectByWholeText("确定", 3000).clickAndWaitForNewWindow();
					}
					if(wifi.isChecked())
					{
						return false;
					}
				}
			}
			return true;
		}
		
		//蓝牙开关
		public static boolean openBluetooth(boolean isOpen) throws UiObjectNotFoundException
		{
			UiObject bar=new UiObject(new UiSelector().resourceId("android:id/action_bar"));
			UiObject bluetooth=bar.getChild(new UiSelector().className("com.oppo.widget.OppoSwitch"));
			if(isOpen)
			{
				if(!bluetooth.isChecked())
				{
					bluetooth.click();
					PF.hold(2000);
					if(!bluetooth.isChecked())
					{
						return false;
					}
				}
			}else{
				if(bluetooth.isChecked())
				{
					bluetooth.click();
					PF.hold(2000);
					if(bluetooth.isChecked())
					{
						return false;
					}
				}
			}
			return true;
		}
	}
	
	public static class Email{
		//com.android.email/.activity.Welcome 
		public static String CN="Email";
		public static String pkM="com.android.email";
//		public static String acM="com.android.email.activity.setup.AccountSetupBasics";
		public static String acM="com.android.email.activity.Welcome ";
		
		private static String reportPath="";
//		private static
		
		private static String accountF="qetest";		
		private static String signatureF="qesignature";
		private static String draftF="qedraft";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
		
		
//		public static void start() throws UiObjectNotFoundException
//		{
//			PF.startActivity(pkM, acM);
//			PF.waitForNewApp(pkM, 5000);
//		}
		public static boolean login(String account,String password,String name) throws UiObjectNotFoundException
		{
			PF.findObjectByRid("com.android.email:id/account_email", 1000).setText(account);
			PF.findObjectByRid("com.android.email:id/account_password", 1000).setText(password);
			PF.findObjectByRid("com.android.email:id/account_description", 1000).setText(name);
			PF.hold(1000);
			PF.findObjectByRid("com.android.email:id/btn_email_login", 1000).click();
			//账号重复
			if(PF.waitForText("帐号重复", 6000))
			{
				PF.findObjectByWholeText("确定", 3000).clickAndWaitForNewWindow();
				return false;
			}else{
				while(PF.waitForClass("android.widget.ProgressBar", 4000))
				{
					PF.hold(4000);
				}
				if(PF.waitForId("com.android.email:id/account_custom_view", 90000))
				{
					return true;
				}else{
					return false;
				}
			}
			
//			PF.enterText(UiDevice.getInstance(), PF.findObjectByRid("com.android.email:id/account_email", 1000), "oppotext@163.com");
//    		PF.enterText(UiDevice.getInstance(), PF.findObjectByRid("com.android.email:id/account_password", 1000), "86076999");
//    		PF.enterText(UiDevice.getInstance(), PF.findObjectByRid("com.android.email:id/account_description", 1000), accountF);
//    		PF.findObjectByRid("com.android.email:id/btn_email_login", 1000).click();
//    		PF.waitForId("com.android.email:id/account_custom_view", 90000);
		}
		public static void signature() throws UiObjectNotFoundException
		{
			PF.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
			PF.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000);
			PF.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
			PF.waitForId("android:id/up", 1000);
			PF.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(1).className("android.widget.LinearLayout")).click();
			PF.waitForId("android:id/checkbox", 1000);
			PF.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(2).className("android.widget.LinearLayout")).click();
			PF.waitForId("android:id/edit", 1000);
			PF.enterText(UiDevice.getInstance(), PF.findObjectByRid("android:id/edit", 1000), "qesignature");
			PF.findObjectByRid("android:id/button1", 1000).click();
			PF.waitForId("android:id/up", 1000);
			PF.findObjectByRid("android:id/up", 1000).click();
			PF.waitForId("oppo:id/MajorOperationButtonMore", 1000);
			PF.findObjectByRid("android:id/up", 1000).click();
			PF.waitForId("com.android.email:id/account_custom_view", 5000);			
		}
		public static void createDraft() throws UiObjectNotFoundException
		{
			PF.findObjectByRid("oppo:id/MajorOperationButtonPositive", 1000).click();
			PF.waitForId("com.android.email:id/to", 2000);
			PF.enterText(UiDevice.getInstance(), PF.findObjectByRid("android:id/edit", 1000), "qedraft");
			PF.findObjectByRid("android:id/up", 1000).click();
			PF.waitForId("android:id/button1", 1000);
			PF.findObjectByRid("android:id/button1", 1000).click();
			PF.waitForId("com.android.email:id/account_custom_view", 2000);	
		}
		public static void quit()
		{
			UiDevice.getInstance().pressBack();
		}
		public static void check(String reportPath,String snapPath) throws UiObjectNotFoundException
		{
			try
			{
			PF.startActivity(pkM, acM);//check having login
			if(PF.waitForId("com.android.email:id/account_custom_view", 5000))
			{
				record(currentTask+" Login email OK",reportPath+"/Logined.png",false,"");					
			}
			else
			{
				record(currentTask+" Login email Fail",reportPath+"/Logined.png",true,snapPath);
			}
			PF.findObjectByRid("oppo:id/MajorOperationButtonMore", 1000).click();
			PF.waitForId("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000);
			PF.findObjectByRid("oppo:id/OppoOptionMenuBarGalleryItemTextView1", 1000).click();
			PF.waitForId("android:id/up", 1000);
			if(PF.waitForText(accountF, 1000))
			{
				record(currentTask+" Set account description OK",reportPath,false,"");
			}
			else
			{
				record(currentTask+" Set account description Fail",reportPath,false,"");
			}
			PF.findObjectByRid("android:id/list", 1000).getChild(new UiSelector().index(1).className("android.widget.LinearLayout")).click();
			PF.waitForId("android:id/checkbox", 1000);
			if(PF.waitForText(signatureF, 1000))
			{
				record(currentTask+" Set signature OK",reportPath,false,"");
			}
			else
			{
				record(currentTask+" Set signature Fail",reportPath,false,"");
			}
			PF.findObjectByRid("android:id/up", 1000).click();
			PF.waitForId("oppo:id/MajorOperationButtonPositive", 1000);
			PF.findObjectByRid("android:id/up", 1000).click();
			PF.waitForId("com.android.email:id/account_custom_view", 2000);
			PF.findObjectByRid("com.android.email:id/img_account_entry", 1000).click();
			PF.waitForId("com.android.email:id/mailbox_layout", 1000);
			PF.findObjectByClassname("android.widget.ListView", 1000).getChild(new UiSelector().index(2).className("android.widget.RelativeLayout")).click();
			PF.waitForId("android:id/search_src_text", 1000);
			if(PF.waitForText(draftF, 1000))
			{
				record(currentTask+" Set draft OK",reportPath,false,"");
			}
			else
			{
				record(currentTask+" Set draft Fail",reportPath,false,"");
			}
			UiDevice.getInstance().pressBack();
			UiDevice.getInstance().pressBack();
			}catch(Exception e)
			{
				record(currentTask+" Fail",reportPath,true,snapPath);
			}
		}
	}
	
	public static class Calendar{
		public static String CN="Calendar";
		public static String pkM="com.android.calendar";
		public static String acM="com.android.calendar.MonthActivity";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
		
		public static boolean NewEvent() throws UiObjectNotFoundException{
			PF.findObjectByWholeText("日程", 4000).clickAndWaitForNewWindow();
			PF.findObjectByWholeText("新建日程", 4000).clickAndWaitForNewWindow();
			PF.findObjectByWholeText("事件名称", 4000).setText(PF.getString(5, 15));
			PF.findObjectByRid("com.android.calendar:id/start_date", 4000).clickAndWaitForNewWindow();
			PF.findObjectByRid("oppo:id/day", 4000).swipeUp(11);
			PF.hold(500);
			PF.findObjectByWholeText("确定", 4000).clickAndWaitForNewWindow();
			PF.findObjectByRid("com.android.calendar:id/save", 4000).clickAndWaitForNewWindow();
			if(PF.waitForWholeText("日程提醒", 3000))
			{
				PF.findObjectByWholeText("取消", 4000).clickAndWaitForNewWindow();
			}
			UiDevice.getInstance().pressBack();
			if(!PF.waitForId("com.android.calendar:id/day0", 4000))
			{
				UiDevice.getInstance().pressBack();
				if(!PF.waitForId("com.android.calendar:id/day0", 4000))
				{
					return false;
				}
			}
			return true;
		}
	}
	
	public static class Clock{
		public static String CN="Calendar";
		public static String pkM="com.oppo.alarmclock";
		public static String acM="com.oppo.alarmclock.AlarmClock";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
		
		//新建一个闹钟
		public static void newAlarm() throws UiObjectNotFoundException{
			PF.findObjectByWholeText("新建闹钟", 4000).clickAndWaitForNewWindow();
			PF.findObjectByRid("com.oppo.alarmclock:id/save", 4000).clickAndWaitForNewWindow();
			PF.waitForWholeText("新建闹钟", 4000);
		}
		
		//删除一个世界时钟
		public static boolean delWorldClocl() throws UiObjectNotFoundException{
			PF.findObjectByWholeText("世界时钟", 4000).clickAndWaitForNewWindow();
			PF.findObjectByRid("oppo:id/MajorOperationButtonMore", 4000).clickAndWaitForNewWindow();
			PF.findObjectByWholeText("删除", 4000).clickAndWaitForNewWindow();
			PF.findObjectByClassname("android.widget.CheckBox", 4000).click();
			PF.hold(1000);
			if(!PF.findObjectByWholeText("删除", 4000).isEnabled())
			{
				PF.hold(3000);
				if(!PF.findObjectByWholeText("删除", 4000).isEnabled())
				{
					return false;
				}
			}
			PF.findObjectByWholeText("删除", 4000).clickAndWaitForNewWindow();
			PF.findObjectByWholeText("确定", 4000).clickAndWaitForNewWindow();
			PF.hold(1000);
			return true;
		}
		
		//增加一个世界时钟
		public static void addCity() throws UiObjectNotFoundException
		{
			PF.findObjectByWholeText("世界时钟", 4000).clickAndWaitForNewWindow();
			PF.findObjectByWholeText("添加城市", 4000).clickAndWaitForNewWindow();
			PF.findObjectByRid("com.oppo.alarmclock:id/allCities", 4000).getChild(new UiSelector().className("android.widget.TextView").index(0)).clickAndWaitForNewWindow();
			PF.findObjectByWholeText("闹钟", 4000).clickAndWaitForNewWindow();
		}
	}
	
	public static class Weather{
		public static String CN="Weather";
		public static String pkM="com.oppo.weather";
		public static String acM="com.oppo.weather.OppoMainActivity";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
		
		//新增一个城市——真坑uiautomator不支持中文输入，曲线救国吧
		public static boolean addCity(String province,String city,String county) throws UiObjectNotFoundException{
			UiDevice.getInstance().pressMenu();
			if(!PF.waitForWholeText("城市管理", 5000))
			{
				UiDevice.getInstance().pressMenu();
				if(!PF.waitForWholeText("城市管理", 5000)){
					return false;
				}
			}
			PF.findObjectByWholeText("城市管理", 4000).clickAndWaitForNewWindow();
			if(!PF.waitForText("添加关注地", 4000))
			{
				return false;
			}
			PF.findObjectByWholeText("添加关注地", 4000).clickAndWaitForNewWindow();
			
			UiScrollable list=new UiScrollable(new UiSelector().className("android.widget.ScrollView").resourceId("com.oppo.weather:id/city_add_province"));
			PF.searchObjByWholeText(list,province).clickAndWaitForNewWindow();
			if(PF.waitForId("com.oppo.weather:id/city_add_list", 4000))
			{
				UiObject cobj=new UiObject(new UiSelector().resourceIdMatches("/(?!.*city)^.*$/").text(city));
				cobj.clickAndWaitForNewWindow();
				if(county!=null||county!="")
				{
					UiObject mcobj=new UiObject(new UiSelector().resourceIdMatches("/(?!.*city)^.*$/").text(city));
					mcobj.clickAndWaitForNewWindow();
				}
			}else{
				return false;
			}
//			PF.findObjectByRid("android:id/search_src_text", 4000).clickAndWaitForNewWindow();
//			PF.findObjectByRid("android:id/search_src_text", 4000).setText(cityName);
//			PF.findObjectByRid("com.oppo.weather:id/city_name", 4000).clickAndWaitForNewWindow();
			//考虑该城市可能已存在的情况
			if(PF.waitForText("城市管理", 3000))
			{
				UiDevice.getInstance().pressBack();
			}else{
				if(PF.waitForClass("android.widget.EditText", 3000))
				{
					UiDevice.getInstance().pressBack();
					PF.hold(1000);
					UiDevice.getInstance().pressBack();
					PF.hold(1000);
					if(!PF.waitForWholeText("今天", 5000))
					{
						UiDevice.getInstance().pressBack();
						if(!PF.waitForWholeText("今天", 5000))
						{
							return false;
						}
					}
				}
			}
			return true;
		}
		
		//修改温标
		public static boolean modifyThermometricScale(String scale) throws UiObjectNotFoundException
		{
			UiDevice.getInstance().pressMenu();
			if(!PF.waitForWholeText("城市管理", 5000))
			{
				UiDevice.getInstance().pressMenu();
				if(!PF.waitForWholeText("城市管理", 5000)){
					return false;
				}
			}
			PF.findObjectByWholeText("设置", 4000).clickAndWaitForNewWindow();
			PF.findObjectByWholeText("温标设置", 4000).clickAndWaitForNewWindow();
			if(scale.contains("F")||scale.contains("f"))
			{
				PF.findObjectByText("华氏度", 4000).click();
			}else{
				PF.findObjectByText("摄氏度", 4000).click();
			}
			PF.hold(500);
			PF.findObjectByRid("android:id/up", 4000).clickAndWaitForNewWindow();
			PF.waitForWholeText("设置", 3000);
			PF.findObjectByRid("android:id/up", 4000).clickAndWaitForNewWindow();
			PF.waitForText("今天", 3000);
			return true;
		}
	}
	
	public static class Notes{
		public static String CN="Notes";
		public static String pkM="com.nearme.note";
		public static String acM="com.nearme.note.view.AllNoteActivity";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
		//新建普通笔记并保存
		public static void creatNote(String mContext) throws UiObjectNotFoundException
		{
			PF.findObjectByWholeText("新建笔记", 4000).clickAndWaitForNewWindow();
			PF.findObjectByRid("com.nearme.note:id/textnote_content", 4000).setText(mContext);
			PF.hold(1000);
			PF.findObjectByRid("com.nearme.note:id/delete_or_save", 4000).clickAndWaitForNewWindow();
		}
		
		//涂鸦笔记
		public static void creatTuya() throws UiObjectNotFoundException
		{
			PF.findObjectByWholeText("新建笔记", 4000).clickAndWaitForNewWindow();
			PF.findObjectByWholeText("涂鸦", 4000).clickAndWaitForNewWindow();
			UiObject tuya=new UiObject(new UiSelector().resourceId("com.nearme.note:id/tuya_view"));
			Rect bounds=tuya.getVisibleBounds();
			int[] location=new int[4];
			location[0]=bounds.left+20;
			location[1]=bounds.right-20;
			location[2]=bounds.top+20;
			location[3]=bounds.bottom-20;
			for(int i=0;i<5;i++)
			{
				UiDevice.getInstance().drag(PF.getRandomNumBetween(location[0], location[1]), PF.getRandomNumBetween(location[3], location[4]), PF.getRandomNumBetween(location[0], location[1]), PF.getRandomNumBetween(location[3], location[4]), 5);
				PF.hold(500);
				UiDevice.getInstance().click(PF.getRandomNumBetween(location[0], location[1]), PF.getRandomNumBetween(location[3], location[4]));
				PF.hold(500);
			}
			PF.findObjectByRid("com.nearme.note:id/tuya_save_btn", 5000).clickAndWaitForNewWindow();
			PF.findObjectByRid("com.nearme.note:id/delete_or_save", 5000).clickAndWaitForNewWindow();
		}
	}
	
	public static class Camera{
		public static String CN="Camera";
		public static String pkM="com.oppo.camera";
		public static String acM="com.oppo.camera.Camera";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
		
		//调焦
	    public static void zoom(boolean expandZoom,int percent) throws UiObjectNotFoundException
	    {
	    	UiObject zoom=PF.findObjectByRid("com.oppo.camera:id/tracker_gesture_view", 6000);
	    	if(expandZoom)
	    	{
	    		zoom.pinchOut(percent, 40);
	    	}else{
	    		zoom.pinchIn(percent, 40);
	    	}
	    	PF.hold(500);
	    }
	    
	    //摄像
	    public static boolean videoRecord(int time) throws UiObjectNotFoundException
	    {
	    	PF.findObjectByRid("com.oppo.camera:id/video_shutter_button", 600).click();
	    	PF.hold(1500);
	    	if(!PF.waitForId("com.oppo.camera:id/video_recording_time", 4000))
	    	{
	    		return false;
	    	}
	    	PF.hold(time-1000);
	    	PF.findObjectByRid("com.oppo.camera:id/video_shutter_button", 600).click();
	    	PF.hold(2000);
	    	if(!PF.waitForWholeDesc("相机设置", 4000))
	    	{
	    		return false;
	    	}
	    	return true;
	    }
	}
	
	public static class Photos{
		public static String CN="Photos";
		public static String pkM="com.oppo.gallery3d";
		public static String acM="com.oppo.gallery3d.app.Gallery";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
		
	}
	
	
	public static class Music{
		public static String CN="Music";
		public static String pkM="com.oppo.music";
		public static String acM="com.oppo.music.MediaPlaybackActivity";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
		
		//新建播放列表
		public static boolean creatPlayList(String listName) throws UiObjectNotFoundException
		{
			PF.findObjectByWholeText("新建列表", 4000).clickAndWaitForNewWindow();
			if(PF.waitForClass("android.widget.EditText", 4000))
			{
				PF.findObjectByRid("com.oppo.music:id/playlist", 4000).setText(listName);
			}else{
				return false;
			}
			return true;
		}
		
		
	}
	
	public static class Video{
		public static String CN="Video";
		public static String pkM="com.android.video";
		public static String acM="com.android.video.MoviePlayerActivity";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
	}
	
	public static class Browser{
		public static String CN="Browser";
		public static String pkM="com.android.browser";
		public static String acM="com.android.browser.BrowserActivity";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
	}
	
	public static class Files{
		public static String CN="Files";
		public static String pkM="com.oppo.filemanager";
		public static String acM="com.oppo.filemanager.Main";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
		
		//进手机存储——判断是否插SD卡
		public static void enterStorage(boolean isSd) throws UiObjectNotFoundException
		{
			UiObject root=new UiObject(new UiSelector().resourceId("android:id/action_bar_container"));
			UiObject child=root.getChild(new UiSelector().textContains("存储"));
			child.clickAndWaitForNewWindow();
			if(PF.waitForWholeText("存储卡", 3000))
			{
				if(isSd)
				{
					PF.findObjectByWholeText("存储卡", 4000).clickAndWaitForNewWindow();
				}else{
					PF.findObjectByWholeText("手机存储", 4000).clickAndWaitForNewWindow();
				}
			}
		}
		
		//新建文件夹
		public static void newFiles(String fileName) throws UiObjectNotFoundException
		{
			PF.findObjectByWholeText("新建文件夹", 4000).clickAndWaitForNewWindow();
			PF.hold(500);
			PF.findObjectByRid("com.oppo.filemanager:id/file_name_edit", 4000).click();
			PF.hold(1000);
			PF.findObjectByRid("com.oppo.filemanager:id/file_name_edit", 4000).clearTextField();
			PF.findObjectByRid("com.oppo.filemanager:id/file_name_edit", 4000).setText(fileName);
			PF.findObjectByWholeText("确定", 4000).clickAndWaitForNewWindow();
			if(PF.waitForText("已存在", 3000)&&!PF.waitForId("oppo:id/search_src_text", 3000))
			{
				PF.findObjectByWholeText("取消", 4000).clickAndWaitForNewWindow();
			}
		}
		
		//移动/复制文件
		public static boolean moveTo(boolean isSD,String toFile,boolean isCopy) throws UiObjectNotFoundException
		{
			if(isCopy)
			{
				PF.findObjectByWholeText("复制", 4000).clickAndWaitForNewWindow();
			}else{
				PF.findObjectByWholeText("移动", 4000).clickAndWaitForNewWindow();
			}
			if(PF.waitForWholeText("手机存储", 3000)&&PF.waitForWholeText("存储卡", 3000))
			{
				if(isSD)
				{
					PF.findObjectByWholeText("存储卡", 4000).clickAndWaitForNewWindow();
				}else{
					PF.findObjectByWholeText("手机存储", 4000).clickAndWaitForNewWindow();
				}
			}
			UiObject root=new UiObject(new UiSelector().resourceId("com.oppo.filemanager:id/folder_list_view_container"));
			UiScrollable list=(UiScrollable) root.getChild(new UiSelector().className("android.widget.ListView"));
			PF.searchObjByWholeText(list, toFile).clickAndWaitForNewWindow();
			PF.findObjectByWholeText("粘贴", 4000).clickAndWaitForNewWindow();
			//等待进度条
			return true;
		}
		
		//文件保险箱
		public static boolean fileSafe() throws UiObjectNotFoundException
		{
			PF.findObjectByWholeText("分类视图", 4000).clickAndWaitForNewWindow();
			PF.findObjectByWholeText("文件保险箱", 4000).clickAndWaitForNewWindow();
			//选择密码形式
			if(PF.waitForText("选择密码形式", 4000))
			{
				PF.findObjectByWholeText("数字密码", 4000).clickAndWaitForNewWindow();
				if(PF.waitForText("请输入密码", 4000))
				{
					PF.findObjectByRid("com.oppo.filemanager:id/passwordinput1", 4000).setText("0");
					PF.findObjectByRid("com.oppo.filemanager:id/passwordinput2", 4000).setText("0");
					PF.findObjectByRid("com.oppo.filemanager:id/passwordinput3", 4000).setText("0");
					PF.findObjectByRid("com.oppo.filemanager:id/passwordinput4", 4000).setText("0");
					if(PF.waitForText("请确认新密码", 4000))
					{
						PF.findObjectByRid("com.oppo.filemanager:id/passwordinput1", 4000).setText("0");
						PF.findObjectByRid("com.oppo.filemanager:id/passwordinput2", 4000).setText("0");
						PF.findObjectByRid("com.oppo.filemanager:id/passwordinput3", 4000).setText("0");
						PF.findObjectByRid("com.oppo.filemanager:id/passwordinput4", 4000).setText("0");
					}else{
						return false;
					}
					if(PF.waitForText("设置安全问题", 4000))
					{
//						PF.findObjectByWholeText("跳过", 3000)
						PF.findObjectByRid("com.oppo.filemanager:id/question_content", 4000).setText("test");
						PF.hold(500);
						PF.findObjectByRid("com.oppo.filemanager:id/answer_content", 4000).setText("test");
						PF.findObjectByWholeText("确定", 4000).clickAndWaitForNewWindow();
					}
				}
			}
			while(UiDevice.getInstance().getCurrentPackageName().contains("com.oppo.filemanager")&&!PF.waitForText("分类视图", 3000))
			{
				UiDevice.getInstance().pressBack();
				PF.hold(1000);
			}
			return true;
		}
		
		//登录网盘	//云相册账号：uitest11 密码0123456789 手机号：13424703909
//		public static boolean loginSkyDrive() throws UiObjectNotFoundException
//		{
//			PF.findObjectByWholeText("网盘", 4000).clickAndWaitForNewWindow();
//			PF.findObjectByWholeText("登录", 4000).clickAndWaitForNewWindow();
//			PF.findObjectByRid("com.oppo.service.account:id/activity_login_et_name", 4000).setText("uitest11");
//			PF.findObjectByRid("com.oppo.service.account:id/activity_login_et_pwd", 4000).setText("0123456789");
//			
//		}
	}
	
	public static class SoundRecording{
		public static String CN="Record";
		public static String pkM="oppo.multimedia.soundrecorder";
		public static String acM="oppo.multimedia.soundrecorder.RecorderActivity";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
		
		//录音
		public static boolean soundRecording(int time) throws UiObjectNotFoundException
		{
			if(PF.waitForText("录音准备中", 4000))
			{
				PF.findObjectByRid("oppo:id/MajorOperationButtonNegative", 4000).click();
				if(!PF.waitForText("正在录音", 3000))
				{
					return false;
				}
				PF.hold(time);
				PF.findObjectByRid("oppo:id/MajorOperationButtonPositive", 4000).click();
			}else{
				return false;
			}
			return true;
		}
	}
	
	//桌面
	public static class Launcher{
		//按menu键后查找控件
//		public static UiObject searchWidgetByText(String mText) throws UiObjectNotFoundException
//		{
//			UiObject child=null;
//			UiObject list=new UiObject(new UiSelector().resourceId("com.oppo.launcher:id/toggle_bar"));
//			UiObject next=new UiObject(new UiSelector().resourceId("com.oppo.launcher:id/right_instruction"));
//			//此处不能用getchild的gettext方法，因为界面只完整显示3个child，第4个child会显示一个边能搜索出来，但无法点击
//			for(int i=0;i<3;i++)
//			{
//				UiObject childText=list.getChild(new UiSelector().className("android.widget.TextView").instance(i));
//				if(childText.exists())
//				{
//					if(childText.getText().contains(mText))
//					{
//						child=childText.getFromParent(new UiSelector().className("android.widget.ImageView"));
//						return child;
//					}
//				}
//			}
//			while(next.exists())
//			{
//				PF.swipeLeft(list, 11);
//				PF.hold(1000);
//				for(int i=0;i<3;i++)
//				{
//					UiObject childText=list.getChild(new UiSelector().className("android.widget.TextView").instance(i));
//					if(childText.exists())
//					{
//						if(childText.getText().contains(mText))
//						{
//							child=childText.getFromParent(new UiSelector().className("android.widget.ImageView"));
//							return child;
//						}
//					}
//				}
//			}
//			return child;
//		}
	}
	
	//WPS
	public static class Wps{
		public static String CN="WPS";
		public static String pkM="cn.wps.moffice_eng";
		public static String acM="cn.wps.moffice.documentmanager.DocumentManager";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
	}
	
	//OPPO乐园
	public static class OPPOTribune{
		public static String CN="OPPOTribune";
		public static String pkM="com.oppo.community";
//		public static String acM="com.oppo.community.OppoTribuneMainActivity";
		public static String acM="com.oppo.community.InitActivity";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
	}
	
	//软件商店
	public static class Market{
		public static String CN="Market";
		public static String pkM="com.oppo.market";
		public static String acM="com.oppo.market.activity.MainActivity";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
	}
	
	//阅读
	public static class Reader{
		public static String CN="Reader";
		public static String pkM="com.oppo.reader";
		public static String acM="com.zhangyue.iReader.bookshelf.ui.ActivityBookShelf";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
	}
	
	//游戏中心
	public static class GameCenter{
		public static String CN="GameCenter";
		public static String pkM="com.nearme.gamecenter";
//		public static String acM="com.nearme.gamecenter/.activity.MainTabPageActivity";
		public static String acM="com.nearme.gamecenter.activity.LoginActivity";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
	}
	
	//QQ
	public static class QQ{
		public static String CN="QQ";
		public static String pkM="com.tencent.mobileqq";
		public static String acM="com.tencent.mobileqq.activity.SplashActivity";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
	}
	
	//微信
	public static class Weixin{
		public static String CN="Weixin";
		public static String pkM="com.tencent.mm";
		public static String acM="com.tencent.mm.ui.LauncherUI";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
	}
	
	//微博
	public static class Weibo{
		public static String CN="Weibo";
		public static String pkM="com.sina.weibo";
		public static String acM="com.sina.weibo.SplashActivity";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
	}
	
	//网易新闻
	public static class NetEaseNews{
		public static String CN="NetEaseNews";
		public static String pkM="com.netease.newsreader.activity";
		public static String acM="com.netease.nr.biz.ad.AdActivity";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
	}
	
	//搜狐视频
	public static class SouhuVedio{
		public static String CN="SouhuVedio";
		public static String pkM="com.sohu.sohuvideo";
		public static String acM="com.sohu.sohuvideo.FirstNavigationActivityGroup";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
	}
	
	//百度地图
	public static class BaiduMap{
		public static String CN="BaiduMap";
		public static String pkM="com.baidu.BaiduMap";
		public static String acM="com.baidu.baidumaps.WelcomeScreen";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
	}
	
	//省电管家
//	public static class PowerManagement{
//		public static String CN="";
//		public static String pkM="";
//		public static String acM="";
//		
//		public static boolean start()
//		{
//			PF.startActivity(pkM, acM);
//			try
//			{
//				if(PF.waitForNewApp(pkM, 5000))
//					return true;
//				else
//					return false;
//			}catch(Exception e)
//			{
//				return false;
//			}
//		}
//	}
	
	//安全中心
	public static class SecurityCenter{
		public static String CN="SecurityCenter";
		public static String pkM="com.oppo.safe";
		public static String acM="com.oppo.safe.SecureSafeMainActivity";
		
		public static boolean start()
		{
			PF.startActivity(pkM, acM);
			try
			{
				if(PF.waitForNewApp(pkM, 5000))
					return true;
				else
					return false;
			}catch(Exception e)
			{
				return false;
			}
		}
	}
	

	public static void record(String mess,String reportPath,boolean doSnap,String snapPath)
	{
		try {
			PF.writeReport(reportPath, mess+"\r\n");
			if(doSnap)
				UiDevice.getInstance().takeScreenshot(new File(snapPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
