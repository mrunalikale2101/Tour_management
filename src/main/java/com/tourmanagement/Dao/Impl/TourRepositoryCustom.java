package com.tourmanagement.Dao.Impl;

import com.tourmanagement.Models.Tour;

import java.util.List;

public interface TourRepositoryCustom {
    List<Tour> findToursBySightseeingId(Long sightseeingId);
}
