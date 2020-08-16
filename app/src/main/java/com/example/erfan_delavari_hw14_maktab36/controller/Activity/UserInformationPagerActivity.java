package com.example.erfan_delavari_hw14_maktab36.controller.Activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.erfan_delavari_hw14_maktab36.controller.Fragments.UserInformationPagerFragment;

import java.util.UUID;

public class UserInformationPagerActivity extends SingleFragmentActivity {

    public static final String EXTRA_USER_ID = "extra_user_id";

    public static Intent newIntent(Context context, UUID userId){
        Intent intent = new Intent(context,UserInformationPagerActivity.class);
        intent.putExtra(EXTRA_USER_ID,userId);
        return intent;
    }


    @Override
    public Fragment fragmentCreator() {
        return UserInformationPagerFragment.newInstance((UUID) getIntent().getSerializableExtra(EXTRA_USER_ID));
    }

}