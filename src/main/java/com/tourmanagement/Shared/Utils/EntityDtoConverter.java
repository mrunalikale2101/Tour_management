package com.tourmanagement.Shared.Utils;

import com.tourmanagement.DTOs.Response.*;
import com.tourmanagement.Models.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Component
public class EntityDtoConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public EntityDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
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

    public BookTourRespDTO convertToBookTourRespDTO(BookedTour bookedTour) {
        BookTourRespDTO dto = modelMapper.map(bookedTour, BookTourRespDTO.class);
        dto.setCustomer(bookedTour.getCustomer());
        dto.setTourId(bookedTour.getId());

        return dto;
    }

    public TourRespDTO convertToTourRespDTO(Tour tour) {
        TourRespDTO dto = modelMapper.map(tour, TourRespDTO.class);

        if (tour.getImages() == null) {
            dto.setImages(new ArrayList<>());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            dto.setDepartureDate(sdf.format(tour.getDepartureDate()));
            dto.setImages(Converter.convertJsonImagesToListImages(tour.getImages()));
        }

        return dto;
    }
}
