package com.onedaydent.onedaydent.Notice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onedaydent.onedaydent.Adapter.NoticeAdapter;
import com.onedaydent.onedaydent.Common.Common;
import com.onedaydent.onedaydent.Common.MysqlConnector;
import com.onedaydent.onedaydent.Notice.Domain.NoticeVO;
import com.onedaydent.onedaydent.R;

import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    private NoticeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        recyclerView = findViewById(R.id.notice_list);
        findViewById(R.id.btn_noticeclose).setOnClickListener(this);
        List<NoticeVO> items = null;
        try{
            if(Common.getNetworkState(this) != 3){
                MysqlConnector conn = new MysqlConnector();
                String result = conn.execute("getNotice").get();
                if(!result.equals("false") && !result.equals("")){
                    Gson gson = new Gson();
                    items = gson.fromJson(result, new TypeToken<ArrayList<NoticeVO>>(){}.getType());
                }
            }else{
                Common.showToast("네트워크가 불안정하여 서비스를 이용하실수 없습니다.", getParent());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        mAdapter = new NoticeAdapter(items, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_noticeclose){
            finish();
        }
    }
}
