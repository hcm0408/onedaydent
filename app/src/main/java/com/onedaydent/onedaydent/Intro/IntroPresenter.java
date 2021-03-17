package com.onedaydent.onedaydent.Intro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.onedaydent.onedaydent.Intro.Fragment.CallFragment;
import com.onedaydent.onedaydent.Login.LoginActivity;
import com.onedaydent.onedaydent.Main.Fragment.NTimetableFragment;
import com.onedaydent.onedaydent.Main.MainActivity;
import com.onedaydent.onedaydent.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import static com.google.common.base.Preconditions.checkNotNull;

public class IntroPresenter implements IntroContract.Presenter{

    private IntroContract.View mView;
    private Activity activity;
    private String TAG = "TAG";
    private SharedPreferences pref;
    private FragmentManager fragmentManager;

    public IntroPresenter(@NonNull IntroContract.View view, @NonNull Activity activity, FragmentManager fragmentManager){
        this.mView = checkNotNull(view);
        this.activity = checkNotNull(activity);
        this.fragmentManager = checkNotNull(fragmentManager);
        mView.setPresenter(this);
        pref = activity.getSharedPreferences("member", Context.MODE_PRIVATE);
    }

    @Override
    public void openPhoneInquryMenu() {
        // 전화문의 메뉴 열기
        Log.d(TAG, "openPhoneInquryMenu: ");
        CallFragment fragment = new CallFragment();
        fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle);
        fragment.show(fragmentManager, "custom_dialog");
    }

    @Override
    public void moveMain() {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void openLogin() {
        // 로그인 화면 이동
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public boolean isLogin() {
        if(!pref.getString("id", "").equals("")){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void start() {
        if(isLogin()){
            moveMain();
        }else{
            mView.viewInit();
        }
    }
}
