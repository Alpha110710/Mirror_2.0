package com.example.dllo.mirror_20.fashion;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dllo.mirror_20.Bean.DataAllBean;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.BaseFragment;
import com.example.dllo.mirror_20.projectshare.ProjectShareBean;

/**
 * Created by dllo on 16/6/23.
 * FlatLightFragment
 * SunglassesFragment
 */
public class FashionFragment extends BaseFragment {
    private TextView smallTitle,title,subTitle,noBoxSmallTitle,noBoxTitle,noBoxNubTitle,noBoxVerticalTitle,noBoxColorTitle
            ,threeSmallTitle,threeTitle,threeSubTitle;
    private LinearLayout haveBox,three;
    private RelativeLayout notBox;

    public static Fragment createFragment(ProjectShareBean.DataBean.ListBean.StoryDataBean.TextArrayBean textArrayBeen){
        Fragment newFragment=new FashionFragment();
        Bundle bundle=new Bundle();
        //给自己传的
        bundle.putString("smallTitle", textArrayBeen.getSmallTitle());
        bundle.putString("title", textArrayBeen.getTitle());
        bundle.putString("titleColor",textArrayBeen.getTitleColor());
        bundle.putString("subTitle", textArrayBeen.getSubTitle());
        bundle.putString("verticalTitle",textArrayBeen.getVerticalTitle());
        bundle.putString("verticalTitleColor",textArrayBeen.getVerticalTitleColor());
        bundle.putString("colorTitle",textArrayBeen.getColorTitle());
        bundle.putString("colorTitleColor",textArrayBeen.getColorTitleColor());
        bundle.putString("category",textArrayBeen.getCategory());
        newFragment.setArguments(bundle);
        return  newFragment;
    }

    public static Fragment createFragment(DataAllBean.DataBean.ListBean.DataInfoBean.StoryDataBean.TextArrayBean textArrayBeen){
        Fragment newFragment=new FashionFragment();
        Bundle bundle=new Bundle();
        //给自己传的
        bundle.putString("smallTitle", textArrayBeen.getSmallTitle());
        bundle.putString("title", textArrayBeen.getTitle());
        bundle.putString("titleColor",textArrayBeen.getTitleColor());
        bundle.putString("subTitle", textArrayBeen.getSubTitle());
        bundle.putString("verticalTitle",textArrayBeen.getVerticalTitle());
        bundle.putString("verticalTitleColor",textArrayBeen.getVerticalTitleColor());
        bundle.putString("colorTitle",textArrayBeen.getColorTitle());
        bundle.putString("colorTitleColor",textArrayBeen.getColorTitleColor());
        bundle.putString("category",textArrayBeen.getCategory());
        newFragment.setArguments(bundle);
        return  newFragment;
    }
    @Override
    public int setLayout() {
        return R.layout.fragment_activity_fashion;
    }

    @Override
    public void initView(View view) {
        threeSmallTitle= (TextView) view.findViewById(R.id.fragment_fashion_three_smallTitle);
        threeTitle= (TextView) view.findViewById(R.id.fragment_fashion_three_title);
        threeSubTitle= (TextView) view.findViewById(R.id.fragment_fashion_three_subTitle);
        three= (LinearLayout) view.findViewById(R.id.three_style_linearlayout);

        noBoxSmallTitle= (TextView) view.findViewById(R.id.fragment_fashion_nobox_smallTitle);
        noBoxTitle= (TextView) view.findViewById(R.id.fragment_fashion_nobox_title);
        noBoxNubTitle= (TextView) view.findViewById(R.id.fragment_fashion_nobox_subTitle);
        noBoxVerticalTitle= (TextView) view.findViewById(R.id.fragment_fashion_nobox_verticalTitle);
        noBoxColorTitle= (TextView) view.findViewById(R.id.fragment_fashion_nobox_colorTitle);
        notBox= (RelativeLayout) view.findViewById(R.id.notBox_style_relativelayout);

        smallTitle= (TextView) view.findViewById(R.id.fragment_fashion_smallTitle);
        title= (TextView) view.findViewById(R.id.fragment_fashion_title);
        subTitle= (TextView) view.findViewById(R.id.fragment_fashion_subTitle);
        haveBox= (LinearLayout) view.findViewById(R.id.box_style_linearlayout);

    }

    @Override
    public void initData() {

        if (getArguments().getString("category").equals("styleTwo")){

            //出现最多的那种布局
            haveBox.setVisibility(View.VISIBLE);
            smallTitle.setText(getArguments().getString("smallTitle"));
            title.setText(getArguments().getString("title"));
            //判断颜色的那个字符串是不是为空  并且长度小于6
            if (!getArguments().getString("titleColor").equals("")&&getArguments().get("titleColor").toString().length()<=5){//还是那个问题  服务器返回的数据不能用
                title.setTextColor(Color.parseColor("#"+getArguments().getString("titleColor")));
            }
            subTitle.setText(getArguments().getString("subTitle"));
        }else if (getArguments().getString("category").equals("styleOne")||getArguments().getString("category").equals("styleFive")){
            //一种特殊的布局  没有边框
            notBox.setVisibility(View.VISIBLE);
            noBoxColorTitle.setText(getArguments().getString("colorTitle"));
            //set颜色  一开始运行程序有时候蹦 原因是服务器返回的颜色 有的为"" 所以加一个判断
            if (!getArguments().getString("colorTitleColor").equals("")){
            noBoxColorTitle.setTextColor(Color.parseColor("#"+getArguments().getString("colorTitleColor")));
            }
            noBoxVerticalTitle.setText(getArguments().getString("verticalTitle"));
            if (!getArguments().getString("verticalTitleColor").equals("")) {
                noBoxVerticalTitle.setTextColor(Color.parseColor("#" + getArguments().getString("verticalTitleColor")));
            }
            noBoxSmallTitle.setText(getArguments().getString("smallTitle"));
            noBoxTitle.setText(getArguments().getString("title"));
            noBoxNubTitle.setText(getArguments().getString("subTitle"));
            //判断颜色的那个字符串是不是为空  并且长度小于6
            if (!getArguments().getString("titleColor").equals("")&&getArguments().getString("titleColor").toString().length()<=5){//这里返回的有一个颜色的值是9位 所以在这里判断
            noBoxTitle.setTextColor(Color.parseColor("#"+getArguments().getString("titleColor")));
            }
        }else if (getArguments().getString("category").equals("styleThree")) {
            three.setVisibility(View.VISIBLE);
            threeSmallTitle.setText(getArguments().getString("smallTitle"));
            threeTitle.setText(getArguments().getString("title"));
            if (!getArguments().getString("titleColor").equals("")){
            threeTitle.setTextColor(Color.parseColor("#"+getArguments().getString("titleColor")));
            }
            threeSubTitle.setText(getArguments().getString("subTitle"));
        }
    }
}
