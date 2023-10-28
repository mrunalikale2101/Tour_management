package com.tourmanagement.Controllers;

import com.tourmanagement.Models.SightseeingSpot;
import com.tourmanagement.Services.SightseeingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/sightseeing-spots")
@ResponseStatus(HttpStatus.OK)
public class SightseeingSpotController {
    private final SightseeingSpotService sightseeingSpotService;

    @Autowired
    public SightseeingSpotController(SightseeingSpotService sightseeingSpotService) {
        this.sightseeingSpotService = sightseeingSpotService;
    }

    @GetMapping()
    public List<SightseeingSpot> handleGetSightseeingSpots() {
        List<SightseeingSpot> sightseeingSpots = sightseeingSpotService.getSightseeingSpots();
        return sightseeingSpots;
    }
}
