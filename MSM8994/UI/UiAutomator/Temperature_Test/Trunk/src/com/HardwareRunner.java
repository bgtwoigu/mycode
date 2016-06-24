package com;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class HardwareRunner extends UiAutomatorTestCase {
	
	private String filePath = "/storage/emulated/0/";
	private String trpath = filePath + "temprature_result/";
	private String logpath = trpath + "log/";
	private String tdpath = "/sys/class/thermal/thermal_zone24/temp";
	boolean stopFlag = false;
	private String tag="init";
	private recordTemprature rt = new recordTemprature();
	private Thread thread = new Thread(rt);
	private Double tmpThd = 35.0;
	private int time = 45;
	
	
	public static void main(String[] args) {
		String android_id = "8";  //android list target
		String test_class = "com.HardwareRunner";
		String jar_name = "UiTest";
		String test_name = "videoTest";
		new Debug(jar_name, test_class, test_name, android_id);
	} 
	

	public void videoTest() {
		
		Bundle mBundle = getParams();
		if (mBundle.getString("time") != null) {
			time = Integer.parseInt(mBundle.getString("time"));
		}
		time = time*60*1000;
		
		thread.start();
		
		CM.startActivity("com.mxtech.videoplayer.ad", ".ActivityMediaList");
		sleep(2000);
		try {
//			CM.findObjectByText("Movies", 1000).click();
			CM.findObjectByRid("com.mxtech.videoplayer.ad:id/title", 0, 1000).click();
			CM.findObjectByRid("com.mxtech.videoplayer.ad:id/thumb", 0, 1000).click();
			sleep(time);
			UiDevice.getInstance().pressRecentApps();
			CM.findObjectByRid("com.android.systemui:id/clear_recents", 1000).click();
		} catch (UiObjectNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			stopFlag = true;
			sleep(35000);			
		}
		
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
		private int interval = 30000;
		
		public recordTemprature() {
			File path = new File(logpath);
			if (path.exists()) {
				CM.deleteDir(path);
			}
			System.out.println(logpath);
			boolean status = path.mkdirs();
			if (!status) {
				System.out.println("create log path fail");
			}
			formatter = new SimpleDateFormat("yyyyMMddHHmm");
			String filename = trpath + "temprature" + CM.getTime(formatter) + ".xls";
			try {
				File file = new File(filename);
				file.createNewFile();
				workbook = Workbook.createWorkbook(file);
				sheet = workbook.createSheet("Temprature", 0);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			System.out.println("create excel successful");
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				sheet.addCell(new Label(0, 0, "tag"));
				sheet.addCell(new Label(1, 0, "threshold"));
				sheet.addCell(new Label(2, 0, "time"));
				sheet.addCell(new Label(3, 0, CM.getTime(formatter)));
				
				while (!stopFlag) {
//					System.out.println("tag:" + tag);
//					System.out.println("time:" + CM.getTime());
//					System.out.println("temprature:" + CM.getText(tdpath));
					tmp = Double.valueOf(CM.getText(tdpath));
					sheet.addCell(new Label(0, row, tag));
					sheet.addCell(new Number(1, row, tmpThd));
					sheet.addCell(new DateTime(2, row, new Date(System.currentTimeMillis())));
					sheet.addCell(new Number(3, row, tmp));
					row++;
					
					if (tmp > tmpThd && logFlag ) {
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
								CM.writeReport(logpath + "dmesg_" + tag + ".txt", dmesg);
								CM.writeReport(logpath + "top_" + tag + ".txt", top);
								CM.writeReport(logpath + "logcat_" + tag + ".txt", logmsg);
							}
						}.run();
					} else if (tmp < tmpThd) {
						logFlag = true;
					}
					sleep(interval);					
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
	

	
}
