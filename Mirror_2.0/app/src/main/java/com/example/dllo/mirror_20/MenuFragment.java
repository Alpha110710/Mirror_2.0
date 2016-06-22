package com.example.dllo.mirror_20;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dllo.mirror_20.base.BaseFragment;

/**
 * Created by dllo on 16/6/22.
 */
public class MenuFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout menuAllCategoriesLlayout;
    private TextView viewById;

    @Override
    public int setLayout() {
        return R.layout.fragment_menu;
    }

    @Override
    public void initView(View view) {
        menuAllCategoriesLlayout = (LinearLayout) view.findViewById(R.id.menu_all_categories_llayout);

    }

    @Override
    public void initData() {
        menuAllCategoriesLlayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_all_categories_llayout:
                ((MenuDetailOnClickListener) getActivity()).menuDetailOnClick(0);
                break;
        }
    }

    public interface MenuDetailOnClickListener {
        void menuDetailOnClick(int pos);
    }
}
