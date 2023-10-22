package com.tourmanagement.Models;

import com.tourmanagement.Shared.Types.EnumStatusBookedTour;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Data
@Table(name="booked_tours")
public class BookedTour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @Column(
            nullable = true,
            name = "booking_date"
    )
    private Date bookingDate;

    @Enumerated(EnumType.STRING)
    private EnumStatusBookedTour status;

    @Column(nullable = true)
    private String note;
}
