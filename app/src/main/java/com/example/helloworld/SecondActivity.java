package com.example.helloworld;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        String str = getIntent().getExtras().getString("message");

        Toast tost = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG);
        tost.show();

    }
}
