package com.leon.skillshare.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.net.Uri;

import com.leon.skillshare.domain.Course;
import com.leon.skillshare.domain.ResourceUploadRequest;
import com.leon.skillshare.domain.ServerRequest;
import com.leon.skillshare.repositories.CourseRepository;


public class OfferCourseViewModel extends ViewModel {

    private final CourseRepository courseRepository;
    private String courseLogoUrl;



    public OfferCourseViewModel() {
        courseRepository = CourseRepository.getInstance();
    }

    public LiveData<ServerRequest> postNewCourse(Course course){
        return courseRepository.postNewCourse(course);
    }

    public String getCourseLogoUrl() {
        return courseLogoUrl;
    }

    public void setCourseLogoUrl(String courseLogoUrl) {
        this.courseLogoUrl = courseLogoUrl;
    }

    public LiveData<ResourceUploadRequest> uploadCourseLogo(String courseLogoUrl) {
        return courseRepository.uploadCourseLogo(courseLogoUrl);
    }
}
