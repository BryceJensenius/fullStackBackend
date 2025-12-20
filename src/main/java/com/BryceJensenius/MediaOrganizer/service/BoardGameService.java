package com.BryceJensenius.MediaOrganizer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BryceJensenius.MediaOrganizer.model.BoardGame;

@Service
public class BoardGameService {
    @Autowired
    private XMLApiService xmlApiService;

    public BoardGame getBoardGameByName(String name) {
        String objectId = xmlApiService.fetchBoardGameIdByName(name);
        return xmlApiService.fetchBoardGameDetailsById(objectId);
    }
}