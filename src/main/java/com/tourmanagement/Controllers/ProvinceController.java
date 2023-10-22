package com.tourmanagement.Controllers;

import com.tourmanagement.DTOs.SightseeingSpotDTO;
import com.tourmanagement.Models.Province;
import com.tourmanagement.Models.SightseeingSpot;
import com.tourmanagement.Services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("provinces")
@ResponseStatus(HttpStatus.OK)
public class ProvinceController {
    private final ProvinceService provinceService;

    @Autowired()
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping()
    public List<Province> handleGetProvinces() {
        List<Province> provinces = provinceService.getProvinces();

        return provinces;
    }

    @GetMapping("/{id}/sightseeing-spots")
    public List<SightseeingSpot> handleGetSightseeingSpotByProvinceId(@PathVariable Long id) {
        List<SightseeingSpot> sightseeingSpots = provinceService.getSightSeeingSpotOfProvince(id);

        return sightseeingSpots;
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public SightseeingSpot handleAddNewSightseeingSpot(@PathVariable() Long id, @RequestBody() @Valid() SightseeingSpotDTO sightseeingSpotDTO) {
        SightseeingSpot newSightseeingSpot = provinceService.addNewSightseeingSpot(id, sightseeingSpotDTO);

        return newSightseeingSpot;
    }
}
