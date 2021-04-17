package com.example.helloworld.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helloworld.Fragments.ListFragment;
import com.example.helloworld.R;

public class listActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.MainContainer,new ListFragment()).commit();
    }
}
