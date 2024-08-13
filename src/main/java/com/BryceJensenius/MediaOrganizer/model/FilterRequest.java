package com.BryceJensenius.MediaOrganizer.model;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.BryceJensenius.MediaOrganizer.comparators.MediaComparators;

public class FilterRequest {
    private String nameFilter = "";
    private String ratingFilter = "";
    private String sortType = "name";
    private String sortOrder = "desc";

    public String getNameFilter() {
        return nameFilter;
    }

    public String getRatingFilter() {
        return ratingFilter;
    }

    public String getSortType() {
        return sortType;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public void setRatingFilter(String ratingFilter) {
        this.ratingFilter = ratingFilter;
    }

    public void sort(List<MediaItem> media){
        media.sort(getComparator(sortType, sortOrder));
    }

    public static Comparator<MediaItem> getComparator(String sortType, String sortOrder) {
        Comparator<MediaItem> comparator;

        switch (sortType) {
            case "id":
                comparator = MediaComparators.byId();
                break;
            case "name":
                comparator = MediaComparators.byName();
                break;
            case "rating":
                comparator = MediaComparators.byRating();
                break;
            case "finishDate":
                comparator = MediaComparators.byFinishDate();
                break;
            default:
                throw new IllegalArgumentException("Invalid sort type: " + sortType);
        }

        if(!Objects.equals(sortOrder, "asc")){
            return comparator.reversed();
        }
        return comparator;
    }
}
