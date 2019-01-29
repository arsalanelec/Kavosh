package com.example.arsalan.kavosh.room;

import com.example.arsalan.kavosh.model.AudioMemo;
import com.example.arsalan.kavosh.model.Contexture;
import com.example.arsalan.kavosh.model.ExcavationItem;
import com.example.arsalan.kavosh.model.ExcavationItemSupervisorHistory;
import com.example.arsalan.kavosh.model.Feature;
import com.example.arsalan.kavosh.model.Found;
import com.example.arsalan.kavosh.model.Layer;
import com.example.arsalan.kavosh.model.LayerFeature;
import com.example.arsalan.kavosh.model.Photo;
import com.example.arsalan.kavosh.model.Project;
import com.example.arsalan.kavosh.model.Supervisor;
import com.example.arsalan.kavosh.model.SurFound;
import com.example.arsalan.kavosh.model.Survey;
import com.example.arsalan.kavosh.model.SurveyProject;
import com.example.arsalan.kavosh.model.User;
import com.example.arsalan.kavosh.typeconverters.MyTypeConverters;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {

        User.class,
        Project.class,
        ExcavationItem.class,
        ExcavationItemSupervisorHistory.class,
        LayerFeature.class,
        Contexture.class,
        Photo.class,
        AudioMemo.class,
        Layer.class,
        Found.class,
        Feature.class,
        Survey.class,
        SurFound.class,
        SurveyProject.class,
        Supervisor.class
}, exportSchema = false
        , version = 39)
@TypeConverters({MyTypeConverters.class})
public abstract class MyDatabase extends RoomDatabase {


    abstract public UserDao userDao();

    abstract public ProjectDao projectDao();

    abstract public ExcavationItemDao excavationItemDao();

    abstract public ExcavationItemSupervisorDao excavationItemSupervisorDao();

    abstract public LayerFeatureDao layerFeatureDao();

    abstract public ContextureDao contextureDao();

    abstract public PhotoDao photoDao();

    abstract public AudioDao audioDao();

    abstract public LayerDao layerDao();

    abstract public FeatureDao featureDao();

    abstract public FoundDao foundDao();

    abstract public SurveyDao surveyDao();

    abstract public SurFoundDao surFoundDao();

    abstract public SurveyProjectDao surveyProjectDao();

    abstract public SupervisorDao supervisorDao();


}

