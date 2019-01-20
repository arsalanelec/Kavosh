package com.example.arsalan.kavosh.room;

import com.example.arsalan.kavosh.model.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {
    @Insert(onConflict = IGNORE)
    long[] saveList(List<User> userList);

    @Insert(onConflict = REPLACE)
    long saveUser(User user);


    @Query("SELECT * FROM User")
    LiveData<List<User>> loadAllList();

    @Query("SELECT * FROM User WHERE id=:id")
    LiveData<User> loadById(String id);

   /* @Query("SELECT * FROM User WHERE id = :id")
    LiveData<User> getUserById(long id);

    @Query("SELECT * From User WHERE userName = :userName")
    LiveData<User> getUserByUserName(long userName);

    @Query("SELECT * From User WHERE trainerId = :trainerId")
    LiveData<List<User>> getUserByTrainer(long trainerId);*/
}
