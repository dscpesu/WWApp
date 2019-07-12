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

    @Insert
    void insertProfile(UserProfile userProfile);
    @Update
    void updateProfile(UserProfile userProfile);
    @Delete
    void deleteProfile(UserProfile userProfile);



}
