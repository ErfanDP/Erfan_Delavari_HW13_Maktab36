package com.example.erfan_delavari_hw14_maktab36.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.erfan_delavari_hw14_maktab36.database.UserDataBase;
import com.example.erfan_delavari_hw14_maktab36.model.Task;
import com.example.erfan_delavari_hw14_maktab36.model.User;

import java.util.List;
import java.util.UUID;

public class UserDBRepository implements UserRepositoryInterface<User> {

    private static UserDBRepository sUserDBRepository;
    private static Context mContext;

    private UserDataBase mDatabase;


    public static UserDBRepository getInstance(Context context) {
        mContext = context.getApplicationContext();
        if (sUserDBRepository == null) {
            sUserDBRepository = new UserDBRepository();
        }
        return sUserDBRepository;
    }

    public UserDBRepository() {
        mDatabase = Room
                .databaseBuilder(mContext,UserDataBase.class,"UserDB.db")
                .allowMainThreadQueries()
                .build();
    }

    @Override
    public User getUserByID(UUID uuid) {
        User user =mDatabase.mDataBaseDAO().getUserByID(uuid.toString());
        user.setTaskList(mDatabase.mDataBaseDAO().getUsersTasks(user.getId()));
        return user;
    }


    @Override
    public List<User> getUserList() {
        List<User> users= mDatabase.mDataBaseDAO().getUserList();
        for (User user:users) {
            user.setTaskList(mDatabase.mDataBaseDAO().getUsersTasks(user.getId()));
        }
        return users;
    }

    @Override
    public void deleteUser(User user) {
        mDatabase.mDataBaseDAO().deleteUserTasks(user.getId());
        mDatabase.mDataBaseDAO().deleteUser(user);
    }

    @Override
    public void deleteAllUsersTasks(User user) {
        mDatabase.mDataBaseDAO().deleteUserTasks(user.getId());
    }

    @Override
    public User getTasksUser(Task task){
        return mDatabase.mDataBaseDAO().getTasksUser(task.getUserID());
    }


    @Override
    public void insertUser(User user) {
        mDatabase.mDataBaseDAO().insertUser(user);
        Task[] tasks = new Task[user.getTaskList().size()];
        user.getTaskList().toArray(tasks);
        mDatabase.mDataBaseDAO().insetTasks(tasks);
    }

    @Override
    public void insertListToList(List<User> list) {
        //later
    }

    @Override
    public void updateUser(User user) {
        mDatabase.mDataBaseDAO().updateUser(user);
        for (Task task : user.getTaskList()) {
            updateTask(task);
        }
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
    public void insertTask(Task task) {
        mDatabase.mDataBaseDAO().insertTask(task);
    }

    @Override
    public void deleteTask(Task task) {
        mDatabase.mDataBaseDAO().deleteTask(task);
    }

    @Override
    public void updateTask(Task task) {
        mDatabase.mDataBaseDAO().updateTask(task);
    }

    @Override
    public List<Task> searchTask(String name, String description) {
        return mDatabase.mDataBaseDAO().searchTask("%"+name+"%","%"+description+"%");
    }

    @Override
    public List<Task> searchTask(String name, String description, long timeFrom, long timeTo) {
        return mDatabase.mDataBaseDAO().searchTask("'%"+name+"%'",
                "'%"+description+"%'",
                timeFrom,
                timeTo);

    }
}
