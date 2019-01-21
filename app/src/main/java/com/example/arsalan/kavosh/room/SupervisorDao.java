package com.example.arsalan.kavosh.room;

import com.example.arsalan.kavosh.model.Supervisor;
import com.example.arsalan.kavosh.model.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface SupervisorDao {
    @Insert
    void insert(Supervisor supervisor);

    @Insert(onConflict = REPLACE)
    long[] saveList(List<Supervisor> supervisors);

    @Insert(onConflict = REPLACE)
    long save(Supervisor supervisor);


    /* @Query("SELECT * FROM user INNER JOIN Supervisor " +
             "ON user.id=Supervisor.userId " +
             "WHERE Supervisor.projectId=:projectId")
     LiveData<List<User>> getUsersForProject(final String projectId);

     @Query("SELECT * FROM project INNER JOIN Supervisor ON " +
             "project.id = Supervisor.projectId WHERE " +
             "Supervisor.userId =:userId")
     LiveData<List<Project>> getProjectsForUsers(final String  userId);

     @Query("Delete FROM user WHERE id IN "+"(SELECT id FROM user INNER JOIN Supervisor " +
             "ON user.id=Supervisor.userId " +
             "WHERE Supervisor.projectId=:projectId)")
     void deleteByProjectId(String projectId);*/
    @Query("DELETE FROM supervisor WHERE projectId=:projectId")
    void deleteByProjectId(String projectId);

    @Insert(onConflict = IGNORE)
    long[] savesetSupervisorList(List<User> userList);


    @Query("SELECT * FROM User")
    LiveData<List<User>> loadAllList();

    @Query("SELECT * FROM User WHERE id=:id")
    LiveData<User> loadById(String id);

    @Query("SELECT * FROM Supervisor WHERE projectId=:id")
    LiveData<List<Supervisor>> loadAllListByProjectId(String id);


   /* @Query("SELECT * FROM User WHERE id = :id")
    LiveData<User> getUserById(long id);

    @Query("SELECT * From User WHERE userName = :userName")
    LiveData<User> getUserByUserName(long userName);

    @Query("SELECT * From User WHERE trainerId = :trainerId")
    LiveData<List<User>> getUserByTrainer(long trainerId);*/
}
