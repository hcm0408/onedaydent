package com.onedaydent.onedaydent.Main.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onedaydent.onedaydent.Main.MainActivity;
import com.onedaydent.onedaydent.Main.Tab.NLocationTab1;
import com.onedaydent.onedaydent.Main.Tab.NLocationTab2;
import com.onedaydent.onedaydent.Main.Tab.NLocationTab3;
import com.onedaydent.onedaydent.R;

public class ShowHallFragment extends Fragment implements View.OnClickListener, MainActivity.onKeyBackPressedListener {

    public ShowHallFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBack() {
        Log.d("TAG", "onBack: ");
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        ((MainActivity) getActivity()).setOnKeyBackPressedListener(null);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_showhalllayout){
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        } else if(v.getId() == R.id.btn_showhall1){
            NLocationTab1 frag = new NLocationTab1();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout, frag).commit();
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        } else if(v.getId() == R.id.btn_showhall2){
            NLocationTab2 frag = new NLocationTab2();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout, frag).commit();
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        } else if(v.getId() == R.id.btn_showhall3){
            NLocationTab3 frag = new NLocationTab3();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout, frag).commit();
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MainActivity) context).setOnKeyBackPressedListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_show_hall, container, false);
        TextView hall1 = v.findViewById(R.id.btn_showhall1);
        TextView hall2 = v.findViewById(R.id.btn_showhall2);
        TextView hall3 = v.findViewById(R.id.btn_showhall3);
        ConstraintLayout c = v.findViewById(R.id.btn_showhalllayout);
        c.setOnClickListener(this);
        hall1.setOnClickListener(this);
        hall2.setOnClickListener(this);
        hall3.setOnClickListener(this);
        return v;
    }
}