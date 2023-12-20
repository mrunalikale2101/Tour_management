package com.tourmanagement.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tourmanagement.DTOs.Response.TourRespDTO;
import com.tourmanagement.Shared.Types.EnumStatusBookedTour;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Data
@Table(name = "booked_tours")
public class BookedTour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @Column(
            name = "booking_date"
    )
    private Date bookingDate = new Date();

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EnumStatusBookedTour status = EnumStatusBookedTour.PENDING;

    @Column(nullable = true)
    private String note;

    @Column(name = "is_paid", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isPaid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @Column(name = "total_money", columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double totalMoney;
}
