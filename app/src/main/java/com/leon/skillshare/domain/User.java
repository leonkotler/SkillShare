package com.leon.skillshare.domain;

import java.util.Map;

public class User {

    private String userId;
    private String email;
    private String fullName;
    private String college;
    private Map<String, CourseDetails> coursesTaking;
    private Map<String, CourseDetails> coursesOffering;

    public User(String userId, String email, String fullName, String college, Map<String, CourseDetails> coursesTaking, Map<String, CourseDetails> coursesOffering) {
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.college = college;
        this.coursesTaking = coursesTaking;
        this.coursesOffering = coursesOffering;
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public Map<String, CourseDetails> getCoursesTaking() {
        return coursesTaking;
    }

    public void setCoursesTaking(Map<String, CourseDetails> coursesTaking) {
        this.coursesTaking = coursesTaking;
    }

    public Map<String, CourseDetails> getCoursesOffering() {
        return coursesOffering;
    }

    public void setCoursesOffering(Map<String, CourseDetails> coursesOffering) {
        this.coursesOffering = coursesOffering;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", college='" + college + '\'' +
                ", coursesTaking=" + coursesTaking +
                ", coursesOffering=" + coursesOffering +
                '}';
    }
}
