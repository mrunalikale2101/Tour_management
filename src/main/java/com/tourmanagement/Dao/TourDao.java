package com.tourmanagement.Dao;

import com.tourmanagement.DTOs.Request.SearchTourDTO;
import com.tourmanagement.Models.Tour;

import java.util.List;

public interface TourDao {
    public List<Tour> searchTours(SearchTourDTO searchTourDTO);
}
