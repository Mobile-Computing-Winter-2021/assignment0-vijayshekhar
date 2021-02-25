package com.example.helloworld.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.helloworld.Activities.StudentDetailsActivity;
import com.example.helloworld.R;
import com.example.helloworld.Models.Students;
import com.example.helloworld.adaptors.StudentListRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;


public class StudentListFragment extends Fragment implements StudentListRecyclerAdapter.OnDetailsListener {


    public StudentListFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    List<Students> itemList;
    private static final String TAG = "StudentListFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);


        View view=inflater.inflate(R.layout.student_list_fragment, container, false);

        recyclerView=view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        initData();
        //TODO  Modify this new StudentListRecyclerAdapter(initData(), this)
        recyclerView.setAdapter(new StudentListRecyclerAdapter(initData(), this));

        return view;
    }

    private List<Students> initData() {

        itemList=new ArrayList<>();
        itemList.add(new Students("student 1","branch 1","rollno 1"));
        itemList.add(new Students("student 2"," branch 2","rollno 2"));
        itemList.add(new Students("student 3"," branch 3","rollno 3"));
        itemList.add(new Students("student 4"," branch 4","rollno 4"));
        itemList.add(new Students("student 5"," branch 5","rollno 5"));
        itemList.add(new Students("student 6"," branch 6","rollno 6"));
        itemList.add(new Students("student 7"," branch 7","rollno 7"));
        itemList.add(new Students("student 8"," branch 8","rollno 8"));
        itemList.add(new Students("student 9"," branch 9","rollno 9"));
        itemList.add(new Students("student 10"," branch 10","rollno 10"));
        itemList.add(new Students("student 11"," branch 11","rollno 11"));
        itemList.add(new Students("student 12"," branch 12","rollno 12"));
        itemList.add(new Students("student 13"," branch 13","rollno 13"));
        itemList.add(new Students("student 14"," branch 14","rollno 14"));
        itemList.add(new Students("student 15"," branch 15","rollno 15"));
        itemList.add(new Students("student 16"," branch 16","rollno 16"));
        itemList.add(new Students("student 17"," branch 17","rollno 17"));
        itemList.add(new Students("student 18"," branch 18","rollno 18"));
        itemList.add(new Students("student 19"," branch 19","rollno 19"));
        itemList.add(new Students("student 20"," branch 20","rollno 20"));
        itemList.add(new Students("student 21"," branch 21","rollno 21"));
        itemList.add(new Students("student 22"," branch 22","rollno 22"));
        itemList.add(new Students("student 23"," branch 23","rollno 23"));
        itemList.add(new Students("student 24"," branch 24","rollno 24"));
        itemList.add(new Students("student 25"," branch 25","rollno 25"));
        itemList.add(new Students("student 26"," branch 26","rollno 26"));
        itemList.add(new Students("student 27"," branch 27","rollno 27"));
        itemList.add(new Students("student 28"," branch 28","rollno 28"));
        itemList.add(new Students("student 29"," branch 29","rollno 29"));
        itemList.add(new Students("student 30"," branch 30","rollno 30"));



        return itemList;
    }


    @Override
    public void onDetailsClick(Students student) {


        Log.d(TAG, "onDetailsClicked: !!!");

//        Log.d(TAG, "clicked index details:  "+ itemList.get(student));
        Intent intent = new Intent(getContext(), StudentDetailsActivity.class);

//        intent.putExtra("clicked index", String.valueOf(itemList.get()));
        intent.putExtra("name", student.getName1());
        intent.putExtra("branch", student.getBranch1());
        intent.putExtra("rollno", student.getRollno1());

        startActivity(intent);

    }
}
