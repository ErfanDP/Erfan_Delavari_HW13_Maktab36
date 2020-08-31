package com.example.erfan_delavari_hw14_maktab36.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.erfan_delavari_hw14_maktab36.model.Task;
import com.example.erfan_delavari_hw14_maktab36.model.User;
import com.example.erfan_delavari_hw14_maktab36.database.UserTaskDBSchema.*;
import java.util.List;

@Dao
public interface DataBaseDAO {
    @Query("SELECT * FROM usertable")
    List<User> getUserList();

    @Query("SELECT * FROM usertable WHERE user_uuid =:uuid")
    User getUserByID(String uuid);

    @Delete
    void deleteUser(User user);

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Insert
    void insertTask(Task task);

    @Insert
    void insetTasks(Task... tasks);

    @Delete
    void deleteTask(Task task);

    @Update
    void updateTask(Task task);

    @Query("SELECT * FROM usertable WHERE user_db_id =:tasksUserID")
    User getTasksUser(long tasksUserID);


    @Query("SELECT *" +
            " FROM "+TaskTable.NAME+
            " WHERE "+TaskTable.COLS.NAME +" LIKE :name AND "+
            TaskTable.COLS.DESCRIPTION +" LIKE :description ")
    List<Task> searchTask(String name,String description);

    @Query("SELECT *" +
            " FROM "+TaskTable.NAME+
            " WHERE "+TaskTable.COLS.NAME +" LIKE :name AND "+
            TaskTable.COLS.DESCRIPTION +" LIKE :description AND "+
            TaskTable.COLS.DATE +">:timeFrom AND "+
            TaskTable.COLS.DATE +"<:timeTo")
    List<Task> searchTask(String name,String description,long timeFrom,long timeTo);


    @Query("SELECT * FROM tasktable WHERE user_id =:userId")
    List<Task> getUsersTasks(long userId);

    @Query("DELETE FROM tasktable WHERE user_id =:userId")
    void deleteUserTasks(long userId);
}
