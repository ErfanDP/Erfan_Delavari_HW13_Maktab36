package com.example.erfan_delavari_hw11_maktab36.controller.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.erfan_delavari_hw11_maktab36.R;
import com.example.erfan_delavari_hw11_maktab36.controller.Activity.TaskListActivity;

public class EnterFragment extends Fragment {
    private EditText mEditTextName;
    private EditText mEditTextNumberOfTasks;
    private ImageButton mButtonDone;

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
                startActivity(TaskListActivity.newIntent(getActivity(),
                        mEditTextName.getText().toString(),
                        Integer.parseInt(mEditTextNumberOfTasks.getText().toString())));
            }
        });
        return view;
    }

    private void findViews(View view) {
        mEditTextName = view.findViewById(R.id.entry_edit_text_name);
        mEditTextNumberOfTasks = view.findViewById(R.id.entry_edit_text_number_of_tasks);
        mButtonDone = view.findViewById(R.id.entry_button_done);
    }
}