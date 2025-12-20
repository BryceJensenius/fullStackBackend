package com.BryceJensenius.MediaOrganizer.model;

public interface Media {
    int getId();
    void setId(int id);
    String getName();
    void setName(String name);
    String getFinishDate();
    void setFinishDate(String finishDate);
    double getRating();
    void setRating(double rating);
    String getReview();
    void setReview(String review);
    User getUser();
    void setUser(User user);
}
