package com.example.erfan_delavari_hw12_maktab36.controller.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.erfan_delavari_hw12_maktab36.R;
import com.example.erfan_delavari_hw12_maktab36.controller.Fragments.TaskListFragment;
import com.example.erfan_delavari_hw12_maktab36.model.Task;
import com.example.erfan_delavari_hw12_maktab36.model.TaskState;
import com.example.erfan_delavari_hw12_maktab36.repository.RepositoryInterface;
import com.example.erfan_delavari_hw12_maktab36.repository.TaskRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TaskPagerActivity extends AppCompatActivity {
    private static final String EXTRA_NAME = "com.example.erfan_delavari_hw11_maktab36.extra_name";
    private static final String EXTRA_NUMBER_OF_TASKS = "com.example.erfan_delavari_hw11_maktab36.extra_number_tasks";

    private static final String BUNDLE_NUMBER_OF_TASKS = "com.example.erfan_delavari_hw11_maktab36.bundle_number_tasks";



    private RepositoryInterface<Task> mRepository;
    private ViewPager2 mViewPager;
    private String mName;
    private int mNumberOfTasks;
    private TaskPagerAdapter mTaskPagerAdapter;

    private FloatingActionButton mButtonAdd;


    public static Intent newIntent(Context context, String name, int numberOfTasks) {
        Intent intent = new Intent(context, TaskPagerActivity.class);
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_NUMBER_OF_TASKS, numberOfTasks);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extrasInit();
        setContentView(R.layout.activity_task_pager);
        findViews();
        if(savedInstanceState == null){
            repositoryInit();
        }else{
            mNumberOfTasks = (int) savedInstanceState.get(BUNDLE_NUMBER_OF_TASKS);
        }
        mTaskPagerAdapter = new TaskPagerAdapter(this);
        mViewPager.setAdapter(mTaskPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        tabLayoutAndViewPagerBinder();
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberOfTasks++;
                Task task = Task.randomTaskCreator(mName + "#" + mNumberOfTasks);
                mTaskPagerAdapter.getFragment(task.getTaskState()).addTask(task);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_NUMBER_OF_TASKS,mNumberOfTasks);

    }

    private void extrasInit() {
        mName = getIntent().getStringExtra(EXTRA_NAME);
        mNumberOfTasks = getIntent().getIntExtra(EXTRA_NUMBER_OF_TASKS, 0);
    }

    private void findViews() {
        mButtonAdd = findViewById(R.id.add_button);
        mViewPager = findViewById(R.id.viewPager2_tasks);
    }

    private void tabLayoutAndViewPagerBinder() {
        TabLayout tabLayout = findViewById(R.id.tabLayout_tasks);
        new TabLayoutMediator(tabLayout, mViewPager, (tab, position) -> tab.setText(getTabName(position))
        ).attach();
    }

    private String getTabName(int position) {
        switch (position) {
            case 0:
                return "ToDo";
            case 1:
                return "Doing";
            case 2:
                return "Done";
        }
        return "null";
    }

    private void repositoryInit() {
        TaskRepository.initialiseTaskList(mNumberOfTasks, mName);
        mRepository = TaskRepository.getRepository();
    }


    private class TaskPagerAdapter extends FragmentStateAdapter {

        private TaskListFragment mToDoFragment;
        private TaskListFragment mDoingFragment;
        private TaskListFragment mDoneFragment;


        public TaskPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
            mDoingFragment = TaskListFragment.newInstance(TaskState.DOING);
            mDoneFragment = TaskListFragment.newInstance(TaskState.DONE);
            mToDoFragment = TaskListFragment.newInstance(TaskState.TODO);
        }


        public TaskListFragment getFragment(TaskState taskState) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            switch (taskState){
                case TODO:
                    return (TaskListFragment) fragmentManager.findFragmentByTag("f0");
                case DOING:
                    return (TaskListFragment) fragmentManager.findFragmentByTag("f1");
                default:
                    return (TaskListFragment) fragmentManager.findFragmentByTag("f2");
            }
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return mToDoFragment;
                case 1:
                    return mDoingFragment;
                default:
                    return mDoneFragment;
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

}