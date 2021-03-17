package com.onedaydent.onedaydent.Intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.onedaydent.onedaydent.Common.BackPressClose;
import com.onedaydent.onedaydent.Common.ChangeStatusBarColor;
import com.onedaydent.onedaydent.Common.Common;
import com.onedaydent.onedaydent.R;
import static com.google.common.base.Preconditions.checkNotNull;

public class IntroActivity extends AppCompatActivity implements IntroContract.View, View.OnClickListener {

    private IntroContract.Presenter mPresenter;
    private String TAG = "TAG";
    private TextView txt_phoneinquiry;
    private Button btn_loginOpen;
    public static IntroActivity mActivity;
    private BackPressClose bpc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        new IntroPresenter(this, this, getSupportFragmentManager());
        new ChangeStatusBarColor(this, Color.argb(1,40,125,250));
        mActivity = this;
        mPresenter.start();
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver,
                new IntentFilter("saveToken"));
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
            Common.saveToken(token, mActivity);
        }
    };

    @Override
    public void viewInit() {
        this.txt_phoneinquiry = findViewById(R.id.txt_phoneinquiry);
        this.btn_loginOpen = findViewById(R.id.btn_loginOpen);
        txt_phoneinquiry.setOnClickListener(this);
        btn_loginOpen.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_loginOpen){
            // 로그인창 열기
            mPresenter.openLogin();
        }else if(view.getId() == R.id.txt_phoneinquiry){
            // 전화문의 열기
            mPresenter.openPhoneInquryMenu();
        }
    }

    @Override
    public void setPresenter(IntroContract.Presenter presenter) {
        this.mPresenter = checkNotNull(presenter);
    }
}
