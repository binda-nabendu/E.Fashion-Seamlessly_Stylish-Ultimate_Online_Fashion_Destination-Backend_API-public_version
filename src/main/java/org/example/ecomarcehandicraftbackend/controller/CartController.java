package org.example.ecomarcehandicraftbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.ecomarcehandicraftbackend.config.JwtConstant;
import org.example.ecomarcehandicraftbackend.exception.ProductException;
import org.example.ecomarcehandicraftbackend.exception.UserException;
import org.example.ecomarcehandicraftbackend.model.Cart;
import org.example.ecomarcehandicraftbackend.model.User;
import org.example.ecomarcehandicraftbackend.request.AddItemRequest;
import org.example.ecomarcehandicraftbackend.response.ApiResponse;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.CartService;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@Tag(name="Cart Management", description= "find user cart, add item to cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    @Operation(description = "find cart by user id")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.FOUND);
    }

    @PutMapping("/add")
    @Operation(description = "add item to cart")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest addItemRequest, @RequestHeader(JwtConstant.JWT_HEADER)String jwt)
        throws ProductException, UserException{
        User user = userService.findUserProfileByJwt(jwt);
        cartService.addCartItem(user.getId(), addItemRequest);

        ApiResponse apiResponse = new ApiResponse("Product added", true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}