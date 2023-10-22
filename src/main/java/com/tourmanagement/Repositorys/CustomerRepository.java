package com.tourmanagement.Repositorys;

import com.tourmanagement.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
