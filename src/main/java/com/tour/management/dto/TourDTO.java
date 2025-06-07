package com.tour.management.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TourDTO {
    private Long id;
    private String name;
    private String destination;
    private String departure;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer duration;
    private BigDecimal price;
    private Integer maxCapacity;
    private Integer currentBookings;
    private String description;
    private boolean isActive;
    private BigDecimal discountPercentage;
    private LocalDateTime discountValidUntil;
} 