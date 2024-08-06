package com.BryceJensenius.MediaOrganizer.controller;

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

    @PostMapping("/add")
    public String add(@RequestBody MediaItem media){
        mediaService.saveMedia(media);
        return "New Media Item Was Added";
    }

    @GetMapping("/getAll")
    public List<MediaItem> getAllMedia(){
        return mediaService.getAllMedia();
    }
}