package com.tourmanagement.Repositorys;
import com.tourmanagement.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.tour.id = :tourId")
    List<Review> findByTourId(Long tourId);

    @Query("SELECT r FROM Review r WHERE r.customer.id = :customerId")
    List<Review> findByCustomerId(Long customerId);
}
