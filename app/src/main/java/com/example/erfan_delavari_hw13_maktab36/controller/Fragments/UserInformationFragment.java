package com.example.erfan_delavari_hw13_maktab36.controller.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erfan_delavari_hw13_maktab36.R;
import com.example.erfan_delavari_hw13_maktab36.controller.adapters.TaskListAdapter;
import com.example.erfan_delavari_hw13_maktab36.model.Task;
import com.example.erfan_delavari_hw13_maktab36.model.User;
import com.example.erfan_delavari_hw13_maktab36.repository.UserRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class UserInformationFragment extends Fragment {

    private static final String ARG_USER_ID = "arg_user_id";
    public static final String TAG_TASK_INFORMATION_DIALOG = "TagTaskInformationDialog ";
    public static final int REQ_TASK_INFORMATION_EDIT = 15;
    public static final int REQ_TASK_INFORMATION_ADD = 16;
    public static final String ARG_ON_BUTTON_CLICK = "arg_on_button_click";

    private User mUser;
    private TextView mTextUserName;
    private TextView mTextUserPassword;
    private RecyclerView mRecyclerView;
    private TaskListAdapter mAdapter;
    private Button mButtonEdit;
    private Button mButtonDelete;
    private OnButtonClick mOnButtonClick;
    private ImageView mImageViewNoTaskFound;
    private FloatingActionButton mButtonAddTask;


    public static UserInformationFragment newInstance(UUID userId,OnButtonClick onButtonClick) {
        UserInformationFragment fragment = new UserInformationFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_ID, userId);
        args.putSerializable(ARG_ON_BUTTON_CLICK, onButtonClick);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = UserRepository.getRepository().getUserByID((UUID) getArguments().getSerializable(ARG_USER_ID));
            mOnButtonClick = (OnButtonClick) getArguments().getSerializable(ARG_ON_BUTTON_CLICK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_information, container, false);
        findViews(view);
        recyclerViewInit();
        viewInit();
        listeners();

        return view;
    }

    private void viewInit() {
        mTextUserName.setText(mUser.getUserName());
        mTextUserPassword.setText(mUser.getPassword());
    }

    private void listeners() {
        mButtonAddTask.setOnClickListener(v -> {
            Task task = new Task();
            DialogTaskInformationFragment taskInformationFragment = DialogTaskInformationFragment.newInstance(true,task);
            taskInformationFragment.setTargetFragment(UserInformationFragment.this,REQ_TASK_INFORMATION_ADD);
            taskInformationFragment.show(Objects.requireNonNull(getFragmentManager()),"tag_task_information_add");
        });

        mButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO editing user
            }
        });
        mButtonDelete.setOnClickListener(v -> {
            UserRepository.getRepository().deleteUser(mUser);
            mOnButtonClick.buttonDelete();
        });
    }

    public interface OnButtonClick extends Serializable {
        void buttonDelete();
    }

    private void recyclerViewInit() {
        int rowNumbers = 1;
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rowNumbers = 2;
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), rowNumbers));
        mAdapter = new TaskListAdapter(mUser.getTaskList(),
                () -> mImageViewNoTaskFound.setVisibility(View.VISIBLE),
                task -> {
                    DialogTaskInformationFragment dialogTaskInformationFragment
                            = DialogTaskInformationFragment.newInstance(false,task);
                    dialogTaskInformationFragment.setTargetFragment(UserInformationFragment.this, REQ_TASK_INFORMATION_EDIT);
                    dialogTaskInformationFragment.show(Objects.requireNonNull(getFragmentManager()), TAG_TASK_INFORMATION_DIALOG);
                });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != Activity.RESULT_OK || data == null){
            return;
        }

        if(requestCode == REQ_TASK_INFORMATION_EDIT){
            Task task = (Task) data.getSerializableExtra(DialogTaskInformationFragment.EXTRA_TASK);
            if(data.getBooleanExtra(DialogTaskInformationFragment.EXTRA_DELETED,false)){
                mUser.deleteTask(task);
            }else if(data.getBooleanExtra(DialogTaskInformationFragment.EXTRA_HAS_CHANGED,true)) {
                mUser.updateTask(task);
            }
            mAdapter.notifyDataSetChanged();
        }

        if(requestCode == REQ_TASK_INFORMATION_ADD){
            Task task = (Task) data.getSerializableExtra(DialogTaskInformationFragment.EXTRA_TASK);
            if(data.getBooleanExtra(DialogTaskInformationFragment.EXTRA_HAS_CHANGED,true)) {
                mUser.insertTask(task);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_user_tasks);
        mTextUserName = view.findViewById(R.id.admin_text_user_name);
        mTextUserPassword = view.findViewById(R.id.admin_text_user_password);
        mButtonDelete = view.findViewById(R.id.admin_button_delete);
        mButtonEdit = view.findViewById(R.id.admin_button_edit);
        mButtonAddTask = view.findViewById(R.id.admin_button_add_task);
        mImageViewNoTaskFound = view.findViewById(R.id.admin_no_task_found);
    }

}