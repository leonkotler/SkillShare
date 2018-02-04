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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.leon.skillshare.R;
import com.leon.skillshare.domain.Course;
import com.leon.skillshare.domain.ServerRequest;
import com.leon.skillshare.viewmodels.CourseDetailsViewModel;
import com.leon.skillshare.viewmodels.UserDetailsViewModel;

import java.util.Map;


public class CourseInfoFragment extends Fragment implements View.OnClickListener {

    private String courseId;

    private TextView courseNameTv;
    private TextView targetAudienceTv;
    private TextView descriptionTv;
    private TextView authorTv;
    private TextView priceTv;
    private TextView reviewsTv;
    private Button reviewBtn;
    private Button joinBtn;
    private ProgressBar progressBar;
    private ProgressBar btnProgressBar;
    private CourseDetailsViewModel courseDetailsVm;
    private UserDetailsViewModel userDetailsViewVm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_info, container, false);
        courseId = getArguments().getString("courseId");

        courseDetailsVm = ViewModelProviders.of(this).get(CourseDetailsViewModel.class);
        userDetailsViewVm = ViewModelProviders.of(this).get(UserDetailsViewModel.class);

        bindViewsOfWidget(view);
        registerListeners();
        populateData();

        return view;
    }


    private void bindViewsOfWidget(View view) {
        courseNameTv = view.findViewById(R.id.fragment_course_info_course_name_label);
        descriptionTv = view.findViewById(R.id.fragment_course_info_details_text);
        targetAudienceTv = view.findViewById(R.id.fragment_course_info_course_target_audience_text);
        authorTv = view.findViewById(R.id.fragment_course_info_author_text);
        priceTv = view.findViewById(R.id.fragment_course_info_price_text);
        reviewsTv = view.findViewById(R.id.fragment_course_info_reviews_text);
        reviewBtn = view.findViewById(R.id.fragment_course_info_rate_btn);
        joinBtn = view.findViewById(R.id.fragment_course_info_join_btn);
        progressBar = view.findViewById(R.id.fragment_course_info_progress_bar);
        btnProgressBar = view.findViewById(R.id.fragment_course_info_button_progress_bar);
    }

    private void registerListeners() {
        joinBtn.setOnClickListener(this);
        reviewBtn.setOnClickListener(this);
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
        courseDetailsVm.setCurrentCourseRegisteredUsersMap(course.getRegisteredUsers());
        courseDetailsVm.setCurrentCourseReviews(course.getReviews());
        courseDetailsVm.setCurrentCourse(course);
    }

    private void populateViewsWithData(Course course) {
        courseNameTv.setText(course.getName());
        descriptionTv.setText(course.getDescription());
        targetAudienceTv.setText(course.getTargetAudience());
        authorTv.setText(course.getAuthorEmail());
        setPrice(course);
        setReviews(course);
    }


    private void setPrice(Course course) {
        if (course.getPrice() == 0)
            priceTv.setText("FREE");
        else
            priceTv.setText(String.valueOf(course.getPrice()));
    }

    private void setReviews(Course course) {
        if (course.getReviews() == null || course.getReviews().size() == 0)
            reviewsTv.setText("No reviews yet");

        else {
            StringBuilder sb = new StringBuilder();

            for (Map.Entry<String, String> entry : course.getReviews().entrySet())
                sb.append("\"" + entry.getValue() + "\" by - " + entry.getKey() + '\n');

            reviewsTv.setText(sb);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_course_info_join_btn:
                joinCourse();
                break;
            case R.id.fragment_course_info_rate_btn:
                rateCourse();
                break;
        }
    }

    private void joinCourse() {
        if (!userAlreadyRegisteredToCourse()) {

            sendJoinRequestToDB(courseId
                    , courseDetailsVm.getCurrentCourse().getName()
                    , userDetailsViewVm.getCurrentUserId()
                    , userDetailsViewVm.getCurrentUserEmail());

        } else
            Toast.makeText(getContext(), "You are already registered to this course", Toast.LENGTH_SHORT).show();
    }


    private boolean userAlreadyRegisteredToCourse() {
        return courseMapHasValue(courseDetailsVm.getCurrentCourseRegisteredUsersMap(), userDetailsViewVm.getCurrentUserId());
    }

    private void sendJoinRequestToDB(String courseId, String courseName, String currentUserId, String currentUserEmail) {
        btnProgressBar.setVisibility(View.VISIBLE);
        joinBtn.setClickable(false);
        courseDetailsVm.registerUserToCourse(courseId,courseName,currentUserId,currentUserEmail).observe(this, new Observer<ServerRequest>() {
            @Override
            public void onChanged(@Nullable ServerRequest serverRequest) {
                btnProgressBar.setVisibility(View.GONE);
                joinBtn.setClickable(true);
                if (serverRequest.isSucceeded()) {
                    Toast.makeText(getContext(), serverRequest.getMessage(), Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
                else {
                    Toast.makeText(getContext(), serverRequest.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void rateCourse() {

        if (!userAlreadyLeftReview()) {
            addReview(courseId, userDetailsViewVm.getCurrentUserId(), userDetailsViewVm.getCurrentUserEmail());
        } else {
            Toast.makeText(getContext(), "You are already left a review for this course this course", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean userAlreadyLeftReview() {
        return courseMapHasValue(courseDetailsVm.getCurrentCourseReviews(), userDetailsViewVm.getCurrentUserEmail());
    }

    private void addReview(String courseId, String currentUserId, String currentUserEmail) {

    }

    private boolean courseMapHasValue(Map<String, String> map, String value) {
        if (map == null || map.size() == 0)
            return false;

        for (Map.Entry<String, String> entry : map.entrySet())
            if (entry.getKey().equals(value))
                return true;

        return false;
    }
}
