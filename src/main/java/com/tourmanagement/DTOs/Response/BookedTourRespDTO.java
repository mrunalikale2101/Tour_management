package com.tourmanagement.DTOs.Response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.tourmanagement.Models.Customer;
import com.tourmanagement.Models.Discount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookedTourRespDTO {
    private Long id;
    private Customer customer;
    private TourRespDTO tour;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date bookingDate;
    private String status;
    private String note;
    private boolean isPaid;
    private double totalMoney;
}
