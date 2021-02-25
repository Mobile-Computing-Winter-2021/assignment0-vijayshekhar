package com.example.helloworld.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

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
    private int reqKey = 0;
    Students temp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        View view = inflater.inflate(R.layout.student_list_fragment, container, false);

        recyclerView = view.findViewById(R.id.container_recycle);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //TODO  Modify this new StudentListRecyclerAdapter(initData(), this)
        recyclerView.setAdapter(new StudentListRecyclerAdapter(populateRecyclerView(), this));

        Log.d(TAG, "onCreateView: reached");

        return view;

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

//        startActivity(intent);
        startActivityForResult(intent, reqKey);
        temp = student;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == reqKey) {
            if (data == null) {
                return;
            }


            temp.setName1(data.getStringExtra("name update"));
            temp.setBranch1(data.getStringExtra("branch update"));

            Log.d(TAG, "onActivityResult: " + temp.getBranch1() + " " + temp.getName1() + " " + temp.getRollno1());


            for (Students s : itemList) {
                if (s.getRollno1() != null && s.getRollno1().contains(temp.getRollno1())) {
                    s.setName1(temp.getName1());
                    s.setBranch1(temp.getBranch1());
                }
            }
        }

        Log.d(TAG, "onActivityResult: setAdapter called");
        recyclerView.setAdapter(new StudentListRecyclerAdapter(itemList, this));

    }

    private List<Students> populateRecyclerView() {

        itemList = new ArrayList<>();
        int i =0;
        String t1;
        for(i =0 ; i<31; i++){
            t1 = String.valueOf(i);
            itemList.add(new Students("student "+t1, "branch "+t1, "rollno "+t1));

        }

        return itemList;
    }

}
