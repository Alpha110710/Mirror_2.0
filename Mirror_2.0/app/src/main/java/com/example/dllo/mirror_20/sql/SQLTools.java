package com.example.dllo.mirror_20.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
    private DataAllBean dataAllBean;

    public SQLTools(Context context) {
        this.context = context;
        helper = new SQLLiteHelper(context, SQLValues.SQL_BUY_CAR_NAME, null, 1);
        database = helper.getWritableDatabase();
    }

    public void insert(DBBean dbBean) {

        Boolean has = exit(dbBean.getGoods_id());
        if (has) {
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

    public ArrayList<DataAllBean> querAll() {
        ArrayList<DataAllBean> bean = new ArrayList<>();

        Cursor cursor = database.query(SQLValues.TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {

                String goodsName = cursor.getString(cursor.getColumnIndex(SQLValues.GOODS_NAME));
                String goodsImg = cursor.getString(cursor.getColumnIndex(SQLValues.GOODS_IMG));
                String goodsPrice = cursor.getString(cursor.getColumnIndex(SQLValues.GOODS_PRICE));
                String goodsId = cursor.getString(cursor.getColumnIndex(SQLValues.GOOD_ID));
                String model = cursor.getString(cursor.getColumnIndex(SQLValues.MODEL));
                String productArea = cursor.getString(cursor.getColumnIndex(SQLValues.PRODUCT_AREA));


                dataAllBean = new DataAllBean();
                for (int i = 0; i < dataAllBean.getData().getList().size(); i++) {
                    dataAllBean.getData().getList().get(i).getData_info().setGoods_name(goodsName);
                    dataAllBean.getData().getList().get(i).getData_info().setGoods_id(goodsId);
                    dataAllBean.getData().getList().get(i).getData_info().setGoods_img(goodsImg);
                    dataAllBean.getData().getList().get(i).getData_info().setGoods_price(goodsPrice);
                    dataAllBean.getData().getList().get(i).getData_info().setModel(model);
                    dataAllBean.getData().getList().get(i).getData_info().setProduct_area(productArea);
                }
                bean.add(dataAllBean);

            }
        }

        if (cursor != null) {

            cursor.close();
        }

        return bean;
    }

    public void deleteAll(ArrayList<DataAllBean.DataBean.ListBean.DataInfoBean> been) {

        for (int i = 0; i < been.size(); i++) {
            String[] str = {been.get(i).getGoods_name()};
            database.delete(SQLValues.TABLE_NAME, "goods_name =?", str);
        }


    }
}
