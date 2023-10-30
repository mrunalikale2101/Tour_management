package com.tourmanagement.DTOs.Response;

import com.tourmanagement.Models.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRespDTO {

    private Long id;
    private Customer customer;
    private Long tourId;
    private String comment;
    private Double rating;

}
