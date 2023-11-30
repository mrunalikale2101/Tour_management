package com.tourmanagement.DTOs.Response;

import lombok.Data;

@Data
public class ScheduleTourRespDTO {
    private String title;
    private String description;
    private Long id;
    private Integer day;
}
