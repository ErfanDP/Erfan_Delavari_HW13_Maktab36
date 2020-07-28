package com.example.erfan_delavari_hw11_maktab36.repository;

import com.example.erfan_delavari_hw11_maktab36.model.Task;

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
}
