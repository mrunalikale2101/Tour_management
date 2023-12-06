package com.tourmanagement.Repositorys;

import com.tourmanagement.Dao.Impl.TourRepositoryCustom;
import com.tourmanagement.Models.Discount;
import com.tourmanagement.Models.Tour;
import com.tourmanagement.Shared.Types.EnumStatusDiscount;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long>, TourRepositoryCustom {

    List<Tour> findByPriceBetween(Double minPrice, Double maxPrice);

    @Query("SELECT t FROM Tour t ORDER BY t.rating DESC")
    List<Tour> findTopRatedTours(Pageable pageable);

    @Query("select t from Tour t where DATE(t.departureDate) = :date")
    List<Tour> findToDayTour(@Param("date") java.sql.Date date);

    @Query("SELECT t FROM Tour t WHERE (:name IS NULL OR t.name LIKE %:name%)")
    List<Tour> searchTourbyName(
            @Param("name") String name);

    @Query("SELECT d FROM Tour d WHERE " +
            "(:departureDate is null or d.endDate >= :departureDate) and " +
            "(:endDate is null or d.endDate <= :endDate) and " +
            "(:maxPrice is null or d.price <= :maxPrice) and " + "(:minPrice is null or d.price >= :minPrice)" )
    List<Tour> findToursByFilterTour(
            Date departureDate,
            Date endDate,
            Double minPrice,
            Double maxPrice,
            Pageable pageable);

    @Query("SELECT count(*) FROM Tour d WHERE " +
            "(:departureDate is null or d.endDate >= :departureDate) and " +
            "(:endDate is null or d.endDate <= :endDate) and " +
            "(:maxPrice is null or d.price <= :maxPrice) and " + "(:minPrice is null or d.price >= :minPrice)" )
    Long countTourByFilterTour(
            Date departureDate,
            Date endDate,
            Double minPrice,
            Double maxPrice
    );
}
