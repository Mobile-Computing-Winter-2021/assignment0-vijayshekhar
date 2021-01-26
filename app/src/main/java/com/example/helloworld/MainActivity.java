package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button submitbt, clearbt;
    EditText nameet;

    // on create state
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submitbt = (Button)findViewById(R.id.submit_button);
        nameet = (EditText)findViewById(R.id.name);

        submitbt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String msg = nameet.getText().toString();
                Intent intent = new Intent(MainActivity.this , SecondActivity.class);
                intent.putExtra("message",msg);
                startActivity(intent);
            }
        });
    }
}
