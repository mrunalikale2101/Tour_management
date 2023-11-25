package com.tourmanagement.DTOs.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT+7")
    private Date departureDate;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT+7")
    private Date endDate;
    private Integer duration;
    private String departureLocation;
    private String destinationLocation;
    private TourGuide guide;
    private Integer availableSeats;
    private String gatheringAddress;
    private EnumTransportModeTour transportationMode = EnumTransportModeTour.BUS;
    private List<String> images;
    private List<SightseeingSpot> sightseeingSpots;
    private Integer likes;
    private Integer views;
    private Double price;
    private Double rating;
}
