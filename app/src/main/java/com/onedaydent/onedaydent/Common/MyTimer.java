package com.onedaydent.onedaydent.Common;

import android.os.CountDownTimer;

import com.onedaydent.onedaydent.Login.LoginContract;

import java.util.concurrent.TimeUnit;

public class MyTimer extends CountDownTimer {

    private LoginContract.View mView;

    public MyTimer(long millie, long interval, LoginContract.View mView){
        super(millie, interval);
        this.mView = mView;
    }

    @Override
    public void onTick(long l) {
        String hms = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(l) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(l)),
                TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
        mView.changeAuthTimer("인증번호 유효 " + hms);
    }

    @Override
    public void onFinish() {
        mView.changeAuthTimer("인증번호 만료");
        mView.changeAuthTimerTextColor(false);
        mView.btnLogin_textChange("인증번호 재발송");
        mView.changeTimerValid(false);
    }
}
