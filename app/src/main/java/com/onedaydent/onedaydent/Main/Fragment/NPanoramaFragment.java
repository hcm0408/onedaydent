package com.onedaydent.onedaydent.Main.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.onedaydent.onedaydent.Adapter.PanoramaAdapter;
import com.onedaydent.onedaydent.Common.Common;
import com.onedaydent.onedaydent.Common.MssqlConnectorEasydent;
import com.onedaydent.onedaydent.Main.Domain.PanoramaVO;
import com.onedaydent.onedaydent.R;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NPanoramaFragment extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;
    private PanoramaAdapter mAdapter;
    private ImageButton npano_phone;
    private String chartID = "";
    private String TAG = "TAG";

    public NPanoramaFragment(String chartID){
        this.chartID = chartID;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_npanorama, container, false);
        recyclerView = v.findViewById(R.id.npano_list);
        npano_phone = v.findViewById(R.id.npano_phone);
        npano_phone.setOnClickListener(new Common.Click(getActivity()));

        mAdapter = new PanoramaAdapter(dataInit());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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

    public ArrayList<PanoramaVO> dataInit(){
        ArrayList<PanoramaVO> items;
        MssqlConnectorEasydent conn = new MssqlConnectorEasydent(getActivity());
        items = conn.getPanoramaList(chartID);

        Calendar img1 = Calendar.getInstance();
        img1.set(Calendar.YEAR, 2019);
        img1.set(Calendar.MONTH, 4);
        img1.set(Calendar.DATE, 31);

        Iterator<PanoramaVO> iter = items.iterator();

        while(iter.hasNext()){
            PanoramaVO item = iter.next();
            Calendar cal = Calendar.getInstance();
            cal.setTime(item.getC_Date());
            String url = "";
            String url2= "";

            if( img1.compareTo(cal) == -1){
                // img2
                url = getURL(cal, false ,true);
                url2 = getURL(cal, false ,false);
            }else{
                // img1
                url = getURL(cal, true ,true);
                url2 = getURL(cal, true ,false);
            }
            if(!myURLConnector(url)){
                if(!myURLConnector(url2)){
                    iter.remove();
                }else{
                    item.setC_Url(url2);
                }
            }else{
                item.setC_Url(url);
            }
        }
        return items;
    }

    public boolean myURLConnector(String url){
        HttpURLConnection conn = null;
        boolean bool = true;
        try {
            URL urlConn = new URL(url);
            conn = (HttpURLConnection) urlConn.openConnection();
            if (conn.getResponseCode() != 200) {
                bool = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }
        return bool;
    }

    public String getURL(Calendar cal, boolean first, boolean upper){
        SimpleDateFormat format2 = new SimpleDateFormat("yyMM");
        SimpleDateFormat format3 = new SimpleDateFormat("dd");
        SimpleDateFormat format4 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat format5 = new SimpleDateFormat("HHmmss");
        String url = "http://106.245.224.219:8080/";
        if(first){
            url += "img/Sub0";
        }else{
            url += "img2/Sub0";
        }
        url += format2.format(cal.getTime()) + format3.format(cal.getTime()).charAt(0);
        String temp = "";
        if(upper){
            temp = "/P" + format4.format(cal.getTime()) + "_" + format5.format(cal.getTime()) + "_0000.BMP";
        }else{
            temp = "/p" + format4.format(cal.getTime()) + "_" + format5.format(cal.getTime()) + "_0000.bmp";
        }
        Log.d(TAG, "getURL: " + url);
        url += temp;
        return url;
    }

}
