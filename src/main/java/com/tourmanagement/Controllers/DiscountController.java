package com.tourmanagement.Controllers;

import com.tourmanagement.DTOs.CustomerDTO;
import com.tourmanagement.DTOs.DiscountDTO;
import com.tourmanagement.Models.Customer;
import com.tourmanagement.Models.Discount;
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
    public List<Discount> getAllDiscount() {
        List<Discount> discounts = discountService.getAllDiscount();

        return discounts;
    }

    @GetMapping("/{id}")
    public Discount getDiscountById(@PathVariable Long id) {
        Discount discount = discountService.getDiscountById(id);

        return discount;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Discount createDiscount(@RequestBody DiscountDTO discountDTO) {
        Discount newDiscount = discountService.createDiscount(discountDTO);

        return newDiscount;
    }

    @PutMapping("/{id}")
    public Discount updateDiscount(@PathVariable Long id, @RequestBody DiscountDTO discountDTO) {
        Discount updatedDiscount = discountService.updateDiscount(id, discountDTO);

        return updatedDiscount;
    }

    @DeleteMapping("/{id}")
    public String deleteDiscount(@PathVariable Long id) {
        discountService.deleteDiscount(id);

        return "Discount with [%S] deleted successfully!".formatted(id);
    }
}
