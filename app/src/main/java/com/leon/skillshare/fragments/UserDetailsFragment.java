package com.leon.skillshare.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.leon.skillshare.R;
import com.leon.skillshare.domain.Course;
import com.leon.skillshare.domain.CourseDetails;
import com.leon.skillshare.domain.User;
import com.leon.skillshare.viewmodels.UserDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsFragment extends Fragment {

    private TextView fullNameTv;
    private TextView emailTv;
    private TextView collegeTv;
    private ListView takingCoursesLv;
    private ListView offeringCoursesLv;
    private ProgressBar takingCoursesProgressBar;
    private ProgressBar offeringCoursesProgressBar;
    private ProgressBar userDetailsProgressBar;
    private List<CourseDetails> offeringCourseList = new ArrayList<>();

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
        final OfferingCoursesListAdapter adapter = new OfferingCoursesListAdapter();
        offeringCoursesLv.setAdapter(adapter);

        userDetailsVm.getOfferingCoursesList().observe(this, new Observer<List<CourseDetails>>() {

            @Override
            public void onChanged(@Nullable List<CourseDetails> courseDetails) {
                offeringCoursesProgressBar.setVisibility(View.GONE);
                offeringCourseList = courseDetails;
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void populateUserDetails() {

        userDetailsProgressBar.setVisibility(View.VISIBLE);

        userDetailsVm.getCurrentUser().observe(this, new Observer<User>() {

            @Override
            public void onChanged(@Nullable User user) {
                userDetailsProgressBar.setVisibility(View.GONE);
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

    class OfferingCoursesListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return offeringCourseList.size();
        }

        @Override
        public Object getItem(int position) {
            return offeringCourseList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null){
                view = View.inflate(getContext(),R.layout.user_details_course_row, null);
            }

            TextView courseName = view.findViewById(R.id.course_details_course_name);
            final ImageView courseImg = view.findViewById(R.id.course_details_course_image);

            final CourseDetails courseInCtx = offeringCourseList.get(position);
            courseName.setText(courseInCtx.getCourseName());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "You pressed on course " + courseInCtx.getCourseId(), Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }
    }

}
