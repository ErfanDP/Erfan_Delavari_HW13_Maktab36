package com.example.erfan_delavari_hw13_maktab36.controller.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erfan_delavari_hw13_maktab36.R;
import com.example.erfan_delavari_hw13_maktab36.controller.Activity.UserInformationPagerActivity;
import com.example.erfan_delavari_hw13_maktab36.controller.adapters.UserListAdapter;
import com.example.erfan_delavari_hw13_maktab36.repository.UserRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class AdminFragment extends Fragment {

    public static final String TAG_SIGN_UP_USER = "tag_sign_up_user";
    public static final int REQUEST_CODE_SIGN_UP = 21;
    private UserRepository mRepository;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mButtonAddUser;
    private ImageView mNoUserFound;
    private UserListAdapter mAdapter;


    public static AdminFragment newInstance() {
        AdminFragment fragment = new AdminFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = UserRepository.getRepository();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        findViews(view);
        listeners();
        recyclerViewInit();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK || data == null){
            return;
        }

        if(requestCode == REQUEST_CODE_SIGN_UP){
            imageNoUserVisibility();
        }
    }

    private void imageNoUserVisibility() {
        if(mRepository.getUserList().size() == 0){
            mNoUserFound.setVisibility(View.VISIBLE);
        }else{
            mNoUserFound.setVisibility(View.GONE);
        }
    }

    private void recyclerViewInit() {
        int rowNumbers = 1;
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rowNumbers = 2;
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), rowNumbers));
        mAdapter = new UserListAdapter(mRepository.getUserList()
                , () -> mNoUserFound.setVisibility(View.VISIBLE)
                , user -> startActivity(UserInformationPagerActivity
                .newIntent(getActivity(),user.getUUID())));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void listeners() {
        mButtonAddUser.setOnClickListener(v -> {
            DialogSignUpFragment dialogSignUpFragment = DialogSignUpFragment.newInstance("", "");
            dialogSignUpFragment.setTargetFragment(AdminFragment.this, REQUEST_CODE_SIGN_UP);
            dialogSignUpFragment.show(Objects.requireNonNull(getFragmentManager()), TAG_SIGN_UP_USER);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
        imageNoUserVisibility();
    }

    private void findViews(View view) {
        mButtonAddUser = view.findViewById(R.id.admin_button_add_user);
        mRecyclerView = view.findViewById(R.id.recycler_view_users_list);
        mNoUserFound = view.findViewById(R.id.imageView_no_user_found);
    }


}