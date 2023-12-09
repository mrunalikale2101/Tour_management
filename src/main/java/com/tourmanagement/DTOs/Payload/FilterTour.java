package com.tourmanagement.DTOs.Payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FilterTour {
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date startDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date endDate;
    private Double minPrice;
    private Double maxPrice;
    @PositiveOrZero(message = "Page must be a positive number or Zero number")
    @NotNull(message = "Page cannot be null")
    private int page;
    @Positive(message = "Items per page must be a positive number")
    @NotNull(message = "ItemsPerPage cannot be null")
    private int itemsPerPage;
    private String typeSort;

    @AssertTrue(message = "Start date must be before or equal to end date")
    private boolean isStartDateBeforeEndDate() {
        return startDate == null || endDate == null || !startDate.after(endDate);
    }
}