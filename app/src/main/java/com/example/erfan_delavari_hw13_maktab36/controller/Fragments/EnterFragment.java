package com.example.erfan_delavari_hw13_maktab36.controller.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.erfan_delavari_hw13_maktab36.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EnterFragment extends Fragment {
    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private FloatingActionButton mButtonDone;
    private FloatingActionButton mButtonSignUp;

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
        listeners();
        return view;
    }

    private void listeners() {

        mButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO start the activity with users UUID
            }
        });

        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSignUpFragment.newInstance(mEditTextUserName.getText().toString()
                        ,mEditTextPassword.getText().toString()).show(getFragmentManager(),"TaG");
            }
        });
    }

    private void findViews(View view) {
        mEditTextUserName = view.findViewById(R.id.entry_edit_text_user_name);
        mEditTextPassword = view.findViewById(R.id.entry_edit_text_password);
        mButtonDone = view.findViewById(R.id.entry_button_done);
        mButtonSignUp = view.findViewById(R.id.button_sign_up);
    }
}