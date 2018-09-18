package com.xrd.testtablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/9/18.
 */

public class VpAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments=new ArrayList<>();
    public void getFragment(List<Fragment> list){
        fragments.clear();
        fragments.addAll(list);
        notifyDataSetChanged();

    }
    public VpAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
