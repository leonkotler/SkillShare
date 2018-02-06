package com.leon.skillshare.domain;


import android.net.Uri;

public class CourseDetails {

    private String courseId;
    private String courseName;
    private String logoUrl;

    public CourseDetails(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public CourseDetails() {
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
