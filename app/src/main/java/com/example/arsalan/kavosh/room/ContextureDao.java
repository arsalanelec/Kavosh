package com.example.arsalan.kavosh.room;

import com.example.arsalan.kavosh.model.Contexture;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ContextureDao {
    @Insert(onConflict = REPLACE)
    long[] saveList(List<Contexture> contextureList);

    @Insert(onConflict = REPLACE)
    long save(Contexture contexture);

    @Query("SELECT * FROM Contexture WHERE layerId=:id")
    LiveData<Contexture> loadAllListByLayerId(String id);

    @Query("SELECT * FROM Contexture WHERE id=:id")
    LiveData<Contexture> loadById(String id);

}
