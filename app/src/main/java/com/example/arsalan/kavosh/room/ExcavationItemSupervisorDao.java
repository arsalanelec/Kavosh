package com.example.arsalan.kavosh.room;

import com.example.arsalan.kavosh.model.ExcavationItemSupervisorHistory;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ExcavationItemSupervisorDao {
    @Insert(onConflict = REPLACE)
    long[] saveList(List<ExcavationItemSupervisorHistory> excavationItemSupervisorList);

    @Insert(onConflict = REPLACE)
    long save(ExcavationItemSupervisorHistory excavationItemSupervisor);


    @Query("SELECT * FROM ExcavationItemSupervisorHistory WHERE excavationItemId=:id")
    LiveData<List<ExcavationItemSupervisorHistory>> loadAllListByItemId(String id);

    @Query("SELECT * FROM ExcavationItemSupervisorHistory WHERE id=:id")
    LiveData<ExcavationItemSupervisorHistory> loadById(String id);

   /* @Query("SELECT * FROM ExcavationItemSupervisor WHERE id = :id")
    LiveData<ExcavationItemSupervisor> getExcavationItemSupervisorById(long id);

    @Query("SELECT * From ExcavationItemSupervisor WHERE excavationItemSupervisorName = :excavationItemSupervisorName")
    LiveData<ExcavationItemSupervisor> getExcavationItemSupervisorByExcavationItemSupervisorName(long excavationItemSupervisorName);

    @Query("SELECT * From ExcavationItemSupervisor WHERE trainerId = :trainerId")
    LiveData<List<ExcavationItemSupervisor>> getExcavationItemSupervisorByTrainer(long trainerId);*/
}
