package com.example.dllo.mirror_20.allcategories;

import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.BaseActivity;
import com.example.dllo.mirror_20.networktools.NetworkTools;

/**
 * Created by dllo on 16/6/25.
 */
public class BigPictureActivity extends BaseActivity {

    private ImageView bigPictureImg;
    private String imgUrl;
    private NetworkTools networkTools;


    @Override
    public void initActivity() {
        setContentView(R.layout.activity_big_picture);

        bigPictureImg = (ImageView) findViewById(R.id.big_picture_img);

        networkTools = new NetworkTools();

        Intent intent = getIntent();
        imgUrl = intent.getStringExtra("imgUrl");

        networkTools.getNetworkImage(imgUrl, bigPictureImg);

        setAmination();


    }

    //设置动画
    private void setAmination() {
        //平移动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(3000);
        scaleAnimation.setRepeatCount(0);
        bigPictureImg.startAnimation(scaleAnimation);

    }
}
