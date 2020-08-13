package com.example.erfan_delavari_hw13_maktab36.controller.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erfan_delavari_hw13_maktab36.R;
import com.example.erfan_delavari_hw13_maktab36.model.Task;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskHolder> {

    private static final int TYPE_ODD = 1;
    private static final int TYPE_EVEN = 2;

    private List<Task> mTaskList;
    private OnListEmpty mOnListEmpty;
    private OnRowClick mOnRowClick;

    public void setTaskList(List<Task> taskList) {
        mTaskList = taskList;
    }

    public TaskListAdapter(List<Task> taskList, OnListEmpty onListEmpty,OnRowClick onRowClick) {
        mTaskList = taskList;
        mOnListEmpty = onListEmpty;
        mOnRowClick = onRowClick;
        if(mTaskList.size() == 0){
            mOnListEmpty.onListIsEmpty();
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

        private Task mTask;
        private TextView mName;
        private TextView mDate;
        private TextView mDescription;
        private TextView mFirstLetter;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.list_row_name);
            mDate = itemView.findViewById(R.id.list_row_date);
            mDescription = itemView.findViewById(R.id.list_row_description);
            mFirstLetter = itemView.findViewById(R.id.list_row_first_letter_user);
            itemView.setOnClickListener(v -> mOnRowClick.rowClick(mTask));
        }

        public void viewBinder(Task task) {
            mTask = task;
            mFirstLetter.setText(String.valueOf(Character.toUpperCase(task.getName().charAt(0))));
            mName.setText(task.getName());
            mDescription.setText(task.getDescription());
            SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss", Locale.US);
            mDate.setText(simpleDateFormatDate.format(task.getDate()));
        }
    }


    public interface OnListEmpty{
        void onListIsEmpty();
    }

    public interface OnRowClick {
        void rowClick(Task task);
    }

}
