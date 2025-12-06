package com.BryceJensenius.MediaOrganizer.controller;

import java.util.List;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BryceJensenius.MediaOrganizer.model.MediaWatchItem;
import com.BryceJensenius.MediaOrganizer.model.User;
import com.BryceJensenius.MediaOrganizer.service.MediaWatchlistService;
import com.BryceJensenius.MediaOrganizer.service.UserService;

@RestController
@RequestMapping("/mediaWatch")
@CrossOrigin
public class MediaWatchlistController {
    @Autowired
    private MediaWatchlistService mediaWatchlistService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String add(@RequestHeader("Authorization") String authHeader, @RequestBody MediaWatchItem mediaWatchItem){
        User user = userService.getUserFromAuthorizationHeader(authHeader);
        if(user == null){
            return "Invalid or expired session token";
        }
        MediaWatchItem result = mediaWatchlistService.add(mediaWatchItem, user);
        if(result == null){
            return "Failed to add Media Watch List Item";
        }
        return "New Media Watch List Item Was Added";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id, @RequestHeader("Authorization") String authHeader){
        User user = userService.getUserFromAuthorizationHeader(authHeader);
        if(user == null){
            return "Invalid or expired session token";
        }
        boolean result = mediaWatchlistService.delete(id, user);
        if(!result){
            return "Failed to delete Media Watch List item";
        }
        return "Media Watch List item deleted";
    }

    @GetMapping("/getById/{id}")
    public MediaWatchItem getMediaById(@PathVariable int id, @RequestHeader("Authorization") String authHeader){
        User user = userService.getUserFromAuthorizationHeader(authHeader);
        if(user == null){
            return null;
        }
        return mediaWatchlistService.getMediaById(id, user);
    }

    @GetMapping("/getAll")
    public List<MediaWatchItem> getAllMedia(@RequestHeader("Authorization") String authHeader) {
        User user = userService.getUserFromAuthorizationHeader(authHeader);
        if(user == null){
            return null;
        }
        return mediaWatchlistService.getAllMedia(user); //get media list from database
    }
}