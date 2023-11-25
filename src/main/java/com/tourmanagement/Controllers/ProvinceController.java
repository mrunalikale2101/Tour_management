package com.tourmanagement.Controllers;

import com.tourmanagement.DTOs.Payload.PaginationRequest;
import com.tourmanagement.DTOs.Request.SightseeingSpotDTO;
import com.tourmanagement.DTOs.Response.PaginationRespDTO;
import com.tourmanagement.DTOs.Response.SightseeingSpotRespDTO;
import com.tourmanagement.Models.Province;
import com.tourmanagement.Models.SightseeingSpot;
import com.tourmanagement.Services.ProvinceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public List<Province> handleAddNewProvince(@RequestBody @Valid List<Province> province) {
        List<Province> newProvince = provinceService.createProvince(province);
        return newProvince;
    }

    @GetMapping("/{id}/sightseeing-spots")
    public List<SightseeingSpot> handleGetSightseeingSpotByProvinceId(@PathVariable Long id) {
        List<SightseeingSpot> sightseeingSpots = provinceService.getSightSeeingSpotOfProvince(id);

        return sightseeingSpots;
    }

    @GetMapping("/{id}/sightseeing-spots-page")
    public PaginationRespDTO<SightseeingSpotRespDTO> handleGetSightseeingSpotByProvinceIdPage(@PathVariable Long id, @ModelAttribute PaginationRequest paginationRequest) {
        return provinceService.getSightSeeingSpotOfProvincePage(id, paginationRequest);

    }

    @PostMapping("/{id}/sightseeing-spots")
    @ResponseStatus(HttpStatus.CREATED)
    public SightseeingSpot handleAddNewSightseeingSpot(@PathVariable() Long id, @RequestBody() @Valid() SightseeingSpotDTO sightseeingSpotDTO) {
        SightseeingSpot newSightseeingSpot = provinceService.addNewSightseeingSpot(id, sightseeingSpotDTO);

        return newSightseeingSpot;
    }

    @DeleteMapping("/{id}/sightseeing-spots/{sightseeingId}")
    public String handleRemoveSightseeingSpot(@PathVariable() Long id, @PathVariable() Long sightseeingId) {
        provinceService.removeSightseeingSpot(id, sightseeingId);

        return "Sightseeing spot id [%s] of province id [%s] is removed successfully!".formatted(sightseeingId, id);
    }

    @PostMapping("/{id}/sightseeing-spots/{sightseeingId}")
    public SightseeingSpot handleUpdateSightseeingSpot(
            @PathVariable() Long id,
            @PathVariable() Long sightseeingId,
            @RequestBody() @Valid() SightseeingSpotDTO sightseeingSpotDTO
    ) {
        SightseeingSpot updateSightseeingSpot = provinceService.updateExistedSightseeingSpot(id, sightseeingId, sightseeingSpotDTO);

        return updateSightseeingSpot;
    }
}
