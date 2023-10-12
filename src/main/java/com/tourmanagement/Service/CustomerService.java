package com.tourmanagement.Service;
import com.tourmanagement.DTOs.CustomerDTO;
import com.tourmanagement.Model.Customer;
import com.tourmanagement.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        // Map fields from DTO to Customer
        customer.setName(customerDTO.getName());
        customer.setAddress(customerDTO.getAddress());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setEmail(customerDTO.getEmail());
        customer.setIdCard(customerDTO.getIdCard());
        customer.setGender(customerDTO.getGender());
        customer.setDateOfBirth(customerDTO.getDateOfBirth());
        customer.setAvatar(customerDTO.getAvatar());
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, CustomerDTO customerDTO) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isPresent()) {
            Customer customer = existingCustomer.get();
            // Map fields from DTO to existing Customer
            customer.setName(customerDTO.getName());
            customer.setAddress(customerDTO.getAddress());
            customer.setPhoneNumber(customerDTO.getPhoneNumber());
            customer.setEmail(customerDTO.getEmail());
            customer.setIdCard(customerDTO.getIdCard());
            customer.setGender(customerDTO.getGender());
            customer.setDateOfBirth(customerDTO.getDateOfBirth());
            customer.setAvatar(customerDTO.getAvatar());
            return customerRepository.save(customer);
        }
        return null; // Không tìm thấy khách hàng cần cập nhật
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
