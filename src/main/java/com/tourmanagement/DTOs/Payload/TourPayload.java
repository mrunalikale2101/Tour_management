package com.tourmanagement.DTOs.Payload;

import com.tourmanagement.DTOs.Request.TourDTO;
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

    public TourDTO convertTourPayloadToTourDTO(TourPayload payload) {
        TourDTO tourDTO = new TourDTO();
        tourDTO.setName(payload.getName());
        tourDTO.setDuration(payload.getDuration());
        tourDTO.setAvailableSeats(payload.getAvailableSeats());
        tourDTO.setGatheringAddress(payload.getGatheringAddress());
        tourDTO.setPrice(payload.getPrice());
        tourDTO.setDepartureDate(payload.getDepartureDate());
        return tourDTO;
    }
}
