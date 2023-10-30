package com.tourmanagement.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(columnDefinition = "DOUBLE PRECISION", name = "discount_percentage", nullable = false)
    private Double discountPercentage;

    @ManyToOne(fetch = FetchType.LAZY)
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
