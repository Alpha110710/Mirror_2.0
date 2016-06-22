package com.example.dllo.mirror_20.allcategories;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
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

/**
 * Created by dllo on 16/6/21.
 */
public class AllCategoriesDetailActivity extends BaseActivity {

    private String url = "http://lizhongren.com.cn/mengke/jsonhandle.php";
    private NetworkTools networkTools = new NetworkTools();
    private Gson gson;
    private DataAllBean dataAllBean;
    private NetworkListener networkListener = new NetworkListener() {
        @Override
        public void onSuccessed(String result) {
            gson = new Gson();
            dataAllBean = gson.fromJson(result, DataAllBean.class);

            if (dataAllBean.getData().getList().get(0).getType().equals("1")) {

                Picasso.with(AllCategoriesDetailActivity.this).load(dataAllBean.getData().getList().get(0).getData_info().getGoods_img())
                        .fit().into(allCategoriesDetailBackImg);
//                networkTools.getNetworkImage(dataAllBean.getData().getList().get(0).getData_info().getGoods_img(), allCategoriesDetailBackImg);
            }
        }

        @Override
        public void onFailed(VolleyError error) {

        }
    };

    private ImageView allCategoriesDetailBackImg;
    private ListView allCategoriesDetailOutListView, allCategoriesDetailInListView;
    private DetailActivityOutListViewAdapter detailActivityOutListViewAdapter;
    private RelativeLayout relativeheaderTranslucentBackRlayoutLayout;
    private AccelerateDecelerateInterpolator mSmoothInterpolator;

    @Override
    public void initActivity() {
        setContentView(R.layout.activity_all_categories_detail);

        allCategoriesDetailBackImg = (ImageView) findViewById(R.id.all_categories_detail_back_img);
        allCategoriesDetailOutListView = (ListView) findViewById(R.id.all_categories_detail_out_list_view);
        allCategoriesDetailInListView = (ListView)findViewById(R.id.all_categories_detail_in_list_view);


        detailActivityOutListViewAdapter = new DetailActivityOutListViewAdapter(this);
        allCategoriesDetailOutListView.setAdapter(detailActivityOutListViewAdapter);

        View view = LayoutInflater.from(this).inflate(R.layout.listview_header_translucent, null);
        relativeheaderTranslucentBackRlayoutLayout = (RelativeLayout) view.findViewById(R.id.header_translucent_back_rlayout);

        allCategoriesDetailOutListView.addHeaderView(view);
        allCategoriesDetailInListView.addHeaderView(view);


        initData();

        detailActivityOutListViewAdapter.setStrs(strings);

        networkTools.getNetworkData(url, networkListener);

//        allCategoriesDetailOutListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//                int scrollY = getScrollY();
//                float alpha = clamp(relativeheaderTranslucentBackRlayoutLayout.getTranslationY() / mMinHeaderTranslation, 0.0f, 1.0f);
//                float actual = clamp(1.0f - mSmoothInterpolator.getInterpolation(alpha), 0.0f, 1.0f);
//                relativeheaderTranslucentBackRlayoutLayout.setAlpha(1.0f - actual);//设置图片的透明度,从0-->1
//
//
//            }
//        });
//
//        //程序开始运行时,获取滚动的最小值
//        relativeheaderTranslucentBackRlayoutLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                /*获取滚动的最小值*/
//                mMinHeaderTranslation = -mMainheaderViewLayout.getMeasuredHeight() + mActionBarSize;
//            }
//        });
//

    }

//    float pos = 0f;

    ArrayList<String> strings = new ArrayList<>();

    //假数据
    private void initData() {

        for (int i = 0; i < 50; i++) {
            strings.add("aaa" + i);
        }
    }

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


}
