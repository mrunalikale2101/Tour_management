package com.tourmanagement.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;


import java.util.Date;

public class TourDTO {
    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Duration cannot be null")
    private Integer duration;

    @NotNull(message = "Available Seats cannot be null")
    private Integer availableSeats;

    @NotNull(message = "Gathering Address cannot be null")
    private Integer gatheringAddress;

    @NotNull(message = "Price cannot be null")
    private Double price;

    @NotNull(message = "departure Date cannot be null")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date departureDate;

}
