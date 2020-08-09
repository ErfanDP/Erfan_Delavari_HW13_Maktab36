package com.example.erfan_delavari_hw13_maktab36.controller.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;

import com.example.erfan_delavari_hw13_maktab36.R;
import com.example.erfan_delavari_hw13_maktab36.model.Task;
import com.example.erfan_delavari_hw13_maktab36.model.User;
import com.example.erfan_delavari_hw13_maktab36.repository.UserRepository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.UUID;

public class TaskInformationFragment extends DialogFragment {

    private static final String ARG_EDITABLE = "editable";
    public static final String ARG_USER_ID = "userID";
    public static final String ARG_TASK_ID = "taskID";

    private Task mTask;
    private User mUser;
    private boolean mEditable;

    public static TaskInformationFragment newInstance(boolean editable, UUID userId, UUID taskId) {
        TaskInformationFragment fragment = new TaskInformationFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_EDITABLE, editable);
        args.putSerializable(ARG_USER_ID, userId);
        args.putSerializable(ARG_TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEditable = getArguments().getBoolean(ARG_EDITABLE);
            mUser = UserRepository.getRepository().get((UUID) getArguments().getSerializable(ARG_USER_ID));
            mTask = mUser.get((UUID) getArguments().getSerializable(ARG_TASK_ID));

            if (mTask == null) {
                mEditable = true;
            } else {
                mEditable = false;
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_dialog_task_information, null);
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity())
                .setPositiveButton(R.string.save, (dialog, which) -> {
                    //Method
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setTitle(mEditable ? "Task Edit Table" : "Task Information");
        if (!mEditable) {
            materialAlertDialogBuilder.setNeutralButton(R.string.edit, new Dialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mEditable = true;
                }
            });
        }
        return materialAlertDialogBuilder.create();
    }
}