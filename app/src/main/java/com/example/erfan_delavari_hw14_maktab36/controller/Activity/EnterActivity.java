package com.example.erfan_delavari_hw14_maktab36.controller.Activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.erfan_delavari_hw14_maktab36.controller.Fragments.EntryFragment;

public class EnterActivity extends SingleFragmentActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Fragment fragmentCreator() {
        return EntryFragment.newInstance();
    }
}