package com.example.dllo.mirror_20.projectshare;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.allcategories.MyRvOnClickListener;
import com.example.dllo.mirror_20.base.BaseFragment;
import com.example.dllo.mirror_20.networktools.NetworkListener;
import com.example.dllo.mirror_20.networktools.NetworkTools;
import com.google.gson.Gson;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dllo on 16/6/22.
 */
public class ProjectShareFragment extends BaseFragment implements MyRvOnClickListener, View.OnClickListener {
    private ProjectShareBean bean;
    private ProgressBar bar;
    private RecyclerView recyclerView;
    private ProjectShareRvAdapter adapter;
    private NetworkTools tools;
    private AutoLinearLayout linearLayout;
    private Map<String,String> map;
    private String url = "http://api.mirroreye.cn/index.php/story/story_list";
    @Override
    public int setLayout() {
        return R.layout.fragment_projectshare;
    }

    @Override
    public void initView(View view) {
        linearLayout = (AutoLinearLayout) view.findViewById(R.id.project_share_linear_layout);
        bar = (ProgressBar) view.findViewById(R.id.project_share_progress_bar);
        recyclerView = (RecyclerView) view.findViewById(R.id.project_share_rv);

    }

    @Override
    public void initData() {
        bar.setVisibility(View.VISIBLE);
        initMap();
        adapter = new ProjectShareRvAdapter(context);
        tools = new NetworkTools();
        tools.getNetworkPostData(url, map, new NetworkListener() {
            @Override
            public void onSuccessed(String result) {
                Gson gson = new Gson();
                bean = gson.fromJson(result,ProjectShareBean.class);
                LinearLayoutManager manager = new LinearLayoutManager(context);
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(manager);
                adapter.setBean(bean);
                recyclerView.setAdapter(adapter);
                bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailed(VolleyError error) {

            }
        });
        adapter.setListener(this);
        linearLayout.setOnClickListener(this);

    }

    private void initMap() {
        map = new HashMap<>();
        map.put("device_type","1");
    }


    //RecyclerView的点击事件
    @Override
    public void onClick(int position) {
        Toast.makeText(context, "position:" + position, Toast.LENGTH_SHORT).show();

    }

    //头布局的点击事件
    @Override
    public void onClick(View v) {
        Toast.makeText(context, "别点了，还没加效果呢", Toast.LENGTH_SHORT).show();
    }
}
