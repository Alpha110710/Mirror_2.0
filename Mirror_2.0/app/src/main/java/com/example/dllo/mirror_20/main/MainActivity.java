package com.example.dllo.mirror_20.main;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.dllo.mirror_20.MenuFragment;
import com.example.dllo.mirror_20.R;

import com.example.dllo.mirror_20.allcategories.SecondFragment;
import com.example.dllo.mirror_20.allcategories.AllCategoriesFragment;
import com.example.dllo.mirror_20.view.VerticalViewPager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AllCategoriesFragment.MenuOnClickListener, MenuFragment.MenuDetailOnClickListener {
    private VerticalViewPager mainVerticalViewpager;
    private MainVerticalViewPagerAdapter mainVerticalViewPagerAdapter;
    private ArrayList<Fragment> fragments;
    private FrameLayout mainFrameLayout;
    private MenuFragment menuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainVerticalViewpager = (VerticalViewPager) findViewById(R.id.main_vertical_viewpager);
        mainFrameLayout = (FrameLayout) findViewById(R.id.main_frame_layout);

        menuFragment = new MenuFragment();

        mainVerticalViewpager.internalCanScrollVertically(View.FOCUS_LEFT);
        mainVerticalViewPagerAdapter = new MainVerticalViewPagerAdapter(getSupportFragmentManager());
        fragments = new ArrayList<>();
        fragments.add(new AllCategoriesFragment());
        fragments.add(new SecondFragment());
        mainVerticalViewPagerAdapter.setFragments(fragments);

        mainVerticalViewpager.setAdapter(mainVerticalViewPagerAdapter);


    }


    @Override
    public void menuOnClick() {

        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, menuFragment).commit();
        mainVerticalViewpager.setVisibility(View.GONE);
        int pos = mainVerticalViewpager.getCurrentItem();
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        menuFragment.setArguments(bundle);

    }

    @Override
    public void menuDetailOnClick(int pos) {
        mainVerticalViewpager.setVisibility(View.VISIBLE);
        switch (pos) {
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new AllCategoriesFragment())
                        .commit();
                break;
        }
    }
}
