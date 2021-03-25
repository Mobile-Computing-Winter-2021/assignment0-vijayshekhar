package com.example.helloworld.Models;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.helloworld.Models.Entities.AccelerometerEntity;
import com.example.helloworld.Models.Entities.GPSEntity;
import com.example.helloworld.Models.Entities.LightEntity;
import com.example.helloworld.Models.Entities.Linear_accelerationEntity;
import com.example.helloworld.Models.Entities.ProximityEntity;
import com.example.helloworld.Models.Entities.TemperatureEntity;

import java.util.List;

@Dao
public interface SensorDAO {

    @Insert
    public void accDataInsert(AccelerometerEntity accelerometerEntity);

    @Query("SELECT * FROM AccelerometerEntity")
    List<AccelerometerEntity> getAccData();


    @Query("SELECT * FROM AccelerometerEntity where time >= :present_time  ")
    List<AccelerometerEntity> getAccDataPast1Hour(Long present_time);


    @Insert
    public void tempDataInsert(TemperatureEntity temperatureEntity);

    @Query("SELECT * FROM TemperatureEntity")
    List<TemperatureEntity> getTempData();


    @Query("SELECT * FROM TemperatureEntity  where time >= :present_time ")
    List<TemperatureEntity> getTempDataPast1Hour(Long present_time);

    @Insert
    public void gpsDataInsert(GPSEntity gpsEntity);

    @Insert
    public void linaccDataInsert(Linear_accelerationEntity linear_accelerationEntity);

    @Insert
    public  void proxDataInsert(ProximityEntity proximityEntity);

    @Insert
    public void lightDataInsert(LightEntity lightEntity);


}

