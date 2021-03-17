package com.onedaydent.onedaydent.Main.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onedaydent.onedaydent.Adapter.DoctorAdapter;
import com.onedaydent.onedaydent.Common.Common;
import com.onedaydent.onedaydent.Common.MysqlConnector;
import com.onedaydent.onedaydent.Main.Domain.DoctorVO;
import com.onedaydent.onedaydent.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NDocInfoFragment extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;
    private DoctorAdapter mAdapter;
    private ImageButton items1_phone;

    private String TAG = "TAG";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.doctor_list, container, false);
        recyclerView = v.findViewById(R.id.doctor_list);
        items1_phone = v.findViewById(R.id.items1_phone);
        items1_phone.setOnClickListener(new Common.Click(getActivity()));
        mAdapter = new DoctorAdapter(dataInit());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())) ;

        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog d = (BottomSheetDialog) getDialog();
                FrameLayout bottomSheet = (FrameLayout) d.findViewById(R.id.design_bottom_sheet);
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) bottomSheet.getParent();
                coordinatorLayout.getParent().requestLayout();
            }
        });
        return v;
    }

    public List<DoctorVO> dataInit(){
        String result = "";
        if(Common.getNetworkState(getContext()) != 3){
            MysqlConnector conn = new MysqlConnector();
            try{
                result = conn.execute("getDoctor").get();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            Common.showToast("네트워크가 불안정하여 서비스를 이용하실수 없습니다.", getActivity());
        }
        List<DoctorVO> items = null;
        if(!result.equals("false") && !result.equals("")){
            Gson gson = new Gson();
            if(!result.equals("")){
                items = gson.fromJson(result, new TypeToken<ArrayList<DoctorVO>>(){}.getType());
            }
        }
        return items;
    }
}
