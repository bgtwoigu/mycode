package com;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import jxl.Workbook;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


import android.os.Bundle;
import android.os.RemoteException;

import com.android.uiautomator.core.Configurator;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class PowerRunner extends UiAutomatorTestCase {
	private static UiDevice ud;
	// com.android.uiautomator.core.UiDevice
//	private static String rpath =Environment.getExternalStorageDirectory().getAbsolutePath()+ "/耗电测试/模拟待机测试.txt";
//	private static String ppath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/模拟待机测试";
	private static String filePath = "/storage/emulated/0/batteryTest/";
	private static String rpath = filePath + "模拟待机测试.txt";
	private static String ppath = filePath + "模拟待机测试";
	private static String cap_Path = "/sys/class/power_supply/battery/capacity";
	private static String vol_Path = "/sys/class/power_supply/battery/batt_vol";
	private int choice = 100;
	private String number = "112";
	EPlus3_H ePlus3_H;
	
	//add by libin 2016-5-19
	private String trpath = filePath + "temprature_result/";
	private String logpath = trpath + "log";
	private String tdpath = "/sys/class/thermal/thermal_zone24/temp";
	boolean stopFlag = false;
	private String tag="";
	private recordTemprature rt = new recordTemprature();
	private Thread thread = new Thread(rt);
	private boolean powerFlag = false;     //续航or温升测试
	private Double tmpThd = 45.0;
	
	public static void main(String[] args) {
		String android_id = "8";  //android list target
		String test_class = "com.PowerRunner";
		String jar_name = "UiTest";
		String test_name = "testDemo";
		new Debug(jar_name, test_class, test_name, android_id);
	}
	
	public void test() {
		
		ud=getUiDevice();
		startAllApp(packages);
		tag = "standby app init";
		try {
			ud.pressHome();
			sleep(5000);
			ud.sleep();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sleep(60000);
		tag = "standby";
	}
	

	public void testDemo() {
		thread.start();
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
			if (mBundle.getString("power") != null) {
				powerFlag = false;
			}
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

							//rpath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/模块耗电测试.txt";
							//ppath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/模块耗电测试";
							
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
					//rpath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/续航能力测试.txt";
					//ppath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/续航能力测试";
					CM.createFolder(ppath);
					record("续航能力测试: " + model);
					EPlus3_H.watchLowPower();
					
					record("IMEI号："
							+ EPlus3_H.getEplusIMEI(EPlus3_H.dailer_Pack,EPlus3_H.dailer_Pack));

					int count = 0;

					do {
						try {
							number = EPlus3_H.getProviderNumber();
							record("通话开始");
							int remainingTimes = EPlus3_H.call(8, number); //8
							while (remainingTimes > 0)
								remainingTimes = EPlus3_H.call(remainingTimes,
										number);
							tag = "call";
							record("通话结束");
						} catch (Exception ce) {
							record("通话结束 #####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							
							record("微信开始");
							int remainingTimes = EPlus3_H.wxTest(8);  //8
							while (remainingTimes > 0)
								remainingTimes = EPlus3_H.wxTest(remainingTimes);
							record("微信结束");
							tag = "wechat";
						} catch (Exception we) {
							record("微信结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							
							record("QQ开始");
							int remainingTimes = EPlus3_H.qqTest(7);  //7
							while (remainingTimes > 0)
								remainingTimes = EPlus3_H
										.qqTest(remainingTimes);
							tag = "QQ";
							record("QQ结束, 进入待机");
						} catch (Exception qe) {
							record("QQ结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							
							record("视频开始");
							int remainingTimes = EPlus3_H.videoTest(7);  //7
							while (remainingTimes > 0)
								remainingTimes = EPlus3_H
										.videoTest(remainingTimes);
							tag = "video";
							record("视频结束");
						} catch (Exception ve) {
							record("视频结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							
							record("上网开始");
							int remainingTimes = EPlus3_H.browserTest(7);  //7
							while (remainingTimes > 0)
								remainingTimes = EPlus3_H
										.browserTest(remainingTimes);
							tag = "internet";
							record("上网结束");
						} catch (Exception be) {
							CM.stopPackage(EPlus3_H.music_Pack);
							record("上网结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							
							record("相机开始");
							int remainingTimes = EPlus3_H.cameraTest(7);  //7
							while (remainingTimes > 0)
								remainingTimes = EPlus3_H
										.cameraTest(remainingTimes);
							tag = "camera";
							record("相机结束");
						} catch (Exception ce) {
							record("相机结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						try {
							
							record("阅读开始");
							int remainingTimes = EPlus3_H.readerTest(7); //7
							while (remainingTimes > 0)
								remainingTimes = EPlus3_H
										.readerTest(remainingTimes);
							tag = "read";
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
							int remainingTimes = EPlus3_H.gameTest(9); //9
							while (remainingTimes > 0)
								remainingTimes = EPlus3_H.gameTest(remainingTimes);
							tag = "Subway Surfers";
							record("游戏结束");
						} catch (Exception ge) {
							record("游戏结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						count++;
						record("round " + count + " over\r\n");
					} while (powerFlag);
					
//					EPlus3_H.clearRecent();					
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

							//rpath =Environment.getExternalStorageDirectory().getAbsolutePath()+ "/耗电测试/模块耗电测试.txt";
							//ppath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/模块耗电测试";
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
								tag = "call";
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
								tag = "wechat";
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
								tag = "QQ";
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
								tag = "video";
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
								tag = "internet";
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
								tag = "camera";
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
								tag = "read";
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
								tag = "Subway Surfers";
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
					//rpath =Environment.getExternalStorageDirectory().getAbsolutePath()+ "/耗电测试/续航能力测试.txt";
					//ppath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/耗电测试/续航能力测试";
					CM.createFolder(ppath);
					record("续航能力测试: " + model);
					EPlus3_O.watchLowPower();
					record("IMEI号："+ EPlus3_O.getEplusIMEI(EPlus3_O.dailer_Pack,EPlus3_O.dailer_Act));
					//
					int count = 0;

					do {
						try {
							number = EPlus3_O.getProviderNumber();
							
							record("通话开始");
							int remainingTimes = EPlus3_O.call(8, number);
							while (remainingTimes > 0)
								remainingTimes = EPlus3_O.call(remainingTimes,
										number);
							tag = "call";
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
							tag = "wechat";
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
							tag = "QQ";
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
							tag = "video";
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
							tag = "internet";
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
							tag = "camera";
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
							tag = "read";
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
							tag = "Subway Surfers";
							record("游戏结束");
						} catch (Exception ge) {
							record("游戏结束####WRONG");
							ud.pressBack();
							ud.pressBack();
						}
						count++;
						record("round " + count + " over\r\n");
					} while (powerFlag);
				}
			} catch (Exception e) {
				record("Quit abnormal:" + e);
			}
		}
		
		try {
			
			record("NBA2015 Start");
			NBA2015.gameTest(2);  //2
			tag = "NBA2015";
			record("NBA2015 End");
		} catch (Exception ge) {
			record("NBA2015####WRONG");
			ud.pressBack();
			ud.pressBack();
		}
		
		
		startAllApp(packages);
		try {
			ud.pressHome();
			ud.sleep();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CM.hold(3600000);
		tag = "standby";
		sleep(15000);
		
		//停止线程，保存并关闭excel表格 ,add by libin 2016-5-19
		stopFlag = true;
		sleep(15000);

	}
	
	class recordTemprature implements Runnable {
		/**excel表写入数据*/
		
		private int row=1;
		private WritableWorkbook workbook;
		private WritableSheet sheet;
		private SimpleDateFormat formatter;
		private boolean logFlag = true;
		private Double tmp;
		private String top;
		private String logmsg;
		private String dmesg;
		
		public recordTemprature() {
			formatter = new SimpleDateFormat("yyyyMMddHHmm");
			logpath = logpath + CM.getTime(formatter) + "/";
			File path = new File(logpath);
			if (path.exists()) {
				CM.deleteDir(path);
			}
			boolean status = path.mkdirs();
			if (!status) {
				System.out.println("create log path fail");
			}
			String filename = trpath + "temprature" + CM.getTime(formatter) + ".xls";
			try {
				System.out.println(filename);
				File file = new File(filename);
				file.createNewFile();
				workbook = Workbook.createWorkbook(file);
				sheet = workbook.createSheet("Temprature", 0);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				sheet.addCell(new Label(0, 0, "tag"));
				sheet.addCell(new Label(1, 0, "threshold"));
				sheet.addCell(new Label(2, 0, "time"));
				sheet.addCell(new Label(3, 0, CM.getTime(formatter)));
				System.out.println("create excel successful");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RowsExceededException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			
			try {
				
				while (!stopFlag) {
//					System.out.println("tag:" + tag);
//					System.out.println("time:" + CM.getTime());
//					System.out.println("temprature:" + CM.getText(tdpath));
					tmp = Double.valueOf(CM.getText(tdpath));
//					System.out.println("tmp:" + tmp);
					if (!tag.isEmpty()) {
						sheet.addCell(new Label(0, row, tag));
						tag = "";
					}
					sheet.addCell(new Number(1, row, tmpThd));
					sheet.addCell(new DateTime(2, row, new Date(System.currentTimeMillis())));
					sheet.addCell(new Number(3, row, tmp));
					row++;
					
					if (logFlag && (tmp > tmpThd) ) {
						logFlag = false;
						top = "";
						logmsg = "";
						dmesg = "";
						new Runnable() {
							//抓tog,logcat,dmesg
							public void run() {
								dmesg = CM.getShellMsg("dmesg");
								logmsg = CM.getShellMsg("logcat -t 20000");
								top = CM.getShellMsg("top -m 5 -t -n 10");
								CM.writeReport(logpath + "dmesg" + ".txt", dmesg);
								CM.writeReport(logpath + "top" + ".txt", "&&&&&&&&&&" + CM.getTime() + "&&&&&&&&&&");
								CM.writeReport(logpath + "top" + ".txt", top);
								CM.writeReport(logpath + "logcat" + ".txt", logmsg);
							}
						}.run();
					} else if (tmp < tmpThd) {
						logFlag = true;
					}
					sleep(10000);					
				} 			
				
			} catch (RowsExceededException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					workbook.write();
					workbook.close();
					System.out.println("workbook closed");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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
	
	private static String[] packages = {
		//后台运行的package
		"com.sohu.sohuvideo/.ui.MainActivity", "com.tmall.wireless/.maintab.module.TMMainTabActivity",
		"com.qihoo360.mobilesafe/.ui.index.AppEnterActivity", "com.taobao.taobao/com.taobao.tao.homepage.MainActivity3",
		"com.ss.android.article.news/.activity.MainActivity", "com.chinamworld.main/com.ccb.biz.MainActivity",
		"com.cleanmaster.mguard_cn/com.keniu.security.main.MainActivity", "com.tencent.feiji/.MainActivity",
		"com.youdao.dict/.activity.MainActivity", "com.wandoujia/.ripple.activity.HomeActivity",
		"cn.ledongli.ldl/.activity.MainFragmentActivity", "com.netease.my.nearme.gamecenter/com.netease.my.AppActivity"
	};
	
	private static void startAllApp(String[] packages) {
		/**启动指定的所有应用*/
		for (String pack : packages) {
//			System.out.println("starting:" + pack);
			String[] tmp = pack.split("/");
			CM.startActivity(tmp[0], tmp[1]);
			CM.hold(5000);
			for (int i = 0; i < 30; i++) {
				CM.scroll(ud, 2);
				CM.hold(5000);
				CM.scroll(ud, 3);
				CM.hold(5000);
			}			
		}
	}
}