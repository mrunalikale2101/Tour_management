package com.tourmanagement.Services;


import com.tourmanagement.DTOs.Request.DiscountDTO;
import com.tourmanagement.DTOs.Response.DiscountRespDTO;
import com.tourmanagement.Models.Discount;
import com.tourmanagement.Models.Review;
import com.tourmanagement.Models.Tour;
import com.tourmanagement.Repositorys.DiscountRepository;
import com.tourmanagement.Shared.Utils.EntityDtoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final ModelMapper modelMapper;
    private final TourService tourService;
    private final EntityDtoConverter entityDtoConverter;

    @Autowired
    public DiscountService(DiscountRepository discountRepository, ModelMapper modelMapper, TourService tourService, EntityDtoConverter entityDtoConverter) {
        this.discountRepository = discountRepository;
        this.modelMapper = modelMapper;
        this.tourService = tourService;
        this.entityDtoConverter = entityDtoConverter;
    }

    public List<DiscountRespDTO> getAllDiscount() {
        List<Discount> discounts = discountRepository.findAll();
        return discounts.stream()
                .map(entityDtoConverter::convertToDiscountRespDTO)
                .collect(Collectors.toList());
    }

    public Discount getDiscountById(Long id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount with id [%s] is not found".formatted(id)));

        return discount;
    }

    public DiscountRespDTO getDiscountResponseById(Long id) {
        Discount discount = getDiscountById(id);
        DiscountRespDTO result = entityDtoConverter.convertToDiscountRespDTO(discount);
        return result;
    }

    public List<DiscountRespDTO> getALlDiscountByTour(Long tourId){
        tourService.getTourById(tourId);
        List<Discount> discounts = discountRepository.findAllDiscountByTour(tourId);
        return discounts.stream()
                .map(entityDtoConverter::convertToDiscountRespDTO)
                .collect(Collectors.toList());
    }

    public DiscountRespDTO createDiscount(DiscountDTO discountDTO) {
        Tour tour = tourService.getTourById(discountDTO.getTourId());
        Discount discount = modelMapper.map(discountDTO, Discount.class);
        discount.setTour(tour);

        return entityDtoConverter.convertToDiscountRespDTO(discountRepository.save(discount));
    }

    public DiscountRespDTO updateDiscount(Long id, DiscountDTO discountDTO) {
        Discount oldDiscount = getDiscountById(id);
        Tour tour = tourService.getTourById(discountDTO.getTourId());

        modelMapper.map(discountDTO, oldDiscount);
        oldDiscount.setTour(tour);

        return entityDtoConverter.convertToDiscountRespDTO(discountRepository.save(oldDiscount));
    }

    public void deleteDiscount(Long id) {
        getDiscountById(id);
        discountRepository.deleteById(id);
    }

}
