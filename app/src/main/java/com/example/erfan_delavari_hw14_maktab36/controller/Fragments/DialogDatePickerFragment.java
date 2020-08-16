package com.example.erfan_delavari_hw14_maktab36.controller.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.erfan_delavari_hw14_maktab36.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;


public class DialogDatePickerFragment extends DialogFragment {

    public static final String ARG_DATE = "currentDate";
    public static final String EXTRA_USER_SELECTED_DATE = "com.example.criminalintent.userSelectedDate";

    private Date mCurrentDate;
    private Calendar mCalendar;
    private DatePicker mDatePicker;


    public static DialogDatePickerFragment newInstance (Date currentDate) {
        DialogDatePickerFragment fragment = new DialogDatePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, currentDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCurrentDate = (Date) getArguments().getSerializable(ARG_DATE);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_dialog_date_picker, null);

        findViews(view);
        initDatePicker();

        return new MaterialAlertDialogBuilder(Objects.requireNonNull(getActivity()))
                .setView(view)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    Date datePicked = getSelectedDateFromDatePicker();
                    setResult(datePicked);
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private void findViews(View view) {
        mDatePicker = view.findViewById(R.id.date_picker);
    }

    private void initDatePicker() {
        //convert date to calendar
        mCalendar = Calendar.getInstance();
        mCalendar.setTime(mCurrentDate);
        int year = mCalendar.get(Calendar.YEAR);
        int monthOfYear = mCalendar.get(Calendar.MONTH);
        int dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
        mDatePicker.init(year, monthOfYear, dayOfMonth, null);
    }

    private Date getSelectedDateFromDatePicker() {
        int year = mDatePicker.getYear();
        int monthOfYear = mDatePicker.getMonth();
        int dayOfMonth = mDatePicker.getDayOfMonth();
        int hourOfDay = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar .get(Calendar.MINUTE);
        GregorianCalendar gregorianCalendar =
                new GregorianCalendar(year, monthOfYear, dayOfMonth,hourOfDay,minute);
        return gregorianCalendar.getTime();
    }

    private void setResult(Date userSelectedDate) {
        Fragment fragment = getTargetFragment();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_USER_SELECTED_DATE, userSelectedDate);
        if (fragment != null) {
            fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        }
    }
}