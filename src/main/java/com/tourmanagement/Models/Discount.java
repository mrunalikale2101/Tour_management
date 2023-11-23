package com.tourmanagement.Models;

import com.tourmanagement.Shared.Types.EnumStatusDiscount;
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

    @Column(nullable = false)
    private Integer quantityDiscounts;

    @Column(nullable = false)
    private Integer quantityUsedDiscounts =  0;

    @Enumerated(EnumType.STRING)
    private EnumStatusDiscount status = EnumStatusDiscount.ACTIVE;
    @PrePersist
    @PreUpdate
    private void updateStatus() {
        Date currentDate = new Date();
        if (currentDate.before(startDate) || currentDate.after(endDate)) {
            this.status = EnumStatusDiscount.EXPIRED;
        }

        // Check if the quantity is zero
        if (quantityDiscounts != null && quantityDiscounts.equals(quantityUsedDiscounts)) {
            this.status = EnumStatusDiscount.EXPIRED;
        }

    }

}
