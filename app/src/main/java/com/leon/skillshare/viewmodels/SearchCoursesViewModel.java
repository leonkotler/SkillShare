package com.leon.skillshare.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.leon.skillshare.domain.CourseDetails;
import com.leon.skillshare.repositories.CourseRepository;

import java.util.List;


public class SearchCoursesViewModel extends ViewModel {

    private LiveData<List<CourseDetails>> courseDetailsList;
    private final CourseRepository courseRepository;

    public SearchCoursesViewModel() {
        courseRepository = CourseRepository.getInstance();
        courseDetailsList = courseRepository.getAllCourses();
    }

    public LiveData<List<CourseDetails>> getAllCourses(){
        return courseDetailsList;
    }
}
