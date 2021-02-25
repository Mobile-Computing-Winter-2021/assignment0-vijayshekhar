package com.example.helloworld.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.helloworld.R;

public class StudentDetailsFragment extends Fragment {

    private static final String TAG = "StudentDetailsFragment";

    //data variables
    String name1;
    String branch1;
    String rollno1;
    private Boolean update = false;



    EditText rollnoet, nameet , branchet;
    Button submitbt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.student_details_fragment, container , false);


        return view ;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent intent = getActivity().getIntent();
        // Receiving intent data
        name1 = getActivity().getIntent().getStringExtra("name") ;
        branch1 = getActivity().getIntent().getStringExtra("branch") ;
        rollno1 = getActivity().getIntent().getStringExtra("rollno") ;



        Log.d(TAG, "onCreateView: reached -------"+name1 + " "+branch1+" "+rollno1);

        // referencing the widgets
        rollnoet = view.findViewById(R.id.rollno_et);
        nameet = view.findViewById(R.id.name_et);
        branchet = view.findViewById(R.id.branch_et);
        submitbt = view.findViewById(R.id.submit_bt);


        //setting the selected details of student
        rollnoet.setText(rollno1);
        nameet.setText(name1);
        branchet.setText(branch1);

        submitbt.setOnClickListener(v ->{
            Log.d(TAG, "onViewCreated , submit clicked ");
            update = Boolean.TRUE;

            //getting user input (updation)
            String name2 = nameet.getText().toString();
            String branch2 = branchet.getText().toString();

            // intent for result
            Intent update_intent = new Intent();



            if(update){
                update_intent.putExtra("name update", nameet.getText().toString());
                update_intent.putExtra("branch update", branchet.getText().toString());
                update_intent.putExtra("rollno", rollno1);

            }
            else{
                update_intent.putExtra("name update", name1);
                update_intent.putExtra("branch update", branch1);
                update_intent.putExtra("rollno", rollno1);
            }

            getActivity().setResult(Activity.RESULT_OK, update_intent);

        });


    }

}
