package com.BryceJensenius.MediaOrganizer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BryceJensenius.MediaOrganizer.model.FilterRequest;
import com.BryceJensenius.MediaOrganizer.model.MediaItem;
import com.BryceJensenius.MediaOrganizer.model.User;
import com.BryceJensenius.MediaOrganizer.service.MediaService;
import com.BryceJensenius.MediaOrganizer.service.UserService;

@RestController
@RequestMapping("/mediaItems")
@CrossOrigin
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @Autowired
    private UserService userService;

    // private FilterRequest filterRequest = new FilterRequest();

    @PostMapping("/add")
    public String add(@RequestHeader("Authorization") String authHeader, @RequestBody MediaItem media){
        User user = userService.getUserFromAuthorizationHeader(authHeader);
        if(user == null){
            return "Invalid or expired session token";
        }
        mediaService.saveMedia(media, user);
        return "New Media Item Was Added";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@RequestHeader("Authorization") String authHeader, @PathVariable int id){
        User user = userService.getUserFromAuthorizationHeader(authHeader);
        if(user == null){
            return "Invalid or expired session token";
        }
        mediaService.deleteById(id, user);
        return "Media Item Was Deleted";
    }

    // @PostMapping("/setFilter")
    // public void setFilter(@RequestBody FilterRequest filterRequest) {
    //     this.filterRequest = filterRequest; // Get the filter from the request object
    // }

    @GetMapping("/getById/{id}")
    public MediaItem getMediaById(@RequestHeader("Authorization") String authHeader, @PathVariable int id){
        User user = userService.getUserFromAuthorizationHeader(authHeader);
        if(user == null){
            return null;
        }
        return mediaService.getMediaById(id, user);
    }

    @GetMapping("/getAll")
    public List<MediaItem> getAllMedia(
            @RequestParam(required = false, defaultValue = "") String nameFilter,
            @RequestParam(required = false, defaultValue = "") String ratingFilter,
            @RequestParam(required = false, defaultValue = "rating") String sortType,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder,
            @RequestHeader("Authorization") String authHeader) {
        User user = userService.getUserFromAuthorizationHeader(authHeader);
        if(user == null){
            return null;
        }
        
        FilterRequest filterRequest = new FilterRequest(nameFilter, ratingFilter, sortType, sortOrder);
        
        List<MediaItem> mediaList = mediaService.getAllMedia(user, filterRequest); // get media list from database

        return mediaList;
    }
}