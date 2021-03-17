package com.onedaydent.onedaydent.Main.Tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.onedaydent.onedaydent.R;

import androidx.fragment.app.Fragment;

public class NTimeTab2 extends Fragment implements View.OnTouchListener {

    public NTimeTab2(){
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_time_tab2, container, false);
        return v;
    }

}
