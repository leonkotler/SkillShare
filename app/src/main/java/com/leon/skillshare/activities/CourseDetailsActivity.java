package com.leon.skillshare.activities;


import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.leon.skillshare.R;
import com.leon.skillshare.fragments.CourseInfoFragment;
import com.leon.skillshare.fragments.OfferedCourseDetailsFragment;

public class CourseDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        Intent intent = getIntent();
        String courseId = intent.getStringExtra("courseId");
        Bundle dataToFragment = new Bundle();
        dataToFragment.putString("courseId", courseId);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (intent.getStringExtra("viewType")){

            case "courseDetails":
                CourseInfoFragment courseInfoFragment = new CourseInfoFragment();
                courseInfoFragment.setArguments(dataToFragment);
                fragmentTransaction.replace(R.id.activity_course_details_fragment, courseInfoFragment);
                fragmentTransaction.commit();
                break;

            case "offerCourseDetails":
                OfferedCourseDetailsFragment offeredCourseDetailsFragment = new OfferedCourseDetailsFragment();
                offeredCourseDetailsFragment.setArguments(dataToFragment);
                fragmentTransaction.replace(R.id.activity_course_details_fragment, offeredCourseDetailsFragment);
                fragmentTransaction.commit();
                break;
        }
    }
}
