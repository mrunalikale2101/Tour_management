package com.tourmanagement.Repositorys;

import com.tourmanagement.Models.Discount;
import com.tourmanagement.Models.TourGuide;
import com.tourmanagement.Shared.Types.EnumStatusDiscount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TourGuideRepository extends JpaRepository<TourGuide, Long> {

}
