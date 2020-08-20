package com.example.erfan_delavari_hw14_maktab36.controller.database.cursorwrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.erfan_delavari_hw14_maktab36.controller.database.UserTaskDBSchema.*;
import com.example.erfan_delavari_hw14_maktab36.model.Task;
import com.example.erfan_delavari_hw14_maktab36.model.TaskState;

import java.util.Date;
import java.util.UUID;

public class TaskCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Task getTask(){
        String uuid = getString(getColumnIndex(TaskTable.COLS.UUID));
        String name = getString(getColumnIndex(TaskTable.COLS.NAME));
        String description = getString(getColumnIndex(TaskTable.COLS.DESCRIPTION));
        Date date = new Date(getLong(getColumnIndex(TaskTable.COLS.DATE)));
        TaskState taskState = TaskState.getTaskStatByTag(getInt(getColumnIndex(TaskTable.COLS.TASK_STATE)));
        return new Task(UUID.fromString(uuid),name,description,taskState,date);
    }

    public int getTasksUserID(){
        return getInt(getColumnIndex(TaskTable.COLS.USER_ID));
    }
}
