package com.example.erfan_delavari_hw13_maktab36.controller.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.erfan_delavari_hw13_maktab36.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.zip.Inflater;


public class DialogSignUpFragment extends DialogFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER_NAME = "userName";
    private static final String ARG_PASSWORD = "password";

    private String mUserName;
    private String mPassword;

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
        View view = inflater.inflate(R.layout.fragment_dialog_sign_up,null);



        return new MaterialAlertDialogBuilder(getContext())
                .setIcon(R.drawable.ic_action_signup)
                .setTitle(R.string.sign_up)
                .setView(view)
                .setPositiveButton(R.string.sign_up, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(android.R.string.cancel,null)
                .create();
    }
}