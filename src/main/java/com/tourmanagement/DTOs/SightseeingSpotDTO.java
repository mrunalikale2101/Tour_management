package com.tourmanagement.DTOs;

import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
public class SightseeingSpotDTO implements Serializable {
    @NotNull(message = "Name cannot be null!")
    @Size(min = 2, message = "Name must have 2 characters at least")
    private String name;

    @NotNull(message = "Address cannot be null!")
    @Size(min = 2, message = "Address must have 2 characters at least")
    private String address;
}
