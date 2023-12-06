package com.tourmanagement.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "schedule_tours")
public class ScheduleTour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "tour_id")
    private Tour tour;
    @Column(nullable = false)
    private Integer day;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String title;
}
