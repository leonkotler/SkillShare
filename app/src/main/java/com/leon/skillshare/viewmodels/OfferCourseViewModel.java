package com.leon.skillshare.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.leon.skillshare.domain.Course;
import com.leon.skillshare.domain.ServerRequest;
import com.leon.skillshare.domain.User;
import com.leon.skillshare.repositories.CourseRepository;
import com.leon.skillshare.repositories.UserRepository;


public class OfferCourseViewModel extends ViewModel {

    private final CourseRepository courseRepository;

    public OfferCourseViewModel() {
        courseRepository = CourseRepository.getInstance();
    }

    public LiveData<ServerRequest> postNewCourse(Course course){
        return courseRepository.postNewCourse(course);
    }

}
