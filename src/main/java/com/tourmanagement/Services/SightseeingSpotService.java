package com.tourmanagement.Services;

import com.tourmanagement.DTOs.Payload.PaginationRequest;
import com.tourmanagement.DTOs.Request.SightseeingSpotDTO;
import com.tourmanagement.DTOs.Response.PaginationRespDTO;
import com.tourmanagement.DTOs.Response.SightseeingSpotRespDTO;
import com.tourmanagement.DTOs.Response.TourRespDTO;
import com.tourmanagement.Models.SightseeingSpot;
import com.tourmanagement.Models.Tour;
import com.tourmanagement.Repositorys.SightseeingSpotRepository;
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
public class SightseeingSpotService {
    private final SightseeingSpotRepository sightseeingSpotRepository;

    @Autowired
    public SightseeingSpotService(SightseeingSpotRepository sightseeingSpotRepository) {
        this.sightseeingSpotRepository = sightseeingSpotRepository;
    }

    public SightseeingSpot getSightSeeingSpotById(Long id) {
        SightseeingSpot sightseeingSpot = sightseeingSpotRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sightseeing Spot with id [%s] is not found".formatted(id)));

        return sightseeingSpot;
    }

    public PaginationRespDTO<SightseeingSpotRespDTO> getSightseeingSpots(PaginationRequest pagination) {

        PaginationRespDTO<SightseeingSpotRespDTO> result = new PaginationRespDTO<SightseeingSpotRespDTO>();
        result.setTotal(sightseeingSpotRepository.count());
        result.setPage(pagination.getPage());
        result.setItemsPerPage(pagination.getItemsPerPage());
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getItemsPerPage());

        Page<SightseeingSpot> SightseeingSpotPage = sightseeingSpotRepository.findAll(pageable);
        result.setData(
                SightseeingSpotPage.getContent().stream()
                        .map(SightseeingSpotRespDTO::convert)
                        .collect(Collectors.toList()));

        return result;
    }

    public List<SightseeingSpot> getSightseeingSpotsByProvinceId(Long provinceId) {
        List<SightseeingSpot> sightseeingSpots = sightseeingSpotRepository.findByProvinceId(provinceId);

        return sightseeingSpots;
    }

    public SightseeingSpot createNewSightseeingSpot(SightseeingSpot newSightseeingSpot) {
        return sightseeingSpotRepository.save(newSightseeingSpot);
    }

    public SightseeingSpot updateSightseeingSpot(Long id, SightseeingSpotDTO sightseeingSpotDTO) {
        SightseeingSpot sightseeingSpotNeedUpdate = getSightSeeingSpotById(id);
        sightseeingSpotNeedUpdate.setAddress(sightseeingSpotDTO.getAddress());
        sightseeingSpotNeedUpdate.setName(sightseeingSpotDTO.getName());

        return sightseeingSpotRepository.save(sightseeingSpotNeedUpdate);
    }

    public void deleteSightseeingSpot(Long id) {
        getSightSeeingSpotById(id);
        sightseeingSpotRepository.deleteById(id);
    }
}
