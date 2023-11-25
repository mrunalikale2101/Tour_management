package com.tourmanagement.Services;


import com.tourmanagement.DTOs.Payload.FilterBookedTour;
import com.tourmanagement.DTOs.Payload.FilterRevenue;
import com.tourmanagement.DTOs.Request.BookedTourDTO;
import com.tourmanagement.DTOs.Request.UpdateStatusBookedTourDTO;
import com.tourmanagement.DTOs.Response.BookedTourRespDTO;
import com.tourmanagement.DTOs.Response.PaginationRespDTO;
import com.tourmanagement.DTOs.Response.RevenueRespDTO;
import com.tourmanagement.DTOs.Response.TopProvinceRespDTO;
import com.tourmanagement.Dao.BookedTourDao;
import com.tourmanagement.Models.BookedTour;
import com.tourmanagement.Models.Customer;
import com.tourmanagement.Models.Tour;
import com.tourmanagement.Repositorys.BookedTourRepository;
import com.tourmanagement.Shared.Types.EnumStatusBookedTour;
import com.tourmanagement.Shared.Utils.EntityDtoConverter;
import com.tourmanagement.Shared.Utils.Helper;
import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
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
    private final MailService mailService;

    public BookedTourService(BookedTourRepository bookedTourRepository,
                             ModelMapper modelMapper,
                             TourService tourService,
                             CustomerService customerService,
                             EntityDtoConverter entityDtoConverter,
                             BookedTourDao bookedTourDao,
                             MailService mailService) {
        this.bookedTourRepository = bookedTourRepository;
        this.modelMapper = modelMapper;
        this.tourService = tourService;
        this.customerService = customerService;
        this.entityDtoConverter = entityDtoConverter;
        this.bookedTourDao = bookedTourDao;
        this.mailService = mailService;
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
        return bookedTourRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "BookedTour with id [%s] is not found".formatted(id)));
    }

    public BookedTourRespDTO updateStatus(Long id, UpdateStatusBookedTourDTO updateStatusBookedTourDTO) {
        BookedTour bookedTour = getBookedTourById(id);
        bookedTour.setStatus(EnumStatusBookedTour.fromString(updateStatusBookedTourDTO.getStatus()));
        String subject = "";
        String htmlContent = "";

        try {
            switch (EnumStatusBookedTour.fromString(updateStatusBookedTourDTO.getStatus())) {
                case CONFIRMED -> {
                    subject = "NOTICE! CONFIRMED YOUR BOOKING TOUR";
                    htmlContent =
                            "<h2>Dear Mr/Mrs %s,</h2>" +
                                    "<div>We are very happy to serve you. We are writing this letter to inform you about the tour <strong>" +
                                    "%s</strong> you booked on %s that was confirmed.</div>" +
                                    "<div>You should access our application to check your information and information of the tour.</div>" +
                                    "<div>If you have any problem, please contact us by phone 0123456789 or email contract@2ht-company.com</div>" +
                                    "<div>Thanks for your choice</div>";
                    htmlContent = String.format(
                            htmlContent,
                            bookedTour.getCustomer().getName(),
                            bookedTour.getTour().getName(),
                            bookedTour.getBookingDate()
                    );
                    mailService.sendMailResponseUser(bookedTour.getCustomer().getEmail(), subject, htmlContent);
                }
                case REJECTED -> {
                    subject = "NOTICE! REJECT YOUR BOOKING TOUR";
                    htmlContent =
                            "<h2>Dear Mr/Mrs %s,</h2>" +
                                    "<div>We are very happy to serve you. We are writing this letter to inform you about the tour <strong>" +
                                    "%s</strong> you booked on %s that was rejected because of the some reasons.</div>" +
                                    "<div>Thanks for your choice</div>";
                    htmlContent = String.format(
                            htmlContent,
                            bookedTour.getCustomer().getName(),
                            bookedTour.getTour().getName(),
                            bookedTour.getBookingDate()
                    );
                    mailService.sendMailResponseUser(bookedTour.getCustomer().getEmail(), subject, htmlContent);
                    Tour tour = bookedTour.getTour();
                    tour.setRegisteredSeats(tour.getRegisteredSeats() - 1);
                    tourService.saveTour(tour);
                }
                default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot handle!");
            }
        } catch (MessagingException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot send mail!");
        }

        bookedTour = bookedTourRepository.save(bookedTour);
        return entityDtoConverter.convertToBookTourRespDTO(bookedTour);
    }

    public BookedTourRespDTO getBookedTourResponseById(Long id) {
        BookedTour bookedTour = getBookedTourById(id);
        return entityDtoConverter.convertToBookTourRespDTO(bookedTour);
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
        //tourService.updateTour(tour);

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

    public List<RevenueRespDTO> getRevenuesFromDateToDate(FilterRevenue filterRevenue) {
        var nearestDates = Helper.getDateList(filterRevenue.getStartDate(), filterRevenue.getEndDate());

        return bookedTourDao.revenues(nearestDates);
    }

    public List<TopProvinceRespDTO> getTopTheMostAmazingProvinces() {
        return bookedTourDao.theMostAmazingProvinces();
    }

    public Long countBookedTourByDate(Date date) {
        return bookedTourDao.countBookedTourByDate(date);
    }

    public void handleSendRequestToUser(Long id) {
        BookedTour bookedTour = getBookedTourById(id);
        bookedTour = calculateTotalMoney(bookedTour);

        String subject = "[IMPORTANT] PAYMENT YOUR BOOKED TOUR";
        String htmlContent =
                "<h2>Dear Mr/Mrs %s,</h2>" +
                        "<div>We are very happy to serve you. We are writing this letter to inform that you have a tour booked on %s [%s] it still do not pay.</div>" +
                        "<div>Please pay us a fee of about %s for us to confirm this tour for you. After payment, please access our application to confirm your payment</div>" +
                        "<div>Thanks for your choice</div>";
        htmlContent = String.format(
                htmlContent,
                bookedTour.getBookingDate(),
                bookedTour.getTour().getName(),
                bookedTour.getTotalMoney()
        );
        try {
            mailService.sendMailResponseUser(bookedTour.getCustomer().getEmail(), subject, htmlContent);
        } catch (MessagingException mex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Send mail failure");
        }
    }

    public BookedTour calculateTotalMoney(BookedTour bookedTour) {
        Double totalMoney = bookedTour.getTour().getPrice();

        if (bookedTour.getDiscount() != null) {
            totalMoney = ((100 - bookedTour.getDiscount().getDiscountPercentage()) / 100) * bookedTour.getTour().getPrice();
        }

        bookedTour.setTotalMoney(totalMoney);
        return bookedTour;
    }
}
