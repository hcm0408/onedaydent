package com.onedaydent.onedaydent.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onedaydent.onedaydent.Common.Common;
import com.onedaydent.onedaydent.Main.Domain.PaymentVO;
import com.onedaydent.onedaydent.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ItemListFragmentAdapter3 extends RecyclerView.Adapter<ItemListFragmentAdapter3.ViewHolder> {

    private List<PaymentVO> list = null;

    public ItemListFragmentAdapter3(List<PaymentVO> list) {
        this.list = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public ItemListFragmentAdapter3.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.fragment_item_list_dialog_item3, parent, false) ;
        ItemListFragmentAdapter3.ViewHolder vh = new ItemListFragmentAdapter3.ViewHolder(view) ;
        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ItemListFragmentAdapter3.ViewHolder holder, int position) {
        PaymentVO item = list.get(position);
        // 첫번째 항목의 타입이 A이면 미수금액 존재
        if(item.getPayType().equals("B")){
            holder.left.setText("현금");
            holder.center.setText(Common.intTypeTrans(item.getPayCash()) );
        }else if(item.getPayType().equals("C")){
            holder.left.setText("카드");
            holder.center.setText(Common.intTypeTrans(item.getPayCard()));
        }else if(item.getPayType().equals("D")){
            holder.left.setText("온라인");
            holder.center.setText(Common.intTypeTrans(item.getPayOnline()));
        }
        holder.right.setText(item.getPayDate());
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
            left = itemView.findViewById(R.id.item3_title1);
            center = itemView.findViewById(R.id.item3_title2);
            right = itemView.findViewById(R.id.item3_title3);
        }
    }

}