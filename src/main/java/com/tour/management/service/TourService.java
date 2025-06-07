package com.tour.management.service;

import com.tour.management.entity.Tour;
import java.util.List;
import java.util.Optional;

public interface TourService {
    List<Tour> getAllTours();
    Optional<Tour> getTourById(Long id);
    Tour createTour(Tour tour);
    Tour updateTour(Long id, Tour tour);
    void deleteTour(Long id);
} 