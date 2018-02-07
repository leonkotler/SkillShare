package com.leon.skillshare.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.leon.skillshare.domain.Course;
import com.leon.skillshare.domain.CourseDetails;
import com.leon.skillshare.domain.Review;
import com.leon.skillshare.domain.ServerRequest;
import com.leon.skillshare.repositories.CourseRepository;

import java.util.Map;

public class CourseDetailsViewModel extends ViewModel {

    private final CourseRepository courseRepository;
    private Map<String, String> currentCourseRegisteredUsersMap;
    private Course currentCourse;
    private Map<String, Review> currentCourseReviews;

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

    public Map<String, Review> getCurrentCourseReviews() {
        return currentCourseReviews;
    }

    public void setCurrentCourseReviews(Map<String, Review> currentCourseReviews) {
        this.currentCourseReviews = currentCourseReviews;
    }

    public LiveData<ServerRequest> registerUserToCourse(CourseDetails courseDetails, String userId, String userEmail) {
        return courseRepository.registerUserToCourse(courseDetails, userId, userEmail);
    }

    public Course getCurrentCourse() {
        return currentCourse;
    }

    public void setCurrentCourse(Course currentCourse) {
        this.currentCourse = currentCourse;
    }

    public LiveData<ServerRequest> postReview(String courseId, Review review){
        return courseRepository.postReview(courseId, review);
    }

}

