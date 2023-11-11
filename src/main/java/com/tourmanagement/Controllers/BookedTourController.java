package com.tourmanagement.Controllers;


import com.tourmanagement.DTOs.Request.BookTourDTO;
import com.tourmanagement.DTOs.Response.BookTourRespDTO;
import com.tourmanagement.Services.BookTourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("booked-tour")
@ResponseStatus(HttpStatus.OK)
public class BookedTourController 
{
    private final BookTourService bookTourService;

    
    @Autowired
    public BookedTourController(BookTourService bookTourService) {
        this.bookTourService = bookTourService;
    }


    @GetMapping
    public List<BookTourRespDTO> getAllBookTour() {
        List<BookTourRespDTO> booktours = bookTourService.getAllBookedTour();

        return booktours;
    }

    @GetMapping("/{id}")
    public BookTourRespDTO getBookTourById(@PathVariable Long id) {
        BookTourRespDTO bookTourRespDTO = bookTourService.getBookedTourResponseById(id);

        return bookTourRespDTO;
    }

    @GetMapping("/tour/{tourId}")
    public List<BookTourRespDTO> getAllBookTourByTour(@PathVariable Long tourId) {
        List<BookTourRespDTO> booktours = bookTourService.getAllBookedTourByTour(tourId);
        return booktours;
    }

    @GetMapping("/customer/{customerId}")
    public List<BookTourRespDTO> getAllBookTourByCustomer(@PathVariable Long customerId) {
        List<BookTourRespDTO> booktours = bookTourService.getAllBookedTourByCustomer(customerId);
        return booktours;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public BookTourRespDTO createBookTour(@RequestBody BookTourDTO bookTourDTO) {
        BookTourRespDTO newBookTour = bookTourService.createBookedTour(bookTourDTO);

        return newBookTour;
    }

    @PutMapping("/{id}")
    public BookTourRespDTO updateBookTour(@PathVariable Long id, @RequestBody BookTourDTO bookTourDTO) {
        BookTourRespDTO updateBookedTour = bookTourService.updateBookedTour(id, bookTourDTO);

        return updateBookedTour;
    }

    @DeleteMapping("/{id}")
    public String deleteBookedTour(@PathVariable Long id) {
        bookTourService.deleteBookedTour(id);

        return "BookedTour with [%S] deleted successfully!".formatted(id);
    }
}
