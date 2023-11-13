package com.tourmanagement.Models;

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
    private EnumStatusBookedTour status = EnumStatusBookedTour.PENDING;

    @Column(nullable = true)
    private String note;
}
