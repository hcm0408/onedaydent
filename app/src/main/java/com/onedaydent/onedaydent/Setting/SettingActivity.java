package com.onedaydent.onedaydent.Setting;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.onedaydent.onedaydent.R;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity implements SettingContract.View, View.OnClickListener {

    private SettingContract.Presenter mPresenter;
    private ImageView btn_close;
    private TextView toolbar_txt1;
    private TextView setting_version;
    private SwitchMaterial setting_push;
    private SwitchMaterial setting_event;

    private SharedPreferences pref;
    private SharedPreferences pref2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        pref = getSharedPreferences("setting", MODE_PRIVATE);
        pref2 = getSharedPreferences("member", MODE_PRIVATE);
        new SettingPresenter(this, this, pref);
        mPresenter.start();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_fcmclose){
            mPresenter.finish();
        }else if(view.getId() == R.id.setting_push){
            mPresenter.settingPush(setting_push.isChecked(), pref2.getString("id", ""));
        }else if(view.getId() == R.id.setting_event){
            mPresenter.settingEvent(setting_event.isChecked(), pref2.getString("id", ""));
        }
    }

    @Override
    public void viewInit() {
        this.btn_close = findViewById(R.id.btn_fcmclose);
        this.toolbar_txt1 = findViewById(R.id.toolbar_txt1);
        this.setting_version = findViewById(R.id.setting_version);
        this.setting_push = findViewById(R.id.setting_push);
        this.setting_event = findViewById(R.id.setting_event);
        toolbar_txt1.setText("설정");
        try{
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            setting_version.setText("v" + info.versionName);
        }catch(Exception e){
            e.printStackTrace();
        }
        btn_close.setOnClickListener(this);
        setting_push.setOnClickListener(this);
        setting_event.setOnClickListener(this);
        setting_push.setChecked(pref.getBoolean("push", false));
        setting_event.setChecked(pref.getBoolean("event", false));
    }

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
