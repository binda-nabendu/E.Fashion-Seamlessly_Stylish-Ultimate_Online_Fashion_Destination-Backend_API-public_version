package org.example.ecomarcehandicraftbackend.service.implementation;

import org.example.ecomarcehandicraftbackend.exception.ProductException;
import org.example.ecomarcehandicraftbackend.model.Product;
import org.example.ecomarcehandicraftbackend.model.Review;
import org.example.ecomarcehandicraftbackend.model.User;
import org.example.ecomarcehandicraftbackend.repository.ProductRepository;
import org.example.ecomarcehandicraftbackend.repository.ReviewRepository;
import org.example.ecomarcehandicraftbackend.model.request.ReviewRequest;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.ProductService;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.ReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EcommerceReviewService implements ReviewService {
    private ReviewRepository reviewRepository;
    private ProductService productService;
    private ProductRepository productRepository;

    public EcommerceReviewService(ReviewRepository reviewRepository, ProductService productService, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Override
    public Review createReview(ReviewRequest reviewRequest, User user) throws ProductException {
        Product product = productService.findProductById(reviewRequest.getProductId());

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReview(review.getReview());
        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllReviewWithProduct(productId);
    }
}
