package com.example.dllo.mirror_20.allcategories;

import android.widget.ListView;
import android.widget.VideoView;

import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.BaseActivity;

/**
 * Created by dllo on 16/6/24.
 */
public class WearPictureActivity extends BaseActivity{

    private VideoView wearPictureVideoView;
    private ListView wearPictureListView;


    @Override
    public void initActivity() {
        setContentView(R.layout.activity_wear_picture);

        wearPictureVideoView = (VideoView) findViewById(R.id.wear_picture_video_view);
        wearPictureListView = (ListView) findViewById(R.id.wear_picture_list_view);
    }
}
