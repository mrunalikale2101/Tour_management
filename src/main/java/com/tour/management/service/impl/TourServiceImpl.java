package com.tour.management.service.impl;

import com.tour.management.entity.Tour;
import com.tour.management.repository.TourRepository;
import com.tour.management.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;

    @Autowired
    public TourServiceImpl(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @Override
    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    @Override
    public Optional<Tour> getTourById(Long id) {
        return tourRepository.findById(id);
    }

    @Override
    public Tour createTour(Tour tour) {
        return tourRepository.save(tour);
    }

    @Override
    public Tour updateTour(Long id, Tour tour) {
        if (tourRepository.existsById(id)) {
            tour.setId(id);
            return tourRepository.save(tour);
        }
        throw new RuntimeException("Tour not found with id: " + id);
    }

    @Override
    public void deleteTour(Long id) {
        tourRepository.deleteById(id);
    }
} 