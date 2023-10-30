package com.tourmanagement.Services;

import com.tourmanagement.DTOs.Request.TourGuideDTO;
import com.tourmanagement.Models.TourGuide;
import com.tourmanagement.Repositorys.TourGuideRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TourGuideService {
    private final TourGuideRepository tourGuideRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TourGuideService(TourGuideRepository tourGuideRepository, ModelMapper modelMapper) {
        this.tourGuideRepository = tourGuideRepository;
        this.modelMapper = modelMapper;
    }

    public TourGuide getTourGuideById(Long id) {
        TourGuide  tourGuide = tourGuideRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tour guide with id [%s] not found".formatted(id)));

        return tourGuide;
    }

    public List<TourGuide> getTourGuides() {
        List<TourGuide> tourGuides = tourGuideRepository.findAll();

        return tourGuides;
    }

    public TourGuide createNewTourGuide(TourGuideDTO tourGuideDTO) {
        TourGuide newTourGuide = modelMapper.map(tourGuideDTO, TourGuide.class);


        return tourGuideRepository.save(newTourGuide);
    }

    public TourGuide updateTourGuide(Long id, TourGuideDTO tourGuideDTO) {
        getTourGuideById(id);

        TourGuide tourGuideNeedUpdate = modelMapper.map(tourGuideDTO, TourGuide.class);
        tourGuideNeedUpdate.setId(id);

        return tourGuideRepository.save(tourGuideNeedUpdate);
    }
}
