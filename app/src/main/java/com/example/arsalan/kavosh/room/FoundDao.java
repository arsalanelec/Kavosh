package com.example.arsalan.kavosh.room;

import com.example.arsalan.kavosh.model.Found;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface FoundDao {
    @Insert(onConflict = REPLACE)
    long[] saveList(List<Found> foundList);

    @Insert(onConflict = REPLACE)
    long save(Found found);

    @Query("SELECT * FROM Found WHERE excavationItemId=:id AND type=:type")
    LiveData<List<Found>> loadAllListByExcavationItemId(String id, int type);

    @Query("SELECT * FROM Found WHERE id=:id")
    LiveData<Found> loadById(String id);

    @Query("Delete From Found WHERE id=:id")
    void delete(String id);

    @Query("SELECT COUNT(id) FROM Found WHERE excavationItemId=:excavationItemId")
    int getNumberOfRows(String excavationItemId);
}
