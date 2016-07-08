package com.example.dllo.mirror_20.allcategories;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.dllo.mirror_20.Bean.DBBean;
import com.example.dllo.mirror_20.Bean.DBDataBean;
import com.example.dllo.mirror_20.Bean.DataAllBean;
import com.example.dllo.mirror_20.Bean.EventBusBean;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.BaseActivity;
import com.example.dllo.mirror_20.login.LoginActivity;
import com.example.dllo.mirror_20.login.LoginBean;
import com.example.dllo.mirror_20.networktools.NetworkListener;
import com.example.dllo.mirror_20.networktools.NetworkTools;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import com.example.dllo.mirror_20.networktools.URLValue;
import com.example.dllo.mirror_20.sql.SQLTools;
import com.example.dllo.mirror_20.networktools.NetworkListener;
import com.example.dllo.mirror_20.networktools.NetworkTools;
import com.example.dllo.mirror_20.orderdetails.OrderDetailsActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by dllo on 16/6/21.
 */
public class AllCategoriesDetailActivity extends BaseActivity implements View.OnClickListener {

    private String url = "http://lizhongren.com.cn/mengke/jsonhandle.php";
    private NetworkTools networkTools = new NetworkTools();
    private Gson gson;
    private DataAllBean dataAllBean;
    private int position;
    private SQLTools tools;
    private String goods_id;
    private String goods_img;
    private String goods_name;
    private String goods_price;
    private String model;
    private String product_area;
    private String introContent;
    private String cellHeight;
    private String name;
    private String location;
    private String country;
    private String english;


    private String goodsPic;//sun hao
    private String goodsName;//sun hao
    private String goodsPrice;//sun hao
    private NetworkListener networkListener = new NetworkListener() {
        @Override
        public void onSuccessed(String result) {
            gson = new Gson();
            dataAllBean = gson.fromJson(result, DataAllBean.class);

            if (dataAllBean.getData().getList().get(position).getType().equals("1")) {
                //解析背景图片
                Picasso.with(AllCategoriesDetailActivity.this).load(dataAllBean.getData().getList().get(position).getData_info().getGoods_img())
                        .fit().into(allCategoriesDetailBackImg);
                for (int i = 0; i < dataAllBean.getData().getList().get(position).getData_info().getGoods_data().size(); i++) {
                    location = dataAllBean.getData().getList().get(position).getData_info().getGoods_data().get(i).getLocation();
                    name = dataAllBean.getData().getList().get(position).getData_info().getGoods_data().get(i).getName();
                    introContent = dataAllBean.getData().getList().get(position).getData_info().getGoods_data().get(i).getIntroContent();
                    cellHeight = dataAllBean.getData().getList().get(position).getData_info().getGoods_data().get(i).getCellHeight();
                    english = dataAllBean.getData().getList().get(position).getData_info().getGoods_data().get(i).getEnglish();
                    country = dataAllBean.getData().getList().get(position).getData_info().getGoods_data().get(i).getCountry();
                }
                goods_id = dataAllBean.getData().getList().get(position).getData_info().getGoods_id();
                goods_img = dataAllBean.getData().getList().get(position).getData_info().getGoods_img();
                goods_name = dataAllBean.getData().getList().get(position).getData_info().getGoods_name();
                goods_price = dataAllBean.getData().getList().get(position).getData_info().getGoods_price();
                model = dataAllBean.getData().getList().get(position).getData_info().getModel();
                product_area = dataAllBean.getData().getList().get(position).getData_info().getProduct_area();

                goodsPic = dataAllBean.getData().getList().get(position).getData_info().getGoods_pic();
                goodsName = dataAllBean.getData().getList().get(position).getData_info().getGoods_name();
                goodsPrice = dataAllBean.getData().getList().get(position).getData_info().getGoods_price();

            }
            //给外面listview适配器设置数据
            List<DataAllBean.DataBean.ListBean.DataInfoBean.GoodsDataBean> goodsDataBeans = dataAllBean.getData().getList().get(position).getData_info().getGoods_data();
            detailActivityOutListViewAdapter.setGoodsDataBeans(goodsDataBeans);

            //给里面listview适配器设置数据
            List<DataAllBean.DataBean.ListBean.DataInfoBean.DesignDesBean> designDesBeans = dataAllBean.getData().getList().get(position).getData_info().getDesign_des();
            detailActivityInListViewAdapter.setDesignDesBeans(designDesBeans);

            //设置里面listview头的内容
            headerTranslucentInfoDesTv.setText(dataAllBean.getData().getList().get(position).getData_info().getInfo_des());
            headerTranslucentBrandTv.setText(dataAllBean.getData().getList().get(position).getData_info().getBrand());
            headerTranslucentGoodsNameTv.setText(dataAllBean.getData().getList().get(position).getData_info().getGoods_name());
            headeTranslucentGoodsPriceTv.setText(dataAllBean.getData().getList().get(position).getData_info().getGoods_price());
        }


        @Override
        public void onFailed(VolleyError error) {

        }
    };

    private ImageView allCategoriesDetailBackImg, allCategoriesDetailRlayoutBackImg, share;
    private ListView allCategoriesDetailInListView;
    private ListView allCategoriesDetailOutListView;
    private DetailActivityOutListViewAdapter detailActivityOutListViewAdapter;
    private DetailActivityInListViewAdapter detailActivityInListViewAdapter;
    private RelativeLayout relativeheaderTranslucentBackRlayoutLayout;
    private LinearLayout allCategoriesDetailLlayout;
    private AccelerateDecelerateInterpolator mSmoothInterpolator;
    private int pos = -1;
    private boolean flag = true;
    private TextView headerTranslucentInfoDesTv;
    private TextView headerTranslucentBrandTv;
    private TextView headerTranslucentGoodsNameTv;
    private TextView headeTranslucentGoodsPriceTv;
    private TextView allCategoriesDetailRlayoutPictureTv, allCategoriesDetailRlayoutBuyTv, allCategoriesDetailRlayoutBuyCar;


    @Override
    public void initActivity() {
        setContentView(R.layout.activity_all_categories_detail);

        allCategoriesDetailBackImg = (ImageView) findViewById(R.id.all_categories_detail_back_img);
        allCategoriesDetailOutListView = (ListView) findViewById(R.id.all_categories_detail_out_list_view);
        allCategoriesDetailInListView = (ListView) findViewById(R.id.all_categories_detail_in_list_view);

        allCategoriesDetailLlayout = (LinearLayout) findViewById(R.id.all_categories_detail_rlayout);
        allCategoriesDetailRlayoutBackImg = (ImageView) findViewById(R.id.all_categories_detail_rlayout_back_img);
        allCategoriesDetailRlayoutPictureTv = (TextView) findViewById(R.id.all_categories_detail_rlayout_picture_tv);
        allCategoriesDetailRlayoutBuyTv = (TextView) findViewById(R.id.all_categories_detail_rlayout_buy_tv);
        allCategoriesDetailRlayoutBuyCar = (TextView) findViewById(R.id.all_categories_detail_rlayout_buy_car);


        detailActivityOutListViewAdapter = new DetailActivityOutListViewAdapter(this);
        detailActivityInListViewAdapter = new DetailActivityInListViewAdapter(this);
        allCategoriesDetailOutListView.setAdapter(detailActivityOutListViewAdapter);
        allCategoriesDetailInListView.setAdapter(detailActivityInListViewAdapter);


        allCategoriesDetailRlayoutBackImg.setOnClickListener(this);
        allCategoriesDetailRlayoutPictureTv.setOnClickListener(this);
        allCategoriesDetailRlayoutBuyCar.setOnClickListener(this);
        allCategoriesDetailRlayoutBuyTv.setOnClickListener(this);

        //头布局
        View view = LayoutInflater.from(this).inflate(R.layout.listview_header_translucent, null);
        relativeheaderTranslucentBackRlayoutLayout = (RelativeLayout) view.findViewById(R.id.header_translucent_back_rlayout);

        headerTranslucentInfoDesTv = (TextView) view.findViewById(R.id.header_translucent_info_des_tv);
        headerTranslucentBrandTv = (TextView) view.findViewById(R.id.header_translucent_brand_tv);
        headerTranslucentGoodsNameTv = (TextView) view.findViewById(R.id.header_translucent_goods_name_tv);
        headeTranslucentGoodsPriceTv = (TextView) view.findViewById(R.id.header_translucent_goods_price_tv);

        share = (ImageView) view.findViewById(R.id.header_translucent_title_share);
        share.setOnClickListener(this);

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

                //头布局渐变
                View b = allCategoriesDetailInListView.getChildAt(0);
                if (b == null) {
                    return;
                }
                int scrollyHeader = -b.getTop();
                relativeheaderTranslucentBackRlayoutLayout.setAlpha(1.1f - (float) scrollyHeader / 1700);


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
                        allCategoriesDetailLlayout.setVisibility(View.VISIBLE);
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
//                return true;
//                Log.d("AllCategoriesDetailActi", "allCategoriesDetailInListView.dispatchTouchEvent(event):" + allCategoriesDetailInListView.dispatchTouchEvent(event));
//                if (!allCategoriesDetailInListView.dispatchTouchEvent(event)){
//                    Log.d("AllCategoriesDetailActi", "aaa");
//                }
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
        allCategoriesDetailLlayout.startAnimation(translateAnimation);

    }

    //设置消失动画
    private void setDismissAmination() {

        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, -1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_PARENT, 0);
        translateAnimation.setDuration(250);
        translateAnimation.setRepeatCount(0);
        allCategoriesDetailLlayout.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //对动画进行监听,设置消失
                allCategoriesDetailLlayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_categories_detail_rlayout_back_img:
                finish();
                break;
            case R.id.all_categories_detail_rlayout_picture_tv:
                //Todo: 调转播放界面
                Intent intent = new Intent(this, WearPictureActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);

                break;
            case R.id.all_categories_detail_rlayout_buy_tv:
                //todo:判断登录,进入购买
                SharedPreferences getSp = getSharedPreferences("test", MODE_PRIVATE);
                String token = getSp.getString("token", "1");

                if (!token.equals("1")) {

                    Intent intent1 = new Intent(this, OrderDetailsActivity.class);
                    intent1.putExtra("goodsPic", goodsPic);
                    intent1.putExtra("goodsName", goodsName);
                    intent1.putExtra("goodsPrice", goodsPrice);
                    startActivity(intent1);
                } else {

                    Intent intent2 = new Intent(this, LoginActivity.class);
                    intent2.putExtra("goodsPic", goodsPic);
                    intent2.putExtra("goodsName", goodsName);
                    intent2.putExtra("goodsPrice", goodsPrice);
                    intent2.putExtra("type", 1);
                    startActivity(intent2);



                }

                break;
            case R.id.header_translucent_title_share:
                showShare();
                break;
            case R.id.all_categories_detail_rlayout_buy_car:
                // TODO: 加入购物车

                setBuyCar();
                break;
        }

    }

    public void setBuyCar() {
        tools = new SQLTools(this);
        DBBean dbBean = new DBBean();

        dbBean.setGoods_id(goods_id);
        dbBean.setGoods_name(goods_name);
        dbBean.setProduct_area(product_area);
        dbBean.setGoods_img(goods_img);
        dbBean.setModel(model);
        dbBean.setGoods_price(goods_price);

        tools.insert(dbBean);

    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

                // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
                //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("我是Title");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
}
