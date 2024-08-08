package com.BryceJensenius.MediaOrganizer.controller;

import com.BryceJensenius.MediaOrganizer.model.FilterRequest;
import com.BryceJensenius.MediaOrganizer.model.MediaItem;
import com.BryceJensenius.MediaOrganizer.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mediaItems")
@CrossOrigin
public class MediaController {
    @Autowired
    private MediaService mediaService;

    private String filter = "";

    @PostMapping("/add")
    public String add(@RequestBody MediaItem media){
        mediaService.saveMedia(media);
        return "New Media Item Was Added";
    }

    @PostMapping("/setFilter")
    public void setFilter(@RequestBody FilterRequest filterRequest) {
        this.filter = filterRequest.getFilter(); // Get the filter from the request object
    }

    @GetMapping("/getAll")
    public List<MediaItem> getAllMedia() {
        List<MediaItem> mediaList = mediaService.getAllMedia();//get media list from database
        System.out.println(filter);
        //remove all media without the filter in the name
        mediaList.removeIf(m -> !m.getName().toLowerCase().contains(filter.toLowerCase()));
        return mediaList;
    }
}