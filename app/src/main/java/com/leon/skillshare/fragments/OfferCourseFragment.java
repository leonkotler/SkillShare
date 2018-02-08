package com.leon.skillshare.fragments;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.leon.skillshare.R;
import com.leon.skillshare.activities.CourseDetailsActivity;
import com.leon.skillshare.domain.Course;
import com.leon.skillshare.domain.ResourceUploadRequest;
import com.leon.skillshare.domain.Review;
import com.leon.skillshare.domain.ServerRequest;
import com.leon.skillshare.viewmodels.OfferCourseViewModel;
import com.leon.skillshare.viewmodels.UserDetailsViewModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class OfferCourseFragment extends Fragment implements View.OnClickListener {

    private static final int GALLERY_REQUEST_CODE = 999;
    private EditText courseNameEt;
    private EditText courseDescriptionEt;
    private EditText targetAudienceEt;
    private EditText priceEt;
    private Button submitBtn;
    private Button addLogoBtn;
    private ImageView logoImgView;
    private ProgressBar progressBar;
    private ProgressBar addLogoProgressBar;

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
        addLogoBtn.setOnClickListener(this);
    }

    private void bindWidgetsOfView(View view) {
        courseNameEt = view.findViewById(R.id.fragment_offer_course_course_name_input);
        courseDescriptionEt = view.findViewById(R.id.fragment_offer_course_description_input);
        targetAudienceEt = view.findViewById(R.id.fragment_offer_course_target_audience_input);
        priceEt = view.findViewById(R.id.fragment_offer_course_price_input);
        submitBtn = view.findViewById(R.id.fragment_offer_course_submit_btn);
        progressBar = view.findViewById(R.id.fragment_offer_course_progress_bar);
        addLogoBtn = view.findViewById(R.id.fragment_offer_course_add_logo_btn);
        logoImgView = view.findViewById(R.id.fragment_offer_course_course_logo);
        addLogoProgressBar = view.findViewById(R.id.fragment_offer_course_add_logo_progress_bar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_offer_course_submit_btn:
                submitCourseDetails();
                break;
            case R.id.fragment_offer_course_add_logo_btn:
                addImage();
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

    private void addImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        addLogoBtn.setVisibility(View.GONE);
        addLogoProgressBar.setVisibility(View.VISIBLE);
        final Uri localImgUri = data.getData();

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            offerCourseViewModel.uploadCourseLogo(data.getData().toString()).observe(this, new Observer<ResourceUploadRequest>() {

                @Override
                public void onChanged(@Nullable ResourceUploadRequest resourceUploadRequest) {
                    addLogoProgressBar.setVisibility(View.GONE);
                    addLogoBtn.setVisibility(View.VISIBLE);

                    if (resourceUploadRequest.isSucceeded()){
                        offerCourseViewModel.setCourseLogoUrl(resourceUploadRequest.getDownloadUri().toString());
                        Picasso.with(getContext()).load(localImgUri).into(logoImgView);
                    } else {
                        offerCourseViewModel.setCourseLogoUrl("NO_LOGO");
                        Toast.makeText(getContext(), resourceUploadRequest.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void initCourseDetails(Course course) {
        course.setName(courseNameEt.getText().toString());
        course.setDescription(courseDescriptionEt.getText().toString());
        course.setAuthorId(userDetailsViewModel.getCurrentUserId());
        course.setAuthorEmail(userDetailsViewModel.getCurrentUserEmail());

        try {
            course.setPrice(Double.parseDouble(priceEt.getText().toString()));
        } catch (NumberFormatException e){
            course.setPrice(0);
        }

        course.setLogoUrl(offerCourseViewModel.getCourseLogoUrl());
        course.setTargetAudience(targetAudienceEt.getText().toString());
        course.setRegisteredUsers(new HashMap<String, String>());
        course.setReviews(new HashMap<String, Review>());
    }

    private boolean courseDetailsAreValid(Course course) {
        return (isCourseNameValid(course.getName())
                && isDescriptionValid(course.getDescription())
                && isTargetAudienceValid(course.getTargetAudience()));
    }

    private void submitCourseToDB(final Course course) {

        offerCourseViewModel.postNewCourse(course).observe(this, new Observer<ServerRequest>() {
            @Override
            public void onChanged(@Nullable ServerRequest serverRequest) {
                progressBar.setVisibility(View.GONE);
                submitBtn.setClickable(true);
                if (serverRequest.isSuccessful()) {
                    Toast.makeText(getContext(), serverRequest.getMessage(), Toast.LENGTH_SHORT).show();
                    clearInputs();
                    redirectToCourseOfferedDetails(serverRequest.getRefId());
                } else {
                    Toast.makeText(getContext(), "An error has occurred: " + serverRequest.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void redirectToCourseOfferedDetails(String refId) {
        Intent intent = new Intent(getContext(), CourseDetailsActivity.class);
        intent.putExtra("courseId", refId);
        intent.putExtra("viewType", "offerCourseDetails");
        startActivity(intent);
    }

    private void clearInputs() {
        courseNameEt.setText("");
        courseDescriptionEt.setText("");
        targetAudienceEt.setText("");
        priceEt.setText("");
        offerCourseViewModel.setCourseLogoUrl("NO_LOGO");
        Picasso.with(getContext()).load(android.R.drawable.ic_menu_gallery).into(logoImgView);
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
