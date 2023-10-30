package com.tourmanagement.Repositorys;

import com.tourmanagement.Models.CustomerDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerDiscountRepository extends JpaRepository<CustomerDiscount, Long> {

    @Query("SELECT d FROM CustomerDiscount d WHERE d.tour.id = :tourId and d.customer.id = :customerId")
    List<CustomerDiscount> findAllCustomerDiscountByTourAndCustomer(Long tourId, Long customerId);

    @Query("SELECT d FROM CustomerDiscount d WHERE d.tour.id = :tourId")
    List<CustomerDiscount> findAllCustomerDiscountByTour(Long tourId);

}
