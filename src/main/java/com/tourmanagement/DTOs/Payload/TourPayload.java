package com.tourmanagement.DTOs.Payload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class TourPayload {
    private String name;
    private Integer duration;
    private Integer availableSeats;
    private String gatheringAddress;
    private Double price;
    private Date departureDate;
    private MultipartFile[] images;
}
