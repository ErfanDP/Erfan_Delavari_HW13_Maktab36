package com.example.erfan_delavari_hw12_maktab36.controller.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.erfan_delavari_hw12_maktab36.R;
import com.example.erfan_delavari_hw12_maktab36.controller.Fragments.TaskListFragment;
import com.example.erfan_delavari_hw12_maktab36.model.TaskState;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TaskPagerActivity extends AppCompatActivity {
    private static final String EXTRA_NAME = "com.example.erfan_delavari_hw11_maktab36.extra_name";
    private static final String EXTRA_NUMBER_OF_TASKS = "com.example.erfan_delavari_hw11_maktab36.extra_number_tasks";

    private ViewPager2 mViewPager;
    private String mName;
    private int mNumberOfTasks;

    public static Intent newIntent(Context context, String name, int numberOfTasks) {
       Intent intent = new Intent(context, TaskPagerActivity.class);
       intent.putExtra(EXTRA_NAME,name);
       intent.putExtra(EXTRA_NUMBER_OF_TASKS,numberOfTasks);
       return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extrasInit();
        setContentView(R.layout.activity_task_pager);
        findViews();
        mViewPager.setAdapter(new TaskPagerAdapter(this));
        tabLayoutAndViewPagerBinder();
    }

    private void extrasInit() {
        mName = getIntent().getStringExtra(EXTRA_NAME);
        mNumberOfTasks = getIntent().getIntExtra(EXTRA_NUMBER_OF_TASKS,0);
    }

    private void findViews() {
        mViewPager= findViewById(R.id.viewPager2_tasks);
    }

    private void tabLayoutAndViewPagerBinder() {
        TabLayout tabLayout = findViewById(R.id.tabLayout_tasks);
        new TabLayoutMediator(tabLayout, mViewPager, (tab, position) -> tab.setText(getTabName(position))
        ).attach();
    }

    private String getTabName(int position){
        switch (position){
            case 0:
                return "ToDo";
            case 1:
                return "Doing";
            case 2:
                return "Done";
        }
        return "null";
    }
    private class TaskPagerAdapter extends FragmentStateAdapter{

        public TaskPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return TaskListFragment.newInstance(mName,mNumberOfTasks,TaskState.TODO);
                case 1:
                    return TaskListFragment.newInstance(mName,mNumberOfTasks,TaskState.DOING);
                case 2:
                    return TaskListFragment.newInstance(mName,mNumberOfTasks,TaskState.DONE);
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

}