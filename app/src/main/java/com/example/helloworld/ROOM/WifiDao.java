package com.example.helloworld.ROOM;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WifiDao {


    @Insert
    void dataInsert( WifiEnity wifiEnity);

    @Query("SELECT * FROM WifiEnity")
    List<WifiEnity> getAll();
}
