package com.example.erfan_delavari_hw14_maktab36.model;

import com.example.erfan_delavari_hw14_maktab36.controller.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Task implements Serializable {
    private UUID mUUID;
    private String mName;
    private String mDescription;
    private TaskState mTaskState;
    private Date mDate;


    public Task() {
        mUUID = UUID.randomUUID();
        mName = "";
        mDescription = "";
        mTaskState = TaskState.DONE;
        mDate = new Date();
    }

    public Task(String name, String description, TaskState taskState, Date date) {
        mUUID = UUID.randomUUID();
        mName = name;
        mDescription = description;
        mTaskState = taskState;
        mDate = date;
    }

    public Task(UUID UUID, String name, String description, TaskState taskState, Date date) {
        mUUID = UUID;
        mName = name;
        mDescription = description;
        mTaskState = taskState;
        mDate = date;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public TaskState getTaskState() {
        return mTaskState;
    }

    public void setTaskState(TaskState taskState) {
        mTaskState = taskState;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public static Task randomTaskCreator(String name){
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
        return new Task(name,description,taskState,DateUtils.getRandomDate(2000, 2020));
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
}
