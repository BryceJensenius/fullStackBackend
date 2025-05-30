package com.BryceJensenius.MediaOrganizer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MediaWatchItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String mediaName;

    public MediaWatchItem() {}

    public MediaWatchItem(String mediaName){
        this.mediaName = mediaName;
    }

    public void setMediaName(String mediaName){
        this.mediaName = mediaName;
    }

    public String getMediaName(){
        return mediaName;
    }

    public int getId(){
        return id;
    }
}
