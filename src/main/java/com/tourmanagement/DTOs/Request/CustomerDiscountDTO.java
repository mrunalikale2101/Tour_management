package com.tourmanagement.DTOs.Request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDiscountDTO {

    @NotNull(message = "Discount ID cannot be null!")
    private Long discountId;
    @NotNull(message = "Customer ID cannot be null!")
    private Long customerId;
    @NotNull(message = "Tour ID cannot be null!")
    private Long tourId;
    @NotNull(message = "UsageDate cannot be null!")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date usageDate;
}
