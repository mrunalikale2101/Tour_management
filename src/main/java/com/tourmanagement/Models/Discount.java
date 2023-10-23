package com.tourmanagement.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Data
@Table(name = "discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(columnDefinition = "DOUBLE PRECISION", name = "discount_percentage", nullable = false)
    private Double discountPercentage;

    @ManyToOne()
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @Column(
            nullable = false,
            name = "start_date"
    )
    private Date startDate;

    @Column(
            nullable = false,
            name = "end_date"
    )
    private Date endDate;
}
