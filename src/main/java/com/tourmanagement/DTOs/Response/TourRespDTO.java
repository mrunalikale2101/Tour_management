package com.tourmanagement.DTOs.Response;

import com.tourmanagement.Models.SightseeingSpot;
import com.tourmanagement.Models.TourGuide;
import com.tourmanagement.Shared.Types.EnumTransportModeTour;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TourRespDTO {
    private Long id;
    private String name;
    private Date departureDate;
    private Integer duration;
    private String departureLocation;
    private Integer availableSeats;
    private SightseeingSpot sightseeingSpot;
    private TourGuide guide;
    private String gatheringAddress;
    private EnumTransportModeTour transportationMode = EnumTransportModeTour.BUS;
    private List<String> images;
    private Integer likes;
    private Integer views;
    private Double price;
    private Double rating;
}
