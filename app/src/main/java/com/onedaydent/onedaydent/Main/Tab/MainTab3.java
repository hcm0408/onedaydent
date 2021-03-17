package com.onedaydent.onedaydent.Main.Tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.onedaydent.onedaydent.Common.Common;
import com.onedaydent.onedaydent.Common.DB;
import com.onedaydent.onedaydent.Main.Domain.PaymentVO;
import com.onedaydent.onedaydent.Main.Fragment.ItemListDialogFragment3;
import com.onedaydent.onedaydent.R;

import java.util.ArrayList;
import java.util.Iterator;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainTab3 extends Fragment implements View.OnTouchListener{

    private ItemListDialogFragment3 fragment;
    private Common.Gesture ges;

    private boolean isLogin = false;
    private ImageButton btn;
    private TextView txt_tab3_price_n;
    private TextView txt_tab3_payment_n;
    private TextView txt_tab3_balance_n;

    private int misu = 0;
    private int result = 0;

    public MainTab3(boolean isLogin, FragmentManager fragmentManager, FragmentTransaction transaction){
        this.isLogin = isLogin;
        fragment = new ItemListDialogFragment3();
        fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle);
        ges = new Common.Gesture(getActivity(), fragment, fragmentManager, 3);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        ges.getDetector().onTouchEvent(motionEvent);
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info_tab3, container, false);
        btn = v.findViewById(R.id.btn_tab3_arrow);
        this.txt_tab3_price_n = v.findViewById(R.id.txt_tab3_price_n);
        this.txt_tab3_payment_n = v.findViewById(R.id.txt_tab3_payment_n);
        this.txt_tab3_balance_n = v.findViewById(R.id.txt_tab3_balance_n);
        btn.setOnTouchListener(this);

        dataInit();
        return v;
    }

    public void dataInit(){
        misu = 0;
        result = 0;
        ArrayList<PaymentVO> items = Common.readPaymentCursor(DB.getInstance().getDbHelper().getAllData("TB_PAY"));
        Iterator<PaymentVO> iter = items.iterator();
        int i = 0;
        while(iter.hasNext()){
            if(i == 0){
                PaymentVO item = iter.next();
                if(item.getPayType().equals("A")){
                    txt_tab3_balance_n.setText(Common.intTypeTrans(item.getPayMisu()));
                    misu = item.getPayMisu();
                }
            }
        }
        for(PaymentVO item : items){
            result += item.getPayCard() + item.getPayCash() + item.getPayOnline();
        }
        txt_tab3_payment_n.setText(Common.intTypeTrans(result));
        txt_tab3_price_n.setText(Common.intTypeTrans(result + misu));
    }
}
