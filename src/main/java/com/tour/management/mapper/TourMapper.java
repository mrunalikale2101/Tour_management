package com.tour.management.mapper;

import com.tour.management.dto.TourDTO;
import com.tour.management.entity.Tour;
import org.springframework.stereotype.Component;

@Component
public class TourMapper {
    
    public TourDTO toDTO(Tour tour) {
        if (tour == null) {
            return null;
        }
        
        TourDTO dto = new TourDTO();
        dto.setId(tour.getId());
        dto.setName(tour.getName());
        dto.setDestination(tour.getDestination());
        dto.setDeparture(tour.getDeparture());
        dto.setStartDate(tour.getStartDate());
        dto.setEndDate(tour.getEndDate());
        dto.setDuration(tour.getDuration());
        dto.setPrice(tour.getPrice());
        dto.setMaxCapacity(tour.getMaxCapacity());
        dto.setCurrentBookings(tour.getCurrentBookings());
        dto.setDescription(tour.getDescription());
        dto.setActive(tour.isActive());
        dto.setDiscountPercentage(tour.getDiscountPercentage());
        dto.setDiscountValidUntil(tour.getDiscountValidUntil());
        
        return dto;
    }
    
    public Tour toEntity(TourDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Tour tour = new Tour();
        tour.setId(dto.getId());
        tour.setName(dto.getName());
        tour.setDestination(dto.getDestination());
        tour.setDeparture(dto.getDeparture());
        tour.setStartDate(dto.getStartDate());
        tour.setEndDate(dto.getEndDate());
        tour.setDuration(dto.getDuration());
        tour.setPrice(dto.getPrice());
        tour.setMaxCapacity(dto.getMaxCapacity());
        tour.setCurrentBookings(dto.getCurrentBookings());
        tour.setDescription(dto.getDescription());
        tour.setActive(dto.isActive());
        tour.setDiscountPercentage(dto.getDiscountPercentage());
        tour.setDiscountValidUntil(dto.getDiscountValidUntil());
        
        return tour;
    }
} 