package com.onedaydent.onedaydent.Main.Tab;

import android.app.Activity;
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
import android.widget.TextView;

import com.onedaydent.onedaydent.Adapter.Tab2Adapter1;
import com.onedaydent.onedaydent.Common.Common;
import com.onedaydent.onedaydent.Common.DB;
import com.onedaydent.onedaydent.Main.Domain.TreatListVO;
import com.onedaydent.onedaydent.Main.Fragment.ItemListDialogFragment2;
import com.onedaydent.onedaydent.R;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainTab2 extends Fragment implements View.OnTouchListener{

    private SharedPreferences pref;
    private ItemListDialogFragment2 fragment;
    private FragmentManager fragmentManager;
    private Common.Gesture ges;

    private RecyclerView recyclerView;
    private Tab2Adapter1 mAdapter1;

    boolean isLogin = false;
    private TextView txt;

    public MainTab2(boolean isLogin, FragmentManager fragmentManager, FragmentTransaction transaction){
        this.isLogin = isLogin;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        ges.getDetector().onTouchEvent(motionEvent);
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info_tab2, container, false);
        txt =  v.findViewById(R.id.txt_tab2_main);
        recyclerView = v.findViewById(R.id.tab2_list);
        List<TreatListVO> items = Common.readTreatListCursor(DB.getInstance().getDbHelper().getAllData("TB_TREATLIST"));
        SharedPreferences pref = getActivity().getSharedPreferences("member", Activity.MODE_PRIVATE);

        mAdapter1 = new Tab2Adapter1(items, fragmentManager, pref.getString("id", ""));
        recyclerView.setAdapter(mAdapter1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())) ;

        dataInit();
        return v;
    }

    public void dataInit(){
        String str = "";
        if(isLogin){
            pref = getActivity().getSharedPreferences("member", Context.MODE_PRIVATE);
            String name = pref.getString("name", "");
            String id = pref.getString("id", "");
            str = name + "님의, 치료 내역은";
            SpannableStringBuilder ssb = new SpannableStringBuilder(str);
            ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4081")), str.indexOf(" "), str.lastIndexOf("은"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            txt.setText(ssb);
        }else{
            str = "로그인 정보가 없습니다.";
            txt.setText(str);
        }
    }
}
