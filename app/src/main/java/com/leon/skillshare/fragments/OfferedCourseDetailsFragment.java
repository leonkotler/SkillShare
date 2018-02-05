package com.leon.skillshare.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.leon.skillshare.R;
import com.leon.skillshare.domain.Course;
import com.leon.skillshare.domain.Review;
import com.leon.skillshare.viewmodels.CourseDetailsViewModel;

import java.util.Map;


public class OfferedCourseDetailsFragment extends Fragment {

    private String courseId;

    private TextView courseNameTv;
    private TextView registeredUsersTv;
    private TextView reviewsTv;
    private ProgressBar progressBar;

    private CourseDetailsViewModel courseDetailsVm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offered_course_details,container,false);
        courseId = getArguments().getString("courseId");

        courseDetailsVm = ViewModelProviders.of(this).get(CourseDetailsViewModel.class);

        bindWidgetsOfView(view);
        populateData();

        return view;
    }



    private void bindWidgetsOfView(View view) {
        courseNameTv = view.findViewById(R.id.fragment_offered_course_details_title);
        registeredUsersTv = view.findViewById(R.id.fragment_offered_course_details_reg_students_text);
        reviewsTv  = view.findViewById(R.id.fragment_offered_course_details_reviews_text);
        progressBar = view.findViewById(R.id.fragment_offered_course_details_progress_bar);
    }

    private void populateData() {
        progressBar.setVisibility(View.VISIBLE);
        courseDetailsVm.getCourse(courseId).observe(this, new Observer<Course>() {
            @Override
            public void onChanged(@Nullable Course course) {
                progressBar.setVisibility(View.GONE);
                populateViewModel(course);
                populateViewsWithData(course);
            }
        });
    }

    private void populateViewModel(Course course) {
        courseDetailsVm.setCurrentCourse(course);
        courseDetailsVm.setCurrentCourseReviews(course.getReviews());
        courseDetailsVm.setCurrentCourseRegisteredUsersMap(course.getRegisteredUsers());
    }

    private void populateViewsWithData(Course course) {
        courseNameTv.setText(course.getName());
        setReviews(course);
        setRegisteredUsers(course);
    }

    private void setReviews(Course course) {
        if (course.getReviews() == null || course.getReviews().size() == 0)
            reviewsTv.setText("No reviews yet");

        else {
            StringBuilder sb = new StringBuilder();

            for (Map.Entry<String, Review> entry : course.getReviews().entrySet())
                sb.append("\"" + entry.getValue().getContent() + "\" by - " + entry.getValue().getUserEmail() + '\n' + '\n');

            reviewsTv.setText(sb);
        }
    }

    private void setRegisteredUsers(Course course) {
        if (course.getRegisteredUsers() == null || course.getRegisteredUsers().size() == 0)
            registeredUsersTv.setText("No registrations yet");

        else {
            StringBuilder sb = new StringBuilder();

            for (Map.Entry<String, String> entry : course.getRegisteredUsers().entrySet())
                sb.append(entry.getValue() + '\n');

            registeredUsersTv.setText(sb);
        }
    }
}
