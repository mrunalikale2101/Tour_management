package com.tourmanagement.Services;

import com.tourmanagement.DTOs.TourDTO;
import com.tourmanagement.Models.Tour;
import com.tourmanagement.Models.SightseeingSpot;
import com.tourmanagement.Repositorys.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
@Service
public class TourService {
    private final TourRepository tourRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TourService(TourRepository tourRepository, ModelMapper modelMapper) {
        this.tourRepository = tourRepository;
        this.modelMapper = modelMapper;
    }

    public List<Tour> getTours(){
        List<Tour> tours = tourRepository.findAll();

        return tours;
    }

    public Tour getTourById(Long id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tour with id [%s] is not found!".formatted(id)));

        return tour;
    }

    public Tour createTour(TourDTO tourDTO) {
        Tour tour = modelMapper.map(tourDTO, Tour.class);
        return tourRepository.save(tour);
    }

    public Tour updateTour(Long id, TourDTO tourDTO) {
        getTourById(id);

        Tour tourUpdate = modelMapper.map(tourDTO, Tour.class);
        tourUpdate.setId(id);

        return tourRepository.save(tourUpdate);
    }

    public void deleteTour(Long id) {
        getTourById(id);
        tourRepository.deleteById(id);
    }

    public List<Tour> searchTours(String name, String sightseeing, String province, Date date) {
        return tourRepository.searchTour(name, sightseeing, province, date);
    }

    public List<Tour> filterToursByPrice(Double minPrice, Double maxPrice) {
        return tourRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Tour> getTopRatedTours(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Tour> topRatedTours = tourRepository.findTopRatedTours(pageable);
        return topRatedTours;
    }

}
