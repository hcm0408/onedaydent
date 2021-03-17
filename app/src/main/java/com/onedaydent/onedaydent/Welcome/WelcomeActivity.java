package com.onedaydent.onedaydent.Welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.widget.*;

import com.onedaydent.onedaydent.Adapter.Adapter;
import com.onedaydent.onedaydent.Common.BackPressClose;
import com.onedaydent.onedaydent.Common.ChangeStatusBarColor;
import com.onedaydent.onedaydent.Common.Common;
import com.onedaydent.onedaydent.R;

public class WelcomeActivity extends AppCompatActivity implements WelcomeContract.View {

    private WelcomeContract.Presenter mPresenter;

    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private BackPressClose bpc;
    private static WelcomeActivity activity;

    private String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new WelcomePresenter(this, this, getSupportFragmentManager());
        new ChangeStatusBarColor(this, Color.argb(1,40,125,250));
        this.activity = this;
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver,
                new IntentFilter("saveToken"));
        mPresenter.start();
        bpc = new BackPressClose(this);
    }

    @Override
    public void onBackPressed() {
        bpc.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 토큰 Shared 저장
            String token = intent.getStringExtra("token");
            Common.saveToken(token, activity);
        }
    };

    @Override
    public void viewInit() {
        this.viewPager = findViewById(R.id.welcome_view_pager);
        this.dotsLayout = findViewById(R.id.welcomeDots);
        addBottomDots(0);
    }

    @Override
    public void setAdapter() {
        int[] layouts = new int[]{
            R.layout.welcome1,
            R.layout.welcome2,
            R.layout.welcome3
        };

        Adapter adapter = new Adapter(layouts, this);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.stopBroadCast();
    }

    @Override
    public void addBottomDots(int currentPage) {
        TextView[] dots = new TextView[3];
        dotsLayout.removeAllViews();
        for(int i = 0;i < 3;i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(45);
            dots[i].setTextColor(getResources().getColor(R.color.gray));
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT);
            llp.setMargins(15, 0, 15, 0);
            dots[i].setLayoutParams(llp);
            dotsLayout.addView(dots[i]);
        }

        if(dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void setPresenter(WelcomeContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
