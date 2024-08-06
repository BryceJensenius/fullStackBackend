package com.BryceJensenius.MediaOrganizer.service;

import com.BryceJensenius.MediaOrganizer.model.MediaItem;

import java.util.List;

public interface MediaService {
    public MediaItem saveMedia(MediaItem media);
    public List<MediaItem> getAllMedia();
}
