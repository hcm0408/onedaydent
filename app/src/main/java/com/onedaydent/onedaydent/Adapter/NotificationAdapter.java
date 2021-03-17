package com.onedaydent.onedaydent.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onedaydent.onedaydent.Notification.Domain.NotificationVO;
import com.onedaydent.onedaydent.Notification.UrlLoadActivity;
import com.onedaydent.onedaydent.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<NotificationVO> list = null;
    private Activity activity;

    public NotificationAdapter(List<NotificationVO> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.fcm_list_item, parent, false) ;
        NotificationAdapter.ViewHolder vh = new NotificationAdapter.ViewHolder(view) ;
        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(NotificationAdapter.ViewHolder holder, int position) {
        NotificationVO item = list.get(position);
        holder.left.setText(item.getTitle());
        holder.center.setText(item.getContent());
        holder.right.setText(item.getDate());
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView left;
        TextView center;
        TextView right;

        public ViewHolder(View itemView) {
            super(itemView) ;
            left = itemView.findViewById(R.id.fcm_item1);
            center = itemView.findViewById(R.id.fcm_item2);
            right = itemView.findViewById(R.id.fcm_item3);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        // 데이터 리스트로부터 아이템 데이터 참조.
                        NotificationVO item = list.get(pos);
                        if(item.getType().equals("event")){
                            if(!item.getURL().equals("")){
                                Intent intent = new Intent(activity, UrlLoadActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("URL", item.getURL());
                                intent.putExtras(bundle);
                                activity.startActivity(intent);
                            }
                        }
                    }
                }
            });
        }
    }

}