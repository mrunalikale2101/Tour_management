package com.tourmanagement.DTOs.Response;

import lombok.Data;

@Data
public class QuantityStatisticResp {
    private Long quantityTour;
    private Long quantityCustomer;
    private Long quantityBookedTour;
    private Long quantityBookedTourToday;
}
