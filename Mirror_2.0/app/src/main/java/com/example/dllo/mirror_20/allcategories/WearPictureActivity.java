package com.example.dllo.mirror_20.allcategories;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.VolleyError;
import com.example.dllo.mirror_20.Bean.WearPictureBean;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.BaseActivity;
import com.example.dllo.mirror_20.networktools.NetworkListener;
import com.example.dllo.mirror_20.networktools.NetworkTools;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dllo on 16/6/24.
 * 图集穿戴
 */
public class WearPictureActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, UniversalVideoView.VideoViewCallback {

    private UniversalVideoView wearPictureVideoView;
    private UniversalMediaController mediaController;
    private ListView wearPictureListView;
    private ImageView pictureHeaderPictureImg, pictureHeaderPauseImg;
    private FrameLayout mVideoLayout;
    private NetworkTools networkTools;
    private String url = "http://api.mirroreye.cn/index.php/index/mrtj";//post请求 url 返回数据里有视频接口
    private HashMap<String, String> map;
    private int position;
    private WearPictureListViewAdapter listViewAdapter;
    private ArrayList<String> imgUrls;
    private String videoAddress;
    private int mSeekPosition;
    private NetworkListener networkListener = new NetworkListener() {
        @Override
        public void onSuccessed(String result) {
            Gson gson = new Gson();
            WearPictureBean wearPictureBean = gson.fromJson(result, WearPictureBean.class);
            //遍历实体类
            for (WearPictureBean.DataBean.ListBean.DataInfoBean.WearVideoBean wearVideoBean : wearPictureBean.getData().getList().get(position).getData_info().getWear_video()) {
                //type为2时 wearVideoBean 为空
                if (wearVideoBean != null) {
                    //将不是 8和9类型的 实体类挑出 传给适配器
                    if (!wearVideoBean.getType().equals("9") && !wearVideoBean.getType().equals("8")) {

                        imgUrls.add(wearVideoBean.getData());

                    } else if (wearVideoBean.getType().equals("9")) {
                        //将9类型的数据拿出为解析视频的背景图片
                        networkTools.getNetworkImage(wearVideoBean.getData(), pictureHeaderPictureImg);

                    } else if (wearVideoBean.getType().equals("8")) {
                        //将视频接口取出  付给videoView
                        videoAddress = wearVideoBean.getData();
//                        Log.d("WearPictureActivity", videoAddress);
//                        Uri uri = Uri.parse(videoAddress);
//                        Uri uri = Uri.parse("http://static.video.qq.com/TPout.swf?vid=u0020376cjn&auto=0");
                        wearPictureVideoView.setVideoPath(videoAddress);


                    }

                }
            }
            listViewAdapter.setImgUrls(imgUrls);


        }

        @Override
        public void onFailed(VolleyError error) {

        }
    };
    private ImageView wearPictureBackImg;
    private TextView wearPictureBuyTv;
    private int cachedHeight;
    private static final String TAG = "MainActivity";
    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
    private boolean isFullscreen;
    private RelativeLayout wearPictureTitleRlayout;
    private RelativeLayout wearPictureRlayout;

    /**
     * 当屏幕旋转会走这个方法
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE ){

            setMargins(mVideoLayout, 0, 0, 0, 0);

            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = getWindowManager().getDefaultDisplay().getWidth();
            layoutParams.height = getWindowManager().getDefaultDisplay().getHeight();

            mVideoLayout.setLayoutParams(layoutParams);
            wearPictureRlayout.setVisibility(View.GONE);
            wearPictureTitleRlayout.setVisibility(View.GONE);
        }else{

            setMargins(mVideoLayout, 25, 10, 25, 10);

            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = this.cachedHeight;
            mVideoLayout.setLayoutParams(layoutParams);
            wearPictureTitleRlayout.setVisibility(View.VISIBLE);
            wearPictureRlayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initActivity() {
        setContentView(R.layout.activity_wear_picture);

        wearPictureListView = (ListView) findViewById(R.id.wear_picture_list_view);
        wearPictureBackImg = (ImageView) findViewById(R.id.wear_picture_back_img);
        wearPictureBuyTv = (TextView) findViewById(R.id.wear_picture_buy_tv);
        wearPictureTitleRlayout = (RelativeLayout) findViewById(R.id.wear_picture_title_rlayout);
        wearPictureRlayout = (RelativeLayout) findViewById(R.id.wear_picture_rlayout);

        //intent 获得上级传入的position
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);

        //初始化
        networkTools = new NetworkTools();
        listViewAdapter = new WearPictureListViewAdapter(this);
        imgUrls = new ArrayList<>();
        videoAddress = new String();

        map = new HashMap<>();
        map.put("device_type", "1");
        map.put("version", "1.0.1");

        //绑定头布局组件
        View view = LayoutInflater.from(this).inflate(R.layout.item_listview_wear_picture_header, null);
        wearPictureVideoView = (UniversalVideoView) view.findViewById(R.id.mVideoView);
        pictureHeaderPictureImg = (ImageView) view.findViewById(R.id.picture_header_picture_img);
        pictureHeaderPauseImg = (ImageView) view.findViewById(R.id.picture_header_pause_img);
        mediaController = (UniversalMediaController) view.findViewById(R.id.mMediaController);
        mVideoLayout = (FrameLayout) view.findViewById(R.id.video_layout);
//        setMargins(mVideoLayout, 50, 10, 50, 10);
        mVideoLayout.setOnClickListener(this);

        wearPictureVideoView.setMediaController(mediaController);
        wearPictureVideoView.setVideoViewCallback(this);
        wearPictureVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
//                Log.d("WearPictureActivity", "onCompletion ");
            }
        });


        wearPictureListView.addHeaderView(view);
        wearPictureListView.setAdapter(listViewAdapter);
        //post请求
        networkTools.getNetworkPostData(url, map, networkListener);
        //设置监听事件
        pictureHeaderPauseImg.setOnClickListener(this);
        wearPictureBuyTv.setOnClickListener(this);
        wearPictureBackImg.setOnClickListener(this);
        wearPictureListView.setOnItemClickListener(this);


        setVideoAreaSize();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.picture_header_pause_img:
                //点击播放视频
                wearPictureVideoView.setVisibility(View.VISIBLE);
                pictureHeaderPictureImg.setVisibility(View.GONE);
                pictureHeaderPauseImg.setVisibility(View.GONE);


                if (mSeekPosition > 0) {
                    wearPictureVideoView.seekTo(mSeekPosition);
                }
                wearPictureVideoView.start();

                break;
            case R.id.wear_picture_back_img:
                finish();
                break;
            case R.id.wear_picture_buy_tv:
                //todo:跳转购买界面 判断登录
                break;

            case R.id.video_layout:
                wearPictureVideoView.pause();
                break;
        }
    }

    //在适配器里用的点击事件  这里没有用上
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    protected void onPause() {
        super.onPause();
        //videoview暂停时 记录播放位置

        if (wearPictureVideoView != null && wearPictureVideoView.isPlaying()) {
            mSeekPosition = wearPictureVideoView.getCurrentPosition();
//            Log.d("MainActivity", "onPause mSeekPosition=" + mSeekPosition);
            wearPictureVideoView.pause();
        }

    }


    /**
     * 置视频区域大小
     */
    private void setVideoAreaSize() {
        mVideoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mVideoLayout.getWidth();
                cachedHeight = (int) (width * 405f / 720f);

                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = cachedHeight;
                mVideoLayout.setLayoutParams(videoLayoutParams);
//                mVideoView.setVideoPath(VIDEO_URL);
                wearPictureVideoView.requestFocus();
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        Log.d(TAG, "onSaveInstanceState Position=" + wearPictureVideoView.getCurrentPosition());
        outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        mSeekPosition = outState.getInt(SEEK_POSITION_KEY);
//        Log.d(TAG, "onRestoreInstanceState Position=" + mSeekPosition);
    }

    //设置view的margin属性
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }



    @Override
    public void onScaleChange(boolean isFullscreen) {
//        this.isFullscreen = isFullscreen;
//        if (isFullscreen) {
//
//            setMargins(mVideoLayout, 0, 0, 0, 0);
//
//            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
//            layoutParams.width = getWindowManager().getDefaultDisplay().getWidth();
//            layoutParams.height = getWindowManager().getDefaultDisplay().getHeight();
//
//            mVideoLayout.setLayoutParams(layoutParams);
//            wearPictureRlayout.setVisibility(View.GONE);
//            wearPictureTitleRlayout.setVisibility(View.GONE);
//
//
//        } else {
//
//            setMargins(mVideoLayout, 25, 10, 25, 10);
//
//            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
//            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//            layoutParams.height = this.cachedHeight;
//            mVideoLayout.setLayoutParams(layoutParams);
//            wearPictureTitleRlayout.setVisibility(View.VISIBLE);
//            wearPictureRlayout.setVisibility(View.VISIBLE);
//
//
//        }
//
//        switchTitleBar(!isFullscreen);
    }

    private void switchTitleBar(boolean show) {
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            if (show) {
                supportActionBar.show();
            } else {
                supportActionBar.hide();
            }
        }
    }

    @Override
    public void onPause(MediaPlayer mediaPlayer) {
        pictureHeaderPauseImg.setVisibility(View.VISIBLE);
//        Log.d(TAG, "onPause UniversalVideoView callback");
    }

    @Override
    public void onStart(MediaPlayer mediaPlayer) {
//        Log.d(TAG, "onStart UniversalVideoView callback");
    }

    @Override
    public void onBufferingStart(MediaPlayer mediaPlayer) {
//        Log.d(TAG, "onBufferingStart UniversalVideoView callback");
    }

    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {
//        Log.d(TAG, "onBufferingEnd UniversalVideoView callback");
    }

    @Override
    public void onBackPressed() {
        if (this.isFullscreen) {
            wearPictureVideoView.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }
}
