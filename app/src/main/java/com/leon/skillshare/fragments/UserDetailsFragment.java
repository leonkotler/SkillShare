package com.leon.skillshare.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.leon.skillshare.R;
import com.leon.skillshare.activities.CourseDetailsActivity;
import com.leon.skillshare.domain.CourseDetails;
import com.leon.skillshare.domain.User;
import com.leon.skillshare.viewmodels.UserDetailsViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

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
        final TakingCoursesListAdapter adapter = new TakingCoursesListAdapter();
        takingCoursesLv.setAdapter(adapter);

        userDetailsVm.getTakingCoursesLiveDataList().observe(this, new Observer<List<CourseDetails>>() {
            @Override
            public void onChanged(@Nullable List<CourseDetails> courseDetails) {
                takingCoursesProgressBar.setVisibility(View.GONE);
                userDetailsVm.setTakingCoursesSnapshotList(courseDetails);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void populateOfferingCoursesList() {
        offeringCoursesProgressBar.setVisibility(View.VISIBLE);
        final OfferingCoursesListAdapter adapter = new OfferingCoursesListAdapter();
        offeringCoursesLv.setAdapter(adapter);

        userDetailsVm.getOfferingCoursesLiveDataList().observe(this, new Observer<List<CourseDetails>>() {

            @Override
            public void onChanged(@Nullable List<CourseDetails> courseDetails) {
                offeringCoursesProgressBar.setVisibility(View.GONE);
                userDetailsVm.setOfferingCoursesSnapshotList(courseDetails);
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
            return userDetailsVm.getOfferingCoursesSnapshotList().size();
        }

        @Override
        public Object getItem(int position) {
            return userDetailsVm.getOfferingCoursesSnapshotList().get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = View.inflate(getContext(), R.layout.course_row, null);
            }

            TextView courseName = view.findViewById(R.id.course_row_course_name);
            final ImageView courseImg = view.findViewById(R.id.course_row_course_image);

            final CourseDetails courseInCtx = userDetailsVm.getOfferingCoursesSnapshotList().get(position);
            courseName.setText(courseInCtx.getCourseName());

            if (courseInCtx.getLogoUrl() != null && !courseInCtx.getLogoUrl().equals("NO_LOGO"))
                Picasso.with(getContext()).load(courseInCtx.getLogoUrl()).networkPolicy(NetworkPolicy.OFFLINE).into(courseImg, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(getContext()).load(courseInCtx.getLogoUrl()).into(courseImg);
                    }
                });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getContext(), CourseDetailsActivity.class);
                    intent.putExtra("courseId", courseInCtx.getCourseId());
                    intent.putExtra("viewType", "offerCourseDetails");
                    startActivity(intent);
                }
            });

            return view;
        }
    }

    class TakingCoursesListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return userDetailsVm.getTakingCoursesSnapshotList().size();
        }

        @Override
        public Object getItem(int position) {
            return userDetailsVm.getTakingCoursesSnapshotList().get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = View.inflate(getContext(), R.layout.course_row, null);
            }

            TextView courseName = view.findViewById(R.id.course_row_course_name);
            final ImageView courseImg = view.findViewById(R.id.course_row_course_image);

            final CourseDetails courseInCtx = userDetailsVm.getTakingCoursesSnapshotList().get(position);
            courseName.setText(courseInCtx.getCourseName());

            if (courseInCtx.getLogoUrl() != null && !courseInCtx.getLogoUrl().equals("NO_LOGO"))
                Picasso.with(getContext()).load(courseInCtx.getLogoUrl()).networkPolicy(NetworkPolicy.OFFLINE).into(courseImg, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(getContext()).load(courseInCtx.getLogoUrl()).into(courseImg);
                    }
                });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getContext(), CourseDetailsActivity.class);
                    intent.putExtra("courseId", courseInCtx.getCourseId());
                    intent.putExtra("viewType", "courseDetails");
                    startActivity(intent);
                }
            });

            return view;
        }
    }


}
