package com.BryceJensenius.MediaOrganizer.comparators;

import com.BryceJensenius.MediaOrganizer.model.MediaItem;

import java.util.Comparator;

public class MediaComparators {
    // Comparator for sorting by name
    public static Comparator<MediaItem> byName() {
        return Comparator.comparing(MediaItem::getName);//MediaItem::method, shorthand for it to invoke this method to get the value
    }

    // Comparator for sorting by rating (assuming rating is an Integer)
    public static Comparator<MediaItem> byRating() {
        return Comparator.comparingDouble(MediaItem::getRating);
    }

    // Comparator for sorting by finish date
    public static Comparator<MediaItem> byFinishDate() {
        return Comparator.comparing(MediaItem::getFinishDate);
    }

    // Comparator for sorting by order added, ID
    public static Comparator<MediaItem> byId() {
        return Comparator.comparing(MediaItem::getId);
    }
}
