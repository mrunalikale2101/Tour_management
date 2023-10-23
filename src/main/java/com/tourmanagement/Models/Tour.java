package com.tourmanagement.Models;

import com.tourmanagement.Shared.Types.EnumTransportModeTour;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;


@Entity
@Data
@Table(name = "tours")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "departure_date")
    private Date departureDate;

    @Column(nullable = false)
    private Integer duration;

    @Column(columnDefinition = "TEXT")
    private String departureLocation;

    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id")
    private SightseeingSpot sightseeingSpot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id")
    private TourGuide guide;

    @Column(nullable = false, name = "gathering_address")
    private String gatheringAddress;

    @Enumerated(EnumType.STRING)
    private EnumTransportModeTour transportationMode;

    @Column(columnDefinition = "json")
    private String images;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer likes;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer views;

    @Column(columnDefinition = "DOUBLE PRECISION", nullable = false)
    private Double price;

    @Column(columnDefinition = "DOUBLE PRECISION DEFAULT 0.0", nullable = false)
    private Double rating;
}
