package com.example.erfan_delavari_hw14_maktab36.controller.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.example.erfan_delavari_hw14_maktab36.R;
import com.example.erfan_delavari_hw14_maktab36.controller.utils.PictureUtils;
import com.example.erfan_delavari_hw14_maktab36.model.Task;
import com.example.erfan_delavari_hw14_maktab36.model.TaskState;
import com.example.erfan_delavari_hw14_maktab36.repository.UserDBRepository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
    public static final int REQ_CODE_SELECT_PICTURE = 1;
    public static final String FILE_PROVIDER_AUTHORITIES= "com.example.erfan_delavari_hw14_maktab36.fileProvider";
    public static final int REQ_CODE_CAPTURE_PIC = 2;

    private EditText mEditTextName;
    private EditText mEditTextDescription;
    private RadioGroup mRadioGroupTaskState;
    private Button mButtonDate;
    private Button mButtonTime;
    private Button mButtonShare;
    private File mFilePicture;
    private ImageView mImageViewTask;
    private UserDBRepository mRepository;

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
        mRepository = UserDBRepository.getInstance(Objects.requireNonNull(getContext()));
        if (getArguments() != null) {
            mEditable = getArguments().getBoolean(ARG_EDITABLE);
            mTask = (Task) getArguments().getSerializable(ARG_TASK_ID);
        }
        mFilePicture = mRepository.getPhotoFile(getContext(),mTask);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQ_CODE_DATE_PICKER) {
            Date userSelectedDate = (Date) data.getSerializableExtra(DialogDatePickerFragment.EXTRA_USER_SELECTED_DATE);
            mTask.setDate(userSelectedDate);
            setButtonDateText(mTask.getDate());
        }else if(requestCode == REQ_CODE_TIME_PICKER){
            Date userSelectedTime = (Date) data.getSerializableExtra(DialogTimePickerFragment.EXTRA_USER_SELECTED_TIME);
            mTask.setDate(userSelectedTime);
            setButtonTimeText(mTask.getDate());
        } else if(requestCode == REQ_CODE_SELECT_PICTURE) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                try (FileOutputStream out = new FileOutputStream(mFilePicture)) {
                    selectedImage.compress(Bitmap.CompressFormat.PNG, 100, out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mImageViewTask.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
            updatePhotoView();

        }else if (requestCode == REQ_CODE_CAPTURE_PIC) {
            updatePhotoView();
            Uri photoUri = FileProvider.getUriForFile(
                    getActivity(),
                    FILE_PROVIDER_AUTHORITIES,
                    mFilePicture);
            getActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_dialog_task_information, null);
        findViews(view);
        listeners();
        viewInit();
        editableCheck(view);

        return materialAlertDialogBuilder(view).create();
    }

    private void listeners() {
        mButtonDate.setOnClickListener(v -> {
            DialogDatePickerFragment datePickerFragment = DialogDatePickerFragment.newInstance(mTask.getDate());
            datePickerFragment.setTargetFragment(DialogTaskInformationFragment.this, REQ_CODE_DATE_PICKER);
            datePickerFragment.show(Objects.requireNonNull(getFragmentManager()), TAG_DATE_PICKER);
        });

        mButtonTime.setOnClickListener(v -> {
            DialogTimePickerFragment timePickerFragment = DialogTimePickerFragment.newInstance(mTask.getDate());
            timePickerFragment.setTargetFragment(DialogTaskInformationFragment.this, REQ_CODE_TIME_PICKER);
            timePickerFragment.show(Objects.requireNonNull(getFragmentManager()), TAG_TIME_PICKER);
        });
        mButtonShare.setOnClickListener(v -> {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getShareText());
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.text_share_subject));
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null)
                startActivity(shareIntent);
        });

        if(mEditable){
            mImageViewTask.setOnClickListener(new View.OnClickListener() {
                String[] items = new String[]{"Select From Gallery", "Take a picture"};
                @Override
                public void onClick(View v) {
                    new MaterialAlertDialogBuilder(getContext())
                            .setItems(items, (dialog, which) -> {
                                switch (which){
                                    case 0:
                                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                        if (photoPickerIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                            if (mFilePicture == null)
                                                return;
                                            photoPickerIntent.setType("image/*");
                                            startActivityForResult(photoPickerIntent, REQ_CODE_SELECT_PICTURE);
                                        }
                                        break;
                                    case 1:
                                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                            if (mFilePicture == null)
                                                return;
                                            Uri photoURI = FileProvider.getUriForFile(
                                                    getActivity(),
                                                    FILE_PROVIDER_AUTHORITIES,
                                                    mFilePicture);
                                            grantTemPermissionForTakePicture(takePictureIntent, photoURI);
                                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                            startActivityForResult(takePictureIntent, REQ_CODE_CAPTURE_PIC);
                                        }
                                        break;
                                }
                            }).create().show();
                }
            });
        }
    }

    private void grantTemPermissionForTakePicture(Intent takePictureIntent, Uri photoURI) {
        PackageManager packageManager = Objects.requireNonNull(getActivity()).getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(
                takePictureIntent,
                PackageManager.MATCH_DEFAULT_ONLY);

        for (ResolveInfo activity: activities) {
            getActivity().grantUriPermission(activity.activityInfo.packageName,
                    photoURI,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }

    private String getShareText(){
        return getString(R.string.text_share
                ,mTask.getName()
                ,mTask.getDescription()
                ,mTask.getTaskState().toString()
                ,mTask.getDate().toString());
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
        }else{
            mButtonShare.setVisibility(View.GONE);
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
        ViewTreeObserver observer = mImageViewTask.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(() -> updatePhotoView());
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
        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy.MM.dd", Locale.US);
        mButtonDate.setText(simpleDateFormatDate.format(date));
    }
    private void findViews(View view) {
        mButtonDate = view.findViewById(R.id.button_date);
        mButtonTime = view.findViewById(R.id.button_time);
        mEditTextDescription = view.findViewById(R.id.tasks_Description);
        mEditTextName = view.findViewById(R.id.tasks_name);
        mRadioGroupTaskState = view.findViewById(R.id.radioGroup_task_state);
        mButtonShare = view.findViewById(R.id.button_share);
        mImageViewTask = view.findViewById(R.id.imageView_Task);
    }

    private void updatePhotoView() {
        if (mFilePicture == null || !mFilePicture.exists()) {
            mImageViewTask.setImageDrawable(getResources().getDrawable(R.drawable.ic_task));
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mFilePicture.getPath(),mImageViewTask);
            mImageViewTask.setImageBitmap(bitmap);
        }
    }

}