package com.dsc.wwapp.database.profile;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = UserProfile.class, exportSchema = false,version = 1)
public abstract class ProfileDatabase extends RoomDatabase {

    private static final String DB_NAME = "profile_db";
    private static final Object LOCK = new Object();
    private static ProfileDatabase instance;

    public static synchronized ProfileDatabase getInstance(Context context){
        if (instance == null){
            synchronized (LOCK){
                instance = Room.databaseBuilder(context.getApplicationContext(), ProfileDatabase.class,DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }

        }
        return instance;
    }

    public abstract ProfileDAO profileDAO();

}
