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
import com.example.erfan_delavari_hw13_maktab36.controller.Fragments.DialogTaskInformationFragment;
import com.example.erfan_delavari_hw13_maktab36.controller.Fragments.TaskListFragment;
import com.example.erfan_delavari_hw13_maktab36.controller.Fragments.TaskPagerFragment;
import com.example.erfan_delavari_hw13_maktab36.model.Task;
import com.example.erfan_delavari_hw13_maktab36.model.TaskState;
import com.example.erfan_delavari_hw13_maktab36.model.User;
import com.example.erfan_delavari_hw13_maktab36.repository.UserRepository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.UUID;

public class TaskPagerActivity extends SingleFragmentActivity {

    public static final String EXTRA_USER_ID = "com.example.erfan_delavari_hw13_maktab36.user_ID";

    public static Intent newIntent(Context context, UUID userID) {
        Intent intent = new Intent(context, TaskPagerActivity.class);
        intent.putExtra(EXTRA_USER_ID,userID);
        return intent;
    }


    @Override
    public Fragment fragmentCreator() {
        return TaskPagerFragment.newInstance((UUID) getIntent().getSerializableExtra(EXTRA_USER_ID));
    }
}