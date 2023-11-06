package com.tourmanagement.Repositorys;

import com.tourmanagement.Models.TourGuide;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourGuideRepository extends JpaRepository<TourGuide, Long> {
}
