package com.tourmanagement.Services;
import com.tourmanagement.DTOs.Request.CustomerReqDTO;
import com.tourmanagement.DTOs.Response.CustomerDTO;
import com.tourmanagement.Models.Customer;
import com.tourmanagement.Repositorys.CustomerRepository;
import com.tourmanagement.Shared.Utils.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final ImageService imageService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper, ImageService imageService) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.imageService = imageService;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Long getCountCustomers() {
        return customerRepository.count();
    }

    public Customer getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with id [%s] is not found".formatted(id)));

        return customer;
    }

    public Customer createCustomer(CustomerReqDTO customerDTO) {
        if(imageService.isEmptyFile(customerDTO.getAvatar())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No uploaded images");
        }
        Customer customer = modelMapper.map(customerDTO, Customer.class);

        String imageUrl = this.imageService.uploadSingleImage(customerDTO.getAvatar(), "customer");
        customer.setAvatar(imageUrl);

        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, CustomerReqDTO customerDTO) {
        Customer oldCustomer = getCustomerById(id);
        if(customerDTO.getAvatar() != null && !oldCustomer.getAvatar().isEmpty()){
            try {
                imageService.deleteImage("customer", getFileNameImage(oldCustomer.getAvatar()));
            } catch (Exception e){
                e.printStackTrace();
            }
            String newImageUrl = imageService.uploadSingleImage(customerDTO.getAvatar(), "customer");
            modelMapper.map(customerDTO, oldCustomer);
            oldCustomer.setAvatar(newImageUrl);
        }else {
            String oldUrl = oldCustomer.getAvatar();
            modelMapper.map(customerDTO, oldCustomer);
            oldCustomer.setAvatar(oldUrl);
        }
        return customerRepository.save(oldCustomer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = getCustomerById(id);
        imageService.deleteImage("customer", getFileNameImage(customer.getAvatar()));
        customerRepository.deleteById(id);
    }

    private String getFileNameImage(String imageUrl){
        String fileName = "";

        String[] lst = imageUrl.split("/");

        fileName = lst[lst.length - 1];

        return fileName;
    }
}
