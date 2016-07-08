package com.example.dllo.mirror_20.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.dllo.mirror_20.Bean.DBBean;
import com.example.dllo.mirror_20.Bean.DataAllBean;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/25.
 */
public class SQLTools {
    private SQLiteDatabase database;
    private SQLLiteHelper helper;
    private Context context;
    private DBBean dbBean;

    public SQLTools(Context context) {
        this.context = context;
        helper = new SQLLiteHelper(context, SQLValues.SQL_BUY_CAR_NAME, null, 1);
        database = helper.getWritableDatabase();
    }

    public void insert(DBBean dbBean) {

        Boolean has = exit(dbBean.getGoods_id());
        if (has) {
            Toast.makeText(context, "已经添加过了", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLValues.GOODS_IMG, dbBean.getGoods_img());
        contentValues.put(SQLValues.GOODS_NAME, dbBean.getGoods_name());
        contentValues.put(SQLValues.GOODS_PRICE, dbBean.getGoods_price());
        contentValues.put(SQLValues.GOOD_ID, dbBean.getGoods_id());
        contentValues.put(SQLValues.MODEL, dbBean.getModel());
        contentValues.put(SQLValues.PRODUCT_AREA, dbBean.getProduct_area());
        database.insert(SQLValues.TABLE_NAME, null, contentValues);
        Toast.makeText(context, "已加入购物车", Toast.LENGTH_SHORT).show();
    }

    public Boolean exit(String number) {

        Boolean flag = false;
        String sql = "select * from " + SQLValues.TABLE_NAME + " where " + SQLValues.GOOD_ID + "=?";
        String[] temp = {number};
        Cursor cursor = database.rawQuery(sql, temp);
        if (cursor.getCount() > 0) {
            flag = true;
        }
        cursor.close();
        return flag;
    }

    public ArrayList<DBBean> querAll() {
        ArrayList<DBBean> bean = new ArrayList<>();
        Cursor cursor = database.query(SQLValues.TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {

                String goodsName = cursor.getString(cursor.getColumnIndex(SQLValues.GOODS_NAME));
                String goodsImg = cursor.getString(cursor.getColumnIndex(SQLValues.GOODS_IMG));
                String goodsPrice = cursor.getString(cursor.getColumnIndex(SQLValues.GOODS_PRICE));
                String goodsId = cursor.getString(cursor.getColumnIndex(SQLValues.GOOD_ID));
                String model = cursor.getString(cursor.getColumnIndex(SQLValues.MODEL));
                String productArea = cursor.getString(cursor.getColumnIndex(SQLValues.PRODUCT_AREA));

                dbBean = new DBBean();
                dbBean.setGoods_name(goodsName);
                dbBean.setGoods_id(goodsId);
                dbBean.setGoods_price(goodsPrice);
                dbBean.setGoods_img(goodsImg);
                dbBean.setModel(model);
                dbBean.setProduct_area(productArea);

                bean.add(dbBean);

            }
        }

        if (cursor != null) {

            cursor.close();
        }

        return bean;
    }

    public void deleteAll(DBBean bean) {

//        for (int i = 0; i < been.size(); i++) {
            String[] str = {bean.getGoods_name()};
            database.delete(SQLValues.TABLE_NAME, "goods_name =?", str);
//        }


    }
}
