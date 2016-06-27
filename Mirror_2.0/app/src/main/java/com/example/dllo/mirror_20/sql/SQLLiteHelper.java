package com.example.dllo.mirror_20.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dllo on 16/6/25.
 */
public class SQLLiteHelper extends SQLiteOpenHelper {

    String sql="create table "+ SQLValues.TABLE_NAME
            + " (" + SQLValues.GOOD_ID+" text primary key ,"
            + SQLValues.GOODS_IMG+" text,"
            + SQLValues.GOODS_PRICE+" text,"
            + SQLValues.MODEL+" text,"
            + SQLValues.PRODUCT_AREA+" text,"
            + SQLValues.GOODS_NAME+" text )";
    public SQLLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
