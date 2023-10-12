package com.tourmanagement.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CustomerDTO implements Serializable {
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String idCard;
    private String gender;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;
    private String avatar;

    // Constructors, getters, and setters
}
