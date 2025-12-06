package com.BryceJensenius.MediaOrganizer.service;

import java.util.List;
import java.util.Optional;

import com.BryceJensenius.MediaOrganizer.model.MediaItem;

public interface MediaService {
    public MediaItem saveMedia(MediaItem media);
    public List<MediaItem> getAllMedia();
    public Optional<MediaItem> getMediaById(int id);
    public void deleteById(int id);
}
