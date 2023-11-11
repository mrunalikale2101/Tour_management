package com.tourmanagement.Services;

import com.tourmanagement.DTOs.Request.CustomerDiscountDTO;
import com.tourmanagement.DTOs.Request.DiscountDTO;
import com.tourmanagement.DTOs.Response.CustomerDiscountRespDTO;
import com.tourmanagement.DTOs.Response.DiscountRespDTO;
import com.tourmanagement.Models.Customer;
import com.tourmanagement.Models.CustomerDiscount;
import com.tourmanagement.Models.Discount;
import com.tourmanagement.Models.Tour;
import com.tourmanagement.Repositorys.CustomerDiscountRepository;
import com.tourmanagement.Shared.Utils.EntityDtoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerDiscountService {

    private final CustomerDiscountRepository customerDiscountRepository;
    private final ModelMapper modelMapper;
    private final TourService tourService;
    private final DiscountService discountService;
    private final CustomerService customerService;
    private final EntityDtoConverter entityDtoConverter;


    @Autowired
    public CustomerDiscountService(CustomerDiscountRepository customerDiscountRepository, ModelMapper modelMapper, TourService tourService, DiscountService discountService, CustomerService customerService, EntityDtoConverter entityDtoConverter) {
        this.customerDiscountRepository = customerDiscountRepository;
        this.modelMapper = modelMapper;
        this.tourService = tourService;
        this.discountService = discountService;
        this.customerService = customerService;
        this.entityDtoConverter = entityDtoConverter;
    }



    public List<CustomerDiscountRespDTO> getAllCustomerDiscount() {
        List<CustomerDiscount> discounts = customerDiscountRepository.findAll();
        return discounts.stream()
                .map(entityDtoConverter::convertToCustomerDiscountRespDTO)
                .collect(Collectors.toList());
    }

    public CustomerDiscount getCustomerDiscountById(Long id) {
        CustomerDiscount customerDiscount = customerDiscountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CustomerDiscount with id [%s] is not found".formatted(id)));

        return customerDiscount;
    }

    public CustomerDiscountRespDTO getCustomerDiscountResponseById(Long id) {
        CustomerDiscount customerDiscount = getCustomerDiscountById(id);
        CustomerDiscountRespDTO result = entityDtoConverter.convertToCustomerDiscountRespDTO(customerDiscount);
        return result;
    }

    public List<CustomerDiscountRespDTO> getAllCustomerDiscountByTour(Long tourId){
        tourService.getTourById(tourId);
        List<CustomerDiscount> discounts = customerDiscountRepository.findAllCustomerDiscountByTour(tourId);
        return discounts.stream()
                .map(entityDtoConverter::convertToCustomerDiscountRespDTO)
                .collect(Collectors.toList());
    }

    public List<CustomerDiscountRespDTO> getAllCustomerDiscountByCustomer(Long customerId){
        customerService.getCustomerById(customerId);
        List<CustomerDiscount> discounts = customerDiscountRepository.findCustomerDiscountByCustomer(customerId);
        return discounts.stream()
                .map(entityDtoConverter::convertToCustomerDiscountRespDTO)
                .collect(Collectors.toList());
    }

    public CustomerDiscountRespDTO createCustomerDiscount(CustomerDiscountDTO customerDiscountDTO) {
        Tour tour = tourService.getTourById(customerDiscountDTO.getTourId());
        Customer customer = customerService.getCustomerById(customerDiscountDTO.getCustomerId());
        Discount discount = discountService.getDiscountById(customerDiscountDTO.getDiscountId());

        CustomerDiscount customerDiscount = modelMapper.map(customerDiscountDTO, CustomerDiscount.class);
        customerDiscount.setTour(tour);
        customerDiscount.setCustomer(customer);
        customerDiscount.setDiscount(discount);

        return entityDtoConverter.convertToCustomerDiscountRespDTO(customerDiscountRepository.save(customerDiscount));
    }

    public CustomerDiscountRespDTO updateCustomerDiscount(Long id, CustomerDiscountDTO customerDiscountDTO) {
        CustomerDiscount oldDiscount = getCustomerDiscountById(id);
        Tour tour = tourService.getTourById(customerDiscountDTO.getTourId());
        Customer customer = customerService.getCustomerById(customerDiscountDTO.getCustomerId());
        Discount discount = discountService.getDiscountById(customerDiscountDTO.getDiscountId());

        modelMapper.map(customerDiscountDTO, oldDiscount);
        oldDiscount.setTour(tour);
        oldDiscount.setCustomer(customer);
        oldDiscount.setDiscount(discount);

        return entityDtoConverter.convertToCustomerDiscountRespDTO(customerDiscountRepository.save(oldDiscount));
    }

    public void deleteCustomerDiscount(Long id) {
        getCustomerDiscountById(id);
        customerDiscountRepository.deleteById(id);
    }

}
