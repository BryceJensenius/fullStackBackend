package com.BryceJensenius.MediaOrganizer.comparators;

import com.BryceJensenius.MediaOrganizer.model.MediaItem;

import java.util.Comparator;

public class MediaComparators {
    // Comparator for sorting by name
    public static Comparator<MediaItem> byName() {
        return Comparator.comparing(MediaItem::getName);
    }

    // Comparator for sorting by rating (assuming rating is an Integer)
    public static Comparator<MediaItem> byRating() {
        return Comparator.comparingDouble(MediaItem::getRating);
    }

    // Comparator for sorting by finish date
    public static Comparator<MediaItem> byFinishDate() {
        return Comparator.comparing(MediaItem::getFinishDate);
    }
}
