package com.example.erfan_delavari_hw11_maktab36.model;

public class Task {
    private String mName;
    private TaskState mTaskState;

    public Task(String name, TaskState taskState) {
        mName = name;
        mTaskState = taskState;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public TaskState getTaskState() {
        return mTaskState;
    }

    public void setTaskState(TaskState taskState) {
        mTaskState = taskState;
    }
}
