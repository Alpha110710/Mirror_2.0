package com.example.dllo.mirror_20.shoppingcart;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dllo.mirror_20.Bean.DBBean;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.allcategories.AllCategoriesDetailActivity;
import com.example.dllo.mirror_20.allcategories.AllCategoriesFragment;
import com.example.dllo.mirror_20.allcategories.MyRvOnClickListener;
import com.example.dllo.mirror_20.base.BaseFragment;
import com.example.dllo.mirror_20.fashion.FashionActivity;
import com.example.dllo.mirror_20.sql.SQLTools;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/23.
 */
public class ShoppingCartFragment extends BaseFragment implements View.OnClickListener {
    private AutoRelativeLayout autoRelativeLayout;
    private ArrayList<DBBean> data;
    private DBBean dbBean;
    private ShoppingCartAdapter shoppingCartAdapter;
    private RecyclerView recyclerView;
    private RelativeLayout relativeLayoutPic;
    private ProgressBar progressBar;
    private SQLTools sqlTools;


    @Override
    public int setLayout() {
        return R.layout.fragment_shoppingcart;
    }

    @Override
    public void initView(View view) {
        autoRelativeLayout = (AutoRelativeLayout) view.findViewById(R.id.shopping_car_rl);
        recyclerView = (RecyclerView) view.findViewById(R.id.shopping_car_rv);
        relativeLayoutPic = (RelativeLayout) view.findViewById(R.id.shopping_car_img_relativeLayout);
        progressBar = (ProgressBar) view.findViewById(R.id.shopping_car_progress_bar);


    }

    @Override
    public void initData() {

        autoRelativeLayout.setOnClickListener(this);
        progressBar.setVisibility(View.VISIBLE);
        sqlTools = new SQLTools(context);
        data = new ArrayList<>();
        shoppingCartAdapter = new ShoppingCartAdapter(context);
        data = sqlTools.querAll();
        shoppingCartAdapter.setData(data);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(shoppingCartAdapter);

        progressBar.setVisibility(View.GONE);

        shoppingCartAdapter.setListener(new MyRvOnClickListener() {
            @Override
            public void onClick(int position) {
                deleteDialog();
                dbBean = new DBBean();
                dbBean.setGoods_name(data.get(position).getGoods_name());
            }
        });


    }


    public void deleteDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        //设置标题
        alert.setTitle("确定要删除?");
        //参数2 点击监听
        alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                sqlTools.deleteAll(dbBean);
                data = sqlTools.querAll();
                shoppingCartAdapter.setData(data);

            }
        });

        //取消按钮
        alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }


    //头布局的点击事件
    @Override
    public void onClick(View v) {

        ((AllCategoriesFragment.MenuOnClickListener) getActivity()).menuOnClick();
    }
}
