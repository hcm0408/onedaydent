package com.onedaydent.onedaydent.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onedaydent.onedaydent.Notice.Domain.NoticeVO;
import com.onedaydent.onedaydent.Notice.NoticeDetailActivity;
import com.onedaydent.onedaydent.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    private List<NoticeVO> list = null;
    private Activity activity;

    public NoticeAdapter(List<NoticeVO> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.fragment_notice_item, parent, false) ;
        NoticeAdapter.ViewHolder vh = new NoticeAdapter.ViewHolder(view) ;
        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(NoticeAdapter.ViewHolder holder, int position) {
        NoticeVO item = list.get(position);
        Date date = new Date(item.getWr_datetime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        holder.left.setText(item.getWr_subject());
        holder.center.setText(format.format(date));
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return list.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView left ;
        TextView center ;

        public ViewHolder(View itemView) {
            super(itemView) ;
            // 뷰 객체에 대한 참조. (hold strong reference)
            left = itemView.findViewById(R.id.notice_title1);
            center = itemView.findViewById(R.id.notice_title2);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        // 데이터 리스트로부터 아이템 데이터 참조.
                        NoticeVO item = list.get(pos);
                        Intent intent = new Intent(activity, NoticeDetailActivity.class);
                        intent.putExtra("wr_content" , item.getWr_content());
                        intent.putExtra("bf_file" , item.getBf_file());
                        activity.startActivity(intent);
                    }
                }
            });
        }
    }

}