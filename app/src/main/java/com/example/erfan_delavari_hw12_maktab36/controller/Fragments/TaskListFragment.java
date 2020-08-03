package com.example.erfan_delavari_hw12_maktab36.controller.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.erfan_delavari_hw12_maktab36.R;
import com.example.erfan_delavari_hw12_maktab36.controller.adapters.TaskListAdapter;
import com.example.erfan_delavari_hw12_maktab36.model.Task;
import com.example.erfan_delavari_hw12_maktab36.model.TaskState;
import com.example.erfan_delavari_hw12_maktab36.repository.RepositoryInterface;
import com.example.erfan_delavari_hw12_maktab36.repository.TaskRepository;

import java.util.List;


public class TaskListFragment extends Fragment {

    private static final String ARG_TASK_STATE = "com.example.erfan_delavari_hw12_maktab36.arg_task_state_list";


    private RepositoryInterface<Task> mRepository;
    private RecyclerView mRecyclerView;
    private TaskState mTaskState;
    private ImageView mImageViewNoDataFound;

    public static TaskListFragment newInstance(String name, int numberOfTasks, TaskState taskState) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_STATE, taskState);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = TaskRepository.getRepository();
        if (getArguments() != null) {
            mTaskState = (TaskState) getArguments().getSerializable(ARG_TASK_STATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        mRepository.getList();
        mRecyclerView.setAdapter(new TaskListAdapter(mRepository.getTaskListByTaskState(mTaskState)
                ,new TaskListAdapter.OnListEmpty(){
                    @Override
                    public void onListIsEmpty() {
                        mImageViewNoDataFound.setVisibility(View.VISIBLE);
                    }
                }));
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_contaner);
        mImageViewNoDataFound = view.findViewById(R.id.imageView_no_data_found);
    }



}

