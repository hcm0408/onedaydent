package com.onedaydent.onedaydent.Adapter;

import com.onedaydent.onedaydent.Main.Tab.*;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TimetableAdapter extends FragmentPagerAdapter {

    private int tabCount;
    private NTimeTab1 tabFragment1;
    private NTimeTab2 tabFragment2;
    private NTimeTab3 tabFragment3;

    public TimetableAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        tabFragment1 = new NTimeTab1();
        tabFragment2 = new NTimeTab2();
        tabFragment3 = new NTimeTab3();
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