package com.BryceJensenius.MediaOrganizer.service;

import com.BryceJensenius.MediaOrganizer.model.MediaItem;

import java.util.List;
import java.util.Optional;

public interface MediaService {
    public MediaItem saveMedia(MediaItem media);
    public List<MediaItem> getAllMedia();
    public Optional<MediaItem> getMediaById(int id);
}
