package com.example.arsalan.kavosh.room;

import com.example.arsalan.kavosh.model.AudioMemo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface AudioDao {
    @Insert(onConflict = REPLACE)
    long[] saveList(List<AudioMemo> audioMemoList);

    @Insert(onConflict = REPLACE)
    long save(AudioMemo audioMemo);

    @Query("SELECT * FROM AudioMemo WHERE audioable_id=:id")
    LiveData<List<AudioMemo>> loadAllByParentId(String id);

    @Query("SELECT * FROM AudioMemo WHERE id=:id")
    LiveData<AudioMemo> loadById(String id);

}
