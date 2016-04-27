package com.example.timetest;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper {

    DBConnection helper;

    public DBHelper(Context ctx) {
        helper = new DBConnection(ctx);
    }

    public DBHelper() {
    }

    public void add(ContentValues values) {

        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(UserSchema.TABLE_NAME, null, values);
        db.close();

    }

    public void DropTable(String TB_NAME) {
        try {

            SQLiteDatabase db = helper.getWritableDatabase();
            String sql = "DROP TABLE " + TB_NAME;
            db.execSQL(sql);
        } catch (Exception ex) {

        }
    }

    public void DropDatabase(Context ctx, String DATABASE_NAME) {
        try {
            ctx.deleteDatabase(DATABASE_NAME);
        } catch (Exception ex) {

        }
    }

    public void update(ContentValues values, String where, String[] whereArgs) {

        SQLiteDatabase db = helper.getWritableDatabase();
        db.update(UserSchema.TABLE_NAME, values, where, whereArgs);
        db.close();
    }

    public void delete(String where, String[] whereArgs) {

        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(UserSchema.TABLE_NAME, where, whereArgs);
        db.close();
    }

    public void delDB(String DBName) {

        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DROP Database " + DBName);
        db.close();
    }

    public List query(String where, String[] whereArgs, String orderBy,
            String limit) {

        try {
            final SQLiteDatabase db = helper.getReadableDatabase();
            Cursor c = null;
            c = db.query(UserSchema.TABLE_NAME, null, where, whereArgs, null,
                    null, orderBy, limit);
            List lst = new ArrayList();

            while (c != null && c.moveToNext()) {
                CItem item = new CItem();
                item.setID(c.getString(c.getColumnIndex("m_key")));
                item.setValue(c.getString(c.getColumnIndex("m_value")));
                lst.add(item);
            }
            if (c != null)c.close();
            return lst;
        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;

    }

    public interface UserSchema {

        String TABLE_NAME = "movement";
        String ID = "m_id";
        String TYPE = "m_type";
        String Key = "m_key";
        String Value = "m_value";

    }}