package com.example.erfan_delavari_hw11_maktab36.repository;

import com.example.erfan_delavari_hw11_maktab36.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRepository implements RepositoryInterface<Task>{
    private static TaskRepository sRepository;
    private List<Task> mTaskList = new ArrayList<>();
    private int mNumberOfCrimes = -1;

    public TaskRepository() {

    }

    public static TaskRepository getRepository() {
        if(sRepository == null){
            sRepository = new TaskRepository();
        }
        return sRepository;
    }

    @Override
    public List<Task> getList() {
        return mTaskList;
    }

    @Override
    public Task get(UUID uuid) {
        for (Task task:mTaskList) {
            if(task.getUUID().equals(uuid)){
                return task;
            }
        }
        return null;
    }

    @Override
    public void setList(List<Task> list) {
        mTaskList = list;
    }

    @Override
    public void delete(Task task) {
        for (int i = 0; i < mTaskList.size(); i++) {
            if (mTaskList.get(i).getUUID().equals(task.getUUID())) {
                mTaskList.remove(i);
                return;
            }
        }
    }

    @Override
    public void update(Task task) {
        Task updateTask = get(task.getUUID());
        updateTask.setName(task.getName());
        updateTask.setTaskState(task.getTaskState());
    }
    @Override
    public void insert(Task task) {
        mTaskList.add(task);
    }

    @Override
    public void insertToList(List<Task> list) {mTaskList.addAll(list);}
}
