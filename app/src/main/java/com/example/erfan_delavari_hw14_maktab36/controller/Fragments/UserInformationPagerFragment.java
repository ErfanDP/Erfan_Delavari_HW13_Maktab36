package com.example.erfan_delavari_hw14_maktab36.controller.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.erfan_delavari_hw14_maktab36.R;
import com.example.erfan_delavari_hw14_maktab36.model.User;
import com.example.erfan_delavari_hw14_maktab36.repository.UserRepositoryInterface;
import com.example.erfan_delavari_hw14_maktab36.repository.UserDBRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserInformationPagerFragment extends Fragment {


    private static final String ARG_USER_ID = "arg_user_id";

    private User mUser;
    private UserRepositoryInterface<User> mRepository;
    private UserViewPagerAdapter mAdapter;

    public static UserInformationPagerFragment newInstance(UUID userId) {
        UserInformationPagerFragment fragment = new UserInformationPagerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = UserDBRepository.getInstance(Objects.requireNonNull(getContext()));
        if (getArguments() != null) {
            mUser = mRepository.getUserByID((UUID) getArguments().getSerializable(ARG_USER_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_pager, container, false);
        ViewPager2 viewPager = view.findViewById(R.id.view_pager_user_information);
        mAdapter = new UserViewPagerAdapter(UserInformationPagerFragment.this,
                mRepository.getUserList());
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(mRepository.getUserPosition(mUser));
        return view;
    }


    private class UserViewPagerAdapter extends FragmentStateAdapter {

        private List<User> mUserList;

        public void setUserList(List<User> userList) {
            mUserList = userList;
        }

        public UserViewPagerAdapter(@NonNull Fragment fragment, List<User> userList) {
            super(fragment);
            mUserList = userList;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return UserInformationFragment.newInstance(mUserList.get(position).getUUID(),
                    (UserInformationFragment.OnButtonClick) () -> {
                        setUserList(mRepository.getUserList());
                        mAdapter.notifyDataSetChanged();
                        Objects.requireNonNull(getActivity()).finish();
                    }
            );
        }

        @Override
        public int getItemCount() {
            return mUserList.size();
        }
    }
}