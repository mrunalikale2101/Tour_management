package com.tourmanagement.Repositorys;

import com.tourmanagement.Models.SightseeingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SightseeingSpotRepository extends JpaRepository<SightseeingSpot, Long> {
    List<SightseeingSpot> findByProvinceId(Long provinceId);
}
