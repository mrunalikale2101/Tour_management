package com.tourmanagement.Dao.Impl;

import com.tourmanagement.Models.Tour;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class TourRepositoryCustomImpl implements TourRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tour> findToursBySightseeingId(Long sightseeingId) {
        String nativeQuery = "SELECT * FROM tours t, JSON_TABLE(t.sightseeing_id_list, '$[*]' COLUMNS (sightseeingId INT PATH '$')) AS jt WHERE jt.sightseeingId = :sightseeingId";
        Query query = entityManager.createNativeQuery(nativeQuery, Tour.class);
        query.setParameter("sightseeingId", sightseeingId);
        return query.getResultList();
    }
}