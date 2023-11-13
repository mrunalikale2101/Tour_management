package com.tourmanagement.Dao;

import com.tourmanagement.DTOs.Payload.FilterBookedTour;
import com.tourmanagement.Models.BookedTour;

import java.util.List;

public interface BookedTourDao {
    public List<BookedTour> filter(FilterBookedTour filter);
    public Long filterCount(FilterBookedTour filter);
}
