package com.example.arsalan.kavosh.room;

import com.example.arsalan.kavosh.model.Layer;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface LayerDao {
    @Insert(onConflict = REPLACE)
    long[] saveList(List<Layer> layerList);

    @Insert(onConflict = REPLACE)
    long save(Layer layer);


    @Query("SELECT * FROM Layer")
    LiveData<List<Layer>> loadAll();

    @Query("SELECT * FROM Layer WHERE id=:id")
    LiveData<Layer> loadById(String id);

    @Query("Delete From Layer WHERE id=:id")
    void delete(String id);


}
