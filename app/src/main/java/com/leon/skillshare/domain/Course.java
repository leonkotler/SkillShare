package com.leon.skillshare.domain;

import java.util.List;

public class Course {

    private String id;
    private String name;
    private String description;
    private String targetAudience;
    private int rating;
    private String authorId;
    private double price;
    private List<String> reviews;
    private List<String> registeredUserIds;
    private List<String> joinRequestUserIds;

    public Course() {
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }

    public List<String> getRegisteredUserIds() {
        return registeredUserIds;
    }

    public void setRegisteredUserIds(List<String> registeredUserIds) {
        this.registeredUserIds = registeredUserIds;
    }

    public List<String> getJoinRequestUserIds() {
        return joinRequestUserIds;
    }

    public void setJoinRequestUserIds(List<String> joinRequestUserIds) {
        this.joinRequestUserIds = joinRequestUserIds;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", targetAudience='" + targetAudience + '\'' +
                ", rating=" + rating +
                ", authorId='" + authorId + '\'' +
                ", price=" + price +
                ", reviews=" + reviews +
                ", registeredUserIds=" + registeredUserIds +
                ", joinRequestUserIds=" + joinRequestUserIds +
                '}';
    }
}
