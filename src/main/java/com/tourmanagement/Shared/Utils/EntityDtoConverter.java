package com.tourmanagement.Shared.Utils;

import com.tourmanagement.DTOs.Response.BookTourRespDTO;
import com.tourmanagement.DTOs.Response.CustomerDiscountRespDTO;
import com.tourmanagement.DTOs.Response.DiscountRespDTO;
import com.tourmanagement.DTOs.Response.ReviewRespDTO;
import com.tourmanagement.Models.BookedTour;
import com.tourmanagement.Models.CustomerDiscount;
import com.tourmanagement.Models.Discount;
import com.tourmanagement.Models.Review;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
