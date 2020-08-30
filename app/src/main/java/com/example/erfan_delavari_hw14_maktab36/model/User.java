package com.example.erfan_delavari_hw14_maktab36.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.example.erfan_delavari_hw14_maktab36.database.UserTaskDBSchema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity(tableName = UserTaskDBSchema.UserTable.NAME)
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = UserTaskDBSchema.UserTable.COLS.ID)
    private long mId;
    @ColumnInfo(name = UserTaskDBSchema.UserTable.COLS.UUID)
    private UUID mUUID;
    @ColumnInfo(name = UserTaskDBSchema.UserTable.COLS.USERNAME)
    private String mUserName;
    @ColumnInfo(name = UserTaskDBSchema.UserTable.COLS.PASSWORD)
    private String mPassword;
    @ColumnInfo(name = UserTaskDBSchema.UserTable.COLS.DATE)
    private Date mRegisterDate;
    @Ignore
    private List<Task> mTaskList = new ArrayList<>();

    public User(String userName, String password) {
        mUserName = userName;
        mPassword = password;
        mUUID = UUID.randomUUID();
        mRegisterDate = new Date();
    }

    public User(UUID UUID, String userName, String password, Date registerDate) {
        mUUID = UUID;
        mUserName = userName;
        mPassword = password;
        mRegisterDate = registerDate;
    }

    public Date getRegisterDate() {
        return mRegisterDate;
    }



    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public void setTaskList(List<Task> list) {
        mTaskList = list;
    }

    public List<Task> getTaskList() {
        return mTaskList;
    }

    public void deleteTask(Task task) {
        for (int i = 0; i < mTaskList.size(); i++) {
            if (mTaskList.get(i).getUUID().equals(task.getUUID())) {
                mTaskList.remove(i);
                return;
            }
        }
    }
    public Task getTaskByUUID(UUID uuid) {
        for (Task task : mTaskList) {
            if(task.getUUID().equals(uuid)){
                return task;
            }
        }
        return null;
    }

    public void updateTask(Task task) {
        Task updateTask = getTaskByUUID(task.getUUID());
        if(updateTask == null){
            return;
        }
        updateTask.setName(task.getName());
        updateTask.setTaskState(task.getTaskState());
        updateTask.setDescription(task.getDescription());
        updateTask.setDate(task.getDate());
    }

    public void insertTask(Task task) {
        mTaskList.add(task);
    }

    public void insertToList(List<Task> list) {
        mTaskList.addAll(list);}

    public List<Task> getTaskListByTaskState(TaskState taskState){
        List<Task> taskList = new ArrayList<>();
        for (Task task: mTaskList) {
            if(task.getTaskState() == taskState ){
                taskList.add(task);
            }
        }
        return taskList;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public void setRegisterDate(Date registerDate) {
        mRegisterDate = registerDate;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public boolean loginCheck(String userName,String password){
        return this.mUserName.equals(userName) && this.mPassword.equals(password);
    }

    public void deleteAllTasks(){
        mTaskList = new ArrayList<>();
    }

    public Date getDate(){
        return mRegisterDate;
    }

    public static class DateConverter{
        @TypeConverter
        public static Date toDate(long time){
            return new Date(time);
        }

        @TypeConverter
        public static Long fromDate(Date date){
            return date.getTime();
        }
    }

}



