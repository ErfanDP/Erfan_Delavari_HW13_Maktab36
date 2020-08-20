package com.example.erfan_delavari_hw14_maktab36.controller.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erfan_delavari_hw14_maktab36.R;
import com.example.erfan_delavari_hw14_maktab36.controller.adapters.TaskSearchAdapter;
import com.example.erfan_delavari_hw14_maktab36.model.Task;
import com.example.erfan_delavari_hw14_maktab36.repository.UserDBRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class SearchFragment extends Fragment {

    public static final int REQ_CODE_DATE_FROM = 1;
    public static final String TAG_DATE_PICKER = "tag_date_picker";
    public static final String TAG_TIME_PICKER = "tag_time_picker";
    public static final int REQ_CODE_TIME_FROM = 2;
    public static final int REQ_CODE_TIME_TO = 3;
    public static final int REQ_CODE_DATE_TO = 4;

    private UserDBRepository mRepository;

    private EditText mTextTaskName;
    private EditText mTextTaskDescription;
    private CheckBox mCheckBoxSearchByDate;
    private View mLayoutSearchByDate;
    private Button mButtonDateFrom;
    private Button mButtonTimeFrom;
    private Button mButtonDateTo;
    private Button mButtonTimeTo;
    private FloatingActionButton mButtonSearch;
    private ImageView mImageViewNoResultFound;
    private RecyclerView mRecyclerView;
    private TaskSearchAdapter mAdapter;

    private Date mFromdate;
    private Date mToDate;


    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mRepository = UserDBRepository.getInstance(Objects.requireNonNull(getContext()));
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        findViews(view);
        viewInit();
        mAdapter = new TaskSearchAdapter(new ArrayList<>(),
                () -> mImageViewNoResultFound.setVisibility(View.VISIBLE),
                Objects.requireNonNull(getContext()));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        mRecyclerView.setAdapter(mAdapter);
        listeners();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK || data == null){
            return;
        }

        if(requestCode == REQ_CODE_DATE_FROM){
            Date date = (Date) data.getSerializableExtra(DialogDatePickerFragment.EXTRA_USER_SELECTED_DATE);
            setButtonFromText(date);
        }

        if(requestCode == REQ_CODE_DATE_TO) {
            Date date = (Date) data.getSerializableExtra(DialogDatePickerFragment.EXTRA_USER_SELECTED_DATE);
            setButtonToText(date);
        }

        if(requestCode == REQ_CODE_TIME_TO){
            Date date = (Date) data.getSerializableExtra(DialogTimePickerFragment.EXTRA_USER_SELECTED_TIME);
            setButtonToText(date);
        }

        if(requestCode == REQ_CODE_TIME_FROM){
            Date date = (Date) data.getSerializableExtra(DialogTimePickerFragment.EXTRA_USER_SELECTED_TIME);
            setButtonFromText(date);
        }
    }

    private void listeners() {
        mButtonDateFrom.setOnClickListener(v -> dialogDatePickerCreator(mFromdate, REQ_CODE_DATE_FROM));
        mButtonTimeFrom.setOnClickListener(v -> dialogTimePickerCreator(mFromdate, REQ_CODE_TIME_FROM));
        mButtonTimeTo.setOnClickListener(v -> dialogTimePickerCreator(mToDate, REQ_CODE_TIME_TO));
        mButtonDateTo.setOnClickListener(v -> dialogDatePickerCreator(mToDate, REQ_CODE_DATE_TO));
        mButtonSearch.setOnClickListener(v -> {
            List<Task> tasks;
            if (!mCheckBoxSearchByDate.isChecked()) {
                tasks = mRepository.searchTask(mTextTaskName.getText().toString(),
                        mTextTaskDescription.getText().toString());
            } else {
                tasks = mRepository.searchTask(mTextTaskName.getText().toString(),
                        mTextTaskDescription.getText().toString(),
                        mFromdate.getTime(), mToDate.getTime());
            }
            if(tasks.size() == 0){
                mImageViewNoResultFound.setVisibility(View.VISIBLE);
            }else{
                mImageViewNoResultFound.setVisibility(View.GONE);
            }
            mAdapter.setTaskList(tasks);
            mAdapter.notifyDataSetChanged();
        });

        mCheckBoxSearchByDate.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mLayoutSearchByDate.setVisibility(View.VISIBLE);
            } else
                mLayoutSearchByDate.setVisibility(View.GONE);
        });
    }

    private void dialogTimePickerCreator(Date date, int reqCode) {
        DialogTimePickerFragment dialog = DialogTimePickerFragment.newInstance(date);
        dialog.setTargetFragment(SearchFragment.this, reqCode);
        dialog.show(Objects.requireNonNull(getFragmentManager()), TAG_TIME_PICKER);
    }

    private void dialogDatePickerCreator(Date date, int reqCode) {
        DialogDatePickerFragment dialog = DialogDatePickerFragment.newInstance(date);
        dialog.setTargetFragment(SearchFragment.this, reqCode);
        dialog.show(Objects.requireNonNull(getFragmentManager()), TAG_DATE_PICKER);
    }

    private void viewInit() {
        mFromdate = new Date();
        mToDate = mFromdate;
        setButtonFromText(mFromdate);
        setButtonToText(mToDate);
    }

    private void setButtonFromText(Date date) {
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        mButtonTimeFrom.setText(simpleDateFormatTime.format(date));
        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy.MM.dd", Locale.US);
        mButtonDateFrom.setText(simpleDateFormatDate.format(date));
        mFromdate = date;
    }

    private void setButtonToText(Date date) {
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        mButtonTimeTo.setText(simpleDateFormatTime.format(date));
        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy.MM.dd", Locale.US);
        mButtonDateTo.setText(simpleDateFormatDate.format(date));
        mToDate = date;
    }

    private void findViews(View view) {
        mCheckBoxSearchByDate = view.findViewById(R.id.search_checkBox_search_by_date);
        mButtonDateFrom = view.findViewById(R.id.search_button_from_date);
        mButtonDateTo = view.findViewById(R.id.search_button_to_date);
        mButtonTimeFrom = view.findViewById(R.id.search_button_from_time);
        mButtonTimeTo = view.findViewById(R.id.search_button_to_time);
        mButtonSearch = view.findViewById(R.id.search_button_search);
        mLayoutSearchByDate = view.findViewById(R.id.layout_date_search);
        mTextTaskName = view.findViewById(R.id.search_edit_text_task_name);
        mTextTaskDescription = view.findViewById(R.id.search_edit_text_task_description);
        mRecyclerView = view.findViewById(R.id.search_recycler_view_task_search);
        mImageViewNoResultFound = view.findViewById(R.id.imageView_no_result_found);
    }
}