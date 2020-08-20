package com.example.erfan_delavari_hw14_maktab36.model;

import java.io.Serializable;
import java.util.List;

public class SerializableList<E> implements Serializable {
    private List<E> mList;

    public SerializableList(List<E> list) {
        mList = list;
    }

    public List<E> getList() {
        return mList;
    }

    public void setList(List<E> list) {
        mList = list;
    }
}
