package com.BryceJensenius.MediaOrganizer.model;

public class BoardGame {
    private String name;
    private int yearPublished;

    public BoardGame() {
    }
    public BoardGame(String name, int yearPublished) {
        this.name = name;
        this.yearPublished = yearPublished;
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
}
