package com.zbin.blacknumber;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * ���ݿ���ɾ�Ĳ鹤����
 * 
 * @author Administrator
 *
 */
public class BlackNumberDao {

	private blackNumberHelp helper;

	/**
	 * ���췽����������ݿ�򿪰�����ĳ�ʼ��
	 * 
	 * @param context
	 */
	public BlackNumberDao(Context context) {
		helper = new blackNumberHelp(context);
	}

	/**
	 * ���һ������������
	 * 
	 * @param number
	 *            ����������
	 * @param mode
	 *            ����ģʽ 1�绰���� 2�������� 3ȫ�����ء�
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
	 * ɾ��һ������������
	 * 
	 * @param number
	 *            ����������
	 */
	public void delete(String number) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete("blacknumber", "number=?", new String[] { number });
		db.close();
	}

	/**
	 * ��������
	 * 
	 */
	public void updata(String number) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.update("blacknumber", null, "number=?", new String[] { number });
		db.close();

	}

	/**
	 * ��ѯȫ������
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
	 * ��ѯ���������������ģʽ
	 * @param number
	 * @return  null��������  1�绰 2���� 3ȫ��
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
