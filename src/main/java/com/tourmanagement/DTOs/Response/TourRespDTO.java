package com.tourmanagement.DTOs.Response;

import com.tourmanagement.Shared.Types.EnumTransportModeTour;
import lombok.Data;

import java.util.List;

@Data
public class TourRespDTO {
    private Long id;
    private String name;
    private String departureDate;
    private Integer duration;
    private String departureLocation;
    private Integer availableSeats;
    private String gatheringAddress;
    private EnumTransportModeTour transportationMode = EnumTransportModeTour.BUS;
    private List<String> images;
    private Integer likes;
    private Integer views;
    private Double price;
    private Double rating;
}
