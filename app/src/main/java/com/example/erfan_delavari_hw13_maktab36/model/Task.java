package com.example.erfan_delavari_hw13_maktab36.model;

import com.example.erfan_delavari_hw13_maktab36.controller.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Task{
    private UUID mUUID;
    private String mName;
    private String mDescription;
    private TaskState mTaskState;
    private Date mDate;


    public Task(String name, TaskState taskState) {
        mUUID = UUID.randomUUID();
        mName = name;
        mTaskState = taskState;
        mDate = DateUtils.getRandomDate(2000, 2020);
    }

    public Task(String name, String description, TaskState taskState, Date date) {
        mUUID = UUID.randomUUID();
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
        return new Task(name,taskState);
    }
}
