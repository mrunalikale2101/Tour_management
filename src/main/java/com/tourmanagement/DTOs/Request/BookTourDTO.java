package com.tourmanagement.DTOs.Request;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.tourmanagement.Shared.Types.EnumStatusBookedTour;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookTourDTO {

    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;
    @NotNull(message = "Tour ID cannot be null")
    private Long tourId;
    @NotNull(message = "Booking date cannot be null")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date bookingDate;
    @NotNull(message = "Status cannot be null")
    private EnumStatusBookedTour status;
    private String note;
}
