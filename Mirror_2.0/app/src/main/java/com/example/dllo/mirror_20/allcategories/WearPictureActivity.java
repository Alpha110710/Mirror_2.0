package com.example.dllo.mirror_20.allcategories;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dllo on 16/6/24.
 */
public class WearPictureActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private VideoView wearPictureVideoView;
    private ListView wearPictureListView;
    private ImageView pictureHeaderPictureImg, pictureHeaderPauseImg;
    private NetworkTools networkTools;
    private String url = "http://api.mirroreye.cn/index.php/index/mrtj";//post请求 url 返回数据里有视频接口
    private HashMap<String, String> map;
    private int position;
    private WearPictureListViewAdapter listViewAdapter;
    private ArrayList<String> imgUrls;
    private String videoAddress;
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
                        Uri uri = Uri.parse(videoAddress);
//                        Uri uri = Uri.parse("http://static.video.qq.com/TPout.swf?vid=u0020376cjn&auto=0");
                        wearPictureVideoView.setVideoURI(uri);
                        //设置给mediaController系统自定义的bar  设置关联
                        MediaController mediaController = new MediaController(WearPictureActivity.this);
                        wearPictureVideoView.setMediaController(mediaController);
                        mediaController.setMediaPlayer(wearPictureVideoView);
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

    @Override
    public void initActivity() {
        setContentView(R.layout.activity_wear_picture);

        wearPictureListView = (ListView) findViewById(R.id.wear_picture_list_view);
        wearPictureBackImg = (ImageView) findViewById(R.id.wear_picture_back_img);
        wearPictureBuyTv = (TextView) findViewById(R.id.wear_picture_buy_tv);

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
        wearPictureVideoView = (VideoView) view.findViewById(R.id.wear_picture_video_view);
        pictureHeaderPictureImg = (ImageView) view.findViewById(R.id.picture_header_picture_img);
        pictureHeaderPauseImg = (ImageView) view.findViewById(R.id.picture_header_pause_img);


        wearPictureListView.addHeaderView(view);
        wearPictureListView.setAdapter(listViewAdapter);
        //post请求
        networkTools.getNetworkPostData(url, map, networkListener);
        //设置监听事件
        pictureHeaderPauseImg.setOnClickListener(this);
        wearPictureBuyTv.setOnClickListener(this);
        wearPictureBackImg.setOnClickListener(this);
        wearPictureListView.setOnItemClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.picture_header_pause_img:
                //点击播放视频
                wearPictureVideoView.setVisibility(View.VISIBLE);
                pictureHeaderPictureImg.setVisibility(View.GONE);
                pictureHeaderPauseImg.setVisibility(View.GONE);

                if (!videoAddress.equals("")) {

//                    ViewGroup.LayoutParams params = wearPictureVideoView.getLayoutParams();//设置参数
//                    WindowManager wm = (WindowManager) this.getSystemService(WINDOW_SERVICE);
//                    params.width = wm.getDefaultDisplay().getWidth();
//                    wearPictureVideoView.setLayoutParams(params);
                    //设置播放是的焦点
                    wearPictureVideoView.requestFocus();
                    wearPictureVideoView.start();//播放
                }

                break;
            case R.id.wear_picture_back_img:
                finish();
                break;
            case R.id.wear_picture_buy_tv:
                //todo:跳转购买界面 判断登录
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, BigPictureActivity.class);
        intent.putExtra("imgUrl", imgUrls.get(position - 1));
        startActivity(intent);

    }
}
