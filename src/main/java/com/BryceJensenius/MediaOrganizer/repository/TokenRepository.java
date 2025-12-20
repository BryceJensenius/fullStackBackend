package com.BryceJensenius.MediaOrganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BryceJensenius.MediaOrganizer.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    public Token findById(int id);

    public Token findByTokenValue(String tokenValue);
}