package com.tourmanagement.Repositorys;

import com.tourmanagement.Models.BookedTour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookedTourRepository extends JpaRepository<BookedTour, Long> {

    @Query("SELECT d FROM BookedTour d WHERE d.tour.id = :tourId")
    List<BookedTour> findBookedTourByTour(Long tourId);

    @Query("SELECT d FROM BookedTour d WHERE d.customer.id = :customerId")
    List<BookedTour> findBookedTourByCustomer(Long customerId);
}
