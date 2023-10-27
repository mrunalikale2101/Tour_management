package com.tourmanagement.DTOs;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class ReviewDTO{

    @NotNull(message = "Name cannot be null!")
    private Long customerId;
    @NotNull(message = "Name cannot be null!")
    private Long tourId;
    @Size(min = 2, message = "Comment must have 2 characters at least")
    private String comment;
    private Double rating;


    public ReviewDTO(Long customerId, Long tourId, String comment, Double rating) {
        this.customerId = customerId;
        this.tourId = tourId;
        this.comment = comment;
        this.rating = rating;
    }
}
