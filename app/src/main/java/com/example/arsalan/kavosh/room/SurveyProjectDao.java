package com.example.arsalan.kavosh.room;

import com.example.arsalan.kavosh.model.SurveyProject;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface SurveyProjectDao {
    @Insert(onConflict = REPLACE)
    long[] saveList(List<SurveyProject> surveyList);

    @Insert(onConflict = REPLACE)
    long save(SurveyProject surveyProject);


    @Query("SELECT * FROM SurveyProject")
    LiveData<List<SurveyProject>> loadAll();

    @Query("SELECT * FROM SurveyProject WHERE id=:id")
    LiveData<SurveyProject> loadById(String id);

    @Query("Delete From SurveyProject WHERE id=:id")
    void delete(String id);


}
