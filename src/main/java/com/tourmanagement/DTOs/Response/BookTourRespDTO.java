package com.tourmanagement.DTOs.Response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.tourmanagement.Models.Customer;
import com.tourmanagement.Shared.Types.EnumStatusBookedTour;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookTourRespDTO {
    Long id;

    private Customer customer;

    private Long tourId;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date bookingDate;

    private EnumStatusBookedTour status;

    private String note;
}
