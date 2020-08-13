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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DialogTaskInformationFragment extends DialogFragment {

    private static final String ARG_EDITABLE = "editable";
    public static final String ARG_TASK_ID = "taskID";
    public static final String EXTRA_HAS_CHANGED = "hasChanged";
    public static final String EXTRA_TASK = "extra_task";
    public static final String EXTRA_DELETED = "extra_deleted";
    public static final int REQ_CODE_DATE_PICKER = 10;
    public static final String TAG_DATE_PICKER = "tag_date_picker";
    public static final String TAG_TIME_PICKER = "tag_time_picker";
    public static final int REQ_CODE_TIME_PICKER = 12;

    private EditText mEditTextName;
    private EditText mEditTextDescription;
    private RadioGroup mRadioGroupTaskState;
    private Button mButtonDate;
    private Button mButtonTime;

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQ_CODE_DATE_PICKER) {
            Date userSelectedDate = (Date) data.getSerializableExtra(DialogDatePickerFragment.EXTRA_USER_SELECTED_DATE);
            mTask.setDate(userSelectedDate);
            setButtonDateText(mTask.getDate());
        }

        if(requestCode == REQ_CODE_TIME_PICKER){
            Date userSelectedTime = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_USER_SELECTED_TIME);
            mTask.setDate(userSelectedTime);
            setButtonTimeText(mTask.getDate());
        }
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_dialog_task_information, null);
        findViews(view);
        mButtonDate.setOnClickListener(v -> {
            DialogDatePickerFragment datePickerFragment = DialogDatePickerFragment.newInstance(mTask.getDate());
            datePickerFragment.setTargetFragment(DialogTaskInformationFragment.this, REQ_CODE_DATE_PICKER);
            datePickerFragment.show(Objects.requireNonNull(getFragmentManager()), TAG_DATE_PICKER);
        });

        mButtonTime.setOnClickListener(v -> {
            TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mTask.getDate());
            timePickerFragment.setTargetFragment(DialogTaskInformationFragment.this, REQ_CODE_TIME_PICKER);
            timePickerFragment.show(Objects.requireNonNull(getFragmentManager()), TAG_TIME_PICKER);
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
        setButtonDateText(mTask.getDate());
        setButtonTimeText(mTask.getDate());
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
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(Objects.requireNonNull(getActivity()))

                .setNegativeButton(android.R.string.cancel, null)
                .setTitle(mEditable ? "Task Edit Table" : "Task Information")
                .setView(view);
        if (!mEditable) {
            materialAlertDialogBuilder
                    .setPositiveButton(R.string.edit, (dialog, which) -> {
                        TaskPagerFragment.creatingDialogTaskInformation(mTask, getTargetFragment()
                                , true, getTargetRequestCode());
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
        Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    private void setButtonTimeText(Date date) {
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        mButtonTime.setText(simpleDateFormatTime.format(date));
    }

    private void setButtonDateText(Date date) {
        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy.MMMMM.dd", Locale.US);
        mButtonDate.setText(simpleDateFormatDate.format(date));
    }
    private void findViews(View view) {
        mButtonDate = view.findViewById(R.id.button_date);
        mButtonTime = view.findViewById(R.id.button_time);
        mEditTextDescription = view.findViewById(R.id.tasks_Description);
        mEditTextName = view.findViewById(R.id.tasks_name);
        mRadioGroupTaskState = view.findViewById(R.id.radioGroup_task_state);
    }
}