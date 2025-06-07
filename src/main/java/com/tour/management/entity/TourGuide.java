package com.tour.management.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "tour_guides")
public class TourGuide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(length = 1000)
    private String bio;

    @Column(nullable = false)
    private String languages; // Comma-separated list of languages

    @Column(nullable = false)
    private Integer yearsOfExperience;

    @OneToMany(mappedBy = "tourGuide", cascade = CascadeType.ALL)
    private List<Tour> tours = new ArrayList<>();

    @Column(nullable = false)
    private boolean isAvailable = true;

    @Column
    private Double rating; // Average rating from reviews
} 