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

import com.example.helloworld.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
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

        ArrayList<String > lableName = new ArrayList<>();

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
//                    str = str + "ap"+(i+1)+" ";
                    lableName.add(res.SSID);
                    i++;
                }


                BarDataSet barDataSet = new BarDataSet(visitor,"Wifi access points");
                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                barDataSet.setValueTextColor(Color.BLACK);
                barDataSet.setValueTextSize(10f);

                BarData barData = new BarData(barDataSet);
                graph.setFitBars(true);
                graph.setData(barData);

                XAxis xAxis = graph.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(lableName));
                xAxis.setDrawGridLines(false);
                xAxis.setDrawAxisLine(false);
                xAxis.setGranularity(1f);
                xAxis.setLabelCount(lableName.size());
                xAxis.setLabelRotationAngle(270);



                graph.getDescription().setText("Wifi RSS");
                graph.animateY(2000);
                graph.invalidate();
            }
        }catch (Exception e){
            Toast.makeText(this, "scan results not arrived", Toast.LENGTH_SHORT).show();
        }



    }
}