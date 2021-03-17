package com.onedaydent.onedaydent.Adapter;

import com.onedaydent.onedaydent.Main.Tab.*;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LocationAdapter extends FragmentPagerAdapter {

    private int tabCount;
    private NLocationTab1 tabFragment1;
    private NLocationTab2 tabFragment2;
    private NLocationTab3 tabFragment3;

    public LocationAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        tabFragment1 = new NLocationTab1();
        tabFragment2 = new NLocationTab2();
        tabFragment3 = new NLocationTab3();
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return tabFragment1;
            case 1:
                return tabFragment2;
            case 2:
                return tabFragment3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.tabCount;
    }
}