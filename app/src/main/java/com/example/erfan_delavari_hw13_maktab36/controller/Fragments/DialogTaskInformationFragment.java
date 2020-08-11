package com.example.erfan_delavari_hw13_maktab36.controller.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.erfan_delavari_hw13_maktab36.R;
import com.example.erfan_delavari_hw13_maktab36.model.Task;
import com.example.erfan_delavari_hw13_maktab36.model.TaskState;
import com.example.erfan_delavari_hw13_maktab36.model.User;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DialogTaskInformationFragment extends DialogFragment {

    private static final String ARG_EDITABLE = "editable";
    public static final String ARG_TASK_ID = "taskID";
    public static final String EXTRA_HAS_CHANGED = "hasChanged";
    public static final String EXTRA_TASK = "extra_task";
    public static final String EXTRA_DELETED = "extra_deleted";

    private EditText mEditTextName;
    private EditText mEditTextDescription;
    private RadioGroup mRadioGroupTaskState;
    private Button mButtonDate;
    private Button mButtonTime;

    private User mUser;
    private Task mTask;
    private boolean mEditable;

    public static DialogTaskInformationFragment newInstance(boolean editable, Task task) {
        DialogTaskInformationFragment fragment = new DialogTaskInformationFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_EDITABLE, editable);
        args.putSerializable(ARG_TASK_ID, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEditable = getArguments().getBoolean(ARG_EDITABLE);
            mTask = (Task) getArguments().getSerializable(ARG_TASK_ID);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_dialog_task_information, null);
        findViews(view);
        mButtonDate.setOnClickListener(v -> {
           //TODO datePiker  fragment
        });

        mButtonTime.setOnClickListener(v -> {
            //TODO timePiker fragment
        });
        viewInit();
        editableCheck(view);
        return materialAlertDialogBuilder(view).create();
    }

    private void editableCheck(View view) {
        if (!mEditable) {
            mEditTextName.setEnabled(false);
            mEditTextDescription.setEnabled(false);
            mButtonDate.setEnabled(false);
            mButtonTime.setEnabled(false);
            mRadioGroupTaskState.setEnabled(false);
            view.findViewById(R.id.radioButton_doing).setEnabled(false);
            view.findViewById(R.id.radioButton_done).setEnabled(false);
            view.findViewById(R.id.radioButton_todo).setEnabled(false);
        }
    }

    private void viewInit() {
        mEditTextName.setText(mTask.getName());
        mEditTextDescription.setText(mTask.getDescription());
        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        mButtonDate.setText(simpleDateFormatDate.format(mTask.getDate()));
        mButtonTime.setText(simpleDateFormatTime.format(mTask.getDate()));
        switch (mTask.getTaskState()) {
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

                .setNegativeButton(android.R.string.cancel, null)
                .setTitle(mEditable ? "Task Edit Table" : "Task Information")
                .setView(view);
        if (!mEditable) {
            materialAlertDialogBuilder
                    .setPositiveButton(R.string.edit, (dialog, which) -> {
                        TaskPagerFragment.creatingDialogTaskInformation(mTask, getTargetFragment()
                                , true, TaskPagerFragment.REQUEST_CODE_TASK_INFORMATION_EDIT);
                        setResult(false, Activity.RESULT_CANCELED, false);
                    })
                    .setNeutralButton(R.string.delete, (dialog, which) ->
                            setResult(false, Activity.RESULT_OK, true));
        } else {
            materialAlertDialogBuilder.setPositiveButton(R.string.save, (dialog, which) -> {
                mTask.setName(mEditTextName.getText().toString());
                mTask.setDescription(mEditTextDescription.getText().toString());
                switch (mRadioGroupTaskState.getCheckedRadioButtonId()) {
                    case R.id.radioButton_doing:
                        mTask.setTaskState(TaskState.DOING);
                        break;
                    case R.id.radioButton_done:
                        mTask.setTaskState(TaskState.DONE);
                        break;
                    default:
                        mTask.setTaskState(TaskState.TODO);
                }
                setResult(true, Activity.RESULT_OK, false);
            });
        }
        return materialAlertDialogBuilder;
    }

    private void setResult(boolean hasChanged, int resultCode, boolean deleted) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_HAS_CHANGED, hasChanged);
        intent.putExtra(EXTRA_TASK, mTask);
        intent.putExtra(EXTRA_DELETED, deleted);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    private void findViews(View view) {
        mButtonDate = view.findViewById(R.id.button_date);
        mButtonTime = view.findViewById(R.id.button_time);
        mEditTextDescription = view.findViewById(R.id.tasks_Description);
        mEditTextName = view.findViewById(R.id.tasks_name);
        mRadioGroupTaskState = view.findViewById(R.id.radioGroup_task_state);
    }
}