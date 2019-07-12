package com.dsc.wwapp.database.questions;

import android.app.Service;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "questions")
public class Questions {

    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo
    int question_category;
    int question_uid;
    String question;
    String question_key;

    public Questions(String question, int question_category, int question_uid,String question_key){
        this.question = question ;
        this.question_category = question_category;
        this.question_uid = question_uid;
        this.question_key = question_key;
    }

    public int getQuestion_category() {
        return question_category;
    }

    public String getQuestion() {
        return question;
    }

    public int getQuestion_uid() {
        return question_uid;
    }

    public String getQuestion_key(){return question_key;}
}
