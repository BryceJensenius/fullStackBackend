package com.BryceJensenius.MediaOrganizer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BryceJensenius.MediaOrganizer.model.MediaWatchItem;
import com.BryceJensenius.MediaOrganizer.model.User;
import com.BryceJensenius.MediaOrganizer.repository.MediaWatchlistRepository;
import com.BryceJensenius.MediaOrganizer.repository.UserRepository;

@Service
public class MediaWatchlistService {

    @Autowired
    private MediaWatchlistRepository mediaWatchlistRepository;

    @Autowired
    private UserRepository userRepository;

    public MediaWatchItem add(MediaWatchItem item, User user) {
        user.addMediaWatchItem(item);
        item = mediaWatchlistRepository.save(item);
        userRepository.save(user);
        return item;
    }

    public List<MediaWatchItem> getAllMedia(User user) {
        List<MediaWatchItem> allItems = user.getMediaWatchList();
        return allItems;
    }

    public MediaWatchItem getMediaById(int id, User user) {
        MediaWatchItem item = mediaWatchlistRepository.findById(id);
        if(item == null || item.getUser() == null || item.getUser().getId() != user.getId()){
            return null;
        }
        return mediaWatchlistRepository.findById(id);
    }

    public boolean delete(int id, User user){
        MediaWatchItem item = mediaWatchlistRepository.findById(id);
        if(item == null || item.getUser() == null || item.getUser().getId() != user.getId()){
            return false;
        }
        mediaWatchlistRepository.deleteById(id);
        return true;
    }
}
