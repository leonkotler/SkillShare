package com.leon.skillshare.domain;



public class Review {

    private String userId;
    private String userEmail;
    private String content;

    public Review(String userId, String userEmail, String content) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.content = content;
    }

    public Review() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Review{" +
                "userId='" + userId + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
