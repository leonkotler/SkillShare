package com.leon.skillshare.domain;

import java.util.Map;

public class User {

    private String userId;
    private String email;
    private String fullName;
    private String college;
    private Map<String, String> coursesTaking;
    private Map<String, String> coursesOffering;

    public User(String userId, String email, String fullName, String college, Map<String, String> coursesTaking, Map<String, String> coursesOffering) {
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

    public Map<String, String> getCoursesTaking() {
        return coursesTaking;
    }

    public void setCoursesTaking(Map<String, String> coursesTaking) {
        this.coursesTaking = coursesTaking;
    }

    public Map<String, String> getCoursesOffering() {
        return coursesOffering;
    }

    public void setCoursesOffering(Map<String, String> coursesOffering) {
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
