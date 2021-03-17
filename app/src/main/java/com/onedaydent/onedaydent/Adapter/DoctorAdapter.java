package com.onedaydent.onedaydent.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.onedaydent.onedaydent.Common.PicassoTransformations;
import com.onedaydent.onedaydent.Main.Domain.DoctorVO;
import com.onedaydent.onedaydent.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

    private List<DoctorVO> list = null;
    private String TAG = "TAG";

    public DoctorAdapter(List<DoctorVO> list) {
        if(list != null){
            this.list = list;
        }else{
            this.list = new ArrayList<DoctorVO>();
        }
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public DoctorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.doctor_list_item, parent, false) ;
        DoctorAdapter.ViewHolder vh = new DoctorAdapter.ViewHolder(view) ;
        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(DoctorAdapter.ViewHolder holder, int position) {
        if(list != null){
            DoctorVO item = list.get(position);
            String url = item.getDoc_ImgURL().substring(10, item.getDoc_ImgURL().length() - 3);
            Picasso.get()
                    .load(url)
                    .transform(PicassoTransformations.resizeTransformation)
                    .into(holder.img);
        }
    }



    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView) ;
            // 뷰 객체에 대한 참조. (hold strong reference)
            img = itemView.findViewById(R.id.doctor_imgitem);
        }
    }

}