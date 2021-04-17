package com.example.helloworld.ROOM;


import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface WifiDao {


    @Insert
    void dataInsert( WifiEnity wifiEnity);
}
