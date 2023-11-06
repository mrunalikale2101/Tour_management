package com.tourmanagement.DTOs.Request;

import com.tourmanagement.Models.Province;
import lombok.Data;

import java.util.Date;

@Data
public class SearchTourDTO {
    private String name;
    private Date date;
    private Province province;
}
