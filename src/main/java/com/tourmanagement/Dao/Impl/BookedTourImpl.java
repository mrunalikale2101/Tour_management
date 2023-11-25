package com.tourmanagement.Dao.Impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.tourmanagement.DTOs.Payload.FilterBookedTour;
import com.tourmanagement.DTOs.Response.RevenueRespDTO;
import com.tourmanagement.DTOs.Response.TopProvinceRespDTO;
import com.tourmanagement.Dao.BookedTourDao;
import com.tourmanagement.Models.BookedTour;
import com.tourmanagement.Models.QBookedTour;
import com.tourmanagement.Models.QTour;
import com.tourmanagement.Shared.Types.EnumStatusBookedTour;
import org.springframework.stereotype.Repository;
import java.util.Calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class BookedTourImpl extends BasicDao implements BookedTourDao {
    @Override
    public List<BookedTour> filter(FilterBookedTour filter) {
        JPAQuery<BookedTour> query = new JPAQuery<>(_entityManager);
        QBookedTour bookedTour = QBookedTour.bookedTour;

        query = query.from(bookedTour);

        if (filter.getStartDate() != null && filter.getEndDate() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(filter.getEndDate());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date endDate = calendar.getTime();

            query = query.where(bookedTour.bookingDate.between(filter.getStartDate(), endDate));
        }

        if (filter.getStatus() != null) {
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

        if (filter.getStartDate() != null && filter.getEndDate() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(filter.getEndDate());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date endDate = calendar.getTime();

            query = query.where(bookedTour.bookingDate.between(filter.getStartDate(), endDate));
        }

        if (filter.getStatus() != null) {
            query = query.where(bookedTour.status.eq(EnumStatusBookedTour.fromString(filter.getStatus())));
        }

        return query.fetchCount();
    }

    @Override
    public List<RevenueRespDTO> revenues(List<Date> dates) {
        List<RevenueRespDTO> result = new ArrayList<>();
        JPAQuery<BookedTour> query = new JPAQuery<>(_entityManager);
        QBookedTour bookedTour = QBookedTour.bookedTour;
        QTour tour = QTour.tour;

        query = query.from(bookedTour).leftJoin(bookedTour.tour, tour);

        for (Date date : dates) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date nextDate = calendar.getTime();

            List<BookedTour> matchedBookedTours = query
                    .where(bookedTour.bookingDate.between(date, nextDate)
                            .and(bookedTour.isPaid.eq(true))
                            .and(bookedTour.status.eq(EnumStatusBookedTour.CONFIRMED)))
                    .fetch();

            Double totalMoney = 0.0;
            for (BookedTour _bookedTour : matchedBookedTours) {
                totalMoney += _bookedTour.getTotalMoney();
            }

            result.add(new RevenueRespDTO(totalMoney, date));
        }

        return result;
    }

    @Override
    public List<TopProvinceRespDTO> theMostAmazingProvinces() {
        JPAQuery<BookedTour> query = new JPAQuery<>(_entityManager);
        QBookedTour bookedTour = QBookedTour.bookedTour;

        List<Tuple> dataResp = query.select(bookedTour.tour.destinationLocation, bookedTour.tour.destinationLocation.count())
                .from(bookedTour)
                .groupBy(bookedTour.tour.destinationLocation)
                .fetch();

        return dataResp.stream()
                .map(tuple -> {
                    String province = tuple.get(bookedTour.tour.destinationLocation);
                    Long tourCount = tuple.get(bookedTour.tour.destinationLocation.count());
                    assert province != null;
                    return new TopProvinceRespDTO(province, tourCount);
                })
                .toList();
    }

    @Override
    public Long countBookedTourByDate(Date date) {
        JPAQuery<BookedTour> query = new JPAQuery<>(_entityManager);
        QBookedTour bookedTour = QBookedTour.bookedTour;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date previousDay = calendar.getTime();

        query = query.from(bookedTour).where(bookedTour.bookingDate.between(previousDay, date));

        return query.fetchCount();
    }
}
