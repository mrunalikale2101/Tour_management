package com.tourmanagement.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Data
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

    private Date bookingDate;
    @Column(columnDefinition="TEXT")
    private String status;
    @Column(columnDefinition="TEXT")
    private String otherRequests;
}
