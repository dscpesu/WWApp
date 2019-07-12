package com.dsc.wwapp.database.profile;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.annotation.Nonnull;

@Entity(tableName = "userprofile")
public class UserProfile {

    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo
    private int taskUID;
    private int taskType;
    private int taskOptionSelected;
    private double weight;
    @ColumnInfo
    boolean valid ;


    public UserProfile(int taskUID,int taskType,boolean valid,int taskOptionSelected,double weight){

        this.taskUID = taskUID;
        this.taskType = taskType;
        this.valid = valid;
        this.taskOptionSelected = taskOptionSelected;
        this.weight = weight;

    }

    public int getTaskUID() {
        return taskUID;
    }

    public int getTaskType() {
        return taskType;
    }

    public boolean isValid() {
        //for some case if valid is somehow assigned null in db
        if(valid == true || valid == false)
            return valid;
        else
            return false;
    }

    public int getTaskOptionSelected() {
        return taskOptionSelected;
    }

    public double getWeight() {
        return weight;
    }
}
