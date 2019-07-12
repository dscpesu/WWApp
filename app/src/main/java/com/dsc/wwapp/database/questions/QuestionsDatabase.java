package com.dsc.wwapp.database.questions;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Questions.class,exportSchema = false,version = 1)
public abstract class QuestionsDatabase extends RoomDatabase {

    private static final String DB_NAME = "questions_db";
    private static final Object LOCK = new Object();
    private static QuestionsDatabase instance;

    public static synchronized QuestionsDatabase getInstance(Context context){

        if (instance == null){
            synchronized (LOCK){
                instance = Room.databaseBuilder(context.getApplicationContext(), QuestionsDatabase.class,DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }

        }

        return instance;
    }

    public abstract QuestionsDAO questiondDAO();


}
