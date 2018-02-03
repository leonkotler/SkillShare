package com.leon.skillshare.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.leon.skillshare.R;
import com.leon.skillshare.domain.Course;
import com.leon.skillshare.domain.ServerRequest;
import com.leon.skillshare.viewmodels.OfferCourseViewModel;
import com.leon.skillshare.viewmodels.UserDetailsViewModel;

import java.util.ArrayList;
import java.util.UUID;


public class OfferCourseFragment extends Fragment implements View.OnClickListener {

    private EditText courseNameEt;
    private EditText courseDescriptionEt;
    private EditText targetAudienceEt;
    private EditText priceEt;
    private Button submitBtn;
    private ProgressBar progressBar;

    private UserDetailsViewModel userDetailsViewModel;
    private OfferCourseViewModel offerCourseViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offer_course, container, false);

        bindWidgetsOfView(view);
        setListeners();

        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
        offerCourseViewModel = ViewModelProviders.of(this).get(OfferCourseViewModel.class);

        return view;
    }

    private void setListeners() {
        submitBtn.setOnClickListener(this);
    }

    private void bindWidgetsOfView(View view) {
        courseNameEt = view.findViewById(R.id.fragment_offer_course_course_name_input);
        courseDescriptionEt = view.findViewById(R.id.fragment_offer_course_description_input);
        targetAudienceEt = view.findViewById(R.id.fragment_offer_course_target_audience_input);
        priceEt = view.findViewById(R.id.fragment_offer_course_price_input);
        submitBtn = view.findViewById(R.id.fragment_offer_course_submit_btn);
        progressBar = view.findViewById(R.id.fragment_offer_course_progress_bar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_offer_course_submit_btn:
                submitCourseDetails();
                break;
        }
    }

    private void submitCourseDetails() {
        progressBar.setVisibility(View.VISIBLE);
        submitBtn.setClickable(false);

        Course course = new Course();
        initCourseDetails(course);

        if (courseDetailsAreValid(course)) {
            submitCourseToDB(course);
        } else {
            progressBar.setVisibility(View.GONE);
            submitBtn.setClickable(true);
        }
    }

    private void initCourseDetails(Course course) {
        course.setName(courseNameEt.getText().toString());
        course.setDescription(courseDescriptionEt.getText().toString());
        course.setAuthorId(userDetailsViewModel.getCurrentUserId());
        try {
            course.setPrice(Double.parseDouble(priceEt.getText().toString()));
        } catch (NumberFormatException e){
            course.setPrice(0);
        }
        course.setTargetAudience(targetAudienceEt.getText().toString());
        course.setRating(0);
        course.setJoinRequestUserIds(new ArrayList<String>());
        course.setRegisteredUserIds(new ArrayList<String>());
        course.setReviews(new ArrayList<String>());
    }

    private boolean courseDetailsAreValid(Course course) {
        return (isCourseNameValid(course.getName())
                && isDescriptionValid(course.getDescription())
                && isTargetAudienceValid(course.getTargetAudience()));
    }

    private void submitCourseToDB(Course course) {
        offerCourseViewModel.postNewCourse(course).observe(this, new Observer<ServerRequest>() {
            @Override
            public void onChanged(@Nullable ServerRequest serverRequest) {
                progressBar.setVisibility(View.GONE);
                submitBtn.setClickable(true);
                if (serverRequest.isSucceeded()) {
                    Toast.makeText(getContext(), serverRequest.getMessage(), Toast.LENGTH_SHORT).show();
                    clearInputs();
                } else {
                    Toast.makeText(getContext(), "An error has occurred: " + serverRequest.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void clearInputs() {
        courseNameEt.setText("");
        courseDescriptionEt.setText("");
        targetAudienceEt.setText("");
        priceEt.setText("");
    }

    private boolean isCourseNameValid(String name) {
        if (name.isEmpty()) {
            courseNameEt.setError("Name must not be empty");
            courseNameEt.requestFocus();
            return false;
        } else
            return true;
    }

    private boolean isDescriptionValid(String description) {
        if (description.isEmpty()) {
            courseDescriptionEt.setError("Description must not be empty");
            courseDescriptionEt.requestFocus();
            return false;
        } else
            return true;
    }

    private boolean isTargetAudienceValid(String targetAudience) {
        if (targetAudience.isEmpty()) {
            targetAudienceEt.setError("Target audience must not be empty");
            targetAudienceEt.requestFocus();
            return false;
        } else
            return true;
    }


}
