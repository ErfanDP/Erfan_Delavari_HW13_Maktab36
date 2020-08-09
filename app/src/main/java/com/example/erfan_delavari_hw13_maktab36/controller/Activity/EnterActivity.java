package com.example.erfan_delavari_hw13_maktab36.controller.Activity;

import androidx.fragment.app.Fragment;

import com.example.erfan_delavari_hw13_maktab36.controller.Fragments.EnterFragment;

public class EnterActivity extends SingleFragmentActivity{


    @Override
    public Fragment fragmentCreator() {
        return EnterFragment.newInstance();
    }
}