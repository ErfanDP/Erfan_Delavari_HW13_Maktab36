package com.example.erfan_delavari_hw13_maktab36.repository;

import com.example.erfan_delavari_hw13_maktab36.model.TaskState;

import java.util.List;
import java.util.UUID;

public interface RepositoryInterface<E> {

    List<E> getUserList();

    E getUserByID(UUID uuid);

    void setUserList(List<E> list);

    void deleteUser(E e);

    void insertUser(E e);

    void insertListToList(List<E> list);

    void updateUser(E e);

    int getUserPosition(E e);
}
