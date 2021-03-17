package com.onedaydent.onedaydent.Login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.onedaydent.onedaydent.Common.BackPressClose;
import com.onedaydent.onedaydent.Common.Common;
import com.onedaydent.onedaydent.Common.MyTimer;
import com.onedaydent.onedaydent.R;

public class LoginActivity extends AppCompatActivity implements LoginContract.View, View.OnClickListener{

    private LoginContract.Presenter mPresenter;
    private String TAG = "TAG";
    private ImageButton btn_loginClose;
    private Button btn_login;
    private EditText edit_phone;
    private EditText edit_auth;
    private TextView txt_loginTimer;
    private boolean isTimerValid = false;
    private MyTimer timer = null;
    private static LoginActivity activity;
    private BackPressClose bpc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        new LoginPresenter(this, this);
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
        this.btn_loginClose = findViewById(R.id.btn_loginClose);
        this.btn_login = findViewById(R.id.btn_login);
        this.edit_phone = findViewById(R.id.edit_phone);
        this.edit_auth = findViewById(R.id.edit_auth);
        this.txt_loginTimer = findViewById(R.id.txt_loginTimer);
        btn_loginClose.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void btnLogin_textChange(String str) {
        // 로그인 버튼 텍스트 변경
        this.btn_login.setText(str);
    }

    @Override
    public void showAuthTimer(boolean bool) {
        // 타이머 보여주는 여부
        if(bool)
            txt_loginTimer.setVisibility(View.VISIBLE);
    }

    @Override
    public void changeAuthTimer(String str) {
        // 타이머 시간 변경
        txt_loginTimer.setText(str);
    }

    @Override
    public void changeAuthTimerTextColor(boolean bool) {
        if(bool){
            txt_loginTimer.setTextColor(Color.BLACK);
        }else{
            txt_loginTimer.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void changeTimerValid(boolean bool) {
        this.isTimerValid = bool;
        mPresenter.loginAuthexpiration();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_loginClose:
                mPresenter.close();
                break;
            case R.id.btn_login:
                Log.d(TAG, "onClick: " + Common.getNetworkState(activity));
                if(Common.getNetworkState(activity) != 3){
                    if(mPresenter.isMember(edit_phone.getText().toString())){
                        if(!isTimerValid){
                            // 전화번호 정규식 체크
                            if(mPresenter.checkPhoneNumber(edit_phone.getText().toString())){
                                // 인증번호 타이머
                                timer = new MyTimer(300000, 1000,this);
                                timer.start();
                                mPresenter.generateAuthKey(edit_phone.getText().toString());
                                isTimerValid = true;
                            }else{
                                Toast.makeText(this, "올바른 핸드폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            // 인증번호 확인
                            if(mPresenter.checkAutkKey(edit_auth.getText().toString())){
                                timer.onFinish();
                                mPresenter.getMemberInfo(edit_auth.getText().toString());
                            }else{
                                Toast.makeText(this, "인증번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{
                        Toast.makeText(this, "원데이치과에 등록된 번호가 아닙니다.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Common.showToast("네트워크가 불안정하여 서비스를 이용하실수 없습니다.", activity);
                }
                break;
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
