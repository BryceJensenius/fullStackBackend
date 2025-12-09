package com.BryceJensenius.MediaOrganizer.service;

import java.util.List;

import com.BryceJensenius.MediaOrganizer.model.MediaItem;
import com.BryceJensenius.MediaOrganizer.model.User;

public interface MediaService {
    public MediaItem saveMedia(MediaItem media, User user);
    public List<MediaItem> getAllMedia(User user);
    public MediaItem getMediaById(int id, User user);
    public void deleteById(int id, User user);
}