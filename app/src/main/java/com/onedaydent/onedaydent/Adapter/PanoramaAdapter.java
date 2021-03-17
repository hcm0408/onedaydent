package com.onedaydent.onedaydent.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onedaydent.onedaydent.Common.PicassoTransformations;
import com.onedaydent.onedaydent.Main.Domain.PanoramaVO;
import com.onedaydent.onedaydent.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class PanoramaAdapter extends RecyclerView.Adapter<PanoramaAdapter.ViewHolder> {

    private List<PanoramaVO> list = null;

    public PanoramaAdapter(List<PanoramaVO> list) {
        this.list = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public PanoramaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.fragment_npanorama_item, parent, false) ;
        PanoramaAdapter.ViewHolder vh = new PanoramaAdapter.ViewHolder(view) ;
        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(PanoramaAdapter.ViewHolder holder, int position) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        PanoramaVO item = list.get(position);
        Log.d("TAG", "onBindViewHolder: " + item.getC_Url());
        Picasso.get()
                .load(item.getC_Url())
                .transform(PicassoTransformations.resizeTransformation)
                .into(holder.imageView);
        holder.date.setText(format.format(item.getC_Date()));
    }
    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return list.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date ;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView) ;
            // 뷰 객체에 대한 참조. (hold strong reference)
            date = itemView.findViewById(R.id.npanos_date);
            imageView = itemView.findViewById(R.id.npanos_img);
        }
    }

}