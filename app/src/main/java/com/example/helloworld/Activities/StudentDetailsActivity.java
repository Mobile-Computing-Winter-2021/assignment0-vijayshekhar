package com.example.helloworld.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.helloworld.Fragments.StudentDetailsFragment;
import com.example.helloworld.R;

public class StudentDetailsActivity extends AppCompatActivity {
    private static final String TAG = "StudentDetailsActivity";
    
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: reached!!!");
        setContentView(R.layout.activity_student_details);

        // retrieving the position of the clicked item
//        String pos = getIntent().getStringExtra("clicked index");
//        Log.d(TAG, "index received = "+pos);

        String name1 = getIntent().getStringExtra("name") ;
        String branch1 = getIntent().getStringExtra("branch") ;
        String rollno1 = getIntent().getStringExtra("rollno") ;
        
        loadFragment();
    }




    private void loadFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detailsContainer, new StudentDetailsFragment());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        Log.d(TAG, "loadFragment: reached and executed");
    }
}
