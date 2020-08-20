package com.example.erfan_delavari_hw14_maktab36.controller.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.erfan_delavari_hw14_maktab36.controller.Fragments.SearchFragment;

public class SearchActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context,SearchActivity.class);
    }

    @Override
    public Fragment fragmentCreator() {
        return SearchFragment.newInstance();
    }

}