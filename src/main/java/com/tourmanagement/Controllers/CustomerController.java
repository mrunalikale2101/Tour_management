package com.tourmanagement.Controllers;

import com.tourmanagement.DTOs.Request.CustomerReqDTO;
import com.tourmanagement.DTOs.Response.CustomerDTO;
import com.tourmanagement.Models.Customer;
import com.tourmanagement.Services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@ResponseStatus(HttpStatus.OK)
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();

        return customers;
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
       Customer customer = customerService.getCustomerById(id);

       return customer;
    }

    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@ModelAttribute @Valid CustomerReqDTO customerDTO) {
        Customer newCustomer = customerService.createCustomer(customerDTO);

        return newCustomer;
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer updateCustomer(@PathVariable Long id, @ModelAttribute @Valid CustomerReqDTO customerDTO) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDTO);

        return updatedCustomer;
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);

        return "Customer with [%S] deleted successfully!".formatted(id);
    }
}

