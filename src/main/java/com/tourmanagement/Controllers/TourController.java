package com.tourmanagement.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourmanagement.DTOs.Payload.PaginationRequest;
import com.tourmanagement.DTOs.Payload.TourPayload;
import com.tourmanagement.DTOs.Request.SearchTourDTO;
import com.tourmanagement.DTOs.Request.TourDTO;
import com.tourmanagement.DTOs.Response.BookedTourRespDTO;
import com.tourmanagement.DTOs.Response.PaginationRespDTO;
import com.tourmanagement.DTOs.Response.TourRespDTO;
import com.tourmanagement.Models.Tour;
import com.tourmanagement.Services.TourService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
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
    public List<TourRespDTO> handleGetTours() {
        List<TourRespDTO> tours = tourService.getTours();
        return tours;
    }

    @GetMapping
    public PaginationRespDTO<TourRespDTO> getAllTour(@ModelAttribute PaginationRequest paginationRequest) {
        return tourService.getAllTour(paginationRequest);
    }

    @GetMapping("/{id}")
    public Tour handleGetTourById(@PathVariable Long id) {
        Tour tour = tourService.getTourById(id);
        return tour;
    }

    @RequestMapping(value = "" , method = RequestMethod.POST, consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.CREATED)
    public Tour handleCreateNewTour(@ModelAttribute @Valid TourPayload payload) {
        Tour createdTour = tourService.createTour(payload);
        return createdTour;
    }

    @RequestMapping(value = "/{id}" , method = RequestMethod.POST)
    public Tour handleUpdateExistedTour(@PathVariable Long id, @ModelAttribute @Valid TourPayload payload) {
        Tour updatedTour = tourService.updateDataTour(id, payload);

        return updatedTour;
    }

    @DeleteMapping("/{id}")
    public String handleDeleteTour(@PathVariable Long id) {
        tourService.deleteTour(id);

        return "Tour with [%S] deleted successfully!".formatted(id);
    }

    @DeleteMapping("/delete-sightseeing/{id}")
    public String handleDelete(@PathVariable Long id) {
        tourService.removeSightseeing(id);
        return "Tour with [%S] deleted successfully!".formatted(id);
    }

    @GetMapping("/search")
    public List<TourRespDTO> searchTours(
            @ModelAttribute SearchTourDTO searchTourDTO) {
        List<TourRespDTO> tours = tourService.searchTours(searchTourDTO);
        return tours;
    }


    @GetMapping("/search-page")
    public PaginationRespDTO<TourRespDTO> searchToursPage(
            @RequestParam(value = "name", required = false) String name, @ModelAttribute PaginationRequest paginationRequest) {
        return tourService.searchToursPage(name, paginationRequest);
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
    public String uploadImage(@RequestParam("images") MultipartFile file,
                              @RequestParam(value = "id_tour", required = false) Long id,
                              @RequestParam(value = "index") int index){
        Tour updatedTour = tourService.updateuploadImageTour(id, file, index);
        return "Success!";

    }



}