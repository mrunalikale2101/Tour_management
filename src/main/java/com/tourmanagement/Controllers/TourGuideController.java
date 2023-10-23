package com.tourmanagement.Controllers;

import com.tourmanagement.Services.TourGuideService;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class TourGuideController {
    private final TourGuideService tourGuideService;

    public TourGuideController(TourGuideService tourGuideService) {
        this.tourGuideService = tourGuideService;
    }
}
