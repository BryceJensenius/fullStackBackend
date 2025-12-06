package com.BryceJensenius.MediaOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BryceJensenius.MediaOrganizer.model.BoardGame;
import com.BryceJensenius.MediaOrganizer.service.BoardGameService;

@RestController
@RequestMapping("/boardGame")
@CrossOrigin
public class BoardGameController {
    @Autowired
    private BoardGameService boardGameService;

    @GetMapping("/{name}")
    public BoardGame add(@PathVariable String name){
        return boardGameService.getBoardGameByName(name);
    }
}