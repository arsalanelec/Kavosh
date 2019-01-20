package com.example.arsalan.kavosh.room;

import com.example.arsalan.kavosh.model.SurFound;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface SurFoundDao {
    @Insert(onConflict = REPLACE)
    long[] saveList(List<SurFound> SurFoundList);

    @Insert(onConflict = REPLACE)
    long save(SurFound surFound);


    @Query("SELECT * FROM SurFound")
    LiveData<List<SurFound>> loadAll();

    @Query("SELECT * FROM SurFound WHERE survey_id=:id")
    LiveData<List<SurFound>> loadBySurveyId(String id);

    @Query("SELECT * FROM SurFound WHERE id=:id")
    LiveData<SurFound> loadById(String id);

    @Query("Delete From SurFound WHERE id=:id")
    void delete(String id);


}
