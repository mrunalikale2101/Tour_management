package com.tourmanagement.DTOs.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleTourReqDTO {
    @NotNull(message = "Title cannot be empty!")
    private String title;

    @NotNull(message = "Description cannot be empty!")
    private String description;

    @NotNull(message = "Day cannot be empty!")
    @Positive(message = "Day must be a positive number!")
    private Integer day;
}
