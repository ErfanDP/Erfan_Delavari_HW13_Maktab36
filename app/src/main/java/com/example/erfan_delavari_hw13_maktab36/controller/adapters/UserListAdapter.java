package com.example.erfan_delavari_hw13_maktab36.controller.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erfan_delavari_hw13_maktab36.R;
import com.example.erfan_delavari_hw13_maktab36.model.User;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserHolder> {

    private static final int TYPE_ODD = 1;
    private static final int TYPE_EVEN = 2;

    private List<User> mUserList;
    private OnRowClick mOnRowClick;


    public UserListAdapter(List<User> userList, OnListEmpty onListEmpty,OnRowClick onRowClick) {
        mUserList = userList;
        mOnRowClick = onRowClick;
        if(mUserList.size() == 0){
            onListEmpty.onListIsEmpty();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_ODD;
        } else
            return TYPE_EVEN;
    }

    @NonNull
    @Override
    public UserListAdapter.UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == TYPE_ODD) {
            view = inflater.inflate(R.layout.list_row_odd_user, parent, false);
        } else {
            view = inflater.inflate(R.layout.list_row_even_user, parent, false);
        }
        return new UserListAdapter.UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.UserHolder holder, int position) {
        holder.viewBinder(mUserList.get(position));
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    class UserHolder extends RecyclerView.ViewHolder{

        private User mUser;
        private TextView mUserName;
        private TextView mPassword;
        private TextView mNumberOfTasks;
        private TextView mFirstLetterName;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            mUserName = itemView.findViewById(R.id.list_username);
            mPassword = itemView.findViewById(R.id.list_password);
            mNumberOfTasks = itemView.findViewById(R.id.list_number_of_tasks);
            mFirstLetterName = itemView.findViewById(R.id.list_row_first_letter_user);
            itemView.setOnClickListener(v -> mOnRowClick.rowClick(mUser));
        }

        public void viewBinder(User user) {
            mUser = user;
            mUserName.setText(user.getUserName());
            mPassword.setText(user.getPassword());
            mFirstLetterName.setText(String.valueOf(Character.toUpperCase(user.getUserName().charAt(0))));
            mNumberOfTasks.setText(String.valueOf(user.getTaskList().size()));
        }
    }


    public interface OnListEmpty{
        void onListIsEmpty();
    }

    public interface OnRowClick {
        void rowClick(User user);
    }

}
