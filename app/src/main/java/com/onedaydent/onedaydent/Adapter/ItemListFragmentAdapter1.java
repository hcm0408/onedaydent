package com.onedaydent.onedaydent.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onedaydent.onedaydent.Main.Domain.ReservationVO;
import com.onedaydent.onedaydent.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ItemListFragmentAdapter1 extends RecyclerView.Adapter<ItemListFragmentAdapter1.ViewHolder> {

    private List<ReservationVO> list = null;

    public ItemListFragmentAdapter1(List<ReservationVO> list) {
        this.list = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public ItemListFragmentAdapter1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.fragment_item_list_dialog_item, parent, false) ;
        ItemListFragmentAdapter1.ViewHolder vh = new ItemListFragmentAdapter1.ViewHolder(view) ;
        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ItemListFragmentAdapter1.ViewHolder holder, int position) {
        ReservationVO item = list.get(position) ;
        holder.left.setText("방문완료") ;
        holder.center.setText(item.getReserv_TxName()) ;
        holder.right.setText(item.getReserv_Date()) ;
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return list.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView left ;
        TextView center ;
        TextView right ;

        public ViewHolder(View itemView) {
            super(itemView) ;
            // 뷰 객체에 대한 참조. (hold strong reference)
            left = itemView.findViewById(R.id.item1_title1);
            center = itemView.findViewById(R.id.item1_title2);
            right = itemView.findViewById(R.id.item1_title3);
        }
    }

}