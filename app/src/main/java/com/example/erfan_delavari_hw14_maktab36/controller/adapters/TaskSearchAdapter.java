package com.example.erfan_delavari_hw14_maktab36.controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erfan_delavari_hw14_maktab36.R;
import com.example.erfan_delavari_hw14_maktab36.model.Task;
import com.example.erfan_delavari_hw14_maktab36.repository.UserDBRepository;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskSearchAdapter  extends RecyclerView.Adapter<TaskSearchAdapter.TaskHolder>{

    private static final int TYPE_ODD = 1;
    private static final int TYPE_EVEN = 2;

    private List<Task> mTaskList;
    private Context mContext;

    public void setTaskList(List<Task> taskList) {
        mTaskList = taskList;
    }


    public TaskSearchAdapter(List<Task> taskList, TaskSearchAdapter.OnListEmpty onListEmpty, Context context) {
        mContext = context.getApplicationContext();
        mTaskList = taskList;
        if(mTaskList.size() == 0){
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
    public TaskSearchAdapter.TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == TYPE_ODD) {
            view = inflater.inflate(R.layout.list_row_odd_task_search, parent, false);
        } else {
            view = inflater.inflate(R.layout.list_row_even_task_search, parent, false);
        }
        return new TaskSearchAdapter.TaskHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull TaskSearchAdapter.TaskHolder holder, int position) {
        holder.viewBinder(mTaskList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    class TaskHolder extends RecyclerView.ViewHolder{

        private TextView mName;
        private TextView mDate;
        private TextView mDescription;
        private TextView mFirstLetter;
        private TextView mUserName;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.list_row_name);
            mDate = itemView.findViewById(R.id.list_row_date);
            mDescription = itemView.findViewById(R.id.list_row_description);
            mFirstLetter = itemView.findViewById(R.id.list_row_first_letter_task);
            mUserName = itemView.findViewById(R.id.list_row_user);
        }

        public void viewBinder(Task task) {
            mFirstLetter.setText(String.valueOf(Character.toUpperCase(task.getName().charAt(0))));
            mName.setText(task.getName());
            mDescription.setText(task.getDescription());
            mUserName.setText(UserDBRepository.getInstance(mContext).getTasksUser(task).getUserName());
            SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss", Locale.US);
            mDate.setText(simpleDateFormatDate.format(task.getDate()));
        }
    }


    public interface OnListEmpty{
        void onListIsEmpty();
    }
}
