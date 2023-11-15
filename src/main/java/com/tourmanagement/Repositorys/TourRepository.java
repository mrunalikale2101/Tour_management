package com.tourmanagement.Repositorys;

import com.tourmanagement.Models.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {

    List<Tour> findByPriceBetween(Double minPrice, Double maxPrice);

    @Query("SELECT t FROM Tour t WHERE (:name IS NULL OR t.name LIKE %:name%) " +
            "AND (:sightseeing IS NULL OR t.sightseeingSpot.name LIKE %:sightseeing%) " +
            "AND (:province IS NULL OR t.sightseeingSpot.province.name LIKE %:province%) " +
            "AND (:date IS NULL OR DATE(t.departureDate) = DATE(:date))")
    List<Tour> searchTour(
            @Param("name") String name,
            @Param("sightseeing") String sightseeing,
            @Param("province") String province,
            @Param("date") Date date);

    @Query("SELECT t FROM Tour t ORDER BY t.rating DESC")
    List<Tour> findTopRatedTours(Pageable pageable);

    @Query("select t from Tour t where DATE(t.departureDate) = :date")
    List<Tour> findToDayTour(@Param("date") java.sql.Date date);

}
