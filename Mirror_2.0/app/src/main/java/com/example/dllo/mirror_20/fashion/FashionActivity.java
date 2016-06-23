package com.example.dllo.mirror_20.fashion;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.BaseActivity;
import com.example.dllo.mirror_20.networktools.NetworkListener;
import com.example.dllo.mirror_20.networktools.NetworkTools;
import com.example.dllo.mirror_20.projectshare.ProjectShareBean;
import com.example.dllo.mirror_20.view.VerticalViewPager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

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
    private int pos;
    private NetworkListener networkListener = new NetworkListener() {
        @Override
        public void onSuccessed(String result) {
            Gson gson = new Gson();
            bBean = gson.fromJson(result, ProjectShareBean.class);

            Intent intent=getIntent();

            pos=intent.getIntExtra("position",0);
            for (int i = 0; i < bBean.getData().getList().get(pos).getStory_data().getText_array().size(); i++) {
                fragments.add(FashionFragment.createFragment(
                        bBean.getData().getList().get(pos).getStory_data().getText_array().get(i)));

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

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            networkTools.getNetworkImage(bBean.getData().getList().get(pos)
                            .getStory_data().getImg_array().get(position),
                    fashionBackgroundImg);
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
        fashionBackgroundImg.setScaleType(ImageView.ScaleType.FIT_XY);

        networkTools = new NetworkTools();
        map = new HashMap<>();
        fashionAdapter = new FashionAdapter(getSupportFragmentManager());
        fragments = new ArrayList<>();

        verticalViewPager.setAdapter(fashionAdapter);

        map.put("device_type", "1");
        networkTools.getNetworkPostData(url, map, networkListener);

//        close.setFocusable(true);
//        close.setFocusableInTouchMode(true);
//        close.requestFocus();
//        close.requestFocusFromTouch();
//        share.setFocusable(true);
//        share.setFocusableInTouchMode(true);
//        share.requestFocus();
//        share.requestFocusFromTouch();
//        close.setOnFocusChangeListener(this);
//        share.setOnFocusChangeListener(this);

        close.setOnClickListener(this);
        share.setOnClickListener(this);

        verticalViewPager.setOnPageChangeListener(onPageChangeListener);

    }


//    @Override
//    public void OnClick(View v, boolean hasFocus) {
//        switch (v.getId()) {
//            case R.id.fashion_close:
//                finish();
//                break;
//            case R.id.fashion_share:
//                Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fashion_close:
           finish();
                break;
            case R.id.fashion_share:
                Toast.makeText(this, "没写呢,点我干啥", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
