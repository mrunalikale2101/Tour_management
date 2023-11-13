package com.tourmanagement.DTOs.Payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FilterBookedTour {
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date startDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date endDate;
    private String status;
    @PositiveOrZero(message = "Page must be a positive number or Zero number")
    private int page;
    @Positive(message = "Items per page must be a positive number")
    private int itemsPerPage;
}
