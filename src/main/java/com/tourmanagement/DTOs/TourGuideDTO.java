package com.tourmanagement.DTOs;

import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
public class TourGuideDTO implements Serializable {
    @NotNull(message = "Name cannot be null!")
    @Size(min = 2, message = "Name must have 2 characters at least")
    private String name;

    @NotNull(message = "Address cannot be null!")
    @Size(min = 2, message = "Address must have 2 characters at least")
    private String address;

    @NotNull(message = "Phone Number cannot be null!")
    @Size(min = 10, max = 10, message = "Phone number must have exactly 10 characters")
    private String phoneNumber;

    @Email(message = "Email is not suitable!")
    private String email;

    @NotNull(message = "Id card cannot be null!")
    @Size(min = 2, message = "Id card must have 2 characters at least")
    private String idCard;
}
