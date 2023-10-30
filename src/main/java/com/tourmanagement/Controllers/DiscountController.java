package com.tourmanagement.Controllers;

import com.tourmanagement.DTOs.Request.DiscountDTO;
import com.tourmanagement.DTOs.Response.DiscountRespDTO;
import com.tourmanagement.Services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("discount")
@ResponseStatus(HttpStatus.OK)
public class DiscountController {

    private final DiscountService discountService;


    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }


    @GetMapping
    public List<DiscountRespDTO> getAllDiscount() {
        List<DiscountRespDTO> discounts = discountService.getAllDiscount();

        return discounts;
    }

    @GetMapping("/{id}")
    public DiscountRespDTO getDiscountById(@PathVariable Long id) {
        DiscountRespDTO discount = discountService.getDiscountResponseById(id);

        return discount;
    }

    @GetMapping("/tour/{tourId}")
    public List<DiscountRespDTO> getALlDiscountByTour(@PathVariable Long tourId) {
        List<DiscountRespDTO> discounts = discountService.getALlDiscountByTour(tourId);
        return discounts;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public DiscountRespDTO createDiscount(@RequestBody DiscountDTO discountDTO) {
        DiscountRespDTO newDiscount = discountService.createDiscount(discountDTO);

        return newDiscount;
    }

    @PutMapping("/{id}")
    public DiscountRespDTO updateDiscount(@PathVariable Long id, @RequestBody DiscountDTO discountDTO) {
        DiscountRespDTO updatedDiscount = discountService.updateDiscount(id, discountDTO);

        return updatedDiscount;
    }

    @DeleteMapping("/{id}")
    public String deleteDiscount(@PathVariable Long id) {
        discountService.deleteDiscount(id);

        return "Discount with [%S] deleted successfully!".formatted(id);
    }
}
