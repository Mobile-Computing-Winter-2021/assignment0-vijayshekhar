package com.example.helloworld.ROOM;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {WifiEnity.class}, version = 1)
public abstract class WifiDB extends RoomDatabase {

    public abstract WifiDao dao();
}
