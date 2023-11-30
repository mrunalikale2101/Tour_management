package com.tourmanagement.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tourmanagement.Shared.Types.EnumStatusTour;
import com.tourmanagement.Shared.Types.EnumTransportModeTour;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


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
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date departureDate;

    @Column(nullable = true, name = "end_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date endDate;

    @Column(nullable = false)
    private Integer duration;

    @Column(columnDefinition = "TEXT")
    private String departureLocation;

    @Column(columnDefinition = "TEXT", name = "destination_location")
    private String destinationLocation;

    @Column(name = "registered_seats", nullable = false)
    private Integer registeredSeats = 0;

    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    @Column(columnDefinition = "json", name = "sightseeing_id_list")
    private String idSightSeeing;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guide_id")
    private TourGuide guide;

    @Column(nullable = false, name = "gathering_address")
    private String gatheringAddress;

    @Enumerated(EnumType.STRING)
    private EnumTransportModeTour transportationMode = EnumTransportModeTour.BUS;

    @Column(columnDefinition = "json")
    private String images;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer likes = 0;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer views = 0;

    @Column(columnDefinition = "DOUBLE PRECISION", nullable = false)
    private Double price;

    @Column(columnDefinition = "DOUBLE PRECISION DEFAULT 0.0")
    private Double rating = 0.0;

    @Enumerated(EnumType.STRING)
    private EnumStatusTour status;
}
