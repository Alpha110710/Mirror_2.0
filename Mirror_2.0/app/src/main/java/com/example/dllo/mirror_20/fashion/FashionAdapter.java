package com.example.dllo.mirror_20.fashion;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by dllo on 16/6/23.
 */
public class FashionAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments;

    public FashionAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(ArrayList<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments != null && fragments.size() > 0 ? fragments.get(position) : null;
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }
}
