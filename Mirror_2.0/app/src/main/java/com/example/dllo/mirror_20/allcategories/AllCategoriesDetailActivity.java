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
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.dllo.mirror_20.Bean.DataAllBean;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.BaseActivity;
import com.example.dllo.mirror_20.networktools.NetworkListener;
import com.example.dllo.mirror_20.networktools.NetworkTools;
import com.example.dllo.mirror_20.view.NoScrollListview;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dllo on 16/6/21.
 */
public class AllCategoriesDetailActivity extends BaseActivity implements View.OnClickListener {

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

    private ImageView allCategoriesDetailBackImg, allCategoriesDetailRlayoutBackImg;
    private ListView allCategoriesDetailInListView;
    private ListView allCategoriesDetailOutListView;
    private DetailActivityOutListViewAdapter detailActivityOutListViewAdapter;
    private DetailActivityInListViewAdapter detailActivityInListViewAdapter;
    private RelativeLayout relativeheaderTranslucentBackRlayoutLayout, allCategoriesDetailRlayout;
    private AccelerateDecelerateInterpolator mSmoothInterpolator;
    private int pos = -1;
    private boolean flag = true;
    private TextView allCategoriesDetailRlayoutPictureTv, allCategoriesDetailRlayoutBuyTv;

    @Override
    public void initActivity() {
        setContentView(R.layout.activity_all_categories_detail);

        allCategoriesDetailBackImg = (ImageView) findViewById(R.id.all_categories_detail_back_img);
        allCategoriesDetailOutListView = (ListView) findViewById(R.id.all_categories_detail_out_list_view);
        allCategoriesDetailInListView = (ListView) findViewById(R.id.all_categories_detail_in_list_view);
        allCategoriesDetailRlayout = (RelativeLayout) findViewById(R.id.all_categories_detail_rlayout);
        allCategoriesDetailRlayoutBackImg = (ImageView)findViewById(R.id.all_categories_detail_rlayout_back_img);
        allCategoriesDetailRlayoutPictureTv = (TextView)findViewById(R.id.all_categories_detail_rlayout_picture_tv);
        allCategoriesDetailRlayoutBuyTv = (TextView)findViewById(R.id.all_categories_detail_rlayout_buy_tv);


        detailActivityOutListViewAdapter = new DetailActivityOutListViewAdapter(this);
        detailActivityInListViewAdapter = new DetailActivityInListViewAdapter(this);
        allCategoriesDetailOutListView.setAdapter(detailActivityOutListViewAdapter);
        allCategoriesDetailInListView.setAdapter(detailActivityInListViewAdapter);


        allCategoriesDetailRlayoutBackImg.setOnClickListener(this);
        allCategoriesDetailRlayoutPictureTv.setOnClickListener(this);

        //头布局
        View view = LayoutInflater.from(this).inflate(R.layout.listview_header_translucent, null);
        relativeheaderTranslucentBackRlayoutLayout = (RelativeLayout) view.findViewById(R.id.header_translucent_back_rlayout);

        final View viewOut = LayoutInflater.from(this).inflate(R.layout.listview_header_transparent, null);
        View viewFlow = LayoutInflater.from(this).inflate(R.layout.listview_out_flow, null);

        //加头加尾
        allCategoriesDetailOutListView.addHeaderView(viewOut);
        allCategoriesDetailOutListView.addFooterView(viewFlow);
        allCategoriesDetailInListView.addHeaderView(view);

        //接收上一个fragment传入的position
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);

        //开始解析
        networkTools.getNetworkData(url, networkListener);

        //里层listview滑动监听
        allCategoriesDetailInListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //参考view为里面listview的第一个item
                View view1 = allCategoriesDetailInListView.getChildAt(1);
                if (view1 == null) {
                    return;
                }
                //算法计算相对距离
                int scrolly = -view1.getTop() + allCategoriesDetailInListView.getPaddingTop() +
                        allCategoriesDetailInListView.getFirstVisiblePosition() * view1.getHeight();
                //设置外层listview的滑动距离
                allCategoriesDetailOutListView.setSelectionFromTop(1, -(int) (scrolly * 1.2));

                //增加的过滤判断
                if (firstVisibleItem != pos) {
                    pos = firstVisibleItem;
                    //判断设置最下面的相对布局 可见不可见
                    if (firstVisibleItem == 1 && flag) {
                        allCategoriesDetailRlayout.setVisibility(View.VISIBLE);
                        setAmination();
                        flag = false;
                    } else if (firstVisibleItem == 0 && !flag) {
                        setDismissAmination();
                        flag = true;
                    }

                }
            }
        });


        //给最底层listview设置焦点
        allCategoriesDetailOutListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return allCategoriesDetailInListView.dispatchTouchEvent(event);
            }
        });


    }


    //设置动画
    private void setAmination() {
        //平移动画
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1,
                Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_PARENT, 0);
        translateAnimation.setDuration(250);
        translateAnimation.setRepeatCount(0);
        allCategoriesDetailRlayout.startAnimation(translateAnimation);

    }

    //设置消失动画
    private void setDismissAmination() {

        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, -1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_PARENT, 0);
        translateAnimation.setDuration(250);
        translateAnimation.setRepeatCount(0);
        allCategoriesDetailRlayout.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //对动画进行监听,设置消失
                allCategoriesDetailRlayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.all_categories_detail_rlayout_back_img:
                finish();
                break;
            case R.id.all_categories_detail_rlayout_picture_tv:
                //Todo: 调转播放界面
                break;
            case R.id.all_categories_detail_rlayout_buy_tv:
                //todo:判断登录,进入购买

                break;
        }
    }
}
