package com.BryceJensenius.MediaOrganizer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class MediaWatchItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String mediaName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public MediaWatchItem() {}

    public MediaWatchItem(String mediaName, User user){
        this.mediaName = mediaName;
        this.user = user;
    }

    public void setMediaName(String mediaName){
        this.mediaName = mediaName;
    }

    public String getMediaName(){
        return mediaName;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public int getId(){
        return id;
    }
}
