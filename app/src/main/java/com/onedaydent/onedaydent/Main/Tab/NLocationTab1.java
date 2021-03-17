package com.onedaydent.onedaydent.Main.Tab;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.onedaydent.onedaydent.Adapter.NLocationTab1Adapter;
import com.onedaydent.onedaydent.Main.MainActivity;
import com.onedaydent.onedaydent.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class NLocationTab1 extends Fragment implements View.OnTouchListener, MainActivity.onKeyBackPressedListener {

    public NLocationTab1(){
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MainActivity) context).setOnKeyBackPressedListener(this);
    }

    @Override
    public void onBack() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        ((MainActivity) getActivity()).setOnKeyBackPressedListener(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_location_tab1, container, false);
        MapView mapView = new MapView(getActivity());
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.502521, 127.026464), true);
        // 줌 레벨 변경
        mapView.setZoomLevel(1, true);
        // 줌 인
        mapView.zoomIn(true);
        // 줌 아웃
        mapView.zoomOut(true);
        ViewGroup mapViewContainer = (ViewGroup) v.findViewById(R.id.map_view1);
        mapViewContainer.addView(mapView);

        MapPOIItem customMarker = new MapPOIItem();
        customMarker.setItemName("원데이치과 1관");
        customMarker.setTag(0);
        customMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(37.502521, 127.026464));
        customMarker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        customMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//        customMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
//        customMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
        mapView.addPOIItem(customMarker);

        return v;
    }
}
