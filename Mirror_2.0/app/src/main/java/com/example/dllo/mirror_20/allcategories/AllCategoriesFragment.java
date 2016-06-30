package com.example.dllo.mirror_20.allcategories;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.example.dllo.mirror_20.Bean.DataAllBean;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.BaseFragment;
import com.example.dllo.mirror_20.fashion.FashionActivity;
import com.example.dllo.mirror_20.networktools.NetworkListener;
import com.example.dllo.mirror_20.networktools.NetworkTools;
import com.google.gson.Gson;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dllo on 16/6/21.
 */


public class AllCategoriesFragment extends BaseFragment implements MyRvOnClickListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private NetworkTools tools;
    private DataAllBean bean;
    private ProgressBar progressBar;
    private AllCategoriesRVAdapter adapter;
    private AutoLinearLayout autoLinearLayout;
    private String url = "http://lizhongren.com.cn/mengke/jsonhandle.php";


    @Override
    public int setLayout() {
        return R.layout.fragment_allcategories;
    }

    @Override
    public void initView(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.all_categories_progress_bar);
        autoLinearLayout = (AutoLinearLayout) view.findViewById(R.id.fragment_all_categories_autoll);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_all_categories_rv);
    }


    @Override
    public void initData() {


        progressBar.setVisibility(View.VISIBLE);

        tools = new NetworkTools();
        adapter = new AllCategoriesRVAdapter(context);

        tools.getNetworkData(url, new NetworkListener() {
            @Override
            public void onSuccessed(String result) {
                Gson gson = new Gson();
                bean = gson.fromJson(result,DataAllBean.class);

                adapter.setBean(bean);
                LinearLayoutManager manager = new LinearLayoutManager(context);
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailed(VolleyError error) {

            }
        });


        adapter.setListener(this);
        autoLinearLayout.setOnClickListener(this);

    }




    //RecyclerView的点击事件
    @Override
    public void onClick(int position) {
        if (bean.getData().getList().get(position).getType().equals("1") ){
            Intent intent = new Intent(context, AllCategoriesDetailActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        }else {
            Intent intent = new Intent(context, FashionActivity.class);
            intent.putExtra("pos", position);
            startActivity(intent);
        }


    }

    //fragment头标题的点击事件
    @Override
    public void onClick(View v) {

        ((MenuOnClickListener) getActivity()).menuOnClick();

    }

    public interface MenuOnClickListener {
        void menuOnClick();

    }
}
