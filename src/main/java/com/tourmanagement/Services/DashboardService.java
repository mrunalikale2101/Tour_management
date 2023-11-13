package com.tourmanagement.Services;

import com.tourmanagement.DTOs.Response.QuantityStatisticResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private final  TourService tourService;
    private final CustomerService customerService;
    private final BookedTourService bookedTourService;

    @Autowired
    public DashboardService(TourService tourService, CustomerService customerService, BookedTourService bookedTourService) {
        this.tourService= tourService;
        this.customerService=customerService;
        this.bookedTourService = bookedTourService;
    }

    public QuantityStatisticResp getQuantityStatistics() {
        QuantityStatisticResp quantityStatisticResp = new QuantityStatisticResp();
        quantityStatisticResp.setQuantityCustomer(customerService.getCountCustomers());
        quantityStatisticResp.setQuantityTour(tourService.getCountTour());
        quantityStatisticResp.setQuantityBookedTour(bookedTourService.getCountBookedTour());
        quantityStatisticResp.setQuantityBookedTourToday(10L);

        return quantityStatisticResp;
    }

}
