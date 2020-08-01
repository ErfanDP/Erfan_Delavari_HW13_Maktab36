package com.example.erfan_delavari_hw12_maktab36.repository;

import com.example.erfan_delavari_hw12_maktab36.model.Task;
import com.example.erfan_delavari_hw12_maktab36.model.TaskState;

import java.util.List;
import java.util.UUID;

public interface RepositoryInterface<E> {

    List<E> getList();

    E get(UUID uuid);

    void setList(List<E> list);

    void delete(E e);

    void insert(E e);

    void insertToList(List<E> list);

    void update(E e);

    List<E> getTaskListByTaskState(TaskState taskState);
}
