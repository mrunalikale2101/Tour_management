package com.tourmanagement.Controllers;


import com.tourmanagement.DTOs.Payload.FilterBookedTour;
import com.tourmanagement.DTOs.Request.BookedTourDTO;
import com.tourmanagement.DTOs.Request.UpdateStatusBookedTourDTO;
import com.tourmanagement.DTOs.Response.BookedTourRespDTO;
import com.tourmanagement.DTOs.Response.PaginationRespDTO;
import com.tourmanagement.Services.BookedTourService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("booked-tour")
@ResponseStatus(HttpStatus.OK)
public class BookedTourController {
    private final BookedTourService bookedTourService;


    @Autowired
    public BookedTourController(BookedTourService bookedTourService) {
        this.bookedTourService = bookedTourService;
    }


    @GetMapping
    public PaginationRespDTO<BookedTourRespDTO> getAllBookTour(@Valid @ModelAttribute FilterBookedTour filterBookedTour) {
        return bookedTourService.getAllBookedTour(filterBookedTour);
    }

    @GetMapping("/{id}")
    public BookedTourRespDTO getBookTourById(@PathVariable Long id) {
        return bookedTourService.getBookedTourResponseById(id);
    }

    @PatchMapping("/{id}/status")
    public BookedTourRespDTO updateStatusBookedTour(@PathVariable Long id, @Valid @RequestBody UpdateStatusBookedTourDTO updateStatusBookedTourDTO){
        return bookedTourService.updateStatus(id, updateStatusBookedTourDTO);
    }

    @GetMapping("/tour/{tourId}")
    public List<BookedTourRespDTO> getBookedToursOfTour(@PathVariable Long tourId) {
        return bookedTourService.getAllBookedTourByTour(tourId);
    }

    @GetMapping("/customer/{customerId}")
    public List<BookedTourRespDTO> getAllBookTourByCustomer(@PathVariable Long customerId) {
        return bookedTourService.getAllBookedTourByCustomer(customerId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public BookedTourRespDTO createBookTour(@RequestBody BookedTourDTO bookedTourDTO) {
        return bookedTourService.createBookedTour(bookedTourDTO);
    }

    @PutMapping("/{id}")
    public BookedTourRespDTO updateBookTour(@PathVariable Long id, @RequestBody BookedTourDTO bookedTourDTO) {
        return bookedTourService.updateBookedTour(id, bookedTourDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteBookedTour(@PathVariable Long id) {
        bookedTourService.deleteBookedTour(id);

        return "BookedTour with [%S] deleted successfully!".formatted(id);
    }
}
