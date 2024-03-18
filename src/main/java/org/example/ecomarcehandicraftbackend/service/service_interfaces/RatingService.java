package org.example.ecomarcehandicraftbackend.service.service_interfaces;

import org.example.ecomarcehandicraftbackend.exception.ProductException;
import org.example.ecomarcehandicraftbackend.model.Rating;
import org.example.ecomarcehandicraftbackend.model.User;
import org.example.ecomarcehandicraftbackend.request.RatingRequest;

import java.util.List;

public interface RatingService {
    public Rating giveRating(RatingRequest ratingRequest, User user) throws ProductException;
    public List<Rating> getProductRating(Long productId);
}
