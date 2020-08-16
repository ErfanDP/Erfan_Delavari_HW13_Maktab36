package com.example.erfan_delavari_hw14_maktab36.model;

import java.io.Serializable;

public enum TaskState implements Serializable {
    TODO(0),DOING(1),DONE(2);

    private int tag;
    TaskState(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    public static TaskState getTaskStatByTag(int tag){
        switch (tag){
            case 0:
                return TODO;
            case 1:
                return DOING;
            case 2:
                return DONE;
        }
        return null;
    }
}
