package com.onedaydent.onedaydent.Notice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.onedaydent.onedaydent.Common.PicassoTransformations;
import com.onedaydent.onedaydent.R;
import com.squareup.picasso.Picasso;

public class NoticeDetailActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        Intent intent = getIntent();
        findViewById(R.id.btn_noticeclose).setOnClickListener(this);
        TextView txt = findViewById(R.id.notice_detail_txt);
        ImageView img = findViewById(R.id.notice_detail_img);
        String str = intent.getStringExtra("wr_content");
        String file = intent.getStringExtra("bf_file");
        if(file != null)
            Picasso.get().load("http://www.onedaysister.com/data/file/notice/" + file).transform(PicassoTransformations.resizeTransformation).into(img);
        txt.setText(Html.fromHtml(str));
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_noticeclose){
            finish();
        }
    }
}