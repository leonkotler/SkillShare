package com.leon.skillshare.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.leon.skillshare.domain.Course;
import com.leon.skillshare.domain.ServerRequest;
import com.leon.skillshare.repositories.CourseRepository;

import java.util.Map;

public class CourseDetailsViewModel extends ViewModel {

    private final CourseRepository courseRepository;
    private Map<String, String> currentCourseRegisteredUsersMap;
    private Course currentCourse;
    private Map<String, String> currentCourseReviews;

    public CourseDetailsViewModel() {
        courseRepository = CourseRepository.getInstance();
    }

    public LiveData<Course> getCourse(String courseId) {
        return courseRepository.getCourse(courseId);
    }

    public Map<String, String> getCurrentCourseRegisteredUsersMap() {
        return currentCourseRegisteredUsersMap;
    }

    public void setCurrentCourseRegisteredUsersMap(Map<String, String> currentCourseRegisteredUsersMap) {
        this.currentCourseRegisteredUsersMap = currentCourseRegisteredUsersMap;
    }

    public Map<String, String> getCurrentCourseReviews() {
        return currentCourseReviews;
    }

    public void setCurrentCourseReviews(Map<String, String> currentCourseReviews) {
        this.currentCourseReviews = currentCourseReviews;
    }

    public LiveData<ServerRequest> registerUserToCourse(String courseId, String courseName, String userId, String userEmail) {
        return courseRepository.registerUserToCourse(courseId, courseName, userId, userEmail);
    }

    public Course getCurrentCourse() {
        return currentCourse;
    }

    public void setCurrentCourse(Course currentCourse) {
        this.currentCourse = currentCourse;
    }

}

