package com.tour.management.controller;

import com.tour.management.dto.TourDTO;
import com.tour.management.entity.Tour;
import com.tour.management.mapper.TourMapper;
import com.tour.management.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tours")
public class TourController {

    private final TourService tourService;
    private final TourMapper tourMapper;

    @Autowired
    public TourController(TourService tourService, TourMapper tourMapper) {
        this.tourService = tourService;
        this.tourMapper = tourMapper;
    }

    @GetMapping
    public ResponseEntity<List<TourDTO>> getAllTours() {
        List<TourDTO> tours = tourService.getAllTours().stream()
            .map(tourMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(tours);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourDTO> getTourById(@PathVariable Long id) {
        return tourService.getTourById(id)
                .map(tourMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TourDTO> createTour(@RequestBody TourDTO tourDTO) {
        Tour tour = tourMapper.toEntity(tourDTO);
        Tour createdTour = tourService.createTour(tour);
        return ResponseEntity.ok(tourMapper.toDTO(createdTour));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourDTO> updateTour(@PathVariable Long id, @RequestBody TourDTO tourDTO) {
        try {
            Tour tour = tourMapper.toEntity(tourDTO);
            Tour updatedTour = tourService.updateTour(id, tour);
            return ResponseEntity.ok(tourMapper.toDTO(updatedTour));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        tourService.deleteTour(id);
        return ResponseEntity.ok().build();
    }
} 