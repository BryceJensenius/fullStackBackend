package com.BryceJensenius.MediaOrganizer.REST_API;

/*
    everything needed to represent a movie from the online database
 */
public class Movie {
    private String Title;
    private String Year;
    private String Genre;
    private String Director;

    // Getters and Setters
    public String getTitle() { return Title; }
    public String getYear() { return Year; }
    public String getGenre() { return Genre; }
    public String getDirector() { return Director; }
}
