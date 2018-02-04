package com.leon.skillshare.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.leon.skillshare.R;


public class CourseInfoFragment extends Fragment {

    private String courseId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_info,container,false);
        courseId = getArguments().getString("courseId");
        Toast.makeText(getContext(), "You pressed on course " + courseId, Toast.LENGTH_SHORT).show();
        return view;
    }
}
