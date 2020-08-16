package com.example.erfan_delavari_hw14_maktab36.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.erfan_delavari_hw14_maktab36.controller.database.UserTaskDBBaseHelper;
import com.example.erfan_delavari_hw14_maktab36.controller.database.cursorwrapper.TaskCursorWrapper;
import com.example.erfan_delavari_hw14_maktab36.controller.database.cursorwrapper.UserCursorWrapper;
import com.example.erfan_delavari_hw14_maktab36.model.Task;
import com.example.erfan_delavari_hw14_maktab36.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.erfan_delavari_hw14_maktab36.controller.database.UserTaskDBSchema.TaskTable;
import static com.example.erfan_delavari_hw14_maktab36.controller.database.UserTaskDBSchema.UserTable;

public class UserDBRepository implements UserRepositoryInterface<User> {

    private static UserDBRepository sUserDBRepository;
    private static Context mContext;

    private SQLiteDatabase mDatabase;


    public static UserDBRepository getInstance(Context context) {
        mContext = context.getApplicationContext();
        if (sUserDBRepository == null) {
            sUserDBRepository = new UserDBRepository();
        }
        return sUserDBRepository;
    }

    public UserDBRepository() {
        UserTaskDBBaseHelper userTaskDBBaseHelper = new UserTaskDBBaseHelper(mContext);
        mDatabase = userTaskDBBaseHelper.getWritableDatabase();
    }

    @Override
    public User getUserByID(UUID uuid) {
        String selectionUser = UserTable.COLS.UUID + "=?";
        String[] selectionArgsUser = new String[]{uuid.toString()};

        try (UserCursorWrapper cursorUser = queryUser(selectionUser, selectionArgsUser)) {
            cursorUser.moveToFirst();
            return cursorUser.getUser();
        }
    }

    private UserCursorWrapper queryUser(String selection, String[] selectionArgs) {
        Cursor cursor = mDatabase.query(
                UserTable.NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        return new UserCursorWrapper(cursor, dataBaseUserID -> {
            String selectionTask = TaskTable.COLS.USER_ID + "=?";
            String[] selectionArgsTask = new String[]{String.valueOf(dataBaseUserID)};
            List<Task> tasks = new ArrayList<>();
            try (TaskCursorWrapper cursorTask = queryTask(selectionTask, selectionArgsTask)) {
                cursorTask.moveToFirst();
                while (!cursorTask.isAfterLast()) {
                    tasks.add(cursorTask.getTask());
                    cursorTask.moveToNext();
                }
                return tasks;
            }
        });
    }

    private TaskCursorWrapper queryTask(String selection, String[] selectionArgs) {
        Cursor cursor = mDatabase.query(
                TaskTable.NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        return new TaskCursorWrapper(cursor);
    }


    @Override
    public List<User> getUserList() {
        List<User> users = new ArrayList<>();
        try (UserCursorWrapper cursor = queryUser(null, null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                users.add(cursor.getUser());
                cursor.moveToNext();
            }
        }
        return users;
    }

    @Override
    public void deleteUser(User user) {
        String whereUser = UserTable.COLS.UUID + "=?";
        String[] whereArgsUser = new String[]{user.getUUID().toString()};
        deleteAllTasks(user);
        mDatabase.delete(UserTable.NAME, whereUser, whereArgsUser);
    }

    @Override
    public void deleteAllTasks(User user) {
        String whereUser = UserTable.COLS.UUID + "=?";
        String[] whereArgsUser = new String[]{user.getUUID().toString()};
        UserCursorWrapper userCursorWrapper = queryUser(whereUser, whereArgsUser);
        userCursorWrapper.moveToFirst();

        String whereTask = TaskTable.COLS.USER_ID + "=?";
        String[] whereArgsTask = new String[]{String.valueOf(userCursorWrapper.getUserID())};

        mDatabase.delete(TaskTable.NAME, whereTask, whereArgsTask);
    }



    @Override
    public void insertUser(User user) {
        ContentValues valuesUser = getUserContentValues(user);
        mDatabase.insert(UserTable.NAME, null, valuesUser);
        for (Task task : user.getTaskList()) {
            insertTask(user,task);
        }
    }

    @Override
    public void insertListToList(List<User> list) {
        //later
    }

    @Override
    public void updateUser(User user) {
        String whereUser = UserTable.COLS.UUID + "=?";
        String[] whereArgsUser = new String[]{user.getUUID().toString()};

        ContentValues valuesUser = getUserContentValues(user);
        mDatabase.update(UserTable.NAME, valuesUser, whereUser, whereArgsUser);
        for (Task task : user.getTaskList()) {
            updateTask(user,task);
        }
    }



    private ContentValues getTaskContentValues(User user, Task task) {
        ContentValues valuesTask = new ContentValues();
        valuesTask.put(TaskTable.COLS.NAME, task.getName());
        valuesTask.put(TaskTable.COLS.DESCRIPTION, task.getDescription());
        valuesTask.put(TaskTable.COLS.DATE, task.getDate().getTime());
        valuesTask.put(TaskTable.COLS.TASK_STATE, task.getTaskState().getTag());
        valuesTask.put(TaskTable.COLS.UUID, task.getUUID().toString());

        String whereUser = UserTable.COLS.UUID + "=?";
        String[] whereArgsUser = new String[]{user.getUUID().toString()};

        UserCursorWrapper userCursorWrapper = queryUser(whereUser, whereArgsUser);
        userCursorWrapper.moveToFirst();
        valuesTask.put(TaskTable.COLS.USER_ID, userCursorWrapper.getUserID());
        return valuesTask;
    }

    private ContentValues getUserContentValues(User user) {
        ContentValues valuesUser = new ContentValues();
        valuesUser.put(UserTable.COLS.UUID, user.getUUID().toString());
        valuesUser.put(UserTable.COLS.USERNAME, user.getUserName());
        valuesUser.put(UserTable.COLS.PASSWORD, user.getPassword());
        valuesUser.put(UserTable.COLS.DATE, user.getRegisterDate().getTime());
        return valuesUser;
    }

    @Override
    public int getUserPosition(User user) {
        List<User> users = getUserList();
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getUUID().equals(user.getUUID())){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void insertTask(User user,Task task) {
        ContentValues valuesTask = getTaskContentValues(user, task);
        mDatabase.insert(TaskTable.NAME, null, valuesTask);
    }

    @Override
    public void deleteTask(Task task) {
        String selection = TaskTable.COLS.UUID +"=?";
        String[] selectionArgs = new String[]{task.getUUID().toString()};
        mDatabase.delete(TaskTable.NAME,selection,selectionArgs);
    }

    @Override
    public void updateTask(User user,Task task) {
        String whereTask = TaskTable.COLS.UUID + "=?";
        String[] whereArgsTask = new String[]{task.getUUID().toString()};
        ContentValues valuesTask = getTaskContentValues(user,task);
        mDatabase.update(TaskTable.NAME,  valuesTask,whereTask,whereArgsTask);
    }
}
