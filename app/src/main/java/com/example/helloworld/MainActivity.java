package com.example.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button submitbt, clearbt;
    EditText nameet;
    CheckBox c1,c2,c3,c4,c5;

    private int reqKey =0;
    private boolean checkClicked=false;

    // on create state
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        submitbt = (Button)findViewById(R.id.submit_button);
        clearbt = (Button)findViewById(R.id.clear_button);
        nameet = (EditText)findViewById(R.id.name);
        c1 = (CheckBox)findViewById(R.id.prec1);
        c2 = (CheckBox)findViewById(R.id.prec2);
        c3 = (CheckBox)findViewById(R.id.prec3);
        c4 = (CheckBox)findViewById(R.id.prec4);
        c5 = (CheckBox)findViewById(R.id.prec5);




        submitbt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String msg = nameet.getText().toString();
                Intent intent = new Intent(MainActivity.this , SecondActivity.class);
                intent.putExtra("user name",msg);

                boolean c1check, c2check, c3check, c4check, c5check;
                c1check = c1.isChecked();
                c2check = c2.isChecked();
                c3check = c3.isChecked();
                c4check = c4.isChecked();
                c5check = c5.isChecked();


                intent.putExtra("c1" , c1check);
                intent.putExtra("c2" , c2check);
                intent.putExtra("c3" , c3check);
                intent.putExtra("c4" , c4check);
                intent.putExtra("c5" , c5check);

//                String s1=String.valueOf(c1check);
//                String s2=String.valueOf(c2check);
//                String s3=String.valueOf(c3check);
//                String s4=String.valueOf(c4check);
//                String s5=String.valueOf(c5check);

//                Toast.makeText(MainActivity.this, s1+" "+s3+" "+s4+" "+s5 , Toast.LENGTH_SHORT).show();

                startActivityForResult(intent , reqKey);
            }
        });

        clearbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c1.setChecked(false);
                c2.setChecked(false);
                c3.setChecked(false);
                c4.setChecked(false);
                c5.setChecked(false);
                nameet.setText("");
                nameet.setHint("Enter your name");

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode!= Activity.RESULT_OK)
            return;
        if(requestCode==reqKey){
            if(data==null){
                return;
            }
            checkClicked= data.getBooleanExtra("safe_msg",false);

            if(checkClicked){
                Toast.makeText(MainActivity.this, "You are safe!!!", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(MainActivity.this, "You are NOT SAFE!!!", Toast.LENGTH_LONG).show();
            }

        }

    }


}
