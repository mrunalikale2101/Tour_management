package com.tourmanagement.DTOs.Response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.tourmanagement.Models.Customer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDiscountRespDTO {
    private Long id;
    private Long discountId;
    private Customer customer;
    private Long tourId;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date usageDate;
}
