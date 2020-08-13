package com.example.erfan_delavari_hw13_maktab36.controller.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.erfan_delavari_hw13_maktab36.R;
import com.example.erfan_delavari_hw13_maktab36.model.User;
import com.example.erfan_delavari_hw13_maktab36.repository.UserRepository;

import java.util.List;
import java.util.UUID;

public class UserInformationPagerFragment extends Fragment {


    private static final String ARG_USER_ID = "arg_user_id";

    private User mUser;
    private UserViewPagerAdapter mAdapter;
    private ViewPager2 mViewPager;

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
        if (getArguments() != null) {
            mUser = UserRepository.getRepository().getUserByID((UUID) getArguments().getSerializable(ARG_USER_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_pager, container, false);
        mViewPager = view.findViewById(R.id.view_pager_user_information);
        mAdapter = new UserViewPagerAdapter(UserInformationPagerFragment.this,
                UserRepository.getRepository().getUserList());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(UserRepository.getRepository().getUserPosition(mUser));
        return view;
    }


    private class UserViewPagerAdapter extends FragmentStateAdapter {

        private List<User> mUserList;


        public UserViewPagerAdapter(@NonNull Fragment fragment, List<User> userList) {
            super(fragment);
            mUserList = userList;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return UserInformationFragment.newInstance(mUserList.get(position).getUUID(), new UserInformationFragment.OnButtonClick() {
                @Override
                public void buttonDelete() {
                    getActivity().finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mUserList.size();
        }
    }
}