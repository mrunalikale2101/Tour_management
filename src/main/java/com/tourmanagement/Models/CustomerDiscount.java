package com.tourmanagement.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Data
@Table(name = "customer_discounts")
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

    @Column(
            nullable = false,
            name = "usage_date"
    )
    private Date usageDate;
}
