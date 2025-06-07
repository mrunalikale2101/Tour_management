package com.tour.management.repository;

import com.tour.management.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long>, QuerydslPredicateExecutor<Tour> {
} 