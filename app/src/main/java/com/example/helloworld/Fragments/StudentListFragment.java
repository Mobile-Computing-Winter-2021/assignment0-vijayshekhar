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
import com.example.helloworld.adaptors.StudentListRecyclerAdapter;

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

        recyclerView.setAdapter(new StudentListRecyclerAdapter(initData()));

        return view;
    }

    private List<Students> initData() {

        itemList=new ArrayList<>();
        itemList.add(new Students("student 1","video branch 1","rollno 1"));
        itemList.add(new Students("student 2","video branch 2","rollno 2"));
        itemList.add(new Students("student 3","video branch 3","rollno 3"));
        itemList.add(new Students("student 4","video branch 4","rollno 4"));
        itemList.add(new Students("student 5","video branch 5","rollno 5"));
        itemList.add(new Students("student 6","video branch 6","rollno 6"));
        itemList.add(new Students("student 7","video branch 7","rollno 7"));
        itemList.add(new Students("student 8","video branch 8","rollno 8"));
        itemList.add(new Students("student 9","video branch 9","rollno 9"));
        itemList.add(new Students("student 10","video branch 10","rollno 10"));
        itemList.add(new Students("student 11","video branch 11","rollno 11"));
        itemList.add(new Students("student 12","video branch 12","rollno 12"));
        itemList.add(new Students("student 13","video branch 13","rollno 13"));
        itemList.add(new Students("student 14","video branch 14","rollno 14"));
        itemList.add(new Students("student 15","video branch 15","rollno 15"));
        itemList.add(new Students("student 16","video branch 16","rollno 16"));
        itemList.add(new Students("student 17","video branch 17","rollno 17"));
        itemList.add(new Students("student 18","video branch 18","rollno 18"));
        itemList.add(new Students("student 19","video branch 19","rollno 19"));
        itemList.add(new Students("student 20","video branch 20","rollno 20"));
        itemList.add(new Students("student 21","video branch 21","rollno 21"));
        itemList.add(new Students("student 22","video branch 22","rollno 22"));
        itemList.add(new Students("student 23","video branch 23","rollno 23"));
        itemList.add(new Students("student 24","video branch 24","rollno 24"));
        itemList.add(new Students("student 25","video branch 25","rollno 25"));
        itemList.add(new Students("student 26","video branch 26","rollno 26"));
        itemList.add(new Students("student 27","video branch 27","rollno 27"));
        itemList.add(new Students("student 28","video branch 28","rollno 28"));
        itemList.add(new Students("student 29","video branch 29","rollno 29"));
        itemList.add(new Students("student 30","video branch 30","rollno 30"));



        return itemList;
    }



    }
