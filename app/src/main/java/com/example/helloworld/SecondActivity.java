package com.example.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    Button checkbt;
    CheckBox c1,c2,c3,c4,c5;
    EditText et;
    boolean msg;
    private boolean safe;
    boolean c1check, c2check, c3check, c4check, c5check;
    private final static String DEBUG_TAG="Second Activity";
    private String presentState="none";
    private String prevstate="none";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        presentState="onCreate state.";
//        creating logs and state transition toast
        Log.d(DEBUG_TAG,prevstate+" ---> "+ presentState);
        Toast.makeText(SecondActivity.this, prevstate+" ---> "+ presentState, Toast.LENGTH_SHORT).show();

        prevstate = "onCreate state.";


//        initializing checkboxes
        checkbt = (Button)findViewById(R.id.check_button);
        c1 = (CheckBox)findViewById(R.id.prec1);
        c2 = (CheckBox)findViewById(R.id.prec2);
        c3 = (CheckBox)findViewById(R.id.prec3);
        c4 = (CheckBox)findViewById(R.id.prec4);
        c5 = (CheckBox)findViewById(R.id.prec5);

        et = (EditText)findViewById(R.id.namereg);


//        retrieving checkbox values from previous activity

        c1check = this.getIntent().getBooleanExtra("c1", false);
        c2check = this.getIntent().getBooleanExtra("c2", false);
        c3check = this.getIntent().getBooleanExtra("c3", false);
        c4check = this.getIntent().getBooleanExtra("c4", false);
        c5check = this.getIntent().getBooleanExtra("c5", false);

        String namereg = this.getIntent().getStringExtra("user name");

//      assigning checked value to the checkboxes in another activity.
        c1.setChecked(c1check);
        c2.setChecked(c2check);
        c3.setChecked(c3check);
        c4.setChecked(c4check);
        c5.setChecked(c5check);

        et.setText(namereg);


    }



    public void checkStatus(View v){
        safe = c1check && c2check && c3check && c4check && c5check;

                if(safe) {
                    msg = true;
                }
                else{
                    msg = false;
                }

        Log.d(DEBUG_TAG, "you have been checked");
        Intent results = new Intent();
        results.putExtra("safe_msg" , msg);
        setResult(Activity.RESULT_OK,results);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presentState="onStart state.";
//        creating logs and state transition toast
        Log.d(DEBUG_TAG,prevstate+" ---> "+ presentState);
        Toast.makeText(SecondActivity.this, prevstate+" ---> "+ presentState, Toast.LENGTH_SHORT).show();

        prevstate = "onStart state.";
    }

    @Override
    protected void onResume() {
        super.onResume();
        presentState="onResume state.";
//        creating logs and state transition toast
        Log.d(DEBUG_TAG,prevstate+" ---> "+ presentState);
        Toast.makeText(SecondActivity.this, prevstate+" ---> "+ presentState, Toast.LENGTH_SHORT).show();

        prevstate = "onResume state.";
    }

    @Override
    protected void onPause() {
        super.onPause();
        presentState="onPause state.";
//        creating logs and state transition toast
        Log.d(DEBUG_TAG,prevstate+" ---> "+ presentState);
        Toast.makeText(SecondActivity.this, prevstate+" ---> "+ presentState, Toast.LENGTH_SHORT).show();

        prevstate = "onPause state.";
    }

    @Override
    protected void onStop() {
        super.onStop();
        presentState="onStop state.";
//        creating logs and state transition toast
        Log.d(DEBUG_TAG,prevstate+" ---> "+ presentState);
        Toast.makeText(SecondActivity.this, prevstate+" ---> "+ presentState, Toast.LENGTH_SHORT).show();

        prevstate = "onStop state.";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presentState="onDestroy state.";
//        creating logs and state transition toast
        Log.d(DEBUG_TAG,prevstate+" ---> "+ presentState);
        Toast.makeText(SecondActivity.this, prevstate+" ---> "+ presentState, Toast.LENGTH_SHORT).show();

        prevstate = "onDestroy state.";
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        presentState="onRestart state.";
//        creating logs and state transition toast
        Log.d(DEBUG_TAG,prevstate+" ---> "+ presentState);
        Toast.makeText(SecondActivity.this, prevstate+" ---> "+ presentState, Toast.LENGTH_SHORT).show();

        prevstate = "onRestart state.";
    }


}
