package com.onedaydent.onedaydent.Setting;

import android.app.Activity;
import android.content.SharedPreferences;

import com.onedaydent.onedaydent.Common.Common;
import com.onedaydent.onedaydent.Common.MysqlConnector;

import androidx.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class SettingPresenter implements SettingContract.Presenter  {

    private SettingContract.View mView;
    private Activity activity;
    private String TAG = "TAG";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private MysqlConnector connector;

    public SettingPresenter(@NonNull SettingContract.View view, @NonNull Activity activity, SharedPreferences pref) {
        this.mView = checkNotNull(view);
        this.activity = checkNotNull(activity);
        this.pref = pref;
        mView.setPresenter(this);
        editor = pref.edit();
    }

    @Override
    public void settingPush(boolean bool, String id) {
        editor.putBoolean("push", bool);
        editor.commit();
        if(!id.equals("")){
            int i = 0;
            if(bool){
                i = 1;
            }
            if(Common.getNetworkState(activity) != 3){
                connector = new MysqlConnector();
                connector.execute("updateAlert", "push", i + "", id);
            }else{
                Common.showToast("네트워크가 불안정하여 서비스를 이용하실수 없습니다.", activity);
            }
        }
    }

    @Override
    public void settingEvent(boolean bool, String id) {
        editor.putBoolean("event", bool);
        editor.commit();
        if(!id.equals("")){
            int i = 0;
            if(bool){
                i = 1;
            }
            if(Common.getNetworkState(activity) != 3){
                connector = new MysqlConnector();
                connector.execute("updateAlert", "event", i + "", id);
            }else{
                Common.showToast("네트워크가 불안정하여 서비스를 이용하실수 없습니다.", activity);
            }
        }
    }

    @Override
    public void finish() {
        activity.finish();
    }

    @Override
    public void start() {
        mView.viewInit();
    }

}
