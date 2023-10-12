package com.tourmanagement.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Data
public class CustomerDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "discount_code")
    private Discount discount;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    private Date usageDate;
}
