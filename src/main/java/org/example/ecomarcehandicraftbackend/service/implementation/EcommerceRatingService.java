package org.example.ecomarcehandicraftbackend.service.implementation;

import org.example.ecomarcehandicraftbackend.exception.ProductException;
import org.example.ecomarcehandicraftbackend.model.Product;
import org.example.ecomarcehandicraftbackend.model.Rating;
import org.example.ecomarcehandicraftbackend.model.User;
import org.example.ecomarcehandicraftbackend.repository.RatingRepository;
import org.example.ecomarcehandicraftbackend.request.RatingRequest;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.ProductService;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.RatingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EcommerceRatingService implements RatingService {
    private RatingRepository ratingRepository;
    private ProductService productService;

    public EcommerceRatingService(RatingRepository ratingRepository, ProductService productService) {
        this.ratingRepository = ratingRepository;
        this.productService = productService;
    }

    @Override
    public Rating giveRating(RatingRequest ratingRequest, User user) throws ProductException {
        Product product = productService.findProductById(ratingRequest.getProductId());

        Rating rating = new Rating();
        rating.setRating(ratingRequest.getRating());
        rating.setProduct(product);
        rating.setUser(user);
        rating.setCreatedAt(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductRating(Long productId) {
        return ratingRepository.getAllProductsRating(productId);
    }
}
