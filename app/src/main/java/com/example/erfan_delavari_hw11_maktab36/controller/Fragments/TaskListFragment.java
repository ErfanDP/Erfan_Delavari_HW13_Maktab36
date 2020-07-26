package com.example.erfan_delavari_hw11_maktab36.controller.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.erfan_delavari_hw11_maktab36.R;


public class TaskListFragment extends Fragment {

    private static final String ARG_NAME = "com.example.erfan_delavari_hw11_maktab36.arg_name";
    private static final String ARG_PARAM2 = "com.example.erfan_delavari_hw11_maktab36.arg_number_of_tasks";

    private String mName;
    private int mNumberOfTasks;

    public static TaskListFragment newInstance(String name, int numberOfTasks) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putInt(ARG_PARAM2, numberOfTasks );
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(ARG_NAME);
            mNumberOfTasks = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_list, container, false);
    }
}