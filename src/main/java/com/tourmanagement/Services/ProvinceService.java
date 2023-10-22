package com.tourmanagement.Services;

import com.tourmanagement.DTOs.SightseeingSpotDTO;
import com.tourmanagement.Models.Province;
import com.tourmanagement.Models.SightseeingSpot;
import com.tourmanagement.Repositorys.ProvinceRepository;
import com.tourmanagement.Repositorys.SightseeingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProvinceService {
    private final ProvinceRepository provinceRepository;
    private final SightseeingSpotService sightseeingSpotService;

    @Autowired()
    public ProvinceService(ProvinceRepository provinceRepository, SightseeingSpotService sightseeingSpotService) {
        this.provinceRepository = provinceRepository;
        this.sightseeingSpotService = sightseeingSpotService;
    }

    public List<Province> getProvinces() {
        List<Province> provinces = provinceRepository.findAll();

        return provinces;
    }

    public Province getProvinceById(Long id) {
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Province with id [%s] is not found!".formatted(id)));

        return province;
    }

    public List<SightseeingSpot> getSightSeeingSpotOfProvince(Long id) {
        getProvinceById(id);

        List<SightseeingSpot> sightseeingSpots = sightseeingSpotService.getSightseeingSpotsByProvinceId(id);

        return sightseeingSpots;
    }

    public SightseeingSpot addNewSightseeingSpot(Long provinceId, SightseeingSpotDTO sightseeingSpotDTO) {
        Province province = getProvinceById(provinceId);

        SightseeingSpot newSightseeingSpot = new SightseeingSpot();
        newSightseeingSpot.setProvince(province);
        newSightseeingSpot.setName(sightseeingSpotDTO.getName());
        newSightseeingSpot.setAddress(sightseeingSpotDTO.getAddress());

        return sightseeingSpotService.createNewSightseeingSpot(newSightseeingSpot);
    }
}
