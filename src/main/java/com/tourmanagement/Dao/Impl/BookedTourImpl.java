package com.tourmanagement.Dao.Impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.tourmanagement.DTOs.Payload.FilterBookedTour;
import com.tourmanagement.Dao.BookedTourDao;
import com.tourmanagement.Models.BookedTour;
import com.tourmanagement.Models.QBookedTour;
import com.tourmanagement.Shared.Types.EnumStatusBookedTour;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookedTourImpl extends BasicDao implements BookedTourDao {
    @Override
    public List<BookedTour> filter(FilterBookedTour filter) {
        JPAQuery<BookedTour> query = new JPAQuery<>(_entityManager);
        QBookedTour bookedTour = QBookedTour.bookedTour;

        query = query.from(bookedTour);

        if(filter.getStartDate() != null && filter.getEndDate() != null) {
            query = query.where(bookedTour.bookingDate.between(filter.getStartDate(), filter.getEndDate()));
        }

        if(filter.getStatus() != null) {
            query = query.where(bookedTour.status.eq(EnumStatusBookedTour.fromString(filter.getStatus())));
        }

        query = query.orderBy(bookedTour.bookingDate.asc()).offset((long) filter.getItemsPerPage() * filter.getPage()).limit(filter.getItemsPerPage());
        return query.fetch();
    }

    @Override
    public Long filterCount(FilterBookedTour filter) {
        JPAQuery<BookedTour> query = new JPAQuery<>(_entityManager);
        QBookedTour bookedTour = QBookedTour.bookedTour;

        query = query.from(bookedTour);

        if(filter.getStartDate() != null && filter.getEndDate() != null) {
            query = query.where(bookedTour.bookingDate.between(filter.getStartDate(), filter.getEndDate()));
        }

        if(filter.getStatus() != null) {
            query = query.where(bookedTour.status.eq(EnumStatusBookedTour.fromString(filter.getStatus())));
        }

        return query.fetchCount();
    }
}
