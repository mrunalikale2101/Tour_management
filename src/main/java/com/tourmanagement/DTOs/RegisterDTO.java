package com.tourmanagement.DTOs;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.io.Serializable;

@Data
public class RegisterDTO implements Serializable {
    @NotNull(message = "Username cannot be null!")
    @Size(min = 5, message = "Username has 5 characters at least!")
    private String username;

    @NotNull(message = "Password cannot be null!")
    @Size(min = 8, message = "Password has 8 characters at least!")
    private String password;
}
