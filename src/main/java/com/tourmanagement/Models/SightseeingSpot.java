package com.tourmanagement.Models;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "sightseeing_spots")
public class SightseeingSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;
}
