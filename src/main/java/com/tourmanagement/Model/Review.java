package com.tourmanagement.Model;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @Column(columnDefinition="TEXT")
    private String comment;
    private Integer rating;
}
