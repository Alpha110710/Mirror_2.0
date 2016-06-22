package com.example.dllo.mirror_20.main;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.dllo.mirror_20.FirstFragment;
import com.example.dllo.mirror_20.R;
import com.example.dllo.mirror_20.SecondFragment;
import com.example.dllo.mirror_20.view.VerticalViewPager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private VerticalViewPager mainVerticalViewpager;
    private MainVerticalViewPagerAdapter mainVerticalViewPagerAdapter;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainVerticalViewpager = (VerticalViewPager)findViewById(R.id.main_vertical_viewpager);

        mainVerticalViewpager.internalCanScrollVertically(View.FOCUS_LEFT);
        mainVerticalViewPagerAdapter = new MainVerticalViewPagerAdapter(getSupportFragmentManager());
        fragments = new ArrayList<>();
        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        mainVerticalViewPagerAdapter.setFragments(fragments);

        mainVerticalViewpager.setAdapter(mainVerticalViewPagerAdapter);


    }
}
