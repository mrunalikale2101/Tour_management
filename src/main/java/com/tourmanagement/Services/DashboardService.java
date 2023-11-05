package com.tourmanagement.Services;

import com.tourmanagement.DTOs.Response.QuantityStatisticResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private final  TourService tourService;
    private final CustomerService customerService;
    private final BookTourService bookTourService;

    @Autowired
    public DashboardService(TourService tourService, CustomerService customerService, BookTourService bookTourService) {
        this.tourService= tourService;
        this.customerService=customerService;
        this.bookTourService = bookTourService;
    }

    public QuantityStatisticResp getQuantityStatistics() {
        QuantityStatisticResp quantityStatisticResp = new QuantityStatisticResp();
        quantityStatisticResp.setQuantityCustomer(customerService.getCountCustomers());
        quantityStatisticResp.setQuantityTour(tourService.getCountTour());
        quantityStatisticResp.setQuantityBookedTour(bookTourService.getCountBookedTour());
        quantityStatisticResp.setQuantityBookedTourToday(10L);

        return quantityStatisticResp;
    }

}
