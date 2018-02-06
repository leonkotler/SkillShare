package com.leon.skillshare.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.leon.skillshare.R;
import com.leon.skillshare.activities.CourseDetailsActivity;
import com.leon.skillshare.domain.CourseDetails;
import com.leon.skillshare.viewmodels.SearchCoursesViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CourseSearchFragment extends Fragment {

    private SearchCoursesViewModel searchCoursesVm;
    private EditText searchText;
    private ListView coursesLv;
    private ProgressBar progressBar;
    private List<CourseDetails> courseDetailsList = new ArrayList<>();
    private CoursesListAdapter coursesListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_search, container, false);

        bindWidgetsOfView(view);
        setupViewModel();
        setupAdapter();
        getAllCourses();
        setupSearchText();

        return view;
    }

    private void bindWidgetsOfView(View view) {
        searchText = view.findViewById(R.id.fragment_course_search_text);
        coursesLv = view.findViewById(R.id.fragment_course_search_courses_lv);
        progressBar = view.findViewById(R.id.fragment_course_search_progress_bar);
        coursesLv.setTextFilterEnabled(true);
    }

    private void setupViewModel() {
        searchCoursesVm = ViewModelProviders.of(this).get(SearchCoursesViewModel.class);

    }

    private void setupAdapter() {
        coursesListAdapter = new CoursesListAdapter();
        coursesLv.setAdapter(coursesListAdapter);
    }

    private void getAllCourses() {

        progressBar.setVisibility(View.VISIBLE);

        searchCoursesVm.getAllCourses().observe(this, new Observer<List<CourseDetails>>() {
            @Override
            public void onChanged(@Nullable List<CourseDetails> courseDetails) {
                progressBar.setVisibility(View.GONE);
                courseDetailsList = courseDetails;
                coursesListAdapter.getFilter().filter("");

            }
        });
    }

    private void setupSearchText() {

        searchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                coursesListAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    class CoursesListAdapter extends BaseAdapter implements Filterable {

        private ArrayList<CourseDetails> filteredCoursesList = (ArrayList<CourseDetails>) courseDetailsList;

        @Override
        public int getCount() {
            return filteredCoursesList.size();
        }

        @Override
        public Object getItem(int position) {
            return filteredCoursesList.get(position);
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

            final CourseDetails courseInCtx = filteredCoursesList.get(position);
            courseName.setText(courseInCtx.getCourseName());

            if (courseInCtx.getLogoUrl()!=null && !courseInCtx.getLogoUrl().equals("NO_LOGO"))
                Picasso.with(getContext()).load(courseInCtx.getLogoUrl()).fit().centerCrop().into(courseImg);

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

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    if (constraint == null || constraint.length() == 0) {
                        //no constraint given, just return all the data. (no search)
                        results.count = courseDetailsList.size();
                        results.values = courseDetailsList;
                    } else {//do the search
                        List<CourseDetails> resultsData = new ArrayList<>();
                        String searchStr = constraint.toString().toUpperCase();
                        for (CourseDetails o : courseDetailsList)
                            if (o.getCourseName().toUpperCase().startsWith(searchStr))
                                resultsData.add(o);
                        results.count = resultsData.size();
                        results.values = resultsData;
                    }
                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    filteredCoursesList = (ArrayList<CourseDetails>) results.values;
                    notifyDataSetChanged();
                }
            };
        }

    }

}
