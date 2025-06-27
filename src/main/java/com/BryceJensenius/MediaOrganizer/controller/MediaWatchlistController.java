package com.BryceJensenius.MediaOrganizer.controller;

import com.BryceJensenius.MediaOrganizer.model.MediaWatchItem;
import com.BryceJensenius.MediaOrganizer.service.MediaWatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mediaWatch")
@CrossOrigin
public class MediaWatchlistController {
    @Autowired
    private MediaWatchlistService mediaWatchlistService;

    @PostMapping("/add")
    public String add(@RequestBody MediaWatchItem mediaWatchItem){
        mediaWatchlistService.add(mediaWatchItem);
        return "New Media Watch List Item Was Added";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id){
        mediaWatchlistService.delete(id);
        return "Media Watch List item deleted";
    }

    @GetMapping("/getById/{id}")
    public MediaWatchItem getMediaById(@PathVariable int id){
        return mediaWatchlistService.getMediaById(id);
    }

    @GetMapping("/getAll")
    public List<MediaWatchItem> getAllMedia() {
        return mediaWatchlistService.getAllMedia(); //get media list from database
    }
}