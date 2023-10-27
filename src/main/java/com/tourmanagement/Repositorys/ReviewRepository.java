package com.tourmanagement.Repositorys;

import com.tourmanagement.DTOs.ReviewDTO;
import com.tourmanagement.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
