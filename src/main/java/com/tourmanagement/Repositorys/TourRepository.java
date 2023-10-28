package com.tourmanagement.Repositorys;

import com.tourmanagement.Models.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {

    List<Tour> findByPriceBetween(Double minPrice, Double maxPrice);

    @Query("SELECT t FROM Tour t WHERE (:name IS NULL OR t.name LIKE %:name%) " +
            "AND (:sightseeing IS NULL OR t.sightseeingSpot.name LIKE %:sightseeing%) " +
            "AND (:province IS NULL OR t.sightseeingSpot.province.name LIKE %:province%) " +
            "AND (:date IS NULL OR t.departureDate = :date)")
    List<Tour> searchTour(
            @Param("name") String name,
            @Param("sightseeing") String sightseeing,
            @Param("province") String province,
            @Param("date") Date date);
}
