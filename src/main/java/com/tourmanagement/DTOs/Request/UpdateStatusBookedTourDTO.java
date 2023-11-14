package com.tourmanagement.DTOs.Request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateStatusBookedTourDTO {
    @Pattern(
            regexp = "^(confirmed|rejected)$",
            message = "Invalid status. Allowed values are: confirmed, rejected"
    )
    private String status;
}
