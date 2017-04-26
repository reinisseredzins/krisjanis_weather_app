package com.example.pc.weatherapplication;





import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;



public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> newFragmentList) {
        this(fm);
        fragmentList = newFragmentList;
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {

        } else if (position == 1) {

        }
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
