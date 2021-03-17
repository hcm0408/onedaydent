package com.onedaydent.onedaydent.Common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.onedaydent.onedaydent.Notification.Domain.NotificationVO;
import com.onedaydent.onedaydent.Main.Domain.PaymentVO;
import com.onedaydent.onedaydent.Main.Domain.ReservationVO;
import com.onedaydent.onedaydent.Main.Domain.TreatListVO;
import com.onedaydent.onedaydent.Main.Fragment.ItemListDialogFragment;
import com.onedaydent.onedaydent.Main.Fragment.ItemListDialogFragment2;
import com.onedaydent.onedaydent.Main.Fragment.ItemListDialogFragment3;
import com.onedaydent.onedaydent.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import androidx.fragment.app.FragmentManager;

public class Common {

    public static String intTypeTrans(int i){
        return new DecimalFormat("###,###원").format(i);
    }

    public static ArrayList<ReservationVO> readReservCursor(Cursor cursor){
        ArrayList<ReservationVO> items = new ArrayList<ReservationVO>();
        while(cursor.moveToNext()){
            ReservationVO item = new ReservationVO();
            item.setReserv_Date(cursor.getString(cursor.getColumnIndexOrThrow(ReservationHelper.getCol1())));
            item.setReserv_Time(cursor.getString(cursor.getColumnIndexOrThrow(ReservationHelper.getCol2())));
            item.setReserv_TxName(cursor.getString(cursor.getColumnIndexOrThrow(ReservationHelper.getCol3())));
            item.setReserv_Next(cursor.getString(cursor.getColumnIndexOrThrow(ReservationHelper.getCol4())));
            items.add(item);
        }
        return items;
    }

    public static ArrayList<PaymentVO> readPaymentCursor(Cursor cursor){
        ArrayList<PaymentVO> items = new ArrayList<PaymentVO>();
        while(cursor.moveToNext()){
            PaymentVO item = new PaymentVO();
            item.setPayCash(cursor.getInt(cursor.getColumnIndexOrThrow(PaymentHelper.getCol1())));
            item.setPayCard(cursor.getInt(cursor.getColumnIndexOrThrow(PaymentHelper.getCol2())));
            item.setPayOnline(cursor.getInt(cursor.getColumnIndexOrThrow(PaymentHelper.getCol3())));
            item.setPayMisu(cursor.getInt(cursor.getColumnIndexOrThrow(PaymentHelper.getCol4())));
            item.setPayType(cursor.getString(cursor.getColumnIndexOrThrow(PaymentHelper.getCol5())));
            item.setPayDate(cursor.getString(cursor.getColumnIndexOrThrow(PaymentHelper.getCol6())));
            items.add(item);
        }
        return items;
    }

    public static ArrayList<TreatListVO> readTreatListCursor(Cursor cursor){
        ArrayList<TreatListVO> items = new ArrayList<TreatListVO>();
        while(cursor.moveToNext()){
            TreatListVO item = new TreatListVO();
            item.setTL_Listkey(cursor.getString(cursor.getColumnIndexOrThrow(TreatListHelper.getCol1())));
            item.setTL_Masterkey(cursor.getString(cursor.getColumnIndexOrThrow(TreatListHelper.getCol2())));
            item.setTL_TxName(cursor.getString(cursor.getColumnIndexOrThrow(TreatListHelper.getCol3())));
            item.setTL_Doctor(cursor.getString(cursor.getColumnIndexOrThrow(TreatListHelper.getCol4())));
            item.setTL_Date(cursor.getString(cursor.getColumnIndexOrThrow(TreatListHelper.getCol5())));
            item.setTL_TxDetail(cursor.getString(cursor.getColumnIndexOrThrow(TreatListHelper.getCol6())));
            item.setTL_Result(cursor.getString(cursor.getColumnIndexOrThrow(TreatListHelper.getCol7())));
            items.add(item);
        }
        return items;
    }

    public static ArrayList<NotificationVO> readNotificationListCursor(Cursor cursor){
        ArrayList<NotificationVO> items = new ArrayList<NotificationVO>();
        while(cursor.moveToNext()){
            NotificationVO item = new NotificationVO();
            item.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(NotificationHelper.getCol1())));
            item.setContent(cursor.getString(cursor.getColumnIndexOrThrow(NotificationHelper.getCol2())));
            item.setImgURL(cursor.getString(cursor.getColumnIndexOrThrow(NotificationHelper.getCol3())));
            item.setURL(cursor.getString(cursor.getColumnIndexOrThrow(NotificationHelper.getCol4())));
            item.setType(cursor.getString(cursor.getColumnIndexOrThrow(NotificationHelper.getCol5())));
            item.setDate(cursor.getString(cursor.getColumnIndexOrThrow(NotificationHelper.getCol6())));
            item.setIsRead(cursor.getInt(cursor.getColumnIndexOrThrow(NotificationHelper.getCol7())));
            items.add(item);
        }
        return items;
    }

    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 3;

    public static int getNetworkState(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if(networkInfo != null){
            int type = networkInfo.getType();
            if(type == ConnectivityManager.TYPE_MOBILE){//쓰리지나 LTE로 연결된것(모바일을 뜻한다.)
                return TYPE_MOBILE;
            }else if(type == ConnectivityManager.TYPE_WIFI){//와이파이 연결된것
                return TYPE_WIFI;
            }
        }
        return TYPE_NOT_CONNECTED;  //연결이 되지않은 상태
    }

    public static class Click implements View.OnClickListener {

        private Activity activity;

        public Click(Activity activity){
            this.activity = activity;
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.items1_phone || view.getId() == R.id.items2_phone
                    || view.getId() == R.id.items3_phone || view.getId() == R.id.npano_phone
                    || view.getId() == R.id.ntime_phone || view.getId() == R.id.nlo_phone){
                String tel = "tel:025082875";
                Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(tel));
                activity.startActivity(intent);
            }
        }
    }

    public static void saveToken(String token, Activity activity){
        SharedPreferences pref = activity.getSharedPreferences("token", activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", token);
        editor.commit();

        SharedPreferences pref2 = activity.getSharedPreferences("member", activity.MODE_PRIVATE);
        String id = pref2.getString("id","");
        if(!id.equals("")){
            if(Common.getNetworkState(activity) != 3){
                MysqlConnector conn = new MysqlConnector();
                conn.execute("saveToken", id, token);
            }else{
                Common.showToast("네트워크가 불안정하여 서비스를 이용하실수 없습니다.", activity);
            }
        }
    }
    public static void showToast(String text, Activity activity){
        Toast.makeText(activity.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
    public static class Gesture {

        private GestureDetector detector;
        private Activity activity;
        private ItemListDialogFragment fragment1;
        private ItemListDialogFragment2 fragment2;
        private ItemListDialogFragment3 fragment3;
        private FragmentManager fragmentManager;
        private int id = 0;

        public Gesture(Activity activity, ItemListDialogFragment fragment, FragmentManager fragmentManager, int id){
            this.activity = activity;
            this.fragmentManager = fragmentManager;
            this.fragment1 = fragment;
            this.id = id;
            init();
        }

        public Gesture(Activity activity, ItemListDialogFragment2 fragment, FragmentManager fragmentManager, int id){
            this.activity = activity;
            this.fragmentManager = fragmentManager;
            this.fragment2 = fragment;
            this.id = id;
            init();
        }

        public Gesture(Activity activity, ItemListDialogFragment3 fragment, FragmentManager fragmentManager, int id){
            this.activity = activity;
            this.fragmentManager = fragmentManager;
            this.fragment3 = fragment;
            this.id = id;
            init();
        }

        private void init(){
            detector = new GestureDetector(activity, new GestureDetector.OnGestureListener() {

                @Override
                public boolean onDown(MotionEvent motionEvent) {
                    if(id == 1){
                        fragment1.show(fragmentManager, "custom_dialog");
                    }else if(id == 2){
                        fragment2.show(fragmentManager, "custom_dialog");
                    }else if(id == 3){
                        fragment3.show(fragmentManager, "custom_dialog");
                    }
                    return false;
                }

                @Override
                public void onShowPress(MotionEvent motionEvent) {

                }

                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return false;
                }

                @Override
                public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                    return false;
                }

                @Override
                public void onLongPress(MotionEvent motionEvent) {

                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float x, float y) {
                    return true;
                }
            });
        }

        public GestureDetector getDetector(){
            return detector;
        }
    }
}
