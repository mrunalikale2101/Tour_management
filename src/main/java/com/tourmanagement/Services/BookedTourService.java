package com.tourmanagement.Services;


import com.tourmanagement.DTOs.Payload.FilterBookedTour;
import com.tourmanagement.DTOs.Request.BookedTourDTO;
import com.tourmanagement.DTOs.Request.TourDTO;
import com.tourmanagement.DTOs.Response.BookedTourRespDTO;
import com.tourmanagement.DTOs.Response.PaginationRespDTO;
import com.tourmanagement.Dao.BookedTourDao;
import com.tourmanagement.Models.BookedTour;
import com.tourmanagement.Models.Customer;
import com.tourmanagement.Models.Tour;
import com.tourmanagement.Repositorys.BookedTourRepository;
import com.tourmanagement.Shared.Utils.EntityDtoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookedTourService {

    private final BookedTourRepository bookedTourRepository;
    private final ModelMapper modelMapper;
    private final TourService tourService;
    private final CustomerService customerService;
    private final EntityDtoConverter entityDtoConverter;
    private final BookedTourDao bookedTourDao;

    public BookedTourService(BookedTourRepository bookedTourRepository,
                             ModelMapper modelMapper,
                             TourService tourService,
                             CustomerService customerService,
                             EntityDtoConverter entityDtoConverter,
                             BookedTourDao bookedTourDao) {
        this.bookedTourRepository = bookedTourRepository;
        this.modelMapper = modelMapper;
        this.tourService = tourService;
        this.customerService = customerService;
        this.entityDtoConverter = entityDtoConverter;
        this.bookedTourDao = bookedTourDao;
    }

    public PaginationRespDTO<BookedTourRespDTO> getAllBookedTour(FilterBookedTour filterBookedTour) {
        PaginationRespDTO<BookedTourRespDTO> result = new PaginationRespDTO<BookedTourRespDTO>();
        result.setTotal(bookedTourDao.filterCount(filterBookedTour));
        result.setPage(filterBookedTour.getPage());
        result.setItemsPerPage(filterBookedTour.getItemsPerPage());

        List<BookedTour> bookedTours = bookedTourDao.filter(filterBookedTour);

        result.setData(
                bookedTours.stream()
                        .map(entityDtoConverter::convertToBookTourRespDTO)
                        .collect(Collectors.toList()));

        return result;
    }

    public Long getCountBookedTour() {
        return bookedTourRepository.count();
    }

    public BookedTour getBookedTourById(Long id) {
        BookedTour bookedTour = bookedTourRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "BookedTour with id [%s] is not found".formatted(id)));

        return bookedTour;
    }

    public BookedTourRespDTO getBookedTourResponseById(Long id) {
        BookedTour BookedTour = getBookedTourById(id);
        BookedTourRespDTO result = entityDtoConverter.convertToBookTourRespDTO(BookedTour);
        return result;
    }

    public List<BookedTourRespDTO> getAllBookedTourByTour(Long tourId) {
        tourService.getTourById(tourId);
        List<BookedTour> bookedTours = bookedTourRepository.findBookedTourByTour(tourId);
        return bookedTours.stream()
                .map(entityDtoConverter::convertToBookTourRespDTO)
                .collect(Collectors.toList());
    }

    public List<BookedTourRespDTO> getAllBookedTourByCustomer(Long customerId) {
        customerService.getCustomerById(customerId);
        List<BookedTour> bookedTours = bookedTourRepository.findBookedTourByCustomer(customerId);
        return bookedTours.stream()
                .map(entityDtoConverter::convertToBookTourRespDTO)
                .collect(Collectors.toList());
    }

    public BookedTourRespDTO createBookedTour(BookedTourDTO BookedTourDTO) {
        Tour tour = tourService.getTourById(BookedTourDTO.getTourId());
        Customer customer = customerService.getCustomerById(BookedTourDTO.getCustomerId());

        if (tour.getAvailableSeats() <= tour.getRegisteredSeats()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Out of available seats!");
        }

        BookedTour bookedTour = modelMapper.map(BookedTourDTO, BookedTour.class);
        bookedTour.setTour(tour);
        bookedTour.setCustomer(customer);

        tour.setRegisteredSeats(tour.getRegisteredSeats() + 1);
        tourService.updateTour(tour.getId(), modelMapper.map(tour, TourDTO.class));

        return entityDtoConverter.convertToBookTourRespDTO(bookedTourRepository.save(bookedTour));
    }

    public BookedTourRespDTO updateBookedTour(Long id, BookedTourDTO bookedTourDTO) {
        BookedTour bookedTour = getBookedTourById(id);
        Tour tour = tourService.getTourById(bookedTourDTO.getTourId());
        Customer customer = customerService.getCustomerById(bookedTourDTO.getCustomerId());

        modelMapper.map(bookedTourDTO, bookedTour);
        bookedTour.setTour(tour);
        bookedTour.setCustomer(customer);

        return entityDtoConverter.convertToBookTourRespDTO(bookedTourRepository.save(bookedTour));
    }

    public void deleteBookedTour(Long id) {
        getBookedTourById(id);
        bookedTourRepository.deleteById(id);
    }

}
