package com.onedaydent.onedaydent.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onedaydent.onedaydent.Main.Domain.TreatDetailListVO;
import com.onedaydent.onedaydent.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ItemListFragmentAdapter2 extends RecyclerView.Adapter<ItemListFragmentAdapter2.ViewHolder> {

    private List<TreatDetailListVO> list = null;
    private Activity activity;

    public ItemListFragmentAdapter2(List<TreatDetailListVO> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public ItemListFragmentAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.fragment_item_list_dialog_item2, parent, false) ;
        ItemListFragmentAdapter2.ViewHolder vh = new ItemListFragmentAdapter2.ViewHolder(view) ;
        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ItemListFragmentAdapter2.ViewHolder holder, int position) {

        TreatDetailListVO item = list.get(position);
        if(item.getTDL_Result() == 0){
            holder.title.setText("예정");
            holder.title.setTextColor(Color.parseColor("#287DFA"));
            holder.content.setTextColor(Color.parseColor("#000000"));
            holder.img.setImageDrawable(activity.getResources().getDrawable(R.drawable.clear_circle));
        }else{
            holder.title.setText("완료");
            holder.title.setTextColor(Color.parseColor("#C2C2C2"));
            holder.content.setTextColor(Color.parseColor("#C2C2C2"));
            holder.dr.setTextColor(Color.parseColor("#C2C2C2"));
            holder.date.setText(item.getTDL_Date());
            holder.dr.setText(item.getTDL_Doctor());
        }
        holder.content.setText(item.getTDL_TxName());
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return list.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        TextView date;
        TextView dr;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView) ;
            // 뷰 객체에 대한 참조. (hold strong reference)
            title = itemView.findViewById(R.id.item2_title);
            content = itemView.findViewById(R.id.item2_content);
            date = itemView.findViewById(R.id.item2_date);
            dr = itemView.findViewById(R.id.item2_dr);
            img = itemView.findViewById(R.id.item2_img);
        }
    }

}