package com.example.helloworld.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
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
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener, LocationListener {

    private SensorManager sensorManager;
    private Sensor sAccelerometer, sGPS, slinearAcc, sLight, sTemperature, sProximity;
    Boolean bacc, bgps, blinacc, blight, btemp, bprox = false;
    SensorDatabase sensorDatabase;

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
                sensorManager.registerListener(this, sAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
            } else {
                bacc = false;
                sensorManager.unregisterListener(this);
            }
        });

        // linear acceleration switch listener
        linearSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                blinacc = true;
                sensorManager.registerListener(this, slinearAcc, SensorManager.SENSOR_DELAY_FASTEST);
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
                sensorManager.registerListener(this,sLight,SensorManager.SENSOR_DELAY_FASTEST);
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
                sensorManager.registerListener(this,sTemperature,SensorManager.SENSOR_DELAY_FASTEST);
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
                sensorManager.registerListener(this,sProximity,SensorManager.SENSOR_DELAY_FASTEST);
            }
            else{
                bprox = false;
                sensorManager.unregisterListener(this);
            }
        });

        accavg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AccelerometerEntity> accData = sensorDatabase.dao().getAccData();

                float x = 0.0f,y=0.0f,z= 0.0f;

                for (int i =0; i<accData.size(); i++){
                    x += accData.get(i).getX();
                    y += accData.get(i).getY();
                    z += accData.get(i).getZ();

                    Log.d("SENSOR_DATA" , ""+(accData.get(i).getX() +": "+
                            accData.get(i).getY()+": "+ accData.get(i).getZ()+": "+
                            accData.get(i).getTime()));

                }
                float xavg = x/accData.size();
                float yavg = y/accData.size();
                float zavg = z/accData.size();

                float res = roundFloat((xavg+ yavg+ zavg) /3);


                accview.setText(String.valueOf( res) );


            }
        });

        tempavg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<TemperatureEntity> temperatureEntityList = sensorDatabase.dao().getTempData();
                float x = 0.0f;
                for (int i =0; i<temperatureEntityList.size(); i++){
                    x += temperatureEntityList.get(i).getTemp();

                    Log.d("SENSOR_DATA" , ""+temperatureEntityList.get(i).getTemp() );
                }

                float xavg = x/temperatureEntityList.size();
                float res = roundFloat(xavg);
                tempview.setText(String.valueOf(res));

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

            x = roundFloat(x);
            y = roundFloat(y);
            z = roundFloat(z);


            Log.d(TAG, "onSensorChanged: Accelerometer"+"x="+x+", y="+y+", z="+z);

//            motionDetection(x,y,z);

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

        List<AccelerometerEntity> lastPos = sensorDatabase.dao().getAccData();
        int index = lastPos.size() -1;
        float x_prev = lastPos.get(index).getX();
        float y_prev = lastPos.get(index).getY();
        float z_prev = lastPos.get(index).getZ();

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