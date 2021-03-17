package com.onedaydent.onedaydent.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onedaydent.onedaydent.Main.Domain.TreatListVO;
import com.onedaydent.onedaydent.Main.Fragment.ItemListDialogFragment2;
import com.onedaydent.onedaydent.R;

import java.util.List;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class Tab2Adapter1 extends RecyclerView.Adapter<Tab2Adapter1.ViewHolder> {

    private List<TreatListVO> list = null;
    private FragmentManager fragmentManager;
    private String id = "";

    public Tab2Adapter1(List<TreatListVO> list, FragmentManager fragmentManager, String id) {
        this.list = list;
        this.fragmentManager = fragmentManager;
        this.id = id;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public Tab2Adapter1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.fragment_tab2_item, parent, false) ;
        Tab2Adapter1.ViewHolder vh = new Tab2Adapter1.ViewHolder(view) ;
        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(Tab2Adapter1.ViewHolder holder, int position) {
        TreatListVO item = list.get(position) ;
        holder.content.setText(item.getTL_TxName());
        holder.date.setText(item.getTL_Date());
        if(item.getTL_Result().equals("1")){
            holder.result.setText("종료");
            holder.content.setTextColor(Color.parseColor("#C2C2C2"));
        }else{
            holder.result.setText("치료중");
            holder.date.setTextColor(Color.parseColor("#000000"));
            holder.result.setTextColor(Color.parseColor("#287DFA"));
        }
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return list.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView content ;
        TextView result ;
        TextView date ;

        public ViewHolder(View itemView) {
            super(itemView) ;
            // 뷰 객체에 대한 참조. (hold strong reference)
            content = itemView.findViewById(R.id.item2_content);
            result = itemView.findViewById(R.id.item2_title);
            date = itemView.findViewById(R.id.item2_date);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        // 데이터 리스트로부터 아이템 데이터 참조.
                        TreatListVO item = list.get(pos) ;
                        ItemListDialogFragment2 fragment = new ItemListDialogFragment2(item, id);
                        fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle);
                        fragment.show(fragmentManager, "custom_dialog");
                    }
                }
            });
        }
    }
}