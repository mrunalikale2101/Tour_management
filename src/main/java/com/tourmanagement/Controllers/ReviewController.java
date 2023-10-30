package com.tourmanagement.Controllers;

import com.tourmanagement.DTOs.Request.ReviewDTO;
import com.tourmanagement.DTOs.Response.ReviewRespDTO;
import com.tourmanagement.Models.Review;
import com.tourmanagement.Services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@ResponseStatus(HttpStatus.OK)
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<ReviewRespDTO> getAllReviews() {
        List<ReviewRespDTO> reviews = reviewService.getAllReviews();
        return reviews;
    }

    @GetMapping("/{id}")
    public ReviewRespDTO getReviewById(@PathVariable Long id) {
        ReviewRespDTO review = reviewService.getReviewResponseById(id);
        return review;
    }

    @GetMapping("/tour/{tourId}")
    public List<ReviewRespDTO> getReviewsForTour(@PathVariable Long tourId) {
        List<ReviewRespDTO> reviews = reviewService.getReviewsForTour(tourId);
        return reviews;
    }

    @GetMapping("/customer/{customerId}")
    public List<ReviewRespDTO> getReviewForCustomer(@PathVariable Long customerId) {
        List<ReviewRespDTO> reviews = reviewService.getReviewsForCustomer(customerId);
        return reviews;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewRespDTO createReview(@RequestBody ReviewDTO reviewDTO) {
        ReviewRespDTO newReview = reviewService.createReview(reviewDTO);
        return newReview;
    }

    @PutMapping("/{id}")
    public ReviewRespDTO updateReview(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) {
        ReviewRespDTO updatedReview = reviewService.updateReview(id, reviewDTO);
        return updatedReview;
    }

    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "Review with [%s] deleted successfully!".formatted(id);
    }

}
