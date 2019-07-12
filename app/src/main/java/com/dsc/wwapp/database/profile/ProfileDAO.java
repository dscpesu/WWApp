package com.dsc.wwapp.database.profile;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProfileDAO {

    @Query("SELECT * FROM USERPROFILE ")
    List<UserProfile> getCompleteUserProfile();

    @Query("SELECT * FROM USERPROFILE WHERE valid = 1")
    List<UserProfile> getValidTagUserProfile();

    @Query("SELECT * FROM USERPROFILE WHERE taskType=:taskType")
    int getTaskType(int taskType);

    @Query("SELECT COUNT(taskType) FROM USERPROFILE WHERE taskType=:taskType AND valid = 1 ")
    int getCountTaskType(int taskType);

    @Query("SELECT SUM(weight) FROM USERPROFILE WHERE valid = 1")
    double getTotalValidWeight();

    @Insert
    void insertProfile(UserProfile userProfile);
    @Update
    void updateProfile(UserProfile userProfile);
    @Delete
    void deleteProfile(UserProfile userProfile);



}
