package com.onedaydent.onedaydent.Main.Tab;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.onedaydent.onedaydent.Common.Common;
import com.onedaydent.onedaydent.Common.DB;
import com.onedaydent.onedaydent.Main.Domain.ReservationVO;
import com.onedaydent.onedaydent.Main.Fragment.ItemListDialogFragment;
import com.onedaydent.onedaydent.R;

import java.util.ArrayList;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainTab1 extends Fragment implements View.OnTouchListener {

    private SharedPreferences pref;
    private ItemListDialogFragment fragment;

    boolean isLogin = false;
    private TextView txt;
    private TextView txt2;
    private ImageButton btn;
    private Common.Gesture ges;

    public MainTab1(boolean isLogin, FragmentManager fragmentManager, FragmentTransaction transaction){
        this.isLogin = isLogin;
        fragment = new ItemListDialogFragment();
        fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle);
        ges = new Common.Gesture(getActivity(), fragment, fragmentManager, 1);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        ges.getDetector().onTouchEvent(motionEvent);
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info_tab1, container, false);
        txt =  v.findViewById(R.id.txt_tab1_main);
        txt2 =  v.findViewById(R.id.txt_tab1_date);
        btn = v.findViewById(R.id.btn_tab1_arrow);
        btn.setOnTouchListener(this);
        dataInit();
        return v;
    }

    public void dataInit(){
        String str = "";
        if(isLogin){
            pref = getActivity().getSharedPreferences("member", Context.MODE_PRIVATE);
            String name = pref.getString("name", "");
            str = name + "님의, 다음 방문일은";
            SpannableStringBuilder ssb = new SpannableStringBuilder(str);
            ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4081")), str.indexOf(" "), str.lastIndexOf("은"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            txt.setText(ssb);
            ArrayList<ReservationVO> items = Common.readReservCursor(DB.getInstance().getDbHelper().getAllData("TB_RESERV"));
            if(items.size() != 0){
                if(items.get(0).getReserv_Next() != null){
                    if(items.get(0).getReserv_Next().equals("1")){
                        String[] date = items.get(0).getReserv_Date().split("-");
                        txt2.setText(date[0] + "년 " + date[1] + "월 " + date[2] + "일");
                    }
                }
            }
        }else{
            str = "로그인 정보가 없습니다.";
            txt.setText(str);
            txt2.setVisibility(View.INVISIBLE);
        }
    }
}
