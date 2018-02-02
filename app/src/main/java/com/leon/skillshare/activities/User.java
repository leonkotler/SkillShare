package com.leon.skillshare.activities;

/**
 * Created by leonk on 02-Feb-18.
 */

public class User {

    private String userId;
    private String email;
    private String password;
    private String fullName;
    private String college;

    public User(String userId, String email, String password, String fullName, String college) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.college = college;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", college='" + college + '\'' +
                '}';
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
}
