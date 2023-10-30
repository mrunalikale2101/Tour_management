package com.tourmanagement.Repositorys;

import com.tourmanagement.Models.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

    @Query("SELECT d FROM Discount d WHERE d.tour.id = :tourId")
    List<Discount> findALlDiscountByTour(Long tourId);
}
