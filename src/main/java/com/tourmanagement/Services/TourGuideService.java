package com.tourmanagement.Services;

import com.tourmanagement.Repositorys.TourGuideRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TourGuideService {
    private final TourGuideRepository tourGuideRepository;

    @Autowired()
    public TourGuideService(TourGuideRepository tourGuideRepository) {
        this.tourGuideRepository = tourGuideRepository;
    }
}
