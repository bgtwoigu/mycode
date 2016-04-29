package com.zbin.blacknumber;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库增删改查工具类
 * 
 * @author Administrator
 *
 */
public class BlackNumberDao {

	private blackNumberHelp helper;

	/**
	 * 构造方法中完成数据库打开帮助类的初始化
	 * 
	 * @param context
	 */
	public BlackNumberDao(Context context) {
		helper = new blackNumberHelp(context);
	}

	/**
	 * 添加一条黑名单号码
	 * 
	 * @param number
	 *            黑名单号码
	 * @param mode
	 *            拦截模式 1电话拦截 2短信拦截 3全部拦截。
	 */
	public void add(String number, String mode) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("number", number);
		values.put("mode", mode);
		db.insert("blacknumber", null, values);
		db.close();
	}

	/**
	 * 删除一条黑名单号码
	 * 
	 * @param number
	 *            黑名单号码
	 */
	public void delete(String number) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete("blacknumber", "number=?", new String[] { number });
		db.close();
	}

	/**
	 * 更新数据
	 * 
	 */
	public void updata(String number) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.update("blacknumber", null, "number=?", new String[] { number });
		db.close();

	}

	/**
	 * 查询全部数据
	 */
	public List<BlackNumberInfo> findAll() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("blacknumber", new String[] { "number", "mode" }, null, null, null, null, null);
		List<BlackNumberInfo> infos = new ArrayList<BlackNumberInfo>();
		while (cursor.moveToNext()) {
			BlackNumberInfo info = new BlackNumberInfo();
			String number = cursor.getString(0);
			String mode = cursor.getString(1);
			// info.setMode(mode);
			info.setNumber(number);
			infos.add(info);
		}
		return infos;
	}
	/**
	 * 查询黑名单号码的拦截模式
	 * @param number
	 * @return  null代表不存在  1电话 2短信 3全部
	 */
	public String findMode(String number){
		String mode = null;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("blacknumber", new String[]{"mode"}, "number=?", new String[]{number}, null, null, null);
		if(cursor.moveToNext()){
			mode = cursor.getString(0);
		}
		cursor.close();
		db.close();
		return mode;
	}
}
