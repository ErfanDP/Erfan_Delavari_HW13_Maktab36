package com.example.erfan_delavari_hw13_maktab36.controller.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.erfan_delavari_hw13_maktab36.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class TimePickerFragment extends DialogFragment {

    private static final String ARG_DATE = "param1";

    private Date mCurrentDate;
    private TimePicker mTimePicker;
    private GregorianCalendar mCalendar;

    public static final String EXTRA_USER_SELECTED_TIME = "com.example.criminalintent.userSelectedTime";

    public static TimePickerFragment newInstance(Date date) {
        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
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


    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_dialog_time_picker, null);
        findViews(view);
        timePickerAndCalendarInit();
        return new MaterialAlertDialogBuilder(Objects.requireNonNull(getActivity()))
                .setView(view)
                .setMessage("Time Picker")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    Date date = getSelectedDateFromDatePicker();
                    setResult(date);
                }).create();
    }

    private void timePickerAndCalendarInit() {
        mCalendar = new GregorianCalendar();
        mCalendar.setTime(mCurrentDate);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTimePicker.setHour(mCalendar.get(Calendar.HOUR_OF_DAY));
        } else {
            mTimePicker.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTimePicker.setMinute(mCalendar.get(Calendar.MINUTE));
        } else {
            mTimePicker.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
        }
    }


    private void findViews(View view) {
        mTimePicker = view.findViewById(R.id.time_picker);
    }

    private void setResult(Date date) {
        Log.d("TPF", mCurrentDate.toString());
        Fragment fragment = getTargetFragment();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_USER_SELECTED_TIME, date);
        Objects.requireNonNull(fragment).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Date getSelectedDateFromDatePicker() {
        int year = (int) (mCalendar.get(Calendar.YEAR) - 1900.);
        int monthOfYear = mCalendar.get(Calendar.MONTH);
        int dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = mTimePicker.getHour();
        int minute = mTimePicker.getMinute();
        GregorianCalendar gregorianCalendar =
                new GregorianCalendar(year, monthOfYear, dayOfMonth, hourOfDay, minute);
        return gregorianCalendar.getTime();
    }
}