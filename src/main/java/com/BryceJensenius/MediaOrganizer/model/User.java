package com.BryceJensenius.MediaOrganizer.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String encPassword;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "token_id", referencedColumnName = "id") // Create column token_id in User table, with value referencing Token's id
    private Token token;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<MediaWatchItem> mediaWatchList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<MediaItem> mediaList;

    public User() {}

    public User(String username, String encPassword){
        this.username = username;
        this.encPassword = encPassword;
    }

    public User(String username, String encPassword, Token token){
        this.username = username;
        this.encPassword = encPassword;
        this.token = token;
    }

    public User(String username, String encPassword, Token token, List<MediaItem> mediaList, List<MediaWatchItem> mediaWatchList){
        this.username = username;
        this.encPassword = encPassword;
        this.token = token;
        this.mediaWatchList = mediaWatchList;
        this.mediaList = mediaList;
    }

    public void addMediaWatchItem(MediaWatchItem item){
        if(this.mediaWatchList == null){
            this.mediaWatchList = new ArrayList<>();
        }
        item.setUser(this);
        this.mediaWatchList.add(item);
    }

    public void addMedia(MediaItem media){
        if(this.mediaList == null){
            this.mediaList = new ArrayList<>();
        }
        media.setUser(this);
        this.mediaList.add(media);
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setEncPassword(String encPassword){
        this.encPassword = encPassword;
    }

    public String getEncPassword(){
        return encPassword;
    }

    public Token getToken(){
        return token;
    }

    public void setToken(Token token){
        this.token = token;
    }

    public List<MediaWatchItem> getMediaWatchList(){
        return mediaWatchList;
    }

    public void setMediaWatchList(List<MediaWatchItem> mediaWatchList){
        this.mediaWatchList = mediaWatchList;
    }

    public List<MediaItem> getMediaList(){
        return mediaList;
    }

    public void setMediaList(List<MediaItem> mediaList){
        this.mediaList = mediaList;
    }

    public int getId(){
        return id;
    }
}
