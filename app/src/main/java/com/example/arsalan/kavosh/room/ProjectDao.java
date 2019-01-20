package com.example.arsalan.kavosh.room;

import com.example.arsalan.kavosh.model.Project;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ProjectDao {


    @Insert(onConflict = REPLACE)
    long[] saveList(List<Project> projectList);

    @Insert(onConflict = REPLACE)
    long saveProject(Project project);


    @Query("Delete FROM Project")
    void deleteAll();

    @Query("SELECT * FROM Project")
    LiveData<List<Project>> loadAllList();

   /* @Query("SELECT * FROM Project WHERE id = :id")
    LiveData<Project> getProjectById(long id);

    @Query("SELECT * From Project WHERE projectName = :projectName")
    LiveData<Project> getProjectByProjectName(long projectName);

    @Query("SELECT * From Project WHERE trainerId = :trainerId")
    LiveData<List<Project>> getProjectByTrainer(long trainerId);*/
}
