package org.example.ecomarcehandicraftbackend.service.service_interfaces;

import org.example.ecomarcehandicraftbackend.exception.ProductException;
import org.example.ecomarcehandicraftbackend.model.Review;
import org.example.ecomarcehandicraftbackend.model.User;
import org.example.ecomarcehandicraftbackend.request.ReviewRequest;

import java.util.List;

public interface ReviewService {
    public Review createReview(ReviewRequest reviewRequest, User user) throws ProductException;
    public List<Review> getAllReview(Long productId);
}
