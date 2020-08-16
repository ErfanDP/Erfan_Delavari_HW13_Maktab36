package com.example.erfan_delavari_hw14_maktab36.controller.database.cursorwrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.erfan_delavari_hw14_maktab36.controller.database.UserTaskDBSchema.UserTable;
import com.example.erfan_delavari_hw14_maktab36.model.Task;
import com.example.erfan_delavari_hw14_maktab36.model.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserCursorWrapper extends CursorWrapper {

    private TaskListGetter mTaskListGetter;

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public UserCursorWrapper(Cursor cursor,TaskListGetter taskListGetter) {
        super(cursor);
        mTaskListGetter = taskListGetter;
    }

    public User getUser() {
        int dataBaseUserID = getInt(getColumnIndex(UserTable.COLS.ID));
        List<Task> tasks= mTaskListGetter.getTaskList(dataBaseUserID);
        String username = getString(getColumnIndex(UserTable.COLS.USERNAME));
        String password = getString(getColumnIndex(UserTable.COLS.PASSWORD));
        String uuid = getString(getColumnIndex(UserTable.COLS.UUID));
        Date date = new Date(getLong(getColumnIndex(UserTable.COLS.DATE)));
        return new User(UUID.fromString(uuid),tasks, username, password,date);
    }


    public interface TaskListGetter{
        List<Task> getTaskList(int dataBaseUserID);
    }

    public int getUserID(){
        return getInt(getColumnIndex(UserTable.COLS.ID));
    }


}
