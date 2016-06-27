package com.example.dllo.mirror_20.allcategories;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.BaseActivity;
import com.example.dllo.mirror_20.networktools.NetworkTools;
import com.example.dllo.mirror_20.view.SmoothImageView;

/**
 * Created by dllo on 16/6/25.
 * 放大图片
 *
 */
public class BigPictureActivity extends BaseActivity implements View.OnClickListener {

    private String imgUrl;
    private NetworkTools networkTools;
    private int mPosition;
    private int mLocationX;
    private int mLocationY;
    private int mWidth;
    private int mHeight;
    private SmoothImageView imageView;


    @Override
    public void initActivity() {
        networkTools = new NetworkTools();

        //接受WearPictureListViewAdapter传入的值
        Intent intent = getIntent();
        imgUrl = intent.getStringExtra("imgUrl");
        mPosition = getIntent().getIntExtra("position", 0);
        mLocationX = getIntent().getIntExtra("locationX", 0);
        mLocationY = getIntent().getIntExtra("locationY", 0);
        mWidth = getIntent().getIntExtra("width", 0);
        mHeight = getIntent().getIntExtra("height", 0);
        //自定义view
        imageView = new SmoothImageView(BigPictureActivity.this);
        //自定义设置方法
        imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        imageView.transformIn();
        imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //没有绑定布局 设置imageView布局
        setContentView(imageView);

        //解析图片
        networkTools.getImg(imageView, imgUrl);


        imageView.setOnClickListener(this);


    }




    //以下是抄写原demo的代码
    @Override
    public void onBackPressed() {
        imageView.setOnTransformListener(new SmoothImageView.TransformListener() {
            @Override
            public void onTransformComplete(int mode) {
                if (mode == 2) {
                    finish();
                }
            }
        });
        imageView.transformOut();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void onClick(View v) {

        imageView.setOnTransformListener(new SmoothImageView.TransformListener() {
            @Override
            public void onTransformComplete(int mode) {
                if (mode == 2) {
                    finish();
                }
            }
        });
        imageView.transformOut();
    }

}
