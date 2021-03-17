package com.onedaydent.onedaydent.Main.Fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;
import com.onedaydent.onedaydent.Adapter.LocationAdapter;
import com.onedaydent.onedaydent.Adapter.TimetableAdapter;
import com.onedaydent.onedaydent.Common.Common;
import com.onedaydent.onedaydent.R;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

public class NLocationFragment extends BottomSheetDialogFragment {

    private ImageButton nlo_phone;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nlocation, container, false);
        nlo_phone = v.findViewById(R.id.nlo_phone);
        tabLayout = v.findViewById(R.id.nlo_tab);
        viewPager = v.findViewById(R.id.nlo_pager);
        nlo_phone.setOnClickListener(new Common.Click(getActivity()));

        LocationAdapter pagerAdapter = new LocationAdapter(getChildFragmentManager(), tabLayout.getTabCount() );
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#287DFA"));
        tabLayout.addOnTabSelectedListener(tabSelectedListener);

        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog d = (BottomSheetDialog) getDialog();
                FrameLayout bottomSheet = (FrameLayout) d.findViewById(R.id.design_bottom_sheet);
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) bottomSheet.getParent();
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                bottomSheetBehavior.setPeekHeight(2000);
                coordinatorLayout.getParent().requestLayout();
            }
        });
        return v;
    }

    TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };
}
