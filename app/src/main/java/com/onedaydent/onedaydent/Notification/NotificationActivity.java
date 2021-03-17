package com.onedaydent.onedaydent.Notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.onedaydent.onedaydent.Adapter.NotificationAdapter;
import com.onedaydent.onedaydent.Common.Common;
import com.onedaydent.onedaydent.Common.DB;
import com.onedaydent.onedaydent.Notification.Domain.NotificationVO;
import com.onedaydent.onedaydent.R;

import java.util.List;

public class NotificationActivity extends AppCompatActivity implements NotificationContract.View, View.OnClickListener {

    private NotificationContract.Presenter mPresenter;
    private ImageView btn_close;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm);
        new NotificationPresenter(this, this, getSupportFragmentManager());
        mPresenter.start();
    }

    @Override
    public void onBackPressed() {
        DB.getInstance().getDbHelper().updateNotification();
        SharedPreferences pref = getSharedPreferences("badge", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("badgeCount", 0);
        editor.commit();
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_fcmclose){
            DB.getInstance().getDbHelper().updateNotification();
            SharedPreferences pref = getSharedPreferences("badge", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("badgeCount", 0);
            editor.commit();
            mPresenter.finish();
        }
    }

    @Override
    public void viewInit() {
        this.btn_close = findViewById(R.id.btn_fcmclose);
        this.recyclerView = findViewById(R.id.fcm_list);
        btn_close.setOnClickListener(this);
        List<NotificationVO> items = Common.readNotificationListCursor(DB.getInstance().getDbHelper().getAllDataDESC("TB_NOTIFICATION"));
        NotificationAdapter mAdapter = new NotificationAdapter(items, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void setPresenter(NotificationContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
