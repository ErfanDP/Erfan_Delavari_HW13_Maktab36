package com.example.erfan_delavari_hw12_maktab36.controller.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erfan_delavari_hw12_maktab36.R;
import com.example.erfan_delavari_hw12_maktab36.model.Task;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskHolder> {

    private static final int TYPE_ODD = 1;
    private static final int TYPE_EVEN = 2;

    private List<Task> mTaskList;

    public TaskListAdapter(List<Task> taskList) {
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
    public TaskListAdapter.TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == TYPE_ODD) {
            view = inflater.inflate(R.layout.list_row_odd_task, parent, false);
        } else {
            view = inflater.inflate(R.layout.list_row_even_task, parent, false);
        }
        return new TaskListAdapter.TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListAdapter.TaskHolder holder, int position) {
        holder.viewBinder(mTaskList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    class TaskHolder extends RecyclerView.ViewHolder{

        private TextView mName;


        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mName  = itemView.findViewById(R.id.list_row_name);
        }

        public void viewBinder(Task task){
            mName.setText(task.getName());
        }
    }

}
