package com.example.erfan_delavari_hw13_maktab36.controller.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.erfan_delavari_hw13_maktab36.R;
import com.example.erfan_delavari_hw13_maktab36.controller.Activity.TaskPagerActivity;
import com.example.erfan_delavari_hw13_maktab36.model.User;
import com.example.erfan_delavari_hw13_maktab36.repository.UserRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class EntryFragment extends Fragment {

    public static final String TAG_DIALOG_SIGN_UP = "DialogSignUp";

    private List<User> mUserList = UserRepository.getRepository().getList();

    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private FloatingActionButton mButtonDone;
    private FloatingActionButton mButtonSignUp;

    public static EntryFragment newInstance() {
        return new EntryFragment();
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

        mButtonDone.setOnClickListener(v -> {
            boolean userFound = false;
            for(User user : mUserList){
                if(user.loginCheck(mEditTextUserName.getText().toString(),mEditTextPassword.getText().toString())){
                    Intent intent = TaskPagerActivity.newIntent(getActivity(),user.getUUID());
                    startActivity(intent);
                    userFound = true;
                }
            }
            if(!userFound)
                Toast.makeText(getActivity(), R.string.user_not_found,Toast.LENGTH_SHORT).show();
        });
        mButtonSignUp.setOnClickListener(v ->
                DialogSignUpFragment.newInstance(mEditTextUserName.getText().toString()
                ,mEditTextPassword.getText().toString()).show(getFragmentManager(), TAG_DIALOG_SIGN_UP));
    }

    private void findViews(View view) {
        mEditTextUserName = view.findViewById(R.id.entry_edit_text_user_name);
        mEditTextPassword = view.findViewById(R.id.entry_edit_text_password);
        mButtonDone = view.findViewById(R.id.entry_button_done);
        mButtonSignUp = view.findViewById(R.id.button_sign_up);
    }
}