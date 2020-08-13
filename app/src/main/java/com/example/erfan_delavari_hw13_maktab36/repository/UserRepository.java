package com.example.erfan_delavari_hw13_maktab36.repository;

import com.example.erfan_delavari_hw13_maktab36.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository implements RepositoryInterface<User> {

    private static UserRepository sRepository;
    private List<User> mUserList = new ArrayList<>();


    private UserRepository() {
    }


    public static UserRepository getRepository() {
        if (sRepository == null) {
            sRepository = new UserRepository();
        }
        return sRepository;
    }

    @Override
    public List<User> getUserList() {
        return mUserList;
    }

    @Override
    public User getUserByID(UUID uuid) {
        for (User user : mUserList) {
            if (user.getUUID().equals(uuid)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void setUserList(List<User> list) {
        mUserList = list;
    }

    @Override
    public void deleteUser(User user) {
        for (int i = 0; i < mUserList.size(); i++) {
            if (mUserList.get(i).getUUID().equals(user.getUUID())) {
                mUserList.remove(i);
                return;
            }
        }
    }

    @Override
    public void updateUser(User user) {
        User updateUser = getUserByID(user.getUUID());
        updateUser.setUserName(user.getUserName());
        updateUser.setPassword(user.getPassword());
        updateUser.setTaskList(user.getTaskList());
    }

    @Override
    public void insertUser(User user) {
        mUserList.add(user);
    }

    @Override
    public void insertListToList(List<User> users) {
        mUserList.addAll(users);
    }

    @Override
    public int getUserPosition(User user) {
        for (int i = 0; i < mUserList.size(); i++) {
            if(mUserList.get(i).getUUID().equals(user.getUUID())){
                return i;
            }
        }
        return 0;
    }


}
