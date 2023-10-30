package com.tourmanagement.Controllers;

import com.tourmanagement.DTOs.Response.QuantityStatisticResp;
import com.tourmanagement.Services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dash-board")
@ResponseStatus(HttpStatus.OK)
public class DashboardController {
    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("quantity-statistic")
    public QuantityStatisticResp handleGetQuantityStatistic() {
        return dashboardService.getQuantityStatistics();
    }
}
