package com.onedaydent.onedaydent.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.onedaydent.onedaydent.Common.Common;
import com.onedaydent.onedaydent.Common.MssqlConnectorIPRO;
import com.onedaydent.onedaydent.Common.MysqlConnector;
import com.onedaydent.onedaydent.Common.SmsAuth;
import com.onedaydent.onedaydent.Intro.IntroActivity;
import com.onedaydent.onedaydent.Login.Domain.MemberVO;
import com.onedaydent.onedaydent.Main.MainActivity;

import java.util.regex.Pattern;

import androidx.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginPresenter implements LoginContract.Presenter  {

    private LoginContract.View mView;
    private Activity activity;
    private String TAG = "TAG";
    private SharedPreferences pref;
    private SharedPreferences pref2;
    private SharedPreferences pref3;
    private SmsAuth sms;
    private MemberVO vo;

    public LoginPresenter(@NonNull LoginContract.View view, @NonNull Activity activity) {
        this.mView = checkNotNull(view);
        this.activity = checkNotNull(activity);
        mView.setPresenter(this);
        pref = activity.getSharedPreferences("member", Context.MODE_PRIVATE);
        pref2 = activity.getSharedPreferences("token", Context.MODE_PRIVATE);
        pref3 = activity.getSharedPreferences("setting", Context.MODE_PRIVATE);
    }

    @Override
    public String numberPatternChange(String number) {
        String temp = "";
        if(number.startsWith("010")){
            temp = number.substring(0, 3) + "-" + number.substring(3, 7) + "-" + number.substring(7, number.length());
        }else{
            temp = number.substring(0, 3) + "-" + number.substring(3, 6) + "-" + number.substring(6, number.length());
        }
        return temp;
    }

    @Override
    public void generateAuthKey(String number) {
        // 인증키 생성 *
        mView.changeAuthTimerTextColor(true);
        sms = new SmsAuth(numberPatternChange(number));
        sms.sendAuthkey();
    }

    @Override
    public void loginAuthexpiration() {
        sms.authExpiration();
    }

    @Override
    public boolean checkPhoneNumber(String number) {
        // 전화번호 정규식 체크
        if(!Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", number)){
            return false;
        }
        mView.btnLogin_textChange("로그인");
        mView.showAuthTimer(true);
        return true;
    }

    @Override
    public void moveMain() {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
        IntroActivity intro = (IntroActivity) IntroActivity.mActivity;
        intro.finish();
    }

    @Override
    public boolean isMember(String number) {
        vo = new MssqlConnectorIPRO(activity).getIsMember(numberPatternChange(number));
        if(vo.getLM_patientNo() != null){
            return true;
        }
        return false;
    }

    @Override
    public void getMemberInfo(String number) {
        // 사용자 정보 가져오기
        SharedPreferences.Editor eiEditor = pref.edit();
        eiEditor.putString("id", vo.getLM_patientNo());
        eiEditor.putString("name", vo.getLM_name());
        eiEditor.putString("chartID", vo.getLM_chartID());
        eiEditor.apply();
        if(Common.getNetworkState(activity) != 3){
            MysqlConnector conn = new MysqlConnector();
            conn.execute("saveMemberInfo", vo.getLM_patientNo(), vo.getLM_name(), vo.getLM_chartID(), pref2.getString("token", ""), pref3.getBoolean("push", false) + "", pref3.getBoolean("event", false) + "");
        }else{
            Common.showToast("네트워크가 불안정하여 서비스를 이용하실수 없습니다.", activity);
        }
        moveMain();
    }

    @Override
    public boolean checkAutkKey(String key) {
        // 인증번호 확인
        if(!key.equals("") && key.equals(sms.getAuth())){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void close() {
        // 창 닫기
        activity.finish();
    }

    @Override
    public void start() {
        mView.viewInit();
    }

}