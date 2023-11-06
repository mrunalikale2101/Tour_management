package com.tourmanagement.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountRespDTO {

    private Long id;

    private String code;

    private Double discountPercentage;

    private Long tourId;

    private Date startDate;

    private Date endDate;
}
