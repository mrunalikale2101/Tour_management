package com.tourmanagement.Models;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @Column(columnDefinition="TEXT")
    private String comment;

    @Column(columnDefinition = "DOUBLE PRECISION DEFAULT 0.0", nullable = false)
    private Double rating;
}
