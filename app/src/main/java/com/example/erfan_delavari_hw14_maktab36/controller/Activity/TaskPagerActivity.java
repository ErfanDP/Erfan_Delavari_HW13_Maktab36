package com.example.erfan_delavari_hw14_maktab36.controller.Activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.erfan_delavari_hw14_maktab36.controller.Fragments.TaskPagerFragment;

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