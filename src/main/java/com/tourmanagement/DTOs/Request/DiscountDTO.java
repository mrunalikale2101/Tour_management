package com.tourmanagement.DTOs.Request;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
