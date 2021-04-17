package com.example.helloworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    List<Model> itemList1;
    private Context context;

    public ItemAdapter(List<Model> itemList,Context context) {

        this.itemList1=itemList;
        this.context=context;

    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, final int position) {

        holder.itemImage.setImageResource(itemList1.get(position).getImage());
        holder.itemtxt1.setText("SSID: "+itemList1.get(position).getSsid());
        holder.itemtxt2.setText("BSSID: "+itemList1.get(position).getBssid());
        holder.itemtxt3.setText("RSS: "+itemList1.get(position).getRss());

    }

    @Override
    public int getItemCount() {
        return itemList1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView itemtxt1,itemtxt2,itemtxt3;
        LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage=itemView.findViewById(R.id.itemImg);
            itemtxt1=itemView.findViewById(R.id.itemssid);
            itemtxt2=itemView.findViewById(R.id.itembssid);
            itemtxt3=itemView.findViewById(R.id.itemrssi);
            linearLayout=itemView.findViewById(R.id.layout_id);

        }
    }
}