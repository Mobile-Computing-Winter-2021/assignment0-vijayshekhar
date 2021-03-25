package com.example.helloworld.Models;


import androidx.room.Database;
import androidx.room.RoomDatabase;


import com.example.helloworld.Models.Entities.AccelerometerEntity;
import com.example.helloworld.Models.Entities.GPSEntity;
import com.example.helloworld.Models.Entities.LightEntity;
import com.example.helloworld.Models.Entities.Linear_accelerationEntity;
import com.example.helloworld.Models.Entities.ProximityEntity;
import com.example.helloworld.Models.Entities.TemperatureEntity;

@Database(entities = {  AccelerometerEntity.class,
                        GPSEntity.class,
                        LightEntity.class,
                        Linear_accelerationEntity.class,
                        ProximityEntity.class,
                        TemperatureEntity.class},
                        version =1)
public abstract class SensorDatabase extends RoomDatabase {

    public abstract SensorDAO dao();

}


