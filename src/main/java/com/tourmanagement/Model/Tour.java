package com.tourmanagement.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;


@Entity
@Data
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition="TEXT")
    private String name;
    private Date departureDate;
    private Integer duration;
    @Column(columnDefinition="TEXT")
    private String departureLocation;
    @Column(columnDefinition="TEXT")
    private String destination;
    private Integer availableSeats;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private SightseeingSpot sightseeingSpot;

    @ManyToOne
    @JoinColumn(name = "guide_id")
    private TourGuide guide;

    @Column(columnDefinition="TEXT")
    private String gatheringPoint;

    @Column(columnDefinition="TEXT")
    private String transportationMode;

    @Column(columnDefinition = "json")
    private String images;
    private Integer likes;
    private Integer views;
    private Float price;

    @ManyToMany
    @JoinTable(name = "customer_discount_tour",
            joinColumns = @JoinColumn(name = "tour_id"),
            inverseJoinColumns = @JoinColumn(name = "discount_code"))
    private Set<Discount> discounts;
}
