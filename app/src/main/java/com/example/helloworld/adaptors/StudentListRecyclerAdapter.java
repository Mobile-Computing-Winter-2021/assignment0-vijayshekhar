package com.example.helloworld.adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.R;
import com.example.helloworld.Models.Students;


import java.util.List;

public class StudentListRecyclerAdapter extends RecyclerView.Adapter<StudentListRecyclerAdapter.ViewHolder> {

    List<Students> itemList1;
    private OnDetailsListener onDetailsListener1;


    public StudentListRecyclerAdapter(List<Students> itemList, OnDetailsListener onDetailsListener)  {

        this.itemList1 = itemList;
        this.onDetailsListener1 = onDetailsListener;
    }

    @NonNull
    @Override
    public StudentListRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items,parent,false);
        ViewHolder viewHolder = new ViewHolder(view, onDetailsListener1);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentListRecyclerAdapter.ViewHolder holder, int position) {

        holder.nameText.setText(itemList1.get(position).getName1());
        holder.rollnoText.setText(itemList1.get(position).getRollno1());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDetailsListener1.onDetailsClick(itemList1.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList1.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView nameText;
        TextView rollnoText;

        OnDetailsListener onDetailsListener;

        public ViewHolder(@NonNull View itemView , OnDetailsListener onDetailsListener) {
            super(itemView);

            nameText = itemView.findViewById(R.id.name_tv);
            rollnoText = itemView.findViewById(R.id.rollno_tv);
            this.onDetailsListener = onDetailsListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {


        }
    }

    public interface OnDetailsListener{


        void onDetailsClick(Students s1  );

    }

}
