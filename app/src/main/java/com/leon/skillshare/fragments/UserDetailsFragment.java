package com.leon.skillshare.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.leon.skillshare.R;
import com.leon.skillshare.domain.User;
import com.leon.skillshare.viewmodels.UserDetailsViewModel;

public class UserDetailsFragment extends Fragment {

    private TextView fullNameTv;
    private TextView emailTv;
    private TextView collegeTv;
    private ListView takingCoursesLv;
    private ListView offeringCoursesLv;
    private ProgressBar takingCoursesProgressBar;
    private ProgressBar offeringCoursesProgressBar;
    private ProgressBar userDetailsProgressBar;

    private UserDetailsViewModel userDetailsVm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);

        bindWidgetsOfView(view);
        userDetailsVm = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
        populateData();

        return view;
    }

    private void populateData() {
        populateUserDetails();
        populateTakingCoursesList();
        populateOfferingCoursesList();
    }

    private void populateTakingCoursesList() {
        takingCoursesProgressBar.setVisibility(View.VISIBLE);
    }

    private void populateOfferingCoursesList() {
        offeringCoursesProgressBar.setVisibility(View.VISIBLE);
    }

    private void populateUserDetails() {
        userDetailsProgressBar.setVisibility(View.VISIBLE);
        userDetailsVm.getCurrentUser().observe(this, new Observer<User>() {

            @Override
            public void onChanged(@Nullable User user) {
                userDetailsProgressBar.setVisibility(View.INVISIBLE);
                fullNameTv.setText(user.getFullName());
                emailTv.setText(user.getEmail());
                collegeTv.setText(user.getCollege());
            }
        });

    }

    private void bindWidgetsOfView(View view) {
        fullNameTv = view.findViewById(R.id.fragment_user_details_fullname_value);
        emailTv = view.findViewById(R.id.fragment_user_details_email_value);
        collegeTv = view.findViewById(R.id.fragment_user_details_college_value);
        takingCoursesLv = view.findViewById(R.id.fragment_user_details_taking_courses_lv);
        offeringCoursesLv = view.findViewById(R.id.fragment_user_details_offering_courses_lv);
        takingCoursesProgressBar = view.findViewById(R.id.fragment_user_details_taking_courses_progress_bar);
        offeringCoursesProgressBar = view.findViewById(R.id.fragment_user_details_offering_courses_progress_bar);
        userDetailsProgressBar = view.findViewById(R.id.fragment_user_details_progress_bar);
    }


}
