package com.example.erfan_delavari_hw12_maktab36.model;

import java.util.UUID;

public class Task {
    private UUID mUUID;
    private String mName;
    private TaskState mTaskState;

    public Task(String name, TaskState taskState) {
        mUUID = UUID.randomUUID();
        mName = name;
        mTaskState = taskState;
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
}
