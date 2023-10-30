package com.tourmanagement.DTOs.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO{

    @NotNull(message = "Customer ID cannot be null!")
    private Long customerId;
    @NotNull(message = "Tour ID cannot be null!")
    private Long tourId;
    @Size(min = 2, message = "Comment must have 2 characters at least")
    private String comment;
    @PositiveOrZero(message = "Rating must be a positive or zero value")
    private Double rating;

}
