package com.example.helloworld.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.Models.Entities.AccelerometerEntity;
import com.example.helloworld.Models.Entities.GPSEntity;
import com.example.helloworld.Models.Entities.LightEntity;
import com.example.helloworld.Models.Entities.Linear_accelerationEntity;
import com.example.helloworld.Models.Entities.ProximityEntity;
import com.example.helloworld.Models.Entities.TemperatureEntity;
import com.example.helloworld.Models.SensorDatabase;
import com.example.helloworld.R;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SensorEventListener, LocationListener {

    private SensorManager sensorManager;
    private Sensor sAccelerometer, slinearAcc, sLight, sTemperature, sProximity;
    Boolean bacc, bgps, blinacc, blight, btemp, bprox = false;
    SensorDatabase sensorDatabase;
    Long last_time=  System.currentTimeMillis();

    float x_last = 0.0f, y_last =0.0f, z_last = 0.0f;

    private static final String TAG = "SensorActivity";

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SwitchCompat accSwitch, gpsSwitch, linearSwitch, lightSwitch, tempSwitch, proximitySwitch;
        Button tempavg, accavg;
        TextView accview, tempview;

        // sensors initialisation
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        slinearAcc = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        // switches initialisation
        accSwitch = findViewById(R.id.accs);
        gpsSwitch = findViewById(R.id.gpss);
        linearSwitch = findViewById(R.id.lin_accs);
        lightSwitch = findViewById(R.id.lights);
        tempSwitch = findViewById(R.id.temps);
        proximitySwitch = findViewById(R.id.proxs);

        // button initialisation
        tempavg = (Button) findViewById(R.id.temp_avg_button);
        accavg = (Button) findViewById(R.id.acc_avg_button);

        // textview initialisation
        accview = (TextView) findViewById(R.id.acc_result_textview);
        tempview = (TextView) findViewById(R.id.temp_result_textview);

        //Database initialisation
        initDb();

        // location listener initialisation
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        //accelerometer switch listener
        accSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                bacc = true;

                sensorManager.registerListener(this, sAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                bacc = false;
                sensorManager.unregisterListener(this);
            }
        });

        // linear acceleration switch listener
        linearSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                blinacc = true;
                sensorManager.registerListener(this, slinearAcc, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                blinacc = false;
                sensorManager.unregisterListener(this);
            }
        });

        // gps sensor switch listener
        gpsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                bgps = true;

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0.0f, this);
                Log.d(TAG, "onCreate: location service granted");
            }
            else{
                bgps = false;
                locationManager.removeUpdates(this);
                Log.d(TAG, "onCreate: location service removed");
            }
        });

        // light sensor switch listener
        lightSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(isChecked){
                blight = true;
                sensorManager.registerListener(this,sLight,SensorManager.SENSOR_DELAY_NORMAL);
            }
            else{
                blight = false;
                sensorManager.unregisterListener(this);
            }
        });

        // temperature switch listener
        tempSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(isChecked){
                btemp = true;
                sensorManager.registerListener(this,sTemperature,SensorManager.SENSOR_DELAY_NORMAL);
//                Toast.makeText(this, "temp checked on", Toast.LENGTH_SHORT).show();
            }
            else{
                btemp = false;
                sensorManager.unregisterListener(this);
            }
        });

        //proximity switch listener
        proximitySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(isChecked){
                bprox = true;
                sensorManager.registerListener(this,sProximity,SensorManager.SENSOR_DELAY_NORMAL);
            }
            else{
                bprox = false;
                sensorManager.unregisterListener(this);
            }
        });

        accavg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<AccelerometerEntity> accData = sensorDatabase.dao().getAccData();
//                List<AccelerometerEntity> accData = sensorDatabase.dao().getAccDataPast1Hour(curTime);

                Long curTime = System.currentTimeMillis() - 3600000;
                RadioButton axesRadio;
                RadioGroup radioGroup = findViewById(R.id.radioGroup);

                int selectedId = radioGroup.getCheckedRadioButtonId();
                axesRadio = (RadioButton) findViewById(selectedId);
                try {
                    String s = axesRadio.getText().toString();
                    float avg = 0.0f;
                    // if along x axis
                    if(s.equalsIgnoreCase("X axis")){
                        avg = sensorDatabase.dao().getAccDataGenX(curTime);
                        accview.setText(String.valueOf( roundFloat(avg))+" X axis");
                    }

                    // if along y axis
                    if(s.equalsIgnoreCase("Y axis")){
                        avg = sensorDatabase.dao().getAccDataGenY(curTime);
                        accview.setText(String.valueOf( roundFloat(avg))+" Y axis");
                    }

                    // if along z axis
                    if(s.equalsIgnoreCase("Z axis")){
                        avg = sensorDatabase.dao().getAccDataGenZ(curTime);
                        accview.setText(String.valueOf( roundFloat(avg))+" Z axis");
                    }

                    // if none selected
                    if (selectedId == -1 || s.equalsIgnoreCase("All Axes")) {
                        float xavg = sensorDatabase.dao().getAccDataGenX(curTime);
                        float yavg = sensorDatabase.dao().getAccDataGenY(curTime);
                        float zavg = sensorDatabase.dao().getAccDataGenZ(curTime);
                        float res = roundFloat((xavg+ yavg+ zavg) /3);
                        accview.setText(String.valueOf( res)+"  all axes");
                    }
                }catch (NullPointerException e){

                    Toast.makeText(MainActivity.this, "Select at least one axis!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tempavg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long curTime = System.currentTimeMillis() - 3600000;
//                List<TemperatureEntity> temperatureEntityList = sensorDatabase.dao().getTempData();
//                List<TemperatureEntity> temperatureEntityList = sensorDatabase.dao().getTempDataPast1Hour(curTime);
                float avg = sensorDatabase.dao().getTempDataGen(curTime);

                tempview.setText(String.valueOf(roundFloat(avg)));

            }
        });


    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;

        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER && bacc){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            motionDetection(x,y,z);
            x = roundFloat(x);
            y = roundFloat(y);
            z = roundFloat(z);

            Log.d(TAG, "onSensorChanged: Accelerometer"+"x="+x+", y="+y+", z="+z);
            AccelerometerEntity accelerometerEntity = new AccelerometerEntity(x,y,z,System.currentTimeMillis());
            sensorDatabase.dao().accDataInsert(accelerometerEntity);

        }

        if(sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION && blinacc){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            x = roundFloat(x);
            y = roundFloat(y);
            z = roundFloat(z);

            Log.d(TAG, "onSensorChanged: Linear-acceleration"+"x="+x+", y="+y+", z="+z);
            Linear_accelerationEntity linear_accelerationEntity = new Linear_accelerationEntity(x,y,z,System.currentTimeMillis());
            sensorDatabase.dao().linaccDataInsert(linear_accelerationEntity);

        }

        if(sensor.getType() == Sensor.TYPE_LIGHT && blight){
            float x = event.values[0];
            Log.d(TAG, "onSensorChanged: Light value= "+x);
            LightEntity lightEntity = new LightEntity(x,System.currentTimeMillis());
            sensorDatabase.dao().lightDataInsert(lightEntity);

        }

        if(sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE && btemp){
            float x = event.values[0];

            Log.d(TAG, "onSensorChanged: Temperature= "+x);
            TemperatureEntity temperatureEntity = new TemperatureEntity(x, System.currentTimeMillis());
            sensorDatabase.dao().tempDataInsert(temperatureEntity);
        }

        if(sensor.getType() == Sensor.TYPE_PROXIMITY && bprox){
            float x = event.values[0];
            Log.d(TAG, "onSensorChanged: Proximity=  "+x);
            ProximityEntity proximityEntity = new ProximityEntity(x, System.currentTimeMillis());
            sensorDatabase.dao().proxDataInsert(proximityEntity);
        }


    }

    private void motionDetection(float x,float y,float z) {
        float present_sum = x+y+z;
        TextView tv = findViewById(R.id.motion_status);
        Long time_present = System.currentTimeMillis();

        if ((time_present - last_time) > 1000){
            long timeDiff = time_present - last_time;
            last_time = time_present;

            float velocity = (present_sum - x_last - y_last -z_last)/timeDiff * 6000;
            if (velocity > 0.5){
                Log.d(TAG, "motionDetection: Moving !!");
                tv.setText("Moving");
                tv.setTextColor(Color.parseColor("#FF03DAC5"));
//                Toast.makeText(this,"Moving", Toast.LENGTH_SHORT).show();
            }
            else{
                Log.d(TAG, "motionDetection: Stationary !!");
                tv.setText("Stationary !!");
                tv.setTextColor(Color.parseColor("#FF0000"));
//                Toast.makeText(this,"Stationary !!", Toast.LENGTH_SHORT).show();
            }

            x_last = x;
            y_last = y;
            z_last = z;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void initDb(){
        sensorDatabase = Room.databaseBuilder(MainActivity.this , SensorDatabase.class , "SensorDB")
                .allowMainThreadQueries().build();
    }

    float roundFloat(float d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#.####");
        return Float.valueOf(twoDForm.format(d));
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        float longi = (float) location.getLongitude();
        float lat = (float) location.getLatitude();

        Log.d(TAG, "onLocationChanged: "+"long= "+longi+"  latitude= "+lat);
        GPSEntity gpsEntity = new GPSEntity(longi, lat ,System.currentTimeMillis());
        sensorDatabase.dao().gpsDataInsert(gpsEntity);
    }
}