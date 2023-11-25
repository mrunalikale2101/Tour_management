package com.tourmanagement.DTOs.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class RevenueRespDTO {
    private double totalMoney;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;
}
