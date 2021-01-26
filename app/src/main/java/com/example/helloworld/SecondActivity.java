package com.example.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    Button checkbt;
    CheckBox c1,c2,c3,c4,c5;
    EditText et;
    boolean msg;
    private boolean safe;
    boolean c1check, c2check, c3check, c4check, c5check;
    private final static String DEBUG_TAG="Second Activity";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


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
}
