package com.example.erfan_delavari_hw11_maktab36.repository;

import com.example.erfan_delavari_hw11_maktab36.model.Task;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static Repository sRepository;
    private List<Task> mTaskList = new ArrayList<>();

    public Repository(List<Task> taskList) {
        mTaskList = taskList;
    }

    public static Repository getRepository() {
        return sRepository;
    }
}
