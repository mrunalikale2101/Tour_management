package com.tourmanagement.Controllers;

import com.tourmanagement.DTOs.ReviewDTO;
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
    public List<Review> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return reviews;
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Long id) {
        Review review = reviewService.getReviewById(id);
        return review;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Review createReview(@RequestBody ReviewDTO reviewDTO) {
        Review newReview = reviewService.createReview(reviewDTO);
        return newReview;
    }

    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) {
        Review updatedReview = reviewService.updateReview(id, reviewDTO);
        return updatedReview;
    }

    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "Review with [%s] deleted successfully!".formatted(id);
    }

}
