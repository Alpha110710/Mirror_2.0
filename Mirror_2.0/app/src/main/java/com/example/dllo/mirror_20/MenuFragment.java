package com.example.dllo.mirror_20;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dllo.mirror_20.base.BaseFragment;

/**
 * Created by dllo on 16/6/22.
 */
public class MenuFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout menuAllCategoriesLlayout, menuAllSunLlayout, menuAllBackHomeLlayout, menuAllBuyCarLlayout, menuAllGogglesLlayout, menuAllBackLlayout, menuAllTopicLlayout;
    private ImageView menuAllBackImg, menuAllCategoriesImg, menuAllGogglesImg, menuAllSunImg, menuAllTopicImg, menuAllBackHomeImg, menuAllBuyCarImg;
    private TextView menuAllBackTv, menuAllCategoriesTv, menuAllGogglesTv, menuAllSunTv, menuAllTopicTv, menuAllBackHomeTv, menuAllBuyCarTv;
    private int pos;


    @Override
    public int setLayout() {
        return R.layout.fragment_menu;
    }

    @Override
    public void initView(View view) {
        menuAllCategoriesLlayout = (LinearLayout) view.findViewById(R.id.menu_all_categories_llayout);
        menuAllGogglesLlayout = (LinearLayout) view.findViewById(R.id.menu_all_goggles_llayout);
        menuAllBackLlayout = (LinearLayout) view.findViewById(R.id.menu_all_back_llayout);
        menuAllSunLlayout = (LinearLayout)view.findViewById(R.id.menu_all_sun_llayout);
        menuAllTopicLlayout = (LinearLayout) view.findViewById(R.id.menu_all_topic_llayout);
        menuAllBuyCarLlayout = (LinearLayout) view.findViewById(R.id.menu_all_buy_car_llayout);
        menuAllBackHomeLlayout = (LinearLayout)view.findViewById(R.id.menu_all_back_home_llayout);

        menuAllBackImg = (ImageView) view.findViewById(R.id.menu_all_back_img);
        menuAllCategoriesImg = (ImageView) view.findViewById(R.id.menu_all_categories_img);
        menuAllGogglesImg = (ImageView) view.findViewById(R.id.menu_all_goggles_img);
        menuAllSunImg = (ImageView) view.findViewById(R.id.menu_all_sun_img);
        menuAllTopicImg = (ImageView) view.findViewById(R.id.menu_all_topic_img);
        menuAllBackHomeImg = (ImageView) view.findViewById(R.id.menu_all_back_home_img);
        menuAllBuyCarImg = (ImageView) view.findViewById(R.id.menu_all_buy_car_img);

        menuAllBackTv = (TextView) view.findViewById(R.id.menu_all_back_tv);
        menuAllCategoriesTv = (TextView) view.findViewById(R.id.menu_all_categories_tv);
        menuAllGogglesTv = (TextView) view.findViewById(R.id.menu_all_goggles_tv);
        menuAllSunTv = (TextView) view.findViewById(R.id.menu_all_sun_tv);
        menuAllTopicTv = (TextView) view.findViewById(R.id.menu_all_topic_tv);
        menuAllBackHomeTv = (TextView) view.findViewById(R.id.menu_all_back_home_tv);
        menuAllBuyCarTv = (TextView) view.findViewById(R.id.menu_all_buy_car_tv);


    }

    @Override
    public void initData() {
        menuAllCategoriesLlayout.setOnClickListener(this);
        menuAllGogglesLlayout.setOnClickListener(this);
        menuAllSunLlayout.setOnClickListener(this);

        menuAllCategoriesImg.setVisibility(View.INVISIBLE);
        menuAllSunImg.setVisibility(View.INVISIBLE);
        menuAllGogglesImg.setVisibility(View.INVISIBLE);
        menuAllTopicImg.setVisibility(View.INVISIBLE);
        menuAllBuyCarImg.setVisibility(View.INVISIBLE);

        menuAllCategoriesTv.setTextColor(0x88FFFFFF);
        menuAllGogglesTv.setTextColor(0x88FFFFFF);
        menuAllSunTv.setTextColor(0x88FFFFFF);
        menuAllTopicTv.setTextColor(0x88FFFFFF);
        menuAllBuyCarTv.setTextColor(0x88FFFFFF);



        pos = getArguments().getInt("pos");
        switch (pos){
            case 0:
                menuAllCategoriesImg.setVisibility(View.VISIBLE);
                menuAllCategoriesTv.setTextColor(0xFFFFFFFF);
                break;
            case 1:
                menuAllGogglesImg.setVisibility(View.VISIBLE);
                menuAllGogglesTv.setTextColor(0xFFFFFFFF);
                break;
            case 2:
                menuAllSunImg.setVisibility(View.VISIBLE);
                menuAllSunTv.setTextColor(0xFFFFFFFF);
                break;
            //TODO:case3...
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_all_categories_llayout:
                ((MenuDetailOnClickListener) getActivity()).menuDetailOnClick(0);
                break;
            case R.id.menu_all_goggles_llayout:
                ((MenuDetailOnClickListener) getActivity()).menuDetailOnClick(1);
                break;
            case R.id.menu_all_sun_llayout:
                ((MenuDetailOnClickListener) getActivity()).menuDetailOnClick(2);
                break;
        }
    }

    public interface MenuDetailOnClickListener {
        void menuDetailOnClick(int pos);
    }
}
