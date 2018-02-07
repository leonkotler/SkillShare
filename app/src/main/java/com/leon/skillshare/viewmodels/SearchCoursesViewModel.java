package com.leon.skillshare.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.leon.skillshare.domain.CourseDetails;
import com.leon.skillshare.repositories.CourseRepository;

import java.util.ArrayList;
import java.util.List;


public class SearchCoursesViewModel extends ViewModel {

    private LiveData<List<CourseDetails>> courseDetailsLiveDataList;
    private List<CourseDetails> courseDetailsSnapshotList = new ArrayList<>();
    private List<CourseDetails> filteredCourseDetailsSnapshotList;
    private final CourseRepository courseRepository;

    public SearchCoursesViewModel() {
        courseRepository = CourseRepository.getInstance();
        courseDetailsLiveDataList = courseRepository.getAllCourses();
        filteredCourseDetailsSnapshotList = courseDetailsSnapshotList;
    }

    public LiveData<List<CourseDetails>> getAllCourses(){
        return courseDetailsLiveDataList;
    }

    public List<CourseDetails> getCourseDetailsSnapshotList() {
        return courseDetailsSnapshotList;
    }

    public void setCourseDetailsSnapshotList(List<CourseDetails> courseDetailsSnapshotList) {
        this.courseDetailsSnapshotList = courseDetailsSnapshotList;
    }

    public List<CourseDetails> getFilteredCourseDetailsSnapshotList() {
        return filteredCourseDetailsSnapshotList;
    }

    public void setFilteredCourseDetailsSnapshotList(List<CourseDetails> filteredCourseDetailsSnapshotList) {
        this.filteredCourseDetailsSnapshotList = filteredCourseDetailsSnapshotList;
    }
}
