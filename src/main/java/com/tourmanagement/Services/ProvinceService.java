package com.tourmanagement.Services;

import com.tourmanagement.DTOs.Payload.PaginationRequest;
import com.tourmanagement.DTOs.Request.SightseeingSpotDTO;
import com.tourmanagement.DTOs.Response.PaginationRespDTO;
import com.tourmanagement.DTOs.Response.SightseeingSpotRespDTO;
import com.tourmanagement.Models.Province;
import com.tourmanagement.Models.SightseeingSpot;
import com.tourmanagement.Repositorys.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Province> createProvince(List<Province> provinces) {
        List<Province> newProvinces = new ArrayList<>();
        for (Province province : provinces){
            Province newProvince = new Province();
            newProvince.setId(province.getId());
            newProvince.setName(province.getName());
            newProvince.setCodename(province.getCodename());
            newProvinces.add(newProvince);
        }

        return provinceRepository.saveAll(newProvinces);
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

    public PaginationRespDTO<SightseeingSpotRespDTO> getSightSeeingSpotOfProvincePage(Long id, PaginationRequest pagination) {
        getProvinceById(id);
        List<SightseeingSpot> sightseeingSpots = sightseeingSpotService.getSightseeingSpotsByProvinceId(id);

        PaginationRespDTO<SightseeingSpotRespDTO> result = new PaginationRespDTO<SightseeingSpotRespDTO>();

        result.setTotal(Long.valueOf(sightseeingSpots.size()));
        result.setPage(pagination.getPage());
        result.setItemsPerPage(pagination.getItemsPerPage());
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getItemsPerPage());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), sightseeingSpots.size());

        List<SightseeingSpot> paginatedList = sightseeingSpots.subList(start, end);
        Page<SightseeingSpot> SightseeingSpotPage = new PageImpl<>(paginatedList, pageable, sightseeingSpots.size());

        result.setData(
                SightseeingSpotPage.getContent().stream()
                        .map(SightseeingSpotRespDTO::convert)
                        .collect(Collectors.toList()));
        return result;
    }

    public SightseeingSpot addNewSightseeingSpot(Long provinceId, SightseeingSpotDTO sightseeingSpotDTO) {
        Province province = getProvinceById(provinceId);

        SightseeingSpot newSightseeingSpot = new SightseeingSpot();
        newSightseeingSpot.setProvince(province);
        newSightseeingSpot.setName(sightseeingSpotDTO.getName());
        newSightseeingSpot.setAddress(sightseeingSpotDTO.getAddress());

        return sightseeingSpotService.createNewSightseeingSpot(newSightseeingSpot);
    }

    public SightseeingSpot updateExistedSightseeingSpot(Long provinceId, Long sightseeingId, SightseeingSpotDTO sightseeingSpotDTO) {
        getProvinceById(provinceId);

        SightseeingSpot updatedSightseeingSpot = sightseeingSpotService.updateSightseeingSpot(sightseeingId, sightseeingSpotDTO);

        return updatedSightseeingSpot;
    }

    public void removeSightseeingSpot(Long provinceId, Long sightseeingId) {
        getProvinceById(provinceId);
        sightseeingSpotService.deleteSightseeingSpot(sightseeingId);
    }
}
