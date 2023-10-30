package com.tourmanagement.Shared.Utils;

import com.tourmanagement.DTOs.Response.ReviewRespDTO;
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
    public ReviewRespDTO convertToResponseDTO(Review review) {
        ReviewRespDTO dto = modelMapper.map(review, ReviewRespDTO.class);
        dto.setCustomer(review.getCustomer());
        dto.setTourId(review.getTour().getId());
        return dto;
    }
}
