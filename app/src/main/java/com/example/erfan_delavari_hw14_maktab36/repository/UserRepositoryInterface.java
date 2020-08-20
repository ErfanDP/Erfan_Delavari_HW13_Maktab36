package com.example.erfan_delavari_hw14_maktab36.repository;

import com.example.erfan_delavari_hw14_maktab36.model.Task;

import java.util.List;
import java.util.UUID;

public interface UserRepositoryInterface<E> {

    List<E> getUserList();

    E getUserByID(UUID uuid);

    void deleteUser(E e);

    void insertUser(E e);

    void insertListToList(List<E> list);

    void updateUser(E e);

    int getUserPosition(E e);

    void insertTask(E e, Task task);

    void deleteTask(Task task);

    void updateTask(E e,Task task);

    void deleteAllTasks(E e);

    E getTasksUser(Task task);

    List<Task> searchTask(String name,String description);

    List<Task> searchTask(String name,String description,long timeFrom,long timeTo);

}

