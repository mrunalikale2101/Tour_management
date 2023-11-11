package com.tourmanagement.Services;


import com.tourmanagement.DTOs.Request.BookTourDTO;
import com.tourmanagement.DTOs.Response.BookTourRespDTO;
import com.tourmanagement.Models.BookedTour;
import com.tourmanagement.Models.Customer;
import com.tourmanagement.Models.Tour;
import com.tourmanagement.Repositorys.BookTourRepository;
import com.tourmanagement.Shared.Utils.EntityDtoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookTourService {

    private final BookTourRepository bookTourRepository;
    private final ModelMapper modelMapper;
    private final TourService tourService;
    private final CustomerService customerService;
    private final EntityDtoConverter entityDtoConverter;

    public BookTourService(BookTourRepository bookTourRepository, ModelMapper modelMapper, TourService tourService, CustomerService customerService, EntityDtoConverter entityDtoConverter) {
        this.bookTourRepository = bookTourRepository;
        this.modelMapper = modelMapper;
        this.tourService = tourService;
        this.customerService = customerService;
        this.entityDtoConverter = entityDtoConverter;
    }

    public List<BookTourRespDTO> getAllBookedTour() {
        List<BookedTour> bookedTours = bookTourRepository.findAll();
        return bookedTours.stream()
                .map(entityDtoConverter::convertToBookTourRespDTO)
                .collect(Collectors.toList());
    }

    public Long getCountBookedTour() {
        return bookTourRepository.count();
    }

    public BookedTour getBookedTourById(Long id) {
        BookedTour bookedTour = bookTourRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "BookedTour with id [%s] is not found".formatted(id)));

        return bookedTour;
    }

    public BookTourRespDTO getBookedTourResponseById(Long id) {
        BookedTour BookedTour = getBookedTourById(id);
        BookTourRespDTO result = entityDtoConverter.convertToBookTourRespDTO(BookedTour);
        return result;
    }

    public List<BookTourRespDTO> getAllBookedTourByTour(Long tourId){
        tourService.getTourById(tourId);
        List<BookedTour> bookedTours = bookTourRepository.findBookedTourByTour(tourId);
        return bookedTours.stream()
                .map(entityDtoConverter::convertToBookTourRespDTO)
                .collect(Collectors.toList());
    }

    public List<BookTourRespDTO> getAllBookedTourByCustomer(Long customerId){
        customerService.getCustomerById(customerId);
        List<BookedTour> bookedTours = bookTourRepository.findBookedTourByCustomer(customerId);
        return bookedTours.stream()
                .map(entityDtoConverter::convertToBookTourRespDTO)
                .collect(Collectors.toList());
    }

    public BookTourRespDTO createBookedTour(BookTourDTO BookedTourDTO) {
        Tour tour = tourService.getTourById(BookedTourDTO.getTourId());
        Customer customer = customerService.getCustomerById(BookedTourDTO.getCustomerId());

        BookedTour bookedTour = modelMapper.map(BookedTourDTO, BookedTour.class);
        bookedTour.setTour(tour);
        bookedTour.setCustomer(customer);

        return entityDtoConverter.convertToBookTourRespDTO(bookTourRepository.save(bookedTour));
    }

    public BookTourRespDTO updateBookedTour(Long id, BookTourDTO bookedTourDTO) {
        BookedTour bookedTour = getBookedTourById(id);
        Tour tour = tourService.getTourById(bookedTourDTO.getTourId());
        Customer customer = customerService.getCustomerById(bookedTourDTO.getCustomerId());

        modelMapper.map(bookedTourDTO, bookedTour);
        bookedTour.setTour(tour);
        bookedTour.setCustomer(customer);

        return entityDtoConverter.convertToBookTourRespDTO(bookTourRepository.save(bookedTour));
    }

    public void deleteBookedTour(Long id) {
        getBookedTourById(id);
        bookTourRepository.deleteById(id);
    }

}
