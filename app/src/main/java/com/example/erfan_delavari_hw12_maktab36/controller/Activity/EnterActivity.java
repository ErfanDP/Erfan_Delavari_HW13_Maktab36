package com.example.erfan_delavari_hw12_maktab36.controller.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.erfan_delavari_hw12_maktab36.R;
import com.example.erfan_delavari_hw12_maktab36.controller.Fragments.EnterFragment;

public class EnterActivity extends SingleFragmentActivity{


    @Override
    public Fragment fragmentCreator() {
        return EnterFragment.newInstance();
    }
}