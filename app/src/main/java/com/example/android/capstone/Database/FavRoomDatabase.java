package com.example.android.capstone.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.capstone.moviemodel.SearchResult;

@Database(entities = {SearchResult.class},version = 1,exportSchema = false)
public abstract class FavRoomDatabase extends RoomDatabase {
    public abstract FavDao favouriteDao();
    private static volatile FavRoomDatabase INSTANCE;

    public static FavRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (FavRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),FavRoomDatabase.class
                            ,"favo_database").build();
                }
            }
        }
        return INSTANCE;
    }

}
