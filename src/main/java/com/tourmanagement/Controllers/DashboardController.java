package com.tourmanagement.Controllers;

import com.tourmanagement.DTOs.Response.*;
import com.tourmanagement.Services.BookedTourService;
import com.tourmanagement.Services.DashboardService;
import com.tourmanagement.Services.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("dash-board")
@ResponseStatus(HttpStatus.OK)
public class DashboardController {
    private final DashboardService dashboardService;
    private final TourService tourService;
    private final BookedTourService bookedTourService;

    @Autowired
    public DashboardController(DashboardService dashboardService, TourService tourService, BookedTourService bookedTourService) {
        this.dashboardService = dashboardService;
        this.tourService = tourService;
        this.bookedTourService= bookedTourService;
    }

    @GetMapping("quantity-statistic")
    public QuantityStatisticResp handleGetQuantityStatistic() {
        return dashboardService.getQuantityStatistics();
    }

    @GetMapping("today-tour")
    public List<TourRespDTO> handeGetTodayTour() {
        return tourService.getTodayTour();
    }

    @GetMapping("revenues")
    public List<RevenueRespDTO> handleGetRevenuresPerWeek() {
        return  bookedTourService.getRevenuesSevenNearestDate();
    }

    @GetMapping("top-amazing-province")
    public List<TopProvinceRespDTO> handleTopAmazingProvince() {
        return bookedTourService.getTopTheMostAmazingProvinces();
    }
}
