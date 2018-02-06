package com.leon.skillshare.domain;

import android.net.Uri;

import java.util.Map;

public class Course {

    private String name;
    private String description;
    private String targetAudience;
    private String authorId;
    private String authorEmail;
    private String logoUrl;

    private double price;
    private Map<String, Review> reviews;
    private Map<String, String> registeredUsers;

    public Course() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Map<String, Review> getReviews() {
        return reviews;
    }

    public void setReviews(Map<String, Review> reviews) {
        this.reviews = reviews;
    }

    public Map<String, String> getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(Map<String, String> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", targetAudience='" + targetAudience + '\'' +
                ", authorId='" + authorId + '\'' +
                ", authorEmail='" + authorEmail + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", price=" + price +
                ", reviews=" + reviews +
                ", registeredUsers=" + registeredUsers +
                '}';
    }
}
