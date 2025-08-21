package com.BryceJensenius.MediaOrganizer.controller;

import com.BryceJensenius.MediaOrganizer.service.BoardGameService;
import com.BryceJensenius.MediaOrganizer.model.BoardGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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