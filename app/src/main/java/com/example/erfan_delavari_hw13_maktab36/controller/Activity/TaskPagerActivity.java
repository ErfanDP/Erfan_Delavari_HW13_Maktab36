package com.example.erfan_delavari_hw13_maktab36.controller.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.erfan_delavari_hw13_maktab36.R;
import com.example.erfan_delavari_hw13_maktab36.controller.Fragments.TaskListFragment;
import com.example.erfan_delavari_hw13_maktab36.model.TaskState;
import com.example.erfan_delavari_hw13_maktab36.model.User;
import com.example.erfan_delavari_hw13_maktab36.repository.UserRepository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.UUID;

public class TaskPagerActivity extends AppCompatActivity {

    public static final String EXTRA_USER_ID = "com.example.erfan_delavari_hw13_maktab36.user_ID";


    private ViewPager2 mViewPager;
    private User mUser;
    private TaskPagerAdapter mTaskPagerAdapter;
    private FloatingActionButton mButtonAdd;


    public static Intent newIntent(Context context, UUID userID) {
        Intent intent = new Intent(context, TaskPagerActivity.class);
        intent.putExtra(EXTRA_USER_ID,userID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_pager);
        extraInit();
        findViews();
        mTaskPagerAdapter = new TaskPagerAdapter(this);
        mViewPager.setAdapter(mTaskPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        tabLayoutAndViewPagerBinder();
        mButtonAdd.setOnClickListener(v -> {
            // TODO writing a AlertDialog to Create Task and pass it to method below
            // mTaskPagerAdapter.getFragment(task.getTaskState()).addTask(task);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pager_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
                finish();
                return true;
            case R.id.menu_delete_all:
                 new MaterialAlertDialogBuilder(this)
                         .setMessage(R.string.delete_all_question)
                         .setPositiveButton("im sure", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 mTaskPagerAdapter.removeAllTask();
                             }
                         })
                         .setNegativeButton(android.R.string.cancel,null)
                         .create()
                         .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void extraInit() {
        mUser = UserRepository.getRepository().get((UUID) getIntent().getSerializableExtra(EXTRA_USER_ID));
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



    private class TaskPagerAdapter extends FragmentStateAdapter {

        private TaskListFragment mToDoFragment;
        private TaskListFragment mDoingFragment;
        private TaskListFragment mDoneFragment;

        public void removeAllTask(){
            mToDoFragment.removeAllTasks();
            mDoingFragment.removeAllTasks();
            mDoneFragment.removeAllTasks();
        }
        public TaskPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
            mDoingFragment = TaskListFragment.newInstance(TaskState.DOING,mUser.getUUID());
            mDoneFragment = TaskListFragment.newInstance(TaskState.DONE,mUser.getUUID());
            mToDoFragment = TaskListFragment.newInstance(TaskState.TODO,mUser.getUUID());
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