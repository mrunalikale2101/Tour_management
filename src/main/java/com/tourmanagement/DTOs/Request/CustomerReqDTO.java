package com.tourmanagement.DTOs.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerReqDTO {
    @NotNull(message = "Name cannot be null!")
    private String name;
    @NotNull(message = "Address cannot be null!")
    private String address;
    @NotNull(message = "PhoneNumber cannot be null!")
    private String phoneNumber;
    @NotNull(message = "Email cannot be null!")
    private String email;
    @NotNull(message = "ID Card cannot be null!")
    private String idCard;
    @NotNull(message = "Gender cannot be null!")
    private Boolean gender;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;
    private MultipartFile avatar;
}
