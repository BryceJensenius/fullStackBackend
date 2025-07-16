package com.BryceJensenius.MediaOrganizer.model;

import jakarta.persistence.*;

@Entity
public class MediaItem implements Media{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String finishDate;
    private double rating;
    @Column(length = 5000)
    private String review;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        if(review.isEmpty()){
            review = "None";
        }
        this.review = review;
    }
    public MediaItem() {
    }
}
