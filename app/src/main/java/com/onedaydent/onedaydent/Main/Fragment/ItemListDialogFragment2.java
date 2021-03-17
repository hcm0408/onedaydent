package com.onedaydent.onedaydent.Main.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.onedaydent.onedaydent.Adapter.ItemListFragmentAdapter2;
import com.onedaydent.onedaydent.Common.Common;
import com.onedaydent.onedaydent.Common.MssqlConnectorIPRO;
import com.onedaydent.onedaydent.Main.Domain.TreatDetailListVO;
import com.onedaydent.onedaydent.Main.Domain.TreatListVO;
import com.onedaydent.onedaydent.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemListDialogFragment2 extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;
    private ItemListFragmentAdapter2 mAdapter;
    private ImageButton items2_phone;
    private TextView item2_treatName;
    private TreatListVO vo;
    private ArrayList<TreatDetailListVO> items;
    private ArrayList<TreatDetailListVO> myItems;

    private String TAG = "TAG";

    public ItemListDialogFragment2(TreatListVO vo, String id){
        try{
            this.vo = vo;
            MssqlConnectorIPRO conn = new MssqlConnectorIPRO(getActivity());
            items = conn.getTreatDetailList(vo);
            myItems = conn.getMyTreatDetailList(vo, id);
            Collections.reverse(items);
            Iterator<TreatDetailListVO> iterator = myItems.iterator();
            while(iterator.hasNext()){
                TreatDetailListVO item = iterator.next();
                if(item.getTDL_TxName().lastIndexOf("(") != -1){
                    if(item.getTDL_TxName().charAt(item.getTDL_TxName().lastIndexOf("(") + 1) == '임' ||
                            item.getTDL_TxName().charAt(item.getTDL_TxName().lastIndexOf("(") + 1) == 'A' ||
                            item.getTDL_TxName().charAt(item.getTDL_TxName().lastIndexOf("(") + 1) == 'B' ||
                            item.getTDL_TxName().charAt(item.getTDL_TxName().lastIndexOf("(") + 1) == 'C' ){
                        item.setTDL_TxName(item.getTDL_TxName().substring(0, item.getTDL_TxName().lastIndexOf("(") -1));
                    }
                }
            }

            TreatDetailListVO temp = new TreatDetailListVO();
            boolean bool = true;
            for(TreatDetailListVO item : myItems){
                if(bool){
                    if(item.getTDL_Depth() == 1){
                        temp = (TreatDetailListVO) item.clone();
                        bool = false;
                    }
                }else{
                    break;
                }
            }
            iterator = items.iterator();
            while(iterator.hasNext()){
                TreatDetailListVO item = iterator.next();
                if(item.getTDL_TxName().contains(temp.getTDL_TxName())){
                    iterator.remove();
                    break;
                }else{
                    iterator.remove();
                }
            }
            Collections.reverse(items);
            items.addAll(myItems);

            ArrayList<TreatDetailListVO> itemsNew = new ArrayList<TreatDetailListVO>();
            iterator = items.iterator();
            while(iterator.hasNext()){
                TreatDetailListVO item = iterator.next();
                if(item.getTDL_Depth() != 1){
                    itemsNew.add(item);
                    iterator.remove();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_list_dialog2, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.items2_list);
        items2_phone = v.findViewById(R.id.items2_phone);
        item2_treatName = v.findViewById(R.id.item2_treatName);
        items2_phone.setOnClickListener(new Common.Click(getActivity()));
        item2_treatName.setText(vo.getTL_TxName());

        mAdapter = new ItemListFragmentAdapter2(items, getActivity());
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
}
