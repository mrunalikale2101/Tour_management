package com.tourmanagement.DTOs.Response;

import com.tourmanagement.Models.Province;
import com.tourmanagement.Models.SightseeingSpot;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class SightseeingSpotRespDTO {
    private Long id;
    private String name;
    private String address;
    private ProvinceDTO province;

    public static SightseeingSpotRespDTO convert(SightseeingSpot sightseeingSpot) {
        SightseeingSpotRespDTO dto = new SightseeingSpotRespDTO();
        dto.setId(sightseeingSpot.getId());
        dto.setName(sightseeingSpot.getName());
        dto.setAddress(sightseeingSpot.getAddress());

        if (sightseeingSpot.getProvince() != null) {
            dto.setProvince(ProvinceDTO.convert(sightseeingSpot.getProvince()));
        }

        return dto;
    }

    public static List<SightseeingSpotRespDTO> convertList(List<SightseeingSpot> sightseeingSpots) {
        return sightseeingSpots.stream().map(SightseeingSpotRespDTO::convert).collect(Collectors.toList());
    }
}
