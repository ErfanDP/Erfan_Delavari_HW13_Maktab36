package com.example.erfan_delavari_hw11_maktab36.controller.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.erfan_delavari_hw11_maktab36.R;
import com.example.erfan_delavari_hw11_maktab36.model.Task;
import com.example.erfan_delavari_hw11_maktab36.repository.RepositoryInterface;
import com.example.erfan_delavari_hw11_maktab36.repository.TaskRepository;

import java.util.List;


public class TaskListFragment extends Fragment {

    private static final String ARG_NAME = "com.example.erfan_delavari_hw11_maktab36.arg_name";
    private static final String ARG_PARAM2 = "com.example.erfan_delavari_hw11_maktab36.arg_number_of_tasks";

    private static final int TYPE_ODD = 1;
    private static final int TYPE_EVEN = 2;


    private RepositoryInterface<Task> mRepository;
    private RecyclerView mRecyclerView;


    public static TaskListFragment newInstance(String name, int numberOfTasks) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putInt(ARG_PARAM2, numberOfTasks);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            repositoryInit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_task_list, container, false);
        findViews(view);
        int rowNumbers = 1;
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rowNumbers = 2;
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),rowNumbers));
        mRecyclerView.setAdapter(new TaskAdapter(mRepository.getList()));
        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_contaner);
    }

    private void repositoryInit() {
        TaskRepository.initialiseTaskList(getArguments().getInt(ARG_PARAM2), getArguments().getString(ARG_NAME));
        mRepository = TaskRepository.getRepository();
    }

    private class TaskHolder extends RecyclerView.ViewHolder{

        private Task mTask;
        private TextView mName;
        private RadioGroup mTaskState;


        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mName  = itemView.findViewById(R.id.list_row_name);
            mTaskState = itemView.findViewById(R.id.list_row_task_state);
        }

        public void viewBinder(Task task){
            mTask = task;
            mName.setText(mTask.getName());
            switch (mTask.getTaskState()){
                case DONE:
                    mTaskState.check(R.id.radioButton_done);
                    break;
                case TODO:
                    mTaskState.check(R.id.radioButton_to_do);
                    break;
                case DOING:
                    mTaskState.check(R.id.radioButton_doing);
            }
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<Task> mTaskList;

        public TaskAdapter(List<Task> taskList) {
            mTaskList = taskList;
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
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view;
            if (viewType == TYPE_ODD) {
                view = inflater.inflate(R.layout.list_row_odd_task, parent, false);
            } else {
                view = inflater.inflate(R.layout.list_row_even_task, parent, false);
            }
            return new TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            holder.viewBinder(mTaskList.get(position));
        }

        @Override
        public int getItemCount() {
            return mTaskList.size();
        }
    }
}