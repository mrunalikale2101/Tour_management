package com.tourmanagement.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TopProvinceRespDTO {
    private String province;
    private Long statistic;
}
