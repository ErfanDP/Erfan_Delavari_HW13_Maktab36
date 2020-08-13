package com.example.erfan_delavari_hw13_maktab36.controller.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erfan_delavari_hw13_maktab36.R;
import com.example.erfan_delavari_hw13_maktab36.controller.adapters.TaskListAdapter;
import com.example.erfan_delavari_hw13_maktab36.model.Task;
import com.example.erfan_delavari_hw13_maktab36.model.TaskState;
import com.example.erfan_delavari_hw13_maktab36.model.User;
import com.example.erfan_delavari_hw13_maktab36.repository.UserRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class TaskListFragment extends Fragment implements Serializable {

    private static final String ARG_TASK_STATE = "arg_task_state_list";
    public static final String ARG_USER_ID = "arg_user_ID";

    private User mUser;
    private RecyclerView mRecyclerView;
    private TaskState mTaskState;
    private ImageView mImageViewNoDataFound;
    private TaskListAdapter mAdapter;
    private List<Task> mTaskList;

    public TaskListFragment() {
    }

    public static TaskListFragment newInstance(TaskState taskState, UUID userID) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_STATE, taskState);
        args.putSerializable(ARG_USER_ID, userID);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("App",mTaskState+"Fragment onCreate");
        if (getArguments() != null) {
            mTaskState = (TaskState) getArguments().getSerializable(ARG_TASK_STATE);
            mUser = UserRepository.getRepository()
                    .getUserByID((UUID) getArguments().getSerializable(ARG_USER_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("App",mTaskState+"Fragment onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        findViews(view);
        recyclerViewInit();
        return view;
    }



    private void recyclerViewInit() {
        int rowNumbers = 1;
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rowNumbers = 2;
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), rowNumbers));
        mTaskList =mUser.getTaskListByTaskState(mTaskState);
        mAdapter = new TaskListAdapter(mTaskList
                , () -> mImageViewNoDataFound.setVisibility(View.VISIBLE)
                , task ->
                TaskPagerFragment.creatingDialogTaskInformation(task,getParentFragment(),
                        false,TaskPagerFragment.REQUEST_CODE_TASK_INFORMATION_EDIT));
        Log.d("App",mTaskState+"Fragment mTaskList:"+mTaskList.toString());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_contaner);
        mImageViewNoDataFound = view.findViewById(R.id.imageView_no_data_found);
    }

    public void addTask(Task task) {
        mUser.insertTask(task);
        mTaskList.add(task);
        mAdapter.notifyItemInserted(mTaskList.size()-1);
        mImageViewNoDataFound.setVisibility(View.INVISIBLE);
    }

    public void removeAllTasks(){
        mUser.deleteAllTasks();
        mTaskList = new ArrayList<>();
        mAdapter.setTaskList(mTaskList);
        mAdapter.notifyDataSetChanged();
        mImageViewNoDataFound.setVisibility(View.VISIBLE);
    }

    public void notifyAdapter(Task task){
        mUser.updateTask(task);
        mTaskList = mUser.getTaskListByTaskState(mTaskState);
        mAdapter.setTaskList(mTaskList);
        mAdapter.notifyDataSetChanged();
    }

    public void deleteTask(Task task){
        mUser.deleteTask(task);
        mTaskList.remove(task);
        mAdapter.setTaskList(mTaskList);
        mAdapter.notifyDataSetChanged();
        if(mAdapter.getItemCount() == 0){
            mImageViewNoDataFound.setVisibility(View.VISIBLE);
        }
    }

}

