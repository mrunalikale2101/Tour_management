package com.tourmanagement.DTOs.Request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookedTourDTO {
    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;
    @NotNull(message = "Tour ID cannot be null")
    private Long tourId;
    private String note;
}
