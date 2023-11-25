package com.tourmanagement.DTOs.Response;

import com.tourmanagement.Models.Province;
import lombok.Data;

@Data
public class ProvinceDTO {
    private Long id;
    private String name;
    private String codename;

    public static ProvinceDTO convert(Province province) {
        ProvinceDTO dto = new ProvinceDTO();
        dto.setId(province.getId());
        dto.setName(province.getName());
        dto.setCodename(province.getCodename());
        return dto;
    }
}