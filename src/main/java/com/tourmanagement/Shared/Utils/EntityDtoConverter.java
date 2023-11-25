package com.tourmanagement.Shared.Utils;

import com.tourmanagement.DTOs.Response.*;
import com.tourmanagement.Models.*;
import com.tourmanagement.Services.SightseeingSpotService;
import com.tourmanagement.Services.TourGuideService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class EntityDtoConverter {

    private final ModelMapper modelMapper;
    private final SightseeingSpotService sightseeingSpotService;

    @Autowired
    public EntityDtoConverter(ModelMapper modelMapper, SightseeingSpotService sightseeingSpotService)
    {
        this.modelMapper = modelMapper;
        this.sightseeingSpotService = sightseeingSpotService;
    }

    // Helper method to convert Review entity to ReviewResponseDTO
    public ReviewRespDTO convertToReviewResponseDTO(Review review) {
        ReviewRespDTO dto = modelMapper.map(review, ReviewRespDTO.class);
        dto.setCustomer(review.getCustomer());
        dto.setTourId(review.getTour().getId());
        return dto;
    }

    // Helper method to convert Discount entity to DiscountRespDTO
    public DiscountRespDTO convertToDiscountRespDTO(Discount discount) {
        DiscountRespDTO dto = modelMapper.map(discount, DiscountRespDTO.class);
        dto.setTourId(discount.getTour().getId());
        return dto;
    }

    public CustomerDiscountRespDTO convertToCustomerDiscountRespDTO(CustomerDiscount customerDiscount) {
        CustomerDiscountRespDTO dto = modelMapper.map(customerDiscount, CustomerDiscountRespDTO.class);
        dto.setDiscountId(customerDiscount.getDiscount().getId());
        dto.setCustomer(customerDiscount.getCustomer());
        dto.setTourId(customerDiscount.getTour().getId());
        return dto;
    }

    public BookedTourRespDTO convertToBookTourRespDTO(BookedTour bookedTour) {
        BookedTourRespDTO dto = modelMapper.map(bookedTour, BookedTourRespDTO.class);
        dto.setCustomer(bookedTour.getCustomer());
        dto.setTour(this.convertToTourRespDTO(bookedTour.getTour()));

        return dto;
    }

    public TourRespDTO convertToTourRespDTO(Tour tour) {
        TourRespDTO dto = modelMapper.map(tour, TourRespDTO.class);

        List<SightseeingSpot> sightseeingSpots = new ArrayList<>();

        if (tour.getIdSightSeeing() == null){
            dto.setSightseeingSpots(new ArrayList<>());
        } else {
            List<String> string_id = Converter.convertJsonIDToListSightSeeing(tour.getIdSightSeeing());
            for (String id: string_id){
                SightseeingSpot sightseeingSpot = sightseeingSpotService.getSightSeeingSpotById(Long.parseLong(id));
                sightseeingSpots.add(sightseeingSpot);
            }
            dto.setSightseeingSpots(sightseeingSpots);
        }
        if (tour.getImages() == null) {
            dto.setImages(new ArrayList<>());
        } else {
            dto.setImages(Converter.convertJsonImagesToListImages(tour.getImages()));
        }

        return dto;
    }
}
