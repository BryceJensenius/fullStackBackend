package com.BryceJensenius.MediaOrganizer.model;

public class BoardGame {
    private String name;
    private int yearPublished;
    private String objectId;
    private int minPlayers;
    private int maxPlayers;
    private int averagePlaytime; // in minutes
    private int minAge;
    private String description;
    private String imageUrl;
    private String thumbnailUrl;

    public BoardGame() {
    }
    public BoardGame(String objectId, String name, int yearPublished,
                    int minPlayers, int maxPlayers, int averagePlaytime, int minAge,
                    String description, String imageUrl, String thumbnailUrl) {
        this.name = name;
        this.yearPublished = yearPublished;
        this.objectId = objectId;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.averagePlaytime = averagePlaytime;
        this.minAge = minAge;
        this.description = description;
        this.imageUrl = imageUrl;
        this.thumbnailUrl = thumbnailUrl;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getYearPublished() {
        return yearPublished;
    }
    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }
    public String getObjectId() {
        return objectId;
    }
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    public int getMinPlayers() {
        return minPlayers;
    }
    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }
    public int getMaxPlayers() {
        return maxPlayers;
    }
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
    public int getAveragePlaytime() {
        return averagePlaytime;
    }
    public void setAveragePlaytime(int averagePlaytime) {
        this.averagePlaytime = averagePlaytime;
    }
    public int getMinAge() {
        return minAge;
    }
    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
