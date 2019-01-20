package com.example.arsalan.kavosh.room;

import com.example.arsalan.kavosh.model.ExcavationItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ExcavationItemDao {
    @Insert(onConflict = REPLACE)
    long[] saveList(List<ExcavationItem> excavationItemList);

    @Insert(onConflict = REPLACE)
    long save(ExcavationItem excavationItem);

    @Query("Delete From ExcavationItem WHERE excavationProjectId=:excavationProjectId")
    void deleteAll(String excavationProjectId);

    @Query("Delete From ExcavationItem WHERE id=:id")
    void delete(String id);

    @Query("SELECT * FROM ExcavationItem WHERE excavationProjectId=:id")
    LiveData<List<ExcavationItem>> loadAllListByProjectId(String id);

    @Query("SELECT * FROM ExcavationItem WHERE id=:id")
    LiveData<ExcavationItem> loadById(String id);

   /* @Query("SELECT * FROM ExcavationItem WHERE id = :id")
    LiveData<ExcavationItem> getExcavationItemById(long id);

    @Query("SELECT * From ExcavationItem WHERE excavationItemName = :excavationItemName")
    LiveData<ExcavationItem> getExcavationItemByExcavationItemName(long excavationItemName);

    @Query("SELECT * From ExcavationItem WHERE trainerId = :trainerId")
    LiveData<List<ExcavationItem>> getExcavationItemByTrainer(long trainerId);*/
}
