package com.tourmanagement.Repositorys;

import com.tourmanagement.Models.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
