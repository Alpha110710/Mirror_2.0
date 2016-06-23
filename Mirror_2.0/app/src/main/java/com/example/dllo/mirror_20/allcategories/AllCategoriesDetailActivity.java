package com.example.dllo.mirror_20.allcategories;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.example.dllo.mirror_20.Bean.DataAllBean;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.BaseActivity;
import com.example.dllo.mirror_20.networktools.NetworkListener;
import com.example.dllo.mirror_20.networktools.NetworkTools;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dllo on 16/6/21.
 */
public class AllCategoriesDetailActivity extends BaseActivity {

    private String url = "http://lizhongren.com.cn/mengke/jsonhandle.php";
    private NetworkTools networkTools = new NetworkTools();
    private Gson gson;
    private DataAllBean dataAllBean;
    private int position;
    private NetworkListener networkListener = new NetworkListener() {
        @Override
        public void onSuccessed(String result) {
            gson = new Gson();
            dataAllBean = gson.fromJson(result, DataAllBean.class);

            if (dataAllBean.getData().getList().get(position).getType().equals("1")) {
                //解析背景图片
                Picasso.with(AllCategoriesDetailActivity.this).load(dataAllBean.getData().getList().get(position).getData_info().getGoods_img())
                        .fit().into(allCategoriesDetailBackImg);
            }
            //给外面listview适配器设置数据
            List<DataAllBean.DataBean.ListBean.DataInfoBean.GoodsDataBean> goodsDataBeans = dataAllBean.getData().getList().get(position).getData_info().getGoods_data();
            detailActivityOutListViewAdapter.setGoodsDataBeans(goodsDataBeans);

            //给里面listview适配器设置数据
            List<DataAllBean.DataBean.ListBean.DataInfoBean.DesignDesBean> designDesBeans = dataAllBean.getData().getList().get(position).getData_info().getDesign_des();
            detailActivityInListViewAdapter.setDesignDesBeans(designDesBeans);
        }

        @Override
        public void onFailed(VolleyError error) {

        }
    };

    private ImageView allCategoriesDetailBackImg;
    private ListView allCategoriesDetailOutListView, allCategoriesDetailInListView;
    private DetailActivityOutListViewAdapter detailActivityOutListViewAdapter;
    private DetailActivityInListViewAdapter detailActivityInListViewAdapter;
    private RelativeLayout relativeheaderTranslucentBackRlayoutLayout;
    private AccelerateDecelerateInterpolator mSmoothInterpolator;
    private GestureDetector gestureDetector;
    private MyOnGestureListener myOnGestureListener;

    @Override
    public void initActivity() {
        setContentView(R.layout.activity_all_categories_detail);

        allCategoriesDetailBackImg = (ImageView) findViewById(R.id.all_categories_detail_back_img);
        allCategoriesDetailOutListView = (ListView) findViewById(R.id.all_categories_detail_out_list_view);
        allCategoriesDetailInListView = (ListView) findViewById(R.id.all_categories_detail_in_list_view);


        detailActivityOutListViewAdapter = new DetailActivityOutListViewAdapter(this);
        detailActivityInListViewAdapter = new DetailActivityInListViewAdapter(this);
        allCategoriesDetailOutListView.setAdapter(detailActivityOutListViewAdapter);
        allCategoriesDetailInListView.setAdapter(detailActivityInListViewAdapter);

        View view = LayoutInflater.from(this).inflate(R.layout.listview_header_translucent, null);
        relativeheaderTranslucentBackRlayoutLayout = (RelativeLayout) view.findViewById(R.id.header_translucent_back_rlayout);

        View viewOut = LayoutInflater.from(this).inflate(R.layout.listview_header_transparent, null);

        allCategoriesDetailOutListView.addHeaderView(viewOut);
        allCategoriesDetailInListView.addHeaderView(view);

        //接收上一个fragment传入的position
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);


        networkTools.getNetworkData(url, networkListener);

        //TODO
        allCategoriesDetailInListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                switch (scrollState) {
                    //静止时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        scrollFlg = false;
                        break;
                    //触摸时
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        scrollFlg = true;
                        break;
                    //放开时
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        scrollFlg = true;
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                allCategoriesDetailOutListView.smoothScrollToPosition(firstVisibleItem);
                if (scrollFlg = true) {
                    //向上滑动
                    if (firstVisibleItem > lastPos) {
//                        allCategoriesDetailOutListView.setSelectionFromTop(0, 200);
                    }
                    //向下滑动
                    if (firstVisibleItem < lastPos) {

                    }
                    lastPos = firstVisibleItem;
                }
////                    allCategoriesDetailInListView.setSelection();
//                    allCategoriesDetailOutListView.setFriction(ViewConfiguration.getScrollFriction());


//                }
            }
        });


        //给最底层listview设置焦点
        allCategoriesDetailOutListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return allCategoriesDetailInListView.dispatchTouchEvent(event);
            }
        });

        myOnGestureListener = new MyOnGestureListener();
        gestureDetector = new GestureDetector(myOnGestureListener);
    }

    int lastPos = 0;
    boolean scrollFlg = false;


    /**
     * 获取滚动的高度，用于检测是否需要滚动
     */
    private int getScrollY() {
        int scrollY = 0;
        int itemScrollY = 0;
        int itemNum = allCategoriesDetailOutListView.getFirstVisiblePosition();
        View firstVisible = allCategoriesDetailOutListView.getChildAt(0);
        if (firstVisible == null) {
            return scrollY;
        }
        if (itemNum >= 1) {
            itemScrollY = relativeheaderTranslucentBackRlayoutLayout.getMeasuredHeight();
        }
        scrollY = itemScrollY - firstVisible.getTop();
        return scrollY;
    }

    //求大小
    public static float clamp(float value, float min, float max) {
        return Math.max(Math.min(value, max), min);
    }


    class MyOnGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }
}
