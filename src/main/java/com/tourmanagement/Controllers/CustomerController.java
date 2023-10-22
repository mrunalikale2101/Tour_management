package com.tourmanagement.Controllers;

import com.tourmanagement.DTOs.CustomerDTO;
import com.tourmanagement.Models.Customer;
import com.tourmanagement.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer newCustomer = customerService.createCustomer(customerDTO);

        return newCustomer;
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDTO);

        return updatedCustomer;
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);

        return "Customer with [%S] deleted successfully!".formatted(id);
    }
}

