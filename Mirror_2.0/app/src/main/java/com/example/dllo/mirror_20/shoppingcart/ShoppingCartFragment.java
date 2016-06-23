package com.example.dllo.mirror_20.shoppingcart;

import android.view.View;
import android.widget.Toast;

import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.base.BaseFragment;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * Created by dllo on 16/6/23.
 */
public class ShoppingCartFragment extends BaseFragment implements View.OnClickListener {
    private AutoRelativeLayout autoRelativeLayout;
    @Override
    public int setLayout() {
        return R.layout.fragment_shoppingcart;
    }

    @Override
    public void initView(View view) {
        autoRelativeLayout = (AutoRelativeLayout) view.findViewById(R.id.shopping_car_rl);

    }

    @Override
    public void initData() {
       autoRelativeLayout.setOnClickListener(this);
    }



    //头布局的点击事件
    @Override
    public void onClick(View v) {
        Toast.makeText(context, "别点了，还没加效果呢", Toast.LENGTH_SHORT).show();
    }
}
