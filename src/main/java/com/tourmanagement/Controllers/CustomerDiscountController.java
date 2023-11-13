package com.tourmanagement.Controllers;


import com.tourmanagement.DTOs.Request.CustomerDiscountDTO;
import com.tourmanagement.DTOs.Response.CustomerDiscountRespDTO;
import com.tourmanagement.Services.CustomerDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer-discount")
@ResponseStatus(HttpStatus.OK)
public class CustomerDiscountController {

    private final CustomerDiscountService customerDiscountService;


    @Autowired
    public CustomerDiscountController(CustomerDiscountService customerDiscountService) {
        this.customerDiscountService = customerDiscountService;
    }


    @GetMapping
    public List<CustomerDiscountRespDTO> getAllCustomerDiscount() {
        List<CustomerDiscountRespDTO> discounts = customerDiscountService.getAllCustomerDiscount();

        return discounts;
    }

    @GetMapping("/{id}")
    public CustomerDiscountRespDTO getCustomerDiscountById(@PathVariable Long id) {
        CustomerDiscountRespDTO discount = customerDiscountService.getCustomerDiscountResponseById(id);

        return discount;
    }

    @GetMapping("/tour/{tourId}")
    public List<CustomerDiscountRespDTO> getAllCustomerDiscountByTour(@PathVariable Long tourId) {
        List<CustomerDiscountRespDTO> discounts = customerDiscountService.getAllCustomerDiscountByTour(tourId);
        return discounts;
    }

    @GetMapping("/customer/{customerId}")
    public List<CustomerDiscountRespDTO> getAllCustomerDiscountByCustomer(@PathVariable Long customerId) {
        List<CustomerDiscountRespDTO> discounts = customerDiscountService.getAllCustomerDiscountByCustomer(customerId);
        return discounts;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDiscountRespDTO createCustomerDiscount(@RequestBody CustomerDiscountDTO customerDiscountRespDTO) {
        CustomerDiscountRespDTO newDiscount = customerDiscountService.createCustomerDiscount(customerDiscountRespDTO);

        return newDiscount;
    }

    @PutMapping("/{id}")
    public CustomerDiscountRespDTO updateCustomerDiscount(@PathVariable Long id, @RequestBody CustomerDiscountDTO customerDiscountDTO) {
        CustomerDiscountRespDTO updatedDiscount = customerDiscountService.updateCustomerDiscount(id, customerDiscountDTO);

        return updatedDiscount;
    }

    @DeleteMapping("/{id}")
    public String deleteDiscount(@PathVariable Long id) {
        customerDiscountService.deleteCustomerDiscount(id);

        return "CustomerDiscount with [%S] deleted successfully!".formatted(id);
    }
}
