package com.tourmanagement.DTOs;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tourmanagement.Models.Tour;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class DiscountDTO {

    @NotNull(message = "Code cannot be null")
    @Size(min = 2, message = "Code must have at least 2 characters")
    private String code;

    @PositiveOrZero(message = "Discount percentage must be a positive or zero value")
    private Double discountPercentage;

    @NotNull(message = "Tour ID cannot be null")
    private Long tourId;

    @NotNull(message = "Start date cannot be null")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date startDate;

    @NotNull(message = "End date cannot be null")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date endDate;
}
