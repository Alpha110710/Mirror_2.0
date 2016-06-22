package com.example.dllo.mirror_20.allcategories;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.VolleyError;
import com.example.dllo.mirror_20.Bean.DataAllBean;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.BaseFragment;
import com.example.dllo.mirror_20.main.MainActivity;
import com.example.dllo.mirror_20.networktools.NetworkListener;
import com.example.dllo.mirror_20.networktools.NetworkTools;
import com.google.gson.Gson;
import com.zhy.autolayout.AutoLinearLayout;

/**
 * Created by dllo on 16/6/21.
 */
public class AllCategoriesFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private NetworkTools tools;
    private DataAllBean bean;
    private AllCategoriesRVAdapter adapter;
    private String url = "http://lizhongren.com.cn/mengke/jsonhandle.php";
    private AutoLinearLayout fragmentAllCategoriesLlayout;
    private MainActivity.MenuOnClickListener menuOnClickListener;

    public void setMenuOnClickListener(MainActivity.MenuOnClickListener menuOnClickListener) {
        this.menuOnClickListener = menuOnClickListener;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_allcategories;
    }

    @Override
    public void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_all_categories_rv);
        fragmentAllCategoriesLlayout = (AutoLinearLayout) view.findViewById(R.id.fragment_all_categories_llayout);
    }



    @Override
    public void initData() {

        fragmentAllCategoriesLlayout.setOnClickListener(this);


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
                recyclerView.addItemDecoration(new SpacesItemDecoration(22));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailed(VolleyError error) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_all_categories_llayout:
                (MainActivity.MenuOnClickListener)getActivity().
                break;
        }
    }
}
