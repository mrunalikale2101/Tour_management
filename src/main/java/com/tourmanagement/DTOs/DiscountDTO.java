package com.tourmanagement.DTOs;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tourmanagement.Models.Tour;
import lombok.Data;

import java.util.Date;

@Data
public class DiscountDTO {
    private String code;

    private Double discountPercentage;

    private Long tourId;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date startDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date endDate;
}
