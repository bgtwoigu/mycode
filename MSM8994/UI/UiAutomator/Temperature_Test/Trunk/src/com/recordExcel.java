package com;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class recordExcel extends UiAutomatorTestCase {
	
	
	public static void main(String[] args) {
		String android_id = "8";  //android list target
		String test_class = "com.recordExcel";
		String jar_name = "UiTest";
		String test_name = "testDemo";
		new Debug(jar_name, test_class, test_name, android_id);
	} 
	
	private String trpath = "/sdcard/";
	private String emmc = "/sys/class/thermal/thermal_zone24/temp";
	private String msm = "/sys/class/thermal/thermal_zone23/temp";
	private WritableWorkbook workbook;
	private WritableSheet sheet;
	private int count = 6;
	
	
	public void testDemo() throws RowsExceededException, WriteException, IOException {
		createFile();
		record();
		stop();
	}
	
	public void createFile() throws RowsExceededException, WriteException {
		File path = new File(trpath);
		if (!path.exists()) {
			path.mkdirs();
		}
		String filename = trpath + "temprature" + CM.getTime() + ".xls";
		try {
			File file = new File(filename);
			file.createNewFile();
			workbook = Workbook.createWorkbook(file);
			sheet = workbook.createSheet("Temprature", 0);
			sheet.addCell(new Label(1, 0, "time"));
			sheet.addCell(new Label(2, 0, "emmc_therm"));
			sheet.addCell(new Label(3, 0, "msm_therm"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println("create excel successful");
	}
	
	
	public void record() throws RowsExceededException, WriteException {
		for (int i = 1; i < count; i++) {
			sheet.addCell(new Label(1, i, CM.getTime()));
			sheet.addCell(new Label(2, i, CM.getText(emmc)));
			sheet.addCell(new Label(3, i, CM.getText(msm)));
			sleep(10000);
		}
	}
	
	public void stop() throws IOException, WriteException {
		workbook.write();
		workbook.close();
	}

}
