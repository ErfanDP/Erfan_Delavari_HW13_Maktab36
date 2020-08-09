package com.example.erfan_delavari_hw13_maktab36.controller.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.erfan_delavari_hw13_maktab36.R;
import com.example.erfan_delavari_hw13_maktab36.model.User;
import com.example.erfan_delavari_hw13_maktab36.repository.UserRepository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class DialogSignUpFragment extends DialogFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER_NAME = "userName";
    private static final String ARG_PASSWORD = "password";

    private String mUserName;
    private String mPassword;

    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private EditText mEditTextNumberOFTasks;

    public static DialogSignUpFragment newInstance(String userName, String password) {
        DialogSignUpFragment fragment = new DialogSignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_NAME, userName);
        args.putString(ARG_PASSWORD, password);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserName = getArguments().getString(ARG_USER_NAME);
            mPassword = getArguments().getString(ARG_PASSWORD);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_dialog_sign_up, null);

        findViews(view);
        viewInit();

        return new MaterialAlertDialogBuilder(getActivity())
                .setIcon(R.drawable.ic_action_signup)
                .setTitle(R.string.sign_up)
                .setView(view)
                .setPositiveButton(R.string.sign_up, (dialog, which) -> {
                            String numberOfTasks = mEditTextNumberOFTasks.getText().toString();
                            UserRepository.getRepository().insert(
                                    new User(mEditTextUserName.getText().toString()
                                            ,mEditTextPassword.getText().toString()
                                            ,numberOfTasks.equals("")?0:Integer.parseInt(numberOfTasks)));
                        })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private void viewInit() {
        mEditTextUserName.setText(mUserName);
        mEditTextPassword.setText(mPassword);
    }

    private void findViews(View view) {
        mEditTextUserName = view.findViewById(R.id.entry_sign_up_edit_text_user_name);
        mEditTextPassword = view.findViewById(R.id.entry_sign_up_edit_text_password);
        mEditTextNumberOFTasks = view.findViewById(R.id.entry_sign_up_edit_text_number_of_tasks);
    }
}