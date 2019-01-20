package com.example.arsalan.kavosh.room;

import com.example.arsalan.kavosh.model.Feature;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface FeatureDao {
    @Insert(onConflict = REPLACE)
    long[] saveList(List<Feature> featureList);

    @Insert(onConflict = REPLACE)
    long save(Feature feature);


    @Query("SELECT * FROM Feature")
    LiveData<List<Feature>> loadAll();

    @Query("SELECT * FROM Feature WHERE id=:id")
    LiveData<Feature> loadById(String id);

    @Query("Delete From Feature WHERE id=:id")
    void delete(String id);


}
