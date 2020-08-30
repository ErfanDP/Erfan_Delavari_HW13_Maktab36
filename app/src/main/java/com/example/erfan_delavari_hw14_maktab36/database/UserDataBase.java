package com.example.erfan_delavari_hw14_maktab36.database;

import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.erfan_delavari_hw14_maktab36.model.Task;
import com.example.erfan_delavari_hw14_maktab36.model.User;

@androidx.room.Database(entities = {User.class, Task.class},version = UserTaskDBSchema.VERSION,exportSchema = false)
@TypeConverters({User.DateConverter.class,Task.TaskStateConvert.class,Task.UUIDConvert.class})
public abstract class UserDataBase extends RoomDatabase {
    public abstract DataBaseDAO mDataBaseDAO();

}
