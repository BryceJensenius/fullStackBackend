package com.BryceJensenius.MediaOrganizer.service;

import com.BryceJensenius.MediaOrganizer.model.BoardGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardGameService {
    @Autowired
    private XMLApiService xmlApiService;

    public BoardGame getBoardGameByName(String name) {
        String objectId = xmlApiService.fetchBoardGameIdByName(name);
        return xmlApiService.fetchBoardGameDetailsById(objectId);
    }
}