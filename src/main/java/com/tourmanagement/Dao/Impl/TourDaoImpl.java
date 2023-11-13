package com.tourmanagement.Dao.Impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.tourmanagement.DTOs.Request.SearchTourDTO;
import com.tourmanagement.Dao.TourDao;
import com.tourmanagement.Models.QTour;
import com.tourmanagement.Models.Tour;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TourDaoImpl extends BasicDao implements TourDao {
    @Override
    public List<Tour> searchTours(SearchTourDTO searchTourDTO) {
        JPAQuery<Tour> query = new JPAQuery<>(_entityManager);
        QTour tour = QTour.tour;

        query = query.from(tour);

        if (searchTourDTO.getDate() != null) {
            query = query.where(tour.departureDate.eq(searchTourDTO.getDate()));
        }

        if (searchTourDTO.getName() != null) {
            query = query.where(tour.name.containsIgnoreCase(searchTourDTO.getName()));
        }

        return query.fetch();
    }
}
