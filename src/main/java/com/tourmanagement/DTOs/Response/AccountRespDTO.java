package com.tourmanagement.DTOs.Response;


import lombok.Data;

@Data
public class AccountRespDTO {
    private String username;
    private String accessToken;
    private String refreshToken;
}
