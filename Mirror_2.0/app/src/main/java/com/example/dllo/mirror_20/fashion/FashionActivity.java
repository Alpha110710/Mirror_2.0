package com.example.dllo.mirror_20.fashion;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.example.dllo.mirror_20.Bean.DataAllBean;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.BaseActivity;
import com.example.dllo.mirror_20.networktools.NetworkListener;
import com.example.dllo.mirror_20.networktools.NetworkTools;
import com.example.dllo.mirror_20.projectshare.ProjectShareBean;
import com.example.dllo.mirror_20.view.VerticalViewPager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by dllo on 16/6/23.
 */
public class FashionActivity extends BaseActivity implements View.OnClickListener {
    private VerticalViewPager verticalViewPager;
    private ImageView fashionBackgroundImg;
    private Button close, share;
    private ArrayList<Fragment> fragments;
    private NetworkTools networkTools;
    private String url = "http://api.mirroreye.cn/index.php/story/story_list";
    private HashMap<String, String> map;
    private FashionAdapter fashionAdapter;
    private ProjectShareBean bBean;
    private DataAllBean dataAllBean;
    private int pos = -1;
    private int specialPos = -1;
    private String specialUrl = "http://lizhongren.com.cn/mengke/jsonhandle.php";
    //在这里进行解析
    private NetworkListener networkListener = new NetworkListener() {
        @Override
        public void onSuccessed(String result) {
            Gson gson = new Gson();
            bBean = gson.fromJson(result, ProjectShareBean.class);


            for (int i = 0; i < bBean.getData().getList().get(pos).getStory_data().getText_array().size(); i++) {
                //传的是一个类
                fragments.add(FashionFragment.createFragment(bBean.getData().getList().get(pos).getStory_data().getText_array().get(i)));

                //解析背景里面的图片
                networkTools.getNetworkImage(bBean.getData().getList().get(pos)
                                .getStory_data().getImg_array().get(0),
                        fashionBackgroundImg);

            }

            fashionAdapter.setFragments(fragments);
        }

        @Override
        public void onFailed(VolleyError error) {

        }
    };


    private NetworkListener networkListenerSpecial = new NetworkListener() {
        @Override
        public void onSuccessed(String result) {
            Gson gson = new Gson();
            dataAllBean = gson.fromJson(result, DataAllBean.class);


            for (int i = 0; i < dataAllBean.getData().getList().get(specialPos).getData_info().getStory_data().getText_array().size(); i++) {
                //传的是一个类
                fragments.add(FashionFragment.createFragment(dataAllBean.getData().getList().get(specialPos).getData_info().getStory_data().getText_array().get(i)));

                //解析背景里面的图片
                networkTools.getNetworkImage(dataAllBean.getData().getList().get(specialPos)
                                .getData_info().getStory_data().getImg_array().get(0),
                        fashionBackgroundImg);

            }

            fashionAdapter.setFragments(fragments);
        }

        @Override
        public void onFailed(VolleyError error) {

        }
    };

    //viewPager的接听事件
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //viewPager每滑动一次 都要给activity换background

            if (pos != -1) {

                networkTools.getNetworkImage(bBean.getData().getList().get(pos)
                                .getStory_data().getImg_array().get(position),
                        fashionBackgroundImg);
            } else if (specialPos != -1) {
                networkTools.getNetworkImage(dataAllBean.getData().getList().get(specialPos)
                                .getData_info().getStory_data().getImg_array().get(position),
                        fashionBackgroundImg);
            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    @Override
    public void initActivity() {
        setContentView(R.layout.activity_fashion);

        verticalViewPager = (VerticalViewPager) findViewById(R.id.fashion_vertical_viewpager);
        close = (Button) findViewById(R.id.fashion_close);
        share = (Button) findViewById(R.id.fashion_share);
        fashionBackgroundImg = (ImageView) findViewById(R.id.fashion_background_img);

        Intent intent = getIntent();
        //接收传过来的position值  这个position是用来确定第几个item点击的
        pos = intent.getIntExtra("position", -1);
        specialPos = intent.getIntExtra("pos", -1);


        //设置图片拉伸全屏
        fashionBackgroundImg.setScaleType(ImageView.ScaleType.FIT_XY);

        networkTools = new NetworkTools();
        map = new HashMap<>();
        //fragment数据是在这装进去的
        fashionAdapter = new FashionAdapter(getSupportFragmentManager());
        fragments = new ArrayList<>();

        verticalViewPager.setAdapter(fashionAdapter);

        if (pos != -1) {
            //拼的参数
            map.put("device_type", "1");
            //解析
            networkTools.getNetworkPostData(url, map, networkListener);
        } else if (specialPos != -1) {
            networkTools.getNetworkData(specialUrl, networkListenerSpecial);
        }


        close.setOnClickListener(this);
        share.setOnClickListener(this);
        //viewPager点击
        verticalViewPager.setOnPageChangeListener(onPageChangeListener);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fashion_close:
                finish();
                break;
            case R.id.fashion_share:
//                Toast.makeText(this, "没写呢,点我干啥", Toast.LENGTH_SHORT).show();
                showShare();
                break;
        }
    }


    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

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
