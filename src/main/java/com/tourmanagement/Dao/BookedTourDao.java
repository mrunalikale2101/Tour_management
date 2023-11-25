package com.tourmanagement.Dao;

import com.tourmanagement.DTOs.Payload.FilterBookedTour;
import com.tourmanagement.DTOs.Response.RevenueRespDTO;
import com.tourmanagement.DTOs.Response.TopProvinceRespDTO;
import com.tourmanagement.Models.BookedTour;

import java.util.Date;
import java.util.List;

public interface BookedTourDao {
    public List<BookedTour> filter(FilterBookedTour filter);
    public Long filterCount(FilterBookedTour filter);
    public List<RevenueRespDTO> revenues(List<Date> dates);
    public List<TopProvinceRespDTO> theMostAmazingProvinces();
    public Long countBookedTourByDate(Date date);
}
