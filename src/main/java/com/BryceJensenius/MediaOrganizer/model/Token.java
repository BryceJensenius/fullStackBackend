package com.BryceJensenius.MediaOrganizer.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String tokenValue;

    private Instant expirationDate;

    @OneToOne(mappedBy = "token")
    // This allows doing token.getUser() to retrieve the associated User
    @JoinColumn(name = "user_id", referencedColumnName = "id") // Create column user_id in Token table, with value referencing User's id
    private User user;

    public Token() {}

    public Token(String tokenValue, Instant expirationDate){
        this.tokenValue = tokenValue;
        this.expirationDate = expirationDate;
    }

    public Token(String tokenValue, User user, Instant expirationDate){
        this.tokenValue = tokenValue;
        this.user = user;
        this.expirationDate = expirationDate;
    }

    public void setTokenValue(String tokenValue){
        this.tokenValue = tokenValue;
    }

    public String getTokenValue(){
        return tokenValue;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public Instant getExpirationDate(){
        return expirationDate;
    }
    public void setExpirationDate(Instant expirationDate){
        this.expirationDate = expirationDate;
    }

    public int getId(){
        return id;
    }
}
