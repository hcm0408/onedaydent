package com.onedaydent.onedaydent.Welcome;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;

import com.onedaydent.onedaydent.Common.Common;
import com.onedaydent.onedaydent.Common.DBCreater;
import com.onedaydent.onedaydent.Common.MysqlConnector;
import com.onedaydent.onedaydent.Intro.IntroActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static com.google.common.base.Preconditions.checkNotNull;

public class WelcomePresenter implements WelcomeContract.Presenter{

    private WelcomeContract.View mView;
    private Activity activity;
    private String TAG = "TAG";
    private SharedPreferences pref;
    private FragmentManager fragmentManager;

    static final int PERMISSION_REQUEST_CODE = 1;
    String[] PERMISSIONS = {"android.permission.CALL_PHONE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_PHONE_STATE"};

    public WelcomePresenter(@NonNull WelcomeContract.View view, @NonNull Activity activity, @NonNull FragmentManager fragmentManager){
        this.mView = checkNotNull(view);
        this.activity = checkNotNull(activity);
        this.fragmentManager = checkNotNull(fragmentManager);
        mView.setPresenter(this);
        LocalBroadcastManager.getInstance(activity).registerReceiver(mMessageReceiver, new IntentFilter("saveToken"));
    }

    @Override
    public boolean hasPermissions() {
        int res = 0;
        //스트링 배열에 있는 퍼미션들의 허가 상태 여부 확인
        for (String perms : PERMISSIONS){
            res = activity.checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                //퍼미션 허가 안된 경우
                return false;
            }
        }
        //퍼미션이 허가된 경우
        return true;
    }

    @Override
    public void requestNecessaryPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(PERMISSIONS, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void stopBroadCast() {
        // 앱이 종료될시 방송 리시버 등록 해제
        LocalBroadcastManager.getInstance(activity).unregisterReceiver(mMessageReceiver);
    }

    @Override
    public void saveLoginLog() {
        // 로그인 로그 전송
//        MysqlConnector conn = new MysqlConnector();
//        conn.execute("app_login");
    }

    @Override
    public void saveToken(String token) {
        // 토큰 갱신시 토큰 저장
        SharedPreferences pref = activity.getSharedPreferences("member", activity.MODE_PRIVATE);
        String id = pref.getString("id", "");

        // 토큰 Shared 저장
        Common.saveToken(token, activity);
        if(!id.equals("")){
            if(Common.getNetworkState(activity) != 3){
                MysqlConnector conn = new MysqlConnector();
                conn.execute("saveToken", id, token);
            }else{
                Common.showToast("네트워크가 불안정하여 서비스를 이용하실수 없습니다.", activity);
            }
        }
    }

    @Override
    public boolean isFirst() {
        // 최초 실행 여부 확인
        pref = activity.getSharedPreferences("checkFirst", Activity.MODE_PRIVATE);
        return pref.getBoolean("checkFirst", true);
    }

    @Override
    public void start() {
        saveLoginLog();
        if(isFirst()){
            // 최초 실행시
            SharedPreferences.Editor eiEditor = pref.edit();
            eiEditor.putBoolean("checkFirst", false);
            eiEditor.commit();
            if(!this.hasPermissions()){
                requestNecessaryPermissions();
            }
            createDatabase();
            mView.viewInit();
            mView.setAdapter();
            pref = activity.getSharedPreferences("setting", activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("push", true);
            editor.putBoolean("event", true);
            editor.commit();
        }else{
            //최초 실행이 아닌경우 메인으로 이동
            Intent intent = new Intent(activity, IntroActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }
    }

    @Override
    public void createDatabase() {
        DBCreater.getInstance().createDatabase(activity);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            saveToken(message);
        }
    };

}