package com.tourmanagement.Repositorys;

import com.tourmanagement.Models.Discount;
import com.tourmanagement.Shared.Types.EnumStatusDiscount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

    @Query("SELECT d FROM Discount d WHERE d.tour.id = :tourId")
    List<Discount> findAllDiscountByTour(Long tourId);


    @Query("SELECT count(*) FROM Discount d WHERE " +
            "(:startDate is null or d.startDate >= :startDate) and " +
            "(:endDate is null or d.endDate <= :endDate) and " +
            "(:status is null or d.status = :status)")
    Long countDiscountByFilterDiscount(
            Date startDate,
            Date endDate,
            EnumStatusDiscount status
    );

    @Query("SELECT d FROM Discount d WHERE " +
            "(:startDate is null or d.startDate >= :startDate) and " +
            "(:endDate is null or d.endDate <= :endDate) and " +
            "(:status is null or d.status = :status)")
    List<Discount> findDiscountsByFilterDiscount(
            Date startDate,
            Date endDate,
            EnumStatusDiscount status, Pageable pageable);

}
