package com.tourmanagement.DTOs.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RefreshTokenDTO {
    @NotNull(message = "Refresh token cannot be null")
    @Size(min = 5, message = "Refresh token must be 5 characters at least")
    private String refreshToken;
}
