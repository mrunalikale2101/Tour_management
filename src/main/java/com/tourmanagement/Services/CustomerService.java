package com.tourmanagement.Services;
import com.tourmanagement.DTOs.CustomerDTO;
import com.tourmanagement.Models.Customer;
import com.tourmanagement.Repositorys.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isPresent()) {
            return existingCustomer;
        } else {
            throw new EntityNotFoundException("No customer found with ID:  " + id);
        }
    }

    public Customer createCustomer(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, CustomerDTO customerDTO) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isPresent()) {
            Customer customer = existingCustomer.get();
            modelMapper.map(customerDTO, customer);
            return customerRepository.save(customer);
        } else {
            throw new EntityNotFoundException("No customer found with ID:  " + id);
        }
    }

    public void deleteCustomer(Long id) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isPresent()) {
            customerRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("No customer found with ID: " + id);
        }
    }

}
