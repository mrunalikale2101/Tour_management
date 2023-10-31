package com.tourmanagement.Controllers;

import com.tourmanagement.DTOs.Request.TourDTO;
import com.tourmanagement.Models.Tour;
import com.tourmanagement.Services.TourService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tours")
@ResponseStatus(HttpStatus.OK)
public class TourController {
    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping("/getAll")
    public List<Tour> handleGetTours() {
        List<Tour> tours = tourService.getTours();

        return tours;
    }

    @GetMapping("/{id}")
    public Tour handleGetTourById(@PathVariable Long id) {
        Tour tour = tourService.getTourById(id);
        return tour;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Tour handleCreateNewTour(@RequestBody @Valid TourDTO tourDTO) {
        Tour createdTour = tourService.createTour(tourDTO);
        return createdTour;
    }

    @PutMapping("/{id}")
    public Tour handleUpdateExistedTour(@PathVariable Long id, @RequestBody @Valid TourDTO tourDTO) {
        Tour updatedTour = tourService.updateTour(id, tourDTO);

        return updatedTour;
    }

    @DeleteMapping("/{id}")
    public String handleDeleteTour(@PathVariable Long id) {
        tourService.deleteTour(id);

        return "Tour with [%S] deleted successfully!".formatted(id);
    }

    @GetMapping("/search")
    public List<Tour> searchTours(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "address", required = false) String sightseeing,
            @RequestParam(value = "province", required = false) String province,
            @RequestParam(value = "date", required = false) Date date) {
        List<Tour> tours = tourService.searchTours(name, sightseeing, province, date);
        return tours;
    }

    @GetMapping("/filter")
    public List<Tour> filterToursByPrice(
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice) {
        List<Tour> filteredTours = tourService.filterToursByPrice(minPrice, maxPrice);
        return filteredTours;
    }

    @GetMapping("/top-rating")
    public List<Tour> getTopRatedTours() {
        List<Tour> topRatedTours = tourService.getTopRatedTours(5);
        return topRatedTours;
    }

}
