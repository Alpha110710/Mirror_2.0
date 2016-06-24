package com.example.dllo.mirror_20.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.mirror_20.Bean.EventBusBean;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by dllo on 16/6/22.
 */
public class MenuFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout menuAllCategoriesLlayout, menuAllSunLlayout, menuAllBackHomeLlayout, menuAllBuyCarLlayout, menuAllGogglesLlayout, menuAllBackLlayout, menuAllTopicLlayout;
    private ImageView menuAllBackImg, menuAllCategoriesImg, menuAllGogglesImg, menuAllSunImg, menuAllTopicImg, menuAllBackHomeImg, menuAllBuyCarImg;
    private TextView menuAllBackTv, menuAllCategoriesTv, menuAllGogglesTv, menuAllSunTv, menuAllTopicTv, menuAllBackHomeTv, menuAllBuyCarTv;
    private int pos;
    private LinearLayout menuAllBackgroundLlayout;
    private EventBus eventBus;


    @Override
    public int setLayout() {
        return R.layout.fragment_menu;
    }

    @Override
    public void initView(View view) {
        menuAllBackgroundLlayout = (LinearLayout)view.findViewById(R.id.menu_all_background_llayout);
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
        eventBus=EventBus.getDefault();
        menuAllCategoriesLlayout.setOnClickListener(this);
        menuAllGogglesLlayout.setOnClickListener(this);
        menuAllSunLlayout.setOnClickListener(this);
        menuAllBackHomeLlayout.setOnClickListener(this);
        menuAllTopicLlayout.setOnClickListener(this);
        menuAllBuyCarLlayout.setOnClickListener(this);
        menuAllBackLlayout.setOnClickListener(this);
        menuAllBackgroundLlayout.setOnClickListener(this);

        //设置进入动画
        setAmination();

        //设置进入时 下面线和字体变白
        setSelected();


    }

    //设置字体颜色和白线
    private void setSelected() {

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
            case 3:
                menuAllTopicImg.setVisibility(View.VISIBLE);
                menuAllTopicTv.setTextColor(0xFFFFFFFF);
                break;
            case 4:
                menuAllBuyCarImg.setVisibility(View.VISIBLE);
                menuAllBuyCarTv.setTextColor(0xFFFFFFFF);
                break;


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
            case R.id.menu_all_topic_llayout:
                ((MenuDetailOnClickListener) getActivity()).menuDetailOnClick(3);
                break;
            case R.id.menu_all_buy_car_llayout:
                ((MenuDetailOnClickListener) getActivity()).menuDetailOnClick(4);
                break;
            case R.id.menu_all_back_home_llayout:
                ((MenuDetailOnClickListener) getActivity()).menuDetailOnClick(0);
                break;
            case R.id.menu_all_back_llayout:
                //要想取出数据的话，同样需要操作SharedPreference的对象
                SharedPreferences getSp = context.getSharedPreferences("test", Context.MODE_PRIVATE);
                Boolean a=getSp.getBoolean("result",false);
                if (a==false){
                    Toast.makeText(context, "大哥 你没登录,别点了", Toast.LENGTH_SHORT).show();
                }else {
                    showSlertDialog();
                //Log.d("MenuFragment", "a:********" + a);
                }

                break;

            //外围最大背景监听
            case R.id.menu_all_background_llayout:
                ((MenuDetailOnClickListener) getActivity()).menuDetailOnClick(pos);
                break;
        }
    }

    //设置动画
    private void setAmination() {
        AnimationSet localAnimationSet = new AnimationSet(true);
        ScaleAnimation localScaleAnimation = new ScaleAnimation(
                1.10000002384185791016F, 1F, 1.10000002384185791016F, 1F, 1, 0.5F, 1, 0.5F);
        localScaleAnimation.setDuration(250L);
        localAnimationSet.addAnimation(localScaleAnimation);
        menuAllBackgroundLlayout.startAnimation(localAnimationSet);
    }


    public interface MenuDetailOnClickListener {
        void menuDetailOnClick(int pos);
    }
    public  void showSlertDialog(){
        //显示一个alertDialog需要使用AlertDialog.Builder
        //builder对象可以对哦dialog进行各种参数的设置
        AlertDialog.Builder alert=new AlertDialog.Builder(context);
        //设置图标
        alert.setIcon(R.mipmap.mirror_logo);
        //设置标题
        alert.setTitle("不要离开我!亲爱的n(*≧▽≦*)n");
        //设置要注意的东西
       // alert.setMessage("离开我 亲将失去很多特权哦");
       // Log.d("MenuFragment", "aaaaaa");
        //点击监听 确定
        alert.setPositiveButton("忍痛离开", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sp = context.getSharedPreferences("test",Context.MODE_PRIVATE);
                //向硬盘中存储需要获得editor对象
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();//清空方法（调用后也需要在提交才会清空）
                editor.commit();
                EventBusBean bean=new EventBusBean("登录");
                eventBus.post(bean);
            }
        });
        alert.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "谢谢亲没有离开我,我将更加珍惜亲", Toast.LENGTH_SHORT).show();
            }
        });
        //调用builder.show方法 就可以吧alertDialog调用
        alert.show();
    }
}
