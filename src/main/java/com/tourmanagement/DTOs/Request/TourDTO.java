package com.tourmanagement.DTOs.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tourmanagement.Models.SightseeingSpot;
import com.tourmanagement.Models.TourGuide;
import com.tourmanagement.Shared.Types.EnumTransportModeTour;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Data
public class TourDTO {
    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Duration cannot be null")
    private Integer duration;

    @NotNull(message = "Available Seats cannot be null")
    private Integer availableSeats;

    @NotNull(message = "Gathering Address cannot be null")
    private String gatheringAddress;

    @NotNull(message = "Price cannot be null")
    private Double price;

    @NotNull(message = "departure Date cannot be null")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date departureDate;

    @NotNull(message = "End Date cannot be null")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date endDate;

    private String images;

    private String departureLocation;

    private String destinationLocation;

    @NotNull(message = "Guide cannot be null")
    private Long guide_id;

    private String idSightSeeing;

}
