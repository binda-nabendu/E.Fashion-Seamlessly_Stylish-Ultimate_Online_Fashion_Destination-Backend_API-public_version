package org.example.ecomarcehandicraftbackend.controller;


import org.example.ecomarcehandicraftbackend.exception.ProductException;
import org.example.ecomarcehandicraftbackend.exception.UserException;
import org.example.ecomarcehandicraftbackend.model.Review;
import org.example.ecomarcehandicraftbackend.model.User;
import org.example.ecomarcehandicraftbackend.model.request.ReviewRequest;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.ReviewService;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private ReviewService reviewService;

    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Review> createReviewReview(@RequestBody ReviewRequest req, @RequestHeader("Authorization") String jwt)
            throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Review review = reviewService.createReview(req, user);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductsReview(@PathVariable Long productId) throws UserException, ProductException{
        List<Review> reviews = reviewService.getAllReview(productId);
        return new ResponseEntity<>(reviews, HttpStatus.FOUND);
    }
}