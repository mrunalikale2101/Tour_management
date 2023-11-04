package com.tourmanagement.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourmanagement.DTOs.Request.TourDTO;
import com.tourmanagement.Models.Tour;
import com.tourmanagement.Services.ImageService;
import com.tourmanagement.Services.TourService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tours")
@ResponseStatus(HttpStatus.OK)
public class TourController {
    private final TourService tourService;
    private final ImageService imageService;
    private TourDTO tourDTO;
    private MultipartFile imageFile;

    public TourController(TourService tourService, ImageService imageService) {
        this.tourService = tourService;
        this.imageService = imageService;
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
    public Tour handleCreateNewTour(@RequestPart("tourDTO") @Valid TourDTO tourDTO,
                                    @RequestPart("images") MultipartFile file) {
        Tour createdTour = tourService.createTour(tourDTO);
        imageService.uploadImageAndAddToTour(file, createdTour.getId());
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
            @RequestParam(value = "date", required = false) String dateString) {
        Date date = null;
        if (dateString != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
            }
        }
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

    @PostMapping("/uploads")
    public String uploadImage(@RequestParam("images")MultipartFile file,
                              @RequestParam(value = "id_tour", required = false) Long id) throws Exception{
        return imageService.uploadImageAndAddToTour(file, id);
    }

}
