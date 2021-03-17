package com.onedaydent.onedaydent.Main.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.onedaydent.onedaydent.Adapter.ItemListFragmentAdapter3;
import com.onedaydent.onedaydent.Common.Common;
import com.onedaydent.onedaydent.Common.DB;
import com.onedaydent.onedaydent.Main.Domain.PaymentVO;
import com.onedaydent.onedaydent.R;

import java.util.ArrayList;
import java.util.Iterator;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemListDialogFragment3 extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;
    private ItemListFragmentAdapter3 mAdapter;
    private TextView items3_price_n;
    private TextView items3_payment_n;
    private ImageButton items3_phone;

    private ArrayList<PaymentVO> items;
    private int misu = 0;
    private int result = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_list_dialog3, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.items3_list);
        items3_price_n = v.findViewById(R.id.items3_price_n);
        items3_payment_n = v.findViewById(R.id.items3_payment_n);
        items3_phone = v.findViewById(R.id.items3_phone);
        items3_phone.setOnClickListener(new Common.Click(getActivity()));
        dataInit();
        items3_price_n.setText(Common.intTypeTrans(result + misu));
        mAdapter = new ItemListFragmentAdapter3(items);
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

    public void dataInit(){
        misu = 0;
        result = 0;
        items = Common.readPaymentCursor(DB.getInstance().getDbHelper().getAllData("TB_PAY"));
        Iterator<PaymentVO> iter = items.iterator();
        int i = 0;
        while(iter.hasNext()){
            if(i == 0){
                PaymentVO item = iter.next();
                if(item.getPayType().equals("A")){
                    items3_payment_n.setText(Common.intTypeTrans(item.getPayMisu()));
                    misu = item.getPayMisu();
                    iter.remove();
                }
            }
        }
        for(PaymentVO item : items){
            result += item.getPayCard() + item.getPayCash() + item.getPayOnline();
        }
    }

}
