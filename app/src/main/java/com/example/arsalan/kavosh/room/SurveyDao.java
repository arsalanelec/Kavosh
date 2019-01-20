package com.example.arsalan.kavosh.room;

import com.example.arsalan.kavosh.model.Survey;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface SurveyDao {
    @Insert(onConflict = REPLACE)
    long[] saveList(List<Survey> surveyList);

    @Insert(onConflict = REPLACE)
    long save(Survey survey);


    @Query("SELECT * FROM Survey")
    LiveData<List<Survey>> loadAll();

    @Query("SELECT * FROM Survey WHERE id=:id")
    LiveData<Survey> loadById(String id);

    @Query("SELECT * FROM Survey WHERE surveyProjectId=:id")
    LiveData<List<Survey>> loadListByProjectId(String id);

    @Query("Delete From Survey WHERE surveyProjectId=:id")
    void deleteByProjectId(String id);

    @Query("Delete From Survey WHERE id=:id")
    void delete(String id);

    @Query("SELECT COUNT(id) FROM Survey WHERE surveyProjectId=:projectId")
    int getNumberOfRows(String projectId);

}
