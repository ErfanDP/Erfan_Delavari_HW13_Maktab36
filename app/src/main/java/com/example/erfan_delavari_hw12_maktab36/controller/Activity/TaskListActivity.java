package com.example.erfan_delavari_hw12_maktab36.controller.Activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.erfan_delavari_hw12_maktab36.controller.Fragments.TaskListFragment;

public class TaskListActivity extends SingleFragmentActivity {
    private static final String EXTRA_NAME = "com.example.erfan_delavari_hw11_maktab36.extra_name";
    private static final String EXTRA_NUMBER_OF_TASKS = "com.example.erfan_delavari_hw11_maktab36.extra_number_tasks";

    public static Intent newIntent(Context context, String name, int numberOfTasks) {
       Intent intent = new Intent(context,TaskListActivity.class);
       intent.putExtra(EXTRA_NAME,name);
       intent.putExtra(EXTRA_NUMBER_OF_TASKS,numberOfTasks);
       return intent;
    }


    @Override
    public Fragment fragmentCreator() {
        return TaskListFragment.newInstance(getIntent().getStringExtra(EXTRA_NAME),
                getIntent().getIntExtra(EXTRA_NUMBER_OF_TASKS,1));
    }
}