package com.example.erfan_delavari_hw13_maktab36.controller.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.erfan_delavari_hw13_maktab36.R;
import com.example.erfan_delavari_hw13_maktab36.model.Task;
import com.example.erfan_delavari_hw13_maktab36.model.User;
import com.example.erfan_delavari_hw13_maktab36.repository.UserRepository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

public class DialogTaskInformationFragment extends DialogFragment {

    private static final String ARG_EDITABLE = "editable";
    public static final String ARG_USER_ID = "userID";
    public static final String ARG_TASK_ID = "taskID";
    public static final String TAG_DIALOG_TASK_INFORMATION = "dialogTaskInformation";
    public static final String EXTRA_HAS_CHANGED = "hasChanged";

    private EditText mEditTextName;
    private EditText mEditTextDescription;
    private RadioGroup mRadioGroupTaskState;
    private Button mButtonDate;
    private Button mButtonTime;


    private Task mTask;
    private User mUser;
    private boolean mEditable;

    public static DialogTaskInformationFragment newInstance(boolean editable, UUID userId, UUID taskId) {
        DialogTaskInformationFragment fragment = new DialogTaskInformationFragment();
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
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_dialog_task_information, null);
        findViews(view);
        viewInit();
        editableCheck();
        return materialAlertDialogBuilder(view).create();
    }

    private void editableCheck() {
        if(!mEditable){
            mEditTextName.setEnabled(false);
            mEditTextDescription.setEnabled(false);
            mButtonDate.setEnabled(false);
            mButtonTime.setEnabled(false);
            mRadioGroupTaskState.setEnabled(false);
        }
    }

    private void viewInit() {
        mEditTextName.setText(mTask.getName());
        mEditTextDescription.setText(mTask.getDescription());
        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm:ss",Locale.ENGLISH);
        mButtonDate.setText(simpleDateFormatDate.format(mTask.getDate()));
        mButtonTime.setText(simpleDateFormatTime.format(mTask.getDate()));
        switch (mTask.getTaskState()){
            case TODO:
                mRadioGroupTaskState.check(R.id.radioButton_todo);
                break;
            case DONE:
                mRadioGroupTaskState.check(R.id.radioButton_done);
                break;
            case DOING:
                mRadioGroupTaskState.check(R.id.radioButton_doing);
                break;
        }
    }

    private MaterialAlertDialogBuilder materialAlertDialogBuilder(View view) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity())
                .setPositiveButton(R.string.save, (dialog, which) -> {
                    mUser.update(mTask);
                    setResult(true);
                })
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    setResult(false);
                })
                .setTitle(mEditable ? "Task Edit Table" : "Task Information")
                .setView(view);
        if (!mEditable) {
            materialAlertDialogBuilder.setNeutralButton(R.string.edit, (dialog, which) -> {
                DialogTaskInformationFragment.newInstance(true,mUser.getUUID(),mTask.getUUID())
                        .show(getFragmentManager(), TAG_DIALOG_TASK_INFORMATION);
                dismiss();
            });
        }
        return materialAlertDialogBuilder;
    }

    private void setResult(boolean hasChanged) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_HAS_CHANGED,hasChanged);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
    }

    private void findViews(View view) {
        mButtonDate = view.findViewById(R.id.button_date);
        mButtonTime = view.findViewById(R.id.button_time);
        mEditTextDescription = view.findViewById(R.id.tasks_Description);
        mEditTextName = view.findViewById(R.id.tasks_name);
        mRadioGroupTaskState = view.findViewById(R.id.radioGroup_task_state);
    }
}