package com.example.helloworld.adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.R;
import com.example.helloworld.Students;


import java.util.List;

public class StudentListRecyclerAdapter extends RecyclerView.Adapter<StudentListRecyclerAdapter.ViewHolder> {

    List<Students> itemList1;
    public StudentListRecyclerAdapter(List<Students> itemList)  {

        this.itemList1 = itemList;
    }

    @NonNull
    @Override
    public StudentListRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentListRecyclerAdapter.ViewHolder holder, int position) {

        holder.nameText.setText(itemList1.get(position).getName1());
        holder.rollnoText.setText(itemList1.get(position).getRollno1());

    }

    @Override
    public int getItemCount() {
        return itemList1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameText;
        TextView rollnoText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.name_tv);
            rollnoText = itemView.findViewById(R.id.rollno_tv);


        }
    }
}
