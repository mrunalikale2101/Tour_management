package com.tourmanagement.Services;


import com.tourmanagement.DTOs.CustomerDTO;
import com.tourmanagement.DTOs.DiscountDTO;
import com.tourmanagement.Models.Customer;
import com.tourmanagement.Models.Discount;
import com.tourmanagement.Models.Tour;
import com.tourmanagement.Repositorys.DiscountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DiscountService(DiscountRepository discountRepository, ModelMapper modelMapper) {
        this.discountRepository = discountRepository;
        this.modelMapper = modelMapper;
    }

    public List<Discount> getAllDiscount() {
        return discountRepository.findAll();
    }

    public Discount getDiscountById(Long id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount with id [%s]".formatted(id)));

        return discount;
    }

    public Discount createDiscount(DiscountDTO discountDTO) {

        // check tour exist --> get tour

        System.out.println(discountDTO);
        Discount discount = modelMapper.map(discountDTO, Discount.class);

        return discountRepository.save(discount);
    }

    public Discount updateDiscount(Long id, DiscountDTO discountDTO) {
        Discount oldDiscount = getDiscountById(id);
        modelMapper.map(discountDTO, oldDiscount);
        return discountRepository.save(oldDiscount);
    }

    public void deleteDiscount(Long id) {
        getDiscountById(id);
        discountRepository.deleteById(id);
    }

}
