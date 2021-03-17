package com.onedaydent.onedaydent.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.onedaydent.onedaydent.Adapter.FragmentAdapter;
import com.onedaydent.onedaydent.Common.BackPressClose;
import com.onedaydent.onedaydent.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements MainContract.View, View.OnClickListener{

    private MainContract.Presenter mPresenter;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private TabLayout tabLayout;
    private String TAG = "TAG";

    private ViewPager viewPager;

    private ImageButton btn_hamburger;
    private ImageButton btn_bell;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private BackPressClose bpc;

    private LinearLayout btn_logout;
    private LinearLayout btn_setting;
    private LinearLayout btn_navi_call;
    private LinearLayout btn_navi_kakao;
    private LinearLayout btn_navi_faq;
    private LinearLayout btn_navi_youtube;
    private LinearLayout btn_navi_naver;
    private LinearLayout btn_navi_insta;
    private LinearLayout btn_navi_facebook;
    private TextView txt_navi_name;
    private TextView btn_navi_timetable;
    private TextView btn_navi_location;
    private TextView btn_navi_docinfo;
    private TextView btn_navi_notice;
    private TextView btn_navi_paranomic;
    private TextView btn_navi_toothpaste;
    private TextView txt_badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bpc = new BackPressClose(this);
        new MainPresenter(this, this, getSupportFragmentManager());
        mPresenter.start();
        SharedPreferences pref = getSharedPreferences("token", MODE_PRIVATE);
        Log.d(TAG, "onCreate: " + pref.getString("token", ""));
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver,
                new IntentFilter("badgeCount"));
        getHashKey();
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 토큰 Shared 저장
            int badge = intent.getIntExtra("badge", 0);
            showBadge(View.VISIBLE);
            setBadge(badge);
        }
    };

    @Override
    public void onBackPressed() {
        if(mOnKeyBackPressedListener != null){
            mOnKeyBackPressedListener.onBack();
        }else{
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                mDrawerLayout.closeDrawer(GravityCompat.END);
            } else {
                bpc.onBackPressed();
            }
        }
    }

    @Override
    public void closeDrawerLayout() {
        mDrawerLayout.closeDrawer(GravityCompat.END);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_hamburger: // 왼쪽 상단 버튼 눌렀을 때
                mDrawerLayout.openDrawer(GravityCompat.END);
                break;
            case R.id.btn_bell: // 왼쪽 상단 버튼 눌렀을 때
                mPresenter.openFcm();
                break;
            case R.id.logout: // 로그아웃
                mPresenter.logout();
                break;
            case R.id.setting: // 설정창
                mPresenter.openSetting();
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                }
                break;
            case R.id.btn_navi_call: // 전화문의
                mPresenter.phoneCall();
                break;
            case R.id.btn_navi_kakao: // 카톡문의
                mPresenter.openKakao();
                break;
//            case R.id.btn_navi_faq: // FAQ activity
//                mPresenter.openFaq();
//                break;
            case R.id.btn_navi_timetable: // 진료시간
                mPresenter.openTimetable();
                break;
            case R.id.btn_navi_location: // 오시는길
                mPresenter.openLocation();
                break;
            case R.id.btn_navi_docinfo: // 의료진정보
                mPresenter.openDocInfo();
                break;
            case R.id.btn_navi_notice: // 공지
                mPresenter.openNotice();
                break;
            case R.id.btn_navi_paranomic:
                mPresenter.openPanorama();
                break;
            case R.id.btn_navi_toothpaste: // 치약구매
                mPresenter.openToothpaste();
                break;
            case R.id.btn_navi_youtube: // 유튜브
                mPresenter.openYoutube();
                break;
            case R.id.btn_navi_naver: // 네이버
                mPresenter.openNaver();
                break;
            case R.id.btn_navi_insta: // 인스타
                mPresenter.openInsta();
                break;
            case R.id.btn_navi_facebook: // 페이스북
                mPresenter.openFacebook();
                break;
        }
    }

    @Override
    public void showBadge(int bool) {
        this.txt_badge.setVisibility(bool);
    }

    @Override
    public void setBadge(int count) {
        this.txt_badge.setText(count + "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void viewInit() {
        this.tabLayout = findViewById(R.id.tab_main);
        this.viewPager = findViewById(R.id.main_view_pager);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.btn_hamburger = (ImageButton) findViewById(R.id.btn_hamburger);
        this.btn_bell = (ImageButton) findViewById(R.id.btn_bell);
        this.txt_badge = (TextView) findViewById(R.id.txt_badged);
        this.navigationView = (NavigationView) findViewById(R.id.nav_view);
        this.fragmentManager = getSupportFragmentManager();
        this.transaction = fragmentManager.beginTransaction();

        this.btn_logout = navigationView.findViewById(R.id.logout);
        this.btn_setting = navigationView.findViewById(R.id.setting);
        this.btn_navi_call = navigationView.findViewById(R.id.btn_navi_call);
        this.btn_navi_kakao = navigationView.findViewById(R.id.btn_navi_kakao);
//        this.btn_navi_faq = navigationView.findViewById(R.id.btn_navi_faq);
        this.btn_navi_youtube = navigationView.findViewById(R.id.btn_navi_youtube);
        this.btn_navi_naver = navigationView.findViewById(R.id.btn_navi_naver);
        this.btn_navi_insta = navigationView.findViewById(R.id.btn_navi_insta);
        this.btn_navi_facebook = navigationView.findViewById(R.id.btn_navi_facebook);
        this.txt_navi_name = navigationView.findViewById(R.id.txt_navi_name);
        this.btn_navi_timetable = navigationView.findViewById(R.id.btn_navi_timetable);
        this.btn_navi_location = navigationView.findViewById(R.id.btn_navi_location);
        this.btn_navi_docinfo = navigationView.findViewById(R.id.btn_navi_docinfo);
        this.btn_navi_notice = navigationView.findViewById(R.id.btn_navi_notice);
        this.btn_navi_paranomic = navigationView.findViewById(R.id.btn_navi_paranomic);
        this.btn_navi_toothpaste = navigationView.findViewById(R.id.btn_navi_toothpaste);
        btn_bell.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
        btn_setting.setOnClickListener(this);
        btn_navi_call.setOnClickListener(this);
        btn_navi_kakao.setOnClickListener(this);
//        btn_navi_faq.setOnClickListener(this);
        btn_navi_youtube.setOnClickListener(this);
        btn_navi_naver.setOnClickListener(this);
        btn_navi_insta.setOnClickListener(this);
        btn_navi_facebook.setOnClickListener(this);
        txt_navi_name.setOnClickListener(this);
        btn_navi_timetable.setOnClickListener(this);
        btn_navi_location.setOnClickListener(this);
        btn_navi_docinfo.setOnClickListener(this);
        btn_navi_notice.setOnClickListener(this);
        btn_navi_paranomic.setOnClickListener(this);
        btn_navi_toothpaste.setOnClickListener(this);
        btn_hamburger.setOnClickListener(this);

        FragmentAdapter pagerAdapter = new FragmentAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), mPresenter.isLogin(), fragmentManager , transaction);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabTextColors(Color.parseColor("#287DFA"), Color.parseColor("#FF4081"));
        tabLayout.addOnTabSelectedListener(tabSelectedListener);
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

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    public interface onKeyBackPressedListener {
        public void onBack();
    }

    private onKeyBackPressedListener mOnKeyBackPressedListener;

    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener) {
        mOnKeyBackPressedListener = listener;
    }

    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }

}
