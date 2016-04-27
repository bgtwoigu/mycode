package com.example.timetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBConnection extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SP.db";
    private static final int DATABASE_VERSION = 1;

    public DBConnection(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_sql = "CREATE TABLE movement(m_id integer primary key autoincrement,m_type varchar(10),"
                + "m_key varchar(20),m_value varchar(100),m_order integer)";
        db.execSQL(create_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        // String alter_sql = "ALTER TABLE movement";
        // db.execSQL(alter_sql);

    }

}

