package com.tourmanagement.Services;

import com.tourmanagement.DTOs.Payload.FilterDiscount;
import com.tourmanagement.DTOs.Payload.FilterTourGuide;
import com.tourmanagement.DTOs.Request.TourGuideDTO;
import com.tourmanagement.DTOs.Response.DiscountRespDTO;
import com.tourmanagement.DTOs.Response.PaginationRespDTO;
import com.tourmanagement.DTOs.Response.TourGuideRespDTO;
import com.tourmanagement.DTOs.Response.TourRespDTO;
import com.tourmanagement.Models.Discount;
import com.tourmanagement.Models.Tour;
import com.tourmanagement.Models.TourGuide;
import com.tourmanagement.Repositorys.TourGuideRepository;

import com.tourmanagement.Shared.Utils.EntityDtoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourGuideService {
    private final TourGuideRepository tourGuideRepository;
    private final ModelMapper modelMapper;
    private final EntityDtoConverter entityDtoConverter;

    @Autowired
    public TourGuideService(TourGuideRepository tourGuideRepository, ModelMapper modelMapper, EntityDtoConverter entityDtoConverter) {
        this.tourGuideRepository = tourGuideRepository;
        this.modelMapper = modelMapper;
        this.entityDtoConverter = entityDtoConverter;
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

    public PaginationRespDTO<TourGuideRespDTO> getAllTourGuidePagination(FilterTourGuide filterTourGuide) {
        PaginationRespDTO<TourGuideRespDTO> result = new PaginationRespDTO<TourGuideRespDTO>();
        result.setPage(filterTourGuide.getPage());
        result.setTotal((long) getTourGuides().size());
        result.setItemsPerPage(filterTourGuide.getItemsPerPage());

        Pageable pageable = PageRequest.of(filterTourGuide.getPage(), filterTourGuide.getItemsPerPage());
        Page<TourGuide> tourGuideRespDTOS = tourGuideRepository.findAll(pageable);

        result.setData(tourGuideRespDTOS.stream()
                .map(entityDtoConverter::convertToTourGuideRespDTO)
                .collect(Collectors.toList()));

        return result;
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

    public void deleteTourGuide(Long id) {
        getTourGuideById(id);
        tourGuideRepository.deleteById(id);
    }

}
