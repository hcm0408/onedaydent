package com.onedaydent.onedaydent.Adapter;

import android.app.Activity;

import com.onedaydent.onedaydent.Main.Tab.*;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

public class FragmentAdapter extends FragmentPagerAdapter {

    private Activity mActivity;

    private int tabCount;
    private MainTab1 tabFragment1;
    private MainTab2 tabFragment2;
    private MainTab3 tabFragment3;
    private String TAG = "TAG";

    public FragmentAdapter(FragmentManager fm, int tabCount, boolean isLogin, FragmentManager fragmentManager, FragmentTransaction transaction) {
        super(fm);
        tabFragment1 = new MainTab1(isLogin, fragmentManager, transaction);
        tabFragment2 = new MainTab2(isLogin, fragmentManager, transaction);
        tabFragment3 = new MainTab3(isLogin, fragmentManager, transaction);
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