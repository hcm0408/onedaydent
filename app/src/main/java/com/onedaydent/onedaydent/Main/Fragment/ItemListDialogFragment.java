package com.onedaydent.onedaydent.Main.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.*;
import com.onedaydent.onedaydent.Adapter.ItemListFragmentAdapter1;
import com.onedaydent.onedaydent.Common.Common;
import com.onedaydent.onedaydent.Common.DB;
import com.onedaydent.onedaydent.Main.Domain.ReservationVO;
import com.onedaydent.onedaydent.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class ItemListDialogFragment extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;
    private ItemListFragmentAdapter1 mAdapter;
    private TextView items1_date;
    private ImageButton items1_phone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_list_dialog, container, false);
        recyclerView = v.findViewById(R.id.items1_list);
        items1_date = v.findViewById(R.id.items1_date);
        items1_phone = v.findViewById(R.id.items1_phone);
        items1_phone.setOnClickListener(new Common.Click(getActivity()));

        mAdapter = new ItemListFragmentAdapter1(dataInit());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())) ;

        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog d = (BottomSheetDialog) getDialog();
                FrameLayout bottomSheet = (FrameLayout) d.findViewById(R.id.design_bottom_sheet);
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) bottomSheet.getParent();
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                bottomSheetBehavior.setPeekHeight(1200);
                coordinatorLayout.getParent().requestLayout();
            }
        });
        return v;
    }

    public ArrayList<ReservationVO> dataInit(){
        ArrayList<ReservationVO> items = Common.readReservCursor(DB.getInstance().getDbHelper().getAllData("TB_RESERV"));
        if(items.size() != 0){
            if(items.get(0).getReserv_Next().equals("1")){
                Iterator<ReservationVO> iter = items.iterator();
                boolean first = true;
                while(iter.hasNext()){
                    if(first){
                        ReservationVO vo = iter.next();
                        String[] date = vo.getReserv_Date().split("-");
                        items1_date.setText(date[0] + "년 " + date[1] + "월 " + date[2] + "일");
                        iter.remove();
                    }
                    first = false;
                }
            }
            Collections.reverse(items);
        }
        return items;
    }

}
