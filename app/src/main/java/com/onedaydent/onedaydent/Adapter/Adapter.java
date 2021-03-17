package com.onedaydent.onedaydent.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.onedaydent.onedaydent.Intro.IntroActivity;
import com.onedaydent.onedaydent.R;

import androidx.annotation.NonNull;

public class Adapter extends androidx.viewpager.widget.PagerAdapter implements View.OnClickListener {

    private LayoutInflater layoutInflater;
    private int[] layouts;
    private Activity activity;

    public Adapter(int[] layouts, Activity activity){
        this.layouts = layouts;
        this.activity = activity;
    }
    
    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layouts[position], container, false);
        container.addView(view);
        Button button = null;
        switch (position){
            case 0:
                button = view.findViewById(R.id.btn_skip1);
                break;
            case 1:
                button = view.findViewById(R.id.btn_skip2);
                break;
            case 2:
                button = view.findViewById(R.id.btn_start);
                break;
        }
        if(button != null)
            button.setOnClickListener(this);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View)object;
        container.removeView(view);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_skip1 || view.getId() == R.id.btn_skip2 || view.getId() == R.id.btn_start){
            Intent intent = new Intent(activity, IntroActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }

    }
}