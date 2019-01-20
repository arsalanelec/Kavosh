package com.example.arsalan.kavosh.room;

import com.example.arsalan.kavosh.model.Photo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface PhotoDao {
    @Insert(onConflict = REPLACE)
    long[] saveList(List<Photo> photoList);

    @Insert(onConflict = REPLACE)
    long save(Photo photo);

    @Query("SELECT * FROM Photo WHERE photoableId=:id")
    LiveData<List<Photo>> loadAllByParentId(String id);

    @Query("SELECT * FROM Photo WHERE id=:id")
    LiveData<Photo> loadById(String id);

    @Query("Delete From Photo WHERE id=:id")
    void delete(String id);
}
