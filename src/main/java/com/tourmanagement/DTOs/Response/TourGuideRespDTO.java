package com.tourmanagement.DTOs.Response;
import com.tourmanagement.Models.Tour;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourGuideRespDTO {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String idCard;
    private List<Tour> tours;
}
