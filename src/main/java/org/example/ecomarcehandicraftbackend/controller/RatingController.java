package org.example.ecomarcehandicraftbackend.controller;


import org.example.ecomarcehandicraftbackend.exception.ProductException;
import org.example.ecomarcehandicraftbackend.exception.UserException;
import org.example.ecomarcehandicraftbackend.model.Rating;
import org.example.ecomarcehandicraftbackend.model.User;
import org.example.ecomarcehandicraftbackend.request.RatingRequest;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.RatingService;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final UserService userService;
    private final RatingService ratingService;

    public RatingController(UserService userService, RatingService ratingService) {
        this.userService = userService;
        this.ratingService = ratingService;
    }

    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest ratingRequest,
                                               @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Rating rating = ratingService.giveRating(ratingRequest, user);
        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductRating(@PathVariable Long productId,
                                               @RequestHeader("Authorization") String jwt){
        List<Rating> rating = ratingService.getProductRating(productId);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }
}
