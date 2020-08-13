package com.example.erfan_delavari_hw13_maktab36.controller.Activity;

import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.Fragment;

import com.example.erfan_delavari_hw13_maktab36.controller.Fragments.AdminFragment;

public class AdminActivity extends SingleFragmentActivity {
    public static Intent newIntent(Context context) {
        return new Intent(context,AdminActivity.class);
    }

    @Override
    public Fragment fragmentCreator() {
        return AdminFragment.newInstance();
    }
}
