package com.dsc.wwapp.database.questions;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dsc.wwapp.database.questions.Questions;

import java.util.List;

@Dao
public interface QuestionsDAO {

    @Query("SELECT * FROM  Questions")
    List<Questions> getQuestions();

    @Insert
    void insertQuestions(Questions questions);
    @Update
    void updateQuestions(Questions questions);
    @Delete
    void deleteQuestions(Questions questions);

    @Query("SELECT * FROM Questions WHERE question_category = :question_category ")
    List<Questions> loadQuestionByQCategory(int question_category);

    @Query("SELECT question_category FROM QUESTIONS WHERE question_uid = :question_uid ")
    int getCategory(int question_uid);

    @Query("SELECT question_key FROM QUESTIONS WHERE question_uid = :question_uid")
    String  getKey(int question_uid);
}
