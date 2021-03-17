package com.onedaydent.onedaydent.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.onedaydent.onedaydent.Common.DB;
import com.onedaydent.onedaydent.Common.DBHelper;
import com.onedaydent.onedaydent.Common.MssqlConnectorIPRO;
import com.onedaydent.onedaydent.Main.Fragment.NDocInfoFragment;
import com.onedaydent.onedaydent.Main.Fragment.ShowHallFragment;
import com.onedaydent.onedaydent.Notice.NoticeActivity;
import com.onedaydent.onedaydent.Notification.NotificationActivity;
import com.onedaydent.onedaydent.Login.LoginActivity;
import com.onedaydent.onedaydent.Main.Domain.PaymentVO;
import com.onedaydent.onedaydent.Main.Domain.ReservationVO;
import com.onedaydent.onedaydent.Main.Domain.TreatListVO;
import com.onedaydent.onedaydent.Main.Fragment.NLocationFragment;
import com.onedaydent.onedaydent.Main.Fragment.NPanoramaFragment;
import com.onedaydent.onedaydent.Main.Fragment.NTimetableFragment;
import com.onedaydent.onedaydent.R;
import com.onedaydent.onedaydent.Setting.SettingActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainPresenter implements MainContract.Presenter  {

    private MainContract.View mView;
    private Activity activity;
    private String TAG = "TAG";
    private SharedPreferences pref;
    private DBHelper helper;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    public MainPresenter(@NonNull MainContract.View view, @NonNull Activity activity, FragmentManager fragmentManager) {
        this.mView = checkNotNull(view);
        this.activity = checkNotNull(activity);
        this.fragmentManager = fragmentManager;
        mView.setPresenter(this);
        pref = activity.getSharedPreferences("member", Context.MODE_PRIVATE);
    }

    @Override
    public DBHelper getHelper() {
        return helper;
    }

    @Override
    public void phoneCall() {
        String tel = "tel:025082875";
        Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(tel));
        activity.startActivity(intent);
    }

    @Override
    public void openSetting() {
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void openFcm() {
        Intent intent = new Intent(activity, NotificationActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void openKakao() {
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://pf.kakao.com/_xbpxhdxl")));
    }

    @Override
    public void logout() {
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
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
    public void resume() {
        SharedPreferences pref = activity.getSharedPreferences("badge", activity.MODE_PRIVATE);
        int badgeCount = pref.getInt("badgeCount", 0);
        if(badgeCount == 0){
            mView.showBadge(View.INVISIBLE);
        }else{
            mView.setBadge(badgeCount);
        }
    }

    @Override
    public void getData() {
        if(isLogin()){
            MssqlConnectorIPRO conn = new MssqlConnectorIPRO(activity);
            ArrayList<ReservationVO> reserv = conn.getReservationList(pref.getString("id", ""));
            ArrayList<PaymentVO> pay = conn.getPaymentList(pref.getString("id", ""));
            ArrayList<TreatListVO> treatList = conn.getTreatList(pref.getString("id", ""));
            this.helper = new DBHelper(activity, "DB", null, 1);
            DB.getInstance().setDBHelper(helper);
            helper.insertReserv(reserv);
            helper.insertPay(pay);
            helper.insertTreatList(treatList);
        }
    }

    @Override
    public void setText(ArrayList<PaymentVO> items) {
    }

    @Override
    public void openTimetable() {
        NTimetableFragment fragment = new NTimetableFragment();
        fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle);
        fragment.show(fragmentManager, "custom_dialog");
    }

    @Override
    public void openFaq() {

    }

    @Override
    public void openLocation() {
        mView.closeDrawerLayout();
        this.transaction = fragmentManager.beginTransaction();
        ShowHallFragment fragment = new ShowHallFragment();
        transaction.replace(R.id.drawer_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
//        NLocationFragment fragment = new NLocationFragment();
//        fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle);
//        fragment.show(fragmentManager, "custom_dialog");
    }

    @Override
    public void openDocInfo() {
        NDocInfoFragment fragment = new NDocInfoFragment();
        fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle);
        fragment.show(fragmentManager, "custom_dialog");
    }

    @Override
    public void openNotice() {
        Intent intent = new Intent(activity, NoticeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void openPanorama() {
        NPanoramaFragment fragment = new NPanoramaFragment(pref.getString("chartID", ""));
        fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle);
        fragment.show(fragmentManager, "custom_dialog");
    }

    @Override
    public void openYoutube() {
        activity.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.youtube.com/channel/UCPMi12TUn3Ok_UkBScWXGIw")).setPackage("com.google.android.youtube"));
    }

    @Override
    public void openNaver() {
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://blog.naver.com/hingde45")));
    }

    @Override
    public void openInsta() {
        activity.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://instagram.com/_u/oneday_dentistry")).setPackage("com.instagram.android"));
    }

    @Override
    public void openFacebook() {
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/onedaydent")));
    }

    @Override
    public void openToothpaste() {
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://smartstore.naver.com/allrightstore/products/2310873920")));
    }

    @Override
    public void start() {
        mView.viewInit();
        getData();
    }

}
