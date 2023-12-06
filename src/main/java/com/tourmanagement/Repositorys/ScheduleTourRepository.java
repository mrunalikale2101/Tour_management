package com.tourmanagement.Repositorys;

import com.tourmanagement.Models.ScheduleTour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleTourRepository extends JpaRepository<ScheduleTour, Long> {
    @Query("SELECT st FROM ScheduleTour st WHERE st.tour.id = :tourId ORDER BY st.day ASC")
    List<ScheduleTour> findSchedulesOfSpecificTour(Long tourId);
}
