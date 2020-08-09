package com.example.erfan_delavari_hw13_maktab36.controller.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.erfan_delavari_hw13_maktab36.R;
import com.example.erfan_delavari_hw13_maktab36.controller.Activity.TaskPagerActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EnterFragment extends Fragment {
    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private FloatingActionButton mButtonDone;
    //TODO add a list and get it from Repository

    public static EnterFragment newInstance() {
        return new EnterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_entery, container, false);
        findViews(view);
        mButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO start the activity with users UUID
            }
        });
        return view;
    }

    private void findViews(View view) {
        mEditTextUserName = view.findViewById(R.id.entry_edit_text_name);
        mEditTextPassword = view.findViewById(R.id.entry_edit_text_number_of_tasks);
        mButtonDone = view.findViewById(R.id.entry_button_done);
    }
}