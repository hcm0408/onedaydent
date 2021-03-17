package com.onedaydent.onedaydent.Intro.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.onedaydent.onedaydent.R;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class CallFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private TextView txt_implecall;
    private TextView txt_orthocall;
    private TextView txt_normalcall;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_call, container, false);
        txt_implecall = v.findViewById(R.id.txt_implecall);
        txt_orthocall = v.findViewById(R.id.txt_orthocall);
        txt_normalcall = v.findViewById(R.id.txt_normalcall);
        txt_implecall.setOnClickListener(this);
        txt_orthocall.setOnClickListener(this);
        txt_normalcall.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.txt_implecall || view.getId() == R.id.txt_normalcall){
            String tel = "tel:025082875";
            Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(tel));
            getActivity().startActivity(intent);
        }else{
            String tel = "tel:025082873";
            Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(tel));
            getActivity().startActivity(intent);
        }
    }
}
