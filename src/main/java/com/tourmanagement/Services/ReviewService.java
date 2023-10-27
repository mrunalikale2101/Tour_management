package com.tourmanagement.Services;

import com.tourmanagement.DTOs.ReviewDTO;
import com.tourmanagement.Models.Customer;
import com.tourmanagement.Models.Review;
import com.tourmanagement.Repositorys.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomerService customerService;

    // tour service below
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, CustomerService customerService, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with id [%s]".formatted(id)));
        return review;
    }

    public Review createReview(ReviewDTO reviewDTO) {

        Customer customer = customerService.getCustomerById(reviewDTO.getCustomerId());
        // get tour
        // Tour tour = tourService.getTourById(reviewDTO

        Review review = modelMapper.map(reviewDTO, Review.class);
        review.setCustomer(customer);
        //review.setTour(tour);
        return reviewRepository.save(review);
    }

    public Review updateReview(Long id, ReviewDTO reviewDTO) {
        Review oldReview = getReviewById(id);
        modelMapper.map(reviewDTO, oldReview);
        return reviewRepository.save(oldReview);
    }

    public void deleteReview(Long id) {
        getReviewById(id);
        reviewRepository.deleteById(id);
    }


}
