package com.itheima.mobilesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BlackNumberDBOpenHelper extends SQLiteOpenHelper {

	public BlackNumberDBOpenHelper(Context context) {
		super(context, "blacknumber.db", null, 1);
	}
	
	//���ݿ��һ�δ�����ʱ����õķ����� �ʺ������ݿ���ṹ�ĳ�ʼ��
	@Override
	public void onCreate(SQLiteDatabase db) {
		//�������ݿ�ı��ṹ  ����_id ������  number����������  mode����ģʽ  1�绰���� 2�������� 3ȫ�����ء�
		db.execSQL("create table blacknumber (_id integer primary key autoincrement , number varchar(20), mode varchar(2))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}