package com.tourmanagement.Controllers;

import com.tourmanagement.DTOs.Request.TourGuideDTO;
import com.tourmanagement.Models.TourGuide;
import com.tourmanagement.Services.TourGuideService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tour-guides")
@ResponseStatus(HttpStatus.OK)
public class TourGuideController {
    private final TourGuideService tourGuideService;

    public TourGuideController(TourGuideService tourGuideService) {
        this.tourGuideService = tourGuideService;
    }

    @GetMapping("/{id}")
    public TourGuide handleGetSpecificTourGuide(@PathVariable Long id) {
        TourGuide tourGuide = tourGuideService.getTourGuideById(id);

        return tourGuide;
    }

    @PostMapping()
    public TourGuide handleCreateNewTourGuide(@RequestBody @Valid TourGuideDTO tourGuideDTO) {
        TourGuide newTourGuide = tourGuideService.createNewTourGuide(tourGuideDTO);

        return newTourGuide;
    }

    @PutMapping("/{id}")
    public TourGuide handleUpdateExistedTourGuide(@PathVariable Long id, @RequestBody @Valid TourGuideDTO tourGuideDTO) {
        TourGuide updatedTourGuide = tourGuideService.updateTourGuide(id, tourGuideDTO);

        return updatedTourGuide;
    }
}
