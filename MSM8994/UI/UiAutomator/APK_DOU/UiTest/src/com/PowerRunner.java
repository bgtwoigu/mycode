package com;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import com.QQ532.QQDemo;
import com.Weixin61.WxDemo;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;

import com.android.uiautomator.core.Configurator;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.core.UiWatcher;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;


public class PowerRunner extends UiAutomatorTestCase {
	private static UiDevice ud;
	// com.android.uiautomator.core.UiDevice
//	private static String rpath =Environment.getExternalStorageDirectory().getAbsolutePath()+ "/耗电测试/模拟待机测试.txt";
//	private static String ppath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/模拟待机测试";
	private static String rpath ="/sdcard/耗电测试/模拟待机测试.txt";
	private static String ppath = "/sdcard/耗电测试/模拟待机测试";
	private static String cap_Path = "/sys/class/power_supply/battery/capacity";
	private static String vol_Path = "/sys/class/power_supply/battery/batt_vol";
	private int choice = 56;
	private String number = "112";
	EPlus3_H ePlus3_H;
	Samsung7 samsung7;
	
	//
public static void main(String[] args) {
		
		String android_id = "3";  //android list target
		String test_class = "com.PowerRunner";
		String jar_name = "UiTest";
		String test_name = "testDemoS7";
		new Debug(jar_name, test_class, test_name, android_id);
	} 
	//
	

	public void testDemo() {
		ud = getUiDevice();
		EPlus3_H.setUd(ud);
		EPlus3_O.setUd(ud);
		//
		CM.hold(2000);
		CM.scroll(ud, CM.LEFT);
		CM.hold(2000);
		CM.scroll(ud, CM.RIGHT);
		ud.pressHome();
		ud.pressHome();
		try {
			Bundle mBundle = getParams();
			if (mBundle.getString("choice") != null)
				choice = Integer.parseInt(mBundle.getString("choice"));
		} catch (Exception ex) {
		}
		String model = android.os.Build.MODEL;
		
		System.out.println("song model:"+model);

				// 一加三氢 SUM E1001 EPlus1_H
		if (!CM.waitForId("com.google.android.googlequicksearchbox:id/search_plate",1000)) {			
			System.out.println("song start H2");			
			try {

				if (choice != 100) {
					try {
						int totaltimes = 15;
						if (choice > 50) {
							totaltimes = 50000;

//							rpath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/模块耗电测试.txt";
//							ppath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/模块耗电测试";
							
							CM.createFolder(ppath);
							// record("IMEI号："+EPlus3_H.getEplusIMEI("com.android.dialer","com.android.dialer.DialtactsActivity"));
						}//
						cap_Path = EPlus3_H.cap_Path;
						vol_Path = EPlus3_H.vol_Path;
						CM.createFolder(ppath);
						record("模块耗电测试 start: " + model);
						EPlus3_H.watchLowPower();

						switch (choice % 8) {
						case 0:
							try {
								number = EPlus3_O.getProviderNumber();
								record("通话开始");
								int remainingTimes = EPlus3_H.call(totaltimes,
										number);
								while (remainingTimes > 0)
									remainingTimes = EPlus3_H.call(
											remainingTimes, number);
								record("通话结束, 进入待机");
							} catch (Exception ce) {
								record("通话结束 #####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 1:
							try {
								record("微信开始");
								int remainingTimes = EPlus3_H.wxTest(totaltimes);
								while (remainingTimes > 0)
									remainingTimes = EPlus3_H
											.wxTest(remainingTimes);
								record("微信结束, 进入待机");
							} catch (Exception we) {
								record("微信结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 2:
							try {
								record("QQ开始");
								int remainingTimes = EPlus3_H
										.qqTest(totaltimes);
								while (remainingTimes > 0)
									remainingTimes = EPlus3_H
											.qqTest(remainingTimes);
								record("QQ结束, 进入待机");
							} catch (Exception qe) {
								record("QQ结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 3:
							try {
								record("视频开始");
								int remainingTimes = EPlus3_H
										.videoTest(totaltimes);
								while (remainingTimes > 0)
									remainingTimes = EPlus3_H
											.videoTest(remainingTimes);
								record("视频结束, 进入待机");
							} catch (Exception ve) {
								record("视频结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 4:
							try {
								record("上网开始");
								int remainingTimes = EPlus3_H
										.browserTest(totaltimes);
								while (remainingTimes > 0)
									remainingTimes = EPlus3_H
											.browserTest(remainingTimes);
								record("上网结束, 进入待机");
							} catch (Exception be) {
								CM.stopPackage(EPlus3_H.music_Pack);
								record("上网结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 5:
							try {
								record("相机开始");
								int remainingTimes = EPlus3_H
										.cameraTest(totaltimes / 3);
								while (remainingTimes > 0)
									remainingTimes = EPlus3_H
											.cameraTest(remainingTimes);
								record("相机结束, 进入待机");
							} catch (Exception ce) {
								record("相机结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 6:
							try {
								record("阅读开始");
								int remainingTimes = EPlus3_H
										.readerTest(totaltimes);
								while (remainingTimes > 0)
									remainingTimes = EPlus3_H
											.readerTest(remainingTimes);
								record("阅读结束, 进入待机");
							} catch (Exception ce) {
								CM.stopPackage(EPlus3_H.music_Pack);
								record("阅读结束####WRONG");
								ud.pressBack();
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 7:
							try {
								record("游戏开始");
								int remainingTimes = EPlus3_H
										.gameTest(totaltimes / 3);
								while (remainingTimes > 0)
									remainingTimes = EPlus3_H
											.gameTest(remainingTimes);
								record("游戏结束, 进入待机");
							} catch (Exception ge) {
								record("游戏结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						}
					} catch (Exception ex) {
					}
				} else {
					cap_Path = EPlus3_H.cap_Path;
					vol_Path = EPlus3_H.vol_Path;
//					rpath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/续航能力测试.txt";
//					ppath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/续航能力测试";
					CM.createFolder(ppath);
					record("续航能力测试: " + model);
					EPlus3_H.watchLowPower();
					
					record("IMEI号："
							+ EPlus3_H.getEplusIMEI(EPlus3_H.dailer_Pack,EPlus3_H.dailer_Pack));

					int count = 0;

					while (true) {
						try {
							number = EPlus3_H.getProviderNumber();
							record("通话开始");
							int remainingTimes = EPlus3_H.call(8, number);
							while (remainingTimes > 0)
								remainingTimes = EPlus3_H.call(remainingTimes,
										number);
							record("通话结束");
						} catch (Exception ce) {
							record("通话结束 #####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("微信开始");
							int remainingTimes = EPlus3_H.wxTest(8);
							while (remainingTimes > 0)
								remainingTimes = EPlus3_H.wxTest(remainingTimes);
							record("微信结束");
						} catch (Exception we) {
							record("微信结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("QQ开始");
							int remainingTimes = EPlus3_H.qqTest(7);
							while (remainingTimes > 0)
								remainingTimes = EPlus3_H
										.qqTest(remainingTimes);
							record("QQ结束, 进入待机");
						} catch (Exception qe) {
							record("QQ结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("视频开始");
							int remainingTimes = EPlus3_H.videoTest(7);
							while (remainingTimes > 0)
								remainingTimes = EPlus3_H
										.videoTest(remainingTimes);
							record("视频结束");
						} catch (Exception ve) {
							record("视频结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("上网开始");
							int remainingTimes = EPlus3_H.browserTest(7);
							while (remainingTimes > 0)
								remainingTimes = EPlus3_H
										.browserTest(remainingTimes);
							record("上网结束");
						} catch (Exception be) {
							CM.stopPackage(EPlus3_H.music_Pack);
							record("上网结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("相机开始");
							int remainingTimes = EPlus3_H.cameraTest(5);
							while (remainingTimes > 0)
								remainingTimes = EPlus3_H
										.cameraTest(remainingTimes);
							record("相机结束");
						} catch (Exception ce) {
							record("相机结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("阅读开始");
							int remainingTimes = EPlus3_H.readerTest(7);
							while (remainingTimes > 0)
								remainingTimes = EPlus3_H
										.readerTest(remainingTimes);
							record("阅读结束");
						} catch (Exception ce) {
							CM.stopPackage(EPlus3_H.music_Pack);
							record("阅读结束####WRONG");
							ud.pressBack();
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("游戏开始");
							int remainingTimes = EPlus3_H.gameTest(9);
							while (remainingTimes > 0)
								remainingTimes = EPlus3_H.gameTest(remainingTimes);
							record("游戏结束");
						} catch (Exception ge) {
							record("游戏结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						count++;
						record("round " + count + " over\r\n");
					}
				}
			} catch (Exception e) {
				record("Quit abnormal:" + e);
			}
		}
		// 15801氧 SUM E1003 EPlus3_O
		if (CM.waitForId("com.google.android.googlequicksearchbox:id/search_plate",	1000)) {
			try {
				System.out.println("song start O2");
				if (choice != 100) {
					try {
						int totaltimes = 15;
						CM.createFolder(ppath);
						if (choice > 50) {
							totaltimes = 50000;

							rpath =Environment.getExternalStorageDirectory().getAbsolutePath()+ "/耗电测试/模块耗电测试.txt";
							ppath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/模块耗电测试";
							record("模块耗电测试 start: " + model);
							record("IMEI号："+EPlus3_O.getEplusIMEI(EPlus3_O.dailer_Pack,EPlus3_O.dailer_Act));
						}//
						cap_Path = EPlus3_O.cap_Path;
						vol_Path = EPlus3_O.vol_Path;
						EPlus3_H.watchLowPower();
						
						switch (choice % 8)// 一加三通话
						{
						case 0:
							try {
								number = EPlus3_O.getProviderNumber();
								record("通话开始");
								int remainingTimes = EPlus3_O.call(totaltimes,
										number);
								while (remainingTimes > 0)
									remainingTimes = EPlus3_O.call(
											remainingTimes, number);
								record("通话结束, 进入待机");
							} catch (Exception ce) {
								record("通话结束 #####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 1:
							try {
								record("微信开始");
								int remainingTimes = EPlus3_H.wxTest(totaltimes);
								while (remainingTimes > 0)
									remainingTimes = EPlus3_H
											.wxTest(remainingTimes);
								record("微信结束, 进入待机");
							} catch (Exception we) {
								record("微信结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 2:
							try {
								record("QQ开始");
								int remainingTimes = EPlus3_H.qqTest(totaltimes);
								while (remainingTimes > 0)
									remainingTimes = EPlus3_O
											.qqTest(remainingTimes);
								record("QQ结束, 进入待机");
							} catch (Exception qe) {
								record("QQ结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 3:
							try {
								record("视频开始");
								int remainingTimes = EPlus3_H
										.videoTest(totaltimes);
								while (remainingTimes > 0)
									remainingTimes = EPlus3_H
											.videoTest(remainingTimes);
								record("视频结束, 进入待机");
							} catch (Exception ve) {
								record("视频结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 4:
							try {
								record("上网开始");
								int remainingTimes = EPlus3_O
										.browserTest(totaltimes);
								while (remainingTimes > 0)
									remainingTimes = EPlus3_O
											.browserTest(remainingTimes);
								record("上网结束, 进入待机");
							} catch (Exception be) {
								CM.stopPackage(EPlus3_O.music_Pack);
								record("上网结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 5:
							try {
								record("相机开始");
								int remainingTimes = EPlus3_O
										.cameraTest(totaltimes / 3);
								while (remainingTimes > 0)
									remainingTimes = EPlus3_O
											.cameraTest(remainingTimes);
								record("相机结束, 进入待机");
							} catch (Exception ce) {
								record("相机结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 6:
							try {
								record("阅读开始");
								int remainingTimes = EPlus3_O
										.readerTest(totaltimes);
								while (remainingTimes > 0)
									remainingTimes = EPlus3_O
											.readerTest(remainingTimes);
								record("阅读结束, 进入待机");
							} catch (Exception ce) {
								CM.stopPackage(EPlus3_O.music_Pack);
								record("阅读结束####WRONG");
								ud.pressBack();
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 7:
							try {
								record("游戏开始");
								int remainingTimes = EPlus3_O
										.gameTest(totaltimes / 3);
								while (remainingTimes > 0)
									remainingTimes = EPlus3_O
											.gameTest(remainingTimes);
								record("游戏结束, 进入待机");
							} catch (Exception ge) {
								record("游戏结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						}
					} catch (Exception ex) {
					}
				} else {
					cap_Path = EPlus3_O.cap_Path;
					vol_Path = EPlus3_O.vol_Path;
//					rpath =Environment.getExternalStorageDirectory().getAbsolutePath()+ "/耗电测试/续航能力测试.txt";
//					ppath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/续航能力测试";
					CM.createFolder(ppath);
					record("续航能力测试: " + model);
					EPlus3_O.watchLowPower();
					record("IMEI号："+ EPlus3_O.getEplusIMEI(EPlus3_O.dailer_Pack,EPlus3_O.dailer_Act));
					//
					int count = 0;

					while (true) {
						try {
							number = EPlus3_O.getProviderNumber();
							record("通话开始");
							int remainingTimes = EPlus3_O.call(8, number);
							while (remainingTimes > 0)
								remainingTimes = EPlus3_O.call(remainingTimes,
										number);
							record("通话结束");
						} catch (Exception ce) {
							record("通话结束 #####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("微信开始");
							int remainingTimes = EPlus3_H.wxTest(8);
							while (remainingTimes > 0)
								remainingTimes = EPlus3_H.wxTest(remainingTimes);
							record("微信结束");
						} catch (Exception we) {
							record("微信结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("QQ开始");
							int remainingTimes = EPlus3_H.qqTest(7);
							while (remainingTimes > 0)
								remainingTimes = EPlus3_H.qqTest(remainingTimes);
							record("QQ结束");
						} catch (Exception qe) {
							record("QQ结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("视频开始");
							int remainingTimes = EPlus3_H.videoTest(7);
							while (remainingTimes > 0)
								remainingTimes = EPlus3_H
										.videoTest(remainingTimes);
							record("视频结束");
						} catch (Exception ve) {
							record("视频结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("上网开始");
							int remainingTimes = EPlus3_O.browserTest(7);
							while (remainingTimes > 0)
								remainingTimes = EPlus3_O
										.browserTest(remainingTimes);
							record("上网结束");
						} catch (Exception be) {
							CM.stopPackage(EPlus3_O.music_Pack);
							record("上网结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("相机开始");
							int remainingTimes = EPlus3_O.cameraTest(5);
							while (remainingTimes > 0)
								remainingTimes = EPlus3_O
										.cameraTest(remainingTimes);
							record("相机结束");
						} catch (Exception ce) {
							record("相机结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("阅读开始");
							int remainingTimes = EPlus3_O.readerTest(7);
							while (remainingTimes > 0)
								remainingTimes = EPlus3_O
										.readerTest(remainingTimes);
							record("阅读结束");
						} catch (Exception ce) {
							CM.stopPackage(EPlus3_O.music_Pack);
							record("阅读结束####WRONG");
							ud.pressBack();
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("游戏开始");
							int remainingTimes = EPlus3_O.gameTest(9);
							while (remainingTimes > 0)
								remainingTimes = EPlus3_O.gameTest(remainingTimes);
							record("游戏结束");
						} catch (Exception ge) {
							record("游戏结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						count++;
						record("round " + count + " over\r\n");
					}
				}
			} catch (Exception e) {
				record("Quit abnormal:" + e);
			}
		}

	}
	
	//s7
	public void testDemoS7() {
		ud = getUiDevice();
		Samsung7.setUd(ud);
		//
		CM.hold(2000);
		CM.scroll(ud, CM.LEFT);
		CM.hold(2000);
		CM.scroll(ud, CM.RIGHT);
		ud.pressHome();
		ud.pressHome();
		try {
			Bundle mBundle = getParams();
			if (mBundle.getString("choice") != null)
				choice = Integer.parseInt(mBundle.getString("choice"));
		} catch (Exception ex) {
		}
		String model = android.os.Build.MODEL;
		
		System.out.println("song model:"+model);

				// 一加三氢 SUM E1001 EPlus1_H
		if (CM.waitForId("com.google.android.googlequicksearchbox:id/search_edit_frame",1000)) {			
			System.out.println("song start H2");			
			try {

				if (choice != 100) {
					try {
						int totaltimes = 15;
						if (choice > 50) {
							totaltimes = 50000;

							//rpath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/模块耗电测试.txt";
							//ppath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/模块耗电测试";
							
							CM.createFolder(ppath);
							// record("IMEI号："+EPlus3_H.getEplusIMEI("com.android.dialer","com.android.dialer.DialtactsActivity"));
						}//
						cap_Path = Samsung7.cap_Path;
						vol_Path = Samsung7.vol_Path;
						CM.createFolder(ppath);
						record("模块耗电测试 start: " + model);
						Samsung7.watchLowPower();

						switch (choice % 8) {
						case 0:
							try {
								number = Samsung7.getProviderNumber();
								record("通话开始");
								System.out.println("calling");
								int remainingTimes = Samsung7.call(totaltimes,
										number);
								while (remainingTimes > 0)
									remainingTimes = Samsung7.call(
											remainingTimes, number);
								record("通话结束, 进入待机");
							} catch (Exception ce) {
								ce.printStackTrace();
								System.out.println("callend");
								record("通话结束 #####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 1:
							try {
								record("微信开始");
								int remainingTimes = Samsung7.wxTest(totaltimes);
								while (remainingTimes > 0)
									remainingTimes = Samsung7
											.wxTest(remainingTimes);
								record("微信结束, 进入待机");
							} catch (Exception we) {
								record("微信结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 2:
							try {
								record("QQ开始");
								int remainingTimes = Samsung7
										.qqTest(totaltimes);
								while (remainingTimes > 0)
									remainingTimes = Samsung7
											.qqTest(remainingTimes);
								record("QQ结束, 进入待机");
							} catch (Exception qe) {
								record("QQ结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 3:
							try {
								record("视频开始");
								int remainingTimes = Samsung7
										.videoTest(totaltimes);
								while (remainingTimes > 0)
									remainingTimes = Samsung7
											.videoTest(remainingTimes);
								record("视频结束, 进入待机");
							} catch (Exception ve) {
								record("视频结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 4:
							try {
								record("上网开始");
								int remainingTimes = Samsung7
										.browserTest(totaltimes);
								while (remainingTimes > 0)
									remainingTimes = Samsung7
											.browserTest(remainingTimes);
								record("上网结束, 进入待机");
							} catch (Exception be) {
								CM.stopPackage(Samsung7.music_Pack);
								record("上网结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 5:
							try {
								record("相机开始");
								int remainingTimes = Samsung7
										.cameraTest(totaltimes / 3);
								while (remainingTimes > 0)
									remainingTimes = Samsung7
											.cameraTest(remainingTimes);
								record("相机结束, 进入待机");
							} catch (Exception ce) {
								record("相机结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 6:
							try {
								record("阅读开始");
								int remainingTimes = Samsung7
										.readerTest(totaltimes);
								while (remainingTimes > 0)
									remainingTimes = Samsung7
											.readerTest(remainingTimes);
								record("阅读结束, 进入待机");
							} catch (Exception ce) {
								CM.stopPackage(EPlus3_H.music_Pack);
								record("阅读结束####WRONG");
								ud.pressBack();
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 7:
							try {
								record("游戏开始");
								int remainingTimes = Samsung7
										.gameTest(totaltimes / 3);
								while (remainingTimes > 0)
									remainingTimes = Samsung7
											.gameTest(remainingTimes);
								record("游戏结束, 进入待机");
							} catch (Exception ge) {
								record("游戏结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						}
					} catch (Exception ex) {
					}
				} else {
					cap_Path = Samsung7.cap_Path;
					vol_Path = Samsung7.vol_Path;
//					rpath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/续航能力测试.txt";
//					ppath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/续航能力测试";
					CM.createFolder(ppath);
					record("续航能力测试: " + model);
					Samsung7.watchLowPower();
					
//					record("IMEI号："+ Samsung7.getEplusIMEI(Samsung7.dailer_Pack,Samsung7.dailer_Pack));
					record("IMEI号："+ "356156071430458 / 01");
					int count = 0;

					while (true) {
						try {
							number = Samsung7.getProviderNumber();
							record("通话开始");
							int remainingTimes = Samsung7.call(8, number);
							while (remainingTimes > 0)
								remainingTimes = Samsung7.call(remainingTimes,
										number);
							record("通话结束");
						} catch (Exception ce) {
							record("通话结束 #####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("微信开始");
							int remainingTimes = Samsung7.wxTest(8);
							while (remainingTimes > 0)
								remainingTimes = Samsung7.wxTest(remainingTimes);
							record("微信结束");
						} catch (Exception we) {
							record("微信结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("QQ开始");
							int remainingTimes = Samsung7.qqTest(7);
							while (remainingTimes > 0)
								remainingTimes = Samsung7
										.qqTest(remainingTimes);
							record("QQ结束, 进入待机");
						} catch (Exception qe) {
							record("QQ结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("视频开始");
							int remainingTimes = Samsung7.videoTest(7);
							while (remainingTimes > 0)
								remainingTimes = Samsung7
										.videoTest(remainingTimes);
							record("视频结束");
						} catch (Exception ve) {
							record("视频结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("上网开始");
							int remainingTimes = Samsung7.browserTest(7);
							while (remainingTimes > 0)
								remainingTimes = Samsung7
										.browserTest(remainingTimes);
							record("上网结束");
						} catch (Exception be) {
							CM.stopPackage(Samsung7.music_Pack);
							record("上网结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("相机开始");
							int remainingTimes = Samsung7.cameraTest(5);
							while (remainingTimes > 0)
								remainingTimes = Samsung7
										.cameraTest(remainingTimes);
							record("相机结束");
						} catch (Exception ce) {
							record("相机结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("阅读开始");
							int remainingTimes = Samsung7.readerTest(7);
							while (remainingTimes > 0)
								remainingTimes = Samsung7
										.readerTest(remainingTimes);
							record("阅读结束");
						} catch (Exception ce) {
							CM.stopPackage(Samsung7.music_Pack);
							record("阅读结束####WRONG");
							ud.pressBack();
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("游戏开始");
							int remainingTimes = Samsung7.gameTest(9);
							while (remainingTimes > 0)
								remainingTimes = Samsung7.gameTest(remainingTimes);
							record("游戏结束");
						} catch (Exception ge) {
							record("游戏结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						count++;
						record("round " + count + " over\r\n");
					}
				}
			} catch (Exception e) {
				record("Quit abnormal:" + e);
			}
		}
		

	}

	//MI5
	public void testDemoMI5() {
		ud = getUiDevice();
		MI5.setUd(ud);
		//
		CM.hold(2000);
		CM.scroll(ud, CM.LEFT);
		CM.hold(2000);
		CM.scroll(ud, CM.RIGHT);
		ud.pressHome();
		ud.pressHome();
		try {
			Bundle mBundle = getParams();
			if (mBundle.getString("choice") != null)
				choice = Integer.parseInt(mBundle.getString("choice"));
		} catch (Exception ex) {
		}
		String model = android.os.Build.MODEL;
		
		System.out.println("song model:"+model);

				// 一加三氢 SUM E1001 EPlus1_H
		if (!CM.waitForId("com.google.android.googlequicksearchbox:id/search_plate",1000)) {			
			System.out.println("song start H2");			
			try {

				if (choice != 100) {
					try {
						int totaltimes = 15;
						if (choice > 50) {
							totaltimes = 50000;

//							rpath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/模块耗电测试.txt";
//							ppath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/模块耗电测试";
							
							CM.createFolder(ppath);
							// record("IMEI号："+EPlus3_H.getEplusIMEI("com.android.dialer","com.android.dialer.DialtactsActivity"));
						}//
						cap_Path = MI5.cap_Path;
						vol_Path = MI5.vol_Path;
						CM.createFolder(ppath);
						record("模块耗电测试 start: " + model);
						MI5.watchLowPower();

						switch (choice % 8) {
						case 0:
							try {
								number = EPlus3_O.getProviderNumber();
								record("通话开始");
								int remainingTimes = MI5.call(totaltimes,
										number);
								while (remainingTimes > 0)
									remainingTimes = MI5.call(
											remainingTimes, number);
								record("通话结束, 进入待机");
							} catch (Exception ce) {
								record("通话结束 #####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 1:
							try {
								record("微信开始");
								int remainingTimes = MI5.wxTest(totaltimes);
								while (remainingTimes > 0)
									remainingTimes = MI5
											.wxTest(remainingTimes);
								record("微信结束, 进入待机");
							} catch (Exception we) {
								record("微信结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 2:
							try {
								record("QQ开始");
								int remainingTimes = MI5
										.qqTest(totaltimes);
								while (remainingTimes > 0)
									remainingTimes = MI5
											.qqTest(remainingTimes);
								record("QQ结束, 进入待机");
							} catch (Exception qe) {
								record("QQ结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 3:
							try {
								record("视频开始");
								int remainingTimes = MI5
										.videoTest(totaltimes);
								while (remainingTimes > 0)
									remainingTimes = MI5
											.videoTest(remainingTimes);
								record("视频结束, 进入待机");
							} catch (Exception ve) {
								record("视频结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 4:
							try {
								record("上网开始");
								int remainingTimes = MI5
										.browserTest(totaltimes);
								while (remainingTimes > 0)
									remainingTimes = MI5
											.browserTest(remainingTimes);
								record("上网结束, 进入待机");
							} catch (Exception be) {
								CM.stopPackage(MI5.music_Pack);
								record("上网结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 5:
							try {
								record("相机开始");
								int remainingTimes = MI5
										.cameraTest(totaltimes / 3);
								while (remainingTimes > 0)
									remainingTimes = MI5
											.cameraTest(remainingTimes);
								record("相机结束, 进入待机");
							} catch (Exception ce) {
								record("相机结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 6:
							try {
								record("阅读开始");
								int remainingTimes = MI5
										.readerTest(totaltimes);
								while (remainingTimes > 0)
									remainingTimes = MI5
											.readerTest(remainingTimes);
								record("阅读结束, 进入待机");
							} catch (Exception ce) {
								CM.stopPackage(EPlus3_H.music_Pack);
								record("阅读结束####WRONG");
								ud.pressBack();
								ud.pressBack();
								ud.pressBack();
							}
							break;
						case 7:
							try {
								record("游戏开始");
								int remainingTimes = MI5
										.gameTest(totaltimes / 3);
								while (remainingTimes > 0)
									remainingTimes = MI5
											.gameTest(remainingTimes);
								record("游戏结束, 进入待机");
							} catch (Exception ge) {
								record("游戏结束####WRONG");
								ud.pressBack();
								ud.pressBack();
							}
							break;
						}
					} catch (Exception ex) {
					}
				} else {
					cap_Path = MI5.cap_Path;
					vol_Path = MI5.vol_Path;
//					rpath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/续航能力测试.txt";
//					ppath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/续航能力测试";
					CM.createFolder(ppath);
					record("续航能力测试: " + model);
					MI5.watchLowPower();
					
//					record("IMEI号："+ Samsung7.getEplusIMEI(Samsung7.dailer_Pack,Samsung7.dailer_Pack));
					record("IMEI号："+ "mi5");
					int count = 0;

					while (true) {
						try {
							number = MI5.getProviderNumber();
							record("通话开始");
							int remainingTimes = MI5.call(8, number);
							while (remainingTimes > 0)
								remainingTimes = MI5.call(remainingTimes,
										number);
							record("通话结束");
						} catch (Exception ce) {
							record("通话结束 #####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("微信开始");
							int remainingTimes = MI5.wxTest(8);
							while (remainingTimes > 0)
								remainingTimes = MI5.wxTest(remainingTimes);
							record("微信结束");
						} catch (Exception we) {
							record("微信结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("QQ开始");
							int remainingTimes = MI5.qqTest(7);
							while (remainingTimes > 0)
								remainingTimes = MI5
										.qqTest(remainingTimes);
							record("QQ结束, 进入待机");
						} catch (Exception qe) {
							record("QQ结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("视频开始");
							int remainingTimes = MI5.videoTest(7);
							while (remainingTimes > 0)
								remainingTimes = MI5
										.videoTest(remainingTimes);
							record("视频结束");
						} catch (Exception ve) {
							record("视频结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("上网开始");
							int remainingTimes = MI5.browserTest(7);
							while (remainingTimes > 0)
								remainingTimes = MI5
										.browserTest(remainingTimes);
							record("上网结束");
						} catch (Exception be) {
							CM.stopPackage(MI5.music_Pack);
							record("上网结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("相机开始");
							int remainingTimes = MI5.cameraTest(5);
							while (remainingTimes > 0)
								remainingTimes = MI5
										.cameraTest(remainingTimes);
							record("相机结束");
						} catch (Exception ce) {
							record("相机结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("阅读开始");
							int remainingTimes = MI5.readerTest(7);
							while (remainingTimes > 0)
								remainingTimes = MI5
										.readerTest(remainingTimes);
							record("阅读结束");
						} catch (Exception ce) {
							CM.stopPackage(MI5.music_Pack);
							record("阅读结束####WRONG");
							ud.pressBack();
							ud.pressBack();
							ud.pressBack();
						}
						try {
							record("游戏开始");
							int remainingTimes = MI5.gameTest(9);
							while (remainingTimes > 0)
								remainingTimes = MI5.gameTest(remainingTimes);
							record("游戏结束");
						} catch (Exception ge) {
							record("游戏结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						count++;
						record("round " + count + " over\r\n");
					}
				}
			} catch (Exception e) {
				record("Quit abnormal:" + e);
			}
		}
		

	}

	public static void record(String txt) {
		try {
			String t = CM.getTime();
			String c = "UN";
			String v = "UN";
			try {
				c = CM.getText(cap_Path);
				try {
					v = CM.getText(vol_Path);
					if (v.equals("UN") || v.contains("null")) {
						v = CM.getText("/sys/class/power_supply/battery/voltage_now");
					}
				} catch (Exception ex) {
					v = CM.getText("/sys/class/power_supply/battery/voltage_now");
				}
			} catch (Exception x) {
				v = CM.getText("/sys/class/power_supply/battery/voltage_now");
			}
			CM.writeReport(rpath, t + " Capacity: " + c + "  " + " Voltage: "
					+ v + "  " + txt + "\r\n");
			ud.takeScreenshot(new File(ppath + "/" + t + ".png"));
			// if(Integer.parseInt(c.trim())<10)
			// ud.takeScreenshot(new File(ppath+"/consumInfo"+t+".png"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void input() {
		long timeout = Configurator.getInstance()
				.getActionAcknowledgmentTimeout();
		Configurator.getInstance().setActionAcknowledgmentTimeout(0);
		Random r = new Random();
		String message = args[r.nextInt(35)];
		for (int i = 0; i < message.length(); i++)
			enter(message.charAt(i));
		for (int i = 0; i < 21 - message.length(); i++)
			ud.pressKeyCode(62);
		Configurator.getInstance().setActionAcknowledgmentTimeout(timeout);
	}

	public static void eplus3input() {
		for (int x = 0; x < 3; x++) {
			long timeout = Configurator.getInstance()
					.getActionAcknowledgmentTimeout();
			Configurator.getInstance().setActionAcknowledgmentTimeout(0);
			Random r = new Random();
			String message = eplus3args[r.nextInt(35)];
			for (int i = 0; i < 21 - message.length(); i++)
				ud.pressKeyCode(62);
			for (int i = 0; i < message.length(); i++)
				eplus3enter(message.charAt(i));
			ud.click(ud.getDisplayWidth() / 4, ud.getDisplayHeight() * 39 / 40);

			Configurator.getInstance().setActionAcknowledgmentTimeout(timeout);
			try {
				CM.findObjectByText("发送", 1000).click();
				CM.findObjectByRid("com.tencent.mobileqq:id/fun_btn", 1000)
						.click();
				CM.findObjectByRid("com.tencent.mobileqq:id/fun_btn", 1000)
						.click();
			} catch (Exception e) {
			}

		}
	}

	public static void eplus3enter(char ch) {
		String chs = "abcdefghijklmnopqrstuvwxyz";
		if (ch == ' ')
			ud.pressKeyCode(62);
		else if (chs.contains(ch + "")) {
			for (int i = 0; i < chs.length(); i++) {
				if (chs.charAt(i) == ch) {
					ud.pressKeyCode(i + 29, 1);
				}
			}
		}

	}

	private static String[] eplus3args = {//
	"howareyou", "finethankyou", "whereareyoufrom", "iamfromhenan",
			"whatdoyoudo", "iamateacher", "whatisyourfavor",
			"ilikefootballmost", "canihelpyou", "isayiloveyou",
			"pleasebemyfriend", "howoldareyou", "iamthirty", "whereareyou",
			"iamathome", "timeforlunch", "goouttogether", "remembermeplease",
			"givemeakiss", "fucktheqq", "trustmeplease", "areyoufool",
			"joinusok", "leavemealone", "whatabeauty", "youarebeautiful",
			"iamnothungry", "givemeyourhand", "tellmeyournumber", "callmenow",
			"iwillkillyou", "yoursisterde", "gameoverboy", "timetosleep",
			"goodnightman" };

	public static void enter(char ch) {
		String chs = "abcdefghijklmnopqrstuvwxyz";
		if (ch == ' ')
			ud.pressKeyCode(62);
		else if (chs.contains(ch + "")) {
			for (int i = 0; i < chs.length(); i++) {
				if (chs.charAt(i) == ch) {
					ud.pressKeyCode(i + 29);
				}
			}
		}

	}
	
	

	public static UiDevice getUd() {
		return ud;
	}


	private static String[] args = {//
	"how are you", "fine thank you", "where are you from", "i am from henan",
			"what do you do", "i am a teacher", "what is your favor",
			"i like football most", "can i help you", "i say i love you",
			"please be my friend", "how old are you", "i am thirty",
			"where are you", "i am at home", "time for lunch",
			"go out together", "remember me please", "give me a kiss",
			"fuck the qq", "trust me please", "are you fool", "join us ok",
			"leave me alone", "what a beauty", "you are beautiful",
			"i am not hungry", "give me your hand", "tell me your number",
			"call me now", "i will kill you", "your sister de",
			"game over boy", "time to sleep", "good night man" };
}