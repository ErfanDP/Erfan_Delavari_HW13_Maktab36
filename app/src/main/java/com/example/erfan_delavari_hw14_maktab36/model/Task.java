package com.example.erfan_delavari_hw14_maktab36.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.example.erfan_delavari_hw14_maktab36.controller.utils.DateUtils;
import com.example.erfan_delavari_hw14_maktab36.database.UserTaskDBSchema;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity(tableName = UserTaskDBSchema.TaskTable.NAME)
public class Task implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = UserTaskDBSchema.TaskTable.COLS.ID)
    private long mId;
    @ColumnInfo(name = UserTaskDBSchema.TaskTable.COLS.USER_ID)
    private long mUserID;
    @ColumnInfo(name = UserTaskDBSchema.TaskTable.COLS.UUID)
    private UUID mUUID;
    @ColumnInfo(name = UserTaskDBSchema.TaskTable.COLS.NAME)
    private String mName;
    @ColumnInfo(name = UserTaskDBSchema.TaskTable.COLS.DESCRIPTION)
    private String mDescription;
    @ColumnInfo(name = UserTaskDBSchema.TaskTable.COLS.TASK_STATE)
    private TaskState mTaskState;
    @ColumnInfo(name = UserTaskDBSchema.TaskTable.COLS.DATE)
    private Date mDate;

    @Ignore
    public Task(long userID) {
        this.mUserID = userID;
        mUUID = UUID.randomUUID();
        mName = "";
        mDescription = "";
        mTaskState = TaskState.DONE;
        mDate = new Date();
    }
    @Ignore
    public Task(String name, String description, TaskState taskState, Date date,long userID) {
        this.mUserID = userID;
        mUUID = UUID.randomUUID();
        mName = name;
        mDescription = description;
        mTaskState = taskState;
        mDate = date;
    }

    public Task() { }

    public long getUserID() {
        return mUserID;
    }

    public void setUserID(long userID) {
        mUserID = userID;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public TaskState getTaskState() {
        return mTaskState;
    }

    public void setTaskState(TaskState taskState) {
        mTaskState = taskState;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public static Task randomTaskCreator(String name,long userID){
        TaskState taskState = TaskState.DOING;
        switch (((int)(Math.random()*10)) % 3){
            case 1:
                taskState = TaskState.DONE;
                break;
            case 2:
                taskState = TaskState.TODO;
                break;
        }
        String description = "this is random generated Task by system";
        return new Task(name,description,taskState,DateUtils.getRandomDate(2000, 2020),userID);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(mUUID, task.mUUID) &&
                Objects.equals(mName, task.mName) &&
                Objects.equals(mDescription, task.mDescription) &&
                mTaskState == task.mTaskState &&
                Objects.equals(mDate, task.mDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mUUID, mName, mDescription, mTaskState, mDate);
    }

    public static class TaskStateConvert{
        @TypeConverter
        public TaskState fromTag(int tag){
            return TaskState.getTaskStatByTag(tag);
        }
        @TypeConverter
        public int toTag(TaskState taskState){
            return taskState.getTag();
        }
    }
    public static class UUIDConvert{
        @TypeConverter
        public UUID toUUID(String string){
            return UUID.fromString(string);
        }
        @TypeConverter()
        public String fromUUID(UUID uuid){
            return uuid.toString();
        }
    }
}
