package com.tourmanagement.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class ReviewDTO{

    @NotNull(message = "Name cannot be null!")
    private Long customerId;
    @NotNull(message = "Name cannot be null!")
    private Long tourId;
    @Size(min = 2, message = "Comment must have 2 characters at least")
    private String comment;
    @PositiveOrZero(message = "Discount percentage must be a positive or zero value")
    private Double rating;

}
