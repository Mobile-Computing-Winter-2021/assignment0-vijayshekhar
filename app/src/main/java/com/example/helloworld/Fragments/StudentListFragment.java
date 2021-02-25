package com.example.helloworld.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.helloworld.R;
import com.example.helloworld.Students;
import com.example.helloworld.adaptors.ItemAdaptor;

import java.util.ArrayList;
import java.util.List;


public class StudentListFragment extends Fragment {


    public StudentListFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    List<Students> itemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);


        View view=inflater.inflate(R.layout.student_list_fragment, container, false);

        recyclerView=view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        initData();

        recyclerView.setAdapter(new ItemAdaptor(initData()));



        return view;
    }

    private List<Students> initData() {

        itemList=new ArrayList<>();
        itemList.add(new Students(R.drawable.ic_launcher_background,"video 1"));
        itemList.add(new Students(R.drawable.ic_launcher_background,"video 2"));
        itemList.add(new Students(R.drawable.ic_launcher_background,"video 3"));
        itemList.add(new Students(R.drawable.ic_launcher_background,"video 4"));
        itemList.add(new Students(R.drawable.ic_launcher_background,"video 5"));


        return itemList;
    }



    }
