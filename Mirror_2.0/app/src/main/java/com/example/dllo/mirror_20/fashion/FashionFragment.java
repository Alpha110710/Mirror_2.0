package com.example.dllo.mirror_20.fashion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.BaseFragment;
import com.example.dllo.mirror_20.projectshare.ProjectShareBean;

/**
 * Created by dllo on 16/6/23.
 */
public class FashionFragment extends BaseFragment {
    private TextView smallTitle,title,subTitle;


    public static Fragment createFragment(ProjectShareBean.DataBean.ListBean.StoryDataBean.TextArrayBean textArrayBeen){
        Fragment newFragment=new FashionFragment();
        Bundle bundle=new Bundle();
        bundle.putString("smallTitle", textArrayBeen.getSmallTitle());
        bundle.putString("title", textArrayBeen.getTitle());
        bundle.putString("subTitle", textArrayBeen.getSubTitle());
        newFragment.setArguments(bundle);
        return  newFragment;
    }
    @Override
    public int setLayout() {
        return R.layout.fragment_activity_fashion;
    }

    @Override
    public void initView(View view) {
        smallTitle= (TextView) view.findViewById(R.id.fragment_fashion_smallTitle);
        title= (TextView) view.findViewById(R.id.fragment_fashion_title);
        subTitle= (TextView) view.findViewById(R.id.fragment_fashion_subTitle);
    }

    @Override
    public void initData() {
        smallTitle.setText(getArguments().getString("smallTitle"));
        title.setText(getArguments().getString("title"));
        subTitle.setText(getArguments().getString("subTitle"));

    }
}
