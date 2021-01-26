package com.example.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "Main Activity" ;
    Button submitbt, clearbt;
    EditText nameet;
    CheckBox c1,c2,c3,c4,c5;

    private int reqKey =0;
    private boolean checkClicked=false;

    private String presentState="Activity launched";
    private String prevstate="Activity launched";

    // on create state
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView safetv;


//        ------------------------------------------------------------------------------------------
        presentState="onCreate state.";
//        creating logs and state transition toast
        Log.d(DEBUG_TAG,prevstate+" ---> "+ presentState);
        Toast.makeText(MainActivity.this, prevstate+" ---> "+ presentState, Toast.LENGTH_SHORT).show();

        prevstate = "onCreate state.";
//----------------------------------------------------------------------------------------------------


//        if(savedInstanceState != null){
//
//           safetv = (TextView)findViewById(R.id.safetv);
//           boolean res = savedInstanceState.getBoolean("safe_status");
//            if(res){
//
//                safetv.setTextColor(Color.parseColor("lightGreen"));
//                safetv.setText("You are safe!!!");
//            }
//            else{
//                safetv.setTextColor(Color.parseColor("red"));
//                safetv.setText("You are NOT SAFE!!!");
//            }
//
//        }


        setContentView(R.layout.activity_main);

        submitbt = (Button)findViewById(R.id.submit_button);
        clearbt = (Button)findViewById(R.id.clear_button);
        nameet = (EditText)findViewById(R.id.name);
        c1 = (CheckBox)findViewById(R.id.prec1);
        c2 = (CheckBox)findViewById(R.id.prec2);
        c3 = (CheckBox)findViewById(R.id.prec3);
        c4 = (CheckBox)findViewById(R.id.prec4);
        c5 = (CheckBox)findViewById(R.id.prec5);
        safetv = (TextView)findViewById(R.id.safetv);

//        click listener for submit button
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

//        TextView safetv;
//        safetv = (TextView)findViewById(R.id.safetv);

        if(resultCode!= Activity.RESULT_OK)
            return;
        if(requestCode==reqKey){
            if(data==null){
                return;
            }
            checkClicked= data.getBooleanExtra("safe_msg",false);

            if(checkClicked){
                Toast.makeText(MainActivity.this, "You are safe!!!", Toast.LENGTH_LONG).show();

//                safetv.setTextColor(Color.parseColor("lightGreen"));
//                safetv.setText("You are safe!!!");
            }
            else{
                Toast.makeText(MainActivity.this, "You are NOT SAFE!!!", Toast.LENGTH_LONG).show();
//                safetv.setTextColor(Color.parseColor("red"));
//                safetv.setText("You are NOT SAFE!!!");
            }

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("safe_status" , checkClicked);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presentState="onStart state.";
//        creating logs and state transition toast
        Log.d(DEBUG_TAG,prevstate+" ---> "+ presentState);
        Toast.makeText(MainActivity.this, prevstate+" ---> "+ presentState, Toast.LENGTH_SHORT).show();

        prevstate = "onStart state.";
    }

    @Override
    protected void onResume() {
        super.onResume();
        presentState="onResume state.";
//        creating logs and state transition toast
        Log.d(DEBUG_TAG,prevstate+" ---> "+ presentState);
        Toast.makeText(MainActivity.this, prevstate+" ---> "+ presentState, Toast.LENGTH_SHORT).show();

        prevstate = "onResume state.";
    }

    @Override
    protected void onPause() {
        super.onPause();
        presentState="onPause state.";
//        creating logs and state transition toast
        Log.d(DEBUG_TAG,prevstate+" ---> "+ presentState);
        Toast.makeText(MainActivity.this, prevstate+" ---> "+ presentState, Toast.LENGTH_SHORT).show();

        prevstate = "onPause state.";
    }

    @Override
    protected void onStop() {
        super.onStop();
        presentState="onStop state.";
//        creating logs and state transition toast
        Log.d(DEBUG_TAG,prevstate+" ---> "+ presentState);
        Toast.makeText(MainActivity.this, prevstate+" ---> "+ presentState, Toast.LENGTH_SHORT).show();

        prevstate = "onStop state.";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presentState="onDestroy state.";
//        creating logs and state transition toast
        Log.d(DEBUG_TAG,prevstate+" ---> "+ presentState);
        Toast.makeText(MainActivity.this, prevstate+" ---> "+ presentState, Toast.LENGTH_SHORT).show();

        prevstate = "onDestroy state.";
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        presentState="onRestart state.";
//        creating logs and state transition toast
        Log.d(DEBUG_TAG,prevstate+" ---> "+ presentState);
        Toast.makeText(MainActivity.this, prevstate+" ---> "+ presentState, Toast.LENGTH_SHORT).show();

        prevstate = "onRestart state.";
    }
}
