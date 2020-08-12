package com.example.erfan_delavari_hw13_maktab36.controller.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.erfan_delavari_hw13_maktab36.R;
import com.example.erfan_delavari_hw13_maktab36.model.Task;
import com.example.erfan_delavari_hw13_maktab36.model.TaskState;
import com.example.erfan_delavari_hw13_maktab36.model.User;
import com.example.erfan_delavari_hw13_maktab36.repository.UserRepository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;
import java.util.UUID;

public class TaskPagerFragment extends Fragment {
    public static final int REQUEST_CODE_TASK_INFORMATION_ADD = 1;
    public static final int REQUEST_CODE_TASK_INFORMATION_EDIT = 2;

    public static final String ARG_USER_ID = "arg_user_id";
    public static final String TAG_TASK_INFORMATION_DIALOG = "tag_task_information_dialog";


    private ViewPager2 mViewPager;
    private User mUser;
    private TaskPagerAdapter mTaskPagerAdapter;
    private FloatingActionButton mButtonAdd;


    public static TaskPagerFragment newInstance(UUID userID) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_ID,userID);
        TaskPagerFragment fragment = new TaskPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(getArguments() != null){
            mUser = UserRepository.getRepository().get((UUID) getArguments().getSerializable(ARG_USER_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_task_pager, container, false);
        findViews(view);
        viewPagerInit(view);
        listeners();
        return view;
    }

    private void viewPagerInit(View view) {
        mTaskPagerAdapter = new TaskPagerAdapter(this);
        mViewPager.setAdapter(mTaskPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        tabLayoutAndViewPagerBinder(view);
    }

    private void listeners() {
        mButtonAdd.setOnClickListener(v -> {
            Task task = new Task();
            creatingDialogTaskInformation(task,TaskPagerFragment.this,true, REQUEST_CODE_TASK_INFORMATION_ADD);
        });
    }

    public static void creatingDialogTaskInformation(Task task,Fragment fragment,boolean editable,int requestCode) {
        DialogTaskInformationFragment dialogTaskInformationFragment= DialogTaskInformationFragment.newInstance(editable,task);
        dialogTaskInformationFragment.setTargetFragment(fragment, requestCode);
        dialogTaskInformationFragment.show(Objects.requireNonNull(fragment.getFragmentManager()), TAG_TASK_INFORMATION_DIALOG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != Activity.RESULT_OK || data == null){
            return;
        }

        if(requestCode == REQUEST_CODE_TASK_INFORMATION_EDIT){
            Task task = (Task) data.getSerializableExtra(DialogTaskInformationFragment.EXTRA_TASK);
            if(data.getBooleanExtra(DialogTaskInformationFragment.EXTRA_DELETED,false)){
                mTaskPagerAdapter.deleteTask(Objects.requireNonNull(task));
                return;
            }

            if(data.getBooleanExtra(DialogTaskInformationFragment.EXTRA_HAS_CHANGED,true)) {
                mTaskPagerAdapter.notifyLists(task);
                return;
            }
        }

        if(requestCode == REQUEST_CODE_TASK_INFORMATION_ADD){
            Task task = (Task) data.getSerializableExtra(DialogTaskInformationFragment.EXTRA_TASK);
            if(data.getBooleanExtra(DialogTaskInformationFragment.EXTRA_HAS_CHANGED,true)) {
                    mTaskPagerAdapter.getFragment(Objects.requireNonNull(task).getTaskState()).addTask(task);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
                Objects.requireNonNull(getActivity()).finish();
                return true;
            case R.id.menu_delete_all:
                new MaterialAlertDialogBuilder(Objects.requireNonNull(getActivity()))
                        .setMessage(R.string.delete_all_question)
                        .setPositiveButton("im sure", (dialog, which) -> mTaskPagerAdapter.removeAllTask())
                        .setNegativeButton(android.R.string.cancel,null)
                        .create()
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_pager_activity, menu);
    }


    private void findViews(View view) {
        mButtonAdd = view.findViewById(R.id.add_button);
        mViewPager = view.findViewById(R.id.viewPager2_tasks);
    }

    private void tabLayoutAndViewPagerBinder(View view) {
        TabLayout tabLayout = view.findViewById(R.id.tabLayout_tasks);
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
            getFragment(TaskState.DOING).removeAllTasks();
            getFragment(TaskState.DONE).removeAllTasks();
            getFragment(TaskState.TODO).removeAllTasks();
        }


        public TaskPagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
            mDoingFragment = TaskListFragment.newInstance(TaskState.DOING, mUser.getUUID());
            mDoneFragment = TaskListFragment.newInstance(TaskState.DONE,mUser.getUUID());
            mToDoFragment = TaskListFragment.newInstance(TaskState.TODO,mUser.getUUID());
        }

        public void notifyLists(Task task) {
            getFragment(TaskState.DONE).notifyAdapter(task);
            getFragment(TaskState.DOING).notifyAdapter(task);
            getFragment(TaskState.TODO).notifyAdapter(task);
        }

        public void deleteTask(Task task){
            getFragment(task.getTaskState()).deleteTask(task);
        }




        public TaskListFragment getFragment(TaskState taskState) {
            FragmentManager fragmentManager = getChildFragmentManager();
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
