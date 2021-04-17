package com.example.helloworld.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helloworld.Fragments.ListFragment;
import com.example.helloworld.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class graphActivity extends AppCompatActivity {

    private static final String TAG = "graphActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_activity);



        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("listresult");
        List<ScanResult> scanlist = (List<ScanResult>) args.getSerializable("scanlist");



        try {
            if (scanlist.size() > 0){



                BarChart graph = findViewById(R.id.graph);
                ArrayList<BarEntry> visitor = new ArrayList<>();

                int i=0;
                String str = "";
                for (ScanResult res : scanlist) {

                    Log.d(TAG, " SSID  =  " + res.SSID);
                    Log.d(TAG, " BSSID  =  " + res.BSSID);
                    Log.d(TAG, " level (RSS)  =  " + (res.level + 100));
                    Log.d(TAG, "\n \n");

                    visitor.add(new BarEntry(i, (res.level + 100) ));
                    str = str + "ap"+(i+1)+" ";
                    i++;
                }


                BarDataSet barDataSet = new BarDataSet(visitor,str);
                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                barDataSet.setValueTextColor(Color.BLACK);
                barDataSet.setValueTextSize(10f);

                BarData barData = new BarData(barDataSet);
                graph.setFitBars(true);
                graph.setData(barData);

                XAxis xAxis = graph.getXAxis();



                graph.getDescription().setText("wifi data");
                graph.animateY(2000);
            }
        }catch (Exception e){
            Toast.makeText(this, "scan results not arrived", Toast.LENGTH_SHORT).show();
        }



    }
}