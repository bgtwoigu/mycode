package com.zbin.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class blackNumberHelp extends SQLiteOpenHelper {
private static String ku="blacknumber.db";
	public blackNumberHelp(Context context) {
		super(context, ku, null, 1);

	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		// �������ݿ�,������������
		db.execSQL("create table blacknumber (_id integer primary key autoincrement,number varchar(20),mode varchar(2))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
