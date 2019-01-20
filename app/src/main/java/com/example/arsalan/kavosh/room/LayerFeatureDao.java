package com.example.arsalan.kavosh.room;

import com.example.arsalan.kavosh.model.LayerFeature;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface LayerFeatureDao {
    @Insert(onConflict = REPLACE)
    long[] saveList(List<LayerFeature> layerFeatureList);

    @Insert(onConflict = REPLACE)
    long save(LayerFeature layerFeature);


    @Query("SELECT * FROM LayerFeature WHERE excavationItemId=:id ORDER BY name ASC")
    LiveData<List<LayerFeature>> loadAllListByItemId(String id);

    @Query("SELECT * FROM LayerFeature WHERE id=:id")
    LiveData<LayerFeature> loadById(String id);

    @Query("Delete From LayerFeature WHERE childrenId=:id")
    void deleteByChildId(String id);

    @Query("Delete From LayerFeature WHERE excavationItemId=:id")
    void deleteByExcavationId(String id);
}
