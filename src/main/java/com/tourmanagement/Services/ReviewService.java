package com.tourmanagement.Services;

import com.tourmanagement.DTOs.Request.ReviewDTO;
import com.tourmanagement.DTOs.Response.ReviewRespDTO;
import com.tourmanagement.Models.Customer;
import com.tourmanagement.Models.Review;
import com.tourmanagement.Models.Tour;
import com.tourmanagement.Repositorys.ReviewRepository;
import com.tourmanagement.Shared.Utils.EntityDtoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomerService customerService;
    private final TourService tourService;
    private final ModelMapper modelMapper;
    private final EntityDtoConverter entityConverter;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, CustomerService customerService, TourService tourService, ModelMapper modelMapper, EntityDtoConverter entityConverter) {
        this.reviewRepository = reviewRepository;
        this.customerService = customerService;
        this.tourService = tourService;
        this.modelMapper = modelMapper;
        this.entityConverter = entityConverter;
    }

    public List<ReviewRespDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(entityConverter::convertToReviewResponseDTO)
                .collect(Collectors.toList());
    }

    public Review getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with id [%s] is not found".formatted(id)));
        return review;
    }

    public ReviewRespDTO getReviewResponseById(Long id) {
        Review review = getReviewById(id);
        return entityConverter.convertToReviewResponseDTO(review);
    }

    public ReviewRespDTO createReview(ReviewDTO reviewDTO) {
        Customer customer = customerService.getCustomerById(reviewDTO.getCustomerId());
        Tour tour = tourService.getTourById(reviewDTO.getTourId());

        Review review = modelMapper.map(reviewDTO, Review.class);
        review.setCustomer(customer);
        review.setTour(tour);
        return entityConverter.convertToReviewResponseDTO(reviewRepository.save(review));
    }

    public ReviewRespDTO updateReview(Long id, ReviewDTO reviewDTO) {
        Review oldReview = getReviewById(id);
        modelMapper.map(reviewDTO, oldReview);
        return entityConverter.convertToReviewResponseDTO(reviewRepository.save(oldReview));
    }

    public void deleteReview(Long id) {
        getReviewById(id);
        reviewRepository.deleteById(id);
    }

    public List<ReviewRespDTO> getReviewsForTour(Long tourId) {
        List<Review> reviews = reviewRepository.findByTourId(tourId);

        return reviews.stream()
                .map(entityConverter::convertToReviewResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ReviewRespDTO> getReviewsForCustomer(Long customerId) {
        List<Review> reviews = reviewRepository.findByCustomerId(customerId);

        return reviews.stream()
                .map(entityConverter::convertToReviewResponseDTO)
                .collect(Collectors.toList());
    }

}
